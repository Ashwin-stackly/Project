package project2;


class Patient {

    int patientId;
    String name;
    int age;
    String gender;
    String phone;

    Patient(int patientId, String name, int age, String gender, String phone) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
    }

    void displayPatient() {
        System.out.println("Patient ID : " + patientId);
        System.out.println("Name       : " + name);
        System.out.println("Age        : " + age);
        System.out.println("Gender     : " + gender);
        System.out.println("Phone      : " + phone);
    }
}
