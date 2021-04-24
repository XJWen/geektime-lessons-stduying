package Spring.bean;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component(value = "lessons")
public class Klass {

    List<Student> students;

    @Bean
    public void dong(){
        System.out.println(this.getStudents());
    }
}
