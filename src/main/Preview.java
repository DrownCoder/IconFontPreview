package main;

import com.xuan.study.model.XmlIconFont;
import com.xuan.study.util.OSinfo;

import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import static com.xuan.study.Common.RESULT_PAHT;

public class Main {
    public static final String FONT_STRING_PATH = "src/com/xuan/study/string.xml";

    public static void main(String[] args) {

        SAXParserFactory spf = SAXParserFactory.newInstance();
        XmlIconFont result = null;
        try {
            SAXParser sp = spf.newSAXParser();
            FileInputStream inputStream = new FileInputStream(FONT_STRING_PATH);
            IconFontXmlParser parser = new IconFontXmlParser();
            sp.parse(inputStream, parser);
            result = new XmlIconFont(parser.getFonts(), parser.getMaxValue(),
                    parser.getMinValue());
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        HtmlPrinter.init(result).printer();
        try {
            if (OSinfo.isWindows()) {
                Runtime.getRuntime().exec("cmd.exe /c start " + RESULT_PAHT);
            } else {
                Runtime.getRuntime().exec("open " + RESULT_PAHT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
