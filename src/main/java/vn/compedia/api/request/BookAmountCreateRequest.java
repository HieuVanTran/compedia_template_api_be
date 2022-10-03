package vn.compedia.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BookAmountCreateRequest {


    @JsonProperty("bool_id")
    private Long bookId;


    private Integer Amount;


}
