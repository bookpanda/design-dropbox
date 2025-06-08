package com.dropbox.file.dto;

import jakarta.validation.constraints.NotBlank;

public class GetUploadUrlRequest {
    @NotBlank(message = "fileId is required")
    private String fileId;

    public GetUploadUrlRequest(String fileId, String userId) {
        this.fileId = fileId;
    }

    public String getFileId() {
        return fileId;
    }
}
