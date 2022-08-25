package vn.compedia.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CallCardDetailsResponse {


    @JsonProperty("call_card_details_id")
    private Long callCardDetailsId;

    @JsonProperty("book_id")
    private Long bookId;

    @JsonProperty("book_name")
    private String bookName;

    @JsonProperty("amount")
    private Integer amount;


}
