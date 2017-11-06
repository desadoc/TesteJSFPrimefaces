package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import exception.NotFoundException;

public abstract class AModelDAO<T>  {
	
	private final Class<T> type;

	@PersistenceUnit
	private EntityManagerFactory emf;
	
	public AModelDAO(Class<T> type) {
		this.type = type;
	}
	
	public Class<T> getType() {
		return type;
	}
	
	public String getEntityName() {
		return emf.getMetamodel().entity(this.getType()).getName();
	}
	
	public EntityManagerFactory getEntityManagerFactory() {
		return emf;
	}
	
	@SuppressWarnings("unchecked")
	public T findOne(Integer id) {
		String name = this.getEntityName();
		EntityManager em = this.getEntityManagerFactory().createEntityManager();
		
		Query q = em.createQuery(
				String.format("select r from %s r where r.id = %d", name, id),
				this.getType()
		);
		List<T> results = q.getResultList();
		
		if (results.isEmpty()) {
			return null;
		}
		
		T r = results.get(0);
		
		em.close();
		return r;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		String name = this.getEntityName();
		EntityManager em = this.getEntityManagerFactory().createEntityManager();
		
		Query q = em.createQuery(
				String.format("select r from %s r", name),
				this.getType()
		);
		
		List<T> r = q.getResultList();
		
		em.close();
		return r;
	}
	
	public T insert(T obj) {
		EntityManager em = this.getEntityManagerFactory().createEntityManager();
		em.getTransaction().begin();
		
		em.persist(obj);
		em.flush();
		em.close();
		
		em.getTransaction().commit();
		
		return obj;
	}
	
	public T update(T obj) {
		EntityManager em = this.getEntityManagerFactory().createEntityManager();
		em.getTransaction().begin();
		
		obj = em.merge(obj);
		em.flush();
		em.close();
		
		em.getTransaction().commit();
		
		return obj;
	}
	
	public void delete(Integer id) throws NotFoundException {
		EntityManager em = this.getEntityManagerFactory().createEntityManager();
		em.getTransaction().begin();
		
		T obj = this.findOne(id);
		
		if (obj == null) {
			throw new NotFoundException();
		}
		obj = em.merge(obj);
		em.remove(obj);
		
		em.flush();
		em.close();
		
		em.getTransaction().commit();
	}
	
	public Long count() {
		String name = this.getEntityName();
		EntityManager em = this.getEntityManagerFactory().createEntityManager();
		em.getTransaction().begin();
		
		Query q = em.createQuery(
				String.format("select count(*) from %s", name),
				Long.class
		);
		Long r = (Long) q.getSingleResult();
		
		em.close();
		em.getTransaction().commit();
		
		return r;
	}
}
