package br.com.application.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import br.com.application.model.Categoria;
import br.com.application.repository.CategoriaRepository;
import br.com.application.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Categoria.class)
public class CategoriaConverter implements Converter {

	@Inject private CategoriaRepository categorias;
	
	public CategoriaConverter() {
		categorias = CDIServiceLocator.getBean(CategoriaRepository.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Categoria retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = categorias.porId(id);
		}
		
		return retorno;
	}
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Categoria categoria = (Categoria) value;
			return categoria != null && categoria.getId() != null ? categoria.getId().toString() : null;
		}
		return "";
	}

}