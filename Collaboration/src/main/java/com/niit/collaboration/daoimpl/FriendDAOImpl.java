package com.niit.collaboration.daoimpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.collaboration.dao.FriendDAO;
import com.niit.collaboration.model.Friend;
@Repository("friendDAO")
public class FriendDAOImpl implements FriendDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public FriendDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	private Integer getMaxId()
	{
		String hql = "select max(id) from Friend ";
		Query query = sessionFactory.openSession().createQuery(hql);
		Integer maxID = (Integer) query.uniqueResult();
		return maxID;
	}


	@Transactional
	public List<Friend> getMyFriends(String userID) {
		String hql ="from Friend where userID =" +"'" +userID +"' and status ='"+ "A'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List<Friend> list =(List<Friend>) query.list();
	
		return list;
	}
	@Transactional
	public Friend get(String userID, String friendID) {
		String hql = "from Friend where userID=" +"'" + userID + "' and friendID='"+ friendID +"'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List<Friend> list = (List<Friend>)query.list();
		if(list != null && !list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	@Transactional
	public boolean save(Friend friend) {
		try {
			friend.setId(getMaxId()+1);
			sessionFactory.getCurrentSession().save(friend);
			return true;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	@Transactional
	public boolean update(Friend friend) {

		try {
			sessionFactory.getCurrentSession().update(friend);
			return true;
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			// TODO Auto-generated method stub
			return false;
		}
	}
	@Transactional
	public void delete(String userID, String friendID) {
	   Friend friend = new Friend();
	   friend.setFriendID(friendID);
	   friend.setUserID(userID);
	   sessionFactory.getCurrentSession().delete(friend);
		
	}
	@Transactional
	public List<Friend> getNewFriendRequest(String userID) {
		
		String hql = "from Friend where userID ="+"'" +userID +"'	and status ='" +"N'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List<Friend> list = (List<Friend>) query.list();
		
		
		// TODO Auto-generated method stub
		return list;
	}


	@Transactional
	public List<Friend> getMyFriendsT(String id) {
		String hql ="from Friend where id =" +"'" +id +"'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List<Friend> list =(List<Friend>) query.list();
	
		return list;
	}

@Transactional
	public void setOnline(String userID) {
	String hql ="UPDATE Friend SET isOnline = 'Y' where userID='" + userID +"'";
	Query query = sessionFactory.getCurrentSession().createQuery(hql);
	query.executeUpdate();
		
	}

@Transactional
	public void setOffline(String userID) {
		String hql = "UPDATE Friend set isOffline ='N' where userID='" + userID +"'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.executeUpdate();
		
	}

}
 