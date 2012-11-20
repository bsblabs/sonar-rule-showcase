package com.bsb.common.integration.checkstyle.tester;

import com.bsb.common.integration.checkstyle.tester.support.ClassMultipleViolation;
import com.bsb.common.integration.checkstyle.tester.support.ClassWithOneViolation;
import junit.framework.AssertionFailedError;
import org.junit.Test;

import java.io.File;

import static junit.framework.Assert.*;

/**
 * @author Sebastien Gerard
 */
public class CheckStyleTesterTest extends AbstractCheckStyleTest {

    @Test
    public void getResultScanned() {
        getAnalysisNoException().get(ClassWithOneViolation.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getResultNotScanned() {
        getAnalysisNoException().get(String.class);
    }

    @Test
    public void assertNoException() {
        getAnalysisNoException().assertNoException();
    }

    @Test(expected = AssertionFailedError.class)
    public void assertNoExceptionButException() {
        getAnalysisWithException().assertNoException();
    }

    @Test(expected = IllegalArgumentException.class)
    public void fileToScanNotExist() {
        CheckStyleTesterBuilder.forConfigFile(CHECKSTYLE_CONFIG).withSourceLocation(new File("123")).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void fileNotJava() {
        CheckStyleTesterBuilder.forConfigFile(CHECKSTYLE_CONFIG)
            .withSourceLocation(new File(getClass().getResource(CHECKSTYLE_CONFIG).getFile())).build();
    }

    @Test
    public void fileIsJavaFile() {
        final String sourceFile = System.getProperty(CheckStyleTesterBuilder.SOURCE_LOCATION) + "/" +
            ClassWithOneViolation.class.getSimpleName() + ".java";

        final CheckStyleTester tester = CheckStyleTesterBuilder.forConfigFile(CHECKSTYLE_CONFIG)
            .withSourceLocation(new File(sourceFile)).build();

        tester.get(ClassWithOneViolation.class);

        try {
            tester.get(ClassMultipleViolation.class);
            fail("An exception is expected");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test(expected = IllegalStateException.class)
    public void systemPropertyNotSet() {
        final String property = System.getProperty(CheckStyleTesterBuilder.SOURCE_LOCATION);

        try {
            System.clearProperty(CheckStyleTesterBuilder.SOURCE_LOCATION);

            CheckStyleTesterBuilder.forConfigFile(CHECKSTYLE_CONFIG).withSourceLocationAsProperty();
        } finally {
            System.setProperty(CheckStyleTesterBuilder.SOURCE_LOCATION, property);
        }
    }

}
