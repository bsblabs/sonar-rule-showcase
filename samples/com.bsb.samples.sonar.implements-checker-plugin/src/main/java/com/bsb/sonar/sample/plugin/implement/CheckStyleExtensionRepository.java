package com.bsb.sonar.sample.plugin.implement;

import org.apache.commons.io.IOUtils;
import org.sonar.api.resources.Java;
import org.sonar.api.rules.Rule;
import org.sonar.api.rules.RuleRepository;
import org.sonar.api.rules.XMLRuleParser;

import java.io.InputStream;
import java.util.List;

/**
 * @author Sebastien Gerard
 */
public class CheckStyleExtensionRepository extends RuleRepository {

    private XMLRuleParser xmlRuleParser;

    public CheckStyleExtensionRepository(XMLRuleParser xmlRuleParser) {
        super("checkstyle", Java.KEY);
        setName("Checkstyle");

        this.xmlRuleParser = xmlRuleParser;
    }

    @Override
    public List<Rule> createRules() {
        final InputStream input = CheckStyleExtensionRepository.class.getResourceAsStream(getFile());
        try {
            return xmlRuleParser.parse(input);
        } finally {
            IOUtils.closeQuietly(input);
        }
    }

    protected String getFile() {
        return "/checkstyle-extensions.xml";
    }

}