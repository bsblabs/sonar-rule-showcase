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

import org.junit.BeforeClass;

/**
 * @author Sebastien Gerard
 */
public abstract class AbstractCheckStyleTest {

    public static final String CHECKSTYLE_CONFIG = "/checkstyle.xml";
    public static final String CHECKSTYLE_WITH_EXCEPTION_CONFIG = "/checkstyle-with-exception.xml";

    private static CheckStyleTester analysisNoException;
    private static CheckStyleTester analysisWithException;

    @BeforeClass
    public static void setup() {
        analysisNoException = CheckStyleTesterBuilder.forConfigFile(CHECKSTYLE_CONFIG)
            .withSourceLocationAsProperty().build()
            .assertNoException();
        analysisWithException = CheckStyleTesterBuilder.forConfigFile(CHECKSTYLE_WITH_EXCEPTION_CONFIG)
            .withSourceLocationAsProperty().build();
    }

    protected CheckStyleTester getAnalysisNoException() {
        return analysisNoException;
    }

    protected CheckStyleTester getAnalysisWithException() {
        return analysisWithException;
    }

}
