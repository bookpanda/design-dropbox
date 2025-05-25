variable "region" {
  description = "The region in which the VPC will be created"
  type        = string
}

variable "availability_zone" {
  description = "The availability zone in which the VPC will be created"
  type        = string
}

variable "bucket_prefix" {
  description = "The prefix of the name of the S3 bucket"
  type        = string
}
