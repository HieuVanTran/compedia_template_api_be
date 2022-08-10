package vn.compedia.api.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RoleCreateRequest {
    private Long Id;

    private String code;

    private String name;
}
