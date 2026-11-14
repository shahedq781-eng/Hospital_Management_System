package hospitalsystem;

import java.util.ArrayList;

public class Room {
    int num;
    ArrayList<Bed> beds = new ArrayList<>();

    public Room(int num, int bedCount) {
        this.num = num;
        for (int i = 1; i <= bedCount; i++) {
            beds.add(new Bed(i));
        }
    }

    public Bed findFreeBed() {
        for (Bed b : beds) {
            if (b.free()) return b;
        }
        return null;
    }
}