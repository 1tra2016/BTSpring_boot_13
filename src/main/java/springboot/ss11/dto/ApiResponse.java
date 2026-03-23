package springboot.ss11.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {

    private String status;
    private int code;
    private T data;
    private Object meta;

    public static <T> ApiResponse<T> success(T data, Object meta) {
        return ApiResponse.<T>builder()
                .status("SUCCESS")
                .code(200)
                .data(data)
                .meta(meta)
                .build();
    }

    public static <T> ApiResponse<T> success(T data) {
        return success(data, null);
    }

    public static <T> ApiResponse<T> error(int code, T data, String message) {
        return ApiResponse.<T>builder()
                .status("ERROR")
                .code(code)
                .data(data)
                .meta(message)
                .build();
    }

}