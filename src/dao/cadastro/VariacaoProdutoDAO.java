package dao.cadastro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VariacaoProdutoDAO {

    private Connection connection;

    public VariacaoProdutoDAO(Connection connection) {
        this.connection = connection;
    }

    // Inserir variação
    public boolean inserirVariacao(VariacaoProdutoModel variacao) throws SQLException {
        String sql = "{CALL inserir_variacao_produto(?, ?, ?, ?, ?, ?, ?)}";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, variacao.getSku());
        stmt.setString(2, variacao.getCodigoProd());
        stmt.setString(3, variacao.getTamanho());
        stmt.setString(4, variacao.getCor());
        stmt.setString(5, variacao.getModelo());
        stmt.setInt(6, variacao.getQuantidade());
        stmt.setBigDecimal(7, variacao.getPreco());
        return stmt.executeUpdate() > 0;
    }

    // Atualizar variação
    public boolean atualizarVariacao(VariacaoProdutoModel variacao) throws SQLException {
        String sql = "UPDATE variacao_produto SET tamanho_prod_var=?, cor_prod_var=?, modelo_prod_var=?, quantidade_prod=?, preco_prod_var=? WHERE sku=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, variacao.getTamanho());
        stmt.setString(2, variacao.getCor());
        stmt.setString(3, variacao.getModelo());
        stmt.setInt(4, variacao.getQuantidade());
        stmt.setBigDecimal(5, variacao.getPreco());
        stmt.setString(6, variacao.getSku());
        return stmt.executeUpdate() > 0;
    }

    // Excluir variação
    public boolean excluirVariacao(String sku) throws SQLException {
        String sql = "DELETE FROM variacao_produto WHERE sku=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, sku);
        return stmt.executeUpdate() > 0;
    }

    // Listar variações por produto
    public List<VariacaoProdutoModel> listarVariacoes(String codigoProd) throws SQLException {
        List<VariacaoProdutoModel> variacoes = new ArrayList<>();
        String sql = "SELECT * FROM vw_variacoes_produto WHERE codigo_prod = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, codigoProd);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            VariacaoProdutoModel v = new VariacaoProdutoModel();
            v.setSku(rs.getString("sku"));
            v.setCodigoProd(rs.getString("codigo_prod"));
            v.setTamanho(rs.getString("tamanho_prod_var"));
            v.setCor(rs.getString("cor_prod_var"));
            v.setModelo(rs.getString("modelo_prod_var"));
            v.setQuantidade(rs.getInt("quantidade_prod"));
            v.setPreco(rs.getBigDecimal("preco_prod_var"));
            variacoes.add(v);
        }
        return variacoes;
    }

    // Consultar por SKU
    public VariacaoProdutoModel consultarPorSku(String sku) throws SQLException {
        String sql = "SELECT * FROM variacao_produto WHERE sku = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, sku);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            VariacaoProdutoModel v = new VariacaoProdutoModel();
            v.setSku(rs.getString("sku"));
            v.setCodigoProd(rs.getString("codigo_prod"));
            v.setTamanho(rs.getString("tamanho_prod_var"));
            v.setCor(rs.getString("cor_prod_var"));
            v.setModelo(rs.getString("modelo_prod_var"));
            v.setQuantidade(rs.getInt("quantidade_prod"));
            v.setPreco(rs.getBigDecimal("preco_prod_var"));
            return v;
        }
        return null;
    }

    // Consultar por atributos (cor/tamanho)
    public List<VariacaoProdutoModel> consultarPorAtributos(String cor, String tamanho) throws SQLException {
        List<VariacaoProdutoModel> variacoes = new ArrayList<>();
        String sql = "SELECT * FROM variacao_produto WHERE cor_prod_var = ? AND tamanho_prod_var = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, cor);
        stmt.setString(2, tamanho);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            VariacaoProdutoModel v = new VariacaoProdutoModel();
            v.setSku(rs.getString("sku"));
            v.setCodigoProd(rs.getString("codigo_prod"));
            v.setTamanho(rs.getString("tamanho_prod_var"));
            v.setCor(rs.getString("cor_prod_var"));
            v.setModelo(rs.getString("modelo_prod_var"));
            v.setQuantidade(rs.getInt("quantidade_prod"));
            v.setPreco(rs.getBigDecimal("preco_prod_var"));
            variacoes.add(v);
        }
        return variacoes;
    }
}
