import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.List;


public class Generator {
    private static int tableWidth;
    private static int tableHeight;
    private static String numberColumnTitle;
    private static int numberColumnWidth;
    private static String dateColumnTitle;
    private static int dateColumnWidth;
    private static String initialsColumnTitle;
    private static int initialsColumnWidth;
    private static String settingsPath = "settings.xml";
    private static String dataFilePath = "source-data.tsv";
    private final static String CELL_SEPARATOR = "|";
    private final static String LINE_SEPARATOR = "-";
    private final static String PAGE_SEPARATOR = "~";
    private final static String SPACE = " ";
    private static List<String[]> allRows;

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(settingsPath));
        document.getDocumentElement().normalize();
        tableWidth = Integer.parseInt(document.getElementsByTagName("width").item(0).getTextContent());
        numberColumnWidth = Integer.parseInt(document.getElementsByTagName("width").item(1).getTextContent());
        dateColumnWidth = Integer.parseInt(document.getElementsByTagName("width").item(2).getTextContent());
        initialsColumnWidth = Integer.parseInt(document.getElementsByTagName("width").item(3).getTextContent());
        tableHeight = Integer.parseInt(document.getElementsByTagName("height").item(0).getTextContent());
        numberColumnTitle = document.getElementsByTagName("title").item(0).getTextContent();
        dateColumnTitle = document.getElementsByTagName("title").item(1).getTextContent();
        initialsColumnTitle = document.getElementsByTagName("title").item(2).getTextContent();

        TsvParserSettings settings = new TsvParserSettings();
        settings.getFormat().setLineSeparator("\n");
        TsvParser parser = new TsvParser(settings);
        allRows = parser.parseAll(new File(dataFilePath), "UTF-16");

        Generator table = new Generator();

        File file = new File("result.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            PrintWriter out = new PrintWriter("result.txt");
            try {
                out.print(table.printFirst());
                out.println();
                out.print(table.printLineSeparator());
                out.println();
                for (int i = 0; i < allRows.size(); i++) {
                    out.println(table.printLine(i));
                }
            } finally {
                out.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    String printFirst() {
        String firstLine;
        firstLine = CELL_SEPARATOR + SPACE + numberColumnTitle;
        for (int i = 0; i < (numberColumnWidth - numberColumnTitle.length() + 1); i++) {
            firstLine += SPACE;
        }
        firstLine += CELL_SEPARATOR + SPACE + dateColumnTitle;
        for (int i = 0; i < (dateColumnWidth - dateColumnTitle.length() + 1); i++) {
            firstLine += SPACE;
        }
        firstLine += CELL_SEPARATOR + SPACE + initialsColumnTitle;
        for (int i = 0; i < (initialsColumnWidth - initialsColumnTitle.length() + 1); i++) {
            firstLine += SPACE;
        }
        firstLine += CELL_SEPARATOR;
        return firstLine;
    }

    String printLineSeparator() {
        String lineSeparators = "";
        for (int i = 0; i < tableWidth; i++)
            lineSeparators += LINE_SEPARATOR;
        return lineSeparators;
    }

    String printLine(int i) {
        String number = allRows.get(i)[0];
        String date = allRows.get(i)[1];
        String initials = allRows.get(i)[2];
        String dateRest = "";
        String initialsRest = "";
        String line = CELL_SEPARATOR + SPACE + number;
        for (int j = 0; j <= (numberColumnWidth - number.length()); j++) {
            line += SPACE;
        }
        if (date.length() <= dateColumnWidth) {
            line += CELL_SEPARATOR + SPACE + date;
            for (int j = 0; j <= dateColumnWidth - date.length(); j++) {
                line += SPACE;
            }
        } else {
            line += CELL_SEPARATOR + SPACE + date.substring(0, date.lastIndexOf("/") + 1);
            dateRest = date.substring(date.lastIndexOf("/") + 1);
            for (int j = 0; j <= dateColumnWidth - date.substring(0, date.lastIndexOf("/") + 1).length(); j++) {
                line += SPACE;
            }
        }
        if (initials.length() <= initialsColumnWidth) {
            line += CELL_SEPARATOR + SPACE + initials;
            for (int j = 0; j <= initialsColumnWidth - initials.length(); j++) {
                line += SPACE;
            }
            line += CELL_SEPARATOR;
        } else {
            line += CELL_SEPARATOR + SPACE + initials.substring(0, initialsColumnWidth) + SPACE + CELL_SEPARATOR;
            initialsRest = initials.substring(initialsColumnWidth, initials.length());
        }
        //Date > dateWidth, initials < initialsWidth
        if (dateRest.length() > 0 & initialsRest.length() <= 0) {
            line += "\n" + CELL_SEPARATOR + SPACE;
            for (int j = 0; j < numberColumnWidth + 1; j++) {
                line += SPACE;
            }
            line += CELL_SEPARATOR + SPACE + dateRest;
            for (int j = 0; j < dateColumnWidth - dateRest.length() + 1; j++) {
                line += SPACE;
            }
            line += CELL_SEPARATOR + SPACE;
            for (int j = 0; j< initialsColumnWidth+1; j++){
                line += SPACE;
            }
            line+= CELL_SEPARATOR;
        }
        //Date < dateWidth, initials > initialsWidth
        if (dateRest.length()<=0&initialsRest.length() > 0){
            line += "\n" + CELL_SEPARATOR + SPACE;
            for (int j = 0; j < numberColumnWidth + 1; j++) {
                line += SPACE;
            }
            for (int j = 0; j < dateColumnWidth + 1; j++) {
                line += SPACE;
            }

        }
        return line;
    }

}
