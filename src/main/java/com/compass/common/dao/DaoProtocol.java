package com.compass.common.dao;

public interface DaoProtocol<Entity, ID> {
    Entity save(Entity entity);
    Entity findById(ID id);
    Iterable<Entity> findAll();
    Entity update(Entity entity);
    void deleteById(ID id);
}
