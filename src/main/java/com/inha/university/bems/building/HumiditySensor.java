package com.inha.university.bems.building;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public// 습도 센서
class HumiditySensor extends Sensor {
    private SensorReading currentReading;
    public HumiditySensor(String id, String location) {
        super(id, location);
    }
}
