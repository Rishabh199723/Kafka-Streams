package com.example.publisher.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Model implements Serializable {
    String domain;
    String date;
    String country;
    Boolean isDead;
    String A;
    String NS;
    String CNAME;
    String MX;
    String TXT;

}
