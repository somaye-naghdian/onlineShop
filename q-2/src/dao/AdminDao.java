package dao;

import entity.Admin;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.Iterator;
import java.util.List;


public class AdminDao {
    Session session = null;
    Transaction transaction = null;

    public Admin passwordValidation(String inputUsername, String inputPassword) {
        String passwordQuery = null;
        List<Admin> adminList;
        Admin admin = new Admin();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Admin.class, "a");
            criteria.add(Restrictions.eq("a.username", inputUsername));
            adminList = criteria.list();

            for (Iterator iterator = adminList.iterator(); iterator.hasNext(); ) {
                admin = (Admin) iterator.next();
                passwordQuery = admin.getPassword();
            }
            if (passwordQuery.equals(inputPassword)) {
                admin.setUsername(inputUsername);
                admin.setPassword(passwordQuery);
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return admin;
    }
}
