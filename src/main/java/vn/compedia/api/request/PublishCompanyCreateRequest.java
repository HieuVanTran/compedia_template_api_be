package vn.compedia.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.xml.crypto.Data;

@Getter
@Setter
public class PublishCompanyCreateRequest {
    Long Id;

    @JsonProperty("publish_name")
    private String publishName;


    @JsonProperty("address")
    private String address;


    @JsonProperty("email")
    private String email;


    @JsonProperty("agent_people")
    private String agentPeople;


    @JsonProperty("date_founding")
    private String dateFounding;

}
