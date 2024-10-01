package bank.dao;

import javax.persistence.EntityManager;

public abstract class AbstractDao<T, ID> {

	private Class<T> entityClass;
	
	public AbstractDao(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	public abstract EntityManager em();
	
	public T findById(ID id) {
		return em().find(entityClass, id);
	}
}
