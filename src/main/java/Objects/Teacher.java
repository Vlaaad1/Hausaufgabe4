package Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Class Teacher is a subclass of Person;
 * Teacher inherited the firstName and lastName attributes from Person and also
 * has one additional attribute:  courses ( a list of courses)
 * Date: 15.11.2021
 */
@JsonIgnoreProperties({"courses"})
public class Teacher extends Person {
    private List<Course> courses;

    /**
     * Class constructor.
     * @param firstName is the name of the teacher
     * @param lastName is the last name of the teacher
     * @param courses is the list of courses
     */
    public Teacher(String firstName, String lastName, List<Course> courses) {
        super(firstName, lastName); this.courses = courses;
    }

    public List<Course> getCourses() {return courses;}
    public void setCourses(List<Course> courses) {this.courses = courses;}

    /**
     * @param c is a new course that we want to add to the teacher's list of courses
     */
    public void addCourse(Course c){
        List<Course> newList = this.getCourses();
        newList.add(c);
        this.setCourses(newList);
    }
}