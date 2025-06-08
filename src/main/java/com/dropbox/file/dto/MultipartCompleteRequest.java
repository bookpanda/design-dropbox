package com.dropbox.file.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import software.amazon.awssdk.services.s3.model.CompletedPart;

public class MultipartCompleteRequest extends GetUploadUrlRequest {
    @NotBlank(message = "parts is required")
    private List<CompletedPart> parts;

    public MultipartCompleteRequest(String fileId, List<CompletedPart> parts) {
        super(fileId);
        this.parts = parts;
    }

    public List<CompletedPart> getParts() {
        return parts;
    }
}
