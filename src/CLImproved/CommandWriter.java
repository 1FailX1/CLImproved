package CLImproved;

public class CommandWriter {
    static String content = "";
    static String tabs = "";
    static private boolean lineJumpMade = true;


    public static void writeLine(String s) {
        content += tabs + s + "\n";
    }

    public static void writeWord(String s) {
        if (lineJumpMade) {
            lineJumpMade = false;
        } else {
            content += " ";
        }
        content += s;
    }

    public static void makeBreak() {
        content += "\n";
        lineJumpMade = true;
    }

    public static void addTab() {
        tabs += "\t";
    }

    public static void removeTab() {
        tabs = tabs.substring(1);
    }

    public static String getString() {
        return content;
    }
}
