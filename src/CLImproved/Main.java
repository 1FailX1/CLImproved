package CLImproved;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        stage.setTitle("CLImproved");
        stage.setResizable(false);
        stage.setMinHeight(1280);
        stage.setMinWidth(720);

        //Main Layout
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 1280, 820);
        stage.setScene(scene);

        HBox hBox1 = new HBox();

        //Adding to Layouts
        borderPane.setTop(hBox1);

        stage.show();
    }
}
