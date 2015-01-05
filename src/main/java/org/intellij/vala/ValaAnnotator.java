package org.intellij.vala;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.psi.PsiElement;
import org.intellij.vala.psi.ValaFieldDeclaration;
import org.intellij.vala.psi.impl.ValaMemberPartImpl;
import org.jetbrains.annotations.NotNull;

public class ValaAnnotator implements Annotator {

    @Override
    public void annotate(@NotNull PsiElement psiElement, @NotNull AnnotationHolder annotationHolder) {
        ProgressManager.checkCanceled();
        if (isClassFieldAccess(psiElement)) {
            annotationHolder.createInfoAnnotation(psiElement, null).setTextAttributes(DefaultLanguageHighlighterColors.INSTANCE_FIELD);
        }
    }

    private static boolean isClassFieldAccess(PsiElement psiElement) {
        return psiElement instanceof ValaMemberPartImpl && psiElement.getReference() != null && psiElement.getReference().resolve() instanceof ValaFieldDeclaration;
    }
}