package com.cx.demo.controller;

import com.cx.demo.entity.User;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {


    /**
     * 只显示name属性
     * @return
     */
    @GetMapping("/getUsersName")
    @JsonView(User.UserOnlyName.class)
    public List<User> getUsers(){
        User user0=new User("a","a","a");
        User user1=new User("b","a","a");
        User user2=new User("c","a","a");
        User user3=new User("d","a","a");
        List<User> userList=new ArrayList<>();
        userList.add(user0);
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        return userList;
    }

    @GetMapping("/getUser/{username}")
    public User getUser(@PathVariable(name = "username") String nickname){
        User user0=new User("a","a","a");
        User user1=new User("b","a","a");
        User user2=new User("c","a","a");
        User user3=new User("d","a","a");
        List<User> userList=new ArrayList<>();
        userList.add(user0);
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        User user=new User();
        for(int i=0;i<userList.size();i++){
            if(userList.get(i).getName().equals(nickname)){
                user=userList.get(i);
            }
        }
        return user;
    }


    /**
     * 显示所有属性
     * @return
     */
    @GetMapping("/getUserDetai")
    @JsonView(User.UserDetail.class)
    public User getUserName(){
        User user0=new User("a","a","a");
        return user0;
    }


    /**
     * 显示所有属性
     * @return
     */
    @PostMapping("/createUser")
    public String createUser(@Valid@RequestBody User user){
        User user0=new User(user.getName(),user.getAge(),user.getGender());
        return user0.getAge();
    }


    /**
     * 只显示name属性
     * @return
     */
    @GetMapping("/login")
    public void getLogin(){
        System.out.println("进来了");
    }
}
