package com.heycream.model;

/**
 * CupType represents the container type (CUP or CONE).
 */
public enum CupType {
    CUP,
    CONE;

    /** Return type as a readable string. */
    public String typeToString() {
        return switch (this) {
            case CUP -> "Cup";
            case CONE -> "Cone";
        };
    }
}
