package com.inha.university.bems.building;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public// 재실 감지 센서
class OccupancySensor extends Sensor {
    private SensorReading currentReading;
    public OccupancySensor(String id, String location) {
        super(id, location);
    }
}

