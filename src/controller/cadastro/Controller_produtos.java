
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

public class Controller_produtos {

    private final TelaProdutos tela;
/*
    private final ... clienteDAO = new ...(); 
    private final ... carroDAO = new ...();
    private final ... dao = new ...();
*/
    public Controller_produtos(TelaProdutos t) {

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

	    private String converterData(String data) {
	        if (
	    data == null || data.trim().isEmpty()) {
	            return null;
	        }
	
	        data = data.trim();
	
	        if (data.matches("\\d{2}/\\d{2}/\\d{4}")) {
	            String[] partes = data.split("/");
	
	            return partes[2] + "-" + partes[1] + "-" + partes[0];
	        }
	
	        return data;
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