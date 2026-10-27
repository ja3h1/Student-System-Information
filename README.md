# Student Information Management System

A CSC 104 Object-Oriented Programming project for managing student records.

## Overview

This Java console application lets school staff add, list, search, update, and delete student records. It validates required fields and year level, then saves records locally.

## The four classes

| Class | Responsibility |
|---|---|
| `Student` *(abstract)* | Shared student fields: ID, name, contact number, year level, course name, and department name. |
| `RegularStudent` | Regular student subtype with an admission date. |
| `TransfereeStudent` | Transferee subtype with a previous school. |
| `StudentManager` | Console menu, student collection, CRUD operations, validation, and file saving/loading. |

There is no address field or separate address class. Course and department are stored as names, without codes. Student status is not collected. Year level accepts 1–4.

CRUD, input validation, and file saving/loading are system functions. The project demonstrates only the five required OOP concepts below.

## Required OOP concepts

- **Classes and objects:** The four classes model student types and the system that manages them.
- **Encapsulation:** Student fields are private and are accessed through methods.
- **Inheritance:** `RegularStudent` and `TransfereeStudent` extend `Student`.
- **Polymorphism:** Each subtype overrides `displayDetails()`; the manager handles both through `Student` references.
- **Abstraction:** `Student` is abstract and provides shared fields and behavior for concrete student types.

## Run from Command Prompt

Requires JDK 11 or newer with `javac` available on PATH. From the project folder, run:

```cmd
run.bat
```

You can also double-click `run.bat` in File Explorer. The script compiles the four Java source files and opens the menu. It saves records in `data/student_records_v2.dat`.

To compile and run manually from PowerShell:

```powershell
New-Item -ItemType Directory -Force out
javac -d out (Get-ChildItem .\src\*.java).FullName
java -cp out StudentManager
```

## Project documents

- [Project plan](PROJECT_PLAN.md)
- [UML design](UML_DESIGN.md)
- [Class diagram image](student_management_class_diagram.png)

Use fictional student information for demonstrations and sample data.
