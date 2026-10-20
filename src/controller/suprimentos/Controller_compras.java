package controller.suprimentos;

import view.suprimentos.TelaMRP;

import javax.swing.JOptionPane;

import util.Validador;
import model.Model;
public class Controller_compras {

	private final TelaMRP tela;
	
	public Controller_compras(TelaMRP tela) {
        this.tela = tela;
    }
	
	public void novo() {

        tela.limpar();

    }
	private void validarPesquisa() {
		try {
	    if (
	      Validador.vazio(tela.getTxtProduto().getText())
	    ) throw new IllegalArgumentException("Informe um produto para a pesquisa.");
	  }catch (Exception e) {
	      erro(e);
	   }
	}
	private void validarSugestao() {

	    if (
	      Validador.naoNumero(tela.getTxtQuantidadeSugerida().getText())
	    ) throw new IllegalArgumentException("Informe uma quantidade sugerida.");
	    if (
	  	      Validador.naoNumero(tela.getTxtQuantidadeReservada().getText())
	    	    ) throw new IllegalArgumentException("Informe uma quantidade sugerida.");
	    
	    if (
		  	      Validador.naoNumero(tela.getTxtQuantidadeSolicitada().getText())
		    	    ) throw new IllegalArgumentException("Informe uma quantidade sugerida.");
	    
	    
	    if (
	  	      Validador.vazio(tela.getTxtDataPrevista().getText())
	  	    ) throw new IllegalArgumentException("Informe uma data para a previsão de reposição.");
	    
	    if (
	  	      Validador.vazio(tela.getTxtResponsavel().getText())
	  	    ) throw new IllegalArgumentException("Informe um responsavel.");
	    
	    if (
		  	      Validador.vazio(tela.getTxtJustificativa().getText())
		  	    ) throw new IllegalArgumentException("Faça uma justificativa.");
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
