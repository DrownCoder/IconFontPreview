package main;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import main.model.XmlIconFont;
import main.model.XmlIconFontModel;
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
            File result = new File(Common.tranPath(RESULT_PATH));
            if (!result.exists()) {    //文件不存在则创建文件，先创建目录
                result.createNewFile();
            }
            FileOutputStream outStream = new FileOutputStream(result);    //文件输出流用于将数据写入文件
            outStream.write(doc.toString().getBytes(StandardCharsets.UTF_8));
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
