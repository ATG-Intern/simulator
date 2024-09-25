package com.inha.university.bems.building;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

// 빌딩 전체를 나타내는 클래스
@Getter
@Setter
public class Building {
    private List<Floor> floors;
    private List<Equipment> equipment;

    public Building() {
        this.floors = new ArrayList<>();
        this.equipment = new ArrayList<>();
    }

    public void addFloor(Floor floor) {
        this.floors.add(floor);
    }

    public void addEquipment(Equipment equipment) {
        this.equipment.add(equipment);
    }
}

