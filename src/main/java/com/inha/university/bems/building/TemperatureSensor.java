package com.inha.university.bems.building;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public// 온도 센서
class TemperatureSensor extends Sensor {
    private SensorReading currentReading;
    public TemperatureSensor(String id, String location) {
        super(id, location);
    }
}
