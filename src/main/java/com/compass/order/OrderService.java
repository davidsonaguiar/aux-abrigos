package com.compass.order;

import com.compass.center.CenterEntity;
import com.compass.center.CenterService;
import com.compass.center.dtos.CenterResponseDto;
import com.compass.common.exception.DaoException;
import com.compass.common.exception.NotFoundException;
import com.compass.item.ItemEntity;
import com.compass.item.ItemService;
import com.compass.order.dtos.CreateOrderRequestDto;
import com.compass.order.dtos.OrderResponseDto;
import com.compass.order.enums.StatusOrder;
import com.compass.order_center.OrderCenterEntity;
import com.compass.order_center.OrderCenterService;
import com.compass.shelter.ShelterEntity;
import com.compass.shelter.ShelterService;
import jakarta.persistence.NoResultException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private final OrderDao orderDao;
    private final ShelterService shelterService;
    private final CenterService centerService;
    private final OrderCenterService orderCenterService;


    public OrderService(OrderDao orderDao, ShelterService shelterService, CenterService centerService, OrderCenterService orderCenterService) {
        this.orderDao = orderDao;
        this.shelterService = shelterService;
        this.centerService = centerService;
        this.orderCenterService = orderCenterService;
    }

    public OrderEntity findOrderById(Long id) {
        try {
            return orderDao.findById(id);
        } catch (DaoException exception) {
            throw exception;
        }
    }

    public List<OrderEntity> findAll() throws NotFoundException, DaoException {
        try {
            return orderDao.findAll();
        }
        catch (NoResultException exception) {
            throw new NotFoundException("Pedidos não encontrados.");
        }
    }

    public OrderEntity save(CreateOrderRequestDto order) throws NotFoundException, DaoException {
        try {
            ShelterEntity shelter = shelterService.findShelterById(order.shelterId());
            if (shelter == null) throw new NotFoundException("Abrigo não encontrado.");

            List<CenterEntity> centerEntities = new ArrayList<>();

            List<OrderCenterEntity> orderCenters = new ArrayList<>();

            for (CenterResponseDto center : order.centersRequest()) {
                CenterEntity centerEntity = centerService.findById(center.id());
                if (centerEntity == null) {
                    throw new NotFoundException("Centro não encontrado: " + center.id() + ".");
                }
                centerEntities.add(centerEntity);

                OrderCenterEntity orderCenter = new OrderCenterEntity();
                orderCenter.setOrder(null); // Set the order later
                orderCenter.setCenter(centerEntity);
                orderCenter.setStatus(StatusOrder.PEDENTE); // Initial status

                orderCenters.add(orderCenter);
            }

            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setDate(LocalDate.now());
            orderEntity.setQuantity(order.quantity());
            orderEntity.setShelter(shelter);
            orderEntity.setSizeItem(order.sizeItem());
            orderEntity.setCategoryItem(order.categoryItem());
            orderEntity.setGenderItem(order.genderItem());
            orderEntity.setHygieneType(order.hygieneType());
            orderEntity.setOrderCenter(orderCenters); // Set the order centers

            OrderEntity savedOrder = orderDao.save(orderEntity);

            for (OrderCenterEntity orderCenter : savedOrder.getOrderCenter()) {
                orderCenter.setOrder(savedOrder);
                orderCenterService.save(orderCenter);
            }

            return savedOrder;
        } catch (NotFoundException exception) {
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

    public List<OrderEntity> findByShelter(Long shelterId) throws NotFoundException, DaoException {
        try {
            return orderDao.findByShelter(shelterId);
        }
        catch (NoResultException exception) {
            throw new NotFoundException("Pedidos não encontrados.");
        }
    }

    public List<OrderEntity> findByCenter(Long id) throws NotFoundException, DaoException {
        try {
            List<OrderEntity> orderDto = orderDao.findByCenter(id);
            if(orderDto.isEmpty()) throw new NotFoundException("Lista de pedidos vazia.");
            return orderDto;
        }
        catch (NoResultException exception) {
            throw new NotFoundException(exception.getMessage());
        }
        catch (DaoException exception) {
            throw exception;
        }
    }

    public OrderEntity checkout(long centerId, long orderId, StatusOrder status) {
        OrderEntity order = orderDao.findById(orderId);
        if(order == null) throw new NotFoundException("Pedido não encontrado.");

        CenterEntity center = centerService.findById(centerId);
        if(center == null) throw new NotFoundException("Centro não encontrado.");

        ShelterEntity shelter = order.getShelter();

        List<ItemEntity> itemsToShelter = new ArrayList<>();

        for(ItemEntity item : center.getItems()) {
            if(order.getCategoryItem().equals(item.getCategory())) {
                switch (order.getCategoryItem()) {
                    case ROUPA:
                        if (item.getSize().equals(order.getSizeItem()) && item.getGender().equals(order.getGenderItem()))
                            itemsToShelter.add(item);
                        break;
                    case ALIMENTO:
                        itemsToShelter.add(item);
                        break;
                    case HIGIENE:
                        if (item.getHygieneType().equals(order.getHygieneType()))
                            itemsToShelter.add(item);
                        break;
                    default:
                        break;
                }
            }
        }

        if(itemsToShelter.isEmpty()) throw new NotFoundException("Nenhum item encontrado para o pedido.");

        OrderCenterEntity orderCenter = order.getOrderCenter().stream()
                .filter(oc -> oc.getCenter().getId().equals(centerId))
                .findFirst()
                .orElse(null);

        if(orderCenter == null) throw new NotFoundException("Pedido não encontrado para o centro.");

        Integer sizeItemToShelter = itemsToShelter.size();
        Integer quantity = order.getQuantity();

        if (quantity - sizeItemToShelter > 0) status = StatusOrder.PEDENTE;
        else status = StatusOrder.ACEITO;

        Integer newQuantity = quantity - sizeItemToShelter < 0 ? 0 : quantity - sizeItemToShelter;

        for(int i = 0; i < quantity - newQuantity; i++) {
            center.getItems().remove(itemsToShelter.get(i));
            shelter.addItem(itemsToShelter.get(i));
        }

        order.setShelter(shelter);
        order.setQuantity(newQuantity);
        orderCenter.setStatus(status);
        orderCenter.setOrder(order);

        OrderCenterEntity ordercenterSaved = orderCenterService.update(orderCenter);

        return ordercenterSaved.getOrder();
    }
}
