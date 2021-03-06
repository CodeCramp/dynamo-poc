package com.example.dynamo.bean;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "student")
public class Student implements Serializable {

	@DynamoDBHashKey(attributeName = "studentId")
	@DynamoDBAutoGeneratedKey
	private String studentId;

	@DynamoDBAttribute
	private String firstName;

	@DynamoDBRangeKey
	private String lastName;

	@DynamoDBAttribute
	private int age;

	@DynamoDBAttribute
	private Address address;
}
