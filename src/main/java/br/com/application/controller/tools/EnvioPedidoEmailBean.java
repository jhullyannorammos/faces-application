package br.com.application.controller.tools;

import java.io.Serializable;
import java.util.Locale;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.velocity.tools.generic.NumberTool;

import com.outjected.email.api.MailMessage;
import com.outjected.email.impl.templating.velocity.VelocityTemplate;

import br.com.application.controller.PedidoEdicao;
import br.com.application.model.Pedido;
import br.com.application.util.jsf.FacesUtil;
import br.com.application.util.mail.Mailer;

@SuppressWarnings("serial")
@Named
@RequestScoped
public class EnvioPedidoEmailBean implements Serializable {

	@Inject @PedidoEdicao private Pedido pedido;
	@Inject private Mailer mailer;
	
	public void enviarPedido() {
		
        //File file = getClass().getResourceAsStream("/br/com/application/emails/pedido.template");
		//String template = "/br/com/application/emails/pedido.template";
		
		MailMessage message = mailer.novaMensagem();

        try {
        	message.to(this.pedido.getCliente().getEmail())
		    //.bodyHtml(new VelocityTemplate(template))
		    .bodyHtml(new VelocityTemplate(getClass().getResourceAsStream("/br/com/application/emails/pedido.template").toString()))
		    .subject("Pedido " + this.pedido.getId())
			.put("pedido", this.pedido)
			.put("numberTool", new NumberTool())
			.put("locale", new Locale("pt", "BR"))
			.send();
        	
        	FacesUtil.addInfoMessage("Pedido enviado por e-mail com sucesso!");
        } catch(Exception exception) {
        	exception.printStackTrace();
        	FacesUtil.addErrorMessage(exception.getMessage() + " " + 
        			exception.getCause() + " " +
        			exception.getLocalizedMessage());
		} finally {
			
		}
		
		
	}
	
	
}
