package Repository;

import java.util.ArrayList;
import java.util.List;
/**
  * InMemoryRepo extends the ICrudRepo interface
  * Here are defined the methods from the specified interface
  * Date 15.11.2021
  */
public abstract class InMemoryRepo<T> implements ICrudRepo<T> {
    protected List<T> listRepo;
    public InMemoryRepo() {
        listRepo = new ArrayList<>();
    }

    /**
     * @param entity is the entity to be returned and must be different from null
     * @return the specified entity or null when if there is no such entity
     */
    @Override
    public T findOne(T entity) {
        for(T t:listRepo)
        {
            if(t.equals(entity))
                return t;
        }
        return null;
    }

    /**
     *  @return all entities
     */
    @Override
    public Iterable<T> findAll() {
        return listRepo;
    }

    /**
     * @param entity entity must be not null
     * @return null- if the given entity is saved otherwise returns the entity (id already exists)
     */
    @Override
    public T save(T entity) {
        for(T t:listRepo)
        {
            if(t.equals(entity))
                return t;
        }
        listRepo.add(entity);
        return null;
    }

    /**
     * removes the entity with the specified id
     * @param entity must be not null
     * @return the removed entity or null if there is no entity with the given id
     */
    @Override
    public T delete(T entity) {
        for(T t: listRepo){
            if(t.equals(entity)) {
                listRepo.remove(t);
                return t;
            }
        }
        return null;

    }
}
