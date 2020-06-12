package com.example.dynamo;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.*;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.example.dynamo.model.Student;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping("/dynamo")
public class DynamoController {

    private static final AWSCredentials AWS_CREDENTIALS;
    private static final AmazonDynamoDB DYNAMO_CLIENT;

    static {
        // Your accesskey and secretkey
        AWS_CREDENTIALS = new BasicAWSCredentials(
                "mohit",
                "mohit"
        );

        DYNAMO_CLIENT = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(AWS_CREDENTIALS))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:9090", "us-east-1"))
                .build();

    }

    @PostMapping("/table/{tableName}")
    public CreateTableResult createTable(
            @PathVariable String tableName) {
        DynamoDBMapper mapper = new DynamoDBMapper(DYNAMO_CLIENT);
        CreateTableRequest req = mapper.generateCreateTableRequest(Student.class);
        req.setTableName(tableName);
        // Table provision throughput is still required since it cannot be specified in your POJO
        req.setProvisionedThroughput(new ProvisionedThroughput(5L, 5L));
        // Fire off the CreateTableRequest using the low-level client
        return DYNAMO_CLIENT.createTable(req);
    }
}
