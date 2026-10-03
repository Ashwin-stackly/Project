package project2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

class Appointment {

    int appointmentId;
    Patient patient;
    Doctor doctor;

    LocalDate date;
    LocalTime time;

    LocalDateTime bookedAt;

    Appointment(int appointmentId, Patient patient, Doctor doctor,
                LocalDate date, LocalTime time) {

        this.appointmentId = appointmentId;
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.time = time;

        bookedAt = LocalDateTime.now();
    }

    void displayAppointment() {

        System.out.println("----------------------------------");
        System.out.println("Appointment ID : " + appointmentId);
        System.out.println("Patient ID     : " + patient.patientId);
        System.out.println("Patient Name   : " + patient.name);
        System.out.println("Doctor ID      : " + doctor.doctorId);
        System.out.println("Doctor Name    : " + doctor.name);
        System.out.println("Specialization : " + doctor.specialization);
        System.out.println("Date           : " + date);
        System.out.println("Time           : " + time);
        System.out.println("Booked At      : " + bookedAt);
        System.out.println("----------------------------------");
    }
}

