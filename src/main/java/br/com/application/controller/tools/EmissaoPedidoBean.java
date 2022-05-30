package br.com.application.controller.tools;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.application.controller.PedidoEdicao;
import br.com.application.model.Pedido;
import br.com.application.service.EmissaoPedidoService;
import br.com.application.service.NegocioException;
import br.com.application.util.jsf.FacesUtil;

@SuppressWarnings("serial")
@Named
@RequestScoped
public class EmissaoPedidoBean implements Serializable {

	@Inject private Event<PedidoAlteradoEvent> pedidoAlteradoEvent;
	@Inject private EmissaoPedidoService emissaoPedidoService;
	
	@PedidoEdicao
	@Inject private Pedido pedido;

	public void emitirPedido() {
		this.pedido.removerItemVazio();
		
		try {
			this.pedido = this.emissaoPedidoService.emitir(this.pedido);
			this.pedidoAlteradoEvent.fire(new PedidoAlteradoEvent(this.pedido));
			FacesUtil.addInfoMessage("Pedido emitido com sucesso!");
		} catch (NegocioException ne) {
			FacesUtil.addErrorMessage(ne.getMessage());
		} finally {
			this.pedido.adicionarItemVazio();
		}
	}
	
}
