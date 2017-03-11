import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

/**
 * Created by DSizov on 15.02.2017.
 */
public class Generator {
    public static int tableWidth;
    public static int tableHeight;
    public static String numberColumn;
    public static int numberColumnWidth;
    public static String dateColumn;
    public static int dateColumnWidth;
    public static String initialsColumn;
    public static int initialsColumnWidth;
    public static String settingsPath = "C:\\Users\\Elizaveta\\IdeaProjects\\Generator\\settings.xml";
    public static String dataFilePath = "C:\\Users\\Elizaveta\\IdeaProjects\\Generator\\source-data.tsv";
    public static String line = "|";
    public static String sSep = "-";
    public static String pSep = "~";
    public static String space = " ";

    public static void main(String[] args) throws  IOException, ParserConfigurationException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document =  builder.parse(new File(settingsPath));
        document.getDocumentElement().normalize();
        tableWidth = Integer.parseInt(document.getElementsByTagName("width").item(0).getTextContent());
        numberColumnWidth = Integer.parseInt(document.getElementsByTagName("width").item(1).getTextContent());
        dateColumnWidth = Integer.parseInt(document.getElementsByTagName("width").item(2).getTextContent());
        initialsColumnWidth = Integer.parseInt(document.getElementsByTagName("width").item(3).getTextContent());
        tableHeight = Integer.parseInt(document.getElementsByTagName("height").item(0).getTextContent());
        numberColumn = document.getElementsByTagName("title").item(0).getTextContent();
        dateColumn = document.getElementsByTagName("title").item(1).getTextContent();
        initialsColumn = document.getElementsByTagName("title").item(2).getTextContent();
        Generator start = new Generator();

        start.printFirst();
        start.printSep();
        try {
            BufferedReader bReader = new BufferedReader(new FileReader(dataFilePath));
            String str;

            while ((str = bReader.readLine()) != null) {
                String [] data = str.split("\t");
                System.out.println(data);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    void printFirst(){
        System.out.print(line+space+numberColumn);
        for (int i = 0; i < (numberColumnWidth-numberColumn.length()+1); i++){
            System.out.print(space);
        }
        System.out.print(line+space+ dateColumn);
        for (int i = 0; i < (dateColumnWidth- dateColumn.length()+1); i++){
            System.out.print(space);
        }
        System.out.print(line+space+initialsColumn);
        for (int i = 0; i < (initialsColumnWidth-initialsColumn.length()+1); i++){
            System.out.print(space);
        }
        System.out.println(line);
    }

    void printSep(){
        for(int i = 0; i< tableWidth; i++)
            System.out.print(sSep);
    }


}
