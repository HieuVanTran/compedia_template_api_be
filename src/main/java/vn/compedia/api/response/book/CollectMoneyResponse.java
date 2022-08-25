package vn.compedia.api.response.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectMoneyResponse {

    @JsonProperty("collect_money_id")
    private Long collectMoneyId;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("staff_id")
    private Long staffId;

    @JsonProperty("staff_name")
    private String staffName;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("proceeds")
    private Integer proceeds;

    @JsonProperty("fined_amount")
    private Integer fiendAmount;


}
