package com.pojo;


import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

public class User {
    private Integer id;

    @NotEmpty(message = "用户名不能为空")
    private String name;

    @Size(min = 5, max = 10, message = "密码长度不符合标准")
    private String pwd;

    private Integer qq;

    private String email;

    private Integer phone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    public Integer getQq() {
        return qq;
    }

    public void setQq(Integer qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }
}