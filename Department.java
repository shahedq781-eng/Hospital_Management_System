package hospitalsystem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

class Department {
    String name;
    ArrayList<Room> rooms = new ArrayList<>();
    ArrayList<Patient> patients = new ArrayList<>();
    Queue<Patient> waiting = new LinkedList<>();
    int roundsToday = 0; 
    LsbManager lab;

    public Department(String name, int roomCount, int bedCount) {
        this.name = name;
        for (int i = 1; i <= roomCount; i++) {
            rooms.add(new Room(i, bedCount));
        }
    }
    
    public void setLsb(LsbManager lab) {
        this.lab = lab;
    }

    public boolean addPatient(Patient patient) {
        for (Room r : rooms) {
            Bed b = r.findFreeBed();
            if (b != null) {
                b.giveBed(patient, name, r.num);
                patients.add(patient);
                return true;
            }
        }
        waiting.offer(patient);
        return false;
    }

    public Patient findPatient(String id) {
        for (Patient p : patients) {
            if (p.id.equals(id)) return p;
        }
        return null;
    }

    public void doctorRound(String time, String newDrugs, String testRequest) {
        roundsToday++;
        System.out.println("=== جولة " + time + " - قسم " + name + " ===");
        for (Patient p : patients) {
            if (newDrugs != null && !newDrugs.isEmpty()) p.addPrescription(newDrugs);
            if (testRequest != null && !testRequest.isEmpty() && lab != null) {
                p.addTestRequest(testRequest);
                lab.addRequest(testRequest, p);
            }
        }
    }

    public void printDept() {
        System.out.println("Department: " + name);
        for (Patient p : patients) {
            System.out.println(p.name + " | room " + p.roomNum + " | bed " + p.bedNum + " | drugs: " + p.drugs);
        }
    }
}