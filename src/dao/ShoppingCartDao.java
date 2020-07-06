package dao;

import entity.ShoppingCart;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ShoppingCartDao {


    public void insertShoppingCart(ShoppingCart shoppingCart) {
        try {
          Session  session = HibernateUtil.getSessionFactory().openSession();
          Transaction  transaction = session.beginTransaction();
            session.saveOrUpdate(shoppingCart);

            transaction.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  void deleteFromShoppingCart(ShoppingCart shoppingCart){
        try {
            Session  session = HibernateUtil.getSessionFactory().openSession();
            Transaction  transaction = session.beginTransaction();

            session.delete(shoppingCart);

            transaction.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
