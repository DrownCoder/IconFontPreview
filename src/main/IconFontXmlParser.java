package main;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import main.model.XmlIconFontModel;

import static main.Common.ICON_START;


/**
 * Author : xuan.
 * Date : 2019-01-30.
 * Description :IconFont定义的xml解析器
 */
public class IconFontXmlParser extends DefaultHandler {
    private List<XmlIconFontModel> fonts;
    private HashMap<String, XmlIconFontModel> result;
    private StringBuilder sb;
    private boolean flag = false;

    private int minValue;
    private int maxValue;

    @Override
    public void startDocument() {
        fonts = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (!qName.equals("string")) {
            flag = false;
            return;
        }
        XmlIconFontModel model = new XmlIconFontModel();
        model.setFontKey(attributes.getValue(0));
        fonts.add(model);
        sb = new StringBuilder();
        flag = true;
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (fonts == null || fonts.size() == 0 || !flag) {
            return;
        }
        String value = new String(ch, start, length);
        String result;
        //过滤换行符和空格
        result = value.trim();
        if (result.isEmpty() || result.contains("\n")) {
            return;
        }
        sb.append(value);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        flag = false;
        if (sb != null && sb.length() > 0) {
            fonts.get(fonts.size() - 1).setFontValue(sb.toString());
            System.out.println(sb.toString());
        }
    }

    @Override
    public void endDocument() throws SAXException {
        result = new HashMap<>();
        Iterator<XmlIconFontModel> iterator = fonts.iterator();
        while (iterator.hasNext()) {
            XmlIconFontModel model = iterator.next();
            if (model.getFontKey().startsWith("icon_font")
                    || model.getFontValue().startsWith(ICON_START)) {
                result.put(model.getFontValue(), model);
                System.out.println(model.getFontValue());
                int value = Integer.valueOf(model.getFontValue().substring(3), 16);
                if (minValue == 0) {
                    //初始化
                    minValue = value;
                    maxValue = minValue;
                } else {
                    if (value > maxValue) {
                        maxValue = value;
                    }
                    if (value < minValue) {
                        minValue = value;
                    }
                }
            } else {
                iterator.remove();
            }
        }
        System.out.println("\\ue" + Integer.toHexString(maxValue));
        System.out.println("\\ue" + Integer.toHexString(minValue));
    }

    public HashMap<String, XmlIconFontModel> getFonts() {
        return result;
    }

    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }
}
