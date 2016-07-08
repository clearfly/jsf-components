package com.outjected.jsf.core;

import java.util.List;

/**
 * 
 * @author Cody Lerum
 * 
 */
public class Notices {

    private List<String> errors;
    private List<String> warnings;

    public Notices() {
    }

    public Notices(List<String> errors, List<String> warnings) {
        this.errors = errors;
        this.warnings = warnings;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }

    public boolean containsErrors() {
        return errors != null && !errors.isEmpty();
    }

    public boolean containsWarnings() {
        return warnings != null && !warnings.isEmpty();
    }

    public boolean isContainsNotices() {
        return containsErrors() || containsWarnings();
    }
}
