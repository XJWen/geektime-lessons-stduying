import org.geektimes.projects.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserAutoTest {

    @Autowired
    private UserService userService;

    @Test
    public void testUserAuto(){

    }
}
