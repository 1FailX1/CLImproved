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
 * @version 0.3
 */

public class Main extends Application {
    String[][] commands = {{"d"}, {"d"}};
    String[][] descriptions = {{"d"}, {"d"}};
    ScrollPane scrollPane1 = new ScrollPane();
    BorderPane borderPane = new BorderPane();
    VBox vBox1 = new VBox();
    GridPane gridPane = new GridPane();

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
        commands = new String[][]{JSONFileHandler.getWords()}; //Using inner classes requires a final / effectively final one element array (like this one)
        descriptions = new String[][]{JSONFileHandler.getDescriptions()};

        stage.setTitle("CLImproved");
        stage.setResizable(false);
        stage.setHeight(720);
        stage.setWidth(1280);

        //Main Layout
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

        //Looping through all available modes to create a menu in the header
        for (int i = 0; i < modes.length; i++) {
            Button modeButton = new Button(modes[i]);
            int finalI_mode = i;
            modeButton.setOnAction(actionEvent -> {
                JSONFileHandler.changeMode(finalI_mode);
                scrollPane1 = new ScrollPane();
                final VBox[] vBox1 = {new VBox()};
                final GridPane[] gridPane = {new GridPane()};
                commands[0] = JSONFileHandler.getWords();
                descriptions[0] = JSONFileHandler.getDescriptions();
                gridPane[0].setHgap(50);


                for (int i1 = 0; i1 < commands[0].length; i1++) {
                    int finalI_commands = i1;
                    Button button1 = new Button("Add");
                    /*button1.setOnAction(actionEvent2 -> {
                        System.out.println("Button egdrückt");

                        if(JSONFileHandler.isParam(finalI)){
                            System.out.println("Is parameter");
                        }

                        scrollPane1 = new ScrollPane();
                        vBox1[0] = new VBox();
                        gridPane[0] = new GridPane();
                        JSONFileHandler.loadNextWords(finalI_commands);
                        commands[0] = JSONFileHandler.getWords();
                        descriptions[0] = JSONFileHandler.getDescriptions();
                        printCurrentCommands();
                        scrollPane1.setContent(gridPane[0]);
                        borderPane.setCenter(scrollPane1);

                    });
            */
                    Label label1 = new Label(commands[0][i1]);
                    button1.setPrefWidth(40);
                    label1.setPrefWidth(120);
                    gridPane[0].add(button1, 0, i1);
                    gridPane[0].add(label1, 1, i1);
                    gridPane[0].add(new Label(descriptions[0][i1]), 2, i1);

                }
                vBox1[0].getChildren().add(gridPane[0]);
                scrollPane1.setContent(vBox1[0]);
                borderPane.setCenter(scrollPane1);
            });


            hBox1.getChildren().add(modeButton);
        }

        //CENTER
        ScrollPane scrollPane1 = new ScrollPane();
        GridPane gridPane = new GridPane();
        commands[0] = JSONFileHandler.getWords();
        descriptions[0] = JSONFileHandler.getDescriptions();
        gridPane.setHgap(50);
        for (int i1 = 0; i1 < commands[0].length; i1++) {
            int finalI_commands = i1;
            Button button1 = new Button("Add");
            /*button1.setOnAction(actionEvent2 -> {
                System.out.println("Button egdrückt");
                        /*
                        if(JSONFileHandler.isParam(finalI)){
                            System.out.println("Is parameter");
                        }

                JSONFileHandler.loadNextWords(finalI_commands);
                System.out.println(finalI_commands);
                commands[0] = JSONFileHandler.getWords();
                descriptions[0] = JSONFileHandler.getDescriptions();
                printCurrentCommands();
                scrollPane1.setContent(gridPane);
                borderPane.setCenter(scrollPane1);

            }); */
            Label label1 = new Label(commands[0][i1]);
            button1.setPrefWidth(40);
            label1.setPrefWidth(120);
            gridPane.add(button1, 0, i1);
            gridPane.add(label1, 1, i1);
            gridPane.add(new Label(descriptions[0][i1]), 2, i1);

        }

        scrollPane1.setContent(gridPane);

        //Adding to Layouts
        borderPane.setTop(hBox1);
        borderPane.setCenter(scrollPane1);
        stage.getIcons().add(imageView1.getImage());
        stage.show();
    }

    //Currently not used
    public void printCurrentCommands() {
        for (int i1 = 0; i1 < commands[0].length; i1++) {
            int finalI_commands = i1;
            Button button1 = new Button("Add");
            button1.setOnAction(actionEvent2 -> {
                System.out.println("Button egdrückt");
                        /*
                        if(JSONFileHandler.isParam(finalI)){
                            System.out.println("Is parameter");
                        }
                        */
                scrollPane1 = new ScrollPane();
                vBox1 = new VBox();
                gridPane = new GridPane();
                JSONFileHandler.loadNextWords(finalI_commands);
                commands[0] = JSONFileHandler.getWords();
                descriptions[0] = JSONFileHandler.getDescriptions();
                printCurrentCommands();
                scrollPane1.setContent(gridPane);
                borderPane.setCenter(scrollPane1);

            });
            Label label1 = new Label(commands[0][i1]);
            button1.setPrefWidth(40);
            label1.setPrefWidth(120);
            gridPane.add(button1, 0, i1);
            gridPane.add(label1, 1, i1);
            gridPane.add(new Label(descriptions[0][i1]), 2, i1);

        }
    }
}
