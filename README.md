# Student Information Management System

A CSC 104 Object-Oriented Programming project for organizing student records.

## Overview

This Java console application lets a school staff member add, list, search, update, and delete student records. Records are saved to `data/students.dat` and loaded when the application starts.

## OOP concepts demonstrated

- **Classes and objects:** Student records, courses, departments, and addresses are modeled as objects.
- **Encapsulation:** Data fields are private and accessed through methods.
- **Inheritance:** `Student` extends abstract `Person`; `RegularStudent` and `TransfereeStudent` extend `Student`.
- **Polymorphism:** The student subtypes override `displayDetails()` and are displayed through the common `Student` type.
- **Abstraction:** `Person` and `Student` define shared structure and behavior for their concrete subclasses.

## Classes

| Class | Responsibility |
|---|---|
| `Person` | Abstract base for shared personal details. |
| `Student` | Abstract student record with ID, year level, address, and course. |
| `RegularStudent` | Student subtype with admission date. |
| `TransfereeStudent` | Student subtype with previous-school information. |
| `Address` | Stores a student's address. |
| `Course` | Stores course details and its department. |
| `Department` | Stores department details. |
| `StudentManager` | Manages student records and CRUD operations. |
| `FileHandler` | Saves and loads records using Java object serialization. |
| `InputValidator` | Validates required text and year-level input. |
| `Main` | Runs the console menu and connects user input to the system. |

## Run the application

Requires JDK 11 or newer. From the repository root:

```powershell
New-Item -ItemType Directory -Force out
javac -d out (Get-ChildItem .\src\*.java).FullName
java -cp out Main
```

Year level is limited to 1–4. Student records contain course and department names; status and course/department codes are not collected. The application creates the `data` folder and student data file when records are first saved. Keep that generated data file private if it contains real student information; use fictional sample records for demonstrations and repository examples.

## Project documents

- [Project plan](PROJECT_PLAN.md)
- [UML design](UML_DESIGN.md)
- [Class diagram image](student_management_class_diagram.png)

## Status and team

This is an initial functional version. Add group member names and contributions here. Update this section as the team implements and documents additional requirements.
