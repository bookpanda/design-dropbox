package com.dropbox.file;

import org.springframework.stereotype.Service;

@Service
public class FileService {
    // private final FileRepository fileRepository;
    // private final RedisClient redisClient;

    public FileService() {
        // this.fileRepository = fileRepository;
        // this.redisClient = redisClient;
        // this.cacheTTLSeconds = Duration.ofSeconds(cacheTTLSeconds);
        // this.baseFile = baseFile;
    }

    public String getUploadUrl(String fileName, String mimeType) {
        return "";
    }

}
