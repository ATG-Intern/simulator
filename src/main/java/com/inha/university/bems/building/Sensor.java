package com.inha.university.bems.building;

import lombok.Getter;
import lombok.Setter;

// 센서를 나타내는 추상 클래스
@Getter
@Setter
public abstract class Sensor {
    protected String id;
    protected String location;

    public Sensor(String id, String location) {
        this.id = id;
        this.location = location;
    }

    public abstract SensorReading getCurrentReading();
}
