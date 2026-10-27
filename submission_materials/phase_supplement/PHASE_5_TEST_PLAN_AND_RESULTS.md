# Phase 5 – Testing and Debugging

## Execution status

**Executed successfully on 2026-10-27 using JDK 27.0.1.0.3.** The Java application sources and standalone verification harness compiled successfully. The harness ran from a clean temporary working directory, isolated from project records, and reported **26 of 26 checks passed**. A scripted console smoke run also passed **7 checks** covering add, list, search, update, delete, and local-file creation. The browser interface was not exercised end to end.

## Test cases

| ID | Test | Expected result | Actual result | Status / debugging record |
|---|---|---|---|---|
| T01 | Accept a student ID in `000-00000` format. | Accepted. | `000-12345` accepted. | **Pass.** |
| T02 | Reject a malformed student ID. | Validation rejects the value. | `00-12345` rejected. | **Pass.** |
| T03 | Accept an 11-digit contact beginning with `09`. | Accepted. | `09123456789` accepted. | **Pass.** |
| T04 | Reject a contact with an incorrect prefix or length. | Validation rejects the value. | Incorrect prefix and short contact rejected. | **Pass.** |
| T05 | Accept year levels 1 and 4. | Both boundary values are accepted. | Both boundary values accepted. | **Pass.** |
| T06 | Reject year levels 0, 5, and non-numeric input. | Invalid values are rejected. | All three invalid values rejected. | **Pass.** |
| T07 | Add regular and transferee records. | Both are stored with the correct subtype. | Two records added; both subtypes confirmed. | **Pass.** |
| T08 | Search for present and absent student IDs. | Present record is returned; absent record is not found. | Both existing records found; absent ID returned empty. | **Pass.** |
| T09 | Add a duplicate student ID. | Duplicate is rejected. | Duplicate ID raised the expected error. | **Pass.** |
| T10 | Update common fields on an existing record. | New values are returned and saved. | Updated name, year, and course confirmed; values survived reload. | **Pass.** |
| T11 | Restart manager after saving records. | Records and subtype information reload. | Records and transferee subtype reloaded. | **Pass.** |
| T12 | Save and reload fields containing pipe, backslash, and newline characters. | Escaped values are restored intact. | Name and course/department values restored intact. | **Pass.** |
| T13 | Delete an existing record and restart. | Record is absent after deletion and reload. | Record absent after deletion and a new manager load. | **Pass.** |
| T14 | Update or delete a nonexistent record. | Operation is rejected with an error. | Both operations raised the expected error. | **Pass.** |
| T15 | Use the console to add, list, and search for a regular student. | Console confirms the add, lists the record, and shows its details in search. | All three expected outputs were observed. | **Pass.** |
| T16 | Use the console to update and delete the student. | Console confirms both operations and displays the updated name before deletion. | All three expected outputs were observed. | **Pass.** |
| T17 | Confirm console operation creates the local record file. | `data/students.txt` is created in the isolated working directory. | File was created; temporary test data was removed afterward. | **Pass.** |

## Debugging record

No failures occurred in the 26 harness checks or the 7 console smoke checks, so no code fixes were needed during this verification run. The JDK was invoked from its installation folder because it was not available through the usual command search path. The browser interface still needs end-to-end checks.

## Running the included verification harness

From the project folder, compile the application sources and `submission_materials/phase_supplement/StudentManagerVerification.java` into a temporary classes directory. Run `StudentManagerVerification` with a clean temporary working directory so that its `data/students.txt` file is isolated from project records. The harness prints an individual result for each check and a final count. On 2026-10-27, all 26 harness checks and all 7 scripted console checks passed.
