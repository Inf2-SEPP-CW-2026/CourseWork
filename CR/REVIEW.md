# Code Review Notes

## Code findings

### 1. Fixed-size faculty storage is brittle

In `RegistrationUtility.registerFacultyMember()`, the code creates `new FacultyMember[7]`. The same
assumption is repeated in `LoginUtility.checkLogin()`, which loops from `0` to `6`.

This means the program only works if the input file contains exactly seven users. If the file
contains fewer users, some array entries remain `null` and the login loop can throw a
`NullPointerException`. If the file contains more users, registration will overflow the array.

```bash

(base) XXXXRT@MacBook-Air CR % mvn -q test
Would you like to change your password? (Y/N)
Please enter your new password
Would you like to change your password? (Y/N)
[ERROR] Tests run: 5, Failures: 0, Errors: 1, Skipped: 0, Time elapsed: 0.025 s <<< FAILURE! -- in LoginUtilityTest
[ERROR] LoginUtilityTest.checkWrongLogin -- Time elapsed: 0.002 s <<< ERROR!
java.lang.NullPointerException: Cannot read field "email" because "faculty[i]" is null
	at LoginUtility.checkLogin(Task4.java:79)
	at LoginUtilityTest.checkWrongLogin(LoginUtilityTest.java:40)
	at java.base/java.lang.reflect.Method.invoke(Method.java:569)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)

[ERROR] Errors:
[ERROR]   LoginUtilityTest.checkWrongLogin:40 » NullPointer Cannot read field "email" because "faculty[i]" is null
[ERROR] Tests run: 5, Failures: 0, Errors: 1, Skipped: 0
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-surefire-plugin:3.2.5:test (default-test) on project task4-review:
[ERROR]
[ERROR] Please refer to /Users/XXXXRT/Desktop/UoE/SEPP/CourseWork/CR/target/surefire-reports for the individual test results.
[ERROR] Please refer to dump files (if any exist) [date].dump, [date]-jvmRun[N].dump and [date].dumpstream.
[ERROR] -> [Help 1]
[ERROR]
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR]
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MojoFailureException

```

### 2. File parsing does not match a normal CSV structure

The registration logic reads two lines per faculty member:

- one line for the email
- one line for the password

That does not match a normal CSV row format such as `email,password`. As written, the code is tied
to a very specific text layout and would mis-handle a standard row-based CSV file. It can also fail
if the file has an odd number of lines, because the second `nextLine()` call is unconditional.

The file-reading logic would be stronger if it read one row at a time and then parsed the fields
from that row.

### 3. Login logic assumes all array entries are valid

`LoginUtility.checkLogin()` directly accesses `faculty[i].email` and `faculty[i].password` inside a
fixed loop.

Because the array can contain `null` values when the file has fewer than seven users, the login
logic is not defensive against incomplete registration output. This is a separate issue from the
array size itself: even if registration succeeds for some users, login can still fail because of
unvalidated array entries.

### 4. Input handling is not robust

The registration code assumes that every pair of lines in the file is valid and complete. There is
no validation for empty values, malformed rows, or unexpected file structure.

For a feature that depends on external input, this is a weakness. Even if the coursework tests only
use clean input, the current design is fragile and would be difficult to extend or maintain.

### 5. Wildcard import is a minor style issue

`import java.io.*;` works, but it hides the actual dependencies used by the file. In this case,
explicit imports would make the code clearer and easier to review.

This is a low-priority comment compared with the functional issues above.

## Test findings

### 1. `changePass()` does not verify that the password was updated

The `changePass()` test only checks that `LoginUtility.checkLogin(...)` returns `true`.

That means the test would still pass even if the program displayed the password-change prompt but
did not save the new password

### 2. `secondLogin()` sets internal state directly instead of reaching it through real behaviour

The `secondLogin()` test manually sets `faculty[1].loginAttempts = 1` before calling
`checkLogin(...)`.

This does cover the branch where the password-change prompt should not appear, but it does not
prove that the system reaches that state correctly through the first-login workflow

### 4. The tests are too dependent on one specific input shape

All tests rely on one fixed file layout and one fixed number of users. They do not cover:

- fewer than seven users
- more than seven users
- malformed CSV input
- incomplete input
- failure cases related to file parsing

### 5. There is no test for an invalid CSV format

The current test suite does not include a case where the faculty input file is malformed

### 6. The test suite does not clearly separate unit and system testing
