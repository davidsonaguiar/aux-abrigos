package com.compass.order;

import com.compass.common.Response;
import com.compass.common.exception.DaoException;
import com.compass.common.exception.NotFoundException;
import com.compass.order.dtos.CreateOrderRequestDto;
import com.compass.order.dtos.OrderResponseDto;
import org.hibernate.query.Order;

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
}
