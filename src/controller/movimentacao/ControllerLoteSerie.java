
package controller.movimentacao;
/*
import dao.ClienteDao;
import dao.CarroDao;
import dao.LocacaoDao;
*/
import model.Model;


import util.Validador;
import view.cadastro.TelaProdutos;

import javax.swing.JOptionPane;

public class ControllerLoteSerie {

    private final TelaProdutos tela;
 
    int id;
    
    public ControllerLoteSerie(TelaProdutos t) {

        tela = t;

    }



    public void novo() {

        tela.limpar();

    }

    
    private void validarDados() {
    	if (
	      Validador.naoNumero(tela.getCodigo().getText())
	    ) throw new IllegalArgumentException("Informe o ID corretamente.");
    	
    	try {
    		int id = Integer.parseInt(tela.getCodigo().getText());
    	} catch (Exception e) {
    	    id = 0;
    	}
    	
    	
    	
	    if (
	    		Validador.vazio(tela.getNome().getText()) || tela.getNome().getText().length() > 100
	  	) throw new IllegalArgumentException("Informe o nome corretamente.(Max.: 100 carácteres)");
	    
	    if (
	    		tela.getCategoria().getSelectedItem() == null
	    ) throw new IllegalArgumentException("Selecione um tipo.");
	    
	    //opcional
	    if (
	    		tela.getDescricao().getText().length() > 255 && 
	    		tela.getDescricao().getText() != null
		) throw new IllegalArgumentException("A descrição deve estar correta. (Max.: 255 carácteres)");
	    
	    //opconal
	    if (
	    		tela.getUnidadeMedida().getText().length() > 20 && tela.getUnidadeMedida().getText() != null
		) throw new IllegalArgumentException("O unidade de medida deve estar correta. (Max.: 20 carácteres)");
	    
	    if (
	    		Validador.naoNumero(tela.getQuantidade().getText()) || Validador.vazio(tela.getQuantidade().getText())
	  	) throw new IllegalArgumentException("Informe a quantidade corretamente.");
	    
	    if (
	    		Validador.naoDecimal(tela.getPeso().getText()) && !Validador.vazio(tela.getPeso().getText())
		  	    ) throw new IllegalArgumentException("Informe o Peso corretamente.");
		    
	    if (
		  	    Validador.naoDecimal(tela.getPreco().getText()) || Validador.vazio(tela.getPreco().getText())
		) throw new IllegalArgumentException("Informe o Preço corretamente.");
	    
	    if (
	  	      Validador.naoNumero(tela.getEstoqueMinimo().getText()) || Validador.vazio(tela.getEstoqueMinimo().getText())
	  	) throw new IllegalArgumentException("Informe o estoque mínimo corretamente.");
    
        if (
	  	      Validador.naoNumero(tela.getEstoqueMaximo().getText()) || Validador.vazio(tela.getEstoqueMaximo().getText())
	  	) throw new IllegalArgumentException("Informe o estoque máximo corretamente."); 
    
        if (
    		tela.getStatus().getSelectedItem() == null
        ) throw new IllegalArgumentException("Selecione um tipo.");
    
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