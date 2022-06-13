package CLImproved;

public class CommandWriter {
    static String content = "";
    static String tabs = "";
    static private boolean lineJumpMade = true;

    /**
     * @param s writes a String to the last line without making a break
     */
    public static void writeWord(String s) {
        if (lineJumpMade) {
            lineJumpMade = false;
            content += tabs;
        } else {
            content += " ";
        }
        content += s;
    }

    /**
     * makes a break
     */
    public static void makeBreak() {
        content += "\n";
        lineJumpMade = true;
    }

    /**
     * adds a tab which then are added after evey break
     */
    public static void addTab() {
        tabs += "\t";
    }

    /**
     * removes a tab which then are added after evey break
     */
    public static void removeTab() {
        tabs = tabs.substring(1);
    }

    /**
     * makes a break
     */
    public static String getString() {
        return content;
    }
}
