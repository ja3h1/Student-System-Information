# Phase 2 – Programming Paradigm Analysis

| Paradigm | Description in this project context | Advantages | Trade-offs |
|---|---|---|---|
| Procedural | A sequence of procedures would accept and update student data. | Straightforward for a small menu and a limited number of operations. | Student data and type-specific behavior would be less directly grouped; handling different student types could spread conditional logic across procedures. |
| Functional | Operations would be modeled primarily as functions that transform values, ideally avoiding mutable shared state. | Can make transformations and validation logic easy to reason about when functions are pure. | The current interactive application relies on changing a record collection and coordinating input and file persistence; a functional design is possible but is not the structure demonstrated by this Java project. |
| Object-oriented | The application groups state and behavior into student and manager objects. | Shared data can live in a base type, specialized student details can live in subtypes, and the manager can operate on `Student` references. | Requires deciding class responsibilities and managing mutable object state carefully. |

## Selected paradigm: object-oriented programming

Object-oriented programming is selected because the system models student records with shared and specialized characteristics, and because the course requires demonstration of OOP concepts. The abstract `Student` type defines common data and behavior. `RegularStudent` and `TransfereeStudent` inherit the shared structure and provide distinct details. `StudentManager` maintains a collection of `Student` objects and uses polymorphic display behavior.

This is a pragmatic course-project choice, rather than a claim that OOP is the only way to implement record management. Procedural and functional approaches could also implement these features, but the current domain model and learning objectives make OOP the clearest fit.
