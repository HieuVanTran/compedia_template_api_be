package vn.compedia.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentRequest {

    @JsonProperty("class_id")
    private String classId;

    @JsonProperty("points")
    private Integer points;

}
