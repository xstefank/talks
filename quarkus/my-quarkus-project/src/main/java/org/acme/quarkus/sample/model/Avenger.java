package org.acme.quarkus.sample.model;

public class Avenger {
    
    private String name;
    private String civilName;
    private boolean alive;
    private boolean originalAvenger;

    public Avenger(String name, String civilName, boolean alive, boolean originalAvenger) {
        this.name = name;
        this.civilName = civilName;
        this.alive = alive;
        this.originalAvenger = originalAvenger;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCivilName() {
        return civilName;
    }

    public void setCivilName(String civilName) {
        this.civilName = civilName;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isOriginalAvenger() {
        return originalAvenger;
    }

    public void setOriginalAvenger(boolean originalAvenger) {
        this.originalAvenger = originalAvenger;
    }
}
