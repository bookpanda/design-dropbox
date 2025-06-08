package com.dropbox.file;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dropbox.file.dto.GetFileResponse;
import com.dropbox.file.dto.GetPartUploadUrlRequest;
import com.dropbox.file.dto.GetUploadUrlRequest;
import com.dropbox.file.dto.GetUploadUrlResponse;
import com.dropbox.file.dto.InitMultipartResponse;
import com.dropbox.file.dto.MultipartCompleteRequest;
import com.dropbox.file.dto.ShareFileRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload-url")
    public GetUploadUrlResponse getUploadUrl(@AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody GetUploadUrlRequest request) {
        // client will upload to S3 (user's folder)
        String userId = jwt.getClaimAsString("userId");
        String uploadUrl = fileService.getUploadUrl(userId, request.getFileId());

        return new GetUploadUrlResponse(uploadUrl);
    }

    @GetMapping("/{fileId}/users/{ownerId}")
    public GetFileResponse getFile(@AuthenticationPrincipal Jwt jwt,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("fileId") String fileId) {
        String userId = jwt.getClaimAsString("userId");
        GetFileResponse response = fileService.getFile(userId, ownerId, fileId);

        return response;
    }

    @PostMapping("/share")
    public ResponseEntity<Void> shareFile(@AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody ShareFileRequest request) {
        String ownerId = jwt.getClaimAsString("userId");
        fileService.shareFile(request.getUserId(), ownerId, request.getFileId());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/share")
    public ResponseEntity<Void> unshareFile(@AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody ShareFileRequest request) {
        String ownerId = jwt.getClaimAsString("userId");
        fileService.unshareFile(request.getUserId(), ownerId, request.getFileId());

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<GetFileResponse> getAllFiles(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getClaimAsString("userId");
        List<GetFileResponse> response = fileService.getFiles(userId);

        return response;
    }

    @PostMapping("/multipart")
    public InitMultipartResponse initMultipartUpload(@AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody GetUploadUrlRequest request) {
        String userId = jwt.getClaimAsString("userId");
        String uploadUrl = fileService.initMultipart(userId, request.getFileId());

        return new InitMultipartResponse(uploadUrl);
    }

    @PostMapping("/multipart/{uploadId}")
    public GetUploadUrlResponse getPartUploadUrl(@AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody GetPartUploadUrlRequest request, @PathVariable("uploadId") String uploadId) {
        String userId = jwt.getClaimAsString("userId");
        String key = userId + "/" + request.getFileId();

        String uploadUrl = fileService.getPartUploadUrl(key, uploadId, request.getPartNumber());

        return new GetUploadUrlResponse(uploadUrl);
    }

    @PostMapping("/multipart/{uploadId}/complete")
    public ResponseEntity<Void> completeMultipartUpload(@AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody MultipartCompleteRequest request, @PathVariable("uploadId") String uploadId) {
        String userId = jwt.getClaimAsString("userId");
        String key = userId + "/" + request.getFileId();

        fileService.completeMultipartUpload(key, uploadId, request.getParts());

        return ResponseEntity.ok().build();
    }

}
