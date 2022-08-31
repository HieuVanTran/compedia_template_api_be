package vn.compedia.api.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "publish_company")
public class PublishCompany {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long idPub;

    @Column(name = "publish_name")
    private String publishName;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "agent_people")
    private String agentPeople;

    @Column(name = "date_founding")
    private String dateFounding;


}
