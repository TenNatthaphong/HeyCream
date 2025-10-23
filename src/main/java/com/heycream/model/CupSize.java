/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heycream.model;

/**
 *
 * @author lenovo
 */
public enum CupSize {
    Small("S"),
    Medium("M"),
    Large("L"),
    Cone("C");
    
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
