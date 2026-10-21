package controller.suprimentos;

import view.suprimentos.TelaRecebimento;

import javax.swing.JOptionPane;

import util.Validador;
import model.Model;
public class Controller_recebimento {

	private final TelaRecebimento tela;
	
	public Controller_recebimento(TelaRecebimento tela) {
        this.tela = tela;
    }
	
	public void novo() {

        tela.limpar();

    }
	private void validarPesquisa() {
		try {
	    if (
	      Validador.vazio(tela.getTxtIdPedidoFiltro().getText())
	    ) throw new IllegalArgumentException("Informe um pedido para a pesquisa.");
	    if (
	  	      Validador.vazio(tela.getTxtFornecedorFiltro().getText())
	  	    ) throw new IllegalArgumentException("Informe um fornecedor para a pesquisa.");
	    if (
	  	      Validador.vazio(tela.getTxtProdutoFiltro().getText())
	  	    ) throw new IllegalArgumentException("Informe um produto para a pesquisa.");
	    
	  }catch (Exception e) {
	      erro(e);
	   }
	}
	private void validarSolicitacao() {

	    if (
	      Validador.naoNumero(tela.getTxtIdPedido().getText())
	    ) throw new IllegalArgumentException("Informe o id da solicitação.");
	    
	    if (
		  	      Validador.naoNumero(tela.getTxtFornecedor().getText())
		  	    ) throw new IllegalArgumentException("Informe o id do fornecedor.");
	    
	    if (
	  	      Validador.vazio(tela.getTxtProduto().getText())
	  	    ) throw new IllegalArgumentException("Informe o produto.");
	    
	    if (
	  	      Validador.naoNumero(tela.getTxtQuantidadeSolicitada().getText())
	    	    ) throw new IllegalArgumentException("Informe uma quantidade solicitada.");
	    
	    if (
		  	      Validador.naoNumero(tela.getTxtQuantidadeRecebida().getText())
		    	    ) throw new IllegalArgumentException("Informe uma quantidade recebida.");
	    
	    if (
	    		tela.getCmbLocalDestino().getSelectedItem() == "Selecione um fornecedor"
	    ) throw new IllegalArgumentException("Selecione um local");
	    
	    if (
		  	      Validador.vazio(tela.getTxtDocumentoAquisicao().getText())
		  	    ) throw new IllegalArgumentException("Informe o documento de aquisição.");
	    
	    if (
		  	      Validador.naoNumero(tela.getTxtResponsavel().getText())
		  	    ) throw new IllegalArgumentException("Informe o id do responsavel.");
	    
	    if (
		  	      Validador.vazio(tela.getTxtLote().getText())
		  	    ) throw new IllegalArgumentException("Informe o lote.");
	    
	    if (
		  	      Validador.vazio(tela.getTxtNumeroSerie().getText())
		  	    ) throw new IllegalArgumentException("Informe o número de série.");

	    if (
		  	      Validador.vazio(tela.getTxtDataFabricacao().getText())
		  	    ) throw new IllegalArgumentException("Informe a data de fabricação.");
	    
	    if (
		  	      Validador.vazio(tela.getTxtDataValidade().getText())
		  	    ) throw new IllegalArgumentException("Informe a data de validade.");
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
