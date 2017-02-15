import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Created by DSizov on 15.02.2017.
 */
public class Generator {
    public static int genWidth;
    public static int genHeight;
    public static String number;
    public static int nWidth;
    public static String Date;
    public static int dWidth;
    public static String fio;
    public static int fioWidth;

    public static void main(String[] args) throws  IOException, ParserConfigurationException, SAXException {
        File file = new File("C:\\Users\\DSizov\\IdeaProjects\\Generator\\settings.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document =  builder.parse(file);
        Element el = document.getElementById("width");
        genWidth = Integer.parseInt(el.toString());
        System.out.println(genWidth);
    }
}
