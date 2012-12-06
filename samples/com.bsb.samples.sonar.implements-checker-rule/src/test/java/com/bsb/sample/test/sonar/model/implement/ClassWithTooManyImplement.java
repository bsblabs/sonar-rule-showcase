/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bsb.sample.test.sonar.model.implement;

import java.io.Serializable;

/**
 * @author Sebastien Gerard
 */
public class ClassWithTooManyImplement implements Comparable<ClassWithTooManyImplement>, Cloneable, Serializable {

    private final Integer number;

    public ClassWithTooManyImplement(int number) {
        this.number = number;
    }

    @Override
    public int compareTo(ClassWithTooManyImplement o) {
        return number.compareTo(o.number);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}