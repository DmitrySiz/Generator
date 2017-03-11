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
    public static String columnNumber;
    public static int columnNumberWidth;
    public static String columnDate;
    public static int columnDateWidth;
    public static String fio;
    public static int fioWidth;
    public static String line = "|";
    public static String sSep = "-";
    public static String pSep = "~";
    public static String space = " ";

    public static void main(String[] args) throws  IOException, ParserConfigurationException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document =  builder.parse(new File("C:\\Users\\DSizov\\IdeaProjects\\Generator\\settings.xml"));
        document.getDocumentElement().normalize();
        tableWidth = Integer.parseInt(document.getElementsByTagName("width").item(0).getTextContent());
        columnNumberWidth = Integer.parseInt(document.getElementsByTagName("width").item(1).getTextContent());
        columnDateWidth = Integer.parseInt(document.getElementsByTagName("width").item(2).getTextContent());
        fioWidth = Integer.parseInt(document.getElementsByTagName("width").item(3).getTextContent());
        tableHeight = Integer.parseInt(document.getElementsByTagName("height").item(0).getTextContent());
        columnNumber = document.getElementsByTagName("title").item(0).getTextContent();
        columnDate = document.getElementsByTagName("title").item(1).getTextContent();
        fio = document.getElementsByTagName("title").item(2).getTextContent();
        Generator start = new Generator();

        start.printFirst();
        start.printSep();
        try {
            BufferedReader bReader = new BufferedReader(new FileReader("C:\\Users\\DSizov\\IdeaProjects\\Generator\\source-data.tsv"));
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
        System.out.print(line+space+number);
        for (int i = 0; i < (nWidth-number.length()+1); i++){
            System.out.print(space);
        }
        System.out.print(line+space+ columnDate);
        for (int i = 0; i < (dWidth- columnDate.length()+1); i++){
            System.out.print(space);
        }
        System.out.print(line+space+fio);
        for (int i = 0; i < (fioWidth-fio.length()+1); i++){
            System.out.print(space);
        }
        System.out.println(line);
    }

    void printSep(){
        for(int i = 0; i< tableWidth; i++)
            System.out.print(sSep);
    }


}
