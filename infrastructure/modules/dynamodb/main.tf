resource "aws_dynamodb_table" "shares_table" {
  name         = "dropbox-shares"
  billing_mode = "PAY_PER_REQUEST"
  hash_key     = "userId"
  range_key    = "fileId"

  attribute {
    name = "userId"
    type = "S"
  }

  attribute {
    name = "fileId"
    type = "S"
  }

  tags = {
    Name = "dropbox-shares"
  }
}
