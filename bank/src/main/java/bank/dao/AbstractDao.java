package bank.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public abstract class AbstractDao<T, ID> {

	private Class<T> entityClass;
	
	public AbstractDao(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	public abstract EntityManager em();
	
	public T findById(ID id) {
		return em().find(entityClass, id);
	}
	
	public void create(T entity) {
		em().persist(entity);
	}
	
	public void update(T entity) {
		em().merge(entity);
	}
	
	public void delete(T entity) {
		em().remove(em().merge(entity));
	}
	
	public List<T> findAll() {
		CriteriaBuilder cb = em().getCriteriaBuilder();
		CriteriaQuery<T> q = cb.createQuery(entityClass);
		Root<T> root = q.from(entityClass);
		//q.select(root); ez a default
		return em().createQuery(q).getResultList();
	}
}
