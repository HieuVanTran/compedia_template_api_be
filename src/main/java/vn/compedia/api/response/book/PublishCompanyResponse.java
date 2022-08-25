package vn.compedia.api.response.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class PublishCompanyResponse {

    @JsonProperty("company_id")
    private Long idPub;

    @JsonProperty("publish_name")
    private String publishName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("agent_people")
    private  String agentPeople;

    @JsonProperty("address")
    private String address;

    @JsonProperty("date_founding")
    private String dateFounding;

    @JsonProperty("book_name")
    private String bookName;



}
