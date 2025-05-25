resource "aws_s3_bucket" "uploads" {
  bucket        = var.bucket_name
  force_destroy = true
  tags = {
    Name = "dropbox-uploads"
  }
}

resource "aws_s3_bucket_public_access_block" "uploads" {
  bucket = aws_s3_bucket.uploads.id

  block_public_acls       = true
  block_public_policy     = true
  ignore_public_acls      = true
  restrict_public_buckets = true
}

resource "aws_iam_policy" "s3_policy" {
  name        = "dropbox-s3-policy"
  description = "Policy to allow access to the S3 bucket"
  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action = [
          "s3:GetObject",
          "s3:PutObject",
          "s3:ListBucket"
        ]
        Effect = "Allow"
        Resource = [
          "arn:aws:s3:::${aws_s3_bucket.uploads.bucket}",
          "arn:aws:s3:::${aws_s3_bucket.uploads.bucket}/*"
        ]
      }
    ]
  })
}
