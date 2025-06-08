package com.dropbox.file.dto;

import jakarta.validation.constraints.NotBlank;

public class GetUploadUrlRequest {
    @NotBlank(message = "fileName is required")
    private String fileName;

    // in prod, this will be from auth header
    @NotBlank(message = "userId is required")
    private String userId;

    public GetUploadUrlRequest(String fileName, String userId) {
        this.fileName = fileName;
        this.userId = userId;
    }

    public String getFileName() {
        return fileName;
    }

    public String getUserId() {
        return userId;
    }
}
