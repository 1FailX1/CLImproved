package CLImproved;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.util.Arrays;

import static java.lang.Integer.MAX_VALUE;


public class Main extends Application {
    public static void main(String[] args) {
        //testingFunctions();
        launch(args);

    }

    @Override
    public void start(Stage stage) {

        JSONFileHandler.init("prototyp2.json");
        System.out.println(Arrays.toString(JSONFileHandler.getWords()));
        System.out.println(Arrays.toString(JSONFileHandler.getDescriptions()));

        //-----
        String[] modes = JSONFileHandler.getModes();
        final String[][] commands = {JSONFileHandler.getWords()}; //Using inner classes requires a final / effectively final one element array (like this one)
        final String[][] descriptions = {JSONFileHandler.getDescriptions()};

        stage.setTitle("CLImproved");
        stage.setResizable(false);
        stage.setHeight(720);
        stage.setWidth(1280);

        //Main Layout
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 1280, 820);
        stage.setScene(scene);

        //HEADER
        ImageView imageView1 = new ImageView();
        try {
            imageView1 = new ImageView(new Image(new FileInputStream("assets\\CLImproved_Logo.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        HBox hBox1 = new HBox();
        hBox1.setPrefHeight(100);
        hBox1.setPrefWidth(MAX_VALUE);
        hBox1.setAlignment(Pos.CENTER);
        hBox1.getChildren().add(imageView1);
        for (int i = 0; i < modes.length; i++) {
            Button modeButton = new Button(modes[i]);
            String[] finalModes = modes;
            int finalI = i;
            modeButton.setOnAction(actionEvent -> {
                JSONFileHandler.changeMode(finalI);
                ScrollPane scrollPane1 = new ScrollPane();
                VBox vBox1 = new VBox();
                GridPane gridPane = new GridPane();
                commands[0] = JSONFileHandler.getWords();
                descriptions[0] = JSONFileHandler.getDescriptions();
                gridPane.setHgap(50);
                for (int i1 = 0; i1 < commands[0].length; i1++) {
                    Button button1 = new Button("Add");
                    Label label1 = new Label(commands[0][i1]);
                    button1.setPrefWidth(40);
                    label1.setPrefWidth(60);
                    gridPane.add(button1, 0, i1);
                    gridPane.add(label1, 1, i1);
                    gridPane.add(new Label(descriptions[0][i1]), 2, i1);

                }
                vBox1.getChildren().add(gridPane);
                scrollPane1.setContent(vBox1);
                borderPane.setCenter(scrollPane1);
            });
            hBox1.getChildren().add(modeButton);
        }

        //CENTER
        ScrollPane scrollPane1 = new ScrollPane();
        VBox vBox1 = new VBox();
        GridPane gridPane = new GridPane();
        commands[0] = JSONFileHandler.getWords();
        descriptions[0] = JSONFileHandler.getDescriptions();
        gridPane.setHgap(50);
        for (int i = 0; i < commands[0].length; i++) {
            Button button1 = new Button("Add");
            Label label1 = new Label(commands[0][i]);
            button1.setPrefWidth(40);
            label1.setPrefWidth(60);
            gridPane.add(button1, 0, i);
            gridPane.add(label1, 1, i);
            gridPane.add(new Label(descriptions[0][i]), 2, i);

        }
        vBox1.getChildren().add(gridPane);

        scrollPane1.setContent(vBox1);

        //Adding to Layouts
        borderPane.setTop(hBox1);
        borderPane.setCenter(scrollPane1);
        stage.show();
    }
    static public void testingFunctions(){
        //JsonFileHandler-Test-----------------------------------------------------------
        //loads json file
        JSONFileHandler.init("prototyp2.json");
        JSONFileHandler.changeMode(1);
        System.out.println("getModes: " + Arrays.toString(JSONFileHandler.getModes()));//get all modes for header buttons
        System.out.println("getWords" + Arrays.toString(JSONFileHandler.getWords()));
        System.out.println("get Descriptions " + Arrays.toString(JSONFileHandler.getDescriptions()));

        //simulating user input
        JSONFileHandler.loadNextWords(2);
        JSONFileHandler.loadNextWords(0);
        System.out.println("getWords" + Arrays.toString(JSONFileHandler.getWords()));
        JSONFileHandler.loadNextWords(0);
        System.out.println("getWords" + Arrays.toString(JSONFileHandler.getWords()));
        JSONFileHandler.loadNextWords(0);
        JSONFileHandler.loadNextWords(0);
        JSONFileHandler.loadNextWords(0);
        /*
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
        System.out.println("get Descriptions " + Arrays.toString(JSONFileHandler.getDescriptions()));*/
        System.out.println("----------------------------------------------------------------------------------------");
    }
}
