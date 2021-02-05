package vn.compedia.api.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.compedia.api.dto.StoreDto;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderRetailResponse {

    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("customer_id")
    private Long customerId;

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("email")
    private String email;

    @JsonProperty("province_id")
    private Long provinceId;

    @JsonProperty("district_id")
    private Long districtId;

    @JsonProperty("commune_id")
    private Long communeId;

    @JsonProperty("address")
    private String address;

    @JsonProperty("total_money")
    private Double totalMoney;

    @JsonProperty("create_date")
    private Date createDate;

    @JsonProperty("list_storeDto")
    private List<StoreDto> storeDtoList;


}
