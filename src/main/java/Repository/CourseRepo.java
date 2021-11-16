package Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import Objects.Course;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Class CourseRepo extends InMemoryRepo so can access all functions from InMemoryRepo
 * It also implements an interface that allow us to store and load data from a specified file
 * Date 15.11.2021
 */
public class CourseRepo extends InMemoryRepo<Course> implements IFileRepo<Course>{
    public CourseRepo(){
        super();
    }
    protected String filename;
    /**
     * @param entity entity must not be null
     * @return null - if the entity is updated, otherwise returns the entity
     * (for example when this entity does not exist).
     */
    @Override
    public Course update(Course entity) {
        for(Course c:listRepo) {
            if (c.equals(entity)){
                c.setCredit(entity.getCredit());
                c.setMaxEnrollment(entity.getMaxEnrollment());
                c.setTeacher(entity.getTeacher());
                c.setStudentsEnrolled(entity.getStudentsEnrolled());
                return null;
            }
        }
        return entity;
    }

    /**
     * @param name is a string that represents the name of a course
     * @return the course with called 'name'
     */
    public Course findByName(String name){
        for(Course t:listRepo)
        {
            if(Objects.equals(t.getName(), name))
                return t;
        }
        return null;
    }

    /**
     * Function storeToFile saves the list of courses to a JSON file
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
     * Function loadFromFile loads the list of courses from a JSON file
     */
    @Override
    public void loadFromFile(Class<Course[]> c){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Course> aux= Arrays.asList(objectMapper.readValue(new File(filename),c));
            listRepo.addAll(aux);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param _filename ist ein file where we store/load our data
     */
    @Override
    public void setFile(String _filename) {
        filename=_filename;
    }

}