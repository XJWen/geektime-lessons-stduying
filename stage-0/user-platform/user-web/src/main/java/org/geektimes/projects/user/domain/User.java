package org.geektimes.projects.user.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * 用户领域对象
 * 序列化
 * 双向对象关系--级联
 * 单向对象关系--数据外键约束
 * 多态确保接口的契约性/继承确保属性参数的复用性
 * @since 1.0
 */
@Entity
@Table(name = "USERS")
public class User implements Serializable {


    //id 表字段的属性定义
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(1)
    @Digits(integer = Integer.SIZE, fraction = 0)
    private Long id;

    @Column
    private String name;

    @Column
    @Length(min = 6,max = 32)
    private String password;

    @Column
    private String email;

    @Column
    @Pattern(regexp = "^1[3|4|5|7|8][0-9]{9}$",message = "输入号码格式不正确")
    private String phoneNumber;

    public User(){

    }

    public User(String userName, String password, String email, String phoneNumber) {
        this.name= userName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){ return true;}
        if (o == null || getClass() != o.getClass()){ return false;}
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(phoneNumber, user.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, email, phoneNumber);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }


}
