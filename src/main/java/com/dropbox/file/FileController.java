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
// import com.dropbox.file.dto.CreateShortFileRequest;
// import com.bitly.file.dto.CreateShortFileResponse;

import com.dropbox.file.dto.GetFileResponse;
import com.dropbox.file.dto.UploadFileRequest;
import com.dropbox.file.dto.UploadFileResponse;

@RestController
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/{fileId}")
    public GetFileResponse getRedirectFile(@PathVariable("fileId") String code) {
        // String originalFile = fileService.getRedirectFile(code);

        return new GetFileResponse();
    }

    @PostMapping
    public UploadFileResponse uploadFile(@RequestBody UploadFileRequest request) {
        // String shortFile = fileService.createShortFile(request.getOriginalFile(),
        // request.getCustomCode());

        return new UploadFileResponse();
    }
}
