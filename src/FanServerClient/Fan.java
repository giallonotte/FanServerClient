/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FanServerClient;

import java.io.Serializable;

public class Fan implements Serializable {

    /**
     * For serialization/deserialization to check class version.
     */
    private static final long serialVersionUID = -7621164129293024859L;

    private boolean isOn;
    private double power;

    private double duty;
    private double speed;

    private double minStartingTemp;
    private double minRunningTemp;

    private double minSpeed;
    private double maxSpeed;

    private double temperature;

	// Constructor
    //
    public Fan() {
        this(0.0, 0.0, 0.0, 70, 60, 0.0, 100.0, 70.0);
    }

    public Fan(double power, double duty, double speed,
            double minStartingTemp, double minRunningTemp,
            double minSpeed, double maxSpeed, double temperature) {
        super();

        this.power = power;
        this.duty = duty;
        this.minStartingTemp = minStartingTemp;
        this.minRunningTemp = minRunningTemp;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.temperature = temperature;

        setSpeed(speed);
    }

	// Turn Off
    //
    public void turnOff() {
        isOn = false;

        this.power = 0;
        this.duty = 0;
        setSpeed(0);
    }

	// Turn On
    //
    public void turnOn(double power, double duty, double speed) {
        isOn = true;

        this.power = power;
        this.duty = duty;
        setSpeed(speed);
    }

    public void turnOn(double power, double duty) {
        isOn = true;

        this.power = power;
        this.duty = duty;
        setSpeed(minSpeed);
    }

	// Getters & Setters
    //
    //  Is On?
    public boolean isOn() {
        return isOn;
    }

    // Power
    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    // Duty
    public double getDuty() {
        return duty;
    }

    public void setDuty(double duty) {
        this.duty = duty;
    }

    // Speed
    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        if (isOn && this.speed + speed > this.maxSpeed) {
            this.speed = this.maxSpeed;
        } else if (isOn && this.speed + speed < this.minSpeed) {
            this.speed = this.minSpeed;
        } else {
            this.speed = speed;
        }
    }

    // Temperature
    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    // Minimum Starting Temperature
    public double getMinStartingTemp() {
        return minStartingTemp;
    }

    public void setMinStartingTemp(double minStartingTemp) {
        this.minStartingTemp = minStartingTemp;
    }

    // Minimum Running Temperature
    public double getMinRunningTemp() {
        return minRunningTemp;
    }

    public void setMinRunningTemp(double minRunningTemp) {
        this.minRunningTemp = minRunningTemp;
    }

    // Minimum Speed
    public double getMinSpeed() {
        return minSpeed;
    }

    public void setMinSpeed(double minSpeed) {
        this.minSpeed = minSpeed;
    }

    // Maximum Speed
    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

}
