package com.bsb.sonar.sample.plugin.implement;

import org.sonar.api.SonarPlugin;

import java.util.List;

import static java.util.Collections.singletonList;

/**
 * @author Sebastien Gerard
 */
public class CheckStyleExtensionPlugin extends SonarPlugin {

    @Override
    public List getExtensions() {
        return singletonList(CheckStyleExtensionRepository.class);
    }

}
