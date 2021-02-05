package vn.compedia.api.response.wholesale;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class wholesaleListResponse {

    @JsonProperty("wholesale_id")
    private Long wholesaleId;


    @JsonProperty("day_shipping")
    private Date dayShipping;


    @JsonProperty("account_id")
    private Long accountId;


    @JsonProperty("shop_id")
    private Long shopId;


    @JsonProperty("total_money")
    private Double totalMoney;

    @JsonProperty("note")
    private String note;

    @JsonProperty("status_wholesale")
    private Integer statusWholesale;
}
