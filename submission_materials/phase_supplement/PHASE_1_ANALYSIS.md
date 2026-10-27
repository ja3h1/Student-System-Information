# Phase 1 – Problem Identification and Analysis

## Project title

**Student Information Management System (SIMS)**

## Background

Schools handle student details for routine administrative work. Staff need to find records and keep common details current. A small record-management application can put those details in one place, provide consistent ways to search and update them, and preserve records between sessions.

## Problem statement

The project addresses the need for a simple tool through which school staff can add, view, search, update, and remove regular and transferee student records. Keeping this information in separate notes or files can make retrieval and updates harder and may lead to incomplete or inconsistent entries. SIMS proposes a single local application with basic input checks and saved records.

## Objectives

- Develop a Java application for maintaining student records.
- Support regular and transferee student types.
- Provide add, list, search, update, and delete operations.
- Check required information, student ID format, contact number format, and year level.
- Save records locally and load them on startup.
- Demonstrate the five course OOP concepts: classes and objects, encapsulation, inheritance, polymorphism, and abstraction.
- Document the design and planned verification.

## Target users

The intended users are school staff or administrators who maintain student information. This academic project is a small local tool, not a production school-wide system.

## Proposed solution

SIMS uses Java classes to represent students and coordinate record operations. A student can be regular or a transferee; each subtype carries its relevant additional detail. Staff can use the console menu or the local browser interface to manage records. The application validates key fields and stores records in a readable text file.

## Scope

Included fields are student ID, name, contact number, year level, course name, department name, student type, and type-specific admission date or previous school. Included functions are CRUD operations, validation, and local persistence. Both console and local browser interfaces are included in the current project.

## Limitations

The project does not include user accounts, role-based access, a shared database, hosting, enrollment, grades, attendance, billing, school-system integrations, address, student status, or coded course/department catalogs. Records are stored locally, so the current design is intended for demonstrations and small single-user use.

## Rationale for OOP

Student records share common fields while regular and transferee records have different additional details. An abstract `Student` class can hold the shared information; subclasses can represent the two types. The manager can work with both through the common `Student` type. This domain structure makes abstraction, inheritance, encapsulation, and polymorphism visible in the code and suits the course outcomes.
