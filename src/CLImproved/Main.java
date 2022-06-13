package CLImproved;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.lang.Integer.MAX_VALUE;

/**
 * @author 1FailX1 (Felix Payer)
 * @version 0.8
 */

public class Main extends Application {
    String contentAtLastSave = "";
    String filePathAtLastSave = "";

    String[] modes;
    String[] loaded_commands = {"d"};
    String[] loaded_descriptions = {"d"};
    BorderPane scene_borderPane = new BorderPane();
    Image header_saveSymbol_image;
    private HBox header_hBox_execmodes;
    private ScrollPane center_scrollPane;
    private GridPane center_gridPane;
    Image header_logo_image;
    ImageView header_logo;
    Image header_saveAsSymbol_image;
    ImageView header_saveAsSymbol;
    ImageView header_saveSymbol;
    Image header_appearanceSymbol_image;
    ImageView header_appearanceSymbol;
    private VBox header_vBox_container = new VBox();
    Button[] header_modeButtons;
    Image center_addButton_image;
    ImageView center_addButton;
    private TextArea right_textArea;

    /**
     * @param args Main-method for launching the window
     */
    public static void main(String[] args) {
        //testingFunctions();
        launch(args);

    }

    /**
     * @param stage Opens the GUI-containing window
     */
    @Override
    public void start(Stage stage) {
        JSONFileHandler.init("prototyp2.json");
        //-----
        modes = JSONFileHandler.getModes();
        loaded_commands = JSONFileHandler.getWords();
        loaded_descriptions = JSONFileHandler.getDescriptions();

        header_modeButtons = new Button[modes.length];

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
            header_logo_image = new Image(new FileInputStream("assets\\CLImproved_newLogo.png"));
            header_logo = new ImageView(header_logo_image);
            header_saveAsSymbol_image = new Image(new FileInputStream("assets\\saveAs_icon.png"));
            header_saveAsSymbol = new ImageView(new Image(new FileInputStream("assets\\saveAs_icon.png")));
            header_saveSymbol_image = new Image(new FileInputStream("assets\\save_icon.png"));
            header_saveSymbol = new ImageView(new Image(new FileInputStream("assets\\save_icon.png")));
            header_appearanceSymbol_image = new Image(new FileInputStream("assets\\appearance_icon.png"));
            header_appearanceSymbol = new ImageView(new Image(new FileInputStream("assets\\appearance_icon.png")));
            center_addButton_image = new Image(new FileInputStream("assets\\add_button.png"));
            center_addButton = new ImageView(new Image(new FileInputStream("assets\\add_button.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        header_saveAsSymbol.setFitWidth(15);
        header_saveAsSymbol.setFitHeight(15);
        header_saveSymbol.setFitWidth(15);
        header_saveSymbol.setFitHeight(15);
        header_appearanceSymbol.setFitWidth(17);
        header_appearanceSymbol.setFitHeight(15);

        //HEADER
        MenuBar header_menuBar = new MenuBar();
        Menu header_menu1 = new Menu("File");
        Menu header_menu2 = new Menu("Options");
        Menu header_menu3 = new Menu("View");
        Menu header_menu4 = new Menu("Help");

        MenuItem[] header_menu1Items = new MenuItem[2];
        header_menu1Items[0] = new MenuItem("Save as");
        header_menu1Items[1] = new MenuItem("Save");

        MenuItem[] header_menu2Items = new MenuItem[1];
        header_menu2Items[0] = new MenuItem("Appearance");

        MenuItem header_menu4Item1 = new MenuItem("About");
        //Functionality for all items below header_menu1
        header_menu1Items[0].setOnAction(event -> {
            saveAs(stage);
        });
        header_menu1Items[1].setOnAction(event -> {
            System.out.println(contentAtLastSave);
            if (filePathAtLastSave != "") {
                try {
                    BufferedWriter output = Files.newBufferedWriter(Paths.get(filePathAtLastSave), StandardCharsets.UTF_8);
                    contentAtLastSave = CommandWriter.content;
                    output.write(CommandWriter.content);
                    output.flush();
                } catch (Exception e) {
                    System.out.println("Filesave aborted!");
                }
            } else {
                saveAs(stage);
            }
        });

        header_menu4Item1.setOnAction(event -> {
            Stage aboutStage = new Stage();
            aboutStage.setResizable(false);
            aboutStage.setWidth(500);
            aboutStage.setHeight(700);
            aboutStage.show();
        });

        header_menu2Items[0].setOnAction(actionEvent -> {
            Label secondLabel = new Label("Appearance presets:");
            final ToggleGroup appearance_toggleGroup = new ToggleGroup();
            RadioButton rb1 = new RadioButton("Regular");
            rb1.setToggleGroup(appearance_toggleGroup);
            rb1.setSelected(true);

            RadioButton rb2 = new RadioButton("Dark Mode");
            rb2.setToggleGroup(appearance_toggleGroup);

            RadioButton rb3 = new RadioButton("Please don't");
            rb3.setToggleGroup(appearance_toggleGroup);

            appearance_toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                @Override
                public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                    if (rb2.isSelected()) {
                        for (int i = 0; i < header_modeButtons.length; i++) {
                            header_modeButtons[i].setId("darkMode_header_modeButton");
                        }
                        header_menuBar.setStyle("-fx-background-color: #555555;");
                        header_menu1.setId("darkMode_menu");
                        header_menu2.setId("darkMode_menu");
                        header_menu3.setId("darkMode_menu");
                        header_menu4.setId("darkMode_menu");

                        header_hBox_execmodes.setStyle("-fx-background-color: #3C3F41;" +
                                "-fx-border-width: 2px;" +
                                "-fx-border-color: #515151 #515151 transparent #515151;");

                        center_scrollPane.setStyle("-fx-border-color: #3C3F41;" +
                                "-fx-background: #3C3F41;" +
                                "-fx-text-fill: #BBBBBB;" +
                                "-fx-border-width: 2px;" +
                                "-fx-border-color: #515151;");

                        right_textArea.lookup(".content").setStyle("-fx-background-color: #2b2b2b;" +
                                "-fx-background-radius: 0;");
                        right_textArea.setStyle("-fx-border-color: #2b2b2b;" +
                                "-fx-background-color: #2b2b2b;" +
                                "-fx-text-fill: #A9B7C6;" +
                                "-fx-border-color: #515151;" +
                                "-fx-border-width: 2px;" +
                                "-fx-border-color: #515151 #515151 #515151 transparent;");

                    }
                }
            });

            VBox appearance_vBox = new VBox();
            appearance_vBox.setPadding(new Insets(15));
            appearance_vBox.setSpacing(30);
            appearance_vBox.getChildren().addAll(secondLabel, rb1, rb2, rb3);

            Scene secondScene = new Scene(appearance_vBox, 235, 180);

            // New window (Stage)
            Stage newWindow = new Stage();
            newWindow.getIcons().add(header_appearanceSymbol_image);
            newWindow.setTitle("Appearance");
            newWindow.setScene(secondScene);

            // Set position of second window, related to primary window.
            newWindow.setX(stage.getX() + 400);
            newWindow.setY(stage.getY() + 300);

            newWindow.show();
        });

        header_menu1Items[0].setGraphic(header_saveAsSymbol);
        header_menu1Items[1].setGraphic(header_saveSymbol);
        header_menu1.getItems().addAll(header_menu1Items);

        header_menu2Items[0].setGraphic(header_appearanceSymbol);
        header_menu2.getItems().addAll(header_menu2Items);

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
        printExecModes();


        //CENTER
        center_scrollPane = new ScrollPane();
        center_scrollPane.setPadding(new Insets(5));
        center_scrollPane.setFocusTraversable(false);
        center_gridPane = new GridPane();
        loaded_commands = JSONFileHandler.getWords();
        loaded_descriptions = JSONFileHandler.getDescriptions();
        center_gridPane.setHgap(50);
        printCurrentCommands();
        center_scrollPane.setContent(center_gridPane);

        //RIGHT
        right_textArea = new TextArea(CommandWriter.content);
        right_textArea.setFocusTraversable(false);
        right_textArea.setPrefSize(400, 1000);
        right_textArea.textProperty().addListener((observableValue, s, t1) ->
                CommandWriter.content = right_textArea.getText());

        //Adding to Layouts
        scene_borderPane.setTop(header_vBox_container);
        scene_borderPane.setCenter(center_scrollPane);
        scene_borderPane.setRight(right_textArea);
        stage.getIcons().add(header_logo.getImage());
        stage.show();
    }

    private void printExecModes() {

        for (int i = 0; i < modes.length; i++) {
            header_modeButtons[i] = new Button(modes[i]);
            header_modeButtons[i].setFont(Font.font("System Regular", 15));
            header_modeButtons[i].setFocusTraversable(false);
            int finalI_mode = i;
            header_modeButtons[i].setOnAction(actionEvent -> {
                //Loading the new commands if the mode-changing button is pressed
                JSONFileHandler.changeMode(finalI_mode);

                center_gridPane.getChildren().clear();
                loaded_commands = JSONFileHandler.getWords();
                loaded_descriptions = JSONFileHandler.getDescriptions();
                center_gridPane.setHgap(50);

                //Printing out the commands in the center
                printCurrentCommands();
            });

            header_hBox_execmodes.getChildren().add(header_modeButtons[i]);
        }
    }

    private void printCurrentCommands() {
        center_gridPane.setHgap(50);
        center_gridPane.setVgap(5);

        // System.out.println(Arrays.toString(loaded_commands));
        for (int i1 = 0; i1 < loaded_commands.length; i1++) {
            int finalI_commands = i1;
            Button button1 = new Button();
            //button1.setBackground(null);
            button1.setId("button_commands");
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
                right_textArea.setText(CommandWriter.content);
                right_textArea.setScrollTop(Double.MAX_VALUE);
                right_textArea.positionCaret(CommandWriter.content.length());
            });

            Label label1 = new Label(loaded_commands[i1]);
            button1.setPrefWidth(40);
            label1.setPrefWidth(120);
            center_gridPane.add(button1, 0, i1);
            center_gridPane.add(label1, 1, i1);
            center_gridPane.add(new Label(loaded_descriptions[i1]), 2, i1);

        }

    }
    /*public void printCurrentCommands() {
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
                right_textField.setScrollTop(Double.MAX_VALUE);
                right_textField.positionCaret(CommandWriter.content.length());
            });

            Label label1 = new Label(loaded_commands[i1]);
            button1.setPrefWidth(40);
            label1.setPrefWidth(120);
            center_gridPane.add(button1, 0, i1);
            center_gridPane.add(label1, 1, i1);
            center_gridPane.add(new Label(loaded_descriptions[i1]), 2, i1);

        }

    }
    */

    private void saveAs(Stage stage) {
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
    }

}
