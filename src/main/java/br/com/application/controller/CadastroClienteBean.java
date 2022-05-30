package br.com.application.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.application.model.Cliente;
import br.com.application.model.Endereco;
import br.com.application.model.TipoPessoa;
import br.com.application.service.CadastroClienteService;
import br.com.application.service.NegocioException;
import br.com.application.util.jsf.FacesUtil;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class CadastroClienteBean implements Serializable {
	
	@Inject private CadastroClienteService cadastroClienteService;
	private boolean editandoEndereco;
	private Endereco endereco;
	private Cliente cliente;

	public void inicializar(){
		if (cliente == null) {
			instance();
		}
	}
	
	public void instance() {
		this.cliente = new Cliente();
		this.cliente.setTipo(TipoPessoa.FISICA);
	}
	
	public void salvar() {
		try {
			cadastroClienteService.salvar(cliente);
			FacesUtil.addInfoMessage("Cliente salvo com sucesso!");
		} catch(NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		} finally {
			instance();
		}
	}
	
	public void novoEndereco() {
		this.endereco = new Endereco();
		this.endereco.setCliente(this.cliente);
		this.editandoEndereco = false;
	}
	
	public void editarEndereco(Endereco endereco) {
		this.endereco = endereco;
		this.editandoEndereco = true;
	}
	
	public void excluirEndereco(Endereco endereco) {
		this.cliente.getEnderecos().remove(endereco);
	}
	
	public void confirmarEndereco() {
		if (!this.cliente.getEnderecos().contains(this.endereco)) {
			this.cliente.getEnderecos().add(this.endereco);
		}
	}
	
	public Endereco getEndereco() {
		return endereco;
	}
	
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public boolean isEditando() {
		return cliente != null && cliente.getId() == null;
	}
	
	public boolean isEditandoEndereco() {
		return editandoEndereco;
	}
	
	public TipoPessoa[] getTipos(){
		return TipoPessoa.values();
	}
}
