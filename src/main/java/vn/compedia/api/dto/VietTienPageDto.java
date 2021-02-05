package vn.compedia.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Data
@Setter
@Getter
public class VietTienPageDto<T> {

    private T results;

    @JsonProperty("total_elements")
    private long totalElements;

    @JsonProperty("total_pages")
    private int totalPages;

    private int page;

    @JsonProperty("page_size")
    private int pageSize;

    public static VietTienPageDto<?> build() {
        return new VietTienPageDto<>();
    }

    public static <T> VietTienPageDto<?> build(Page<T> page) {
        return new VietTienPageDto<>().withData(page.getContent())
                .withPage(page.getPageable().getPageNumber() + 1)
                .withTotalPages(page.getTotalPages())
                .withPageSize(page.getSize())
                .withTotalElement(page.getTotalElements());
    }

    public VietTienPageDto<?> withData(T object) {
        this.results = object;
        return this;
    }

    public VietTienPageDto<?> withPage(int page) {
        this.page = page;
        return this;
    }

    public VietTienPageDto<?> withTotalElement(long totalElement) {
        this.totalElements = totalElement;
        return this;
    }

    public VietTienPageDto<?> withTotalPages(int totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public VietTienPageDto<?> withPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

}
