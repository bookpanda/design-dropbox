package com.dropbox.file.dto;

public class GetUploadUrlResponse {
    private String uploadUrl;

    public GetUploadUrlResponse(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

    public String getUploadUrl() {
        return uploadUrl;
    }
}
