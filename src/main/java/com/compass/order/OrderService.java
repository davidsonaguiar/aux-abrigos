package com.compass.order;

import com.compass.center.CenterEntity;
import com.compass.center.CenterService;
import com.compass.center.dtos.CenterResponseDto;
import com.compass.common.exception.DaoException;
import com.compass.common.exception.NotFoundException;
import com.compass.item.ItemEntity;
import com.compass.order.dtos.CreateOrderRequestDto;
import com.compass.order.enums.StatusOrder;
import com.compass.shelter.ShelterEntity;
import com.compass.shelter.ShelterService;
import com.compass.shelter.dtos.ShelterResponseDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private final OrderDao orderDao;
    private final ShelterService shelterService;
    private final CenterService centerService;

    public OrderService(OrderDao orderDao, ShelterService shelterService, CenterService centerService) {
        this.orderDao = orderDao;
        this.shelterService = shelterService;
        this.centerService = centerService;
    }

    public OrderEntity findOrderById(Long id) {
        try {
            return orderDao.findById(id);
        } catch (DaoException exception) {
            throw exception;
        }
    }

    public List<OrderEntity> findAll() {
        try {
            return orderDao.findAll();
        } catch (DaoException exception) {
            throw exception;
        }
    }

    public OrderEntity save(CreateOrderRequestDto order) throws NotFoundException, DaoException {
        try {
            ShelterEntity shelter = shelterService.findShelterById(order.shelterId());
            if(shelter == null) throw new NotFoundException("Abrigo não encontrado.");

            List<CenterEntity> centerEntities = new ArrayList<>();

            List<CenterResponseDto> centers = order.centersRequest();
            for(CenterResponseDto center : centers) {
                CenterEntity centerEntity = centerService.findById(center.id());
                if(centerEntity == null) throw new NotFoundException("Centro não encontrado: " + center.id() + ".");
                centerEntities.add(centerEntity);
            }

            ItemEntity item = new ItemEntity(
                    null,
                    null,
                    order.item().category(),
                    null,
                    null,
                    order.item().size(),
                    order.item().gender(),
                    order.item().quantity(),
                    order.item().expirationDate(),
                    order.item().unit(),
                    order.item().hygieneType()
            );

            OrderEntity orderEntity = new OrderEntity(
                    null,
                    LocalDate.now(),
                    order.quantity(),
                    StatusOrder.PEDENTE,
                    shelter,
                    item,
                    null,
                    centerEntities
            );

            return orderDao.save(orderEntity);
        }
        catch (NotFoundException exception) {
            throw exception;
        }
    }

    public OrderEntity update(OrderEntity order) {
        try {
            return orderDao.update(order);
        }
        catch (NotFoundException exception) {
            throw new NotFoundException(exception.getMessage());
        }
        catch (DaoException exception) {
            throw exception;
        }
    }
}
