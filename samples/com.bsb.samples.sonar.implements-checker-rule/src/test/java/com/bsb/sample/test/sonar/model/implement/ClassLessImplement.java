package com.bsb.sample.test.sonar.model.implement;

/**
 * @author Sebastien Gerard
 */
public class ClassLessImplement implements Comparable<ClassLessImplement>, Cloneable {

    private final int number;

    public ClassLessImplement(int number) {
        this.number = number;
    }

    @Override
    public int compareTo(ClassLessImplement o) {
        return Integer.compare(number, o.number);
    }
}
