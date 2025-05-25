package com.dropbox.file;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dropbox.file.dto.GetFileResponse;
import com.dropbox.file.dto.GetUploadUrlRequest;
import com.dropbox.file.dto.GetUploadUrlResponse;

@RestController
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/upload-url")
    public GetUploadUrlResponse getUploadUrl(@RequestBody GetUploadUrlRequest request) {
        // client will upload to S3 (user's folder)
        String uploadUrl = fileService.getUploadUrl(request.getFileName(), request.getMimeType());

        return new GetUploadUrlResponse(uploadUrl);
    }

    @GetMapping("/{fileId}")
    public GetFileResponse getFile(@PathVariable("fileId") String code) {
        // String originalFile = fileService.getRedirectFile(code);

        return new GetFileResponse(
                "fileId",
                "fileName",
                "fileSize",
                "fileMimeType",
                "fileUrl",
                "uploadedBy");
    }
}
