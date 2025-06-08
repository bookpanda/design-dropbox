package com.dropbox.file.dto;

import jakarta.validation.constraints.Positive;

public class GetPartUploadUrlRequest extends GetUploadUrlRequest {
    @Positive(message = "part_number is required and must be positive")
    private int partNumber;

    public GetPartUploadUrlRequest(String fileId, int partNumber) {
        super(fileId);
        this.partNumber = partNumber;
    }

    public int getPartNumber() {
        return partNumber;
    }
}
