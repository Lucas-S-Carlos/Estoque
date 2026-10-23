
package controller.cadastro;
/*
import dao.ClienteDao;
import dao.CarroDao;
import dao.LocacaoDao;
*/
import model.Model;


import util.Validador;
import view.cadastro.TelaProdutos;

import javax.swing.JOptionPane;

public class ControllerProdutos {

    private final TelaProdutos tela;
/*
    private final ... clienteDAO = new ...(); 
    private final ... carroDAO = new ...();
    private final ... dao = new ...();
*/
    
    int id;
    
    public ControllerProdutos(TelaProdutos t) {

        tela = t;

    }


    public void inicializar() {

        atualizarCadastros();

        listar();

    }

    public void atualizarCadastros() {

        try {

            tela.preencherLocatarios(
                clienteDAO.listar()
            );

            tela.preencherCarros(
                carroDAO.listar()
            );

        } catch (Exception e) {

            erro(e);

        }

    }

    public void novo() {

        tela.limpar();

    }

    public void salvar() {

        try {

            Model l = new Model();

            l.setCodigo_prod(
                tela.getCodigo()
            );


            ClienteModel cliente =
                (ClienteModel)
                tela.getTxtLocatario()
                    .getSelectedItem();

            if (cliente == null) {

                throw new IllegalArgumentException(
                    "Selecione um locatário."
                );

            }

            l.setlocatario(
                cliente.getId()
            );

            CarroModel carro =
                (CarroModel)
                tela.getTxtCarro()
                    .getSelectedItem();

            if (carro == null) {

                throw new IllegalArgumentException(
                    "Selecione um carro."
                );

            }

            l.setcarro(
                carro.getId()
            );

            if (
                Validador.vazio(
                    tela.getTxtDiarias().getText()
                )
            ) {

                throw new IllegalArgumentException(
                    "Informe a quantidade de diárias."
                );

            }

            l.setdiarias(
                Double.parseDouble(
                    tela.getTxtDiarias()
                        .getText()
                        .trim()
                        .replace(",", ".")
                )
            );

            l.setprecadastro(
                tela.getChkPreCadastro()
                    .isSelected()
            );

            l.setadicionais(
                tela.getTxtAdicionais()
                    .getText()
                    .trim()
            );

            l.setdata_retirada(
            	    converterData(tela.getTxtDataRetirada().getText())
            	);

            	l.setdata_devolucao(
            	    converterData(tela.getTxtDataDevolucao().getText())
            	);

            if (
                Validador.vazio(
                    tela.getTxtTempoLocacao()
                        .getText()
                )
            ) {

                throw new IllegalArgumentException(
                    "Informe o tempo de locação."
                );

            }

            l.settempo_locacao(
                Integer.parseInt(
                    tela.getTxtTempoLocacao()
                        .getText()
                        .trim()
                )
            );

            l.setlocal_retirada(
                tela.getTxtLocalRetirada()
                    .getText()
                    .trim()
            );

            l.setplano_seguro(
                tela.getTxtPlanoSeguro()
                    .getText()
                    .trim()
            );

            if (
                Validador.vazio(
                    tela.getTxtValorTotal()
                        .getText()
                )
            ) {

                throw new IllegalArgumentException(
                    "Informe o valor total."
                );

            }

            l.setvalor_total(
                Double.parseDouble(
                    tela.getTxtValorTotal()
                        .getText()
                        .trim()
                        .replace(",", ".")
                )
            );

            if (l.getId() == 0) {

                dao.salvar(l);

                JOptionPane.showMessageDialog(
                    tela,
                    "Locação cadastrada com sucesso!"
                );

            }

            else {

                dao.atualizar(l);

                JOptionPane.showMessageDialog(
                    tela,
                    "Locação atualizada com sucesso!"
                );

            }

            novo();

            listar();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                tela,
                "Digite valores numéricos válidos.",
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );

        } catch (Exception e) {

            erro(e);

        }

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
    
    
    

    public void listar() {

        try {

            tela.preencher(
                dao.listar()
            );

        } catch (Exception e) {

            erro(e);

        }

    }

    public void excluir() {

        try {

            int id = tela.getId();

            if (id == 0) {

                throw new IllegalArgumentException(
                    "Selecione uma locação."
                );

            }

            int resposta =
                JOptionPane.showConfirmDialog(
                    tela,
                    "Deseja realmente excluir esta locação?",
                    "Confirmação",
                    JOptionPane.YES_NO_OPTION
                );

            if (
                resposta != JOptionPane.YES_OPTION
            ) {

                return;

            }

            dao.excluir(id);

            JOptionPane.showMessageDialog(
                tela,
                "Locação excluída com sucesso!"
            );

            novo();

            listar();

        } catch (Exception e) {

            erro(e);

        }

    }

    public void buscar() {

        try {

            String texto =
                tela.getTxtPesquisa()
                    .getText()
                    .trim();

            if (texto.isEmpty()) {

                listar();

                return;

            }

            int id;

            try {

                id = Integer.parseInt(texto);

            } catch (NumberFormatException e) {

                throw new IllegalArgumentException(
                    "Digite um ID válido para pesquisar."
                );

            }

            LocacaoModel l =
                dao.buscarPorId(id);

            if (l == null) {

                JOptionPane.showMessageDialog(
                    tela,
                    "Locação não encontrada."
                );

                tela.preencher(
                    new java.util.ArrayList<LocacaoModel>()
                );

                return;

            }

            java.util.List<LocacaoModel> lista =
                new java.util.ArrayList<>();

            lista.add(l);

            tela.preencher(lista);

        } catch (Exception e) {

            erro(e);

        }

    }

    public void selecionar() {

        try {

            int linha =
                tela.getTabela()
                    .getSelectedRow();

            if (linha < 0) {

                return;

            }

            int id =
                Integer.parseInt(
                    tela.getTabela()
                        .getValueAt(linha, 0)
                        .toString()
                );

            Model l =
                dao.buscarPorId(id);

            if (l != null) {

                tela.mostrar(l);

            }

        } catch (Exception e) {

            erro(e);

        }

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