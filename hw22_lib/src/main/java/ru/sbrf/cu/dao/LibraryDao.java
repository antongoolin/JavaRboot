package ru.sbrf.cu.dao;

import ru.sbrf.cu.model.BaseEntity;

import java.util.List;
import java.util.Optional;

public interface LibraryDao {
    <T extends BaseEntity> void deleteById(Class<T> clazz, long id);
    <T extends BaseEntity> List<T> getListOfEntity(Class<T> clazz);
    <T extends BaseEntity> List<T> findByName(Class<T> clazz, String name);
    <T extends BaseEntity> Optional<T> findById(Class<T> clazz, long id);
    <T extends BaseEntity> T saveEntity(T entity);
}