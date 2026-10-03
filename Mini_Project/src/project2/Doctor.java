package project2;

import java.time.LocalTime;
import java.util.ArrayList;

class Doctor {

    int doctorId;
    String name;
    String specialization;

    ArrayList<LocalTime> slots;

    Doctor(int doctorId, String name, String specialization) {
        this.doctorId = doctorId;
        this.name = name;
        this.specialization = specialization;

        slots = new ArrayList<>();

        // Default time slots
        slots.add(LocalTime.of(10, 0));
        slots.add(LocalTime.of(11, 0));
        slots.add(LocalTime.of(12, 0));
        slots.add(LocalTime.of(14, 0));
        slots.add(LocalTime.of(15, 0));
        slots.add(LocalTime.of(16, 0));
    }

    void displayDoctor() {
        System.out.println("Doctor ID      : " + doctorId);
        System.out.println("Doctor Name    : " + name);
        System.out.println("Specialization : " + specialization);
    }
}
