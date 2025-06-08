package com.dropbox.file;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.dropbox.file.dto.GetFileResponse;
import com.dropbox.s3.S3Service;
import com.dropbox.shares.SharesService;

@Service
public class FileService {
    private final S3Service s3Service;
    private final SharesService sharesService;

    public FileService(S3Service s3Service, SharesService sharesService) {
        this.s3Service = s3Service;
        this.sharesService = sharesService;
    }

    public String getUploadUrl(String userId, String fileName) {
        return s3Service.generatePresignedUploadUrl(
                userId,
                fileName,
                java.time.Duration.ofMinutes(15));
    }

    public GetFileResponse getFile(String userId, String ownerId, String fileId) {
        var isAccessible = sharesService.shareExists(userId, ownerId + "/" + fileId);
        if (!isAccessible) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have access to this file.");
        }

        var metadata = s3Service.getObjectMetadata(userId, fileId);
        if (metadata == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found.");
        }

        var downloadUrl = s3Service.generatePresignedDownloadUrl(
                userId,
                fileId,
                java.time.Duration.ofMinutes(15));

        var fileName = fileId.split("/").length > 1 ? fileId.split("/")[1] : fileId;

        return new GetFileResponse(
                fileId,
                fileName,
                metadata.contentLength().toString(),
                metadata.contentType(),
                downloadUrl,
                ownerId);
    }

    public void shareFile(String userId, String ownerId, String fileId) {
        if (!sharesService.shareExists(userId, ownerId + "/" + fileId)) {
            sharesService.addShare(userId, ownerId + "/" + fileId);
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "File already shared with this user.");
        }
    }

}
