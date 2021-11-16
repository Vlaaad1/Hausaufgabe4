package Controller;

import Exception.ObjectNotFoundException;
import Objects.Course;
import Objects.Student;
import Objects.Teacher;
import Repository.CourseRepo;
import Repository.StudentRepo;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Classname Controller
 * Date: 15.11.2021
 * Here are the main functionalities used by the ConsoleUI
 */
public class Controller {
    private final StudentRepo studentRepo;
    private final CourseRepo courseRepo;

    /**
     * Class constructor.
     *
     * @param studentRepo is the repository for students 
     * @param courseRepo is the repository for courses
     */
    public Controller(StudentRepo studentRepo, CourseRepo courseRepo) {
        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;
    }

    /**
     * method that helps the user to add a course to the list of courses
     * @param name is the name of the course
     * @param firstName is the firstname of the teacher
     * @param lastName  is the lastname of the teacher
     * @param maxEnrollment is number of students that can participate
     * @param credits is the number of credits awarded to the student for promoting this course
     * @return true if the operation was completed
     */
    public boolean addCourse (String name, String firstName, String lastName , int maxEnrollment, int credits) {
        Teacher t = new Teacher(firstName, lastName, new ArrayList<>());
        Course c = new Course(name, t, maxEnrollment, new ArrayList<>(), credits);
        this.courseRepo.save(c);
        t.addCourse(c);
        this.courseRepo.storeToFile();
        return true;
    }

    /**
     * method that helps the user to add a student to the list of students
     * @param firstName is the firstname of the student
     * @param lastName is the lastname of the student
     * @param id is the student's id
     * @return  true if the operation was completed
     */
    public boolean addStudent (String firstName, String lastName, long id){
        Student s = new Student(firstName, lastName, id, new ArrayList<>());
        this.studentRepo.save(s);
        this.studentRepo.storeToFile();
        return true;
    }

    /**
     * @param id is the id of a student
     * @param name is the name of a course
     * @return true if the student was enrolled to this course 
     */
    public boolean register(long id, String name) throws ObjectNotFoundException {
        if(this.studentRepo.findByID(id)==null || this.courseRepo.findByName(name)==null)
            throw new ObjectNotFoundException("Object was not found in repository");
        Student s = this.studentRepo.findByID(id);
        Course c = this.courseRepo.findByName(name);
        if (c.getStudentsEnrolled().size() == c.getMaxEnrollment()) {
            System.out.println("Maximum number of students reached");
            return false;
        } else if (s.getTotalCredits() + c.getCredit() > 30) {
            System.out.println("Maximum number of credits reached");
            return false;
        } else {
            ArrayList<Course> newListCourse = (ArrayList<Course>) s.getEnrolledCourses();
            newListCourse.add(c);
            s.setEnrolledCourses(newListCourse);
            s.setTotalCredits(s.getTotalCredits() + c.getCredit());
            ArrayList<Student> newListStudents = (ArrayList<Student>) c.getStudentsEnrolled();
            newListStudents.add(s);
            c.setStudentsEnrolled(newListStudents);
            this.courseRepo.storeToFile();
            this.studentRepo.storeToFile();
            return true;
        }
    }

    /**
     * @return the list of courses with available places and display the courses and number of places
     */
    public ArrayList<Course> retrieveCourses() {
        ArrayList<Course> newList = new ArrayList<>();
        for (Course c : courseRepo.findAll()) {
            if (c.getStudentsEnrolled().size() < c.getMaxEnrollment()) {
                newList.add(c);
                System.out.println(c + " " + (c.getMaxEnrollment() - c.getStudentsEnrolled().size()));
            }
        }
        return newList;
    }

    /**
     * @param name is the name of a course
     * @return the list of students enrolled for course c
     */
    public ArrayList<Student> retrieveStudents(String name) throws ObjectNotFoundException {
        if(this.courseRepo.findByName(name)==null)
            throw new ObjectNotFoundException("Object was not found in repository");
        Course c = this.courseRepo.findByName(name);
        return (ArrayList<Student>) courseRepo.findOne(c).getStudentsEnrolled();
    }

    /**
     * method that shows the user all courses
     * @return all courses
     */
    public ArrayList<Course> getAllCourses() {
        return (ArrayList<Course>) courseRepo.findAll();
    }

    /**
     * method that shows the user all students
     * @return all students
     */
    public ArrayList<Student> getAllStudents() {
        return (ArrayList<Student>) studentRepo.findAll();
    }
    /**
     * Function updateCredits modifies the no of credits of a course 
     * It also updates the totalCredits of the students that are enrolled to the course
     *
     * @param name is the name of a course
     * @param newCredits is the new number of credits
     */
    public void updateCredits(String name, int newCredits) throws ObjectNotFoundException {
        if(this.courseRepo.findByName(name)==null)
            throw new ObjectNotFoundException("Object was not found in repository");
        Course c = this.courseRepo.findByName(name);
        //using diff the program sees the exact difference between the old and the new value
        int diff = c.getCredit() - newCredits;
        courseRepo.findOne(c).setCredit(newCredits);
        for (Student s : c.getStudentsEnrolled()) {
            s.setTotalCredits(s.getTotalCredits() - diff);
            //this way we properly update the students attribute
        }
        this.courseRepo.storeToFile();
        this.studentRepo.storeToFile();
    }

    /**
     * This function sets the teacher for course to null and 
     * removes the course from all enrolled students
     * @param name is the name of the course
     */
    public void deleteTeacher(String name) throws ObjectNotFoundException {
        if(this.courseRepo.findByName(name)==null)
            throw new ObjectNotFoundException("Object was not found in repository");
        Course c = this.courseRepo.findByName(name); // search the wanted course
        c.getTeacher().getCourses().remove(c);
        c.setTeacher(null);
        for (Student s : c.getStudentsEnrolled()) {
            ArrayList<Course> newListCourse = (ArrayList<Course>) s.getEnrolledCourses();
            newListCourse.remove(c);
            s.setEnrolledCourses(newListCourse);
        }
        this.courseRepo.storeToFile();
    }

    /**
     * @return list of students sorted by last name
     */
    public ArrayList<Student> sortStudentsByName()
    {
        ArrayList<Student> newList = (ArrayList<Student>) studentRepo.findAll();
        newList.sort(new Student.NameSorter());
        return newList;
    }

    /**
     * @return list of courses sorted by name
     */
    public ArrayList<Course> sortCoursesByName()
    {
        ArrayList<Course> newList = (ArrayList<Course>) courseRepo.findAll();
        newList.sort(new Course.NameSorter());
        return newList;
    }

    /**
     * The filterStudentsByCredits() function filters the list of students using
     * the byCredits predicate (using stream)
     * Firstly we verify if the number of total credits lower than 15 is
     * @return a list of students who have less than 15 credits
     */
    public ArrayList<Student> filterStudentsByCredits() {
        Predicate<Student> byCredits = student -> student.getTotalCredits() < 15;
        ArrayList<Student> newList = (ArrayList<Student>) studentRepo.findAll();
        return (ArrayList<Student>) newList.stream().filter(byCredits).collect(Collectors.toList());
    }

    /**
     * Function filterCoursesByCredit filters the list of courses using the
     * byCredits predicate (using stream)
     * @return a list of courses that have less than a specified number of credits
     */
    public ArrayList<Course> filterCoursesByCredit(int credits) {
        Predicate<Course> byCredits = course -> course.getCredit() < credits;
        ArrayList<Course> newList = (ArrayList<Course>) courseRepo.findAll();
        return (ArrayList<Course>) newList.stream().filter(byCredits).collect(Collectors.toList());
    }

}

