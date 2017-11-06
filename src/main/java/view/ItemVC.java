package view;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import business.ItemBC;
import dao.ItemDAO;
import domain.Item;
import exception.NotFoundException;
import exception.ValidationException;

@Named("itemVC")
@ViewScoped
public class ItemVC extends AViewController<Item, ItemDAO, ItemBC>  implements Serializable {
	
	private static final long serialVersionUID = -1180730327778842210L;
	
	private Integer id;
	
	public ItemVC() {
		super(Item.class);
	}
	
	public void init() {
		if (id != null && this.getBean().getId() == null) {
			this.setBean(this.getController().findOne(id));
		}
	}
	
	public List<Item> getAll() {
		return this.getController().getDAO().findAll();
	}
	
	public void submit() {
		try {
			if (this.getBean().getId() == null) {
				this.getController().insert(this.getBean());
				FacesContext.getCurrentInstance().addMessage(
						null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Inserido com sucesso", null)
					);
			} else {
				this.getController().update(this.getBean());
				FacesContext.getCurrentInstance().addMessage(
					null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo com sucesso", null)
				);
			}
		} catch(ValidationException e) {
			FacesContext.getCurrentInstance().addMessage(
				null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null)
			);
		}
	}
	
	public String delete(Integer id) {
		try {
			this.getController().delete(id);
			FacesContext.getCurrentInstance().addMessage(
				null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Removido", null)
			);
		} catch (NotFoundException e) {
			FacesContext.getCurrentInstance().addMessage(
				null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro durante remoção", null)
			);
		}
		return null;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
