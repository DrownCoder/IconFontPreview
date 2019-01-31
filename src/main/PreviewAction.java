package main;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;

import main.Common;
import main.FileUtil;
import main.Preview;


/**
 * Author : xuan.
 * Date : 2019-01-29.
 * Description :the description of this file
 */
public class PreviewAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        Project project = e.getData(PlatformDataKeys.PROJECT);
        String rootPath = project.getBasePath();
        String iconfontPath = rootPath + "/app/src/main/assets/iconfont.ttf";
        String stringPath = rootPath + "/app/src/main/res/values/string.xml";
        Common.ROOT_PATH = rootPath + "/app/src";
        Common.RESULT_PATH = String.format(Common.RESULT_PATH, rootPath);
        Preview.go(iconfontPath, stringPath);
    }
}
