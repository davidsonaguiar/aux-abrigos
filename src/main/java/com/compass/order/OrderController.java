package com.compass.order;

import com.compass.common.Response;
import com.compass.common.exception.DaoException;
import com.compass.common.exception.NotFoundException;
import com.compass.order.dtos.CreateOrderRequestDto;
import com.compass.order.dtos.OrderResponseDto;
import com.compass.order.enums.StatusOrder;
import com.compass.order_center.OrderCenterEntity;
import org.hibernate.query.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    public Response<OrderResponseDto> create(CreateOrderRequestDto createOrderRequestDto) {
        try {
            OrderEntity orderSaved = orderService.save(createOrderRequestDto);
            OrderResponseDto orderResponseDto = OrderResponseDto.fromEntity(orderSaved);
            return new Response<>(orderResponseDto, "Pedido criado com sucesso");
        }
        catch (DaoException | NotFoundException exception) {
            exception.printStackTrace();
            return new Response<>(null, exception.getMessage());
        }
    }

    public Response<List<OrderResponseDto>> listAll() {
        try {
            List<OrderEntity> orders = orderService.findAll();
            if(orders.isEmpty()) {
                return new Response<>(null, "Pedidos não encontrados");
            }
            List<OrderResponseDto> ordersResponseDto = OrderResponseDto.fromEntities(orders);
            return new Response<>(ordersResponseDto, "Pedidos encontrados");
        }
        catch (DaoException | NotFoundException exception) {
            return new Response<>(null, exception.getMessage());
        }
    }

    public Response<List<OrderResponseDto>> listByShelter(Long shelterId) {
        try {
            List<OrderEntity> orders = orderService.findByShelter(shelterId);
            if(orders.isEmpty()) {
                return new Response<>(null, "Lista de pedidos vazia");
            }
            List<OrderResponseDto> ordersResponseDto = OrderResponseDto.fromEntities(orders);
            return new Response<>(ordersResponseDto, "Pedidos encontrados");
        }
        catch (DaoException | NotFoundException exception) {
            return new Response<>(null, exception.getMessage());
        }
    }

    public Response<List<OrderResponseDto>> listByCenter(long id) {
        try {
            List<OrderEntity> orders = orderService.findByCenter(id);
            List<OrderResponseDto> ordersResponseDto = OrderResponseDto.fromEntities(orders);
            return new Response<>(ordersResponseDto, "Pedidos encontrados");
        }
        catch (DaoException | NotFoundException exception) {
            exception.printStackTrace();
            return new Response<>(null, exception.getMessage());
        }
    }

    public Response<OrderResponseDto> checkout(long centerId, long orderId, StatusOrder status) {
        try {
            OrderEntity order = orderService.checkout(centerId, orderId, status);
            OrderResponseDto orderResponseDto = OrderResponseDto.fromEntity(order);
            return new Response<>(orderResponseDto, "Pedido atualizado com sucesso");
        }
        catch (DaoException | NotFoundException exception) {
            return new Response<>(null, exception.getMessage());
        }
    }
}
