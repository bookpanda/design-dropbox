# design-dropbox
Implementing Dropbox, referencing design from [Hello Interview](https://www.hellointerview.com/learn/system-design/problem-breakdowns/dropbox)

## Commands
```bash
tree -I ".gradle|.vscode|bin|build|gradle|infrastructure"
gradle dependencies

./gradlew bootRun --continuous
# in the other terminal
./gradlew build --continuous

# testing
./gradlew test
./gradlew test --info
./gradlew test --debug
./gradlew clean test
./gradlew test --tests "com.bitly.url.UrlServiceTest"
```
## Testing
For auth headers, use `Bearer <token>`.
```bash
# generated with jwt.io

# token for "Joey"
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwidXNlcklkIjoiSm9leSIsImlhdCI6MTUxNjIzOTAyMn0.4pr06P8QhylsKdMzn9Wu-n5pW11Mz3VCDFvPHbITbC0


# token for "Bob"
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwidXNlcklkIjoiQm9iIiwiaWF0IjoxNTE2MjM5MDIyfQ.PGK1lxNdMQ2VZSs-IbCZ4RTBWX-77KA54gcyuZU8aTc
```

## High-level Design
### Uploading files
- client gets a pre-signed upload URL from the server
- client uploads file via the pre-signed URL directly to S3
- S3 notifies Lambda to add metadata (e.g. shares) to DynamoDB

### Sharing files
- DynamoDB `shares table`: userId (PK), fileId (SK)
- users can look for all rows with `userId = <userId>` to find all files shared with them

# Deep Dives
## How can you support large files?
### Key considerations
- Progress Indicator: show upload progress to the user.
- Resumable Uploads: allow users to resume uploads if internet fails.
### Limitations
We can't upload a large file in single POST request due to:
- Web servers have timeout limits to prevent indefinite waiting for a response.
- Popular web servers like Nginx and Apache have a default size limits of < 2GB; AWS API Gateway has a limit of 10MB for payload size.
- Network interruptions can cause large uploads to fail, requiring a complete re-upload.
- Users have no indication of upload progress, leading to frustration.
### Solution
- Client breaks the file into smaller chunks and uploads each chunk separately.
- Use S3 multipart upload to upload large files in chunks.

## How can we make uploads, downloads, and syncing as fast as possible?
- Compression algos e.g. Gzip, Brotli
- Always compress before encrypting (encryption introduces randomness, which reduces compression efficiency)

## How can you ensure file security?
- Encryption at rest (S3 encryption) and in transit (HTTPS)
- Access control: shares table