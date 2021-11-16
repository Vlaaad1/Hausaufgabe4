package Controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import Exception.ObjectNotFoundException;
import Objects.Student;
import Objects.Teacher;
import Objects.Course;
import Repository.StudentRepo;
import Repository.CourseRepo;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @Test
    void register() throws ObjectNotFoundException {
        Student s1 = new Student("John", "Wick", 227, new ArrayList<>());
        Teacher t1 = new Teacher("Ellie", "Clarke", new ArrayList<>());
        Course c1 = new Course("BD", t1, 50, new ArrayList<>(), 5);
        Course c2 = new Course("MAP", t1, 50, new ArrayList<>(), 5);
        StudentRepo studentRepo = new StudentRepo();
        CourseRepo courseRepo = new CourseRepo();
        Controller controller = new Controller(studentRepo, courseRepo);
        studentRepo.save(s1);
        courseRepo.save(c1);
        courseRepo.save(c2);
        assertTrue(controller.register(s1.getStudentID(), c1.getName()));
        assertFalse(controller.register(s1.getStudentID(), c2.getName()));
    }

    @Test
    void retrieveCourses() {
        Student s1 = new Student("John", "Wick", 227, new ArrayList<>());
        Teacher t1 = new Teacher("Ellie", "Clarke", new ArrayList<>());
        Course c1 = new Course("BD", t1, 60, new ArrayList<>(), 6);
        Course c2 = new Course("MAP", t1, 100, new ArrayList<>(), 6);
        StudentRepo studentRepo = new StudentRepo();
        CourseRepo courseRepo = new CourseRepo();
        Controller controller = new Controller(studentRepo, courseRepo);
        studentRepo.save(s1);
        courseRepo.save(c1);
        courseRepo.save(c2);
        assertEquals(controller.retrieveCourses().get(0).getName(), "BD");
        assertEquals(controller.retrieveCourses().get(1).getCredit(), 6);
    }

    @Test
    void retrieveStudents() throws ObjectNotFoundException {
        Student s1 = new Student("John", "Wick", 227, new ArrayList<>());
        Student s2 = new Student("James", "Bond", 777, new ArrayList<>());
        Teacher t1 = new Teacher("Ellie", "Clarke", new ArrayList<>());
        Course c1 = new Course("WS", t1, 200, new ArrayList<>(), 6);
        Course c2 = new Course("CN", t1, 115, new ArrayList<>(), 4);
        StudentRepo studentRepo = new StudentRepo();
        CourseRepo courseRepo = new CourseRepo();
        studentRepo.save(s1);
        studentRepo.save(s2);
        courseRepo.save(c1);
        courseRepo.save(c2);
        Controller controller = new Controller(studentRepo, courseRepo);
        controller.register(s1.getStudentID(), c1.getName());
        controller.register(s2.getStudentID(), c1.getName());
        controller.register(s2.getStudentID(), c2.getName());
        assertEquals(controller.retrieveStudents(c1.getName()).size(), 2);
        assertEquals(controller.retrieveStudents(c2.getName()).size(), 1);

    }

    @Test
    void getAllCourses() {
        Student s1 = new Student("John", "Wick", 227, new ArrayList<>());
        Teacher t1 = new Teacher("Ellie", "Clarke", new ArrayList<>());
        Course c1 = new Course("BD", t1, 60, new ArrayList<>(), 3);
        Course c2 = new Course("WS", t1, 100, new ArrayList<>(), 6);
        Course c3 = new Course("MAP", t1, 100, new ArrayList<>(), 6);
        StudentRepo studentRepo = new StudentRepo();
        CourseRepo courseRepo = new CourseRepo();
        studentRepo.save(s1);
        courseRepo.save(c1);
        courseRepo.save(c2);
        courseRepo.save(c3);
        Controller controller = new Controller(studentRepo, courseRepo);
        assertEquals(controller.getAllCourses().size(), 3);
        assertEquals(controller.getAllCourses().get(1).getCredit(), 6);
        assertEquals(controller.getAllCourses().get(2).getName(), "MAP");
    }

    @Test
    void updateCredits() throws ObjectNotFoundException{
        Student s1 = new Student("John", "Wick", 227, new ArrayList<>());
        Teacher t1 = new Teacher("Ellie", "Clarke", new ArrayList<>());
        Course c1 = new Course("MAP", t1, 600, new ArrayList<>(), 12);
        StudentRepo studentRepo = new StudentRepo();
        CourseRepo courseRepo = new CourseRepo();
        studentRepo.save(s1);
        courseRepo.save(c1);
        Controller controller = new Controller(studentRepo, courseRepo);
        controller.register(s1.getStudentID(),c1.getName());
        controller.updateCredits(c1.getName(), 10);
        assertEquals(s1.getTotalCredits(), 10);
    }

    @Test
    void deleteTeacher() throws ObjectNotFoundException {
        Student s1 = new Student("John", "Wick", 227, new ArrayList<>());
        Teacher t1 = new Teacher("Ellie", "Clarke", new ArrayList<>());
        Course c1 = new Course("CN", t1, 30, new ArrayList<>(), 3);
        StudentRepo studentRepo = new StudentRepo();
        CourseRepo courseRepo = new CourseRepo();
        studentRepo.save(s1);
        courseRepo.save(c1);
        Controller controller = new Controller(studentRepo, courseRepo);
        controller.deleteTeacher(c1.getName());
        assertEquals(s1.getEnrolledCourses().size(), 0);
    }

    @Test
    void sortStudentsByName() {
        Student s1 = new Student("John" , "Wick" , 227 , new ArrayList<>());
        Student s2 = new Student("James" , "Bond" , 777 , new ArrayList<>());
        StudentRepo studentRepo = new StudentRepo();
        studentRepo.save(s1);
        studentRepo.save(s2);
        Teacher t1 = new Teacher("Ellie", "Clarke", new ArrayList<>());
        Course c1 = new Course("BD", t1, 60, new ArrayList<>(), 3);
        CourseRepo courseRepo = new CourseRepo();
        courseRepo.save(c1);
        Controller controller = new Controller(studentRepo , courseRepo);
        controller.sortStudentsByName();
        assertEquals(controller.getAllStudents().get(0).getLastName(), "Bond");
        assertEquals(controller.getAllStudents().get(1).getLastName(), "Wick");
    }
    @Test
    void sortCoursesByName() throws ObjectNotFoundException {
        Student s1 = new Student("James" , "Bond" , 777 , new ArrayList<>());
        StudentRepo studentRepo = new StudentRepo();
        studentRepo.save(s1);
        Teacher t1 = new Teacher("Ellie", "Clarke", new ArrayList<>());
        Course c1 = new Course("BD", t1, 60, new ArrayList<>(), 3);
        Course c2 = new Course("WS", t1, 100, new ArrayList<>(), 5);
        Course c3 = new Course("MAP", t1, 100, new ArrayList<>(), 6);
        CourseRepo courseRepo = new CourseRepo();
        courseRepo.save(c1);
        courseRepo.save(c2);
        courseRepo.save(c3);
        Controller controller = new Controller(studentRepo , courseRepo);
        controller.register(s1.getStudentID(), c1.getName());
        controller.register(s1.getStudentID(), c2.getName());
        controller.register(s1.getStudentID(), c3.getName());
        controller.sortCoursesByName();
        assertEquals(controller.getAllCourses().get(0).getName(), "BD");
        assertEquals(controller.getAllCourses().get(1).getName(), "MAP");
        assertEquals(controller.getAllCourses().get(2).getName(), "WS");
    }

    @Test
    void filterStudentsByCredits() throws ObjectNotFoundException {
        Student s1 = new Student("John" , "Wick" , 227 , new ArrayList<>());
        Student s2 = new Student("James" , "Bond" , 777 , new ArrayList<>());
        StudentRepo studentRepo = new StudentRepo();
        studentRepo.save(s1);
        studentRepo.save(s2);
        Teacher t1 = new Teacher("Ellie", "Clarke", new ArrayList<>());
        Course c1 = new Course("BD", t1, 60, new ArrayList<>(), 3);
        Course c2 = new Course("WS", t1, 100, new ArrayList<>(), 6);;
        CourseRepo courseRepo = new CourseRepo();
        courseRepo.save(c1);
        courseRepo.save(c2);
        Controller controller = new Controller(studentRepo , courseRepo);
        controller.register(s1.getStudentID(), c1.getName());
        controller.register(s1.getStudentID(), c2.getName());
        controller.register(s2.getStudentID(), c1.getName());
        controller.filterStudentsByCredits();
        assertEquals(controller.getAllStudents().get(1).getLastName(), "Wick");
    }

    @Test
    void filterCoursesByCredit() throws ObjectNotFoundException {
        Student s1 = new Student("John" , "Wick" , 227 , new ArrayList<>());
        Student s2 = new Student("James" , "Bond" , 777 , new ArrayList<>());
        StudentRepo studentRepo = new StudentRepo();
        studentRepo.save(s1);
        studentRepo.save(s2);
        Teacher t1 = new Teacher("Ellie", "Clarke", new ArrayList<>());
        Course c1 = new Course("BD", t1, 60, new ArrayList<>(), 3);
        Course c2 = new Course("WS", t1, 100, new ArrayList<>(), 5);
        Course c3 = new Course("MAP", t1, 100, new ArrayList<>(), 6);
        CourseRepo courseRepo = new CourseRepo();
        courseRepo.save(c1);
        courseRepo.save(c2);
        courseRepo.save(c3);
        Controller controller = new Controller(studentRepo , courseRepo);
        controller.register(s1.getStudentID(), c1.getName());
        controller.register(s1.getStudentID(), c2.getName());
        controller.register(s2.getStudentID(), c1.getName());
        controller.register(s2.getStudentID(), c2.getName());
        controller.filterCoursesByCredit(6);
        assertEquals(controller.getAllCourses().get(0).getName(), "BD");
        assertEquals(controller.getAllCourses().get(1).getName(), "WS");
    }
}