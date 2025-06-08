package com.dropbox.file.dto;

import jakarta.validation.constraints.NotBlank;

public class ShareFileRequest {
    @NotBlank(message = "fileId is required")
    private String fileId;

    @NotBlank(message = "userId is required")
    private String userId;

    public ShareFileRequest(String fileId, String userId) {
        this.fileId = fileId;
        this.userId = userId;
    }

    public String getFileId() {
        return fileId;
    }

    public String getUserId() {
        return userId;
    }
}
