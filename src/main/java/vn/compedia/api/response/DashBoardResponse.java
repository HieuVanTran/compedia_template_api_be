package vn.compedia.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DashBoardResponse {

    // Tổng số nhà xuất bản
    @JsonProperty("total_publisher")
    private Long totalPublisher;

    // Tổng số tác giả
    @JsonProperty("total_author")
    private Long totalAuthor;

    // Tổng số sách
    @JsonProperty("total_book")
    private Long totalBook;

    // Tổng số tiền phạt
    @JsonProperty("total_money")
    private Long totalMoney;

    @JsonProperty("list_data")
    private List<MonthDataResponse> listData;
}
