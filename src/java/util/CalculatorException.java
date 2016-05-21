/*
 * Classe para tratamento e tipificação de exceções específicas da calculadora
 */
package util;

/**
 * Classe para tratamento de exceções específicas da calculadora
 * 
 * @author Grupo GRA (Anne, Gilmar, Rodrigo Boreto e Rodrigo Fernandes) <aiec.br>
 */
public class CalculatorException extends Exception{
    
    public CalculatorException() {}
    
    public CalculatorException(String message) {
        super(message);
    }
    
    public CalculatorException(Throwable cause) {
        super(cause);
    }

    public CalculatorException(String message, Throwable cause) {
        super(message, cause);
    }
}
