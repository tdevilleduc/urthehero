package com.tdevilleduc.urthehero.back.config;

import org.togglz.core.Feature;
import org.togglz.core.annotation.Label;
import org.togglz.core.context.FeatureContext;

public enum Features implements Feature {

    @Label("Person Feature")
    PERSON_FEATURE,
    @Label("Page Feature")
    PAGE_FEATURE;

    public boolean isActive() {
        return FeatureContext.getFeatureManager().isActive(this);
    }
}