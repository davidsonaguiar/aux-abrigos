package com.compass.item;

import com.compass.common.Response;
import com.compass.common.exception.ContentConflictException;
import com.compass.common.exception.DaoException;
import com.compass.common.exception.NotFoundException;
import com.compass.item.dtos.ItemDto;

public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    public Response<ItemDto> update(ItemDto itemDto) {
        try {
            ItemEntity response = itemService.update(itemDto);
            ItemDto item = ItemDto.fromEntity(response);
            return new Response<>(item, "Item atualizado com sucesso.");
        }
        catch (NotFoundException exception) {
            throw exception;
        }
        catch (ContentConflictException exception) {
            throw exception;
        }
        catch (DaoException exception) {
            throw exception;
        }
    }

    public Response<ItemDto> delete(Long id) {
        try {
            ItemDto item = itemService.delete(id);
            return new Response<>(item, "Item deletado com sucesso.");
        }
        catch (NotFoundException exception) {
            return new Response<>(null, "Item não encontrado: " + exception.getMessage());
        }
        catch (DaoException exception) {
           return new Response<>(null, "Erro ao deletar item: " + exception.getMessage());
        }
    }
}
