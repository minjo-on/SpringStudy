package com.example.study.repository;

import com.example.study.dto.order.SimpleOrderDTO;
import com.example.study.entity.order.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order){
        em.persist(order);
    }

    public Order findOne(Long id){
        return em.find(Order.class,id);
    }

    public List<Order> findAll(){
        return em.createQuery("select o from Order o", Order.class)
                .getResultList();
    }
    public List<Order> findAllByString(OrderSearch orderSearch) {
        String jpql = "select o from Order o join o.member m";
        boolean isFirstCondition = true;

        if(orderSearch.getOrderStatus() != null){
            if(isFirstCondition){
                jpql += " where";
                isFirstCondition = false;
            }else{
                jpql += " and";
            }
            jpql += " o.status = :status";
        }

        if (StringUtils.hasText(orderSearch.getMemberName())){
            if(isFirstCondition){
                jpql += " where";
                isFirstCondition = false;
            }else {
                jpql += " and";
            }
            jpql += " m.name like :name";
        }

        TypedQuery<Order> query = em.createQuery(jpql,Order.class)
                .setMaxResults(1000);

        if (orderSearch.getOrderStatus() != null){
            query = query.setParameter("status",orderSearch.getOrderStatus());
        }
        if (StringUtils.hasText(orderSearch.getMemberName())){
            query = query.setParameter("name",orderSearch.getMemberName());
        }

        return query.getResultList();
    }
/*
    쿼리 길이가 V4보다 약간 더 길긴 하지만
    Order 테이블에 member , delivery 테이블과
    패치조인한 결과를 그대로 리턴하기 때문에 사용하고 싶은대로
    DTO 타입으로 변환해서 사용할 수 있기 때문에 재사용성이 좋음
*/
    public List<Order> findAllWithMemberDelivery(){
        return em.createQuery(
                "select o from Order o"+
                        " join fetch o.member m" +
                        " join fetch o.delivery d",Order.class
        ).getResultList();
    }
    
    /*
    쿼리 길이가 V3버전보단 줄어들어 성능이 약간 더 좋긴 하지만
    order 데이터에 조인한 것이 아닌
    직접 사용하려고 하는 DTO에 조인한 것이기 때문에
    리턴값 자체가 DTO를 조회한 형식이여서 재사용성이 낮음.
    */
    public List<SimpleOrderDTO> findOrderDTOs() {
        return em.createQuery(
                "select new com.example.study.dto.order.SimpleOrderDTO(o.id,m.name,o.status,o.orderDate,d.address) " +
                        "from Order o"+
                        " join o.member m" +
                        " join o.delivery d",SimpleOrderDTO.class)
                .getResultList();
    }
}
