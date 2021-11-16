package Repository;

/**
 * Interface where we declare the functionalities related to file operations for this project
 * @param <E> can be any model From this project (Course, Person, Student or Teacher)
 * Date 15.11.2021
 */
public interface IFileRepo<E>{
    /**
     * Function storeToFile saves InMemoryRepo contents to JSON file
     */
    void storeToFile();

    /**
     * Function loadFromFile loads data from JSON file
     */
    void loadFromFile(Class<E[]> c);

    /**
     * @param _filename ist ein file where we store/load our data
     */
    void setFile(String _filename);
}