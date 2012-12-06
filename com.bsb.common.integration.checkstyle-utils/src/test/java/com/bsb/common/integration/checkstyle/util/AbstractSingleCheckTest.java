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
package com.bsb.common.integration.checkstyle.util;

import com.bsb.common.integration.checkstyle.tester.CheckStyleTester;
import com.bsb.common.integration.checkstyle.tester.CheckStyleTesterBuilder;
import com.bsb.common.integration.checkstyle.util.support.ClassWithImplements;
import com.bsb.common.integration.checkstyle.util.support.ClassWithViolation;
import com.bsb.common.integration.checkstyle.util.support.NameCheck;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.bsb.common.integration.checkstyle.tester.ViolationMatcher.*;

/**
 * @author Sebastien Gerard
 */
public class AbstractSingleCheckTest {

    public static final String CHECKSTYLE_CONFIG = "/checkstyle.xml";

    private static CheckStyleTester analysis;

    @BeforeClass
    public static void setup() {
        analysis = getTester().build().assertNoException();
    }

    @Test
    public void classWithImplements() {
        analysis.get(ClassWithImplements.class).assertSuccessful();
    }

    @Test
    public void classWithViolation() {
        analysis.get(ClassWithViolation.class).assertOnlyThose(
            violation(NameCheck.class)
                .on(24, 14)
                .withMessage(NameCheck.ERROR_MESSAGE)
                .withLevel(SeverityLevel.IGNORE)
        );
    }

    private static CheckStyleTesterBuilder getTester() {
        return CheckStyleTesterBuilder.forConfigFile(CHECKSTYLE_CONFIG).withSourceLocationAsProperty();
    }

}
