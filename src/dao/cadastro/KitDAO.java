/*
!!!!!Atenção!!!!!
Dao-model incompleto

*/

package dao.cadastro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Model;

public class KitDAO {

    private Connection connection;

    public KitDAO(Connection connection) {
        this.connection = connection;
    }

    // Inserir kit (procedure)
    public boolean inserirKit(Model kit) throws SQLException {
        String sql = "{CALL inserir_kit(?, ?, ?, ?)}";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, kit.getCodigo_kit());
        stmt.setString(2, kit.getNome_kit());
        stmt.setString(3, kit.getTipo_kit());
        stmt.setInt(4, kit.getQuantidade_kit());
        return stmt.executeUpdate() > 0;
    }

    // Atualizar kit
    public boolean atualizarKit(Model kit) throws SQLException {
        String sql = "UPDATE kit SET nome_kit=?, tipo_kit=?, quantidade_kit=? WHERE codigo_kit=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, kit.getNome_kit());
        stmt.setString(2, kit.getTipo_kit());
        stmt.setInt(3, kit.getQuantidade_kit());
        stmt.setString(4, kit.getCodigo_kit());
        return stmt.executeUpdate() > 0;
    }

    // Excluir kit
    public boolean excluirKit(String codigoKit) throws SQLException {
        String sql = "DELETE FROM kit WHERE codigo_kit=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, codigoKit);
        return stmt.executeUpdate() > 0;
    }

    // Inserir componente no kit (procedure)
    public boolean inserirComponente(Model componente) throws SQLException {
        String sql = "{CALL inserir_kit_componente(?, ?, ?, ?)}";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, componente.getCodigo_kit());
        stmt.setString(2, componente.getCodigo_prod());
        stmt.setString(3, componente.getSku());
        stmt.setInt(4, componente.getQuantidade_kit());
        return stmt.executeUpdate() > 0;
    }

    // Excluir componente de kit
    public boolean excluirComponente(String codigoKit, String codigoProd, String sku) throws SQLException {
        String sql = "DELETE FROM kit_componente WHERE codigo_kit = ? AND codigo_prod = ? AND sku = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, codigoKit);
        stmt.setString(2, codigoProd);
        stmt.setString(3, sku);
        return stmt.executeUpdate() > 0;
    }

    // Consultar kit por código
    public Model consultarKitPorCodigo(String codigoKit) throws SQLException {
        String sql = "SELECT * FROM kit WHERE codigo_kit = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, codigoKit);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Model k = new Model();
            k.setCodigo_kit(rs.getString("codigo_kit"));
            k.setNome_kit(rs.getString("nome_kit"));
            k.setTipo_kit(rs.getString("tipo_kit"));
            k.setQuantidade_kit(rs.getInt("quantidade_kit"));
            return k;
        }
        return null;
    }

    // Listar kits e seus componentes (via view)
    public List<Model> listarKits() throws SQLException {
        List<Model> kits = new ArrayList<>();
        String sql = "SELECT * FROM vw_kits_com_componentes";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Model k = new Model();
            k.setCodigo_kit(rs.getString("codigo_kit"));
            k.setNome_kit(rs.getString("nome_kit"));
            k.setTipo_kit(rs.getString("tipo_kit"));
            k.setQuantidade_kit(rs.getInt("quantidade_kit"));

            Model c = new Model();
            c.setCodigo_kit(rs.getString("codigo_kit"));
            c.setCodigo_prod(rs.getString("codigo_prod"));
            c.setSku(rs.getString("sku"));
            c.setQuantidade_kit(rs.getInt("quantidade_componente"));

            k.addComponente(c); // supondo que KitModel tenha lista de componentes
            kits.add(k);
        }
        return kits;
    }
}
