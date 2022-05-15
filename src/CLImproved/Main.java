package CLImproved;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Integer.MAX_VALUE;


public class Main extends Application {
    //test
    public static void main(String[] args) {
        //testingFunctions();
        launch(args);

    }

    @Override
    public void start(Stage stage) {
        JSONFileHandler.init("test.json");
        System.out.println(Arrays.toString(JSONFileHandler.getWords()));
        System.out.println(Arrays.toString(JSONFileHandler.getDescriptions()));

        //-----
        String[] modes        =        JSONFileHandler.getModes();
        String[] commands     =        JSONFileHandler.getWords();
        String[] descriptions = JSONFileHandler.getDescriptions();

        stage.setTitle("CLImproved");
        stage.setResizable(false);
        stage.setHeight(720);
        stage.setWidth(1280);

        //Main Layout
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 1280, 820);
        stage.setScene(scene);

        //TOPPER
        HBox hBox1 = new HBox();
        hBox1.setPrefHeight(100);
        hBox1.setPrefWidth(MAX_VALUE);
        hBox1.setAlignment(Pos.CENTER);
        Button topper_button = new Button("AMONG US");
        topper_button.setPrefWidth(MAX_VALUE);
        topper_button.setMaxHeight(MAX_VALUE);
        hBox1.getChildren().add(topper_button);

        //CENTER
        /*
        GridPane[] gridPane = new GridPane[modes.length];
        Label[] modeNames   = new Label[modes.length];
        for(int e = 0; e < modes.length; e++) {
            modeNames[e] = new Label(modes[e]);
            gridPane[e].setHgap(50);
            for (int i = 0; i < commands.length; i++) {
                Button button1 = new Button(commands[i]);
                button1.setPrefWidth(60);
                gridPane[e].add(button1, 0, i);
                gridPane[e].add(new Label(descriptions[i]), 1, i);
            }
            JSONFileHandler.changeMode();
            modes        =        JSONFileHandler.getModes();
            commands     =        JSONFileHandler.getWords();
            descriptions = JSONFileHandler.getDescriptions();
        }
        */

        //LOOP THROUGH ALL MODES AND LIST THEM WITH FORMAT modeNames[x] and then gridPane[x]

        //Adding to Layouts
        borderPane.setTop(hBox1);
        //borderPane.setCenter(gridPane[0]); //UNFINISHED
        stage.show();
    }
    static public void testingFunctions(){
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
    }
}
