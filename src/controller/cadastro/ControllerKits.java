
package controller.cadastro;
/*
import dao.ClienteDao;
import dao.CarroDao;
import dao.LocacaoDao;
*/
import model.Model;


import util.Validador;
import view.cadastro.TelaKits;

import javax.swing.JOptionPane;

public class ControllerKits {

    private final TelaKits tela;

    public ControllerKits(TelaKits t) {

        tela = t;

    }

  
  
    private void validarDados() {

	    if (
	    		Validador.vazio(tela.getNome().getText()) || tela.getNome().getText().length() > 100
	  	) throw new IllegalArgumentException("Informe o nome do kit corretamente.(Max.: 100 carácteres)");
	    
	    if (
	    		Validador.naoNumero(tela.getQuantidadeKits().getText()) || Validador.vazio(tela.getQuantidadeKits().getText())
	  	) throw new IllegalArgumentException("Informe a quantidade de kits corretamente.");
	    
	    if (
	    		tela.getTipo().getSelectedItem() == null
	    ) throw new IllegalArgumentException("Selecione um tipo.");
	    
	    if (
	    		Validador.vazio(tela.getProduto().getText()) || tela.getProduto().getText().length() > 50
	  	) throw new IllegalArgumentException("Informe o SKU corretamente.(Max.: 50 carácteres)");
	    
	    if (
	    		Validador.naoNumero(tela.getQuantidade().getText()) || Validador.vazio(tela.getQuantidade().getText())
	  	) throw new IllegalArgumentException("Informe a quantidade de produtos no kit corretamente.");

    
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