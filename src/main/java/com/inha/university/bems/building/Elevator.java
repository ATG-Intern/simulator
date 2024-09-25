package com.inha.university.bems.building;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public// 엘리베이터
class Elevator extends Equipment {
    private int currentFloor;

    public Elevator(String id, String name) {
        super(id, name);
        this.currentFloor = 1;
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
        return this.isRunning ? 2000.0 : 100.0; // 예시 값 (와트)
    }

    public void moveToFloor(int floor) {
        this.currentFloor = floor;
    }
}
