package com.niit.collaboration.daoimpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.collaboration.dao.UserDAO;
import com.niit.collaboration.model.User;







@Repository("userDAO")
public class UserDAOImpl implements UserDAO{

	
	 private static final Logger log = LoggerFactory.getLogger(UserDAOImpl.class);
		 
		@Autowired(required=true)
		private SessionFactory sessionFactory;


		public UserDAOImpl(SessionFactory sessionFactory) {
			try {
				this.sessionFactory = sessionFactory;
			} catch (Exception e) {
				log.error(" Unable to connect to db");
				e.printStackTrace();
			}
		}

		@Transactional
		public List<User> list() {
			log.debug("->->Starting of the method list");
			@SuppressWarnings("unchecked")
			List<User> list = (List<User>) sessionFactory.getCurrentSession()
					.createCriteria(User.class)
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

			return list;
		}

		@Transactional
		public boolean update(User user) {
			log.debug("->->Starting of the method update");
			try {
				sessionFactory.getCurrentSession().update(user);
				return true;
			} catch (HibernateException e) {
				
				e.printStackTrace();
				return false;
			}
			
		}
		
		@Transactional
		public boolean save(User user) {
			log.debug("->->Starting of the method save");
			try {
				sessionFactory.openSession().save(user);
				
				return true;
			} catch (Exception e) {
				
				e.printStackTrace();
				return false;
			}
			
		}
		
			


		@Transactional
		public void delete(String id) {
			log.debug("->->Starting of the method delete");
			log.debug("->->id : " + id);
			User user = new User();
			user.setId(id);
			sessionFactory.getCurrentSession().delete(user);
		}

		@Transactional
		public User get(String id, String password) {
			log.debug("->->Starting of the method get");
			log.debug("->->id : " + id);
			log.debug("->->password : " + password);
			String hql = "from User where id=" + "'"+ id+"'  and password = "
					+"'" + password + "'";
		   return getUser(hql);
		}
		
		@Transactional
		public User get(String id) {
			log.debug("->->Starting of the method get");
			log.debug("->->id : " + id);
			String hql = "from User where id=" + "'"+ id + "'" ;
			 return getUser(hql);
			
		}
		
		private User getUser(String hql)
		{
			log.debug("->->Starting of the method getUser");
			log.debug("->->hql : " +hql);
	        Query query = sessionFactory.getCurrentSession().createQuery(hql);
			
			@SuppressWarnings("unchecked")
			List<User> list = (List<User>) query.list();
			
			if (list != null && !list.isEmpty()) {
				return list.get(0);
			}
			return null;
		}
		
		@Transactional
		public User authenticate(String id, String password) {
			log.debug("->->Starting of the method isValidUserDetails");
			String hql = "from User where id= '" + id + "' and " + " password ='" + password+"'";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			
			@SuppressWarnings("unchecked")
			List<User> list = (List<User>) query.list();
			
			if (list != null && !list.isEmpty()) {
				return list.get(0);
			}
			
			return null;
		}


		@Transactional
		public void setOnline(String userID) {
			log.debug("Starting of the metnod setOnline");
			String hql =" UPDATE User	SET isOnline = 'Y' where id='" +  userID + "'" ;
			  log.debug("hql: " + hql);
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			query.executeUpdate();
			log.debug("Ending of the metnod setOnline");
			
		}
		
		@Transactional
		public void setOffLine(String userID) {
			log.debug("Starting of the metnod setOffLine");
			String hql =" UPDATE User	SET isOnline = 'N' where id='" +  userID + "'" ;
			  log.debug("hql: " + hql);
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			query.executeUpdate();
			log.debug("Ending of the metnod setOffLine");
			
		}


}

	