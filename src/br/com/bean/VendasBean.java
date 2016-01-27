package br.com.bean;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.springframework.stereotype.Component;

import br.com.entities.Cliente;
import br.com.entities.Produto;
import br.com.entities.Venda;
import br.com.service.ClientesService;
import br.com.service.ProdutosService;
import br.com.service.VendasService;


@Component
@ManagedBean
@SessionScoped
public class VendasBean {
	
	private Produto produtos = new Produto();
	private Cliente clientes = new Cliente();
	private Venda vendas = new Venda();
	public List<Venda> list = new ArrayList<Venda>();
	public List<Produto> listProdutos = new ArrayList<Produto>();
	public List<Cliente> listClientes = new ArrayList<Cliente>();
	public String codigoProduto, codigoCliente;

	public void init() throws UnknownHostException {
		
		if (!FacesContext.getCurrentInstance().isPostback()) {
			limparTela();
			listarProdutos();
			listarClientes();
			listarVendas();
			
		}
		
	}

	public void limparTela() {
		
		this.codigoCliente = null;
		this.codigoProduto = null;
		this.list = null;
	}
	
	
	public void novoProduto() throws UnknownHostException {
		RequestContext context = RequestContext.getCurrentInstance();
		ProdutosService produtosService = new ProdutosService();
		if(produtosService.insertProduto(produtos)) {
			FacesContext.getCurrentInstance().addMessage("messages",new FacesMessage(FacesMessage.SEVERITY_INFO,
														 "Produto cadastrado com sucesso!",""));
		} else {
			FacesContext.getCurrentInstance().addMessage("messages",new FacesMessage(FacesMessage.SEVERITY_ERROR, 
														 "Erro ao cadastrar produto!", ""));
			context.addCallbackParam("success", false);
		}
	}

	public void novoCliente() throws UnknownHostException {
		RequestContext context = RequestContext.getCurrentInstance();
		ClientesService clientesService = new ClientesService();
		
		if(clientesService.insertCliente(clientes)) {
			FacesContext.getCurrentInstance().addMessage("messages",new FacesMessage(FacesMessage.SEVERITY_INFO,
														 "Cliente cadastrado com sucesso!",""));
		} else {
			FacesContext.getCurrentInstance().addMessage("messages",new FacesMessage(FacesMessage.SEVERITY_ERROR, 
														 "Erro ao cadastrar cliente!", ""));
			context.addCallbackParam("success", false);
		}
	}
		
	public void novaVenda() throws UnknownHostException {
		RequestContext context = RequestContext.getCurrentInstance();
		VendasService vendasService = new VendasService();
		
		if(vendasService.insertVenda(vendas)) {
			FacesContext.getCurrentInstance().addMessage("messages",new FacesMessage(FacesMessage.SEVERITY_INFO,
														 "Venda cadastrada com sucesso!",""));
		} else {
			FacesContext.getCurrentInstance().addMessage("messages",new FacesMessage(FacesMessage.SEVERITY_ERROR, 
														 "Erro ao cadastrar venda!", ""));
			context.addCallbackParam("success", false);
		}
		listarVendas();
	}

	public String listarVendas() throws UnknownHostException {
		VendasService vendasService = new VendasService();
		list = vendasService.buscarVendas(vendas);
		System.out.println("Tamanho lista vendas: " + list.size());
		return "success";
	}

	public String listarProdutos() throws UnknownHostException {
		ProdutosService produtosService = new ProdutosService();
		listProdutos = produtosService.buscarProdutos(produtos); 
		System.out.println("Tamanho lista produtos: " + listProdutos.size());
		return "success";
		}
	
	
	public String listarClientes() throws UnknownHostException {
		ClientesService clientesService = new ClientesService();
		listClientes = clientesService.buscarClientes(clientes);
		System.out.println("Tamanho lista clientes: " + listClientes.size());
		return "success";
	}

	public Produto getProdutos() {
		return produtos;
	}
	public void setProdutos(Produto produtos) {
		this.produtos = produtos;
	}
	public Cliente getClientes() {
		return clientes;
	}
	public void setClientes(Cliente clientes) {
		this.clientes = clientes;
	}
	public Venda getVendas() {
		return vendas;
	}
	public void setVendas(Venda vendas) {
		this.vendas = vendas;
	}
	public List<Venda> getList() {
		return list;
	}
	public void setList(List<Venda> list) {
		this.list = list;
	}
	public List<Produto> getListProdutos() {
		return listProdutos;
	}
	public void setListProdutos(List<Produto> listProdutos) {
		this.listProdutos = listProdutos;
	}

	
	public String getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(String codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	public String getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public List<Cliente> getListClientes() {
		return listClientes;
	}

	public void setListClientes(List<Cliente> listClientes) {
		this.listClientes = listClientes;
	}

	
}