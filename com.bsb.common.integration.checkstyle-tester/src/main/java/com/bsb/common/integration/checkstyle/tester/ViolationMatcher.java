package com.bsb.common.integration.checkstyle.tester;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;

/**
 * This class describes helps to identify a {@link AuditEvent violation}.
 *
 * @author Sebastien Gerard
 */
public final class ViolationMatcher {

    private final Class<?> ruleClass;
    private int line = -1;
    private int column = -1;
    private String message;
    private SeverityLevel level;

    /**
     * Creates the matcher with the class of the rule check.
     *
     * @param ruleClass the class of the rule check
     * @return a new matcher instance
     */
    public static ViolationMatcher violation(Class<?> ruleClass) {
        return new ViolationMatcher(ruleClass);
    }

    /**
     * Creates an instance with the mandatory information.
     *
     * @param ruleClass the class of the rule check
     */
    private ViolationMatcher(Class<?> ruleClass) {
        this.ruleClass = ruleClass;
    }

    /**
     * Asserts that the violation occurred on the given column and line.
     *
     * @param line the given line
     * @param column the given column
     * @return this instance
     */
    public ViolationMatcher on(int line, int column) {
        this.line = line;
        this.column = column;
        return this;
    }

    /**
     * Asserts that the violation has the given message. The message
     * is translated into the language configured in {@link CheckStyleTester}.
     *
     * @param message the given message
     * @return this instance
     */
    public ViolationMatcher withMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * Asserts that the violation has the given level.
     *
     * @param level the given level
     * @return this instance
     */
    public ViolationMatcher withLevel(SeverityLevel level) {
        this.level = level;
        return this;
    }

    /**
     * Asserts that this instance matches one the given violation.
     *
     * @param violation the given violation
     * @return <tt>true</tt> if it matches, otherwise <tt>false</tt>
     */
    boolean match(AuditEvent violation) {
        final boolean ruleClassMatch = violation.getSourceName().equals(ruleClass.getName());
        final boolean messageMatch = (message == null) || message.equals(violation.getMessage());
        final boolean levelMatch = (level == null) || level.equals(violation.getSeverityLevel());
        final boolean lineMatch = (line < 0) || (violation.getLine() == line);
        final boolean columnMatch = (column < 0) || (violation.getColumn() == column);

        return ruleClassMatch && lineMatch && columnMatch && messageMatch && levelMatch;
    }

    @Override
    public String toString() {
        return "Matcher on rule " + ruleClass.getSimpleName();
    }

}
