package com.xuan.study;

import com.xuan.study.model.XmlIconFont;
import com.xuan.study.model.XmlIconFontModel;
import com.xuan.study.util.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.xuan.study.Common.NONE_DEFINE;
import static com.xuan.study.Common.RESULT_PAHT;


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

    public void printer() {
        File html = new File(Common.HTML_PATH);
        try {
            Document doc = Jsoup.parse(html, "UTF-8");
            Elements style = doc.select("style");
            style.prepend(String.format(Common.STYLE_DF, "../iconfont.ttf"));
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
            File result = new File(RESULT_PAHT);
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
