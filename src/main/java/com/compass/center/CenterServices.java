package com.compass.center;

import com.compass.common.exception.ContentConflictException;
import com.compass.common.exception.DaoException;
import com.compass.common.exception.NotFoundException;
import jakarta.persistence.NoResultException;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

public class CenterServices {
    private CenterDao centerDao;

    public CenterServices(CenterDao centerDao) {
        this.centerDao = centerDao;
    }

    public void capacityIsGreaterThanthousand(CenterEntity centerEntity) {
        if (centerEntity.getCapacity() < 1000) {
            throw new IllegalArgumentException("Capacidade mínima do centro é 1000");
        }
    }

    public CenterEntity save(CenterEntity centerEntity) throws ContentConflictException, DaoException {
        try {
            capacityIsGreaterThanthousand(centerEntity);
            return centerDao.save(centerEntity);
        }
        catch (ConstraintViolationException exception) {
            throw new ContentConflictException("Nome do centro já existe");
        }
        catch (Exception exception) {
            throw new DaoException("Erro ao salvar centro");
        }
    }

    public CenterEntity findById(Long id) throws DaoException {
        try {
            return centerDao.findById(id);
        }
        catch (NoResultException exception) {
            return null;
        }
        catch (Exception exception) {
            throw new DaoException("Erro ao buscar centro");
        }
    }

    public List<CenterEntity> findAll() {
        try {
            return centerDao.findAll();
        }
        catch (NoResultException exception) {
            return List.of();
        }
        catch (Exception exception) {
            throw new DaoException("Erro ao buscar centros");
        }
    }

    public CenterEntity update(CenterEntity centerEntity) throws NotFoundException, ContentConflictException, DaoException {
        try {
            capacityIsGreaterThanthousand(centerEntity);
            return centerDao.update(centerEntity);
        }
        catch (NoResultException exception) {
            throw new NotFoundException("Centro não encontrado");
        }
        catch (ConstraintViolationException exception) {
            throw new ContentConflictException("Nome do centro já existe");
        }
        catch (Exception exception) {
            throw new DaoException("Erro ao atualizar centro");
        }
    }

    public void delete(Long id) throws NotFoundException, DaoException {
        try {
            centerDao.deleteById(id);
        }
        catch (NoResultException exception) {
            throw new NotFoundException("Centro não encontrado");
        }
        catch (Exception exception) {
            throw new DaoException("Erro ao deletar centro");
        }
    }
}
