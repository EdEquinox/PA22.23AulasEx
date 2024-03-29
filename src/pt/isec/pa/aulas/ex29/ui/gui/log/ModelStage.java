package pt.isec.pa.aulas.ex29.ui.gui.log;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pt.isec.pa.aulas.ex29.model.ModelLog;

public class ModelStage extends Stage {
    ModelLog model;

    public ModelStage(Stage stage) {
        this.model = ModelLog.getInstance();

        setScene(new Scene(new LogPane(),500,Screen.getPrimary().getVisualBounds().getHeight()));
        setX(Screen.getPrimary().getVisualBounds().getWidth()-500);
        setY(0);
        stage.addEventFilter(
                WindowEvent.WINDOW_CLOSE_REQUEST,
                windowEvent -> close()
        );
        show();
    }

    private class LogPane extends BorderPane {
        Button btnRefresh;
        ListView<String> lstLogs;
        private LogPane() {
            createViews();
            registerHandlers();
            update();
        }

        private void createViews() {
            lstLogs = new ListView<>();
            btnRefresh = new Button("Refresh");
            setCenter(lstLogs);
            btnRefresh.setPrefWidth(Integer.MAX_VALUE);
            setBottom(btnRefresh);
        }

        private void registerHandlers() {
            btnRefresh.setOnAction( a -> update());
        }

        private void update() {
            lstLogs.getItems().addAll(model.getLog());
            model.reset();
        }
    }
}