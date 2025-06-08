package com.dropbox.shares;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.Map;

@Service
public class SharesService {
        private final DynamoDbClient dynamoDbClient;
        private final String tableName;

        public SharesService(DynamoDbClient dynamoDbClient,
                        @Value("${app.dynamodb.shares-table}") String tableName) {
                this.dynamoDbClient = dynamoDbClient;
                this.tableName = tableName;
        }

        public boolean shareExists(String userId, String fileId) {
                Map<String, AttributeValue> key = Map.of(
                                "userId", AttributeValue.builder().s(userId).build(),
                                "fileId", AttributeValue.builder().s(fileId).build());

                GetItemRequest request = GetItemRequest.builder()
                                .tableName(tableName)
                                .key(key)
                                .build();

                return dynamoDbClient.getItem(request).hasItem();
        }

        public void putItem(String userId, String fileName, String metadata) {
                Map<String, AttributeValue> item = new HashMap<>();
                item.put("userId", AttributeValue.builder().s(userId).build());
                item.put("fileName", AttributeValue.builder().s(fileName).build());
                item.put("metadata", AttributeValue.builder().s(metadata).build());

                PutItemRequest request = PutItemRequest.builder()
                                .tableName(tableName)
                                .item(item)
                                .build();

                dynamoDbClient.putItem(request);
        }

        public Map<String, AttributeValue> getItem(String userId, String fileName) {
                Map<String, AttributeValue> key = Map.of(
                                "userId", AttributeValue.builder().s(userId).build(),
                                "fileName", AttributeValue.builder().s(fileName).build());

                GetItemRequest request = GetItemRequest.builder()
                                .tableName(tableName)
                                .key(key)
                                .build();

                return dynamoDbClient.getItem(request).item();
        }

        public void deleteItem(String userId, String fileName) {
                Map<String, AttributeValue> key = Map.of(
                                "userId", AttributeValue.builder().s(userId).build(),
                                "fileName", AttributeValue.builder().s(fileName).build());

                DeleteItemRequest request = DeleteItemRequest.builder()
                                .tableName(tableName)
                                .key(key)
                                .build();

                dynamoDbClient.deleteItem(request);
        }

        public void queryByUser(String userId) {
                QueryRequest request = QueryRequest.builder()
                                .tableName(tableName)
                                .keyConditionExpression("userId = :uid")
                                .expressionAttributeValues(Map.of(
                                                ":uid", AttributeValue.builder().s(userId).build()))
                                .build();

                QueryResponse response = dynamoDbClient.query(request);
                response.items().forEach(item -> {
                        System.out.println(item);
                });
        }
}
