package vn.compedia.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RequestPageBase {
    private int page;

    private int size;

    @JsonProperty("sort_order")
    private String sortOrder;

    @JsonProperty("sort_field")
    private String sortField;
}
