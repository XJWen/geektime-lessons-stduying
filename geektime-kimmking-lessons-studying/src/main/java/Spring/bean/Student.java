package Spring.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Student implements Serializable {

    private int id;

    private String name;

    public Student(){

    }

    public Student(int id,String name){
        this.id = id;
        this.name = name;
    }
}
