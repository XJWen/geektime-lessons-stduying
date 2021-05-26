package org.geektimes.projects.user.mybatis.annotation;

import org.apache.commons.configuration.Configuration;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
@Import(MyBatisBeanDefinitionRegistrar.class)
public @interface EnableMyBatis {

    /**
     * @return  the bean name of {@link SqlSessionFactory}
     * */
    String value() default "sqlSessionFactoryBean";

    /**
     * @return DataSource bean name
     * */
    String dataSource();

    /**
     * the location of {@link Configuration}
     * */
    String configLocation();

    /**
     * @return the location of {@link Mapper}
     * @see org.mybatis.spring.annotation.MapperScan
     * */
    String[] mapperLocation() default {};


    String environment() default "SqlSessionFactoryBean";
}
