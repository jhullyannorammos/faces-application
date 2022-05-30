package br.com.application.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import br.com.application.model.Modelo;
import br.com.application.repository.ModeloRepository;
import br.com.application.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Modelo.class)
public class ModeloConverter implements Converter {
	
	@Inject private ModeloRepository modeloRepository;
	
	public ModeloConverter() {
		modeloRepository = CDIServiceLocator.getBean(ModeloRepository.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Modelo modelo = null;
		if(StringUtils.isNotEmpty(value)) {
			Long codigo = new Long(value);
			modelo = modeloRepository.findBy(codigo);
		}
		return modelo;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null) {
			Modelo modelo = (Modelo) value;
			return modelo != null && modelo.getId() != null ? modelo.getId().toString() : null;
		}
		return "";
	}

}
