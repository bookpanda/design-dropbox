resource "random_id" "bucket_suffix" {
  byte_length = 8
}

locals {
  bucket_name = "${var.bucket_prefix}-${random_id.bucket_suffix.hex}"
}

module "s3" {
  source      = "./modules/s3"
  bucket_name = local.bucket_name
}

module "dynamodb" {
  source = "./modules/dynamodb"
}

module "lambda" {
  source           = "./modules/lambda"
  shares_table_arn = module.dynamodb.shares_table_arn
  bucket_id        = module.s3.bucket_id
  bucket_arn       = module.s3.bucket_arn
}

# module "iam" {
#   source        = "./modules/iam"
#   bucket_name   = local.bucket_name
#   s3_policy_arn = module.s3.s3_policy_arn
# }
