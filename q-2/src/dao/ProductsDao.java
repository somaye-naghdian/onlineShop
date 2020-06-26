package dao;

import entity.Products;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductsDao {
    Session session = null;
    Transaction transaction = null;

    public void showProductsList(String productCategory) {
        List<Products> productsList;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            Criteria criteria = session.createCriteria(Products.class);
            criteria.add(Restrictions.eq("category", productCategory));

            productsList = criteria.list();
            for (Iterator iterator = productsList.iterator(); iterator.hasNext(); ) {
                Products product = (Products) iterator.next();
                System.out.println("category : " + product.getCategory() + "\n" +
                        "product name :" + product.getName() + "\n" + "price :" + product.getPrice() +
                        "\n" + "Brand :" + product.getBrand() + "\n" + "stock :" + product.getStock());
                System.out.println("_______________________");

            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    public Products searchProduct(String category, String name) {
        List<Products> productsList = null;
        Products product = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("from Products p where p.category=:category and " +
                    "p.name=:name", Products.class);
            query.setParameter("category", category);
            query.setParameter("name", name);

            productsList = query.list();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        for (Iterator iterator = productsList.iterator(); iterator.hasNext(); ) {
            product = (Products) iterator.next();

            product.setName(product.getName());
            product.setCategory(product.getCategory());
            product.setBrand(product.getBrand());
            product.setPrice(product.getPrice());
            product.setStock(product.getStock());
        }
        return product;
    }

    public void updateProductsStock(String name, int itemCount) {
        List<Products> productsList = null;
        Products product = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("from Products p where p.name=:name", Products.class);
            query.setParameter("name", name);
            productsList = query.list();
            for (Iterator iterator = productsList.iterator(); iterator.hasNext(); ) {
                product = (Products) iterator.next();
                product.setStock(product.getStock() - itemCount);
            }
            session.update(product);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void printCategory() {
        Object categories;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Products.class);
            ProjectionList projectionList = Projections.projectionList();
            projectionList.add(Projections.property("category"));
            criteria.setProjection(projectionList);

            categories = criteria.list().stream().collect(Collectors.toSet());
            System.out.println(categories);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }
}
