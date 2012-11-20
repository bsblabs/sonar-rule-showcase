package com.bsb.common.integration.checkstyle.tester;

import com.bsb.common.integration.checkstyle.tester.support.ClassWithOneViolation;
import com.bsb.common.integration.checkstyle.tester.support.ClassWithoutViolation;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck;
import junit.framework.AssertionFailedError;
import org.junit.Test;

import static com.bsb.common.integration.checkstyle.tester.ViolationMatcher.*;


/**
 * @author Sebastien Gerard
 */
public class ViolationMatcherTest extends AbstractCheckStyleTest {

    @Test
    public void matchOnlyClass() {
        getAnalysisNoException().get(ClassWithOneViolation.class).assertOnlyThose(
            violation(FinalClassCheck.class)
        );
    }

    @Test(expected = AssertionFailedError.class)
    public void noMatchClass() {
        getAnalysisNoException().get(ClassWithoutViolation.class).assertOnlyThose(
            violation(ConstantNameCheck.class)
        );
    }

    @Test
    public void matchLineColumn() {
        getAnalysisNoException().get(ClassWithOneViolation.class).assertOnlyThose(
            violation(FinalClassCheck.class).on(10, 0)
        );
    }

    @Test(expected = AssertionFailedError.class)
    public void notMatchLineMatchColumn() {
        getAnalysisNoException().get(ClassWithOneViolation.class).assertOnlyThose(
            violation(FinalClassCheck.class).on(4, 0)
        );
    }

    @Test(expected = AssertionFailedError.class)
    public void matchLineNotMatchColumn() {
        getAnalysisNoException().get(ClassWithOneViolation.class).assertOnlyThose(
            violation(FinalClassCheck.class).on(10, 1)
        );
    }

    @Test
    public void matchMessage() {
        getAnalysisNoException().get(ClassWithOneViolation.class).assertOnlyThose(
            violation(FinalClassCheck.class).withMessage(getFinalClassCheckMessage(ClassWithOneViolation.class))
        );
    }

    @Test(expected = AssertionFailedError.class)
    public void notMatchMessage() {
        getAnalysisNoException().get(ClassWithOneViolation.class).assertOnlyThose(
            violation(ConstantNameCheck.class).withMessage("123")
        );
    }

    @Test
    public void matchLevel() {
        getAnalysisNoException().get(ClassWithOneViolation.class).assertOnlyThose(
            violation(FinalClassCheck.class).withLevel(SeverityLevel.ERROR)
        );
    }

    @Test(expected = AssertionFailedError.class)
    public void notMatchLevel() {
        getAnalysisNoException().get(ClassWithOneViolation.class).assertOnlyThose(
            violation(FinalClassCheck.class).withLevel(SeverityLevel.IGNORE)
        );
    }

    @Test
    public void allMatch() {
        getAnalysisNoException().get(ClassWithOneViolation.class).assertOnlyThose(
            violation(FinalClassCheck.class)
                .on(10, 0)
                .withMessage(getFinalClassCheckMessage(ClassWithOneViolation.class))
                .withLevel(SeverityLevel.ERROR)
        );
    }

    private String getFinalClassCheckMessage(Class<?> violatedClass) {
        return "Class " + violatedClass.getSimpleName() + " should be declared as final.";
    }

}
