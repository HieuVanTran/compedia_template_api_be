package vn.compedia.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CallCardCreateRequest {

    @JsonProperty("call_card_id")
    private Long callCardId;

    @JsonProperty("account_id")
    private Long accountId;

    @JsonProperty("staff_id")
    private Long staffId;

//    @JsonProperty("start_date")
//    private Date startDate;

    @JsonProperty("end_date")
    private String endDate;

    @JsonProperty("note")
    private String note;

    @JsonProperty("book_id")
    private Long bookId;

    @JsonProperty("amount")
    private Integer amount;


}
