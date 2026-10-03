package project2;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class HospitalAppointmentManagementSystem {

    Scanner sc = new Scanner(System.in);

    // Store patients
    Map<Integer, Patient> patients = new HashMap<>();

    // Store doctors
    Map<Integer, Doctor> doctors = new HashMap<>();

    // Store appointments
    Map<String, Appointment> appointments = new HashMap<>();

    int appointmentId = 1001;


    // REGISTER PATIENT 

    void registerPatient() {

        try {

            System.out.println("\n===== REGISTER PATIENT =====");

            System.out.print("Enter Patient ID: ");
            int patientId = sc.nextInt();
            sc.nextLine();

            if (patients.containsKey(patientId)) {
                throw new Exception("Patient ID already exists.");
            }

            System.out.print("Enter Patient Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Age: ");
            int age = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Gender: ");
            String gender = sc.nextLine();

            System.out.print("Enter Phone Number: ");
            String phone = sc.nextLine();

            Patient patient =
                    new Patient(patientId, name, age, gender, phone);

            patients.put(patientId, patient);

            System.out.println("Patient registered successfully.");

        } catch (Exception e) {

            System.out.println("Error: " + e.getMessage());

            sc.nextLine();
        }
    }


    // ADD DOCTOR 

    void addDoctor() {

        try {

            System.out.println("\n===== ADD DOCTOR =====");

            System.out.print("Enter Doctor ID: ");
            int doctorId = sc.nextInt();
            sc.nextLine();

            if (doctors.containsKey(doctorId)) {
                throw new Exception("Doctor ID already exists.");
            }

            System.out.print("Enter Doctor Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Specialization: ");
            String specialization = sc.nextLine();

            Doctor doctor =
                    new Doctor(doctorId, name, specialization);

            doctors.put(doctorId, doctor);

            System.out.println("Doctor added successfully.");

        } catch (Exception e) {

            System.out.println("Error: " + e.getMessage());

            sc.nextLine();
        }
    }


    // BOOK APPOINTMENT

    void bookAppointment() {

        try {

            System.out.println("\n===== BOOK APPOINTMENT =====");

            System.out.print("Enter Patient ID: ");
            int patientId = sc.nextInt();

            if (!patients.containsKey(patientId)) {
                throw new Exception("Patient not found.");
            }

            System.out.print("Enter Doctor ID: ");
            int doctorId = sc.nextInt();

            if (!doctors.containsKey(doctorId)) {
                throw new Exception("Doctor not found.");
            }

            sc.nextLine();

            System.out.print("Enter Date (yyyy-MM-dd): ");
            String dateInput = sc.nextLine();

            LocalDate date = LocalDate.parse(dateInput);

            System.out.println("\nAvailable Time Slots:");

            Doctor doctor = doctors.get(doctorId);

            for (LocalTime slot : doctor.slots) {

                String key =
                        doctorId + "_" + date + "_" + slot;

                if (!appointments.containsKey(key)) {

                    System.out.println(slot);
                }
            }

            System.out.print("\nEnter Time (HH:mm): ");
            String timeInput = sc.nextLine();

            LocalTime time = LocalTime.parse(timeInput);

            // Check whether doctor has this slot
            if (!doctor.slots.contains(time)) {

                throw new Exception(
                        "Invalid time slot. Please select an available slot.");
            }

            // Create unique appointment key
            String key =
                    doctorId + "_" + date + "_" + time;

            // Check whether slot already booked
            if (appointments.containsKey(key)) {

                throw new Exception(
                        "This time slot is already booked.");
            }

            Patient patient = patients.get(patientId);

            Appointment appointment =
                    new Appointment(
                            appointmentId,
                            patient,
                            doctor,
                            date,
                            time
                    );

            appointments.put(key, appointment);

            System.out.println("\nAppointment booked successfully.");
            System.out.println("Appointment ID : " + appointmentId);

            appointmentId++;

        } catch (Exception e) {

            System.out.println("Error: " + e.getMessage());

            sc.nextLine();
        }
    }


    // CANCEL APPOINTMENT

    void cancelAppointment() {

        try {

            System.out.println("\n===== CANCEL APPOINTMENT =====");

            System.out.print("Enter Appointment ID: ");
            int id = sc.nextInt();

            String foundKey = null;

            for (Map.Entry<String, Appointment> entry
                    : appointments.entrySet()) {

                if (entry.getValue().appointmentId == id) {

                    foundKey = entry.getKey();
                    break;
                }
            }

            if (foundKey == null) {

                throw new Exception(
                        "Appointment not found.");
            }

            appointments.remove(foundKey);

            System.out.println(
                    "Appointment cancelled successfully.");

        } catch (Exception e) {

            System.out.println("Error: " + e.getMessage());

            sc.nextLine();
        }
    }


    // VIEW AVAILABLE SLOTS

    void viewAvailableSlots() {

        try {

            System.out.println("\n===== AVAILABLE SLOTS =====");

            System.out.print("Enter Doctor ID: ");
            int doctorId = sc.nextInt();

            if (!doctors.containsKey(doctorId)) {

                throw new Exception(
                        "Doctor not found.");
            }

            sc.nextLine();

            System.out.print("Enter Date (yyyy-MM-dd): ");
            String dateInput = sc.nextLine();

            LocalDate date =
                    LocalDate.parse(dateInput);

            Doctor doctor =
                    doctors.get(doctorId);

            System.out.println(
                    "\nAvailable slots for " +
                            doctor.name);

            boolean available = false;

            for (LocalTime slot : doctor.slots) {

                String key =
                        doctorId + "_" + date + "_" + slot;

                if (!appointments.containsKey(key)) {

                    System.out.println(slot);

                    available = true;
                }
            }

            if (!available) {

                System.out.println(
                        "No slots available.");
            }

        } catch (Exception e) {

            System.out.println("Error: " + e.getMessage());

            sc.nextLine();
        }
    }


    // VIEW DOCTOR SCHEDULE

    void viewDoctorSchedule() {

        try {

            System.out.println("\n===== DOCTOR SCHEDULE =====");

            System.out.print("Enter Doctor ID: ");
            int doctorId = sc.nextInt();

            if (!doctors.containsKey(doctorId)) {

                throw new Exception(
                        "Doctor not found.");
            }

            sc.nextLine();

            System.out.print("Enter Date (yyyy-MM-dd): ");
            String dateInput = sc.nextLine();

            LocalDate date =
                    LocalDate.parse(dateInput);

            Doctor doctor =
                    doctors.get(doctorId);

            System.out.println(
                    "\nSchedule of Dr. " +
                            doctor.name);

            boolean found = false;

            for (Appointment appointment
                    : appointments.values()) {

                if (appointment.doctor.doctorId == doctorId
                        && appointment.date.equals(date)) {

                    appointment.displayAppointment();

                    found = true;
                }
            }

            if (!found) {

                System.out.println(
                        "No appointments for this date.");
            }

        } catch (Exception e) {

            System.out.println("Error: " + e.getMessage());

            sc.nextLine();
        }
    }


    // VIEW PATIENT HISTORY

    void viewPatientHistory() {

        try {

            System.out.println("\n===== PATIENT HISTORY =====");

            System.out.print("Enter Patient ID: ");
            int patientId = sc.nextInt();

            if (!patients.containsKey(patientId)) {

                throw new Exception(
                        "Patient not found.");
            }

            Patient patient =
                    patients.get(patientId);

            System.out.println("\nPatient Details");
            System.out.println("================");

            patient.displayPatient();

            System.out.println("\nAppointment History");

            boolean found = false;

            for (Appointment appointment
                    : appointments.values()) {

                if (appointment.patient.patientId == patientId) {

                    appointment.displayAppointment();

                    found = true;
                }
            }

            if (!found) {

                System.out.println(
                        "No appointment history.");
            }

        } catch (Exception e) {

            System.out.println("Error: " + e.getMessage());

            sc.nextLine();
        }
    }


    // VIEW ALL PATIENTS

    void viewPatients() {

        System.out.println("\n===== ALL PATIENTS =====");

        if (patients.isEmpty()) {

            System.out.println("No patients registered.");

            return;
        }

        for (Patient patient : patients.values()) {

            patient.displayPatient();

            System.out.println("----------------------");
        }
    }


    // VIEW ALL DOCTORS 

    void viewDoctors() {

        System.out.println("\n===== ALL DOCTORS =====");

        if (doctors.isEmpty()) {

            System.out.println("No doctors available.");

            return;
        }

        for (Doctor doctor : doctors.values()) {

            doctor.displayDoctor();

            System.out.println("Available Slots:");

            for (LocalTime slot : doctor.slots) {

                System.out.println(slot);
            }

            System.out.println("----------------------");
        }
    }


    //  MAIN MENU 

    void menu() {

        while (true) {

            System.out.println("\n");
            System.out.println("======================================");
            System.out.println(" HOSPITAL APPOINTMENT MANAGEMENT");
            System.out.println("======================================");

            System.out.println("1. Register Patient");
            System.out.println("2. Add Doctor");
            System.out.println("3. Book Appointment");
            System.out.println("4. Cancel Appointment");
            System.out.println("5. View Available Slots");
            System.out.println("6. View Doctor Schedule");
            System.out.println("7. View Patient History");
            System.out.println("8. View All Patients");
            System.out.println("9. View All Doctors");
            System.out.println("10. Exit");

            System.out.print("\nEnter your choice: ");

            try {

                int choice = sc.nextInt();

                switch (choice) {

                    case 1:
                        registerPatient();
                        break;

                    case 2:
                        addDoctor();
                        break;

                    case 3:
                        bookAppointment();
                        break;

                    case 4:
                        cancelAppointment();
                        break;

                    case 5:
                        viewAvailableSlots();
                        break;

                    case 6:
                        viewDoctorSchedule();
                        break;

                    case 7:
                        viewPatientHistory();
                        break;

                    case 8:
                        viewPatients();
                        break;

                    case 9:
                        viewDoctors();
                        break;

                    case 10:

                        System.out.println(
                                "Thank you for using Hospital Appointment System.");

                        return;

                    default:

                        System.out.println(
                                "Invalid choice. Please try again.");
                }

            } catch (Exception e) {

                System.out.println(
                        "Please enter a valid number.");

                sc.nextLine();
            }
        }
    }


    public static void main(String[] args) {

        HospitalAppointmentManagementSystem system =
                new HospitalAppointmentManagementSystem();

        system.menu();
    }
}
