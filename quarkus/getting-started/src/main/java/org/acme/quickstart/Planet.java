package org.acme.quickstart;

public class Planet {
    
    private String name;
    private int diamter;
    private Climate climate;

    public Planet() {
    }

    public Planet(String name, int diamter, Climate climate) {
        this.name = name;
        this.diamter = diamter;
        this.climate = climate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDiamter() {
        return diamter;
    }

    public void setDiamter(int diamter) {
        this.diamter = diamter;
    }

    public Climate getClimate() {
        return climate;
    }

    public void setClimate(Climate climate) {
        this.climate = climate;
    }
}
