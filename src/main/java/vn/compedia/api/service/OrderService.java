package vn.compedia.api.service;

import com.google.common.collect.Maps;
import com.google.maps.errors.ApiException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import vn.compedia.api.dto.OrdersDto;
import vn.compedia.api.dto.VietTienPageDto;
import vn.compedia.api.entity.Customer;
import vn.compedia.api.entity.OrderDetail;
import vn.compedia.api.entity.Orders;
import vn.compedia.api.exception.VietTienException;
import vn.compedia.api.exception.VietTienInvalidParamsException;
import vn.compedia.api.exception.VietTienNotFoundException;
import vn.compedia.api.repository.OrderDetailRepository;
import vn.compedia.api.repository.OrdersRepository;
import vn.compedia.api.repository.ProductRepository;
import vn.compedia.api.repository.customer.CustomerRepository;
import vn.compedia.api.request.OrderDetailRequest;
import vn.compedia.api.request.OrderRequest;
import vn.compedia.api.response.order.OrderResponse;
import vn.compedia.api.response.order.OrderRetailResponse;
import vn.compedia.api.util.user.UserContextHolder;
import java.io.IOException;
import java.util.*;

@Log4j2
@Service
public class OrderService {
    @Autowired
    private OrdersRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productResponse;

    @Autowired
    private CustomerRepository customerRepository;


    @Transactional
    public OrderResponse createOrUpdateOrder(OrderRequest orderRequest) throws VietTienException, VietTienInvalidParamsException {
        Map<String, String> errors = new HashMap<>();
        Date now = new Date();
        if (CollectionUtils.isEmpty(orderRequest.getOrderDetailRequestList())) {
            errors.put("order_detail_list", " Bạn vui lòng thêm sản phẩm");
        } else {
            for (int i = 0; i < orderRequest.getOrderDetailRequestList().size(); i++) {
                OrderDetailRequest orderDetailRequest = orderRequest.getOrderDetailRequestList().get(i);
                if (orderDetailRequest.getProductId() == null) {
                    errors.put("product_id", " Bạn vui lòng thêm sản phẩm");
                }
                if (orderDetailRequest.getProductTypeId() == null) {
                    errors.put("product_type_id", " Bạn vui lòng chọn cỡ sản phẩm");
                }
                if (orderDetailRequest.getQuantity() == null) {
                    errors.put("quantity", " Bạn vui lòng nhập số lượng sản phẩm cho cỡ");
                }
            }
        }

        if (!errors.isEmpty()) {
            throw new VietTienInvalidParamsException(errors);
        }

        Orders orders = new Orders();
        List<OrderDetail> orderDetailList = new ArrayList<>();
        if (orderRequest.getOrderId() != null) {
            orders = orderRepository.findOrderByOrderId(orderRequest.getOrderId());
            orders.setUpdateDate(now);
            orders.setUpdateBy(UserContextHolder.getUser().getAccountId());
        } else {
            orders.setCreateDate(now);
            orders.setCreateBy(UserContextHolder.getUser().getAccountId());
        }

        orders.setOrderId(orderRequest.getOrderId());
        orders.setCustomerId(orderRequest.getCustomerId() != null ? orderRequest.getCustomerId() : orders.getCustomerId());
        orders.setTotalMoney(orderRequest.getTotalMoney() != null ? orderRequest.getTotalMoney() : orders.getTotalMoney());
        orders.setNote(StringUtils.isNotBlank(orderRequest.getNote()) ? orderRequest.getNote() : orders.getNote());
        orders.setStatus(orderRequest.getStatus() != null ? orderRequest.getStatus() : orders.getStatus());
        orders.setDiscount(orderRequest.getDiscount() != null ? orderRequest.getDiscount() : orders.getDiscount());
        orders.setTransportFee(orderRequest.getTransportFee() != null ? orderRequest.getTransportFee() : orders.getTransportFee());
        orders.setShopId(orderRequest.getShopId() != null ? orderRequest.getShopId() : orders.getShopId());
        orders.setDayShipping(orderRequest.getDayShipping() != null ? orderRequest.getDayShipping() : now);
        orders.setTransportFee(orderRequest.getTransportFee() != null ? orderRequest.getTransportFee() : orders.getTransportFee());

        orderRepository.save(orders);
        for (OrderDetailRequest orderDetailRequest : orderRequest.getOrderDetailRequestList()) {
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(orderDetailRequest, orderDetail);
            orderDetail.setOrderId(orders.getOrderId());
            orderDetailList.add(orderDetail);
        }
        orderDetailRepository.saveAll(orderDetailList);
        OrderResponse orderResponse = new OrderResponse();
        BeanUtils.copyProperties(orders, orderResponse);
        orderResponse.setOrderDetailRequestList(orderDetailList);
        return orderResponse;
    }

    public OrderRetailResponse getOrderRetailById(Long id, Integer languageId) throws VietTienNotFoundException, VietTienInvalidParamsException {
        OrderRetailResponse orderRetailResponse = new OrderRetailResponse();
        Orders orders = orderRepository.findById(id).orElseThrow(() -> new VietTienNotFoundException("Not found id: " + id));
        Map<String, String> errors = Maps.newHashMap();

        if (!errors.isEmpty()) {
            throw new VietTienInvalidParamsException(errors);
        }
        orderRetailResponse.setStatus(orders.getStatus());
        orderRetailResponse.setTotalMoney(orders.getTotalMoney());
        orderRetailResponse.setCreateDate(orders.getCreateDate());
        Customer customer = customerRepository.findCustomerByCustomerId(orders.getCustomerId());
        orderRetailResponse.setFullName(customer.getName());
        orderRetailResponse.setPhone(customer.getPhone());
        orderRetailResponse.setEmail(customer.getEmail());
        orderRetailResponse.setProvinceId(customer.getProvinceId());
        orderRetailResponse.setDistrictId(customer.getDistrictId());
        orderRetailResponse.setCommuneId(customer.getCommuneId());
        orderRetailResponse.setAddress(customer.getAddress());

        //List<OrderDetail> orderDetailList =
        return orderRetailResponse;
    }


    public VietTienPageDto<?> search(Map<String, Object> filters, int page, int pageSize) throws VietTienInvalidParamsException, IOException, InterruptedException, ApiException {
        Map<String, String> errors = Maps.newHashMap();
        if (!errors.isEmpty()) {
            throw new VietTienInvalidParamsException(errors);
        }

        Map<String, String> sorts = new HashMap<>();
        if (filters.get("sorts") != null) {
            sorts.putAll((Map<String, String>) filters.get("sorts"));
        }
        Page<OrdersDto> showPage = orderRepository.search(filters, sorts, PageRequest.of(page - 1, pageSize));
        return VietTienPageDto.build(showPage);
    }

}
