package utils;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.xml.XmlDocument;

import java.io.File;

/**
 * Author : xuan.
 * Date : 2019-01-30.
 * Description :文件工具类
 */
public class FileUtil {
    /**
     * 获取AndroidManifest文件
     */
    public static PsiFile getManifestFile(Project project) {
        String path = project.getBasePath() + File.separator +
                "app" + File.separator +
                "src" + File.separator +
                "main" + File.separator +
                "AndroidManifest.xml";
        VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByPath(path);
        if(virtualFile == null) return null;
        return PsiManager.getInstance(project).findFile(virtualFile);
    }

    /**
     * 获取项目的package目录
     */
    public static String getAppPackageName(Project project) {
        PsiFile manifestFile = getManifestFile(project);
        XmlDocument xml = (XmlDocument) manifestFile.getFirstChild();
        return xml.getRootTag().getAttribute("package").getValue();
    }
}
