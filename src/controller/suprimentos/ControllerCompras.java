package controller.suprimentos;

import view.suprimentos.TelaCompras;

import javax.swing.JOptionPane;

import util.Validador;
import model.Model;
public class ControllerCompras {

	private final TelaCompras tela;
	
	public ControllerCompras(TelaCompras tela) {
        this.tela = tela;
    }
	
	public void novo() {

        tela.limpar();

    }
	private void validarPesquisa() {
		try {
	    if (
	      Validador.vazio(tela.getTxtIdSolicitacaoFiltro().getText())
	    ) throw new IllegalArgumentException("Informe uma solicitação para a pesquisa.");
	    if (
	  	      Validador.vazio(tela.getTxtIdPedidoFiltro().getText())
	  	    ) throw new IllegalArgumentException("Informe um pedido para a pesquisa.");
	    if (
	  	      Validador.vazio(tela.getTxtProdutoFiltro().getText())
	  	    ) throw new IllegalArgumentException("Informe um produto para a pesquisa.");
	    if (
	  	      Validador.vazio(tela.getTxtFornecedorFiltro().getText())
	  	    ) throw new IllegalArgumentException("Informe um fornecedor para a pesquisa.");
	    
	    
	    
	    
	    
	    
	  }catch (Exception e) {
	      erro(e);
	   }
	}
	private void validarSolicitacao() {

	    if (
	      Validador.naoNumero(tela.getTxtIdSolicitacao().getText())
	    ) throw new IllegalArgumentException("Informe o id da solicitação.");
	    
	    if (
	  	      Validador.vazio(tela.getTxtProduto().getText())
	  	    ) throw new IllegalArgumentException("Informe o produto.");
	    
	    if (
	  	      Validador.naoNumero(tela.getTxtQuantidadeAprovada().getText())
	    	    ) throw new IllegalArgumentException("Informe uma quantidade aprovada.");
	    
	    if (
	    		tela.getCmbFornecedor().getSelectedItem() == "Selecione um fornecedor"
	    ) throw new IllegalArgumentException("Selecione um fornecedor.");
	    
	    if (
	  	      Validador.naoNumero(tela.getTxtIdPedido().getText())
	  	    ) throw new IllegalArgumentException("Informe o id do pedido.");
	    
	    if (
	  	      Validador.vazio(tela.getTxtDataPedido().getText())
	  	    ) throw new IllegalArgumentException("Informe a data do pedido.");
	    
	    if (
		  	      Validador.vazio(tela.getTxtSituacaoPedido().getText())
		  	    ) throw new IllegalArgumentException("Informe a situação do pedido.");
	}
	
	
	
	private void erro(Exception e) {
	    e.printStackTrace();
	    JOptionPane.showMessageDialog(
	      tela,
	      "Erro: " + e.getMessage(),
	      "Biblioteca",
	      JOptionPane.ERROR_MESSAGE
	  );
	}
}
