# Smart Hospital Management System 🏥

A comprehensive Java-based Smart Hospital Management System developed as part of the Algorithms and Data Structures course project. The system bridges academic concepts with real-world software engineering, implementing hybrid data structures to optimize emergency responses, resource allocation, and critical medical workflows.

## 🌟 Key Features & Sub-Systems

### 1. Reception & Emergency Flow
- **Priority Queue & HashMap Integration:** Manages emergency waiting lists to ensure critical patients are admitted instantly upon bed availability with $O(1)$ lookup time for patient records.
- **Cumulative Event Tracking:** Utilizes linked lists for sequential storage of patient medical histories without pre-allocation overhead.
- **Automated Lab Requests:** Instantly triggers and queues laboratory orders upon patient registration and identification.

### 2. Inpatient, Rounds & Resource Management
- **Dynamic Array Allocation:** Tracks internal departments and monitors real-time bed availability for smooth patient transfers.
- **Doctor Rounds & Medication Updates:** Enables linear traversal across active patients to update medication records and request additional diagnostics.
- **Limited Resource Tracking:** Manages shared hospital devices (like lab equipment) as finite resources using tracking arrays and time countdowns.

### 3. Surgical Operations & Blood Bank
- **Time-Sorted Surgery Scheduling:** Uses sorted dynamic arrays to prevent scheduling overlaps and manage chronological operations.
- **Emergency Preemption & Auto-Rescheduling:** Automatically detects critical emergencies, bumps conflicting non-urgent procedures to a priority-based waiting list, and reschedules them efficiently.
- **Smart Blood Bank (Hybrid Structure):** Combines a `HashMap` (keyed by blood type) with a Min-Heap (`PriorityQueue` ordered by expiration dates) to achieve $O(1)$ access and $O(\log B)$ removal/waste reduction for expired blood bags.

---

## 🛠️ Data Structures & Complexity Overview

| Sub-System / Core Operation | Data Structure Used | Time Complexity | Space Complexity |
| :--- | :--- | :--- | :--- |
| **Emergency Admission** (`admitPatient`) | `PriorityQueue` + `HashMap` | $O(\log N)$ / $O(1)$ | $O(N)$ |
| **Lab Request** (`requestLap`) | `Queue` (Linked List) | $O(1)$ | $O(L)$ |
| **Patient Discharge** (`discargePatinet`) | `Array` + `PriorityQueue` | $O(\log N)$ | $O(1)$ |
| **Patient Report** (`printReport`) | `HashMap` + `LinkedList` | $O(1)$ access | $O(H)$ |
| **Department Allocation** | `ArrayList` | $O(1)$ | $O(D \times R)$ |
| **Doctor Rounds** (`doctorRound`) | Linear Scan (`ArrayList`) | $O(P)$ | $O(1)$ |
| **Blood Expiration Management** | Hybrid (`HashMap` + Min-Heap) | $O(1)$ peek / $O(\log B)$ del | $O(1)$ |

---

## 💻 Technologies Used
- **Language:** Java
- **Core Concepts:** Object-Oriented Programming (OOP), Data Structures & Algorithms (Priority Queues, HashMaps, Dynamic Arrays, Linked Lists), Hybrid Architecture.
