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
        String pathName = FileUtil.getAppPackageName(project);
        if (project == null) {
            return;
        }
        String rootPath = project.getBasePath();
        String[] path = pathName.split("\\.");
        StringBuilder localPath = new StringBuilder();
        for (String pathItem : path) {
            localPath.append(pathItem).append("/");
        }
        //String iconfontPath = localPath + "assets/" + "iconfont.ttf";
        String iconfontPath = rootPath + "/app/src/main/assets/iconfont.ttf";
        //String stringPath = localPath + "/res/" + "string.xml";
        String stringPath = rootPath + "/app/src/main/res/values/string.xml";
        Common.ROOT_PATH = rootPath + "/app/src";
        Common.RESULT_PATH = rootPath + Common.RESULT_PATH;
        Preview.go(iconfontPath, stringPath);
    }
}
