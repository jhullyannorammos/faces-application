package br.com.application.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.primefaces.event.SelectEvent;

import br.com.application.controller.tools.PedidoAlteradoEvent;
import br.com.application.enumerator.FormaPagamento;
import br.com.application.model.Cliente;
import br.com.application.model.EnderecoEntrega;
import br.com.application.model.ItemPedido;
import br.com.application.model.Pedido;
import br.com.application.model.Produto;
import br.com.application.model.Usuario;
import br.com.application.repository.ClienteRepository;
import br.com.application.repository.ProdutoRepository;
import br.com.application.repository.UsuarioRepository;
import br.com.application.service.CadastroPedidoService;
import br.com.application.service.NegocioException;
import br.com.application.util.jsf.FacesUtil;
import br.com.application.validation.SKU;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class CadastroPedidoBean implements Serializable {

	@Inject private CadastroPedidoService cadastroPedidoService;
	@Inject private UsuarioRepository usuarios;
	@Inject private ClienteRepository clientes;
	@Inject private ProdutoRepository produtos;
	private String sku;
	
	@Produces @PedidoEdicao
	private Pedido pedido;
	private List<Usuario> vendedores;
	private Produto produtoLinhaEditavel;
	
	public CadastroPedidoBean() {
		limpar();
	}
	
	public void inicializar() {
		if (this.pedido == null) {
			limpar();
		}
		
		this.vendedores = this.usuarios.vendedores();
		this.pedido.adicionarItemVazio();
		this.recalcularPedido();
	}
	
	public void clienteSelecionado(SelectEvent event) {
		pedido.setCliente((Cliente) event.getObject());
	}
	
	private void limpar() {
		pedido = new Pedido();
		pedido.setEnderecoEntrega(new EnderecoEntrega());
	}
	
	public void pedidoAlterado(@Observes PedidoAlteradoEvent event) {
		this.pedido = event.getPedido();
	}
	
	public void salvar() {
		this.pedido.removerItemVazio();
		try {
			this.pedido = this.cadastroPedidoService.salvar(this.pedido);
			FacesUtil.addInfoMessage("Pedido salvo com sucesso!");
		} catch (NegocioException ne) {
			FacesUtil.addErrorMessage(ne.getMessage());
		} finally {
			this.pedido.adicionarItemVazio();
		}
	}
	
	public void recalcularPedido() {
		if (this.pedido != null) {
			this.pedido.recalcularValorTotal();
		}
	}
	
	public void carregarProdutoPorSku() {
		if (StringUtils.isNotEmpty(this.sku)) {
			this.produtoLinhaEditavel = this.produtos.porSku(this.sku);
			this.carregarProdutoLinhaEditavel();
		}
	}
	
	public void carregarProdutoLinhaEditavel() {
		ItemPedido item = this.pedido.getItens().get(0);
		
		if (this.produtoLinhaEditavel != null) {
			if (this.existeItemComProduto(this.produtoLinhaEditavel)) {
				FacesUtil.addErrorMessage("Já existe um item no pedido com o produto informado.");
			} else {
				item.setProduto(this.produtoLinhaEditavel);
				item.setValorUnitario(this.produtoLinhaEditavel.getValorUnitario());
				
				this.pedido.adicionarItemVazio();
				this.produtoLinhaEditavel = null;
				this.sku = null;
				
				this.pedido.recalcularValorTotal();
			}
		}
	}
	
	private boolean existeItemComProduto(Produto produto) {
		boolean existeItem = false;
		
		for (ItemPedido item : this.getPedido().getItens()) {
			if (produto.equals(item.getProduto())) {
				existeItem = true;
				break;
			}
		}
		
		return existeItem;
	}

	public List<Produto> completarProduto(String nome) {
		return this.produtos.porNome(nome);
	}
	
	public void atualizarQuantidade(ItemPedido item, int linha) {
		if (item.getQuantidade() < 1) {
			if (linha == 0) {
				item.setQuantidade(1);
			} else {
				this.getPedido().getItens().remove(linha);
			}
		}
		
		this.pedido.recalcularValorTotal();
	}
	
	public FormaPagamento[] getFormasPagamento() {
		return FormaPagamento.values();
	}
	
	public List<Cliente> completarCliente(String nome) {
		return this.clientes.porNome(nome);
	}

	public Pedido getPedido() {
		return pedido;
	}
	
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public List<Usuario> getVendedores() {
		return vendedores;
	}
	
	public boolean isEditando() {
		return this.pedido.getId() != null;
	}

	public Produto getProdutoLinhaEditavel() {
		return produtoLinhaEditavel;
	}

	public void setProdutoLinhaEditavel(Produto produtoLinhaEditavel) {
		this.produtoLinhaEditavel = produtoLinhaEditavel;
	}

	@SKU
	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}
	
	@NotBlank
	public String getNomeCliente() {
		return pedido.getCliente() == null ? null : pedido.getCliente().getNome();
	}
	
	public void setNomeCliente(String nome) {
	}

}