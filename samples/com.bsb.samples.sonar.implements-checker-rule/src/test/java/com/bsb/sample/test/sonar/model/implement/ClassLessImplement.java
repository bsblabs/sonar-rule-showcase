package com.bsb.sample.test.sonar.model.implement;

/**
 * @author Sebastien Gerard
 */
public class ClassLessImplement implements Comparable<Object>, Cloneable {

    private final int number;

    public ClassLessImplement(int number) {
        this.number = number;
    }

    @Override
    public int compareTo(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }

        return Integer.compare(number, ((ClassLessImplement) o).number);
    }
}
