package hospitalsystem;

import java.time.LocalDateTime;
import java.time.Duration;

class Operation {
    String patientName;
    LocalDateTime startTime;
    LocalDateTime endTime;
    int urgency; 
    boolean isEmergency;

    public Operation(String patientName, LocalDateTime startTime, int durationMinutes, int urgency, boolean isEmergency) {  
        this.patientName = patientName;  
        this.startTime = startTime;  
        this.endTime = startTime.plusMinutes(durationMinutes);  
        this.urgency = urgency;  
        this.isEmergency = isEmergency;  
    }  

    public int getDurationMinutes() {  
        return (int) Duration.between(startTime, endTime).toMinutes();  
    }  

    @Override  
    public String toString() {  
        return String.format("المريض: %s | البدء: %s | الانتهاء: %s | طوارئ: %b", patientName, startTime.toLocalTime(), endTime.toLocalTime(), isEmergency);  
    }
}

class BloodBag {
    String bagId;
    String bloodType;
    LocalDateTime expiryDate;

    public BloodBag(String bagId, String bloodType, LocalDateTime donationDate) {  
        this.bagId = bagId;  
        this.bloodType = bloodType;  
        this.expiryDate = donationDate.plusDays(5); 
    }  

    @Override  
    public String toString() {  
        return String.format("كيس [%s] | زمرة: %s | انتهاء: %s", bagId, bloodType, expiryDate.toLocalDate());  
    }
}