package com.niit.collaboration.daoimpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.collaboration.dao.JobDAO;
import com.niit.collaboration.model.Job;
import com.niit.collaboration.model.JobApplication;
@Repository("jobDAO")
public class JobDAOImpl implements JobDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public JobDAOImpl(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public boolean postJob(Job job) {
		try {
		/*	job.setId(getMaxID()+1);*/
			job.setId(getMaxID()+1);
			sessionFactory.getCurrentSession().save(job);
		} catch (HibernateException e) {
			e.printStackTrace();
			
			return false;
		}
return true;
	}

	
	private long getMaxID(){
		String hql = "select max(id) from Job";
return		(Long) sessionFactory.getCurrentSession().createQuery(hql).uniqueResult();
	}
	@Transactional
	public boolean updateJob(Job job) {
		try {
			sessionFactory.getCurrentSession().update(job);
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Transactional
	public List<Job> getAllvacantJobs() {
		String  hql = "from Job where status = 'V'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		
		return query.list();
	}

	@Transactional
	public boolean applyForJob(JobApplication jobApplication) {
		try {
			sessionFactory.getCurrentSession().save(jobApplication);
		} catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	@Transactional
	public boolean updateJobApplication(JobApplication jobApplication) {
		try {
			sessionFactory.getCurrentSession().update(jobApplication);
		} catch (HibernateException e) {
	e.printStackTrace();
	return false;
		}
		return true;

	}

	@Transactional
	public JobApplication get(String userID, String jobID) {
		String hql = "from JobApplication where userID=" +"'" + userID + "' and jobID='"+ jobID +"'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List<JobApplication> list = (List<JobApplication>)query.list();
		if(list != null && !list.isEmpty()){
			return list.get(0);
		}
		return null;
	}

	
	/*@Transactional
	public List<User> list() {
		String hql = "from User";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}
*/
	@Transactional
	public List<Job> list() {
		String hql = "from Job";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}

	@Transactional
	public Job get(String id) {
		String hql = "from Job where id = " + "'" + id + "'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List<Job> list = query.list();
		if (list == null) {
			return null;
		} else {
			return list.get(0);
		}
		
	}
}