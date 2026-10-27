# SIMS Project Plan

## Goal

Develop a simple Java console application for managing student records.

## Main user

A school staff member or administrator who maintains student information.

## Current core features

1. Add a regular or transferee student record.
2. View and list student records.
3. Search for a student by student ID.
4. Update a student record.
5. Delete a student record.
6. Validate required input and year level, with understandable feedback.
7. Save and reload records using Java object serialization.

## Class design

- `Person` (abstract): shared personal details and behavior.
- `Student` (abstract): shared student details; extends `Person`.
- `RegularStudent`: regular admission details; extends `Student`.
- `TransfereeStudent`: previous-school details; extends `Student`.
- `Address`: address information associated with a student.
- `Course`: course details associated with students.
- `Department`: department that offers courses.
- `StudentManager`: maintains a collection of students and provides record operations.
- `FileHandler`: saves and loads records.
- `InputValidator`: validates user input.
- `Main`: console menu and application entry point.

## Required OOP demonstration

- **Classes and objects:** Create student, course, address, and management objects.
- **Encapsulation:** Keep data fields private and modify them through appropriate methods.
- **Inheritance:** Use the `Person` → `Student` → student subtype hierarchy.
- **Polymorphism:** Override `displayDetails()` in the concrete student subtypes.
- **Abstraction:** Use abstract classes for shared properties and behavior.

## Development sequence

1. Confirm requirements and project scope. *(Initial scope documented.)*
2. Finalize UML diagrams: use case, class, activity, and sequence. *(Use case, activity, and sequence drafts are in UML_DESIGN.md; the class diagram image is included.)*
3. Implement the model classes and relationships. *(Initial console implementation added.)*
4. Implement student record operations and input validation. *(Initial CRUD menu added.)*
5. Add persistence and error handling. *(File save/load and input error handling added.)*
6. Test valid and invalid inputs, CRUD behavior, and save/load behavior; document actual results.
7. Prepare presentation slides and a stable demonstration path.

## Keep the repository accurate

Update this plan when the implementation changes. Do not report test results or features as complete until the group has actually checked them. See [UML_DESIGN.md](UML_DESIGN.md) for the workflow diagrams.
