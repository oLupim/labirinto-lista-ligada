package org.example.models;

public class Sala {
    public int salaNumber;
    public TypeSala salaType;
    public String salaDescription;
    public Sala next;
    public Sala previous;

    public Sala(int salaNumber, TypeSala salaType, String salaDescription) {
        this.salaNumber =salaNumber;
        this.salaType = salaType;
        this.salaDescription = salaDescription;
        this.next = null;
        this.previous = null;
    }

    public int getSalaNumber() {
        return salaNumber;
    }

    public TypeSala getSalaType() {
        return salaType;
    }

    public String getSalaDescription() {
        return salaDescription;
    }

};