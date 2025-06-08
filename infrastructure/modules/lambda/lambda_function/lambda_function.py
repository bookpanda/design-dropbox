from typing import Any, Dict

import boto3

dynamodb = boto3.resource("dynamodb")
table = dynamodb.Table("dropbox-shares")


def handler(event: Dict[str, Any], context: Any) -> Dict[str, str]:
    print("Received event:", event)

    for record in event.get("Records", []):
        if record.get("eventSource") == "aws:s3":
            s3_info = record.get("s3", {})
            key = s3_info.get("object", {}).get("key", "")
            key = key.replace("+", " ")  # S3 might encode spaces as +

            if "/" in key:
                user_id = key.split("/")[0]
                file_id = key  # full key
                print(f"Adding share for user: {user_id}, file: {file_id}")

                table.put_item(Item={"userId": user_id, "fileId": file_id})

    return {"status": "ok"}
