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

# module "iam" {
#   source        = "./modules/iam"
#   bucket_name   = local.bucket_name
#   s3_policy_arn = module.s3.s3_policy_arn
# }
