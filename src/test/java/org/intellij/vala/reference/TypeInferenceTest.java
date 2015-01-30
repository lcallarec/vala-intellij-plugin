package org.intellij.vala.reference;

import com.intellij.psi.PsiElement;
import org.intellij.vala.psi.ValaExpression;
import org.intellij.vala.psi.ValaMemberPart;
import org.intellij.vala.psi.ValaMethodDeclaration;
import org.intellij.vala.psi.ValaTypeDescriptor;
import org.intellij.vala.psi.inference.ExpressionTypeInference;

import java.io.IOException;

import static org.hamcrest.Matchers.*;
import static org.intellij.vala.psi.PsiMatchers.hasName;
import static org.intellij.vala.psi.impl.QualifiedNameBuilder.nameOf;
import static org.junit.Assert.assertThat;

public class TypeInferenceTest extends ValaReferenceTestBase {

    @Override
    protected String getTestDataPath() {
        return "src/test/resources/org/intellij/vala/inference/test";
    }

    public void testInferFromVariableReference() {
        myFixture.configureByFiles(getTestName(false) + ".vala");
        assertThat(inferredType(), is(ValaTypeDescriptor.INTEGER));
    }

    public void testInferTypeFromMethodCall() throws IOException {
        myFixture.configureByFiles(getTestName(false) + ".vala");
        assertThat(inferredType(), is(ValaTypeDescriptor.LONG));
    }

    public void testInferFromLiteral() {
        myFixture.configureByFiles(getTestName(false) + ".vala");
        assertThat(inferredType(), is(ValaTypeDescriptor.STRING));
    }

    public void testInferTypeFromConstructor() throws IOException {
        myFixture.configureByFiles(getTestName(false) + ".vala");
        assertThat(inferredType().getQualifiedName(), is(equalTo(nameOf("MyClass"))));
    }

    public void testInferFromOtherClassFieldReference() throws IOException {
        myFixture.configureByFiles(getTestName(false) + ".vala");
        assertThat(inferredType(), is(ValaTypeDescriptor.INTEGER));
    }

    public void testInferFromVariableReferenceChain() throws IOException {
        myFixture.configureByFiles(getTestName(false) + ".vala");
        assertThat(inferredType(), is(ValaTypeDescriptor.INTEGER));
    }

    public void testMethodReferenceOnInferredObjectType() throws IOException {
        myFixture.configureByFiles(getTestName(false) + ".vala");

        PsiElement referenced = getElementOfTypeAtCaret(ValaMemberPart.class).getReference().resolve();
        assertThat(referenced, allOf(instanceOf(ValaMethodDeclaration.class), hasName("getName")));
    }

    private ValaTypeDescriptor inferredType() {
        ValaExpression expression = getElementOfTypeAtCaret(ValaExpression.class);
        return ExpressionTypeInference.inferType(expression);
    }
}
