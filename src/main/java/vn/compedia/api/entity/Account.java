package vn.compedia.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.compedia.api.config.Constant;
import vn.compedia.api.util.MessageUtil;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account")
public class Account extends BaseEntity implements Serializable {
	@Column(name = "dob")
	@JsonProperty("date_of_birth")
	@ApiModelProperty(example = "yyyy-MM-dd")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = Constant.TIME_ZONE_DEFAULT)
	public Date dob;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_id")
	@JsonProperty("account_id")
	private Long accountId;
	@Column(name = "role_id")
	@JsonProperty("role_id")
	private Integer roleId;
	@Column(name = "full_name")
	@JsonProperty("full_name")
	@Size(max = 50, message = MessageUtil.FULL_NAME_HAS_MAX_LENGTH)
	private String fullName;
	@Column(name = "phone")
	@Size(max = 11, message = MessageUtil.PHONE_HAS_MAX_LENGTH)
	private String phone;
	@Column(name = "gender")
	private Integer gender;

	@Column(name = "email")
	private String email;

	@Column(name = "province_id")
	@JsonProperty("province_id")
	private Long provinceId;

	@Column(name = "district_id")
	@JsonProperty("district_id")
	private Long districtId;

	@Column(name = "commune_id")
	@JsonProperty("commune_id")
	private Long communeId;

	@Column(name = "address")
	@JsonProperty("address")
	private String address;

	@Column(name = "is_first_login")
	@JsonProperty("is_first_login")
	private boolean firstLogin;

	@Column(name = "images")
	private String images;

	@JsonIgnore
	@Column(name = "password")
	@Size(max = 200)
	private String password;

	@JsonIgnore
	@Column(name = "salt")
	private String salt;

	@Column(name = "status")
	private Integer status;

	@JsonIgnore
	@Column(name = "otp")
	private String otp;

	@JsonIgnore
	@JsonProperty("otp_expires")
	@Column(name = "otp_expires")
	private Date otpExpires;

}
