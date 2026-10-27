# Phase 3 – System Analysis and UML Design

This supplement provides the four required diagrams in Mermaid notation. The class diagram matches the current Java source. Use it in place of the older PNG class diagram, whose field types and subtype details do not match the implementation.

## Class diagram

```mermaid
classDiagram
    class Student {
      <<abstract>>
      -String studentId
      -String name
      -String contactNumber
      -int yearLevel
      -String courseName
      -String departmentName
      +getStudentId() String
      +getName() String
      +setName(String) void
      +getContactNumber() String
      +setContactNumber(String) void
      +getYearLevel() int
      +setYearLevel(int) void
      +getCourseName() String
      +setCourseName(String) void
      +getDepartmentName() String
      +setDepartmentName(String) void
      +getStudentType()* String
      +displayDetails()* String
    }
    class RegularStudent {
      -String admissionDate
      +getAdmissionDate() String
      +getStudentType() String
      +displayDetails() String
    }
    class TransfereeStudent {
      -String previousSchool
      +getPreviousSchool() String
      +getStudentType() String
      +displayDetails() String
    }
    class StudentManager {
      -List~Student~ students
      +listStudents() List~Student~
      +searchStudent(String) Optional~Student~
      +addStudent(Student) void
      +updateStudent(String, ...) void
      +deleteStudent(String) void
      +main(String[]) void
    }
    Student <|-- RegularStudent
    Student <|-- TransfereeStudent
    StudentManager o-- Student : manages
```

## Use case diagram

```mermaid
flowchart LR
    Staff([School staff])
    subgraph SIMS[Student Information Management System]
      Add([Add student])
      List([List students])
      Search([Search by ID])
      Update([Update student])
      Delete([Delete student])
      Persist([Save and load records])
    end
    Staff --> Add
    Staff --> List
    Staff --> Search
    Staff --> Update
    Staff --> Delete
    Add --> Persist
    List --> Persist
    Search --> Persist
    Update --> Persist
    Delete --> Persist
```

## Activity diagram: add a student

```mermaid
flowchart TD
    Start([Start]) --> Choose[Choose regular or transferee]
    Choose --> Enter[Enter student details]
    Enter --> Valid{Required fields and formats valid?}
    Valid -- No --> Error[Show validation error]
    Error --> Enter
    Valid -- Yes --> Exists{Student ID already exists?}
    Exists -- Yes --> Duplicate[Show duplicate ID error]
    Duplicate --> Enter
    Exists -- No --> Type{Selected type?}
    Type -- Regular --> Regular[Create RegularStudent]
    Type -- Transferee --> Transferee[Create TransfereeStudent]
    Regular --> Save[Add to collection and save file]
    Transferee --> Save
    Save --> Done([Show confirmation])
```

## Sequence diagram: add a student

The console menu or browser form collects the input. `StudentManager` validates fields, rejects duplicate IDs, creates the selected student subtype, updates its collection, and saves the records.

```mermaid
sequenceDiagram
    actor Staff
    participant UI as Console or Browser UI
    participant Manager as StudentManager
    participant Student as RegularStudent or TransfereeStudent
    participant File as students.txt

    Staff->>UI: Choose Add and enter details
    UI->>Manager: Submit student type and fields
    Manager->>Manager: Validate required fields and formats
    alt Invalid input
        Manager-->>UI: Return validation error
        UI-->>Staff: Show error
    else Valid input
        Manager->>Manager: Search collection for duplicate ID
        alt ID already exists
            Manager-->>UI: Return duplicate ID error
            UI-->>Staff: Show error
        else ID is available
            Manager->>Student: Construct selected subtype
            Student-->>Manager: Return student object
            Manager->>Manager: Add object to collection
            Manager->>File: Save all student records
            File-->>Manager: Write complete
            Manager-->>UI: Return success
            UI-->>Staff: Show saved confirmation
        end
    end
```

## Model consistency

The Java source stores student IDs and contact numbers as `String` values, preserving the ID hyphen and phone-number leading zero. The diagram reflects those types and the regular/transferee subtype fields and methods. The older `student_class_diagram.png` does not match these details; use the class diagram above for the project report.
