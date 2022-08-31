package vn.compedia.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CollectMoneyCreateRequest {

    Long Id;

    @JsonProperty("account_id")
    private Long accountId;

    @JsonProperty("fined_amount")
    private Double fineAmount;

    private Double proceeds;

    @JsonProperty("staff_id")
    private Integer staffId;
}
