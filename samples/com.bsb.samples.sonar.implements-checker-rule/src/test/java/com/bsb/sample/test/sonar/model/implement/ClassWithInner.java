package com.bsb.sample.test.sonar.model.implement;

import java.io.Serializable;

/**
 * @author Sebastien Gerard
 */
public class ClassWithInner {

    public static class InnerClass implements Comparable<InnerClass>, Cloneable, Serializable {
        private final Integer number;

        public InnerClass(int number) {
            this.number = number;
        }

        @Override
        public int compareTo(InnerClass o) {
            return number.compareTo(o.number);
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

    }

}
