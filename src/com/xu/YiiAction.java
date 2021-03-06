package com.xu;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

import static com.intellij.openapi.actionSystem.CommonDataKeys.VIRTUAL_FILE;

public class YiiAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        final Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        final PsiElement psiElement = e.getData(CommonDataKeys.PSI_ELEMENT);
        final PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);


        final Document document = editor.getDocument();
        final SelectionModel selectionModel = editor.getSelectionModel();

        int start = selectionModel.getSelectionStart();
        int end = selectionModel.getSelectionEnd();
        String replaceStr = selectionModel.getSelectedText();

        final VirtualFile file = e.getData(VIRTUAL_FILE);
        if (file == null) {
            Messages.showMessageDialog("No active file", "Error",Messages.getErrorIcon());
            return;
        }
        if(psiElement!= null){
            Messages.showMessageDialog("Please select the text", "Error",Messages.getErrorIcon());
            return;
        }

        final String findName = "app";

        assert psiFile != null;
        if(psiFile.findElementAt(editor.getCaretModel().getOffset()).getLanguage().getDisplayName().equals("PHP")){
            replaceStr = "Yii::t('"+findName+"','"+replaceStr+"')";
            start--;
            end++;
        }else{
            replaceStr = "<?=Yii::t('"+findName+"','"+replaceStr+"')?>";
        }

        final String str = replaceStr;
        final int LastStart = start;
        final int LastEnd= end;

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                document.replaceString(LastStart,LastEnd,str);
            }
        };
        WriteCommandAction.runWriteCommandAction(project,runnable);
        selectionModel.removeSelection();
    }


    @Override
    public void update(AnActionEvent e) {
        final Project project = e.getData(CommonDataKeys.PROJECT);
        final Editor editor = e.getData(CommonDataKeys.EDITOR);
        e.getPresentation().setVisible(project != null && editor != null && editor.getSelectionModel().hasSelection());
    }
}
