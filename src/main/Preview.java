package main;


import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import main.model.XmlIconFont;
import main.util.OSinfo;

import static main.Common.RESULT_PATH;


public class Preview {

    public static void go(String ttfPath, String stringPath) {
        /**
         * 解析xml资源
         */
        SAXParserFactory spf = SAXParserFactory.newInstance();
        XmlIconFont result = null;
        try {
            SAXParser sp = spf.newSAXParser();
            FileInputStream inputStream = new FileInputStream(stringPath);
            IconFontXmlParser parser = new IconFontXmlParser();
            sp.parse(inputStream, parser);
            result = new XmlIconFont(parser.getFonts(), parser.getMaxValue(),
                    parser.getMinValue());
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        /**
         * 生成Html
         */
        HtmlPrinter.init(result).printer(ttfPath);
        /**
         * 执行展示
         */
        try {
            if (OSinfo.isWindows()) {
                Runtime.getRuntime().exec("cmd.exe /c start " + RESULT_PATH);
            } else {
                Runtime.getRuntime().exec("open " + RESULT_PATH);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
