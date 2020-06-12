package com.example.dynamo.repo;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.document.ItemUtils;
import com.amazonaws.services.dynamodbv2.model.*;
import com.example.dynamo.bean.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.amazonaws.services.dynamodbv2.document.ItemUtils.toAttributeValues;
import static com.amazonaws.services.dynamodbv2.document.ItemUtils.toItem;

@Data
@Repository
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class StudentRepository {
	public static final AWSCredentials AWS_CREDENTIALS;
	public static final AmazonDynamoDB DYNAMO_CLIENT;

	static {
		// Your accesskey and secretkey
		AWS_CREDENTIALS = new BasicAWSCredentials(
				"mohit",
				"mohit"
		);

		DYNAMO_CLIENT = AmazonDynamoDBClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(AWS_CREDENTIALS))
				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
						"http://localhost:9090", "us-east-1"))
				.build();

	}

	private DynamoDBMapper mapper = new DynamoDBMapper(DYNAMO_CLIENT);


	public CreateTableResult createTable(String tableName) {
		CreateTableRequest req = createTableCreationRequest(tableName);
		// Table provision throughput is still required since it cannot be specified in your POJO
		req.setProvisionedThroughput(new ProvisionedThroughput(5L, 5L));
		// Fire off the CreateTableRequest using the low-level client
		return DYNAMO_CLIENT.createTable(req);
	}

	public void insert(Student student) {
		mapper.save(student);
	}

	public Student get(String studentId, String lastName) {
		return mapper.load(Student.class, studentId, lastName);
	}

	public List<Student> get(String lastName) {
		return mapper.query(Student.class, createDynamoQueryExpression(lastName));
	}

	public Student update(Student student) {
		try {
			PutItemRequest putItemRequest = new PutItemRequest()
					.withTableName("student")
					.withItem(getItemMapFromObject(student));
			DYNAMO_CLIENT.putItem(putItemRequest);
			return student;
		} catch (ConditionalCheckFailedException exception) {
			log.error("invalid data - " + exception.getMessage());
			throw exception;
		}
	}

	private Map<String, AttributeValue> getItemMapFromObject(Student student) {
		final Map<String, AttributeValue> studentToUpdate = mapper.getTableModel(Student.class).convert(student);
		return toAttributeValues(toItem(studentToUpdate));
	}

	public void delete(String studentId, String lastName) {
		DYNAMO_CLIENT.deleteItem(createDeleteItemRequest(studentId, lastName));
	}

	private DeleteItemRequest createDeleteItemRequest(String studentId, String lastName) {
		Map<String, AttributeValue> key = new HashMap<String, AttributeValue>() {
			{
				put("studentId", new AttributeValue(studentId));
				put("lastName", new AttributeValue(lastName));
			}
		};
		DeleteItemRequest deleteRequest = new DeleteItemRequest().withTableName("student").withKey(key);
		return deleteRequest;
	}

	private CreateTableRequest createTableCreationRequest(String tableName) {
		CreateTableRequest req = mapper.generateCreateTableRequest(Student.class);
		req.setTableName(tableName);
		return req;
	}

	private DynamoDBQueryExpression<Student> createDynamoQueryExpression(String lastName) {
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":val1", new AttributeValue().withS(lastName));

		DynamoDBQueryExpression<Student> queryExpression = new DynamoDBQueryExpression<Student>()
				.withKeyConditionExpression("lastName = :val1").withExpressionAttributeValues(eav);

		return queryExpression;
	}
}