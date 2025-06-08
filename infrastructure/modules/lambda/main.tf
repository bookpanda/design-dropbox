resource "aws_iam_role" "lambda_role" {
  name = "s3-to-dynamo-role"
  assume_role_policy = jsonencode({
    Version = "2012-10-17",
    Statement = [{
      Action = "sts:AssumeRole",
      Effect = "Allow",
      Principal = {
        Service = "lambda.amazonaws.com"
      }
    }]
  })
}

resource "aws_iam_role_policy" "lambda_policy" {
  name = "lambda-dynamo-s3"
  role = aws_iam_role.lambda_role.id
  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Action = [
          "dynamodb:PutItem",
          "dynamodb:DeleteItem"
        ],
        Effect   = "Allow",
        Resource = var.shares_table_arn
      },
      {
        Action = [
          "logs:CreateLogGroup",
          "logs:CreateLogStream",
          "logs:PutLogEvents"
        ],
        Effect   = "Allow",
        Resource = "*"
      }
    ]
  })
}

data "archive_file" "lambda_zip" {
  type        = "zip"
  source_dir  = "${path.module}/lambda_function"
  output_path = "${path.module}/lambda_function.zip"
}

resource "aws_lambda_function" "s3_dynamo_updater" {
  function_name    = "dropbox-s3-dynamo-updater"
  handler          = "lambda_function.handler"
  runtime          = "python3.11"
  role             = aws_iam_role.lambda_role.arn
  filename         = data.archive_file.lambda_zip.output_path
  source_code_hash = data.archive_file.lambda_zip.output_base64sha256
  timeout          = 10
}

resource "aws_s3_bucket_notification" "s3_event" {
  bucket = var.bucket_id

  lambda_function {
    lambda_function_arn = aws_lambda_function.s3_dynamo_updater.arn
    events              = ["s3:ObjectCreated:*", "s3:ObjectRemoved:*"]
  }

  depends_on = [aws_lambda_permission.allow_s3]
}

resource "aws_lambda_permission" "allow_s3" {
  statement_id  = "AllowS3Invoke"
  action        = "lambda:InvokeFunction"
  function_name = aws_lambda_function.s3_dynamo_updater.function_name
  principal     = "s3.amazonaws.com"
  source_arn    = var.bucket_arn
}
