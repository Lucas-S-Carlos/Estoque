package dao.cadastro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Model;

public class ProdutoDAO {

    private Connection connection;

    public ProdutoDAO(Connection connection) {
        this.connection = connection;
    }

    // Inserir produto (procedure)
    public boolean inserirProduto(Model produto) throws SQLException {
        String sql = "{CALL inserir_produto(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, produto.getCodigo_prod());
        stmt.setString(2, produto.getNome_prod());
        stmt.setString(3, produto.getCategoria_prod());
        stmt.setString(4, produto.getDescricao_prod());
        stmt.setString(5, produto.getModelo_prod());
        stmt.setBigDecimal(6, produto.getPeso_prod());
        stmt.setBigDecimal(7, produto.getPreco_prod());
        stmt.setInt(8, produto.getEstoque_prod_min());
        stmt.setInt(9, produto.getEstoque_prod_max());
        stmt.setString(10, produto.getStatus_prod());
        return stmt.executeUpdate() > 0;
    }

    // Consultar por código
    public ProdutoModel consultarPorCodigo(String codigoProd) throws SQLException {
        String sql = "SELECT * FROM produto WHERE codigo_prod = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, codigoProd);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            ProdutoModel p = new ProdutoModel();
            p.setCodigoProd(rs.getString("codigo_prod"));
            p.setNomeProd(rs.getString("nome_prod"));
            p.setCategoriaProd(rs.getString("categoria_prod"));
            p.setDescricaoProd(rs.getString("descricao_prod"));
            p.setModeloProd(rs.getString("modelo_prod"));
            p.setPesoProd(rs.getBigDecimal("peso_prod"));
            p.setPrecoProd(rs.getBigDecimal("preco_prod"));
            p.setEstoqueMin(rs.getInt("estoque_prod_min"));
            p.setEstoqueMax(rs.getInt("estoque_prod_max"));
            p.setStatusProd(rs.getString("status_prod"));
            return p;
        }
        return null;
    }

    // Consultar por nome
    public List<ProdutoModel> consultarPorNome(String nomeProd) throws SQLException {
        List<ProdutoModel> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto WHERE nome_prod LIKE ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, "%" + nomeProd + "%");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            ProdutoModel p = new ProdutoModel();
            p.setCodigoProd(rs.getString("codigo_prod"));
            p.setNomeProd(rs.getString("nome_prod"));
            p.setCategoriaProd(rs.getString("categoria_prod"));
            p.setPrecoProd(rs.getBigDecimal("preco_prod"));
            p.setEstoqueMin(rs.getInt("estoque_prod_min"));
            p.setEstoqueMax(rs.getInt("estoque_prod_max"));
            p.setStatusProd(rs.getString("status_prod"));
            produtos.add(p);
        }
        return produtos;
    }



    // Atualizar produto
    public boolean atualizarProduto(ProdutoModel produto) throws SQLException {
        String sql = "UPDATE produto SET nome_prod=?, categoria_prod=?, descricao_prod=?, modelo_prod=?, peso_prod=?, preco_prod=?, estoque_prod_min=?, estoque_prod_max=?, status_prod=? WHERE codigo_prod=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, produto.getNomeProd());
        stmt.setString(2, produto.getCategoriaProd());
        stmt.setString(3, produto.getDescricaoProd());
        stmt.setString(4, produto.getModeloProd());
        stmt.setBigDecimal(5, produto.getPesoProd());
        stmt.setBigDecimal(6, produto.getPrecoProd());
        stmt.setInt(7, produto.getEstoqueMin());
        stmt.setInt(8, produto.getEstoqueMax());
        stmt.setString(9, produto.getStatusProd());
        stmt.setString(10, produto.getCodigoProd());
        return stmt.executeUpdate() > 0;
    }

    // Excluir produto
    public boolean excluirProduto(String codigoProd) throws SQLException {
        String sql = "DELETE FROM produto WHERE codigo_prod=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, codigoProd);
        return stmt.executeUpdate() > 0;
    }

    // Listar produtos (usa view para ativos/inativos)
    public List<ProdutoModel> listarProdutos(String status) throws SQLException {
        List<ProdutoModel> produtos = new ArrayList<>();
        String sql = "SELECT * FROM vw_produtos WHERE status_prod = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, status);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            ProdutoModel p = new ProdutoModel();
            p.setCodigoProd(rs.getString("codigo_prod"));
            p.setNomeProd(rs.getString("nome_prod"));
            p.setCategoriaProd(rs.getString("categoria_prod"));
            p.setPrecoProd(rs.getBigDecimal("preco_prod"));
            p.setEstoqueMin(rs.getInt("estoque_prod_min"));
            p.setEstoqueMax(rs.getInt("estoque_prod_max"));
            p.setStatusProd(rs.getString("status_prod"));
            produtos.add(p);
        }
        return produtos;
    }
}
