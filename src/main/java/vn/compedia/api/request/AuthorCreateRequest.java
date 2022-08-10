package vn.compedia.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorCreateRequest {
    private Long Id;
    @JsonProperty ("name_author")
    private String nameAuthor;

    private String address;

    // title == chức danh
    private String title;

    private String note;


}
