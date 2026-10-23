
package controller.cadastro;
/*
import dao.ClienteDao;
import dao.CarroDao;
import dao.LocacaoDao;
*/
import model.Model;


import util.Validador;
import view.cadastro.TelaVariacoes;

import javax.swing.JOptionPane;

public class ControllerVariacoes {

    private final TelaVariacoes tela;
/*
    private final ... clienteDAO = new ...(); 
    private final ... carroDAO = new ...();
    private final ... dao = new ...();
*/
    
    int id;
    
    public ControllerVariacoes(TelaVariacoes t) {

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