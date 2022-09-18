package vn.compedia.api.request;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AccountUpdateRequest {

    private Long Id;

    @JsonIgnore
    private String password;



}
