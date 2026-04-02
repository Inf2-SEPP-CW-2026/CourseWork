// Review: prefer explicit imports here; only File and FileNotFoundException seem to be needed.
import java.io.*;
import java.util.Scanner;
// *

class User {
    String email;
    String password;

    public User(String gmail, String pass) {
        this.email = gmail;
        this.password = pass;
    }

}


class FacultyMember extends User {
    int loginAttempts;

    public FacultyMember(String gmail, String pass) {
        super(gmail, pass);
        this.loginAttempts = 0;
    }

}


class RegistrationUtility {
    String filePath;

    public RegistrationUtility(String filePath) {
        this.filePath = filePath;
    }

    public FacultyMember[] registerFacultyMember() throws FileNotFoundException {
        File names = new File(filePath);
        Scanner sc = new Scanner(names);
        // Review: fixed-size storage makes this fail if the file has fewer or more than 7 users.
        FacultyMember[] faculty = new FacultyMember[7];

        int counter = 0;
        while (sc.hasNextLine()) {
            // Review: this reads two lines per user, so it does not match a normal CSV row format.
            String email = sc.nextLine();
            String password = sc.nextLine();

            faculty[counter] = new FacultyMember(email, password);

            counter++;

        }

        sc.close();
        return faculty;
    }

    public static void main(String[] args) throws FileNotFoundException {
        RegistrationUtility reg = new RegistrationUtility("Task4 Usernames.csv");

        reg.registerFacultyMember();
    }
}


class LoginUtility {
    String username;
    String password;

    public LoginUtility(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static boolean checkLogin(String user, String pass, Scanner sc, FacultyMember[] faculty)
            throws FileNotFoundException {
        for (int i = 0; i < 7; i++) {
            // Review: this loop repeats the same hard-coded size assumption and can hit null slots.
            if (faculty[i].email.equals(user) && faculty[i].password.equals(pass)) {
                faculty[i].loginAttempts++;
                if (faculty[i].loginAttempts == 1) {
                    System.out.println("Would you like to change your password? (Y/N)");
                    String answer = sc.nextLine();

                    if (answer.equals("Y")) {
                        System.out.println("Please enter your new password");
                        String newPass = sc.nextLine();

                        while (newPass.equals(pass)) {
                            System.out.println(
                                    "New password should be different than the old password. Enter again");
                            newPass = sc.nextLine();
                        }

                        faculty[i].password = newPass;
                        return true;
                    }
                    return true;
                }
                return true;
            }
        }
        System.out.println("Incorrect username or password");
        return false;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        RegistrationUtility reg = new RegistrationUtility("Task4 Usernames.csv");

        FacultyMember[] faculty = reg.registerFacultyMember();

        System.out.println("Enter your username: ");
        String username = sc.nextLine();

        System.out.println("Enter your password: ");
        String password = sc.nextLine();

        LoginUtility login = new LoginUtility(username, password);
        if (login.checkLogin(username, password, sc, faculty)) {
            System.out.println("You have successfully logged in!");
        }
    }
}
