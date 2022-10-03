package vn.compedia.api.response.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CallCardResponse {


    @JsonProperty("call_card_id")
    private Long callCardId;

    @JsonProperty("username")
    private String userName;

    @JsonProperty("staff_id")
    private Long staffId;

    @JsonProperty("name_staff")
    private String nameStaff;

    @JsonProperty("status")
    private Integer status;


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

    @JsonProperty("account_id")
    private Long accountId;

    @JsonProperty("book_name")
    private String bookName;

    @JsonProperty("is_action")
    private Integer isAction;


}


