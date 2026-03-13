package com.example.rest.utility;

public class ResponseUtility<T> {

    private final String status;
    private final String message;
    private final T data;
    private final Object metadata;

    public ResponseUtility(String status, String message, T data, Object metadata) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.metadata = metadata;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public Object getMetadata() {
        return metadata;
    }

    public static class SuccessResponse<T> extends ResponseUtility<T> {
        public SuccessResponse(String status, String message, T data, Object metadata) {
            super(status, message, data, metadata);
        }
    }
}
