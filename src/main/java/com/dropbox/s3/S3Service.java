package com.dropbox.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.time.Duration;

@Service
public class S3Service {
    private final String bucket;
    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    public S3Service(S3Client s3Client, S3Presigner s3Presigner, @Value("${app.s3.bucket}") String bucket) {
        this.bucket = bucket;
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
    }

    public String generatePresignedUploadUrl(String userId, String key, Duration expiration) {
        var putRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        var presignRequest = PutObjectPresignRequest.builder()
                .putObjectRequest(putRequest)
                .signatureDuration(expiration)
                .build();

        return s3Presigner.presignPutObject(presignRequest).url().toString();
    }

    public void uploadFile(String key, Path filePath) {
        s3Client.putObject(PutObjectRequest.builder().bucket(bucket).key(key).build(), filePath);
    }

    public byte[] downloadFile(String key) {
        try {
            var inputStream = s3Client.getObject(GetObjectRequest.builder().bucket(bucket).key(key).build());
            return inputStream.readAllBytes();
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read S3 object", e);
        }
    }
}
