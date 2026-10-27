# Student Information Management System
## Final Project Report

**Course:** CSC 104 – Object-Oriented Programming  
**Project:** Student Information Management System (SIMS)  
**Prepared by:** Lester Terante / Kent Tabaranza  
**Date:** 10/28/2026

## 1. Introduction

Schools need an organized way to retrieve and maintain student information. The Student Information Management System (SIMS) is a Java application for school staff or administrators who manage regular and transferee student records. It supports adding, listing, searching, updating, and deleting records, validates selected fields, and saves records locally.

## 2. Problem analysis

Maintaining student details in separate notes or files can make information slower to find and harder to keep consistent. SIMS addresses this problem by providing one local application for the core record-management tasks.

### Objectives

- Manage regular and transferee student records.
- Provide add, list, search, update, and delete operations.
- Validate required details, student ID, contact number, and year level.
- Save records locally and reload them when the application starts.
- Demonstrate the five required OOP concepts.

### Target users and proposed solution

The intended users are school staff or administrators. SIMS provides a console interface and a local browser interface. Both interfaces use the Java student model and local text-file persistence.

### Scope and limitations

The application stores student ID, name, contact number, year level, course name, department name, and the type-specific admission date or previous school. It does not include authentication, a shared database, enrollment, grades, attendance, billing, school-system integrations, address, student status, or coded course and department catalogs. It is designed as a small academic project rather than a school-wide production system.

## 3. Programming paradigm analysis

SIMS uses object-oriented programming. A procedural design could implement the menu and operations as functions, while a functional design could express record transformations as functions. The object-oriented approach is the best fit for this project because student types share common data and also have distinct details, and because the course requires OOP demonstration. The comparison is documented in `PHASE_2_PARADIGM_COMPARISON.md`.

## 4. System design

The system has an abstract `Student` class, `RegularStudent` and `TransfereeStudent` subclasses, and a `StudentManager` class that manages the collection. The class, use-case, activity, and sequence diagrams are included in `PHASE_3_UML_DESIGN.md`. Its class diagram matches the Java source and should be used in place of the older PNG class diagram.

## 5. Implementation

The application implements student record operations, input validation, local text-file persistence, and console and browser interfaces. The required OOP concepts appear as follows:

- **Classes and objects:** Student types and the manager are represented by Java classes and instantiated as objects.
- **Encapsulation:** Student fields are private and accessed or changed through methods.
- **Inheritance:** `RegularStudent` and `TransfereeStudent` extend `Student`.
- **Polymorphism:** Both student subtypes provide their own `displayDetails()` implementation, and the manager works with `Student` references.
- **Abstraction:** `Student` is abstract and defines shared fields and abstract operations.

## 6. Testing and results

The application sources and standalone verification harness compiled with JDK 27.0.1.0.3 on 2026-10-27. All 26 manager-level verification checks passed. These covered field validation, both student subtypes, duplicate detection, search, update, delete, missing-record handling, and persistence including special-character escaping. A scripted console smoke run also passed all 7 checks for add, list, search, update, delete, and local-file creation. The browser interface was not tested end to end. Detailed cases and results are recorded in `PHASE_5_TEST_PLAN_AND_RESULTS.md`.

## 7. Conclusion

SIMS provides a small student-record application that demonstrates the required OOP concepts and supports the core record-management tasks in scope. The Java source compiled successfully, and the recorded manager-level and console checks passed. The browser interface remains an area for further verification.

## 8. Recommendations

Before using the system beyond a course demonstration, add authentication, a shared database, backups, and appropriate privacy protections. As follow-up project work, test the browser workflows end to end and use the UML materials in `PHASE_3_UML_DESIGN.md` when preparing the presentation. Its class diagram is aligned with the source.
