package CLImproved;

public class CommandWriter {
    String content = "";
    String tabs = "";

    CommandWriter() {

    }

    public void writeLine(String text) {
        content += tabs + text + System.lineSeparator();
    }

    public void addTab() {
        tabs += "\t";
    }

    public void removeTab() {
        tabs = tabs.substring(1);
    }

    public String getString(){
        return content;
    }
}
