package com.xuan.study;

import com.xuan.study.model.XmlIconFontModel;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.xuan.study.Common.ICON_START;

/**
 * Author : xuan.
 * Date : 2019-01-30.
 * Description :IconFont定义的xml解析器
 */
public class IconFontXmlParser extends DefaultHandler {
    private List<XmlIconFontModel> fonts;
    private HashMap<String, XmlIconFontModel> result;

    private int minValue;
    private int maxValue;

    @Override
    public void startDocument() {
        fonts = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (!qName.equals("string")) {
            return;
        }
        XmlIconFontModel model = new XmlIconFontModel();
        model.setFontKey(attributes.getValue(0));
        fonts.add(model);
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (fonts == null || fonts.size() == 0) {
            return;
        }
        String value = new String(ch, start, length);
        String result;
        //过滤换行符和空格
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(value);
        result = m.replaceAll("");
        if (result == null || result.isEmpty()) {
            return;
        }
//        System.out.println(result);
        fonts.get(fonts.size() - 1).setFontValue(result);
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
