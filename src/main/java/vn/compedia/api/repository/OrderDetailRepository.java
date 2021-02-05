package vn.compedia.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.compedia.api.entity.OrderDetail;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    List<OrderDetail> findOrderDetailsByOrderId(Long orderId);
}
