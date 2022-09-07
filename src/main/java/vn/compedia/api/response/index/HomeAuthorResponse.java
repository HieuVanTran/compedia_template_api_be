package vn.compedia.api.response.index;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class HomeAuthorResponse {

    @JsonProperty("id_author")
    private Long idAuthor;

    @JsonProperty("name_author")
    private String nameAuthor;

}
