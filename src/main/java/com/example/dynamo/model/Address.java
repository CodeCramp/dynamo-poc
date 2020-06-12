package com.example.dynamo.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@DynamoDBDocument
public class Address implements Serializable {

	@DynamoDBAttribute
	private String addressLine1;

	@DynamoDBAttribute
	private String addressLine2;

	@DynamoDBAttribute
	private String state;

	@DynamoDBAttribute
	private String city;

	@DynamoDBAttribute
	private String zipCode;
}