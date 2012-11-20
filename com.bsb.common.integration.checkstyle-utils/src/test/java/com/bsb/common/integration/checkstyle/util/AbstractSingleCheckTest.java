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
                .on(9, 14)
                .withMessage(NameCheck.ERROR_MESSAGE)
                .withLevel(SeverityLevel.IGNORE)
        );
    }

    private static CheckStyleTesterBuilder getTester() {
        return CheckStyleTesterBuilder.forConfigFile(CHECKSTYLE_CONFIG).withSourceLocationAsProperty();
    }

}
