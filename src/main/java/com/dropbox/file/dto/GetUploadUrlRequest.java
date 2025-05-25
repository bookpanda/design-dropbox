package com.dropbox.file.dto;

public class GetUploadUrlRequest {
    private String fileName;
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
