/*
 * Define as operações básicas de um CRUD (create, retrieve, update e delete)
 */
package dao;

import model.Historic;
import java.util.List;

/**
 *
 * @author Grupo GRAR (Anne, Gilmar, Ricardo Boreto e Ricardo Rodrigues) <aiec.br>
 */
public interface historicoIDAO {
    /**
     * Permite incluir um historico na base
     * 
     * @param historico 
     */
    public void inserir(Historic historico);
    
    /**
     * Permite editar um historico na base com base no ID do histórico
     * 
     * @param historico 
     */
    public void atualizar(Historic historico);
    
    /**
     * Permite excluir um histórico com base no ID informado
     * 
     * @param historicoID
     */
    public void excluir(int historicoID);
    
    /**
     * Permite consultar todos os históricos cadastrados
     * 
     * @return List
     */
    public List<Historic> consultarTodos();
}
