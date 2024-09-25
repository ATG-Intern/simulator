package com.inha.university.bems.building;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
// 각 층을 나타내는 클래스
public class Floor {
    private int floorNumber;
    private List<Space> spaces;
    private List<Sensor> sensors;

    public Floor(int floorNumber) {
        this.floorNumber = floorNumber;
        this.spaces = new ArrayList<>();
        this.sensors = new ArrayList<>();
    }

    public void addSpace(Space space) {
        this.spaces.add(space);
    }

    public void addSensor(Sensor sensor) {
        this.sensors.add(sensor);
    }
}
