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
package com.bsb.sample.sonar.rule.implement;

import com.bsb.common.integration.checkstyle.tester.CheckStyleTester;
import com.bsb.sample.test.sonar.model.implement.ClassLessImplement;
import com.bsb.sample.test.sonar.model.implement.ClassWithInner;
import com.bsb.sample.test.sonar.model.implement.ClassWithNoImplement;
import com.bsb.sample.test.sonar.model.implement.ClassWithTooManyImplement;
import com.bsb.sonar.sample.rule.implement.ImplementClausesCounterCheck;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.bsb.common.integration.checkstyle.tester.ViolationMatcher.*;


/**
 * @author Sebastien Gerard
 */
public class ImplementClausesCounterCheckTest extends AbstractCheckTest {

    private static CheckStyleTester analysis;


    @BeforeClass
    public static void setup() {
        analysis = getTester().build().assertNoException();
    }

    @Test
    public void classWithNoImplement() {
        analysis.get(ClassWithNoImplement.class).assertSuccessful();
    }

    @Test
    public void classLessImplement() {
        analysis.get(ClassLessImplement.class).assertSuccessful();
    }

    @Test
    public void classWithTooManyImplement() {
        analysis.get(ClassWithTooManyImplement.class).assertOnlyThose(
            violation(ImplementClausesCounterCheck.class).on(23, 40)
                .withLevel(SeverityLevel.WARNING)
                .withMessage("There are too many implements (more than 2).")
        );
    }

    @Test
    public void classWithInner() {
        analysis.get(ClassWithInner.class).assertSuccessful();
    }

}
