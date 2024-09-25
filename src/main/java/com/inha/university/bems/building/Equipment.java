package com.inha.university.bems.building;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// 설비를 나타내는 추상 클래스
public abstract class Equipment {
    protected String id;
    protected String name;
    protected boolean isRunning;

    public Equipment(String id, String name) {
        this.id = id;
        this.name = name;
        this.isRunning = false;
    }

    public abstract void turnOn();
    public abstract void turnOff();
    public abstract double getCurrentConsumption();
}