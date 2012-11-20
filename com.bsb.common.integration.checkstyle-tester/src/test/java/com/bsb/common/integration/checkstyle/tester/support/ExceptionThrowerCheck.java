package com.bsb.common.integration.checkstyle.tester.support;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * @author Sebastien Gerard
 */
public class ExceptionThrowerCheck extends Check {

    public static final String SUFFIX = "WithException";
    public static final String MESSAGE = "This exception is expected";

    @Override
    public int[] getDefaultTokens() {
        return new int[]{TokenTypes.CLASS_DEF};
    }

    @Override
    public void visitToken(DetailAST aAST) {
        final DetailAST classNameNode = aAST.findFirstToken(TokenTypes.IDENT);

        if (classNameNode.getText().endsWith(SUFFIX)) {
            throw new RuntimeException(MESSAGE);
        }
    }

}
