package vn.compedia.api.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SettingsDTO {
    @NotEmpty
    @NotNull
    private String themeName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SettingsDTO that = (SettingsDTO) o;
        return themeName.equals(that.themeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(themeName);
    }

    @Override
    public String toString() {
        return "SettingsDTO{" +
                "themeName='" + themeName + '\'' +
                '}';
    }
}
