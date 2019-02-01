package main;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Author : xuan.
 * Date : 2019-01-29.
 * Description :one piece
 */
public class PreviewAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        if (project == null) {
            return;
        }
        String rootPath = project.getBasePath();
        init(rootPath);
        //ttf文件路径
        AtomicReference<String> ttfPath =
                new AtomicReference<>(PersistentPath.getInstance().getTtfPath());
        //xml资源文件路径
        AtomicReference<String> xmlPath =
                new AtomicReference<>(PersistentPath.getInstance().getXmlPath());
        if (ttfPath.get() == null || ttfPath.get().length() == 0) {
            TTFChooser.show(rootPath).setOnPathSelected((ttfPathResult, xmlPathResult) -> {
                ttfPath.set(ttfPathResult);
                xmlPath.set(xmlPathResult);
                PersistentPath.getInstance().save();
                Preview.go(ttfPath.get(), xmlPath.get());
            });
        } else {
            Preview.go(ttfPath.get(), xmlPath.get());
        }
    }

    private void init(String rootPath) {
        Common.RESULT_PATH = String.format(Common.RESULT_PATH, rootPath);
        Common.CACHE_PATH = String.format(Common.CACHE_PATH, rootPath);
    }
}
