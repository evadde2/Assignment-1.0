package com.cinco;

import java.util.List;
import java.util.ArrayList;

public class Person {
    private String uuid;
    private String firstName;
    private String lastName;
    private String phoneNumber; 
    private List<String> emailList; 

    public Person(String uuid, String firstName, String lastName, String phone, List<String> emails) {
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phone;
        
        if (emails == null || emails.isEmpty()) {
            this.emailList = new ArrayList<>();
            this.emailList.add("NO EMAILS");
        } else {
            this.emailList = emails;
        }
    }

    public String getUuid() { return uuid; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhoneNumber() { return phoneNumber; }
    public List<String> getEmailList() { return emailList; }
}
