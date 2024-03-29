package pt.isec.pa.aulas.exemploFSMjavaFX.model.fsm;

import pt.isec.pa.aulas.exemploFSMjavaFX.model.data.GameBWData;
import pt.isec.pa.aulas.exemploFSMjavaFX.model.data.History;

import java.util.List;

public class GameBWContext {
    IGameBWState state;
    GameBWData data;

    public GameBWContext() {
        data = new GameBWData();
        state = GameBWState.BEGIN.createState(this,data);
    }

    void changeState(IGameBWState newState) {
        state = newState;
    }

    public void start() {
        state.start();
    }

    public void end() {
        state.end();
    }

    public BetResult bet(int nr_balls) {
        return state.bet(nr_balls);
    }

    public boolean loseWhiteball() {
        return state.loseWhiteball();
    }
    public boolean removeTwoBalls() {
        return state.removeTwoBalls();
    }

    // getters
    public GameBWState getState() {
        return state.getState();
    }

    public int getNrWhiteBallsWon() {
        return data.getNrWhiteBallsWon();
    }

    public int getNrWhiteBallsOut() {
        return data.getNrWhiteBallsOut();
    }

    public int getNrBlackBallsOut() {
        return data.getNrBlackBallsOut();
    }

    public boolean bagIsEmpty() {
        return data.bagIsEmpty();
    }

    public List<History> getHistory() {
        return state.getHistory();
    }

}
