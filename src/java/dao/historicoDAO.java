/*
 * A referida classe implementa o padrão Data Access Object para um objeto do 
 * tipo Historic para o banco de dados MySQL.
 */
package dao;

import util.DataSourceMySQL;
import model.Historic;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * A referida classe implementa o padrão Data Access Object para um objeto do 
 tipo Historic para o banco de dados MySQL.
 * 
 * @author Grupo GRAR (Anne, Gilmar, Ricardo Boreto e Rodrigo Fernandes) <aiec.br>
 */
public class historicoDAO implements historicoIDAO{
    
    /**
     * Retorna a conexao de para utilizacao do objeto
     * 
     * @return 
     */
    private Connection getConexao() {
        return new DataSourceMySQL().getConexao();
    }

   /**
   * Permite incluir um objeto na tabela de historico do banco de dados
   */
    @Override
    public void inserir(Historic historico) {
        // Obtendo a conexão com SGBD
        Connection conexao = this.getConexao();

        // Query SQL para inserir os dados da historico no banco
        String sql = "INSERT INTO historicoDeFracoes "
                + "(expressao, resultado, simplificado, valorDecimal, classificacao, dataCriacao)"
                + " VALUES ( ?, ?, ?, ?, ?, NOW() )";

        try {
            // Interface universal da API JDBC
            PreparedStatement pstmt;

           // criando o objeto do tipo PreparedStatement a partir da conexão
            pstmt = conexao.prepareStatement(sql);

            // Inclui valor da 'expressao' da Historic na statement da query
            pstmt.setString(1, historico.getExpressao());

            // Inclui valor da 'resultado' da Historic na statement da query
            pstmt.setString(2, historico.getResultado());
            
            // Inclui valor da 'resultado simplificado' da Historic na statement da query
            pstmt.setString(3, historico.getSimplificado());
            
            // Inclui valor da 'notação decimal' do Historic na statement da query
            pstmt.setString(4, historico.getDecimal().toPlainString() );
            
            // Inclui valor da 'classificação' do histórico na statement da query
            pstmt.setString(5, historico.getClassificacao());
            

            // Executando a query no banco
            pstmt.executeUpdate();

            // Finalizando a query
            pstmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Falha ao inserir a historico na tabela de históricos (historicoDeFracoes)", e);
        }
    }

  /**
   * O método tem por finalidade atualizar um registro de histórico de historico 
   * presentes no banco de dados
   */
    @Override
    public void atualizar(Historic historico) {

        // Obtendo a conexão com SGBD
        Connection conexao = this.getConexao();

        // Query sql para consultar todas as fracoes do banco
        String sql = "UPDATE historicoDeFracoes SET "
                + "expressao = ?,"
                + "resultado = ?,"
                + "simplificado = ?,"
                + "valorDecimal = ?,"
                + "classificacao = ?,"
                + "dataModificacao = NOW()"
                + "WHERE historicoID = ?";

        try {
            // Interface universal da API JDBC
            PreparedStatement pstmt;

            // criando o objeto do tipo PreparedStatement a partir da conexão
            pstmt = conexao.prepareStatement(sql);

            // Inclui valor da 'expressao' da Historic na statement da query
            pstmt.setString(1, historico.getExpressao());

            // Inclui valor da 'resultado' da Historic na statement da query
            pstmt.setString(2, historico.getResultado());
            
            // Inclui valor da 'resultado simplificado' da Historic na statement da query
            pstmt.setString(3, historico.getSimplificado());
            
            // Inclui valor da 'notação decimal' do Historic na statement da query
            pstmt.setBigDecimal(4, historico.getDecimal());
            
            // Inclui valor da 'classificação' do histórico na statement da query
            pstmt.setString(5, historico.getClassificacao());
            
             // Inclui valor da 'ID' do histórico na statement da query
            pstmt.setInt(6, historico.getId());

            // Executando a query no banco
            pstmt.executeUpdate();

            // Finalizando a query
            pstmt.close();

        } catch (SQLException e) {
            throw new RuntimeException("Falha ao atualizar o registro: "
                    + historico.getId(), e);
        }
    }

   /**
   * Remove uma historico (registro) com base no ID fornecido
   */
    @Override
    public void excluir(int historicoID) {

        // Obtendo a conexão com SGBD
        Connection conexao = this.getConexao();

        // Query sql para consultar todas as fracoes do banco
        String sql = "DELETE FROM historicoDeFracoes WHERE historicoID = ?";

        try {
            // Interface universal da API JDBC
            PreparedStatement pstmt;

            // criando o objeto do tipo PreparedStatement a partir da conexão
            pstmt = conexao.prepareStatement(sql);

            // Alterando o primeiro simbolo de ? da query pelo id do parâmetro
            // do tipo Historic
            pstmt.setInt(1, historicoID);
            
            // Executando a query no banco
            pstmt.executeUpdate();

            // Finalizando a query
            pstmt.close();

        } catch (SQLException e) {
            throw new RuntimeException("Falha ao exluir o registro: "
                    + historicoID, e);
        }
    }

   /**
   * Retorna todos os registros de frações persistidas na tabela de histórico
   */
    @Override
    public List<Historic> consultarTodos() {
        String sql = "SELECT * FROM historicoDeFracoes";
        return this.searchBySQL(sql);
    }
    
   /**
   * Retorna os últimos registros de histórico com base no limite informado
   * 
   * @param limit   Número de registros desejados
   * 
   * @return  List
   */
    public List<Historic> consultarUtimos(int limit) {
        String sql = String.format( 
            "SELECT * FROM historicoDeFracoes ORDER BY dataCriacao DESC LIMIT %d" ,
            limit
        );
        
        return this.searchBySQL(sql);
    }
    
   /**
   * Retorna uma coleção de objetos do tipo histórico com base na query informada
   * no parâmetro
   * 
   * @return  List
   */
    private List<Historic> searchBySQL(String SQL) {
        Connection conexao = this.getConexao();

        // Lista de retorno que conterá as fracoes registradas no banco
        List<model.Historic> listHistoricos = new LinkedList<model.Historic>();

        try {

            // Interface universal da API JDBC
            PreparedStatement pstmt;

            // criando o objeto do tipo PreparedStatement a partir da conexão
            pstmt = conexao.prepareStatement(SQL);

            // Executando a query no banco
            ResultSet rs = pstmt.executeQuery();

            // Percorrendo o resultado da query
            while (rs.next()) {

                // Criando um objeto de Historic
                Historic historico = new Historic();
                
                /**
                 * Fazemos a leitura dos dados de cada campo e utilizamos
                 * a informação obtida para para definir as propriedades
                 * do objeto de histórico desejado
                 */ 
                historico.setId(rs.getInt("historicoId"));
                historico.setExpressao(rs.getString("expressao"));
                historico.setResultado(rs.getString("resultado"));
                historico.setSimplificado(rs.getString("simplificado"));
                historico.setClassificacao(rs.getString("classificacao"));
                historico.setDecimal(rs.getBigDecimal("valorDecimal"));
                historico.setDataCriacao(new Date(rs.getTimestamp("dataCriacao").getTime()));
                
                if ( rs.getString("dataModificacao") != null && ! rs.getString("dataModificacao").isEmpty() ) {
                    historico.setDataModificacao(new Date(rs.getTimestamp("dataModificacao").getTime()));
                }

                // Adicionando o objeto do tipo historico a lista de resultados
                listHistoricos.add(historico);
            }

            // Finalizando a query
            pstmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(
                "Falha ao consultar todas as fracoes na historicoDeFracoes", e);
        }

        // retornando a lista com todos os registros presentes no banco.
        return listHistoricos;
    }
}