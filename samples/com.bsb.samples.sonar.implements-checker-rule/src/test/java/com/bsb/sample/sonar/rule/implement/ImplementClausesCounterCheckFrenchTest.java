package com.bsb.sample.sonar.rule.implement;

import com.bsb.common.integration.checkstyle.tester.CheckStyleTester;
import com.bsb.sample.test.sonar.model.implement.ClassWithTooManyImplement;
import com.bsb.sonar.sample.rule.implement.ImplementClausesCounterCheck;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Locale;

import static com.bsb.common.integration.checkstyle.tester.ViolationMatcher.*;


/**
 * @author Sebastien Gerard
 */
public class ImplementClausesCounterCheckFrenchTest extends AbstractCheckTest {

    private static CheckStyleTester analysis;


    @BeforeClass
    public static void setup() {
        analysis = getTester().withLocale(Locale.FRENCH).build().assertNoException();
    }

    @Test
    public void locale() {
        analysis.get(ClassWithTooManyImplement.class)
            .assertOnlyThose(
                violation(ImplementClausesCounterCheck.class).on(20, 40)
                    .withLevel(SeverityLevel.WARNING)
                    .withMessage("Trop d'implements (plus de 10).")
            );
    }

}
