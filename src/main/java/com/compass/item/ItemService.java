package com.compass.item;

import com.compass.center.CenterDao;
import com.compass.center.CenterServices;
import com.compass.common.exception.BadRequestException;
import com.compass.common.exception.DaoException;
import com.compass.common.exception.NotFoundException;

public class ItemService {
    private final ItemDao itemDao;
    private final CenterServices centerServices;

    public ItemService(ItemDao itemDao, CenterServices centerServices) {
        this.itemDao = itemDao;
        this.centerServices = centerServices;
    }

    public ItemEntity save(ItemEntity item) {
        try {
            if(centerServices.existsCapacityForItemType(item.getCenter().getId(), item.getQuantity(), item.getType())) {
                return itemDao.save(item);
            }
            throw new BadRequestException("Capacidade do centro insuficiente para o tipo de item: " + item.getType());
        }
        catch (NotFoundException exception) {
            throw new NotFoundException(exception.getMessage());
        }
        catch (BadRequestException exception) {
            throw new BadRequestException(exception.getMessage());
        }
        catch (Exception exception) {
            throw new DaoException("Erro ao salvar item", exception);
        }
    }
}
