package hospitalsystem;

public class LabOrder {
    String orderId;
    String patientId;
    String bloodType;
    String status; 

    public LabOrder(String orderId, String patientId, String bloodType) {
        this.orderId = orderId;
        this.patientId = patientId;
        this.bloodType = bloodType;
        this.status = "Pending";
    }
}