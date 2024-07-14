package com.compass.center;

import com.compass.common.exception.ContentConflictException;
import com.compass.common.exception.DaoException;
import com.compass.common.exception.NotFoundException;
import com.compass.item.enums.CategoryItem;
import jakarta.persistence.NoResultException;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

public class CenterService {
    private CenterDao centerDao;

    public CenterService(CenterDao centerDao) {
        this.centerDao = centerDao;
    }

    public CenterEntity save(CenterEntity centerEntity) throws ContentConflictException, DaoException {
        try {
            centerEntity.setCapacity(1000);
            return centerDao.save(centerEntity);
        }
        catch (ConstraintViolationException exception) {
            throw new ContentConflictException("Nome do centro já existe");
        }
        catch (Exception exception) {
            throw new DaoException("Erro ao salvar centro");
        }
    }

    public CenterEntity findById(Long id) throws DaoException, NotFoundException {
        try {
            CenterEntity center = centerDao.findById(id);
            if (center == null) throw new NotFoundException("Centro não encontrado");
            return center;
        }
        catch (NoResultException exception) {
            throw exception;
        }
        catch (DaoException exception) {
            throw exception;
        }
        catch (Exception exception) {
            throw exception;
        }
    }

    public List<CenterEntity> findAll() {
        try {
            return centerDao.findAll();
        }
        catch (NoResultException exception) {
            throw new NotFoundException("Nenhum centro cadastrado");
        }
        catch (Exception exception) {
            throw new DaoException("Erro ao buscar centros no banco de dados");
        }
    }

    public CenterEntity update(CenterEntity centerEntity) throws NotFoundException, ContentConflictException, DaoException {
        try {
            centerEntity.setCapacity(1000);
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
            throw exception;
        }
    }
}
