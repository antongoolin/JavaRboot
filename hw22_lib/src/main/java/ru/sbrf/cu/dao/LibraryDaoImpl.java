package ru.sbrf.cu.dao;

import org.springframework.stereotype.Repository;
import ru.sbrf.cu.model.BaseEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class LibraryDaoImpl implements LibraryDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public <T extends BaseEntity> List<T> getListOfEntity(Class<T> clazz) {
        TypedQuery<T> query = em.createQuery("select e from " + clazz.getName() + " e", clazz);
        return query.getResultList();
    }

    @Override
    public <T extends BaseEntity> Optional<T> findById(Class<T> clazz, long id) {
        return Optional.ofNullable(em.find(clazz, id));
    }

    @Override
    public <T extends BaseEntity> T saveEntity(T entity) {
        if (entity.getId() <= 0) {
            em.persist(entity);
            return entity;
        } else {
            return em.merge(entity);
        }
    }

    private <T extends BaseEntity> List<T> findByColumnValue(Class<T> clazz, String columnName, Object value) {
        TypedQuery<T> query = em.createQuery("select e from " + clazz.getName() + " e where " +
                columnName + " = :value", clazz);
        query.setParameter("value", value);
        List<T> result = query.getResultList();
        return (result != null ? result : Collections.EMPTY_LIST);
    }

    @Override
    public <T extends BaseEntity> List<T> findByName(Class<T> clazz, String name) {
        return findByColumnValue(clazz, "name", name);
    }

    @Override
    public <T extends BaseEntity> void deleteById(Class<T> clazz, long id) {
        findById(clazz, id).ifPresentOrElse(s -> em.remove(s), () -> {
            throw new LibraryDaoException("В книге учета нет информации о записи");
        });
    }
}
