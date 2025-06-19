package fit.hcmute.JobIT.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Meta {
    private int page;        // Trang hiện tại (bắt đầu từ 0 hoặc 1 tùy hệ thống)

    private int pageSize;    // Số phần tử mỗi trang (ví dụ: 10, 20)

    private int pages;       // Tổng số trang (totalPages)

    private long total;      // Tổng số phần tử (totalElements)
}
