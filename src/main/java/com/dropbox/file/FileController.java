package com.dropbox.file;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dropbox.file.dto.GetFileResponse;
import com.dropbox.file.dto.GetUploadUrlRequest;
import com.dropbox.file.dto.GetUploadUrlResponse;

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
        String uploadUrl = fileService.getUploadUrl(userId, request.getFileName());

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
}
