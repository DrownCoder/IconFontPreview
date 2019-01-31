import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

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
        String txt = Messages.showInputDialog(project,
                "What is your name?",
                "Input your name",
                Messages.getQuestionIcon());
        Messages.showMessageDialog(project,
                "Hello, " + txt + "!\n I am glad to see you.",
                "Information",
                Messages.getInformationIcon());
    }
}
