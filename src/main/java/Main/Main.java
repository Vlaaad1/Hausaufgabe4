package Main;

import Controller.Controller;
import Exception.ObjectNotFoundException;
import Objects.Course;
import Objects.Student;
import Objects.Teacher;
import Repository.CourseRepo;
import Repository.StudentRepo;
import UserInterface.ConsoleUI;

import java.util.ArrayList;

public class Main {
        public static void main(String[] args) throws ObjectNotFoundException {
            Student s1 = new Student("Dorian", "Popa", 777, new ArrayList<>());
            Teacher t1 = new Teacher("Marian", "Coman", new ArrayList<>());
            Course c1 = new Course("BD", t1, 60, new ArrayList<>(), 6);
            Course c2 = new Course("MAP", t1, 100, new ArrayList<>(), 6);
            StudentRepo sRepo = new StudentRepo();
            sRepo.setFile("students.json");
            sRepo.save(s1);
            sRepo.storeToFile();
            CourseRepo cRepo = new CourseRepo();
            cRepo.setFile("courses.json");
            cRepo.save(c1);
            cRepo.save(c2);
            cRepo.storeToFile();
            Controller control = new Controller(sRepo, cRepo);
            ConsoleUI ui = new ConsoleUI(control);
            ui.display();
        }
}

