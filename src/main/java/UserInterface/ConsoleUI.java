package UserInterface;

import Controller.Controller;
import Exception.ObjectNotFoundException;

import java.util.Scanner;

/**
 * Class ConsoleUI displays the options of this project in the console;
 * For this it uses the controller that communicates with
 * the file repositories and a Scanner for user input;
 * Date: 15.11.2021
 */
public class ConsoleUI {
    private final Controller controller;
    private final Scanner input = new Scanner(System.in);
    public ConsoleUI(Controller controller) {
        this.controller = controller;
    }
    /**
     * Function display uses a switch command to decide which methods
     * of the controller to call;
     * User must choose a number between 1-12 and 13 exits the app
     * @throws ObjectNotFoundException if the user inputs the data of an object
     * that does not exist
     */
    public void display() throws ObjectNotFoundException {
        while (true) {
            System.out.println("""
                    Please choose an option from below:\s
                    1. Add a new course\s
                    2. Add a new student\s
                    3. Register a student to a course\s
                    4. Print the list of courses with available places\s
                    5. Print all students enrolled in a certain course\s
                    6. Print all existing courses\s
                    7. Update the credits of a certain course\s
                    8. Delete a teacher by a course\s
                    9. Sort the students by their last name alphabetically\s
                    10. Print the students with less than 15 credits (filter)\s
                    11. Sort the courses by name alphabetically\s
                    12. Print the courses with less than a given number of credits\s
                    13. Exit\s
                    """
            );
            int option = input.nextInt();
            int token = 0;
            switch (option) {
                case 1: //add course
                    System.out.println("Name of the course:\n");
                    String name = input.next();
                    System.out.println("First name of the teacher:\n");
                    String firstName = input.next();
                    System.out.println("Last name of the teacher:\n");
                    String lastName = input.next();
                    System.out.println("Maximum number of students:\n");
                    int maxEnrollment = input.nextInt();
                    System.out.println("Number of credits:\n");
                    int credits = input.nextInt();
                    controller.addCourse(name, firstName, lastName, maxEnrollment, credits);
                    break;
                case 2: //add student
                    System.out.println("First name of the student:\n");
                    firstName = input.next();
                    System.out.println("Last name of the student:\n");
                    lastName = input.next();
                    System.out.println("Student ID:\n");
                    long sID = input.nextLong();
                    controller.addStudent(firstName, lastName, sID);
                    break;
                case 3: //register student to course
                    System.out.println("Student ID:\n");
                    sID = input.nextLong();
                    System.out.println("Name of the course:\n");
                    name = input.next();
                    controller.register(sID, name);
                    break;
                case 4: //print courses with available places and number of available places
                    System.out.println(controller.retrieveCourses());
                    break;
                case 5: //print students by course name
                    System.out.println("Name of the course:\n");
                    name = input.next();
                    System.out.println(controller.retrieveStudents(name));
                    break;
                case 6: //print all courses
                    System.out.println(controller.getAllCourses());
                    break;
                case 7: //update the number of credits for a course
                    System.out.println("Name of the course:\n");
                    name = input.next();
                    System.out.println("New number of credits:\n");
                    credits = input.nextInt();
                    controller.updateCredits(name, credits);
                    break;
                case 8: //delete a teacher by course name
                    System.out.println("Name of the course:\n");
                    name = input.next();
                    controller.deleteTeacher(name);
                case 9: //sort students
                    System.out.println(controller.sortStudentsByName());
                    break;
                case 10: //filter students that have < 15 credits
                    System.out.println(controller.filterStudentsByCredits());
                    break;
                case 11: //sort courses
                    System.out.println(controller.sortCoursesByName());
                    break;
                case 12: //filter courses by a given no of credits
                    System.out.println("Please write the number of credits:\n");
                    credits = input.nextInt();
                    System.out.println(controller.filterCoursesByCredit(credits));
                    break;
                case 13: //exit the app
                    token = 1;
                    break;
            }
            if (token == 1) break;
        }
    }
}
