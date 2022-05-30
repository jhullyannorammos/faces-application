package br.com.application.controller.filter;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.application.model.Cliente;
import br.com.application.repository.ClienteRepository;
import br.com.application.repository.filter.ClienteFilter;
import br.com.application.service.NegocioException;
import br.com.application.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaClientesBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject private ClienteRepository clientes;
	
	private ClienteFilter filtro;
	private List<Cliente> clientesFiltrados;
	
	private Cliente clienteSelecionado;
	
	public PesquisaClientesBean() {
		filtro = new ClienteFilter();
	}
	
	public void pesquisar() {
		clientesFiltrados = clientes.filtrados(filtro);
	}
	
	public void excluir() {
		try {
			clientes.remover(clienteSelecionado);
			clientesFiltrados.remove(clienteSelecionado);
			
			FacesUtil.addInfoMessage("Cliente " + clienteSelecionado.getNome() + 
					" excluído com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public List<Cliente> getClientesFiltrados() {
		return clientesFiltrados;
	}

	public ClienteFilter getFiltro() {
		return filtro;
	}

	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}

	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}
	
}
