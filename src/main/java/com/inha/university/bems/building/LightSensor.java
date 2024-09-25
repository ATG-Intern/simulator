package com.inha.university.bems.building;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public// 조도 센서
class LightSensor extends Sensor {
    private SensorReading currentReading;
    public LightSensor(String id, String location) {
        super(id, location);
    }

}
