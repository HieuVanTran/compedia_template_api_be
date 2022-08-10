package vn.compedia.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CallCardCreateRequest {

    @JsonProperty("call_card_id")
    private Long callCardId;

    @JsonProperty("card_number")
    private String cardNumber;

    @JsonProperty("staff_id")
    private Long staffId;

    @JsonProperty("start_date")
    private String startDate;

    @JsonProperty("end_date")
    private String endDate;

    @JsonProperty("note")
    private String note;

    @JsonProperty("list_book")
    private List<CallCardDetailsRequest> listBook;

}
