package br.com.application.controller.view;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.application.enumerator.Fabricante;
import br.com.application.model.Modelo;
import br.com.application.repository.ModeloRepository;
import br.com.application.util.jsf.FacesUtil;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class TesteBean implements Serializable {
	
	
	private static String ResponseModel = "valor testado com sucesso : ";
	private static String ResponseError = "valor testado com prtoblemas : ";
	
	public void start() {
		TesteBean.criarModelos();
	}
	
	public static void criarModelos() {
		
	    ModeloRepository modeloRepository = new ModeloRepository();
		Modelo modelo = new Modelo();
		
		try {
			modelo.setFabricante(Fabricante.DELL);
			modelo.setModelo("OPTIPLEX 790");
			modelo = modeloRepository.update(modelo);
		}catch(Exception exception) {
			exception.printStackTrace();
			FacesUtil.addErrorMessage(ResponseError + exception.getMessage() + 
					                  "Localized Message" + exception.getLocalizedMessage()
					                  + "Cause: " + exception.getCause());
		} finally {
			FacesUtil.addInfoMessage(ResponseModel + modelo.getModelo());
		}
	}

}
