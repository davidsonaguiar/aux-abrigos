package com.compass.item.services;

import com.compass.center.CenterEntity;
import com.compass.center.CenterService;
import com.compass.common.exception.BadRequestException;
import com.compass.common.exception.ContentConflictException;
import com.compass.common.exception.DaoException;
import com.compass.common.exception.NotFoundException;
import com.compass.item.ItemDao;
import com.compass.item.dtos.ItemDto;
import com.compass.item.entities.ItemClothingEntity;
import com.compass.item.enums.CategoryItem;
import jakarta.persistence.NoResultException;
import org.hibernate.exception.ConstraintViolationException;

public class ItemClothingService extends ItemService {
    public ItemClothingService(ItemDao itemDao, CenterService centerService) {
        super(itemDao, centerService);
    }

    @Override
    public ItemClothingEntity save(ItemDto item) {
        try {
            CenterEntity existsCapacityForCategory = centerService.existsCapacityForCategoryItem(
                    item.centerId(), item.quantity(), CategoryItem.ROUPA);
            if (existsCapacityForCategory == null) throw new BadRequestException("Capacidade do centro para roupas excedida");

            ItemClothingEntity newItem = new ItemClothingEntity();
            newItem.setDescription(item.description());
            newItem.setQuantity(item.quantity());
            newItem.setCenter(existsCapacityForCategory);
            newItem.setSize(item.size());
            newItem.setGender(item.gender());

            return (ItemClothingEntity) this.itemDao.save(newItem);
        }
        catch (ConstraintViolationException exception) {
            throw new ContentConflictException("Existe conflito de conteúdo");
        }
        catch (NoResultException exception) {
            throw new NotFoundException("Centro não encontrado");
        }
        catch (BadRequestException exception) {
            throw exception;
        }
        catch (Exception exception) {
            throw new DaoException("Erro ao salvar item");
        }
    }
}
