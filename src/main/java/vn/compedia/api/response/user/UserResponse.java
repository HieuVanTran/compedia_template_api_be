package vn.compedia.api.response.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

    @JsonProperty("card_id")
    private Long cardId;

    @JsonProperty("full_name")
    private String fullName;

}
