package com.bsb.common.integration.checkstyle.tester.support;

import java.util.Random;

/**
 * The class is not declared final while there is only a private constructor.
 *
 * @author Sebastien Gerard
 */
public class ClassWithOneViolation {

    private static final Random RANDOM = new Random();

    private ClassWithOneViolation() {
    }

    public static long random() {
        return RANDOM.nextLong();
    }

}
