package com.example.dynamo.bean;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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