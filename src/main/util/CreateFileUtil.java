package main.util;

import java.io.File;
import java.io.IOException;

public class CreateFileUtil {

    public static File createFile(String destFileName) {
        File file = new File(destFileName);
        if (file.exists()) {
            return file;
        }
        if (destFileName.endsWith(File.separator)) {
            return null;
        }
        //判断目标文件所在的目录是否存在
        if (!file.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            if (!file.getParentFile().mkdirs()) {
                return file;
            }
        }
        //创建目标文件
        try {
            if (file.createNewFile()) {
                return file;
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
