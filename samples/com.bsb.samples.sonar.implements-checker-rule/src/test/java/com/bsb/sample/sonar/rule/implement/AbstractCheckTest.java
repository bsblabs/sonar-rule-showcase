package com.bsb.sample.sonar.rule.implement;


import com.bsb.common.integration.checkstyle.tester.CheckStyleTesterBuilder;

/**
 * @author Sebastien Gerard
 */
public abstract class AbstractCheckTest {

    protected static CheckStyleTesterBuilder getTester() {
        return CheckStyleTesterBuilder.forConfigFile(ImplementClausesCounterCheckTest.CHECKSTYLE_CONFIG)
            .withSourceLocationAsProperty();
    }

}
