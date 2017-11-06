package view;

import javax.inject.Inject;

import business.ABusinessController;
import dao.AModelDAO;

public abstract class AViewController<T, U extends AModelDAO<T>, V extends ABusinessController<T, U>> {

	T bean;

	@Inject
	private ABusinessController<T, U> bc;
	
	@SuppressWarnings("unchecked")
	public AViewController(Class<T> type) {
		try {
			this.bean = (T) type.getConstructors()[0].newInstance();
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}
	
	public ABusinessController<T, U> getController() {
		return bc;
	}
	
	public T getBean() {
		return bean;
	}
	
	public void setBean(T obj) {
		this.bean = obj;
	}
}
