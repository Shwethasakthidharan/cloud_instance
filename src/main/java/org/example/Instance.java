package org.example;

import lombok.Data;

@Data
public class Instance {
    private String instanceType;
    private String provider;
    private String location;
    private String operatingSystem;
    private double vcpu;
    private double memory; // in GB
    private String storage;
    private String marketOption;
    private double price;

    // debugging
    @Override
    public String toString() {
        return String.format(
                "%s: %d vCPUs, %.0fGB RAM, %s, %s (%s)",
                instanceType, (int)vcpu, memory, operatingSystem, location, marketOption, price);
    }
}
