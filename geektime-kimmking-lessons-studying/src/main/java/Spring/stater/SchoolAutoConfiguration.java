package Spring.stater;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan("Spring.bean")
@EnableConfigurationProperties
@ConditionalOnProperty
@AutoConfigureBefore
@RequiredArgsConstructor
public class SchoolAutoConfiguration implements EnvironmentAware {


    @Override
    public void setEnvironment(Environment environment) {

    }
}
