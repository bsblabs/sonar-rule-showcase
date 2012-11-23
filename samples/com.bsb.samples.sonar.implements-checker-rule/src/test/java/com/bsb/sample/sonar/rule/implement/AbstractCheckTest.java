package com.bsb.sample.sonar.rule.implement;


import com.bsb.common.integration.checkstyle.tester.CheckStyleTesterBuilder;

/**
 * @author Sebastien Gerard
 */
public abstract class AbstractCheckTest {

    public static final String CHECKSTYLE_CONFIG = "/checkstyle.xml";

    protected static CheckStyleTesterBuilder getTester() {
        return CheckStyleTesterBuilder.forConfigFile(CHECKSTYLE_CONFIG)
            .withSourceLocationAsProperty();
    }

}
