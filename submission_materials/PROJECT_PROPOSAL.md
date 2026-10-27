# Project Proposal: Student Information Management System

## 1. Project title

**Student Information Management System (SIMS)**

## 2. Background

Schools need an organized way to keep student details available for routine administrative work. When records are kept in separate notes or files, staff may spend extra time finding information, updating details, and checking whether a record is current. A small information system can make these tasks more consistent by keeping student records in one place and providing common operations through a simple interface.

## 3. Problem statement

The proposed users need a straightforward way to add, view, search, update, and remove student records. Manual record handling can make those tasks slow and can lead to inconsistent or incomplete information. The project will address this need with a student-record application that validates key fields and saves records for later use.

## 4. Objectives

The project aims to:

1. Build a Java application for maintaining student records.
2. Support regular and transferee student record types.
3. Provide create, read/list, search, update, and delete operations.
4. Validate required fields, student ID, contact number, and year level.
5. Save records locally and load them when the application starts.
6. Demonstrate classes and objects, encapsulation, inheritance, polymorphism, and abstraction.
7. Document the design, implementation, and results of planned tests.

## 5. Target users

The primary target users are school staff or administrators who maintain student information. The application is a small academic project and is not intended to replace a school-wide student information platform.

## 6. Proposed solution

SIMS will provide a Java application with a console menu and a browser-based local interface. Both interfaces use the same student model and local records. Users can add regular or transferee students, list records, search by student ID, update common details, and delete records. The application validates key inputs and stores records in a readable local text file.

The current design uses four classes: an abstract `Student` base class, `RegularStudent`, `TransfereeStudent`, and `StudentManager`. The two student types inherit shared fields and behavior from `Student` and provide their own student-specific detail.

## 7. Programming paradigm analysis

| Paradigm | How it could be used | Fit for this project |
|---|---|---|
| Procedural | Organize the application as procedures that operate on student data. | Suitable for a small menu-driven program, but student data and the behavior associated with different student types would be less directly represented. |
| Functional | Use functions that transform data, with emphasis on immutable values and avoiding shared state. | Useful for validating or transforming records, but it is not the main approach used by this Java project and would require a different structure for interactive record management. |
| Object-oriented | Represent students and the manager as objects, with shared and specialized behavior organized in classes. | Best fit for the course requirements and the system’s regular/transferee record types. Inheritance, encapsulation, abstraction, and polymorphism are directly demonstrated by the current design. |

The project will use **object-oriented programming** as its primary paradigm because student records have shared fields alongside type-specific details. The abstract `Student` class holds shared information, while its subclasses represent regular and transferee students. `StudentManager` coordinates records and operations. This structure makes the required OOP concepts visible in the implementation and keeps the class design aligned with the domain.

## 8. Scope and limitations

### Included

- Regular and transferee student records.
- Student ID, name, contact number, year level, course name, department name, and type-specific information.
- Add, list, search, update, and delete operations.
- Required-field and format validation for student ID, contact number, and year level.
- Local text-file persistence.
- Console and local browser interfaces.
- UML design and documented test results.

### Excluded

- User accounts, roles, and authentication.
- A hosted or multi-user database.
- Enrollment, grades, attendance, billing, or reporting modules.
- Address, student status, course codes, and department codes.
- Integration with other school systems.

These limits keep the project small enough for the course while making its intended use clear.

## 9. Design and development plan

| Phase | Planned work and deliverable | Completion evidence |
|---|---|---|
| **1. Problem identification and analysis** | Finalize this proposal with the background, problem, objectives, users, solution, scope, limitations, and OOP rationale. | Proposal contains each required analysis section. |
| **2. Programming paradigm analysis** | Compare procedural, functional, and object-oriented approaches and justify the selected approach. | Comparison and justification are included in the proposal/report. |
| **3. System analysis and UML design** | Maintain the class, use-case, and activity diagrams; add a sequence diagram for a representative operation such as adding a student. | All four required UML diagrams are present and consistent with the code. |
| **4. OOP implementation** | Implement the student classes and manager, CRUD operations, validation, interfaces, and persistence. | Source code demonstrates the required OOP concepts and documented features. |
| **5. Testing and debugging** | Test valid and invalid input, student operations, persistence, and both interfaces; record actual outcomes and fixes. | Test table includes cases, expected results, actual results, pass/fail status, and debugging notes. |
| **6. Final documentation** | Assemble the final report with introduction, analysis, design, implementation, testing/results, conclusion, and recommendations. | Final report covers every required section and reflects the final implementation and test evidence. |

## 10. Proposed test coverage

The following cases are planned. Actual results and pass/fail status must be filled in after the tests are run; they are not claimed here.

| Test area | Example case | Expected result |
|---|---|---|
| Add student | Add a regular student with valid required details. | Record is accepted and appears in the list. |
| Add student | Add a transferee student with valid required details. | Record is accepted and its previous-school detail is retained. |
| Required fields | Submit a blank name or course. | Input is rejected with a useful validation message. |
| Student ID | Submit an ID that does not match `000-00000`. | Input is rejected. |
| Contact number | Submit a number that is not 11 digits beginning with `09`. | Input is rejected. |
| Year level | Submit a value outside 1–4 or a non-number. | Input is rejected. |
| Duplicate ID | Add a record using an ID that already exists. | Duplicate record is rejected. |
| Search | Search for an existing and a nonexistent ID. | Existing record is shown; missing record produces a not-found message. |
| Update | Update an existing record with valid values. | Updated values are shown and persist after restart. |
| Delete | Delete an existing record. | Record is removed and remains absent after restart. |
| Persistence | Restart after adding records. | Saved records load with their fields and student types intact. |
| Special characters | Save fields containing a pipe or backslash. | Values reload without being split or corrupted. |

## 11. Expected outcome

The expected outcome is a working, small-scale student information application and a complete set of project documents that explain the problem, compare programming paradigms, show the UML design, describe implementation choices, and present recorded test evidence. The accompanying phase documents now include the four UML diagrams, verification results, and final report. Browser end-to-end testing remains a recommended follow-up.

## 12. Conclusion and recommendations

SIMS is a practical course project for demonstrating object-oriented design through a familiar record-management problem. The Java implementation and accompanying phase documents provide the proposed scope, UML design, recorded tests, and final report. As future work, the system could add authentication, a shared database, and additional student or academic records if the project scope is expanded.
