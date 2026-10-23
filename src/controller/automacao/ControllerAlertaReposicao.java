package controller.automacao;

import view.automacao.TelaAlertaReposicao;

import javax.swing.JOptionPane;

import util.Validador;
import model.Model;
public class ControllerAlertaReposicao {

	private final TelaAlertaReposicao tela;
	
	public ControllerAlertaReposicao(TelaAlertaReposicao tela) {
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
