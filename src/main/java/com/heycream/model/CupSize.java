
package com.heycream.model;

public enum CupSize {
    Small("S"),
    Medium("M"),
    Large("L");
    
    private final String label;
    CupSize(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}
