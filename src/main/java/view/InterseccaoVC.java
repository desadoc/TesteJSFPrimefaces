package view;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named("interseccaoVC")
@RequestScoped
public class InterseccaoVC implements Serializable {

	private static final long serialVersionUID = -3457215172107936561L;

	private Integer intervaloAStart;
	private Integer intervaloAEnd;

	private Integer intervaloBStart;
	private Integer intervaloBEnd;

	public void consultar() {
		boolean intersecta = (intervaloAEnd > intervaloBStart) && (intervaloAStart < intervaloBEnd);

		if (intersecta) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Mensagem: Existe interseção entre as faixas 1 e 2.", null));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Mensagem: Não há interseção entre as faixas 1 e 2.", null));
		}
	}

	public Integer getIntervaloAStart() {
		return intervaloAStart;
	}

	public void setIntervaloAStart(Integer intervaloAStart) {
		this.intervaloAStart = intervaloAStart;
	}

	public Integer getIntervaloAEnd() {
		return intervaloAEnd;
	}

	public void setIntervaloAEnd(Integer intervaloAEnd) {
		this.intervaloAEnd = intervaloAEnd;
	}

	public Integer getIntervaloBStart() {
		return intervaloBStart;
	}

	public void setIntervaloBStart(Integer intervaloBStart) {
		this.intervaloBStart = intervaloBStart;
	}

	public Integer getIntervaloBEnd() {
		return intervaloBEnd;
	}

	public void setIntervaloBEnd(Integer intervaloBEnd) {
		this.intervaloBEnd = intervaloBEnd;
	}

}
