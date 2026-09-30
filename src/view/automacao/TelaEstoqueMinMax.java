package view.automacao;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class TelaEstoqueMinMax extends JPanel {

    private static final long serialVersionUID = 1L;

    private final Color COR_FUNDO = new Color(245, 247, 250);
    private final Color COR_BOTAO = new Color(52, 152, 219);

    private JTextField campoProduto;
    private JTextField campoEstoqueMin;
    private JTextField campoEstoqueMax;
    private JComboBox<String> comboStatus;

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    private JButton btnSalvar;
    private JButton btnLimpar;
    private JButton btnPesquisar;

    public TelaEstoqueMinMax() {
        criarTela();
    }

    private void criarTela() {
        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(COR_FUNDO);

        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBackground(Color.WHITE);
        formulario.setBorder(BorderFactory.createTitledBorder("Configuração de Estoque Mínimo e Máximo"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        campoProduto = new JTextField(20);
        adicionarCampo(formulario, gbc, 0, 0, "Produto / SKU:", campoProduto);

        comboStatus = new JComboBox<>(new String[]{"Todos", "Ativo", "Inativo"});
        adicionarCampo(formulario, gbc, 0, 2, "Status:", comboStatus);

        campoEstoqueMin = new JTextField(15);
        adicionarCampo(formulario, gbc, 1, 0, "Estoque Mínimo:", campoEstoqueMin);

        campoEstoqueMax = new JTextField(15);
        adicionarCampo(formulario, gbc, 1, 2, "Estoque Máximo:", campoEstoqueMax);

        btnSalvar = new JButton("Salvar Parâmetros");
        btnSalvar.setBackground(COR_BOTAO);
        btnSalvar.setForeground(Color.WHITE);

        btnLimpar = new JButton("Limpar");
        btnPesquisar = new JButton("Pesquisar");

        JPanel botoesForm = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        botoesForm.setBackground(Color.WHITE);
        botoesForm.add(btnLimpar);
        botoesForm.add(btnPesquisar);
        botoesForm.add(btnSalvar);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        gbc.weightx = 1;
        formulario.add(botoesForm, gbc);

        String[] colunas = {"Produto / SKU", "Estoque Mínimo", "Estoque Máximo", "Status"};
        modeloTabela = new DefaultTableModel(new Object[][]{}, colunas) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);
        tabela.setRowHeight(28);

        JPanel painelTabela = new JPanel(new BorderLayout());
        painelTabela.setBackground(Color.WHITE);
        painelTabela.setBorder(BorderFactory.createTitledBorder("Parâmetros Cadastrados"));
        painelTabela.add(new JScrollPane(tabela), BorderLayout.CENTER);

        add(formulario, BorderLayout.NORTH);
        add(painelTabela, BorderLayout.CENTER);

        btnSalvar.addActionListener(e -> salvarParametros());
        btnLimpar.addActionListener(e -> limparCampos());
        btnPesquisar.addActionListener(e -> JOptionPane.showMessageDialog(this, "Filtro aplicado com sucesso."));
    }

    private void adicionarCampo(JPanel painel, GridBagConstraints gbc, int linha, int coluna, String texto, JComponent componente) {
        gbc.gridx = coluna;
        gbc.gridy = linha;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        painel.add(new JLabel(texto), gbc);

        gbc.gridx = coluna + 1;
        gbc.weightx = 1;
        painel.add(componente, gbc);
    }

    private void salvarParametros() {
        if (campoProduto.getText().trim().isEmpty() || campoEstoqueMin.getText().trim().isEmpty() || campoEstoqueMax.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int min = Integer.parseInt(campoEstoqueMin.getText().trim());
            int max = Integer.parseInt(campoEstoqueMax.getText().trim());

            if (min >= max) {
                JOptionPane.showMessageDialog(this, "O estoque mínimo deve ser menor que o máximo.", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }

            modeloTabela.addRow(new Object[]{
                campoProduto.getText().trim(),
                min,
                max,
                comboStatus.getSelectedItem()
            });

            JOptionPane.showMessageDialog(this, "Parâmetros salvos com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Digite valores numéricos inteiros válidos para os estoques.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        campoProduto.setText("");
        campoEstoqueMin.setText("");
        campoEstoqueMax.setText("");
        comboStatus.setSelectedIndex(0);
    }
}
