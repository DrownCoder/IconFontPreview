package main;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import main.model.XmlIconFont;
import main.model.XmlIconFontModel;
import main.util.CreateFileUtil;
import main.util.Utils;

import static main.Common.NONE_DEFINE;
import static main.Common.RESULT_PATH;


/**
 * Author : xuan.
 * Date : 2019-01-30.
 * Description :输出Html文本，并保存
 */
public class HtmlPrinter {
    private XmlIconFont data;

    public static HtmlPrinter init(XmlIconFont data) {
        return new HtmlPrinter(data);
    }

    private HtmlPrinter(XmlIconFont data) {
        this.data = data;
    }

    public void printer(String ttfPath) {
        InputStream html = this.getClass().getResourceAsStream(Common.HTML_PATH);
        try {
            Document doc = Jsoup.parse(html, "UTF-8", "");
            if (data != null) {
                Elements style = doc.select("style");
                style.prepend(String.format(Common.STYLE_DF, ttfPath));
                Elements container = doc.getElementsByClass("icon_lists clear");
                if (Common.MODE_ALL) {
                    //全量模式，从16进制最小值到16进制最大值
                    printerAll(container);
                } else {
                    //自定义模式,只输出定义的资源文件
                    printerDefine(container);
                }
            }
            File result = CreateFileUtil.createFile(RESULT_PATH);
            if (result == null) {
                return;
            }
            FileOutputStream outStream = new FileOutputStream(result);    //文件输出流用于将数据写入文件
            outStream.write(doc.toString().getBytes(StandardCharsets.UTF_8));
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printerDefine(Elements container) {
        for (Map.Entry<String, XmlIconFontModel> entry : data.getFonts().entrySet()) {
            String code = entry.getValue().getFontValue();
            if (code.startsWith(Common.ICON_START)) {
                String value = "&#xe" + code.substring(3) + ";";
                container.append(String.format(Common.ICON_ITEM, value,
                        entry.getValue().getFontKey(), code));
            } else if (code.startsWith(Common.ICON_START_SUB)) {
                container.append(String.format(Common.ICON_ITEM, code,
                        entry.getValue().getFontKey(), code));
            }
        }
    }

    private void printerAll(Elements container) {
        for (int i = data.getMinValue(); i <= data.getMaxValue(); i++) {
            String hexValue = Utils.parseHexTCode(i);
            String code = "\\ue" + hexValue;
            String value = "&#xe" + hexValue + ";";
            XmlIconFontModel keyModel = data.getFonts().get(code);
            String key = NONE_DEFINE;
            if (keyModel != null) {
                key = keyModel.getFontKey();
            }
            container.append(String.format(Common.ICON_ITEM, value, key, code));
        }
    }
}
