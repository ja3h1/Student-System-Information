# SIMS UML Design

These diagrams describe the initial Java console implementation. Update them if the code or agreed project scope changes.

## Use case diagram

```mermaid
flowchart LR
    Staff([School Staff])
    subgraph SIMS[Student Information Management System]
      Add([Add student])
      List([List students])
      Search([Search student])
      Update([Update student])
      Delete([Delete student])
      Validate([Validate input])
      Persist([Save or load records])
    end
    Staff --> Add
    Staff --> List
    Staff --> Search
    Staff --> Update
    Staff --> Delete
    Add --> Validate
    Update --> Validate
    Add --> Persist
    Update --> Persist
    Delete --> Persist
    List --> Persist
    Search --> Persist
```

## Activity diagram: add a student

```mermaid
flowchart TD
    Start([Start]) --> Choose[Choose student type]
    Choose --> Enter[Enter student and course details]
    Enter --> Valid{Required fields and year valid?}
    Valid -- No --> Error[Show validation message]
    Error --> Enter
    Valid -- Yes --> Exists{Student ID already exists?}
    Exists -- Yes --> Duplicate[Show duplicate ID message]
    Duplicate --> Enter
    Exists -- No --> Create[Create RegularStudent or TransfereeStudent object]
    Create --> Save[Add to collection and save file]
    Save --> Done([Show confirmation])
```

## Sequence diagram: add a student

```mermaid
sequenceDiagram
    actor Staff
    participant UI as Main
    participant Validator as InputValidator
    participant Manager as StudentManager
    participant File as FileHandler
    Staff->>UI: Select Add and enter details
    UI->>Validator: Validate required values and year
    Validator-->>UI: Validated values or input error
    UI->>UI: Create concrete Student subtype
    UI->>Manager: addStudent(student)
    Manager->>Manager: Check duplicate student ID
    Manager->>File: save(student collection)
    File-->>Manager: Save result
    Manager-->>UI: Confirmation or error
    UI-->>Staff: Display result
```

## Class diagram

See [`student_management_class_diagram.png`](student_management_class_diagram.png) for the updated class diagram.

## Design notes

- The system uses a `List<Student>` collection, with concrete student subtypes stored polymorphically.
- `StudentManager` owns record operations, `InputValidator` checks user input, and `FileHandler` handles persistence.
- `Address`, `Course`, and `Department` are associated data classes. Course and department are represented by names; code fields and student status are not part of the model. Year level is limited to 1–4.
- The diagrams describe the design, not test results. Add test outcomes only after the group runs and records the tests.
