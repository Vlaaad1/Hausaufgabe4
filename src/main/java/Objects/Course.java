package Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Date: 15.11.2021
 */
@JsonIgnoreProperties({"studentsEnrolled"})
public class Course {
    private String name;
    private Teacher teacher;
    private int maxEnrollment;
    private List<Student> studentsEnrolled;
    private int credit;

    /**
     * Class constructor.
     * @param name is the name of the course
     * @param teacher is the name of the teacher assigned to the course
     * @param maxEnrollment is the no of maximum students
     * @param studentsEnrolled is the list of enrolled students
     * @param credit is the no of credits for this course
     */
    public Course(String name, Teacher teacher, int maxEnrollment, List<Student> studentsEnrolled, int credit) {
        this.name = name;
        this.teacher = teacher;
        this.maxEnrollment = maxEnrollment;
        this.studentsEnrolled = studentsEnrolled;
        this.credit = credit;
        ArrayList<Course> newList = (ArrayList<Course>) teacher.getCourses();
        newList.add(this);
        teacher.setCourses(newList);
    }

    //getters and setters for all attributes
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public Teacher getTeacher() {return teacher;}
    public void setTeacher(Teacher teacher) {this.teacher = teacher;}
    public int getMaxEnrollment() {return maxEnrollment;}
    public void setMaxEnrollment(int maxEnrollment) {this.maxEnrollment = maxEnrollment;}
    public List<Student> getStudentsEnrolled() {return studentsEnrolled;}
    public void setStudentsEnrolled(List<Student> studentsEnrolled) {this.studentsEnrolled = studentsEnrolled;}
    public int getCredit() {return credit;}
    public void setCredit(int credit) {this.credit = credit;}

    /**
     *
     * @param o object to be compared
     * @return boolean value resulted by comparing the names of two courses
     * 2 courses are equal only if they have the same name
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(name, course.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * @return a string containing the name of the course, the name of the teacher,
     * maximum no of students and the number of credits
     */
    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", teacher=" + teacher +
                ", maxEnrollment=" + maxEnrollment +
                ", credit=" + credit +
                '}';
    }

    /**
     * Class NameSorter implements Comparator interface and
     * defines the comparison method for Course
     * (lexicographically ordered by Name)
     */
    public static class NameSorter implements Comparator<Course> {
        @Override
        public int compare(Course c1, Course c2){
            return c1.getName().compareToIgnoreCase(c2.getName());
        }
    }

}