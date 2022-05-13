package CLImproved;

import org.json.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Backend {
    static String jsonFile = "test.json";
    private static JSONArray fileContent;

    public static void init() {
        String resourceName = "input.json";
        InputStream inputStream = null;
        try {
            inputStream = Files.newInputStream(Path.of(jsonFile));
        } catch (IOException e) {
            System.out.println("File konnte nicht gelesen weden");
        }
        if (inputStream == null) {
            throw new IllegalArgumentException("Cannot find resource file " + resourceName);
        }

        JSONTokener tokener = new JSONTokener(inputStream);
        fileContent = new JSONArray(tokener);
    }

    public static String[] getModes() {
        String[] modes = new String[fileContent.length()];
        for (int i = 0; i < fileContent.length(); i++) {
            modes[i] = fileContent.getJSONObject(i).getString("category");
        }
        return modes;
    }

    public static void changeMode(String mode) {
        JSONArray JSONcommands = null;
        for (int i = 0; i < fileContent.length(); i++) {
            if (fileContent.getJSONObject(i).get("category").equals(mode)) {
                JSONcommands = fileContent.getJSONObject(i).getJSONArray("commands");
                break;
            }
        }

        String[] commands = new String[JSONcommands.length()];
        for (int i = 0; i < commands.length; i++) {
            commands[i] = JSONcommands.getJSONObject(i).getString("command");
        }
    }
}
