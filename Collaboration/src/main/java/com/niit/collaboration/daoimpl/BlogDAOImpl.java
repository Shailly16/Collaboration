package com.niit.collaboration.daoimpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.collaboration.dao.BlogDAO;
import com.niit.collaboration.model.Blog;

@Repository("blogDAO")
public class BlogDAOImpl implements BlogDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public BlogDAOImpl(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}
	
	@Transactional
	public boolean save(Blog blog) {
		try {
			sessionFactory.getCurrentSession().save(blog);
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
			
		}
		
	}

	@Transactional
	public boolean update(Blog blog) {

		try {
			sessionFactory.getCurrentSession().update(blog);
			return true;
		} catch (HibernateException e) {
			
			e.printStackTrace();
			
			return false;
		}
	}

	@Transactional
	public boolean delete(Blog blog) {
		try {
			sessionFactory.getCurrentSession().delete(blog);
			return true;
		} catch (HibernateException e) {
			
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public Blog get(Integer id) {
		String hql = "from Blog where id = " + "'" + id + "'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
	
		List<Blog> list = query.list();
		if (list == null) {
			return null;
		} else {
			return list.get(0);
		}

	}

	@Transactional
	public List<Blog> list() {
		String hql = "from Blog";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}

}