package hospitalsystem;

class Bed {
    int num;
    boolean isFree;
    Patient p;
    String deptName;
    int roomNum;

    public Bed(int num) {
        this.num = num;
        this.isFree = true;
        this.p = null;
    }

    public boolean free() {
        return isFree;
    }

    public void giveBed(Patient patient, String dept, int rNum) {
        this.p = patient;
        this.isFree = false;
        this.deptName = dept;
        this.roomNum = rNum;
        patient.roomNum = rNum;
        patient.bedNum = num;
    }
}