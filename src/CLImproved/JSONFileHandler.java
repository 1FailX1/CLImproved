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
    private static JSONArray multipleCommands;
    private static String currentMode = "";
    private static int lengthOfCommands = 0;
    private static int currentCommand = 0;
    private static boolean hasMultiple = false;

    /**
     * @param file JSONfile that should be interpreted
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
     * @return returns array with all available modes
     */
    public static String[] getModes() {
        String[] modes = new String[fileContent.length()];
        for (int i = 0; i < fileContent.length(); i++) {
            modes[i] = fileContent.getJSONObject(i).getString("category");
        }
        return modes;
    }

    /**
     * @param mode changes the mode to mode
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
     * @return array with all currently available words
     */
    public static String[] getWords() {
        String[] commands = new String[nextCommands.length()];
        for (int i = 0; i < commands.length; i++) {
            commands[i] = nextCommands.getJSONObject(i).getString("word");

        }
        return commands;
    }

    /**
     * @return a string array with all descriptions of the current words in the correct order
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
     * <p>loads in next words which can be accessed by the getWords() method</p>
     */
    public static void loadNextWords(int indexOfPressedCommand) {
        if (lengthOfCommands != 0 && currentCommand < lengthOfCommands) {
            try {
                nextCommands = nextCommands.getJSONObject(indexOfPressedCommand).getJSONArray("words");
            } catch (Exception e) {
                currentCommand++;
                nextCommands = multipleCommands.getJSONArray(currentCommand);
                System.out.println(nextCommands);
            }
        } else {
            lengthOfCommands = 0;
            currentCommand = 0;
            try {
                changeMode(nextCommands.getJSONObject(indexOfPressedCommand).getString("jump"));
            } catch (Exception e) {
            }


            try {
                //tries to interpret "words" array as array with 2 dimensions
                multipleCommands = nextCommands.getJSONObject(indexOfPressedCommand).getJSONArray("words");
                multipleCommands.getJSONArray(0);//checks if words is 2 dimensional array,
                // if not exception is thorwn and code bellow is not executed

                nextCommands = multipleCommands.getJSONArray(currentCommand);
                lengthOfCommands = nextCommands.length();
                hasMultiple = true;

            } catch (Exception e) {

                try {
                    //if exception is thrown try to interpret "words" array as 1 dimensional array
                    nextCommands = nextCommands.getJSONObject(indexOfPressedCommand).getJSONArray("words");
                } catch (Exception f) {
                    //if non of the above interpretations worked there is
                    // no "words" array and the first commands are loaded
                    changeMode(currentMode);
                }
            }
        }
    }

    /**
     * <p>checks if a word is a parameter</p>
     *
     * @param indexOfPressedCommand index of the word which should be checked
     * @return the boolean value
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
