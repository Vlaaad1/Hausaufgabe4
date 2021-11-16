package Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import Objects.Student;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Class StudentRepo extends InMemoryRepo so can access all functions from InMemoryRepo
 * It also implements an interface that allow us to store and load data from a specified file
 * Date 15.11.2021
 */
public class StudentRepo extends InMemoryRepo<Student> implements IFileRepo<Student>{
    public StudentRepo(){
        super();
    }
    protected String filename;

    /**
     * @param entity entity must not be null
     * @return null - if the entity is updated, otherwise returns the entity
     * (for example when it doesn't exist).
     */
    @Override
    public Student update(Student entity){
        for(Student s: listRepo) {
            if (s.equals(entity)){
                s.setStudentID(entity.getStudentID());
                s.setEnrolledCourses(entity.getEnrolledCourses());
                s.setTotalCredits(entity.getTotalCredits());
                return null;
            }
        }
        return entity;
    }

    /**
     * findByID searches in the list of students for the one with the specified id
     * @param id is the id of a student
     * @return the student with that specific id
     */
    public Student findByID(long id){
        for(Student t:listRepo)
        {
            if(t.getStudentID() == id)
                return t;
        }
        return null;
    }

    /**
     * stores to students.json the students from the repository list
     */
    @Override
    public void storeToFile() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(filename), listRepo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * loads the students from the students.json file
     */
    @Override
    public void loadFromFile(Class<Student[]> c) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Student> aux= Arrays.asList(objectMapper.readValue(new File(filename),c));
            for(Student elem:aux)
                listRepo.add(elem);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param _filename is the name of the file where we save the students
     */
    @Override
    public void setFile(String _filename) {
        filename=_filename;
    }
}