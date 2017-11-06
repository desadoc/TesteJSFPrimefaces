package view;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import business.ItemBC;
import business.LancamentoBC;
import dao.ItemDAO;
import dao.LancamentoDAO;
import domain.Item;
import domain.Lancamento;
import exception.NotFoundException;
import exception.ValidationException;

@Named("lancamentoVC")
@ViewScoped
public class LancamentoVC extends AViewController<Lancamento, LancamentoDAO, LancamentoBC> implements Serializable {

	private static final long serialVersionUID = -4743168994335856798L;
		
	@Inject
	private ItemBC itemBC;
	
	private Integer id;
	
	private DualListModel<Item> itemDualList;
	
	public LancamentoVC() {
		super(Lancamento.class);
	}

	public void init() {
		if (id != null && this.getBean().getId() == null) {
			this.setBean(this.getController().findOne(id));
		}
		
		initDualList();
	}
	
	private void initDualList() {
		List<Item> disponivel = itemBC.findAll();
		List<Item> incluidos = this.getBean().getItens();
		
		if (incluidos == null) {
			incluidos = new LinkedList<Item>();
			this.getBean().setItens(incluidos);
		}
		
		for (Item i : incluidos) {
			disponivel.remove(i);
		}
		
		itemDualList = new DualListModel<Item>(disponivel, incluidos);
	}
	
	@SuppressWarnings("unchecked")
	public void onDualListTransfer(TransferEvent event) {
		for (Object o : event.getItems()) {
			Item i = (Item) o;
			if (event.isAdd()) {
				this.getBean().getItens().add(i);
			} else {
				this.getBean().getItens().remove(i);
			}
		}
		
		((LancamentoBC) this.getController()).updateValorTotal(this.getBean());
    }
	
	public String submit() {
		try {
			if (this.getBean().getId() == null) {
				List<Item> itens = this.getBean().getItens();
				this.getBean().setItens(null);
				this.getController().insert(this.getBean());
				
				this.getBean().setItens(itens);
				this.getController().update(this.getBean());
				
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
		
		return null;
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

	public DualListModel<Item> getDualList() {
		return itemDualList;
	}
	
	public void setDualList(DualListModel<Item> itemDualList) {
        this.itemDualList = itemDualList;
    }
	
	public String formatItem(Item i) {
		return String.format("%s - R$ %.2f", i.getDescricao(), i.getValor());
	}
	
	public List<Lancamento> getAll() {
		return this.getController().findAll();
	}
	
	public Double consultaItem10() {
		return ((LancamentoDAO) this.getController().getDAO()).consulta_item10((ItemDAO) itemBC.getDAO());
	}
	
	public List<Lancamento> consultaItem11() {
		return ((LancamentoDAO) this.getController().getDAO()).consulta_item11();
	}
	
	public void consultaItem12() {
		int updateCount = ((LancamentoDAO) this.getController().getDAO()).consulta_item12();
		FacesContext.getCurrentInstance().addMessage(
			null, new FacesMessage(FacesMessage.SEVERITY_INFO, String.format("Número de linhas atualizadas: %d", updateCount), null)
		);
	}
}
