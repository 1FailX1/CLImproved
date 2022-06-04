package CLImproved;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Stack;

/**
 * @author Hunor Zakarias
 * @version 1.0
 */
public class JSONFileHandler {
    private static JSONArray fileContent;

    private static Stack<JSONObject> currentMode = new Stack<>();
    private static String currentModeString = "";
    private static boolean isInSubMode = false;

    private static int lengthOfCommands = 0;
    private static int currentMultiCommand = 0;
    private static boolean hasMultiple = false;

    private static JSONArray nextCommands;
    private static JSONArray multipleCommands;

    /**
     * @param file JSONfile that should be interpreted
     */
    public static void init(String file) {
        InputStream inputStream = null;
        try {
            inputStream = Files.newInputStream(Path.of(file));
        } catch (IOException e) {
            System.out.println("File konnte nicht gelesen weden");
        }

        JSONTokener tokener = new JSONTokener(inputStream);
        fileContent = new JSONArray(tokener);
        currentMode.push(fileContent.getJSONObject(0));
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
                currentModeString = mode;
                break;
            }
        }
    }

    public static void changeMode(int index) {
        nextCommands = fileContent.getJSONObject(index).getJSONArray("words");
        currentModeString = getModes()[index];
        currentMode.clear();
        currentMode.push(fileContent.getJSONObject(index));

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
     * <p>loads next words which can be accessed by the getWords() method</p>
     */
    public static void loadNextWords(int indexOfPressedCommand) {
        try {
            switch (nextCommands.getJSONObject(indexOfPressedCommand).getString("type")) {
                case "optCommand":
                case "command":
                    CommandWriter.writeWord(nextCommands.getJSONObject(indexOfPressedCommand).getString("word"));
                    nextCommands = nextCommands.getJSONObject(indexOfPressedCommand).getJSONArray("words");
                    break;

                case "multiCommand":
                    CommandWriter.writeWord(nextCommands.getJSONObject(indexOfPressedCommand).getString("word"));
                    System.out.println(nextCommands.getJSONObject(indexOfPressedCommand).getString("word"));
                    hasMultiple = true;
                    currentMultiCommand = 0;
                    multipleCommands = nextCommands.getJSONObject(indexOfPressedCommand).getJSONArray("words");
                    nextCommands = multipleCommands.getJSONArray(currentMultiCommand);
                    break;

                case "enterSubMode":
                    CommandWriter.writeWord(nextCommands.getJSONObject(indexOfPressedCommand).getString("word"));
                    CommandWriter.makeBreak();
                    CommandWriter.addTab();
                    currentMode.push(nextCommands.getJSONObject(indexOfPressedCommand).getJSONObject("submode"));
                    isInSubMode = true;
                    nextCommands = currentMode.peek().getJSONArray("words");


                    break;
                case "exitSubMode":
                    CommandWriter.writeWord(nextCommands.getJSONObject(indexOfPressedCommand).getString("word"));
                    CommandWriter.makeBreak();
                    CommandWriter.removeTab();
                    currentMode.pop();
                    nextCommands = currentMode.peek().getJSONArray("words");

                    if (currentMode.capacity() < 2) {
                        isInSubMode = false;
                    }
                    break;

                case "param":
                    System.out.println("Parameter is handeld by frontend");
                    nextCommands = nextCommands.getJSONObject(indexOfPressedCommand).getJSONArray("words");
                    break;
            }
        } catch (JSONException e) {
            System.out.println("no more next commands");
            if (hasMultiple && currentMultiCommand < multipleCommands.length() - 1) {
                currentMultiCommand++;
                nextCommands = multipleCommands.getJSONArray(currentMultiCommand);
            } else {
                nextCommands = currentMode.peek().getJSONArray("words");
                hasMultiple = false;
                CommandWriter.makeBreak();
            }

        }
        System.out.println(CommandWriter.content);
      /*  if (lengthOfCommands != 0 && currentCommand < lengthOfCommands) {
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
        }*/
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
