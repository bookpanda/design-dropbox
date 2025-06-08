package com.dropbox.file;

import org.springframework.stereotype.Service;

import com.dropbox.s3.S3Service;

@Service
public class FileService {
    private final S3Service s3Service;
    // private final FileRepository fileRepository;
    // private final RedisClient redisClient;

    public FileService(S3Service s3Service) {
        this.s3Service = s3Service;
        // this.fileRepository = fileRepository;
        // this.redisClient = redisClient;
        // this.cacheTTLSeconds = Duration.ofSeconds(cacheTTLSeconds);
        // this.baseFile = baseFile;
    }

    public String getUploadUrl(String userId, String fileName, String mimeType) {
        return s3Service.generatePresignedUploadUrl(
                userId,
                fileName,
                java.time.Duration.ofMinutes(15));
    }

}
