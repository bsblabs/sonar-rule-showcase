/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
            violation(ConstantNameCheck.class).on(26, 33),
            violation(ConstantNameCheck.class).on(27, 33),
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
