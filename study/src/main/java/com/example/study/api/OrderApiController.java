package com.example.study.api;

import com.example.study.dto.order.OrderDTO;
import com.example.study.dto.order.OrderFlatDTO;
import com.example.study.dto.order.OrderQueryDTO;
import com.example.study.entity.order.Order;
import com.example.study.entity.order.OrderItem;
import com.example.study.repository.OrderRepository;
import com.example.study.repository.OrderSearch;
import com.example.study.repository.query.OrderQueryRepository;
import com.example.study.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;
    private final OrderService orderService;

    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1(){
        return orderService.findOrders(new OrderSearch());
    }

    @GetMapping("/api/v2/orders")
    public List<OrderDTO> orderV2(){
        List<OrderDTO> result = orderRepository.findAllByString(new OrderSearch()).stream()
                .map(OrderDTO::new)
                .collect(Collectors.toList());
        return result;
    }
    /*
    n to many 인 경우
    패치조인으로 쿼리를 짤 경우
    페이징이 불가능
    */
    @GetMapping("/api/v3/orders")
    public List<OrderDTO> orderV3(){
        List<Order> orders = orderRepository.findAllWithItems();
        for(Order order : orders){
            System.out.println("order ref = "+order+" id = "+order.getId());
        }
        List<OrderDTO> result = orderRepository.findAllWithItems().stream()
                .map(OrderDTO::new)
                .collect(Collectors.toList());

        return result;
    }


    @GetMapping("/api/v3.1/orders")
    public List<OrderDTO> orderV3_page(
            @RequestParam(value = "offset",defaultValue = "0") int offset,
            @RequestParam(value = "limit",defaultValue = "100") int limit)
    {
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset,limit);

        List<OrderDTO> result = orders.stream()
                .map(OrderDTO::new)
                .collect(Collectors.toList());

        return result;
    }

    @GetMapping("/api/v4/orders")
    public List<OrderQueryDTO> orderV4(){
        return orderQueryRepository.findOrderQueryDtos();
    }

    @GetMapping("/api/v5/orders")
    public List<OrderQueryDTO> orderV5(){
        return orderQueryRepository.findAllByDTO_optimization();
    }

    @GetMapping("/api/v6/orders")
    public List<OrderFlatDTO> orderV6(){
        return orderQueryRepository.findAllByDTO_flat();
    }

}
