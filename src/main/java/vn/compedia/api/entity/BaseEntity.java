package vn.compedia.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.compedia.api.config.Constant;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Column(name = "create_by", updatable = false)
    @JsonProperty(value = "create_by")
    //  @NotNull
    private Long createBy;

    @Column(name = "create_date", updatable = false)
    @JsonProperty(value = "create_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = Constant.TIME_ZONE_DEFAULT)
    @ApiModelProperty(hidden = true)
    // @NotNull
    private Date createDate;

    @Column(name = "update_by")
    @JsonProperty(value = "update_by")
    //   @NotNull
    private Long updateBy;

    @Column(name = "update_date")
    @JsonProperty(value = "update_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = Constant.TIME_ZONE_DEFAULT)
    @ApiModelProperty(hidden = true)
    // @NotNull
    private Date updateDate;

    public BaseEntity(Long createBy, Date createDate) {
        this.createBy = createBy;
        this.createDate = createDate;
    }

    public BaseEntity(Date createDate) {
        this.createDate = createDate;
    }

    @PrePersist
    protected void prepareCreateEntity() {
        createDate = Timestamp.from(Instant.now());
        updateDate = Timestamp.from(Instant.now());
    }

    @PreUpdate
    protected void prepareUpdateEntity() {
        updateDate = Timestamp.from(Instant.now());
    }
}
