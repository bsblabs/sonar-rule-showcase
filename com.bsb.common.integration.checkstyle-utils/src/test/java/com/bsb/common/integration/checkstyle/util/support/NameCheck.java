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
package com.bsb.common.integration.checkstyle.util.support;

import com.bsb.common.integration.checkstyle.util.AbstractCheckState;
import com.bsb.common.integration.checkstyle.util.AbstractSingleCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * @author Sebastien Gerard
 */
public class NameCheck extends AbstractSingleCheck {

    public static final String ERROR_MESSAGE = "This is an error message";
    public static final String SUFFIX = "WithViolation";

    private NameCheckState state;

    @Override
    protected void createState(DetailAST rootAST) {
        this.state = new NameCheckState();
    }

    @Override
    protected NameCheckState getState() {
        return state;
    }

    @Override
    public void visitToken(DetailAST aAST) {
        if (aAST.getType() != TokenTypes.CLASS_DEF) {
            throw new IllegalArgumentException("The node [" + aAST + "] must a class definition");
        }

        final DetailAST classNameNode = aAST.findFirstToken(TokenTypes.IDENT);

        getState().setViolationColumn(classNameNode.getColumnNo());
        getState().setViolationLine(classNameNode.getLineNo());
        getState().setName(classNameNode.getText());
    }

    @Override
    protected boolean isConstraintSatisfied() {
        return !getState().getName().endsWith(SUFFIX);
    }

    @Override
    protected String getViolationMessage() {
        return ERROR_MESSAGE;
    }

    @Override
    protected SeverityLevel getLevel() {
        return SeverityLevel.IGNORE;
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[]{TokenTypes.CLASS_DEF};
    }

    private final class NameCheckState extends AbstractCheckState {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public void checkAfterVisit() throws IllegalStateException {
            super.checkAfterVisit();

            if (getName() == null) {
                throw new IllegalStateException("The name must be set");
            }
        }
    }

}
