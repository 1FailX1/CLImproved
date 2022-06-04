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

/**
 * @author 1FailX1 (Felix Payer)
 * @version 0.4.1
 */

public class Main extends Application {
    String[] loaded_commands = {"d"};
    String[] loaded_descriptions = {"d"};
    ScrollPane scrollPane1 = new ScrollPane();
    BorderPane scene_borderPane = new BorderPane();
    private HBox header_hBox_execmodes;
    private ScrollPane center_scrollPane;
    private GridPane center_gridPane;
    private VBox header_vBox;

    /**
     * @param args Main-method for launching the window
     */
    public static void main(String[] args) {
        //testingFunctions();
        launch(args);

    }

    static public void testingFunctions() {
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

    /**
     * @param stage Opens the GUI-containing window
     */
    @Override
    public void start(Stage stage) {

        JSONFileHandler.init("prototyp2.json");
        //System.out.println(Arrays.toString(JSONFileHandler.getWords()));
        //System.out.println(Arrays.toString(JSONFileHandler.getDescriptions()));

        //-----
        String[] modes = JSONFileHandler.getModes();
        loaded_commands = JSONFileHandler.getWords();
        loaded_descriptions = JSONFileHandler.getDescriptions();

        stage.setTitle("CLImproved");
        stage.setResizable(false);
        stage.setHeight(720);
        stage.setWidth(1280);

        //Main Layout
        Scene scene = new Scene(scene_borderPane, 1280, 820);
        stage.setScene(scene);

        //HEADER
        ImageView header_logo = new ImageView();
        try {
            header_logo = new ImageView(new Image(new FileInputStream("assets\\CLImproved_Logo.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        header_hBox_execmodes = new HBox();
        header_hBox_execmodes.setPrefHeight(100);
        header_hBox_execmodes.setPrefWidth(MAX_VALUE);
        header_hBox_execmodes.setAlignment(Pos.CENTER);
        header_hBox_execmodes.getChildren().add(header_logo);

        //Looping through all available modes to create a menu in the header
        for (int i = 0; i < modes.length; i++) {
            Button modeButton = new Button(modes[i]);
            modeButton.setFocusTraversable(false);
            int finalI_mode = i;
            modeButton.setOnAction(actionEvent -> {
                //Loading the new commands if the mode-changing button is pressed
                JSONFileHandler.changeMode(finalI_mode);
                center_scrollPane = new ScrollPane();
                header_vBox = new VBox();
                center_gridPane.getChildren().clear();
                loaded_commands = JSONFileHandler.getWords();
                loaded_descriptions = JSONFileHandler.getDescriptions();
                center_gridPane.setHgap(50);

                //Printing out the commands in the center
                printCurrentCommands();
                //header_vBox.getChildren().add(center_gridPane);
                //scrollPane1.setContent(header_vBox);
                //scene_borderPane.setCenter(scrollPane1);

            });

            header_hBox_execmodes.getChildren().add(modeButton);
        }


        //CENTER
        center_scrollPane = new ScrollPane();
        center_gridPane = new GridPane();
        loaded_commands = JSONFileHandler.getWords();
        loaded_descriptions = JSONFileHandler.getDescriptions();
        center_gridPane.setHgap(50);
        printCurrentCommands();

        center_scrollPane.setContent(center_gridPane);

        //Adding to Layouts
        scene_borderPane.setTop(header_hBox_execmodes);
        scene_borderPane.setCenter(center_scrollPane);
        stage.getIcons().add(header_logo.getImage());
        stage.show();
    }

    public void printCurrentCommands() {
       // System.out.println(Arrays.toString(loaded_commands));
        for (int i1 = 0; i1 < loaded_commands.length; i1++) {
            int finalI_commands = i1;
            Button button1 = new Button("Add");
            button1.setFocusTraversable(false);
            button1.setOnAction(actionEvent2 -> {
            //    System.out.println("Button egdrückt");
                        /*
                        if(JSONFileHandler.isParam(finalI)){
                            System.out.println("Is parameter");
                        }
                        */
                scrollPane1 = new ScrollPane();
                center_gridPane = new GridPane();
                JSONFileHandler.loadNextWords(finalI_commands);
                loaded_commands = JSONFileHandler.getWords();
                loaded_descriptions = JSONFileHandler.getDescriptions();
                printCurrentCommands();
                scrollPane1.setContent(center_gridPane);
                scene_borderPane.setCenter(scrollPane1);

            });
            center_gridPane.setHgap(50);
            Label label1 = new Label(loaded_commands[i1]);
            button1.setPrefWidth(40);
            label1.setPrefWidth(120);
            center_gridPane.add(button1, 0, i1);
            center_gridPane.add(label1, 1, i1);
            center_gridPane.add(new Label(loaded_descriptions[i1]), 2, i1);

        }
    }
}
