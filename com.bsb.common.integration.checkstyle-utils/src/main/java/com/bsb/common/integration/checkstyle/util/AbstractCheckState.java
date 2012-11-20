package com.bsb.common.integration.checkstyle.util;

/**
 * This class represents the state of a class checkAfterVisit.
 *
 * @author Sebastien Gerard
 */
public abstract class AbstractCheckState {

    private int violationLine = -1;
    private int violationColumn = -1;

    /**
     * Returns the line where there is a violation.
     *
     * @return the violation line
     */
    public int getViolationLine() {
        return violationLine;
    }

    /**
     * Sets the line where there is a violation.
     *
     * @param violationLine the violation line
     */
    public void setViolationLine(int violationLine) {
        this.violationLine = violationLine;
    }

    /**
     * Returns the column where there is a violation.
     *
     * @return the violation column
     */
    public int getViolationColumn() {
        return violationColumn;
    }

    /**
     * Sets the column where there is a violation.
     *
     * @param violationColumn the violation column
     */
    public void setViolationColumn(int violationColumn) {
        this.violationColumn = violationColumn;
    }

    /**
     * Callback when the visitor has finished a file. This method is called before
     * {@link AbstractSingleCheck#isConstraintSatisfied()}  and can be used to check that the state has all
     * needed information to test whether the condition is satisfied.
     *
     * @throws IllegalStateException if the checkAfterVisit fails
     */
    public void checkAfterVisit() throws IllegalStateException {
    }

    /**
     * Callback when the check is about to log a message.
     */
    public void checkBeforeLog() {
        if ((getViolationLine() < 0) || (getViolationColumn() < 0)) {
            throw new IllegalStateException("The line " + getViolationLine() + " and the column " +
                getViolationColumn() + " number must be greater, or equals to 0");
        }
    }

}
