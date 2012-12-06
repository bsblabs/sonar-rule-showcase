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