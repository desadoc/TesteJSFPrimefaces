package dao;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import domain.Item;

@Dependent
public class ItemDAO extends AModelDAO<Item> implements Serializable {
	
	private static final long serialVersionUID = -4431959007086514854L;

	public ItemDAO() {
		super(Item.class);
	}
}
