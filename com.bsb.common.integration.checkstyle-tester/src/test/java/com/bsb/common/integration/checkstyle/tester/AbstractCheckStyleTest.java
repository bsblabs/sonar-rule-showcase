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
