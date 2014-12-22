package org.intellij.vala.psi.impl;


import com.google.common.collect.ImmutableList;
import org.intellij.vala.psi.ValaClassDeclaration;
import org.intellij.vala.psi.ValaClassMember;
import org.intellij.vala.psi.ValaMethodDeclaration;
import org.intellij.vala.psi.ValaNamespaceMember;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ClassDeclarationUtil {

    @NotNull
    public static List<ValaMethodDeclaration> getMethodDeclarations(ValaClassDeclaration classDeclaration) {
        return getMembersOfType(classDeclaration, ValaMethodDeclaration.class);
    }

    private static <T> List<T> getMembersOfType(ValaClassDeclaration classDeclaration, Class<T> expectedType) {
        ImmutableList.Builder<T> declarations = ImmutableList.builder();
        for (ValaClassMember member : classDeclaration.getClassMemberList()) {
            if (expectedType.isAssignableFrom(member.getNamespaceMember().getClass())) {
                declarations.add((T) member.getNamespaceMember());
            }
        }
        return declarations.build();
    }

    @NotNull
    public static List<ValaNamespaceMember> getNamespaceMemberList(ValaClassDeclaration valaClassDeclaration) {
        return getMembersOfType(valaClassDeclaration, ValaNamespaceMember.class);
    }
}
