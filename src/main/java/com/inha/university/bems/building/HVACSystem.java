package com.inha.university.bems.building;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public// HVAC 시스템
class HVACSystem extends Equipment {
    private double temperature;

    public HVACSystem(String id, String name) {
        super(id, name);
        this.temperature = 22.0; // 기본 설정 온도
    }

    @Override
    public void turnOn() {
        this.isRunning = true;
    }

    @Override
    public void turnOff() {
        this.isRunning = false;
    }

    @Override
    public double getCurrentConsumption() {
        return this.isRunning ? 5000.0 : 0.0; // 예시 값 (와트)
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
