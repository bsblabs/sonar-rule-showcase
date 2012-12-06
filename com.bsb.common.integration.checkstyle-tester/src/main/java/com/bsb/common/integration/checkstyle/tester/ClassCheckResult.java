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
package com.bsb.common.integration.checkstyle.tester;

import com.google.common.base.Function;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import junit.framework.AssertionFailedError;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.*;
import static junit.framework.Assert.*;

/**
 * The result of class check analysis.
 *
 * @author Sebastien Gerard
 */
public class ClassCheckResult {

    public static final String EXCEPTION_MESSAGE_KEY = "general.exception";

    private final List<AuditEvent> violations = new ArrayList<AuditEvent>();
    private final List<AuditEvent> exceptions = new ArrayList<AuditEvent>();

    /**
     * Asserts that the class has been checked without any exception, nor violation.
     *
     * @return this instance
     */
    public ClassCheckResult assertSuccessful() {
        assertTrue("The file must have been successfully checked. But there are the following violations: " +
            violations, violations.isEmpty());
        assertNoException();

        return this;
    }

    /**
     * Asserts that no exception has been thrown during the analysis.
     *
     * @return this instance
     */
    public ClassCheckResult assertNoException() {
        assertTrue("The file must have been successfully checked. But there are the following exceptions: " +
            exceptionsToString(), exceptions.isEmpty());
        return this;
    }

    /**
     * Asserts that an exception has been thrown with a {@link #toString()}
     * containing the given message.
     *
     * @param message the given message
     * @return this instance
     */
    public ClassCheckResult assertExceptionContains(String message) {
        for (AuditEvent exception : getExceptions()) {
            if (exception.getLocalizedMessage().getMessage().contains(message)) {
                return this;
            }
        }

        throw new AssertionFailedError("The exception with the message [" + message + "] has not been found in: " +
            exceptionsToString());
    }

    /**
     * Asserts that the given matchers match the violations occurred during
     * the analysis.
     *
     * @param matchers the given matchers
     * @return this instance
     */
    public ClassCheckResult assertThat(ViolationMatcher... matchers) {
        for (ViolationMatcher matcher : matchers) {
            if (!match(matcher)) {
                fail("The matcher [" + matcher + "] does not match the violations: " + violationsToString());
            }
        }
        return this;
    }

    /**
     * Asserts that only the given violations occurred during the analysis.
     *
     * @param violations the given violations
     * @return this instance
     */
    public ClassCheckResult assertOnlyThose(ViolationMatcher... violations) {
        assertThat(violations);

        if (violations.length != getViolations().size()) {
            throw new AssertionFailedError("Wrong number of violations, expected " + violations.length + " but was " +
                getViolations().size() + ". Violations are " + violationsToString());
        }

        return this;
    }

    /**
     * Adds a violation occurred during the analysis.
     *
     * @param auditEvent a violation occurred during the analysis
     */
    void addError(AuditEvent auditEvent) {
        if (isException(auditEvent)) {
            exceptions.add(auditEvent);
        } else {
            violations.add(auditEvent);
        }
    }

    /**
     * Returns a non-modifiable list of violations occurred during the analysis.
     *
     * @return a list of violations
     */
    List<AuditEvent> getViolations() {
        return unmodifiableList(violations);
    }

    /**
     * Returns a non-modifiable list of exceptions occurred during the analysis.
     *
     * @return a list of violations
     */
    List<AuditEvent> getExceptions() {
        return unmodifiableList(exceptions);
    }

    /**
     * Checks that the given event refers to an exception. If the event
     * is not an exception, it is considered as a violation.
     * <p/>
     * Note that
     * {@link CheckStyleTester.AuditListenerAsserter#addError(AuditEvent)}
     * even in case of an exception.
     *
     * @param auditEvent the given event
     * @return <tt>true</tt> if it is an exception, otherwise <tt>false</tt>
     */
    private boolean isException(AuditEvent auditEvent) {
        return EXCEPTION_MESSAGE_KEY.equals(auditEvent.getLocalizedMessage().getKey());
    }

    /**
     * Asserts that one violation matches the given matcher.
     *
     * @param matcher the given matcher
     * @return <tt>true</tt> if there is a match, otherwise <tt>false</tt>
     */
    private boolean match(ViolationMatcher matcher) {
        for (AuditEvent violation : getViolations()) {
            if (matcher.match(violation)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return the violations under a String format
     */
    private String violationsToString() {
        return getCollectionAsString(getViolations(), new Function<AuditEvent, String>() {
            @Override
            public String apply(AuditEvent violation) {
                return "[[" + violation.getMessage() + "] on " + violation.getLine() + ":" + violation.getColumn() +
                    " with severity " + violation.getSeverityLevel() + "]";
            }
        });
    }

    /**
     * @return the exceptions under a String format
     */
    private String exceptionsToString() {
        return getCollectionAsString(getExceptions(), new Function<AuditEvent, String>() {
            @Override
            public String apply(AuditEvent exception) {
                return "[" + exception.getMessage() + "] ";
            }
        });
    }

    /**
     * Returns a comma separated view of the specified {@link List} using the specified {@link Function}?
     *
     * @param list the list
     * @param toString the function returning the toString of a list element
     * @param <E> the element type
     * @return a comma separated view of the list
     */
    private <E> String getCollectionAsString(List<E> list, Function<E, String> toString) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            final E element = list.get(i);
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(toString.apply(element));
        }
        return builder.toString();
    }

}
