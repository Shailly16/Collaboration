package com.niit.collaboration.daoimpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.collaboration.dao.EventDAO;
import com.niit.collaboration.model.Event;

@Repository("eventDAO")
public class EventDAOImpl implements EventDAO {
	
	@Autowired
	SessionFactory sessionFactory;
	
	public EventDAOImpl(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public boolean save(Event event) {
	
		try {
			sessionFactory.getCurrentSession().save(event);
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}

	}

	@Transactional
	public boolean update(Event event) {
		try {
			sessionFactory.getCurrentSession().update(event);
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
			
		}
		
	}

	@Transactional
	public boolean delete(Event event) {
		try {
			sessionFactory.getCurrentSession().delete(event);
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
			
		}
		
	}

	@Transactional
	public List<Event> list() {
		
		String hql = "from Event";
	Query query =	sessionFactory.getCurrentSession().createQuery(hql);
	return query.list();
		
		
		
		
	}

	@Transactional
	public Event get(String id)  {
		String hql = "from Event where id = " + "'" + id + "'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
	
		List<Event> list = query.list();
		if (list == null) {
			return null;
		} else {
			return list.get(0);
		}

	}

}