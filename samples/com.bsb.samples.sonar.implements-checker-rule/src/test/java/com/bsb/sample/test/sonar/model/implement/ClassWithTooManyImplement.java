package com.bsb.sample.test.sonar.model.implement;

import java.io.Serializable;

/**
 * @author Sebastien Gerard
 */
public class ClassWithTooManyImplement implements Comparable<Object>, Cloneable, Serializable {

    private final int number;

    public ClassWithTooManyImplement(int number) {
        this.number = number;
    }

    @Override
    public int compareTo(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }

        return Integer.compare(number, ((ClassWithTooManyImplement) o).number);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}