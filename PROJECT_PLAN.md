# SIMS Project Plan

## Goal

Develop a simple Student Information Management System for managing student records.

## Main user

A school staff member or administrator who maintains student information.

## Planned core features

1. Add a student record.
2. View and list student records.
3. Search for a student by student ID.
4. Update a student record.
5. Delete a student record.
6. Validate entered values and show understandable feedback.
7. Save and reload records if file persistence is included in the implementation.

## Class design

- `Person` (abstract): shared personal details and behavior.
- `Student` (abstract): shared student details; extends `Person`.
- `RegularStudent`: regular admission details; extends `Student`.
- `TransfereeStudent`: previous-school details; extends `Student`.
- `Address`: address information associated with a student.
- `Course`: course details associated with students.
- `Department`: department that offers courses.
- `StudentManager`: maintains a collection of students and provides record operations.
- `FileHandler`: saves and loads records, if supported.
- `InputValidator`: validates inputs, if supported.

## Required OOP demonstration

- **Classes and objects:** Create student, course, address, and management objects.
- **Encapsulation:** Keep data fields private and modify them through appropriate methods.
- **Inheritance:** Use the `Person` → `Student` → student subtype hierarchy.
- **Polymorphism:** Override `displayDetails()` in the concrete student subtypes.
- **Abstraction:** Use abstract classes for shared properties and behavior.

## Development sequence

1. Confirm requirements and project scope.
2. Finalize UML diagrams: use case, class, activity, and sequence.
3. Implement the model classes and relationships.
4. Implement student record operations and input validation.
5. Add persistence and error handling if required by the instructor.
6. Test valid and invalid inputs, CRUD behavior, and save/load behavior.
7. Prepare presentation slides and a stable demonstration path.

## Keep the repository accurate

This plan describes intended design. Revise it if the implementation changes, and do not report planned features as completed until they work.
