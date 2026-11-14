package hospitalsystem;

import java.util.LinkedList;

class Patient implements Comparable<Patient> {
    String id;
    String name;
    int priority; // 1: حرج (إسعافي)، 5: مستقر
    
    // متغيرات الأقسام الداخلية والمختبر (شغل رفيقتك)
    int severity; 
    int roomNum;
    int bedNum;
    String drugs = "";
    
    LinkedList<Record> history; 

    public Patient(String id, String name, int priority) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.severity = 6 - priority; 
        this.history = new LinkedList<>();
    }

    public void printRecord() {
        System.out.println("📜 التاريخ الطبي للمريض:");
        for (Record r : history) {
            System.out.println("- " + r.detail);
        }
    }

    public void addPrescription(String newDrugs) {
        this.drugs += (this.drugs.isEmpty() ? "" : ", ") + newDrugs;
        this.history.add(new Record("جولة الدكتور: تم صرف دواء: " + newDrugs));
    }
    
    public void addTestRequest(String testName) {
        this.history.add(new Record("جولة الدكتور: تم طلب تحليل مخبري: " + testName));
    }

    @Override
    public int compareTo(Patient other) {
        return Integer.compare(this.priority, other.priority);
    }
}