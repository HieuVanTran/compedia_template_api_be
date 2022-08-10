package vn.compedia.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CallCardDetailsRequest {

    @JsonProperty("call_card_details_id")
    private Long callCardDetailsId;

    @JsonProperty("book_id")
    private Long bookId;

    @JsonProperty("amount")
    private Integer amount;

}
