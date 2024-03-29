package pt.isec.pa.aulas.four_in_a_row.model.data.version1;

import pt.isec.pa.aulas.four_in_a_row.model.data.IMazeElement;
import pt.isec.pa.aulas.four_in_a_row.model.data.Maze;
import pt.isec.pa.aulas.four_in_a_row.model.data.Piece;
import pt.isec.pa.aulas.four_in_a_row.model.data.memento.IMemento;
import pt.isec.pa.aulas.four_in_a_row.model.data.memento.IOriginator;

import java.io.Serial;
import java.io.Serializable;

public class FourInARowData implements Serializable, IOriginator {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final int HEIGHT = 6;
    private static final int WIDTH = 7;
    private static final int SEQUENCE = 4;

    Maze board;
    Piece currentPlayer;

    //transient int contador = 124;

    public FourInARowData() {
        init();
    }

    public void init() {
        board = new Maze(HEIGHT,WIDTH);
        currentPlayer = Math.random() < 0.5 ? Piece.YELLOW : Piece.RED;
    }

    public Piece getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean play(int column) {
        if (column < 1 || column > WIDTH)
            return false;
        column--;
        for (int y = HEIGHT - 1; y >= 0; y--)
            if (board.get(y, column) == null) {
                board.set(y, column, currentPlayer);
                currentPlayer = currentPlayer.other();
                return true;
            }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < HEIGHT; y++) {
            sb.append("+---".repeat(WIDTH)).append("+\n");
            for (int x = 0; x < WIDTH; x++) {
                char chPiece = board.get(y, x) instanceof Piece _piece ? _piece.getSymbol() : ' ';
                sb.append("| ").append(chPiece).append(" ");
            }
            sb.append("|\n");
        }
        sb.append("+---".repeat(WIDTH)).append("+");
        return sb.toString();
    }
    public boolean isFull() {
        for(int x=0;x<WIDTH;x++)
            if (board.get(0, x) == null )
                return false;
        return true;
    }


    public Piece checkWinner() {
        for (int y = 0; y < HEIGHT; y++)
            for (int x = 0; x < WIDTH; x++) {
                Piece piece = board.get(y, x) instanceof Piece _piece ? _piece : null;
                if (piece != null)
                    for (Direction d : Direction.values())
                        if (1+checkDirection(y + d.dy, x + d.dx, d, piece) >= SEQUENCE)
                            return piece;
            }
        return null;
    }

    private int checkDirection(int y, int x, Direction d, Piece p) {
        if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT || board.get(y,x) != p)
            return 0;
        return 1 + checkDirection(y + d.dy, x + d.dx, d, p);
    }

    private enum Direction {
        n(-1, 0), s(1, 0), e(0, 1), w(0, -1),
        nw(-1, -1), ne(-1, 1), se(1, 1), sw(1, -1);
        int dy, dx;

        Direction(int dy, int dx) {
            this.dy = dy;
            this.dx = dx;
        }
    }

    public char [][] getBoard() {
        return board.getMaze();
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getWidth() {
        return WIDTH;
    }
    private static class FourInARowMemento implements IMemento{
        private final IMazeElement[][] board;
        private final Piece currentPlayer;

        public FourInARowMemento(FourInARowData data){
            board = new IMazeElement[HEIGHT][WIDTH];
            for (int y = 0; y<HEIGHT; y++){
                for (int x=0;x<WIDTH;x++){
                    board[y][x] = data.currentPlayer;
                }
            }
            currentPlayer = data.currentPlayer;
        }
    }
    @Override
    public IMemento save() {
        return new FourInARowMemento(this);
    }

    @Override
    public void restore(IMemento memento) {
        if (memento instanceof FourInARowMemento m ){
            board = new Maze(HEIGHT,WIDTH);
            for (int y = 0; y<HEIGHT; y++){
                for (int x=0;x<WIDTH;x++){
                    board.set(y,x,m.board[y][x]);
                }
            }
            currentPlayer = m.currentPlayer;
        }
    }
}
