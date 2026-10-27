# SIMS Project Plan

## Goal

Develop a simple Java console application for managing student records.

## Main user

A school staff member or administrator who maintains student information.

## Current features

1. Add a regular or transferee student.
2. List all student records.
3. Search by student ID.
4. Update a record.
5. Delete a record.
6. Validate required fields and year level (1–4).
7. Save and reload student records from a readable text file (`data/students.txt`).

## Four-class design

- `Student` (abstract): shared integer student ID, name, integer contact number, year level, course name, and department name.
- `RegularStudent`: adds admission date and overrides `displayDetails()`.
- `TransfereeStudent`: adds previous school and overrides `displayDetails()`.
- `StudentManager`: console menu, collection, CRUD operations, validation, and text-file persistence.

The design uses integer types for student ID and contact number; leading zeros and 11-digit contact values cannot be represented. It has no address field or address class. Course and department are names rather than separate classes or coded values. Student status is omitted.

## Required OOP demonstration

- **Classes and objects:** Create records using the concrete student classes and manage them through `StudentManager`.
- **Encapsulation:** Keep fields private and use methods for access or updates.
- **Inheritance:** Both student subtypes extend the abstract `Student` class.
- **Polymorphism:** Each subtype implements its own `displayDetails()` method.
- **Abstraction:** The abstract `Student` class describes shared student data and behavior.

## Development sequence

1. Confirm project scope and required features. *(Four-class scope updated.)*
2. Finalize UML diagrams. *(Updated class, use-case, activity, and sequence diagrams are in UML_DESIGN.md.)*
3. Implement the four classes and relationships. *(Four-class implementation added.)*
4. Implement CRUD operations and validation. *(Console menu included.)*
5. Add file persistence. *(StudentManager saves and loads records.)*
6. Test valid and invalid inputs, CRUD behavior, and save/load behavior; record actual results.
7. Prepare presentation slides and a stable demonstration path.

## Keep the repository accurate

Update this plan when the implementation changes. Add test results only after the group runs and records the tests.
