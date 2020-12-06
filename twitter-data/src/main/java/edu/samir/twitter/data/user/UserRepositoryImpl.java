package edu.samir.twitter.data.user;

import java.util.List;

import edu.samir.twitter.model.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository("userRepository")
@Transactional
public class UserRepositoryImpl implements IUserRepository {

	@Autowired
	private SessionFactory sessionFactory;

	public UserRepositoryImpl() {
	}

	@Override
	public void insertUser(User user) {
		getSession().persist(user);
	}

	@Override
	public User selectUserById(Long userId) {
		return getSession().get(User.class, userId);
	}

	@Override
	public User selectUserByName(String firstName, String lastName) {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
		Root<User> userRoot = criteriaQuery.from(User.class);
		Predicate restriction = builder.and(
				builder.equal(userRoot.get("firstName"), firstName),
				builder.equal(userRoot.get("lastName"), lastName)
				);
		criteriaQuery.select(userRoot).where(restriction);
		return getSession().createQuery(criteriaQuery).getSingleResult();
	}

	@Override
	public List<User> selectAllUsers() {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
		criteriaQuery.select(criteriaQuery.from(User.class));
		return getSession().createQuery(criteriaQuery).getResultList();		 
	}

	@Override
	public void updateUser(User user) {
		getSession().merge(user);
	}

	@Override
	public void removeUserById(Long userId) {
		getSession().delete(selectUserById(userId));
	}

	private Session getSession() {
		//return entityManagerFactory.unwrap(SessionFactory.class).openSession();
		return sessionFactory.getCurrentSession();
	}

}
