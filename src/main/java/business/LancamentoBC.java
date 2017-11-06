package business;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import dao.LancamentoDAO;
import domain.Item;
import domain.Lancamento;
import exception.ValidationException;

@Dependent
public class LancamentoBC extends ABusinessController<Lancamento, LancamentoDAO> implements Serializable {

	private static final long serialVersionUID = 7904720629962467216L;
	
	private boolean validate(Lancamento obj) throws ValidationException {
		if (obj.getValorTotal() == null || obj.getValorTotal() <= 0) {
			throw new ValidationException("Valor não pode ser zero ou negativo");
		}
		
		if (obj.getDataInicial() == null) {
			throw new ValidationException("Campo data inicial é obrigatório");
		}
		
		if (obj.getDataFinal() == null) {
			throw new ValidationException("Campo data final é obrigatório");
		}
		
		if (!obj.getDataFinal().after(obj.getDataInicial())) {
			throw new ValidationException("Data inicial deve ser anteior a final");
		}
		
		return true;
	}
	
	public Lancamento insert(Lancamento obj) throws ValidationException {
		if (!validate(obj)) {
			return null;
		}
		
		return this.getDAO().insert(obj);
	}
	
	public Lancamento update(Lancamento obj) throws ValidationException {
		if (!validate(obj)) {
			return null;
		}
		
		return this.getDAO().update(obj);
	}
	
	public void updateValorTotal(Lancamento obj) {
		Double soma = 0.0;
		for (Item i : obj.getItens()) {
			soma += i.getValor();
		}
		obj.setValorTotal(soma);
	}
}
