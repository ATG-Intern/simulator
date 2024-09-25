package com.inha.university.bems.simulation;

import com.inha.university.bems.building.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BEMSSimulation {
    private Building building;
    private LocalDateTime currentTime;
    private Random random;
    private ScheduledExecutorService scheduler;

    public BEMSSimulation(Building building) {
        this.building = building;
        this.currentTime = LocalDateTime.now();
        this.random = new Random();
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    public void startSimulation() {
        scheduler.scheduleAtFixedRate(this::simulationStep, 0, 10, TimeUnit.SECONDS);
    }

    public void stopSimulation() {
        scheduler.shutdown();
    }

    private void simulationStep() {
        currentTime = currentTime.plusMinutes(1);
        updateSensorReadings();
        updateEquipmentStatus();
        calculateEnergyConsumption();
        logSimulationState();
    }

    private void updateSensorReadings() {
        for (Floor floor : building.getFloors()) {
            for (Sensor sensor : floor.getSensors()) {
                if (sensor instanceof TemperatureSensor) {
                    updateTemperatureSensor((TemperatureSensor) sensor);
                } else if (sensor instanceof HumiditySensor) {
                    updateHumiditySensor((HumiditySensor) sensor);
                } else if (sensor instanceof LightSensor) {
                    updateLightSensor((LightSensor) sensor);
                } else if (sensor instanceof PowerMeter) {
                    updatePowerMeter((PowerMeter) sensor);
                } else if (sensor instanceof OccupancySensor) {
                    updateOccupancySensor((OccupancySensor) sensor);
                }
            }
        }
    }

    private void updateTemperatureSensor(TemperatureSensor sensor) {
        double baseTemp = 22.0;
        double timeVariation = Math.sin(currentTime.getHour() * Math.PI / 12) * 2;
        double randomVariation = (random.nextDouble() - 0.5) * 0.5;
        double newTemp = baseTemp + timeVariation + randomVariation;
        sensor.setCurrentReading(new SensorReading(sensor.getId(), currentTime, newTemp));
    }

    private void updateHumiditySensor(HumiditySensor sensor) {
        double baseHumidity = 50.0;
        double randomVariation = (random.nextDouble() - 0.5) * 5;
        double newHumidity = Math.max(30, Math.min(70, baseHumidity + randomVariation));
        sensor.setCurrentReading(new SensorReading(sensor.getId(), currentTime, newHumidity));
    }

    private void updateLightSensor(LightSensor sensor) {
        double baseLux = isWorkingHours() ? 500 : 50;
        double randomVariation = (random.nextDouble() - 0.5) * 50;
        double newLux = Math.max(0, baseLux + randomVariation);
        sensor.setCurrentReading(new SensorReading(sensor.getId(), currentTime, newLux));
    }

    private void updatePowerMeter(PowerMeter sensor) {
        double basePower = isWorkingHours() ? 5000 : 1000;
        double randomVariation = (random.nextDouble() - 0.5) * 500;
        double newPower = Math.max(0, basePower + randomVariation);
        sensor.setCurrentReading(new SensorReading(sensor.getId(), currentTime, newPower));
    }

    private void updateOccupancySensor(OccupancySensor sensor) {
        boolean isOccupied = isWorkingHours() && random.nextDouble() < 0.8;
        sensor.setCurrentReading(new SensorReading(sensor.getId(), currentTime, isOccupied ? 1.0 : 0.0));
    }

    private boolean isWorkingHours() {
        LocalTime time = currentTime.toLocalTime();
        return time.isAfter(LocalTime.of(8, 0)) && time.isBefore(LocalTime.of(18, 0));
    }

    private void updateEquipmentStatus() {
        for (Equipment equipment : building.getEquipment()) {
            if (equipment instanceof HVACSystem) {
                updateHVACSystem((HVACSystem) equipment);
            } else if (equipment instanceof LightingSystem) {
                updateLightingSystem((LightingSystem) equipment);
            } else if (equipment instanceof Elevator) {
                updateElevator((Elevator) equipment);
            }
        }
    }

    private void updateHVACSystem(HVACSystem hvac) {
        if (isWorkingHours()) {
            hvac.turnOn();
            double targetTemp = 22.0;
            double currentTemp = getAverageTemperature();
            if (Math.abs(currentTemp - targetTemp) > 0.5) {
                hvac.setTemperature(targetTemp > currentTemp ? targetTemp + 1 : targetTemp - 1);
            } else {
                hvac.setTemperature(targetTemp);
            }
        } else {
            hvac.turnOff();
        }
    }

    private void updateLightingSystem(LightingSystem lighting) {
        if (isWorkingHours()) {
            lighting.turnOn();
            double avgLux = getAverageLightLevel();
            int newBrightness = avgLux < 400 ? 100 : avgLux < 500 ? 75 : 50;
            lighting.setBrightness(newBrightness);
        } else {
            lighting.turnOff();
        }
    }

    private void updateElevator(Elevator elevator) {
        if (isWorkingHours()) {
            elevator.turnOn();
            int newFloor = random.nextInt(building.getFloors().size()) + 1;
            elevator.moveToFloor(newFloor);
        } else {
            elevator.turnOff();
        }
    }

    private double getAverageTemperature() {
        return building.getFloors().stream()
                .flatMap(floor -> floor.getSensors().stream())
                .filter(sensor -> sensor instanceof TemperatureSensor)
                .mapToDouble(sensor -> ((TemperatureSensor) sensor).getCurrentReading().getValue())
                .average()
                .orElse(22.0);
    }

    private double getAverageLightLevel() {
        return building.getFloors().stream()
                .flatMap(floor -> floor.getSensors().stream())
                .filter(sensor -> sensor instanceof LightSensor)
                .mapToDouble(sensor -> ((LightSensor) sensor).getCurrentReading().getValue())
                .average()
                .orElse(500.0);
    }

    private void calculateEnergyConsumption() {
        double totalConsumption = building.getEquipment().stream()
                .mapToDouble(Equipment::getCurrentConsumption)
                .sum();
        System.out.printf("Total energy consumption at %s: %.2f kWh%n",
                currentTime, totalConsumption / 1000);
    }

    private void logSimulationState() {
        System.out.println("Simulation state at " + currentTime);
        for (Floor floor : building.getFloors()) {
            System.out.println("Floor " + floor.getFloorNumber());
            for (Sensor sensor : floor.getSensors()) {
                SensorReading reading = sensor.getCurrentReading();
                System.out.printf("  %s: %.2f%n", sensor.getId(), reading.getValue());
            }
        }
        for (Equipment equipment : building.getEquipment()) {
            System.out.printf("%s: %s, Consumption: %.2f W%n",
                    equipment.getName(), equipment.isRunning() ? "ON" : "OFF",
                    equipment.getCurrentConsumption());
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Building building = createSampleBuilding();
        BEMSSimulation simulation = new BEMSSimulation(building);
        simulation.startSimulation();

        // Run simulation for 24 hours
        try {
            Thread.sleep(TimeUnit.HOURS.toMillis(24));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        simulation.stopSimulation();
    }

    private static Building createSampleBuilding() {
        Building building = new Building();

        for (int i = 1; i <= 5; i++) {
            Floor floor = new Floor(i);
            floor.addSpace(new Space("Office " + i, 600, SpaceType.OFFICE));
            floor.addSpace(new Space("Meeting Room " + i, 200, SpaceType.MEETING_ROOM));
            floor.addSpace(new Space("Lounge " + i, 100, SpaceType.LOUNGE));

            floor.addSensor(new TemperatureSensor("TEMP" + i, "Floor " + i));
            floor.addSensor(new HumiditySensor("HUM" + i, "Floor " + i));
            floor.addSensor(new LightSensor("LIGHT" + i, "Floor " + i));
            floor.addSensor(new PowerMeter("POWER" + i, "Floor " + i));
            floor.addSensor(new OccupancySensor("OCC" + i, "Floor " + i));

            building.addFloor(floor);
        }

        building.addEquipment(new HVACSystem("HVAC1", "Central HVAC"));
        building.addEquipment(new LightingSystem("LIGHT1", "Central Lighting"));
        building.addEquipment(new Elevator("ELEV1", "Elevator 1"));

        return building;
    }
}
