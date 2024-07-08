package com.compass.center;

import com.compass.common.exception.ContentConflictException;
import com.compass.common.exception.NotFoundException;

public class CenterServices {
    private CenterDao centerDao;

    public CenterServices(CenterDao centerDao) {
        this.centerDao = centerDao;
    }

    public CenterEntity findById(Long id) {
        return centerDao.findById(id);
    }
}
