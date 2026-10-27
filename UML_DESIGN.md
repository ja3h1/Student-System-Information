# SIMS UML Design

The design is limited to four classes and matches the Java implementation.

## Class diagram

```mermaid
classDiagram
    class Student {
      <<abstract>>
      -int studentId
      -String name
      -int contactNumber
      -int yearLevel
      -String courseName
      -String departmentName
      +getStudentType()* String
      +displayDetails()* String
    }
    class RegularStudent {
      -String admissionDate
      +getStudentType() String
      +displayDetails() String
    }
    class TransfereeStudent {
      -String previousSchool
      +getStudentType() String
      +displayDetails() String
    }
    class StudentManager {
      -List~Student~ students
      +addStudent(Student) void
      +searchStudent(int) Optional~Student~
      +updateStudent(int, ...) void
      +deleteStudent(int) void
      +listStudents() List~Student~
      +main(String[]) void
    }
    Student <|-- RegularStudent
    Student <|-- TransfereeStudent
    StudentManager o-- Student : manages collection
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
      Persist([Save and load text records])
    end
    Staff --> Add
    Staff --> List
    Staff --> Search
    Staff --> Update
    Staff --> Delete
    Add --> Persist
    Update --> Persist
    Delete --> Persist
    List --> Persist
    Search --> Persist
```

## Activity diagram: add a student

```mermaid
flowchart TD
    Start([Start]) --> Choose[Choose regular or transferee]
    Choose --> Enter[Enter student details]
    Enter --> Valid{Required fields valid and year is 1–4?}
    Valid -- No --> Error[Show validation message]
    Error --> Enter
    Valid -- Yes --> Exists{Student ID already exists?}
    Exists -- Yes --> Duplicate[Show duplicate ID message]
    Duplicate --> Enter
    Exists -- No --> Type{Student type?}
    Type -- Regular --> Regular[Create RegularStudent]
    Type -- Transferee --> Transferee[Create TransfereeStudent]
    Regular --> Save[Add to collection and save file]
    Transferee --> Save
    Save --> Done([Show confirmation])
```

## Sequence diagram: add a student

```mermaid
sequenceDiagram
    actor Staff
    participant App as StudentManager
    participant Records as Student collection
    participant File as students.txt
    Staff->>App: Choose Add and enter details
    App->>App: Validate required fields and year 1–4
    App->>Records: Check for duplicate student ID
    App->>App: Create RegularStudent or TransfereeStudent
    App->>Records: Add Student object
    App->>File: Save collection
    File-->>App: Save result
    App-->>Staff: Show confirmation or error
```

## Scope notes

Student ID and contact number use `int`, so leading zeros and 11-digit contact values cannot be represented. There is no address field/class, status field, course code, department code, or separate course and department classes. Course and department names are stored directly on `Student`. `StudentManager` includes the menu, validation, CRUD operations, and text-file persistence so the project remains at four Java classes.
