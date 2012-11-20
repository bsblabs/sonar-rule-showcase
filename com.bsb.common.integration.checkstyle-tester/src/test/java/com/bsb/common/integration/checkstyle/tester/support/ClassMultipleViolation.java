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
