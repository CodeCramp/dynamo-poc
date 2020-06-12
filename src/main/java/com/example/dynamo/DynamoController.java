package com.example.dynamo;

import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.example.dynamo.bean.Student;
import com.example.dynamo.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping("/dynamo")
public class DynamoController {

    @Autowired
    private StudentRepository studentRepo;


    @PostMapping("/table/{tableName}")
    public CreateTableResult createTable(
            @PathVariable String tableName) {
        return studentRepo.createTable(tableName);
    }

    @PostMapping("/students")
    public void insert(
            @RequestBody Student student) throws IOException {
        studentRepo.insert(student);
    }

    @GetMapping("/students/{studentId}/lastName/{lastName}")
    public Student get(
            @PathVariable String studentId,
            @PathVariable String lastName) throws IOException {
        return studentRepo.get(studentId, lastName);
    }

    @GetMapping("/students/lastName/{lastName}")
    public String getAll(
            @PathVariable String lastName) throws IOException {
        try {
            studentRepo.get(lastName);
        } catch (Exception e){}

        return "AmazonDynamoDBException: Query condition missed key schema element." +
                "It means - You're trying to run a query using a condition that does not include the primary key." +
                "Read - https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Query.html";
    }

    @PutMapping("/students")
    public Student update(
            @RequestBody Student student) throws IOException {
        return studentRepo.update(student);
    }

    @DeleteMapping("/students/{studentId}/lastName/{lastName}")
    public void delete(
            @PathVariable String studentId,
            @PathVariable String lastName) throws IOException {
        studentRepo.delete(studentId, lastName);
    }
}
