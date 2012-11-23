package com.bsb.sample.test.sonar.model.implement;

/**
 * @author Sebastien Gerard
 */
public class ClassLessImplement implements Comparable<ClassLessImplement>, Cloneable {

    private final Integer number;

    public ClassLessImplement(int number) {
        this.number = number;
    }

    @Override
    public int compareTo(ClassLessImplement o) {
        return number.compareTo(o.number);
    }

}
