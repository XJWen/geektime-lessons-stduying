package Spring.bean;

import lombok.Data;

import javax.annotation.Resource;

@Data
public class School implements ISchool{

    @Resource(name = "lessons")
     Klass klass;

    Student student;

    @Override
    public void lessons() {
        klass.dong();
    }
}
