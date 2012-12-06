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
package com.bsb.sonar.sample.rule.implement;

import com.bsb.common.integration.checkstyle.util.AbstractCheckState;
import com.bsb.common.integration.checkstyle.util.AbstractSingleCheck;
import com.bsb.common.integration.checkstyle.util.CheckStyleUtils;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * A single check that checks that a class does not implement more than 2 interfaces.
 * Inner classes are not scanned.
 *
 * @author Sebastien Gerard
 */
public class ImplementClausesCounterCheck extends AbstractSingleCheck {

    public static final String MESSAGE = "implements.count";
    public static final int MAX_IMPLEMENTS = 2;

    private ImplementClausesCounterState state;

    @Override
    protected void createState(DetailAST rootAST) {
        state = new ImplementClausesCounterState();
    }

    @Override
    protected ImplementClausesCounterState getState() {
        return state;
    }

    @Override
    public void visitToken(DetailAST classDef) {
        if (classDef.getParent() != null) {
            // inner class are not scanned
            return;
        }

        final DetailAST className = classDef.findFirstToken(TokenTypes.IDENT);
        if (className == null) {
            throw new IllegalStateException("The class name is not defined");
        }

        final DetailAST implementsClause = classDef.findFirstToken(TokenTypes.IMPLEMENTS_CLAUSE);
        if (implementsClause != null) {
            getState().setNumberImplements(CheckStyleUtils.getImplementedClauses(implementsClause).size());
            getState().setViolationLine(implementsClause.getLineNo());
            getState().setViolationColumn(implementsClause.getColumnNo());
        }
    }

    @Override
    protected boolean isConstraintSatisfied() {
        return getState().getNumberImplements() <= MAX_IMPLEMENTS;
    }

    @Override
    protected String getViolationMessage() {
        return MESSAGE;
    }

    @Override
    protected SeverityLevel getLevel() {
        return SeverityLevel.WARNING;
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[]{TokenTypes.CLASS_DEF};
    }

    private static final class ImplementClausesCounterState extends AbstractCheckState {

        private int numberImplements = 0;

        public int getNumberImplements() {
            return numberImplements;
        }

        public void setNumberImplements(int numberImplements) {
            this.numberImplements = numberImplements;
        }

    }

}
