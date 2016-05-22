/*
 * Classe para tratamento e tipificação de exceções específicas da calculadora
 */
package util;

/**
 * Classe para tratamento de exceções específicas da calculadora
 * 
 * @author Grupo GRA (Anne, Gilmar, Rodrigo Boreto e Rodrigo Fernandes) <aiec.br>
 */
public class FractionException extends Exception{
    
    public FractionException() {}
    
    public FractionException(String message) {
        super(message);
    }
    
    public FractionException(Throwable cause) {
        super(cause);
    }

    public FractionException(String message, Throwable cause) {
        super(message, cause);
    }
}
