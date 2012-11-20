package com.bsb.common.integration.checkstyle.tester;

import com.bsb.common.integration.checkstyle.tester.support.ClassMultipleViolation;
import com.bsb.common.integration.checkstyle.tester.support.ClassWithException;
import com.bsb.common.integration.checkstyle.tester.support.ClassWithOneViolation;
import com.bsb.common.integration.checkstyle.tester.support.ClassWithoutViolation;
import com.bsb.common.integration.checkstyle.tester.support.ExceptionThrowerCheck;
import com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck;
import junit.framework.AssertionFailedError;
import org.junit.Test;

import static com.bsb.common.integration.checkstyle.tester.ViolationMatcher.*;


/**
 * @author Sebastien Gerard
 */
public class ClassCheckResultTest extends AbstractCheckStyleTest {

    @Test
    public void assertSuccessful() {
        getAnalysisNoException().get(ClassWithoutViolation.class).assertSuccessful();
    }

    @Test(expected = AssertionFailedError.class)
    public void assertSuccessfulButViolation() {
        getAnalysisNoException().get(ClassWithOneViolation.class).assertSuccessful();
    }

    @Test
    public void assertThat() {
        getAnalysisNoException().get(ClassWithOneViolation.class).assertThat(
            violation(FinalClassCheck.class)
        );
    }

    @Test(expected = AssertionFailedError.class)
    public void assertThatFailed() {
        getAnalysisNoException().get(ClassWithOneViolation.class).assertThat(
            violation(ConstantNameCheck.class).on(Integer.MAX_VALUE, Integer.MAX_VALUE)
        );
    }

    @Test
    public void assertOnlyThose() {
        getAnalysisNoException().get(ClassMultipleViolation.class).assertOnlyThose(
            violation(ConstantNameCheck.class).on(11, 33),
            violation(ConstantNameCheck.class).on(12, 33),
            violation(FinalClassCheck.class)
        );
    }

    @Test(expected = AssertionFailedError.class)
    public void assertOnlyThoseFailedMore() {
        getAnalysisNoException().get(ClassMultipleViolation.class).assertOnlyThose(
            violation(ConstantNameCheck.class)
        );
    }

    @Test
    public void assertExceptionContains() {
        getAnalysisWithException().get(ClassWithException.class).assertExceptionContains(ExceptionThrowerCheck.MESSAGE);
    }

    @Test(expected = AssertionFailedError.class)
    public void assertExceptionContainsFailed() {
        getAnalysisWithException().get(ClassWithException.class).assertExceptionContains("123");
    }

}
