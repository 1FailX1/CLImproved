package CLImproved;

import org.json.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Hunor Zakarias
 * @version 1.0
 */
public class JSONFileHandler {
    private static String jsonFile = "";
    private static JSONArray fileContent;
    private static JSONArray nextCommands;
    private static String currentMode = "";

    /**
     * <p>Methode lädt File ein und setzt intern wichtige Parameter</p>
     * @param file JSONFile welches interpretiert werden soll
     */
    public static void init(String file) {
        jsonFile = file;
        InputStream inputStream = null;
        try {
            inputStream = Files.newInputStream(Path.of(jsonFile));
        } catch (IOException e) {
            System.out.println("File konnte nicht gelesen weden");
        }

        JSONTokener tokener = new JSONTokener(inputStream);
        fileContent = new JSONArray(tokener);
        try {
            changeMode(fileContent.getJSONObject(0).getString("category"));
        } catch (Exception e) {
            throw new IllegalArgumentException("File might be empty or is faulty");
        }
    }

    /**
     * @return Liefert Array mit allen verfügbaren Modes
     */
    public static String[] getModes() {
        String[] modes = new String[fileContent.length()];
        for (int i = 0; i < fileContent.length(); i++) {
            modes[i] = fileContent.getJSONObject(i).getString("category");
        }
        return modes;
    }

    /**
     * <p>Ändert den Mode und alle intern beeinflusten Parameter</p>
     *
     * @param mode Mode zu dem geändert werden soll
     */
    public static void changeMode(String mode) {
        for (int i = 0; i < fileContent.length(); i++) {
            if (fileContent.getJSONObject(i).get("category").equals(mode)) {
                nextCommands = fileContent.getJSONObject(i).getJSONArray("words");
                currentMode = mode;
                break;
            }
        }
    }

    public static void changeMode(int index) {
        nextCommands = fileContent.getJSONObject(index).getJSONArray("words");
        currentMode = getModes()[index];

    }

    /**
     * <p>Ändert den Mode und alle intern beeinflusten Parameter</p>
     *
     * @return Liefert String-Array mit allen Wörtern
     */
    public static String[] getWords() {
        String[] commands = new String[nextCommands.length()];
        for (int i = 0; i < commands.length; i++) {
            commands[i] = nextCommands.getJSONObject(i).getString("word");

        }
        return commands;
    }

    /**
     * <p>Ändert den Mode und alle intern beeinflusten Parameter</p>
     *
     * @return Liefert String-Array mit allen Beschreibungen in der richtigen Wörtern zu den Wärtern
     */
    public static String[] getDescriptions() {
        String[] descriptions = new String[nextCommands.length()];
        for (int i = 0; i < descriptions.length; i++) {
            try {
                descriptions[i] = nextCommands.getJSONObject(i).getString("description");
            } catch (Exception e) {
                descriptions[i] = "No Description";
            }
        }
        return descriptions;
    }

    /**
     * <p>Teilt der Klasse mit das ein Input getätigt wurde<br>
     * Es werden interne Variablen geändert und die folgenden Unterbefehle geladen die
     * mit dem getWords() Befehl bekommen werden können </p>
     *
     * @return Liefert String-Array mit allen Beschreibungen in der richtigen Wörtern zu den Wärtern
     */
    public static void pressedButton(int indexOfPressedCommand) {
        try {
            changeMode(nextCommands.getJSONObject(indexOfPressedCommand).getString("jump"));
        } catch (Exception e) {
        }

        try {
            nextCommands = nextCommands.getJSONObject(indexOfPressedCommand).getJSONArray("words");
        } catch (Exception e) {
            changeMode(currentMode);
            System.out.println("Mögliche Ursachen für Error:\n" +
                    "keine weiteren Befehle vorhanden: Text wird geschrieben und es werden die ersten befehle des modes geladen\n" +
                    "Möglicherweise wird Nutzereingabe erwartet (wenn parameter)\n" +
                    "fehlerhfate indexeingabe");
            //Command is written into the file

        }
    }

    /**
     * <p>Überprüft ob ein gegebenes Wort ein Parameter ist</p>
     *
     * @param indexOfPressedCommand index des Wortes, das überprüft werden
     *                              soll
     * @return Wahrheitswert
     */
    public static boolean isParam(int indexOfPressedCommand) {
        try {
            if (nextCommands.getJSONObject(indexOfPressedCommand).getJSONArray("words")
                    .getJSONObject(indexOfPressedCommand).getString("type").equals("param")) {
                return true;
            }
        } catch (Exception e) {

        }
        return false;
    }
}
