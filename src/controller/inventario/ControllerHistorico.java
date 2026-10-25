package controller.inventario;

import view.automacao.TelaEstoqueMinMax;

import javax.swing.JOptionPane;

import util.Validador;
import model.Model;
public class ControllerHistorico {

	private final TelaEstoqueMinMax tela;
	
	public ControllerHistorico(TelaEstoqueMinMax tela) {
        this.tela = tela;
    }
	
	public void novo() {

        tela.limpar();

    }
	private void validarFiltro() {
		try {
	    if (
	      Validador.vazio(tela.getCampoProduto().getText())
	    ) throw new IllegalArgumentException("Informe uma solicitação para a pesquisa.");
	    
	    if (
	    		Validador.naoNumero(tela.getCampoEstoqueMin().getText()) || Validador.vazio(tela.getCampoEstoqueMin().getText())
	  	) throw new IllegalArgumentException("Informe a quantidade corretamente.");
	    
	    if (
	    		Validador.naoNumero(tela.getCampoEstoqueMax().getText()) || Validador.vazio(tela.getCampoEstoqueMax().getText())
	  	) throw new IllegalArgumentException("Informe a quantidade corretamente.");

	    
	  }catch (Exception e) {
	      erro(e);
	   }
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
