package CLImproved;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class Main extends Application {
    //test
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        //Backend-Test-----------------------------------------------------------
        //loads json file
        Backend.init();
        System.out.println("getModes: " + Arrays.toString(Backend.getModes()));//get all modes for header buttons
        System.out.println("getCommands" + Arrays.toString(Backend.getCommands()));
        System.out.println("get Descriptions " + Arrays.toString(Backend.getDescriptions()));

        //simulating user input
        System.out.println("\nuser input (pressed \"show\" button)");
        Backend.pressedButton(2);
        System.out.println("getCommand" + Arrays.toString(Backend.getCommands()));
        System.out.println("get Descriptions " + Arrays.toString(Backend.getDescriptions()));

        System.out.println("\n2nd user input");
        Backend.pressedButton(1);
        System.out.println("getCommand" + Arrays.toString(Backend.getCommands()));
        System.out.println("get Descriptions " + Arrays.toString(Backend.getDescriptions()));

        //simulate mode change
        System.out.println("\nchange Mode");
        Backend.changeMode(Backend.getModes()[1]);
        System.out.println("getCommand" + Arrays.toString(Backend.getCommands()));
        System.out.println("get Descriptions " + Arrays.toString(Backend.getDescriptions()));
        //------------------------------------------------------------------------


        int x = 15;
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
