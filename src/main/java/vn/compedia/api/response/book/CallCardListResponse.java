package vn.compedia.api.response.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CallCardListResponse {
    @JsonProperty("call_card_id")
    private Long callCardId;

    @JsonProperty("call_card_detail_id")
    private Long callCardDetailId;

    @JsonProperty("book_name")
    private String bookName;

    private String amount;

    @JsonProperty("card_number")
    private String cardNumber;

    @JsonProperty("staff_id")
    private Long staffId;

    @JsonProperty("start_date")
    private String startDate;

    @JsonProperty("end_date")
    private String endDate;

    private String note;

    private Integer status;
}
