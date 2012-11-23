package com.bsb.sample.test.sonar.model.implement;

import java.io.Serializable;

/**
 * @author Sebastien Gerard
 */
public class ClassWithTooManyImplement implements Comparable<ClassWithTooManyImplement>, Cloneable, Serializable {

    private final int number;

    public ClassWithTooManyImplement(int number) {
        this.number = number;
    }

    @Override
    public int compareTo(ClassWithTooManyImplement o) {
        return Integer.compare(number, o.number);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}