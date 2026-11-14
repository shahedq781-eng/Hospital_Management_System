package hospitalsystem;

import java.time.LocalDateTime;

public class HospitalSystem {
    public static void main(String[] args) {
        Hospital hospital = new Hospital();
        LocalDateTime simTime = LocalDateTime.of(2026, 5, 20, 9, 0);

        System.out.println("=== 🏥 1. تشغيل وتجربة قسم الطوارئ والإسعاف والتحاليل (شغلكِ) ===");
        hospital.admitPatient("101", "أحمد علي", 3, true, "O+");
        hospital.admitPatient("102", "سارة حسن", 4, false, "B+");

        System.out.println("\n=== 🔄 2. تجربة نقل المرضى وجولات الأقسام الداخلية (شغل رفيقتك الأولى) ===");
        Department internal = new Department("Internal", 2, 2);
        LsbManager lab = new LsbManager(2);
        internal.setLsb(lab);
        hospital.addDept(internal);
        
        hospital.transferFromEmergencyToDept("101", "Internal");
        hospital.round("Internal", "Updated Antibiotics");

        System.out.println("\n=== ⚔️ 3. تجربة جدولة غرف العمليات وبنك الدم (شغل رفيقك الثاني) ===");
        Operation op1 = new Operation("أحمد علي", LocalDateTime.of(2026, 5, 20, 10, 0), 120, 3, false);  
        hospital.scheduleOperation(op1);
        
        // نداء طارئ جراحي يزيح المريض العادي ويؤجله تلقائياً
        Operation emergency = new Operation("حالة إسعافية لـ لين", LocalDateTime.of(2026, 5, 20, 10, 30), 60, 1, true);  
        hospital.addEmergencyOperation(emergency);

        // إدارة أكياس الدم
        hospital.addBloodBag("BAG-01", "O+", simTime.minusDays(1));
        hospital.retrieveBloodBags("O+", 1, simTime);

        System.out.println("\n=== 📄 4. جرد السجل الطبي التراكمي النهائي لأحمد بعد الرحلة ===");
        hospital.printReport("101");
    }
}