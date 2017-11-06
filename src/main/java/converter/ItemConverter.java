package converter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import domain.Item;

@FacesConverter("itemConverter")
public class ItemConverter implements Converter {
	
	@Override
	public Object getAsObject(FacesContext argfc0, UIComponent uic, String s) {
		Item i = new Item();
		
		Pattern p = Pattern.compile("^(.*?)\\$\\$(.*?)\\$\\$(.*)$");
		Matcher m = p.matcher(s);
		m.find();

		i.setId(Integer.parseInt(m.group(1)));
		i.setDescricao(m.group(2));
		i.setValor(Double.valueOf(m.group(3)));
		
		return i;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object o) {
		Item i = (Item) o;
		return String.format("%d$$%s$$%f", i.getId(), i.getDescricao(), i.getValor());
	}
    
}
