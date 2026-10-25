
package controller.movimentacao;
/*
import dao.ClienteDao;
import dao.CarroDao;
import dao.LocacaoDao;
*/
import model.Model;


import util.Validador;
import view.cadastro.TelaVariacoes;

import javax.swing.JOptionPane;

public class ControllerMultilocalizacao {

    private final TelaVariacoes tela;

    
    int id;
    
    public ControllerMultilocalizacao(TelaVariacoes t) {

        tela = t;

    }


    public void novo() {

        tela.limpar();

    }

    private void validarDados() {
    	if (
	      Validador.naoNumero(tela.getProduto().getText())
	    ) throw new IllegalArgumentException("Informe o ID do produto corretamente.");
    	
    	try {
    		int id = Integer.parseInt(tela.getProduto().getText());
    	} catch (Exception e) {
    	    id = 0;
    	}
    	
    	if (
	    		tela.getSku().getText().length() > 50 || Validador.vazio(tela.getSku().getText())
		) throw new IllegalArgumentException("O SKU não deve estar vazio. (Max.: 50 carácteres)");
	    
    	if (
	    		tela.getNomeVariacao().getText().length() > 100 || Validador.vazio(tela.getNomeVariacao().getText())
		) throw new IllegalArgumentException("O nome da váriação deve ser invormado. (Max.: 100 carácteres)");
	    
	    //opcional
	    if (
	    		tela.getTamanho().getText().length() > 30 && 
	    		!Validador.vazio(tela.getTamanho().getText())
		) throw new IllegalArgumentException("O tamanho deve estar correta. (Max.: 30 carácteres)");
	    
	    //opconal
	    if (
	    		tela.getCor().getText().length() > 30 && 
	    		!Validador.vazio(tela.getCor().getText())
		) throw new IllegalArgumentException("A cor deve estar correta. (Max.: 30 carácteres)");
	    
	  //opconal
	    if (
	    		tela.getModelo().getText().length() > 100 && 
	    		!Validador.vazio(tela.getModelo().getText())
		) throw new IllegalArgumentException("O modelo deve ser invomado corretamente. (Max.: 100 carácteres)");
	    
	    if (
		  	      Validador.naoNumero(tela.getQuantidade().getText()) || Validador.vazio(tela.getQuantidade().getText())
		) throw new IllegalArgumentException("Informe a quantidade corretamente.");
	    
	  //opconal
	    if (
	    		Validador.naoDecimal(tela.getPreco().getText()) && !Validador.vazio(tela.getPreco().getText())
		  	    ) throw new IllegalArgumentException("Informe o preço corretamente.");
    
	}
    
    
    private void erro(Exception e) {

        e.printStackTrace();

        JOptionPane.showMessageDialog(
            tela,
            "Não foi possível concluir.\n"
                + e.getMessage(),
            "Locação",
            JOptionPane.ERROR_MESSAGE
        );

    }

}