package lk.ijse.cmjd.research_tracker.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * Standard API response wrapper for consistent JSON responses.
 *
 * Example:
 * {
 *     "success": true,
 *     "message": "Project Created Successfully",
 *     "data": { ... }
 * }
 *
 * @param <T> type of the data payload
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    /** Indicates whether the request was successful */
    private boolean success;

    /** Descriptive message about the operation result */
    private String message;

    /** Response data payload (null on error responses) */
    private T data;

    /**
     * Create a successful response with data.
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    /**
     * Create a successful response without data.
     */
    public static <T> ApiResponse<T> success(String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .build();
    }

    /**
     * Create an error response.
     */
    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .build();
    }

    /**
     * Create an error response with data (e.g., validation errors).
     */
    public static <T> ApiResponse<T> error(String message, T data) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .data(data)
                .build();
    }
}
