package vn.compedia.api.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CityDistrictDto {

    private Long provinceId;
    private Long districtId;
    private Long communeId;

    private String provinceName;
    private String districtName;
    private String communeName;

    @Override
    public String toString() {
        return communeName + " - " + districtName + " - " + provinceName;
    }
}
