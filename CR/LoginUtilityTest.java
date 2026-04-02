import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class LoginUtilityTest {

    @Test
    void checkCorrectLogin() throws FileNotFoundException {
        // Simulate user typing "N"
        String input = "N\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Scanner sc = new Scanner(System.in);

        RegistrationUtility reg = new RegistrationUtility("Task4 Usernames.csv");

        FacultyMember[] faculty = reg.registerFacultyMember();

        boolean result = LoginUtility.checkLogin("ktaylor@gmail.com", "rockywitch", sc, faculty);

        assertTrue(result);
    }

    @Test
    void checkWrongLogin() throws FileNotFoundException {
        // No user input needed
        String input = "\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Scanner sc = new Scanner(System.in);
        RegistrationUtility reg = new RegistrationUtility("Task4 Usernames.csv");

        FacultyMember[] faculty = reg.registerFacultyMember();

        boolean result = LoginUtility.checkLogin("ktaylor@gmail.com", "123456", sc, faculty);

        assertFalse(result);
    }

    @Test
    void changePass() throws FileNotFoundException {
        // Simulate user typing "N"
        String input = "Y\nNewPassword1223\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Scanner sc = new Scanner(System.in);
        RegistrationUtility reg = new RegistrationUtility("Task4 Usernames.csv");

        FacultyMember[] faculty = reg.registerFacultyMember();

        boolean result = LoginUtility.checkLogin("ktaylor@gmail.com", "rockywitch", sc, faculty);

        // Review: this test only checks a successful return value, not that the password was
        // actually updated.
        assertTrue(result);

    }

    @Test
    void mainLoginTest() throws FileNotFoundException {
        // Simulate user input (username + password + N for password change)
        String input = "scox@gmail.com\nrugbyOranges\nY\nMoreOranges\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Capture output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Run main
        LoginUtility.main(new String[] {});

        // Restore System.out
        System.setOut(originalOut);

        // Get output
        String output = outputStream.toString();

        // Assertions
        assertTrue(output.contains("Enter your username: "));
        assertTrue(output.contains("Enter your password: "));
        assertTrue(output.contains("You have successfully logged in!"));
    }

    @Test
    void secondLogin() throws FileNotFoundException {
        // Capture output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Run create scanner and faculty objects
        String input = "\n"; // no input needed
        Scanner sc = new Scanner(new ByteArrayInputStream(input.getBytes()));
        RegistrationUtility reg = new RegistrationUtility("Task4 Usernames.csv");
        FacultyMember[] faculty = reg.registerFacultyMember();

        // Change faculty[0].loginattempts to 1
        faculty[1].loginAttempts = 1;

        boolean result = LoginUtility.checkLogin("mcryan@gmail.com", "IADShell", sc, faculty);

        // Restore System.out
        System.setOut(originalOut);

        // Get output
        String output = outputStream.toString();

        // Assertions
        assertFalse(output.contains("Would you like to change your password? (Y/N)"));
        assertTrue(result);
    }
}
