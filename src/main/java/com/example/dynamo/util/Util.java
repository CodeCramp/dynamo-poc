package com.example.dynamo.util;

import com.example.dynamo.bean.Address;
import com.example.dynamo.bean.Student;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Util {
    public Student createStudent(String firstName, String lastName, int age) {
        return Student.builder()
                .firstName(firstName)
                .lastName(lastName)
                .age(age)
                .address(createAddress())
                .build();
    }

    private static Address createAddress() {
        return Address.builder()
                .addressLine1("Fortuna-1, #680")
                .addressLine2("15th Cross Rd, 2nd Phase, J. P. Nagar")
                .city("Bengaluru")
                .zipCode("560078")
                .build();
    }
}
