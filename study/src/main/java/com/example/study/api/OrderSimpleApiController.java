package com.example.study.api;

import com.example.study.dto.order.SimpleOrderDTO;
import com.example.study.entity.Address;
import com.example.study.entity.order.Order;
import com.example.study.entity.order.OrderStatus;
import com.example.study.repository.OrderRepository;
import com.example.study.repository.OrderSearch;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDTO> orderV2(){
        return orderRepository.findAllByString(new OrderSearch())
                .stream()
                .map(SimpleOrderDTO::new)
                .collect(Collectors.toList());

        /*
        위랑 같다
        List<SimpleOrderDto> orders = orderRepository.findAllByString(new OrderSearch()).stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());

        return orders;
        */
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDTO> orderV3(){
        return orderRepository.findAllWithMemberDelivery()
                .stream()
                .map(SimpleOrderDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/v4/simple-orders")
    public List<SimpleOrderDTO> orderV4(){
        return orderRepository.findOrderDTOs();
    }

}
