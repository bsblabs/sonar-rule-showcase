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
package com.bsb.common.integration.checkstyle.tester.support;

/**
 * The class is not declared final while there is only a private constructor.
 * The constants do not follow the convention.
 *
 * @author Sebastien Gerard
 */
public class ClassMultipleViolation {

    private static final String defaultTarget = "scanned";
    private static final String defaultNameSpace = "com.bsb.tools";

    private String target = defaultTarget;
    private String nameSpace = defaultNameSpace;

    public static ClassMultipleViolation createInstance(){
        return new ClassMultipleViolation();
    }

    private ClassMultipleViolation(){
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

}
