package com.niit.collaboration.daoimpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.collaboration.dao.FriendDAO;
import com.niit.collaboration.model.Friend;
@Repository("friendDAO")
public class FriendDAOImpl implements FriendDAO {
	private static final Logger log = LoggerFactory.getLogger(FriendDAOImpl.class);
	
	@Autowired(required=true)
	private SessionFactory sessionFactory;

	public FriendDAOImpl(SessionFactory sessionFactory) {
		try {
			this.sessionFactory = sessionFactory;
		} catch (Exception e) {
			log.error(" Unable to connect to db");
			e.printStackTrace();
		}
	}
	
	private Integer getMaxId()
	{
		log.debug("->->Starting of the method getMaxId");
		String hql = "select max(id) from Friend ";
		Query query = sessionFactory.openSession().createQuery(hql);
		Integer maxID; 
		try {
			maxID = (Integer) query.uniqueResult();
		} catch (Exception e) {
			
			e.printStackTrace();
			return 100;
		}
		log.debug("Max id :" + maxID);
		return maxID;
	}


	@Transactional
	public List<Friend> getMyFriends(String userID) {
String hql1 = "select friendID  from Friend where userID='" + userID + "' and status = 'A' ";

		/*+ " union  " +*/

String hql2= "select userID from Friend where friendID='" + userID + "' and status = 'A'";

log.debug("getMyFriends hql1 : " + hql1);
log.debug("getMyFriends hql2 : " + hql2);

List<Friend> list1 = sessionFactory.openSession().createQuery(hql1).list();
List<Friend> list2 = sessionFactory.openSession().createQuery(hql2).list();



list1.addAll(list2);

return list1;

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
			
			e.printStackTrace();
			
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


@Transactional
public Friend get(String userID)
{
	String hql = "from Friend where userID=" + "'" + userID + "' and friendID= '" + userID + "'";

	log.debug("hql: " + hql);
	Query query = sessionFactory.openSession().createQuery(hql);

	return (Friend) query.uniqueResult();

}
@Transactional
public List<Friend> getRequestsSendByMe(String userID) {
	String hql = "select friendID from Friend where userID=" + "'" + userID + "' and status ='" + "N'";

	log.debug(hql);
	return  sessionFactory.openSession().createQuery(hql).list();

}

}