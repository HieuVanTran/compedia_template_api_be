package vn.compedia.api.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class HomeRequestFromRequest {


    @JsonIgnore
    private Long callCardId;

    @JsonIgnore
    private Long accountId;

    @JsonIgnore
    private Long staffId;

    @JsonIgnore
    private String endDate;

    @JsonProperty("note")
    private String note;

    @JsonProperty("book_id")
    private Long bookId;

    @JsonProperty("amount")
    private Integer amount;

    @JsonIgnore
    private Integer status;

}
