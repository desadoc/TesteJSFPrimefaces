package business;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import dao.ItemDAO;
import domain.Item;
import exception.ValidationException;

@Dependent
public class ItemBC extends ABusinessController<Item, ItemDAO> implements Serializable {
	
	private static final long serialVersionUID = -4479095686699160947L;

	private boolean validate(Item obj) throws ValidationException {
		if (obj.getValor() <= 0) {
			throw new ValidationException("Valor não pode ser negativo");
		}
		
		return true;
	}
	
	public Item insert(Item obj) throws ValidationException {
		if (!validate(obj)) {
			return null;
		}
		
		return this.getDAO().insert(obj);
	}
	
	public Item update(Item obj) throws ValidationException {
		if (!validate(obj)) {
			return null;
		}
		
		return this.getDAO().update(obj);
	}
}
