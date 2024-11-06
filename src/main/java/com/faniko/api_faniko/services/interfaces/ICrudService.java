package com.faniko.api_faniko.services.interfaces;

import com.faniko.api_faniko.exceptions.NotFoundException;
import com.faniko.api_faniko.models.base.BaseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ICrudService<T extends BaseEntity, R extends MongoRepository<T, String>> {

    /**
     * Get the repository for the entity
     * @return the repository {@link MongoRepository}
     */
    R getRepository();

    /**
     * Find an entity by id
     * @param id {@link Integer}
     * @return the entity {@link T}
     */
    default T find(String id) {
        return getRepository().findById(id).orElseThrow(
                () -> new NotFoundException("Entity not found with id: " + id)
        );
    }

    /**
     * Create an entity
     * @param entity {@link T}
     * @return the created entity {@link T}
     */
    default T create(T entity) {
        entity.setId(null);
        return getRepository().save(entity);
    }

    /**
     * Update an entity
     * @param id {@link Integer}
     * @param entity {@link T}
     * @return the updated entity {@link T}
     */
    default T update(String id, T entity) {
        T entityToUpdate = find(id);
        entityToUpdate = formatEntityToUpdate(entityToUpdate, entity);

        return getRepository().save(entityToUpdate);
    }

    /**
     * Delete an entity
     * @param id {@link Integer}
     */
    default void delete(String id) {
        getRepository().delete(find(id));
    }

    /**
     * Get all entities
     * @return the list of entities {@link List<T>}
     */
    default List<T> getAll() {
        return getRepository().findAll();
    }

    /**
     * Format the entity to update, it used to map the entity to update with the new entity
     * @param entityToUpdate {@link T}
     * @param entity {@link T}
     * @return the entity to update {@link T}
     */
    T formatEntityToUpdate(T entityToUpdate, T entity);
}
