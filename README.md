# Student Information Management System

A CSC 104 Object-Oriented Programming project for managing student records.

## Overview

This Java console application lets school staff add, list, search, update, and delete student records. The list view uses aligned columns. It validates required fields and year level, then saves records locally.

## The four classes

| Class | Responsibility |
|---|---|
| `Student` *(abstract)* | Shared student fields: student ID (format 000-00000), name, contact number (format 09000000000), year level, course name, and department name. |
| `RegularStudent` | Regular student subtype with an admission date. |
| `TransfereeStudent` | Transferee subtype with a previous school. |
| `StudentManager` | Console menu, student collection, CRUD operations, validation, and file saving/loading. |

There is no address field or separate address class. Course and department are stored as names, without codes. Student status is not collected. Year level accepts 1–4. Student ID must look like 000-00000 and contact number must be 11 digits starting with 09.

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

You can also double-click `run.bat` in File Explorer. The script compiles the four Java source files and opens the menu. It saves readable, pipe-separated text records in `data/students.txt`.

To compile and run manually from PowerShell:

```powershell
New-Item -ItemType Directory -Force out
javac -d out (Get-ChildItem .\src\*.java).FullName
java -cp out StudentManager
```

## Web interface

Run `run-web.bat` and open `http://localhost:8080` to manage the same records in the browser. The page is `docs/index.html`; on GitHub Pages it runs as a demo that saves in the visitor's browser only.

## Project documents

- [Project plan](PROJECT_PLAN.md)
- [UML design](UML_DESIGN.md)
- [Class diagram image](student_management_class_diagram.png)

Use fictional student information for demonstrations and sample data.