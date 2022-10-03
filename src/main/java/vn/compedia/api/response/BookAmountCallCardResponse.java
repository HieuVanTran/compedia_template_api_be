package vn.compedia.api.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.models.auth.In;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BookAmountCallCardResponse {

    @JsonProperty("call_card_id")
    private Long callCardID;

    @JsonIgnore
    private Long bookID;

    @JsonIgnore
    private Integer Amount;

    private Integer type;


}
