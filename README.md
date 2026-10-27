# Student Information Management System

A CSC 104 Object-Oriented Programming final project focused on organizing student records.

## Project overview

The system is intended to help a school staff member manage student information in one place. The planned core functions are to add, view, search, update, and delete student records.

## Planned OOP concepts

- **Classes and objects:** Student and supporting system classes model the records and the work the application performs.
- **Encapsulation:** Keep fields private and expose controlled methods for reading or changing data.
- **Inheritance:** `Student` inherits common personal details from the abstract `Person` class; `RegularStudent` and `TransfereeStudent` inherit from `Student`.
- **Polymorphism:** Student subtypes can provide their own implementation of `displayDetails()`.
- **Abstraction:** Abstract `Person` and `Student` classes define shared behavior without being instantiated directly.

## Planned classes

| Class | Responsibility |
|---|---|
| `Person` | Abstract base for shared personal details. |
| `Student` | Abstract student record with student ID, year level, status, address, and course. |
| `RegularStudent` | Student subtype with regular admission information. |
| `TransfereeStudent` | Student subtype with previous-school information. |
| `Address` | Stores a student's address. |
| `Course` | Stores course information. |
| `Department` | Represents a department that offers courses. |
| `StudentManager` | Manages student records and CRUD operations. |
| `FileHandler` | Handles saving and loading records if implemented. |
| `InputValidator` | Checks user-provided values if implemented. |

## Class diagram

See [`student_management_class_diagram.png`](student_management_class_diagram.png).

## Project status

This repository currently contains the class diagram and project plan. Update this section as implementation progresses. Only describe features as complete after they are implemented and checked.

## Team

Add group members and their contributions here.
