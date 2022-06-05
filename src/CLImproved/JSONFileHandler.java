package CLImproved;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Stack;

/**
 * @author Hunor Zakarias
 * @version 1.0
 */
public class JSONFileHandler {
    private static JSONArray fileContent;
    private static JSONArray nextCommands;

    private static Stack<JSONObject> currentMode = new Stack<>();
    private static String currentModeString = "";
    private static boolean isInSubMode = false;

    private static Stack<Integer> currentMultiCommand = new Stack<>();
    private static boolean hasMultiple = false;
    private static Stack<JSONArray> multiCommands = new Stack<>();

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
        if (!isInSubMode) {
            String[] modes = new String[fileContent.length()];
            for (int i = 0; i < fileContent.length(); i++) {
                modes[i] = fileContent.getJSONObject(i).getString("category");
            }
            return modes;
        }
        return new String[]{};
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
        if (!isInSubMode) {
            nextCommands = fileContent.getJSONObject(index).getJSONArray("words");
            currentModeString = getModes()[index];
            currentMode.clear();
            currentMode.push(fileContent.getJSONObject(index));
        }
    }

    /**
     * @return array with all currently available words
     */
    public static String[] getWords() {
        String[] commands = new String[nextCommands.length()];
        for (int i = 0; i < commands.length; i++) {
            try {
                commands[i] = nextCommands.getJSONObject(i).getString("word");
            } catch (Exception e) {
                commands[i] = "";
            }
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
                case "finish":
                    //force into catch as no further operations have to be made
                    throw new JSONException("");


                case "command":
                    CommandWriter.writeWord(nextCommands.getJSONObject(indexOfPressedCommand).getString("word"));
                    nextCommands = nextCommands.getJSONObject(indexOfPressedCommand).getJSONArray("words");
                    break;

                case "multiCommand":
                    CommandWriter.writeWord(nextCommands.getJSONObject(indexOfPressedCommand).getString("word"));
                    System.out.println(nextCommands.getJSONObject(indexOfPressedCommand).getString("word"));
                    hasMultiple = true;
                    currentMultiCommand.push(0);
                    multiCommands.push(nextCommands.getJSONObject(indexOfPressedCommand).getJSONArray("words"));
                    nextCommands = multiCommands.peek().getJSONArray(currentMultiCommand.peek());
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
            if (hasMultiple && currentMultiCommand.peek() < multiCommands.peek().length() - 1) {
                currentMultiCommand.push(currentMultiCommand.pop() + 1);
                nextCommands = multiCommands.peek().getJSONArray(currentMultiCommand.peek());
            } else {
                if (hasMultiple) {
                    multiCommands.pop();
                    currentMultiCommand.pop();
                }

                nextCommands = currentMode.peek().getJSONArray("words");
                if (multiCommands.empty()) {
                    hasMultiple = false;
                }
                CommandWriter.makeBreak();
            }

        }
        System.out.println(CommandWriter.content);

    }

    /**
     * <p>checks if a word is a parameter</p>
     *
     * @param indexOfPressedCommand index of the word which should be checked
     * @return the boolean value
     */
    public static boolean isParam(int indexOfPressedCommand) {
        return false;
    }
}
