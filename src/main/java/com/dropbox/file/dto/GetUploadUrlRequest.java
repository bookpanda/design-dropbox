package com.dropbox.file.dto;

import jakarta.validation.constraints.NotBlank;

public class GetUploadUrlRequest {
    @NotBlank(message = "fileName is required")
    private String fileName;

    @NotBlank(message = "mimeType is required")
    private String mimeType;

    public GetUploadUrlRequest(String fileName, String mimeType) {
        this.fileName = fileName;
        this.mimeType = mimeType;
    }

    public String getFileName() {
        return fileName;
    }

    public String getMimeType() {
        return mimeType;
    }
}
