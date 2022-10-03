package vn.compedia.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CallCardTypeCreateRequest {
    @JsonProperty("call_card_id")
    private Long callCardId;

    private Integer type;
}
