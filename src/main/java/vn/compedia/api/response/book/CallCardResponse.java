package vn.compedia.api.response.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.models.auth.In;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CallCardResponse {


    @JsonProperty("call_card_id")
    private Long callCardId;

    @JsonProperty("card_number")
    private String cardNumber;

    @JsonProperty("staff_id")
    private Long staffId;

    @JsonProperty("name_staff")
    private String nameStaff;

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("call_card_details_id")
    private Long callCardDetailsId;

    @JsonProperty("amount")
    private Integer amount;

    @JsonProperty("note")
    private String note;

    @JsonProperty("start_date")
    private String startDate;

    @JsonProperty("end_date")
    private String endDate;

    @JsonProperty("book_id")
    private Long bookId;

    @JsonProperty("user_id")
    private Long userId;

}


