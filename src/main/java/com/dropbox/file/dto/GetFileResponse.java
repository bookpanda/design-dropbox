package com.dropbox.file.dto;

public class GetFileResponse {
    private String id;
    private String name;
    private String size;
    private String mimeType;
    private String url;
    private String uploadedBy;

    public GetFileResponse(
            String id,
            String name,
            String size,
            String mimeType,
            String url,
            String uploadedBy) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.mimeType = mimeType;
        this.url = url;
        this.uploadedBy = uploadedBy;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getUrl() {
        return url;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }
}
