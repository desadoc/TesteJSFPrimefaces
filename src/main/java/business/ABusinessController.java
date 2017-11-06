package business;

import java.util.List;

import javax.inject.Inject;

import dao.AModelDAO;
import exception.NotFoundException;
import exception.ValidationException;

public abstract class ABusinessController<T, ModelDAO extends AModelDAO<T>> {

	@Inject
	private AModelDAO<T> dao;
	
	public AModelDAO<T> getDAO() {
		return dao;
	}
	
	public T findOne(Integer id) {
		return this.getDAO().findOne(id);
	}
	
	public List<T> findAll() {
		return this.getDAO().findAll();
	}
	
	public T insert(T obj) throws ValidationException {
		return this.getDAO().insert(obj);
	}
	
	public T update(T obj) throws ValidationException {
		return this.getDAO().update(obj);
	}
	
	public void delete(Integer id) throws NotFoundException {
		this.getDAO().delete(id);
	}
}
