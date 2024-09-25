package com.inha.university.bems.building;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public// 공간을 나타내는 클래스
class Space {
    private String name;
    private double area;
    private SpaceType type;

    public Space(String name, double area, SpaceType type) {
        this.name = name;
        this.area = area;
        this.type = type;
    }
}
