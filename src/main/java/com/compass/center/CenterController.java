package com.compass.center;

import java.util.List;

public class CenterController {
    private final CenterService centerService;

    public CenterController(CenterService centerService) {
        this.centerService = centerService;
    }

    public List<CenterEntity> listAll() {
        return centerService.findAll();
    }

    public CenterEntity getById(Long id) {
        return centerService.findById(id);
    }
}
