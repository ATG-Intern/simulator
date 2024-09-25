package com.inha.university.bems.building;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public// 조명 시스템
class LightingSystem extends Equipment {
    private int brightness;

    public LightingSystem(String id, String name) {
        super(id, name);
        this.brightness = 100; // 기본 밝기 (%)
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
        return this.isRunning ? (this.brightness * 2.0) : 0.0; // 예시 값 (와트)
    }

    public void setBrightness(int brightness) {
        this.brightness = Math.max(0, Math.min(100, brightness));
    }
}
