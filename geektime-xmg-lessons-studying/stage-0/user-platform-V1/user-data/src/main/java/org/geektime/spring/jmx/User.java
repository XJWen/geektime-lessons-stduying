package org.geektime.spring.jmx;



import lombok.Getter;
import lombok.Setter;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * Pojo类 jmx注解
 * */
@ManagedResource(objectName = "org.geektimes:name=User")
public class User {

    @Getter
    @Setter
    private int id;

    private String name;

    private int age;

    @ManagedAttribute
    public String getName() {return name;}

    @ManagedOperation
    @ManagedOperationParameter(name="name",description = "name")
    public void setName(String name) {this.name = name;}

    @ManagedAttribute
    public int getAge() {return age;}

    @ManagedOperation
    @ManagedOperationParameter(name="age",description="age")
    public void setAge(int age) {this.age = age;}


    @Override
    @ManagedOperation
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
