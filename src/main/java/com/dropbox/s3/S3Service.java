package com.dropbox.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;
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

    public String generatePresignedUploadUrl(String userId, String fileId, Duration expiration) {
        var putRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(userId + "/" + fileId)
                .build();

        var presignRequest = PutObjectPresignRequest.builder()
                .putObjectRequest(putRequest)
                .signatureDuration(expiration)
                .build();

        return s3Presigner.presignPutObject(presignRequest).url().toString();
    }

    public String generatePresignedDownloadUrl(String ownerId, String fileId, Duration expiration) {
        var getRequest = GetObjectRequest.builder()
                .bucket(bucket)
                .key(ownerId + "/" + fileId)
                .build();

        var presignRequest = GetObjectPresignRequest.builder()
                .getObjectRequest(getRequest)
                .signatureDuration(expiration)
                .build();

        return s3Presigner.presignGetObject(presignRequest).url().toString();
    }

    public HeadObjectResponse getObjectMetadata(String ownerId, String fileId) {
        var request = HeadObjectRequest.builder()
                .bucket(bucket)
                .key(ownerId + "/" + fileId)
                .build();

        return s3Client.headObject(request);
    }
}
