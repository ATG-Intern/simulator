package com.inha.university.bems.building;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public// 전력 미터
class PowerMeter extends Sensor {
    private SensorReading currentReading;
    public PowerMeter(String id, String location) {
        super(id, location);
    }
}
