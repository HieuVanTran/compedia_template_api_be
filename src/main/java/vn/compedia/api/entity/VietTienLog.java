package vn.compedia.api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.PrePersist;
import java.util.Date;

@Setter
@Getter
public class VietTienLog {

    private int id;
    private int code;
    private String url;
    private String requestId;
    private Date createDate;

    @PrePersist
    public void onPersist() {
        createDate = new Date();
    }
}
