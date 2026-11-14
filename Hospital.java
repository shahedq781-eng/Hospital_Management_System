package hospitalsystem;

import java.util.*;
import java.time.LocalDateTime;

// كلاس غرفة العمليات (شغل رفيقك الثالث)
class OperationRoom {
    int roomNumber;
    ArrayList<Operation> scheduledOperations = new ArrayList<>();  
    PriorityQueue<Operation> deferredQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.urgency));  

    public OperationRoom(int roomNumber) { 
        this.roomNumber = roomNumber; 
    }  

    public boolean isAvailable(LocalDateTime start, LocalDateTime end) {  
        for (Operation op : scheduledOperations) {  
            if (start.isBefore(op.endTime) && end.isAfter(op.startTime)) {
                return false; 
            }
        }  
        return true;  
    }  

    public void insertSorted(Operation newOp) {  
        int i = 0;  
        while (i < scheduledOperations.size() && scheduledOperations.get(i).startTime.isBefore(newOp.startTime)) {
            i++;  
        }
        scheduledOperations.add(i, newOp);  
    }
}

// =========================================================================
// كلاس المشفى المشترك والكبير
// =========================================================================
public class Hospital {
    
    // ⭐ كلاساتكِ ومتغيراتكِ الأساسية (رجعت لمكانها بالقمة)
    private Patient[] rooms = new Patient[10]; 
    private int count = 0;                     
    private PriorityQueue<Patient> waitQueue = new PriorityQueue<>(); 
    private HashMap<String, Patient> db = new HashMap<>();            
    private Queue<LabOrder> labQueue = new LinkedList<>();            
    private int orderCounter = 1;                                     

    // كلاسات الأقسام الداخلية (شغل رفيقتكِ)
    ArrayList<Department> depts = new ArrayList<>(); 

    // كلاسات العمليات وبنك الدم (شغل رفيقكِ)
    ArrayList<OperationRoom> opRooms = new ArrayList<>(); 
    HashMap<String, PriorityQueue<BloodBag>> bloodBank = new HashMap<>(); 
    HashMap<String, Queue<String>> bloodWaitingList = new HashMap<>();    

    // باني المستشفى لتجهيز غرف العمليات وبنك الدم
    public Hospital() {
        for (int i = 1; i <= 2; i++) {
            opRooms.add(new OperationRoom(i));  
        }
        
        String[] types = {"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};  
        for (String type : types) {  
            bloodBank.put(type, new PriorityQueue<>(Comparator.comparing(b -> b.expiryDate)));  
            bloodWaitingList.put(type, new LinkedList<>()); 
        }  
    }

    public void addDept(Department dept) { 
        depts.add(dept); 
    }
    
    public Department findDept(String name) {
        for (Department d : depts) {
            if (d.name.equalsIgnoreCase(name)) {
                return d;
            }
        }
        return null;
    }

    // =========================================================================
    // 📑 قسم الطوارئ والإسعاف والتحاليل (شغلكِ أنتِ - بنسخة الصورة المطابقة)
    // =========================================================================
    
    public void admitPatient(String id, String name, int priority, boolean needLab, String bloodType) {
        Patient p;
        if (db.containsKey(id) == true) {
            p = db.get(id);
            p.priority = priority; 
        } else {
            p = new Patient(id, name, priority); 
            db.put(id, p);                       
        }

        p.history.add(new Record("دخول الإسعاف - خطورة: " + priority + " | فصيلة: " + bloodType));

        if (count < 10) {
            for (int i = 0; i < 10; i++) {
                if (rooms[i] == null) { 
                    rooms[i] = p; 
                    count = count + 1; 
                    System.out.println("✔️ تم إدخال [" + name + "] لغرفة الإسعاف (" + (i + 1) + ")"); 
                    break; 
                }
            }
        } else { 
            waitQueue.add(p); 
            System.out.println("⚠️ غرف الإسعاف ممتلئة! تم وضع [" + name + "] في قائمة انتظار الطوارئ.");
        }

        if (needLab == true) { 
            requestLab(id, bloodType); 
        }
    }

    public void requestLab(String id, String bloodType) {
        String orderId = "Lab-" + (orderCounter++);
        LabOrder newOrder = new LabOrder(orderId, id, bloodType);
        labQueue.add(newOrder); 
        System.out.println("🧪 [قسم التحاليل] تم إنشاء كائن تحليل مخبري! رقم الطلب: " + orderId + " للمريض رقم " + id);
    }

    // دالة التخريج (طِبق الأصل ومطابقة مية بالمية للصورة والـ if المدمجة تبعك)
    public void dischargePatient(int roomNumber) {
        int index = roomNumber - 1;
        
        if (index >= 0 && index < 10 && rooms[index] != null) {
            Patient p = rooms[index];
            System.out.println("❌ تم تخريج [" + p.name + "] من الغرفة رقم (" + roomNumber + "):");
            
            p.history.add(new Record("تم التخريج من المشفى بنجاح"));
            rooms[index] = null;
            count--;
            
            if (!waitQueue.isEmpty()) {
                Patient next = waitQueue.poll();
                System.out.print("🔄 تحديث الطابور -> ");
                admitPatient(next.id, next.name, next.priority, false, "Unknown");
            }
        } else {
            System.out.println("⚠️ الغرفة رقم " + roomNumber + " فارغة بالفعل أو الرقم غير صحيح!");
        }
    }

    public void printReport(String id) {
        if (db.containsKey(id)) {
            Patient p = db.get(id);
            System.out.println("\n=========================================");
            System.out.println("📄 سجل المريض التراكمي المشترك: " + p.name + " | الرقم الطبي: " + p.id);
            System.out.println("=========================================");
            p.printRecord(); 
            System.out.println("=========================================\n");
        }
    }

    // =========================================================================
    // 🔄 جسر الربط مع رفيقتكِ (القسم الداخلي) والعمليات (رفيقكِ الثالث)
    // =========================================================================
    
    public void transferFromEmergencyToDept(String patientId, String deptName) {
        System.out.println("\n🔄 [طلب نقل داخلي] نقل المريض رقم (" + patientId + ") إلى قسم: " + deptName);
        if (db.containsKey(patientId) == false) return;
        
        Patient p = db.get(patientId);
        Department targetDept = findDept(deptName); 
        
        if (targetDept != null && targetDept.addPatient(p)) {
            for (int i = 0; i < 10; i++) {
                if (rooms[i] != null && rooms[i].id.equals(patientId)) { 
                    rooms[i] = null; 
                    count--; 
                    break; 
                }
            }
            p.history.add(new Record("تم النقل بنجاح إلى القسم الداخلي: " + deptName));
            if (!waitQueue.isEmpty()) {
                Patient next = waitQueue.poll();
                admitPatient(next.id, next.name, next.priority, false, "Unknown");
            }
        }
    }

    public boolean scheduleOperation(Operation op) {  
        for (OperationRoom room : opRooms) {  
            if (room.isAvailable(op.startTime, op.endTime)) {  
                room.insertSorted(op);   
                System.out.println("-> [جدولة] تم حجز عملية المريض " + op.patientName + " في الغرفة " + room.roomNumber);  
                return true;  
            }  
        }  
        return false;  
    }  

    public void addEmergencyOperation(Operation emergencyOp) {  
        System.out.println("\n[نداء طوارئ جراحي] وصول حالة إسعافية حرجة للمريض: " + emergencyOp.patientName);  
        if (scheduleOperation(emergencyOp)) return;  
        
        for (OperationRoom room : opRooms) {  
            for (int i = 0; i < room.scheduledOperations.size(); i++) {  
                Operation currentOp = room.scheduledOperations.get(i);  
                if (!currentOp.isEmergency && emergencyOp.startTime.isBefore(currentOp.endTime) && emergencyOp.endTime.isAfter(currentOp.startTime)) {  
                    room.scheduledOperations.remove(i);   
                    room.deferredQueue.add(currentOp);   
                    room.insertSorted(emergencyOp);  
                    System.out.println("!! [تأجيل جراحي] تأجيل عملية " + currentOp.patientName + " لصالح الحالة الطارئة.");  
                    return;  
                }  
            }  
        }  
    }  

    public void addBloodBag(String bagId, String bloodType, LocalDateTime donationDate) {  
        bloodBank.get(bloodType).add(new BloodBag(bagId, bloodType, donationDate));  
    }  

    public void removeExpiredBloodBags(LocalDateTime currentTime) {  
        for (String type : bloodBank.keySet()) {  
            PriorityQueue<BloodBag> pq = bloodBank.get(type);  
            while (!pq.isEmpty() && pq.peek().expiryDate.isBefore(currentTime)) {  
                BloodBag expired = pq.poll(); 
                System.out.println("«تنبيه صلاحية» تم إتلاف كيس الدم المنتهي: " + expired.bagId + " للزمرة " + type);  
            }  
        }  
    }  

    public void retrieveBloodBags(String bloodType, int amount, LocalDateTime currentTime) {  
        System.out.println("\n[طلب صرف دم] مطلوب عدد " + amount + " أكياس من الزمرة " + bloodType);  
        removeExpiredBloodBags(currentTime); 
        PriorityQueue<BloodBag> pq = bloodBank.get(bloodType);  
        while (amount > 0 && !pq.isEmpty()) {  
            BloodBag b = pq.poll();  
            System.out.println(" -> صرف كيس صالحة: " + b.bagId);  
            amount--;  
        }  
    }

    public void round(String deptName, String drugs) {
        Department d = findDept(deptName);
        if (d != null) d.doctorRound("10:00 AM", drugs, "CBC");
    }
}