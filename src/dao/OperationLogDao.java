package dao;

import entity.OperationLog;
import entity.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OperationLogDao {

/*
    public void insertOperationLog(OperationLog operationLog, User user) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            operationLog.setUser(user);
            session.save(operationLog);

            transaction.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public ArrayList<OperationLog> getOperationList(String inputStartDate, String inputEndDate) {
        List<OperationLog> operationLogList = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate = formatter.parse(inputStartDate);
            Date endDate = formatter.parse(inputEndDate);
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            Query query = session.createQuery("from OperationLog o where o.date " +
                    "between :startDate and :endDate ", OperationLog.class);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            operationLogList = query.list();

            transaction.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (ArrayList<OperationLog>) operationLogList;
    }

}
