package com.example.study.repository.query;

import com.example.study.dto.order.OrderFlatDTO;
import com.example.study.dto.order.OrderItemQueryDTO;
import com.example.study.dto.order.OrderQueryDTO;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {
    private final EntityManager em;

    public List<OrderQueryDTO> findOrderQueryDtos(){
        List<OrderQueryDTO> result = findOrders();
        result.forEach(o ->{
            List<OrderItemQueryDTO> orderItems = findOrderItems(o.getOrderId());
            o.setOrderItems(orderItems);
        });

        return result;
    }

    public List<OrderItemQueryDTO> findOrderItems(Long orderId){
        return em.createQuery(
                "select new com.example.study.dto.order.OrderItemQueryDTO(oi.order.id,i.name,oi.orderPrice,oi.count)"+
                        " from OrderItem oi"+
                        " join oi.item i "+
                        " where oi.order.id = :orderId", OrderItemQueryDTO.class)
                .setParameter("orderId",orderId)
                .getResultList();
    }
    public List<OrderQueryDTO> findOrders() {
        return em.createQuery(
                "select new com.example.study.dto.order.OrderQueryDTO(o.id,m.name,o.orderDate, o.status,d.address)" +
                        " from Order o" +
                        " join o.member m" +
                        " join o.delivery d", OrderQueryDTO.class)
                .getResultList();
    }

    public List<OrderQueryDTO> findAllByDTO_optimization() {
        List<OrderQueryDTO> result = findOrders();

        Map<Long, List<OrderItemQueryDTO>> orderItemMap = getOrderItemMap(getIds(result));

        result.forEach(o->o.setOrderItems(orderItemMap.get(o.getOrderId())));

        return result;
    }

    private Map<Long, List<OrderItemQueryDTO>> getOrderItemMap(List<Long> orderIds) {
        List<OrderItemQueryDTO> orderItems = em.createQuery(
                        "select new com.example.study.dto.order.OrderItemQueryDTO(oi.order.id,i.name,oi.orderPrice,oi.count)"+
                                " from OrderItem oi"+
                                " join oi.item i "+
                                " where oi.order.id in :orderIds", OrderItemQueryDTO.class)
                .setParameter("orderIds", orderIds)
                .getResultList();

        Map<Long, List<OrderItemQueryDTO>> orderItemMap = orderItems.stream()
                .collect(Collectors.groupingBy(OrderItemQueryDTO::getOrderId));
        return orderItemMap;
    }

    private static List<Long> getIds(List<OrderQueryDTO> result) {
        List<Long> orderIds = result.stream()
                .map(o->o.getOrderId())
                .collect(Collectors.toList());
        return orderIds;
    }

    public List<OrderFlatDTO> findAllByDTO_flat() {
        return em.createQuery(
                "select new com.example.study.dto.order.OrderFlatDTO(o.id,m.name,o.orderDate,o.status,d.address,i.name,oi.orderPrice,oi.count)"+
                        " from Order o"+
                        " join o.member m" +
                        " join o.delivery d"+
                        " join o.orderItems oi"+
                        " join oi.item i", OrderFlatDTO.class)
                .getResultList();
    }
}
