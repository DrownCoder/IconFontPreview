package main;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import main.util.CreateFileUtil;


/**
 * Author : xuan.
 * Date : 2019-02-01.
 * Description :持久化路径
 */
public class PersistentPath {
    private static final String INDEX = "&";
    private volatile static PersistentPath instance;
    private String ttfPath;
    private String xmlPath;

    public static PersistentPath getInstance() {
        if (instance == null) {
            synchronized (PersistentPath.class) {
                if (instance == null) {
                    instance = new PersistentPath();
                }
            }
        }
        return instance;
    }

    public PersistentPath() {
        init();
    }

    public String getTtfPath() {
        return ttfPath;
    }

    public void setTtfPath(String ttfPath) {
        this.ttfPath = ttfPath;
    }

    public String getXmlPath() {
        return xmlPath;
    }

    public void setXmlPath(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    public void save() {
        try {
            File file = CreateFileUtil.createFile(Common.CACHE_PATH);
            if (file == null) {
                return;
            }
            FileWriter writer = new FileWriter(file);
            writer.write(ttfPath + INDEX + xmlPath);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init() {
        try {
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(Common.CACHE_PATH)); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            StringBuilder line = new StringBuilder();
            String temp;
            while ((temp = br.readLine()) != null) {
                line.append(temp);
            }
            String[] cache = line.toString().trim().split(INDEX);
            ttfPath = cache[0];
            xmlPath = cache[1];
            br.close();
            reader.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
