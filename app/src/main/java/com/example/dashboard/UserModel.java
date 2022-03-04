package com.example.dashboard;

public class UserModel {

    String name;
    String age;
    String address;
    String nic;
    String lname;
    String password;
    String email;
    String mobile;


    public UserModel(String name, String age, String address, String nic, String lname, String password, String email, String mobile) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.nic = nic;
        this.lname = lname;
        this.password = password;
        this.email = email;
        this.mobile = mobile;

    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


}

