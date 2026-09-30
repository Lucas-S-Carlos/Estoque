package view.cadastro;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class TelaVariacoes extends JPanel {

    private static final long serialVersionUID = 1L;

    private final JTextField produto = new JTextField(20);
    private final JTextField nomeProduto = new JTextField(25);

    private final JTextField sku = new JTextField(20);
    private final JTextField nomeVariacao = new JTextField(25);
    private final JTextField tamanho = new JTextField(10);
    private final JTextField cor = new JTextField(15);
    private final JTextField modelo = new JTextField(15);
    private final JTextField quantidade = new JTextField(10);
    private final JTextField preco = new JTextField(10);
    private final JTextField pesquisa = new JTextField(24);

    private final DefaultTableModel modeloTabela = new DefaultTableModel(
            new Object[] {
                    "Código do produto",
                    "Produto",
                    "SKU",
                    "Nome da variação",
                    "Tamanho",
                    "Cor",
                    "Modelo",
                    "Estoque",
                    "Preço"
            },
            0
    ) {
        private static final long serialVersionUID = 1L;

        public boolean isCellEditable(int linha, int coluna) {
            return false;
        }
    };

    private final JTable tabela = new JTable(modeloTabela);

    private final JButton novo = new JButton("Novo");
    private final JButton salvar = new JButton("Salvar");
    private final JButton excluir = new JButton("Excluir");
    private final JButton limpar = new JButton("Limpar");
    private final JButton buscar = new JButton("Buscar");
    private final JButton todos = new JButton("Todos");

    public TelaVariacoes() {

        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        montar();
    }

    private void montar() {

        nomeProduto.setEditable(false);

        JPanel formulario = new JPanel(new GridBagLayout());

        formulario.setBorder(
                BorderFactory.createTitledBorder(
                        "Cadastro de variações - SKU"
                )
        );

        GridBagConstraints g = new GridBagConstraints();

        g.insets = new Insets(3, 4, 3, 4);
        g.anchor = GridBagConstraints.WEST;

        campo(formulario, g, 0, "Código do produto*:", produto);
        campo(formulario, g, 1, "Nome do produto:", nomeProduto);
        campo(formulario, g, 2, "SKU*:", sku);
        campo(formulario, g, 3, "Nome do produto variado*:", nomeVariacao);
        campo(formulario, g, 4, "Tamanho:", tamanho);
        campo(formulario, g, 5, "Cor:", cor);
        campo(formulario, g, 6, "Modelo:", modelo);
        campo(formulario, g, 7, "Quantidade:", quantidade);
        campo(formulario, g, 8, "Preço:", preco);

        JPanel botoes = new JPanel(
                new FlowLayout(FlowLayout.LEFT)
        );

        botoes.add(novo);
        botoes.add(salvar);
        botoes.add(excluir);
        botoes.add(limpar);

        JPanel superior = new JPanel(new BorderLayout());

        superior.add(formulario, BorderLayout.CENTER);
        superior.add(botoes, BorderLayout.SOUTH);

        add(superior, BorderLayout.NORTH);

        JPanel pesquisaPanel = new JPanel(
                new FlowLayout(FlowLayout.LEFT)
        );

        pesquisaPanel.add(new JLabel("Pesquisar SKU:"));
        pesquisaPanel.add(pesquisa);
        pesquisaPanel.add(buscar);
        pesquisaPanel.add(todos);

        JPanel central = new JPanel(new BorderLayout());

        central.add(pesquisaPanel, BorderLayout.NORTH);
        central.add(new JScrollPane(tabela), BorderLayout.CENTER);

        add(central, BorderLayout.CENTER);

        tabela.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );
    }

    private void campo(
            JPanel painel,
            GridBagConstraints g,
            int y,
            String texto,
            JTextField campo
    ) {
        componente(painel, g, y, texto, campo);
    }

    private void componente(
            JPanel painel,
            GridBagConstraints g,
            int y,
            String texto,
            java.awt.Component componente
    ) {

        g.gridx = 0;
        g.gridy = y;
        g.weightx = 0;
        g.fill = GridBagConstraints.NONE;

        painel.add(new JLabel(texto), g);

        g.gridx = 1;
        g.weightx = 1;
        g.fill = GridBagConstraints.HORIZONTAL;

        painel.add(componente, g);
    }

    public void limpar() {

        sku.setText("");
        nomeVariacao.setText("");
        tamanho.setText("");
        cor.setText("");
        modelo.setText("");
        quantidade.setText("");
        preco.setText("");

        produto.setText("");
        nomeProduto.setText("");

        tabela.clearSelection();
    }

    public JTextField getProduto() {
        return produto;
    }

    public JTextField getNomeProduto() {
        return nomeProduto;
    }

    public JTextField getSku() {
        return sku;
    }

    public JTextField getNomeVariacao() {
        return nomeVariacao;
    }

    public JTextField getTamanho() {
        return tamanho;
    }

    public JTextField getCor() {
        return cor;
    }

    public JTextField getModelo() {
        return modelo;
    }

    public JTextField getQuantidade() {
        return quantidade;
    }

    public JTextField getPreco() {
        return preco;
    }

    public JTextField getPesquisa() {
        return pesquisa;
    }

    public JTable getTabela() {
        return tabela;
    }

    public DefaultTableModel getModeloTabela() {
        return modeloTabela;
    }

    public JButton getNovo() {
        return novo;
    }

    public JButton getSalvar() {
        return salvar;
    }

    public JButton getExcluir() {
        return excluir;
    }

    public JButton getLimpar() {
        return limpar;
    }

    public JButton getBuscar() {
        return buscar;
    }

    public JButton getTodos() {
        return todos;
    }
}
