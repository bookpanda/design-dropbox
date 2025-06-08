package com.dropbox.file.dto;

public class InitMultipartResponse {
    private String uploadId;

    public InitMultipartResponse(String uploadId) {
        this.uploadId = uploadId;
    }

    public String getUploadId() {
        return uploadId;
    }
}
