package vn.compedia.api.repository;

import org.hibernate.criterion.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.compedia.api.entity.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Long>, OrdersRepositoryCustom {

    Orders findOrderByOrderId(Long orderId);
}
