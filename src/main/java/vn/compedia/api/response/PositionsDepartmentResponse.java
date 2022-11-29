package vn.compedia.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class PositionsDepartmentResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("codeStaff")
    private String codeStaff;

    @JsonProperty("nameStaff")
    private String nameStaff;

    @JsonProperty("positions_id")
    private  Long positionsId;

    @JsonProperty("positions_name")
    private  String positionsName;

    @JsonProperty("department_id")
    private  Long departmentId;

    @JsonProperty("department_name")
    private  String departmentName;
}
