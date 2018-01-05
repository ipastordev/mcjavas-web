package com.iti.mcjavas.orm.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import com.iti.mcjavas.orm.model.Usuarios;
import com.iti.mcjavas.orm.utils.HibernateUtil;

/**
 * @author WILMER
 *
 */
public class UsuariosDao {


	@SuppressWarnings("unchecked")
	public List<Usuarios> findAll() {
		Session session = HibernateUtil.getSessionFactory().openSession();

		Criteria criteria = session.createCriteria(Usuarios.class);
		criteria.addOrder(Order.asc("IdUsuario"));
		return criteria.list();
	}
	
	 public void addUser(Usuarios usuarios) {
	        Transaction trns = null;
	        Session session = HibernateUtil.getSessionFactory().openSession();
	        try {
	            trns = session.beginTransaction();
	            session.save(usuarios);
	            session.getTransaction().commit();
	        } catch (RuntimeException e) {
	            if (trns != null) {
	                trns.rollback();
	            }
	            e.printStackTrace();
	        } finally {
	            session.flush();
	            session.close();
	        }
	    }

    public void deleteUser(int userid) {
        Transaction trns = null;

        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Usuarios usuarios = (Usuarios) session.load(Usuarios.class, new Integer(userid));
            session.delete(usuarios);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        } finally {
            session.flush();
            session.close();
        }
    }
    
    public void updateUser(Usuarios user) {
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns = session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        } finally {
            session.flush();
            session.close();
        }
    }
    
    public Usuarios getUserById(int userid) {
        Usuarios user = null;
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns = session.beginTransaction();
            String queryString = "from Usuarios where IdUsuario = :IdUsuario";
            Query query = session.createQuery(queryString);
            query.setInteger("IdUsuario", userid);
            user = (Usuarios) query.uniqueResult();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            session.flush();
            session.close();
        }
        return user;
    }
    
    

}