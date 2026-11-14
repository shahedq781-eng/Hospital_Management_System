package hospitalsystem;

import java.util.ArrayList;
import java.util.PriorityQueue;

class LabDevice {
    int num;
    boolean busy;
    TestRequest current;
    int timeLeft;

    public LabDevice(int num) {
        this.num = num;
        this.busy = false;
    }

    public boolean free() { return !busy; }

    public void assign(TestRequest r) {
        busy = true;
        current = r;
        timeLeft = r.duration;
    }

    public void tick() {
        if (busy) {
            timeLeft--;
            if (timeLeft <= 0) {
                busy = false;
                current = null;
            }
        }
    }
}

class TestRequest implements Comparable<TestRequest> {
    String testName;
    Patient patient;
    int duration;
    int severity;
    long id;

    public TestRequest(String testName, Patient patient, int duration, int severity, long id) {
        this.testName = testName;
        this.patient = patient;
        this.duration = duration;
        this.severity = severity;
        this.id = id;
    }

    @Override
    public int compareTo(TestRequest other) {
        if (this.severity != other.severity) {
            return other.severity - this.severity; 
        }
        return Long.compare(this.id, other.id);
    }
}

public class LsbManager {   
    ArrayList<LabDevice> devices = new ArrayList<>();
    PriorityQueue<TestRequest> waiting = new PriorityQueue<>();
    long count = 0;

    public LsbManager(int k) {
        for (int i = 1; i <= k; i++) devices.add(new LabDevice(i));
    }

    public int getDuration(String testName) {
        if (testName.equalsIgnoreCase("CBC")) return 3;
        if (testName.equalsIgnoreCase("MRI")) return 5;
        return 2;
    }

    public void addRequest(String testName, Patient p) {
        TestRequest r = new TestRequest(testName, p, getDuration(testName), p.severity, count++);
        waiting.offer(r);
        assign();
    }

    public void assign() {
        for (LabDevice d : devices) {
            if (d.free() && !waiting.isEmpty()) d.assign(waiting.poll());
        }
    }

    public void advanceTime() {
        for (LabDevice d : devices) d.tick();
        assign();
    }

    public void printLab() {
        System.out.println("Lab status: Waiting requests: " + waiting.size());
    }
}