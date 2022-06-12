package CLImproved;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;

import static java.lang.Integer.MAX_VALUE;

/**
 * @author 1FailX1 (Felix Payer)
 * @version 0.6
 */

public class Main extends Application {
    String contentAtLastSave = "";
    String filePathAtLastSave = "";

    String[] loaded_commands = {"d"};
    String[] loaded_descriptions = {"d"};
    BorderPane scene_borderPane = new BorderPane();
    private VBox header_vBox_container;
    private HBox header_hBox_execmodes;
    private ScrollPane center_scrollPane;
    private GridPane center_gridPane;
    Image header_logo_image;
    ImageView header_logo;
    Image header_saveAsSymbol_image;
    ImageView header_saveAsSymbol;
    Image center_addButton_image;
    ImageView center_addButton;
    private TextArea right_textField;

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
        scene.getStylesheets().add("Main.css");
        stage.setScene(scene);

        //Importing graphics
        try {
            header_logo_image = new Image(new FileInputStream("assets\\CLImproved_Logo.png"));
            header_logo = new ImageView(header_logo_image);
            header_saveAsSymbol_image = new Image(new FileInputStream("assets\\save_icon.png"));
            header_saveAsSymbol = new ImageView(new Image(new FileInputStream("assets\\save_icon.png")));
            center_addButton_image = new Image(new FileInputStream("assets\\add_button.png"));
            center_addButton = new ImageView(new Image(new FileInputStream("assets\\add_button.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        header_saveAsSymbol.setFitWidth(15);
        header_saveAsSymbol.setFitHeight(15);

        //HEADER


        header_vBox_container = new VBox();     //Container for the entire header

        MenuBar header_menuBar = new MenuBar();
        Menu header_menu1 = new Menu("File");
        Menu header_menu2 = new Menu("Options");
        Menu header_menu3 = new Menu("View");
        Menu header_menu4 = new Menu("Help");

        MenuItem[] header_menu1Items = new MenuItem[2];
        header_menu1Items[0]= new MenuItem("Save as");
        header_menu1Items[1] = new MenuItem("Save");
        MenuItem header_menu4Item1 = new MenuItem("About");

        header_menu1Items[0].setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save");
            fileChooser.setInitialFileName("Script_File");

            //Set to user directory or go to default if cannot access
            String userDirectoryString = System.getProperty("user.home") + "/Desktop";
            File userDirectory = new File(userDirectoryString);
            if (!userDirectory.canRead()) {
                userDirectory = new File("c:/");
            }
            fileChooser.setInitialDirectory(userDirectory);

            //Opening a dialog box
            fileChooser.getExtensionFilters()
                    .add(new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt"));
            try {
                File selectedFile = fileChooser.showSaveDialog(stage);
                filePathAtLastSave = selectedFile.getAbsolutePath();
                BufferedWriter output = Files.newBufferedWriter(selectedFile.toPath(), StandardCharsets.UTF_8);
                contentAtLastSave = CommandWriter.content;
                output.write(CommandWriter.content);
                output.flush();
            } catch (Exception e) {
                System.out.println("Filesave aborted!");
            }
        });

        header_menu4Item1.setOnAction(event -> {
            Stage aboutStage = new Stage();
            aboutStage.setResizable(false);
            aboutStage.setWidth(500);
            aboutStage.setHeight(700);
            aboutStage.show();
        });
        header_menu1Items[0].setGraphic(header_saveAsSymbol);
        header_menu1.getItems().addAll(header_menu1Items);
        header_menu4.getItems().add(header_menu4Item1);
        header_menuBar.getMenus().addAll(header_menu1, header_menu2, header_menu3, header_menu4);

        header_hBox_execmodes = new HBox();
        header_hBox_execmodes.setPadding(new Insets(0, 450, 0, 0));
        header_hBox_execmodes.setSpacing(30);
        header_hBox_execmodes.setPrefHeight(50);
        header_hBox_execmodes.setPrefWidth(MAX_VALUE);
        header_hBox_execmodes.setAlignment(Pos.CENTER);
        //header_hBox_execmodes.getChildren().add(header_logo);

        header_vBox_container.getChildren().addAll(header_menuBar, header_hBox_execmodes);

        //Looping through all available modes to create a menu in the header
        for (int i = 0; i < modes.length; i++) {
            Button modeButton = new Button(modes[i]);
            modeButton.setFont(Font.font("System Regular", 15));
            modeButton.setFocusTraversable(false);
            int finalI_mode = i;
            modeButton.setOnAction(actionEvent -> {
                //Loading the new commands if the mode-changing button is pressed
                JSONFileHandler.changeMode(finalI_mode);
                center_scrollPane = new ScrollPane();
                center_gridPane.getChildren().clear();
                loaded_commands = JSONFileHandler.getWords();
                loaded_descriptions = JSONFileHandler.getDescriptions();
                center_gridPane.setHgap(50);

                //Printing out the commands in the center
                printCurrentCommands();
            });

            header_hBox_execmodes.getChildren().add(modeButton);
        }


        //CENTER
        center_scrollPane = new ScrollPane();
        center_scrollPane.setPadding(new Insets(5));
        center_scrollPane.setFocusTraversable(false);
        center_gridPane = new GridPane();
        loaded_commands = JSONFileHandler.getWords();
        loaded_descriptions = JSONFileHandler.getDescriptions();
        center_gridPane.setHgap(50);
        printCurrentCommands();
        center_scrollPane.setFocusTraversable(false);
        center_gridPane.setFocusTraversable(false);
        center_scrollPane.setContent(center_gridPane);

        //RIGHT
        right_textField = new TextArea(CommandWriter.content);
        right_textField.setFocusTraversable(false);
        right_textField.setPrefSize(400, 1000);
        right_textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                CommandWriter.content = right_textField.getText();
            }
        });

        //Adding to Layouts
        scene_borderPane.setTop(header_vBox_container);
        scene_borderPane.setCenter(center_scrollPane);
        scene_borderPane.setRight(right_textField);
        stage.getIcons().add(header_logo.getImage());
        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });
    }

    public void printCurrentCommands() {
        center_gridPane.setHgap(50);
        center_gridPane.setVgap(5);

        // System.out.println(Arrays.toString(loaded_commands));
        for (int i1 = 0; i1 < loaded_commands.length; i1++) {
            int finalI_commands = i1;
            Button button1 = new Button();
            button1.setBackground(null);
            button1.setGraphic(new ImageView(center_addButton_image));
            button1.setFocusTraversable(false);
            button1.setOnAction(actionEvent2 -> {
                //    System.out.println("Button egdrückt")
                if (JSONFileHandler.isParam(finalI_commands)) {
                    String parameter = PopUp.readLine(JSONFileHandler.getWords()[finalI_commands]);
                    System.out.println(parameter);
                    CommandWriter.writeWord(parameter);
                    System.out.println("Is parameter");
                }

                center_gridPane.getChildren().clear();
                JSONFileHandler.loadNextWords(finalI_commands);
                loaded_commands = JSONFileHandler.getWords();
                loaded_descriptions = JSONFileHandler.getDescriptions();
                printCurrentCommands();
                center_scrollPane.setContent(center_gridPane);
                scene_borderPane.setCenter(center_scrollPane);
                //Refreshing the TextField
                right_textField.setText(CommandWriter.content);
            });

            Label label1 = new Label(loaded_commands[i1]);
            button1.setPrefWidth(40);
            label1.setPrefWidth(120);
            center_gridPane.add(button1, 0, i1);
            center_gridPane.add(label1, 1, i1);
            center_gridPane.add(new Label(loaded_descriptions[i1]), 2, i1);

        }

    }

}
