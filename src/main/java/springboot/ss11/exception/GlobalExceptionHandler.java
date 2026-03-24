package springboot.ss11.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import springboot.ss11.dto.ApiResponse;
import tools.jackson.databind.exc.UnrecognizedPropertyException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ===== NOT FOUND (404) =====
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Object> handleNotFound(ResourceNotFoundException ex) {
        return ApiResponse.error(404,null, ex.getMessage());
    }

    // ===== NOT FOUND (410) =====
    @ExceptionHandler(ResourceDeletedException.class)
    @ResponseStatus(HttpStatus.GONE)
    public ApiResponse<Object> handleResourceDeleted(ResourceDeletedException ex) {
        return ApiResponse.error(410,null, ex.getMessage());
    }

    // ===== DUPLICATE (409) =====
    @ExceptionHandler(DuplicateResourceException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse<Object> handleDuplicate(DuplicateResourceException ex) {
        return ApiResponse.error(409,null, ex.getMessage());
    }

    // ===== BAD REQUEST: Invalid Field (400) =====
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Object> handleInvalidField(HttpMessageNotReadableException ex) {
        String fieldName = "unknown"; // 🔥 lấy tên field
        log.warn("Client gửi field bị cấm: {}", fieldName);
        Map<String, Object> data = new HashMap<>();
        data.put("Dữ liệu bị cấm: ", fieldName);
        return ApiResponse.error(400, data, "Field không được phép: " + fieldName);
    }

    // ===== BAD REQUEST: Insufficient stock (400) =====
    @ExceptionHandler(InsufficientStockException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Object> handleInsufficientStock(InsufficientStockException ex) {
        return ApiResponse.error(400, null, ex.getMessage());
    }

    // ===== BAD REQUEST (400) =====
    @ExceptionHandler(InvalidFileException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Object> handleInvalidFile(InvalidFileException ex) {
        return ApiResponse.error(400, null, ex.getMessage());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Object> handleValidation(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ApiResponse.error(400,  errors,"Yêu cầu nhập lại");
    }

    // ===== CUSTOM RUNTIME ERROR =====
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Object> handleRuntimeException(RuntimeException ex) {
        return ApiResponse.error(500,null, ex.getMessage());
    }

    // ===== CATCH ALL =====
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Object> handleException(Exception e) {

        // 🔥 Log full stack trace (quan trọng nhất)
        log.error("Lỗi hệ thống xảy ra: ", e);

        return ApiResponse.error(500, null,"Internal Server Error");
    }
}