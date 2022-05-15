package CLImproved;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.util.Arrays;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        //JsonFileHandler-Test-----------------------------------------------------------
        //loads json file
        JSONFileHandler.init("test.json");
        System.out.println("getModes: " + Arrays.toString(JSONFileHandler.getModes()));//get all modes for header buttons
        System.out.println("getWords" + Arrays.toString(JSONFileHandler.getWords()));
        System.out.println("get Descriptions " + Arrays.toString(JSONFileHandler.getDescriptions()));

        //simulating user input
        System.out.println("\nuser input (pressed \"show\" button)");
        JSONFileHandler.pressedButton(3);
        System.out.println("getWords" + Arrays.toString(JSONFileHandler.getWords()));
        System.out.println("get Descriptions " + Arrays.toString(JSONFileHandler.getDescriptions()));
        JSONFileHandler.pressedButton(0);

        System.out.println("\n2nd user input");
        JSONFileHandler.pressedButton(0);
        System.out.println("getWords" + Arrays.toString(JSONFileHandler.getWords()));
        System.out.println("get Descriptions " + Arrays.toString(JSONFileHandler.getDescriptions()));
        JSONFileHandler.pressedButton(0);
        System.out.println("isParam: "+JSONFileHandler.isParam(0));

        //simulate mode change
        System.out.println("\nchange Mode");
        JSONFileHandler.changeMode(JSONFileHandler.getModes()[1]);
        System.out.println("getWords" + Arrays.toString(JSONFileHandler.getWords()));
        System.out.println("get Descriptions " + Arrays.toString(JSONFileHandler.getDescriptions()));
        //------------------------------------------------------------------------


        int x = 15;
        stage.setTitle("CLImproved");
        stage.setResizable(false);
        stage.setHeight(720);
        stage.setWidth(1280);

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
