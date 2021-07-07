package org.geektime.spring.orm;

import org.geektime.spring.jmx.User;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class HibernateDemo {

    public static void main(String[] args) {
        SessionFactory sessionFactory = getSessionFactory();
        assert sessionFactory != null;
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(User.class);
        List list = criteria.add(Restrictions.eq("id",1L)).list();
        Query query = session.createQuery("from USER where id=:id",Object.class);
        query.setParameter("id",1L);
        query.executeUpdate();
        List<Object> resultList = query.getResultList();
    }

    public static SessionFactory getSessionFactory() {return null;}

}
