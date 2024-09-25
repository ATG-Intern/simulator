package com.inha.university.bems.building;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public// 센서 측정값을 나타내는 클래스
class SensorReading {
    private String sensorId;
    private LocalDateTime timestamp;
    private double value;

    public SensorReading(String sensorId, LocalDateTime timestamp, double value) {
        this.sensorId = sensorId;
        this.timestamp = timestamp;
        this.value = value;
    }
}
