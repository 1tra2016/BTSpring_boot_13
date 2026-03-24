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

    public static <T> ApiResponse<T> success(int code,T data, Object meta) {
        return ApiResponse.<T>builder()
                .status("SUCCESS")
                .code(code)
                .data(data)
                .meta(meta)
                .build();
    }

    public static <T> ApiResponse<T> success(int code, T data) {
        return ApiResponse.<T>builder()
                .status("SUCCESS")
                .code(code)
                .data(data)
                .build();
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