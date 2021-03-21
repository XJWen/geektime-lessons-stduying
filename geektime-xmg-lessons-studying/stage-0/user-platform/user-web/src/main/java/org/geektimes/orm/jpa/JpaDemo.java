package org.geektimes.orm.jpa;

import org.apache.derby.jdbc.EmbeddedDataSource;
import org.geektimes.projects.user.domain.User;

import javax.annotation.Resource;
import javax.persistence.*;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class JpaDemo {

    public static final String CLAS_NAME = JpaDemo.class.getName();

    private static final Logger logger = Logger.getLogger(CLAS_NAME);

    @PersistenceContext(name = "emf")
    private EntityManager entityManager;

    //依赖注入，通过定义多个Bean来处理多数据源
    @Resource(name = "primaryDataSource")
    private DataSource dataSource;

    /**
     * 先构建EntityManagerFactory-->EntityManager-->EntityTransaction
     * EntityManager-->CRUD
     *           --Hibernate Session
     *  EntityTransaction-->涉及事务
     *  javax.persistence.Query-->JPA查询API接口（面向对象）
     *
     *  1. Persistence API 获取 EntityManagerFactory
     * 通过 META-INF/persistence.xml 文件配置
     * 实现 Persistence
     * 2. EntityManagerFactory 获取 EntityManager
     **/
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("emf",getProperties());
        //Hibernate Session对象
        EntityManager entityManager = factory.createEntityManager();
        User user = new User();
        user.setId(8L);
        user.setName("小马哥");
        user.setPassword("******");
        user.setEmail("mercyblitz@gmail.com");
        user.setPhoneNumber("123456789");

        EntityTransaction transaction= entityManager.getTransaction();
        //事务控制
        transaction.begin();
        entityManager.persist(user);
        transaction.commit();
        System.out.println(entityManager.find(User.class,1L));
    }

    //Hibernate 配置
    private static Map<String,Object> getProperties(){
        Map<String,Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.DerbyDialect");
        properties.put("hibernate.id.new_generator_mappings", false);
        properties.put("hibernate.connection.datasource", getDataSource());
        return properties;
    }

    private static DataSource getDataSource(){
        EmbeddedDataSource dataSource = new EmbeddedDataSource();
        dataSource.setDatabaseName("/db/user-platform");
        dataSource.setCreateDatabase("create");
        return dataSource;
    }
}
