/*
 * Classe de conexão com o banco de dados MySQL
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe de conexão com o banco de dados MySQL
 * 
 * @author Grupo GRA (Anne, Gilmar, Ricado Boreto e Rodrigo Fernandes) <aiec.br>
 */
public class DataSourceMySQL {
    // Caminho do servidor de banco de dados
    private final String SERVERNAME = "localhost:3306";
    
    // Nome do banco de dados
    private final String DATABASE = "calculadora";

    // Usuário de conexão do banco de dados
    private final String USER = "root";

    // Senha do usuário de conexão do banco de dados
    private final String PASSWORD = "admin";
    
    // URL de conexão com o banco de dados
    private final String URL = String.format("jdbc:mysql://%s/%s", SERVERNAME, DATABASE);
    
    // FQDN da classe principal do driver JDBC para o MySQL
    private final String DRIVER = "com.mysql.jdbc.Driver";

    // Objeto de conexão com o banco de dados
    private Connection conexao;

   /**
   * Registra o driver JDBC e inicializa a conexão com o SGBD
   */
    public DataSourceMySQL() {
        try {
            // Registrando o Driver para o MySQL
            Class.forName(DRIVER);

            // Estabelecendo a conexão com SGBD
            this.conexao = DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (SQLException e) {
            throw new RuntimeException("Falha na conexão com o SGBD.", e);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver JDBC não encontrado.", e);
        }
    }
    
   /**
   * Retorna o objeto de conexão que foi criado.
   * 
   * @return Connection
   */
    public Connection getConexao(){
        return this.conexao;
    }
}