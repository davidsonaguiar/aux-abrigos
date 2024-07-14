package com.compass.center;

import com.compass.center.dtos.CenterResponseDto;
import com.compass.common.Response;
import com.compass.common.exception.DaoException;
import com.compass.common.exception.NotFoundException;

import java.util.List;

public class CenterController {
    private final CenterService centerService;

    public CenterController(CenterService centerService) {
        this.centerService = centerService;
    }

    public Response<List<CenterResponseDto>> listAll() {
        try {
            List<CenterEntity> centers = centerService.findAll();
            return new Response<>(CenterResponseDto.fromList(centers), "Centros listados com sucesso");
        }
        catch (NotFoundException | DaoException exception) {
            return new Response<>(null, exception.getMessage());
        }
    }

    public Response<CenterResponseDto> getById(Long id) {
        try {
            CenterEntity centers = centerService.findById(id);
            return new Response<>(CenterResponseDto.fromEntity(centers), "Centro encontrado com sucesso");
        }
        catch (NotFoundException | DaoException exception) {
            return new Response<>(null, exception.getMessage());
        }
    }
}
