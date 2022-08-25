package vn.compedia.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonthDataResponse {

    // 1
    @JsonProperty("month")
    private Integer month;

    // Tháng 1
    @JsonProperty("month_text")
    private String monthText;

    // Số lượng mượn
    @JsonProperty("amount_borrow")
    private Integer amountBorrow;

    // Số lượng trả
    @JsonProperty("amount_pay")
    private Integer amountPay;
}
