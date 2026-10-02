package view.movimentacao;

import java.util.ArrayList;
import java.util.List;


/**
 * Catálogo PROVISÓRIO de itens que podem ser movimentados:
 * produtos, variações (SKU) e kits.
 *
 * Enquanto o banco de dados e as telas de cadastro
 * (TelaProdutos, TelaVariacoes, TelaKits) não estiverem ligados
 * às telas de movimentação, esta classe faz o papel do cadastro.
 *
 * QUANDO O BANCO ESTIVER PRONTO: basta trocar o conteúdo dos
 * métodos listar() e buscar() para consultarem o cadastro real.
 * As telas de movimentação não precisam mudar.
 */
public final class CatalogoItens {

    /**
     * Tipos de item que podem ser movimentados.
     */
    public enum TipoItem {

        PRODUTO("Produto", "Código do produto (ex.: ESC001)"),
        VARIACAO("Variação (SKU)", "SKU da variação (ex.: CAD-96-AZ)"),
        KIT("Kit", "Código do kit (ex.: KIT001)");

        private final String rotulo;
        private final String dica;

        TipoItem(String rotulo, String dica) {
            this.rotulo = rotulo;
            this.dica = dica;
        }

        public String getRotulo() {
            return rotulo;
        }

        public String getDica() {
            return dica;
        }

        // O JComboBox usa o toString() para mostrar o texto.
        @Override
        public String toString() {
            return rotulo;
        }
    }


    /**
     * Um item do catálogo.
     */
    public static final class Item {

        private final TipoItem tipo;
        private final String codigo;
        private final String nome;

        // Só preenchido para variações: código do produto "pai".
        private final String codigoProduto;

        private Item(
            TipoItem tipo,
            String codigo,
            String nome,
            String codigoProduto
        ) {
            this.tipo = tipo;
            this.codigo = codigo;
            this.nome = nome;
            this.codigoProduto = codigoProduto;
        }

        public TipoItem getTipo() {
            return tipo;
        }

        public String getCodigo() {
            return codigo;
        }

        public String getNome() {
            return nome;
        }

        public String getCodigoProduto() {
            return codigoProduto;
        }

        // Exemplo: "ESC001 - Caderno"
        public String descricao() {
            return codigo + " - " + nome;
        }

        // Usado pela janela de busca.
        @Override
        public String toString() {
            return descricao();
        }
    }


    private static final List<Item> ITENS = new ArrayList<>();


    static {

        // ---------------- PRODUTOS ----------------
        produto("ESC001", "Caderno");
        produto("ESC002", "Caneta");
        produto("ESC003", "Lápis");
        produto("ESC004", "Borracha");
        produto("ESC005", "Apontador");
        produto("ESC006", "Régua");
        produto("ESC007", "Mochila escolar");
        produto("ESC008", "Estojo");
        produto("ESC009", "Uniforme escolar");
        produto("ESC010", "Papel sulfite");
        produto("ESC011", "Cartolina");
        produto("ESC012", "Cola branca");
        produto("ESC013", "Tesoura escolar");
        produto("ESC014", "Marcador de texto");
        produto("ESC015", "Pasta escolar");

        // ---------------- VARIAÇÕES (SKU) ----------------
        variacao("CAD-96-AZ", "Caderno 96 folhas - Azul", "ESC001");
        variacao("CAD-96-VM", "Caderno 96 folhas - Vermelho", "ESC001");
        variacao("MOC-G-PT", "Mochila escolar G - Preta", "ESC007");
        variacao("MOC-M-AZ", "Mochila escolar M - Azul", "ESC007");
        variacao("UNI-P-BR", "Uniforme escolar P - Branco", "ESC009");
        variacao("UNI-M-BR", "Uniforme escolar M - Branco", "ESC009");

        // ---------------- KITS ----------------
        kit("KIT001", "Kit material básico");
        kit("KIT002", "Kit papelaria");
        kit("KIT003", "Kit uniforme");
    }


    private CatalogoItens() {
    }


    private static void produto(String codigo, String nome) {
        ITENS.add(new Item(TipoItem.PRODUTO, codigo, nome, null));
    }

    private static void variacao(
        String sku,
        String nome,
        String codigoProduto
    ) {
        ITENS.add(new Item(TipoItem.VARIACAO, sku, nome, codigoProduto));
    }

    private static void kit(String codigo, String nome) {
        ITENS.add(new Item(TipoItem.KIT, codigo, nome, null));
    }


    /**
     * Lista todos os itens de um tipo.
     */
    public static List<Item> listar(TipoItem tipo) {

        List<Item> resultado = new ArrayList<>();

        for (Item item : ITENS) {

            if (item.getTipo() == tipo) {
                resultado.add(item);
            }
        }

        return resultado;
    }


    /**
     * Procura um item pelo tipo e pelo código digitado.
     * Ignora maiúsculas/minúsculas e espaços nas pontas.
     * Retorna null se não existir.
     */
    public static Item buscar(TipoItem tipo, String codigo) {

        if (codigo == null) {
            return null;
        }

        String procurado = codigo.trim();

        for (Item item : ITENS) {

            if (
                item.getTipo() == tipo
                && item.getCodigo().equalsIgnoreCase(procurado)
            ) {
                return item;
            }
        }

        return null;
    }
}
