package net.agten.heatersimulator.controller.dto;

public class FuzzyParameters {

    public double power;

    public FuzzyParameters(){
        this.power = 100.0;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

}
