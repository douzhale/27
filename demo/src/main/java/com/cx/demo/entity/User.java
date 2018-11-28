package com.cx.demo.entity;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.NotBlank;
import java.util.Collection;

public class User {


    public interface UserDetail extends UserOnlyName{}

    public interface UserOnlyName{}


    @JsonView(UserOnlyName.class)
    @NotBlank
    private String name;

    @JsonView(UserDetail.class)
    private String age;
    @JsonView(UserDetail.class)
    private String gender;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }



    public User(String name, String age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public User(){

    }

}
