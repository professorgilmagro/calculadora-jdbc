/*
 * Centraliza os métodos comuns para uma fração matemática
 */
package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import util.CalculatorException;

/**
 *
 * @author Grupo GRA (Anne, Gilmar e Ricardo) <aiec.br>
 */
public final class Fraction {
    
    /**
     * Enum para gerir as constantes para os operadores para cálculo das partes
     * envolvidas
     */
    public enum operator {
        ADDITION("+"),
        SUBTRACTION("-"),
        MULTIPLICATION("×"),
        DIVISION("÷");
        
        private final String sign;
        
        /**
         * Construtor do enum
         * Define o operador com base nas constantes definidas
         * 
         * @param value 
         */
        operator(String value){ 
            this.sign = value;
        }
        
        /**
         * Retorna o sinal da operacao definida na constante
         * 
         * @return String
         */
        public String getSign(){ 
            return sign; 
        }
    }
    
    /**
     * Recebe o tipo de operação matemática
     */
    private String operador = "" ;
    
    /**
     * Recebe um inteiro para o numerador
     */
    private Integer numerador ;
    
    /**
     * Recebe um inteiro para o denominador
     */
    private Integer denominador ;
    
    /**
     * Flag para indicar se a expressão é válida para ser calculada
     */
    private Boolean valid ;
    
    /**
     * Recebe uma instância da fração como resultado do cálculo
     */
    private Fraction result ;
    
    /**
     * Recebe a lista de tipos de classificação da fração
     */
    private ArrayList<String> types = new ArrayList<String>();
    
    /**
     * Recebe a lista de frações que serão utilizadas para o cálculo
     */
    private ArrayList<Fraction> fracs = new ArrayList<Fraction>();
    
    /**
     * Construtor para receber um objeto do tipo fração
     * 
     * @param fracao 
     */
    public Fraction(Fraction fracao){
        this.denominador = fracao.getDenominador();
        this.numerador = fracao.getNumerador();
    }
    
    /**
     * Construtor para receber o numerador e denominador
     * 
     * @param numerador
     * @param denominador 
     * 
     * @throws util.CalculatorException 
     */
    public Fraction(Integer numerador, Integer denominador) throws CalculatorException{
        this.setNumerador(numerador);
        this.setDenominador(denominador);
    }
    
    /**
     * Construtor para receber o numerador, denominador e a operação
     * 
     * @param numerador
     * @param denominador 
     * @param operacao 
     * @throws util.CalculatorException 
     */
    public Fraction(Integer numerador, Integer denominador, String operacao) throws CalculatorException{
        this.setNumerador(numerador);
        this.setDenominador(denominador);
        this.setOperador(operacao);
    }
     
    /**
     * Adiciona a fração à coleção de frações que compõe o cálculo
     * 
     * @param frac
     * 
     * @return self
     */
    public Fraction add(Fraction frac){
        this.fracs.add(frac);
        return this;
    }
    
    /**
     * Devolve o numerador da fração
     * 
     * @return Integer
     */
    public Integer getNumerador() {
        return numerador;
    }
    
    /**
     * Define o numerador da fração
     * 
     * @param numerador
     */
    public void setNumerador(Integer numerador) {
        this.numerador = numerador;
    }
    
    /**
     * Devolve o denominador da fração
     * 
     * @return Integer
     */
    public Integer getDenominador() {
        return denominador;
    }
    
    /**
     * Define o denominador da fração
     * 
     * @param denominador  
     * @throws util.CalculatorException  
     */
    public void setDenominador(Integer denominador) throws CalculatorException {
        String errorMessage = "Ops! Apenas números inteiros maiores que zero são aceitos para o denominador.";
        if ( denominador <= 0){
            throw new CalculatorException(errorMessage);
        }
        
        this.denominador = denominador;
        
    }
    
    /**
     * Retorna o operador de relação com o objeto 
     * 
     * @return 
     */
    public String getOperador() {
        return operador;
    }
    
    /**
     * Define o operador de cálculo para este objeto 
     * 
     * @param operador
     */
    public void setOperador(String operador) {
        this.operador = operador;
    }
    
    /**
     * Efetua o cálculo da fração
     * As frações serão solucionadas em pares, seguindo a prioridade conforme
     * a sequência: Divisão, multiplicação, soma e subtração.
     * A medida que as partes que compoẽm o cálculo são solucionadas, a
     * expressão vai sendo reduzida até a obtenção do resultado final.
     * 
     * @throws util.CalculatorException
     */
    protected void calculate() throws CalculatorException {
        /**
         * Se não há itens agregrados à esta fração, logo a fração é composta
         * por um único termo, ou seja, ela mesma
         */
        if ( this.fracs.isEmpty() ){
            this.result = this;
        }
        
        /**
         * As partes que compõem o cálculo, são solucionados em pares e vão sendo
         * diluídos até a obtenção de apenas um item que é o resultado final
         */
        ArrayList<Fraction> fracParts = new ArrayList<Fraction>();
        
        fracParts.add(this);
        fracParts.addAll(fracs);
        
        while( fracParts.size() > 1 ) {
            /**
             * Nesta iteração, efetuamos os cálculos de divisão.
             */
            for (int i = 1; i < fracParts.size(); i++) {
                Fraction curFrac = fracParts.get(i);
                if(curFrac.getOperador().equals(operator.DIVISION.getSign())) {
                    Fraction prevFrac = fracParts.get(i-1);
                    String newSign = i == 1 ? "" : prevFrac.getOperador();
                    fracParts.set(i, prevFrac.dividir(curFrac , newSign ));
                    fracParts.remove(prevFrac);
                    if( i < fracParts.size() ) i = 0;
                }           
            }

            /**
             * Nesta segunda iteração, efetuamos os cálculos de multiplicação.
             */
            for (int i = 1; i < fracParts.size(); i++) {
                Fraction curFrac = fracParts.get(i);
                if(curFrac.getOperador().equals(operator.MULTIPLICATION.getSign())) {
                    Fraction prevFrac = fracParts.get(i-1);
                    String newSign = i == 1 ? "" : prevFrac.getOperador();
                    fracParts.set(i, prevFrac.multiplicar(curFrac , newSign ));
                    fracParts.remove(prevFrac);
                    if( i < fracParts.size() ) i = 0;
                }
            }

            /**
             * Nesta terceira iteração, efetuamos os cálculos de soma  e subtração
             * na ordem como os termos foram constituídos.
             */
            for (int i = 1; i < fracParts.size(); i++) {
                Fraction curFrac = fracParts.get(i);
                Fraction prevFrac = fracParts.get(i-1);

                String newSign = i == 1 ? "" : prevFrac.getOperador();

                Fraction fracOut ;
                if( curFrac.getOperador().equals(operator.ADDITION.getSign())) {
                    fracOut = prevFrac.somar(curFrac, newSign);
                } else {
                    fracOut = prevFrac.subtrair(curFrac, newSign);
                }

                fracOut.setOperador(newSign);
                fracParts.set(i, fracOut);
                fracParts.remove(prevFrac);
            }
        }
         
        this.result = fracParts.get(0);
    }
    
    /**
    * Efetua a soma desta fração com a fração informada no parâmetro
    * 
    * @param frac          Fração a ser somada
    * @param newOperator   Operador a ser atribuído à fração resultante do cálculo
    * 
    * @return Fraction
    * @throws util.CalculatorException
    */
    public Fraction somar( Fraction frac , String newOperator ) throws CalculatorException{
        int newDenominador = this.MMC(this.getDenominador(), frac.getDenominador());
        int num1 = newDenominador/this.getDenominador() * this.getNumerador();
        int num2 = newDenominador/frac.getDenominador() * frac.getNumerador();
        int newNumerador = num1 + num2;
        
        return new Fraction(newNumerador, newDenominador, newOperator);
    }
    
    /**
    * Efetua a subtração desta fração com a fração informada no parâmetro
    * 
    * @param frac           Fração a ser subtraída
    * @param newOperator    Operador a ser atribuído à fração resultante do cálculo
    * 
    * @return Fraction
    * @throws util.CalculatorException
    */
    public Fraction subtrair( Fraction frac, String newOperator ) throws CalculatorException{
        int newDenominador = this.MMC(this.getDenominador(), frac.getDenominador());
        int num1 = newDenominador/this.getDenominador() * this.getNumerador();
        int num2 = newDenominador/frac.getDenominador() * frac.getNumerador();
        int newNumerador = num1 - num2;
        
        return new Fraction(newNumerador, newDenominador, newOperator);
    }
    
    /**
    * Efetua a divisão desta fração com a fração informada no parâmetro
    * Para tanto, multiplicamos a primeira fração pelo inverso da segunda
    * 
    * @param frac           Fração a ser subtraída
    * @param newOperator    Operador a ser atribuído à fração resultante do cálculo
    * 
    * @return Fraction
    * @throws util.CalculatorException
    */
    public Fraction multiplicar( Fraction frac, String newOperator ) throws CalculatorException{
        int newNumerador = this.getNumerador() * frac.getNumerador();
        int newDenominador = this.getDenominador() * frac.getDenominador();
        return new Fraction(newNumerador, newDenominador, newOperator);
    }
    
    /**
    * Efetua a divisão desta fração com a fração informada no parâmetro
    * Para tanto, multiplicamos a primeira fração pelo inverso da segunda
    * 
    * @param frac           Fração a ser subtraída
    * @param newOperator    Operador a ser atribuído à fração resultante do cálculo
    * 
    * @return Fraction
    * @throws util.CalculatorException
    */
    public Fraction dividir( Fraction frac, String newOperator ) throws CalculatorException {
        int newNumerador = this.getNumerador() * frac.getDenominador();
        int newDenominador = this.getDenominador() * frac.getNumerador();
        return new Fraction(newNumerador, newDenominador, newOperator);
    }
        
    /**
     * Retorna os Tipos de Frações
     * 
     * @return ArrayList
     * @throws util.CalculatorException
     */
    public ArrayList<String> getTypes() throws CalculatorException{
        Fraction rst = this.getResult();
        
        if ( ! rst.types.isEmpty() || rst.getNumerador().equals(0) ) {
            return rst.types;
        }
        
        //Unitária: o numerador é igual a 1 e o denominador é um inteiro positivo.
        if ( rst.getNumerador() == 1 && rst.getDenominador() > 0 && rst.getDenominador() % 1 == 0 ) {
            rst.types.add("Unitária");
        }
        
        //Aparente: O numerador é múltiplo ao denominador
        if ( rst.getNumerador() % rst.getDenominador() == 0 ) {
            rst.types.add("Aparente") ;
        }
        
        //Equivalente: Mantêm a mesma proporção de outra fração
         if ( rst.isEquivalentType(rst.getNumerador(), rst.getDenominador()) ) {
            rst.types.add("Equivalente");
        }
        
        //Própria: O numerador é menor que o denominador
        if ( rst.getNumerador() < rst.getDenominador() ) {
            rst.types.add("Própria") ;
        }
        else {
            rst.types.add("Imprópria") ;
        }
        
        //Irredutível: o numerador e o denominador são primos entre si, não permitindo simplificação
        if ( rst.isPrimeNumbers(rst.getNumerador(), rst.getDenominador()) ) {
            rst.types.add("Irredutível");
        }
        
        //Decimal: O denominador é uma potência de 10
        if ( rst.getDenominador().toString().startsWith("1") && rst.getDenominador() % 10 == 0 ) {
            rst.types.add("Decimal") ;
        }
        
        return rst.types;
    }
    
    /**
     * Verifica se os números informados são considerados equivalentes
     * 
     * @param a  int Número
     * @param b  int Número
     * 
     * @return 
     */
    private Boolean isEquivalentType(int a, int b){
        return this.MDC(a, b) > 1 || this.MDC(b, a) > 1  ;
    }
    
    /**
     * Verifica se os números informados são primos entre si
     * Dois números são primos entre si se o MDC deles for 1.
     * 
     * @param a  int Número
     * @param b  int Número
     * 
     * @return 
     */
    public Boolean isPrimeNumbers(int a, int b){
        return this.MDC(a, b) == 1 ;
    }
    
    /**
    * Calcula o MMC (Mínimo múltiplo comum) entre dois números
    * 
    * @param a  int Número
    * @param b  int Número
    * 
    * @return int
    */
    private int MMC(int a, int b) {
        int mmc = a;
        while ( mmc % a != 0 || mmc % b != 0 ) {
            mmc++;
        }

        return mmc;
    }
    
    /**
    * Calcula o MDC (Máximo divisor comum) entre dois números
    * 
    * @param a  Número
    * @param b  Número
    * 
    * @return int
    */
    private int MDC(int a, int b) {
        int mdc = a;
        while (a % mdc != 0 || b % mdc != 0 ) {
            mdc--;
        }

        return mdc;
    }
    
    /**
     * Retorna a fração de resultado correspondente
     * 
     * @return Fraction
     * @throws util.CalculatorException
     */
    public Fraction getResult() throws CalculatorException {
        if ( result == null && valid == null ) {
            this.calculate();
        }
        
        return result;
    }
    
    /**
     * Retorna a fração simplificada do resultado
     * Para tanto, basta dividi-los pelo máximo divisor comum (MDC) entre eles, 
     * obtendo-se uma fração que, além de manter a proporção da original, 
     * é do tipo irredutível:
     * 
     * @return Fraction
     * @throws util.CalculatorException
     */
    public Fraction getSimplifiedResult() throws CalculatorException {
        if ( result == null ) {
            this.calculate();
        }
        
        // Se o resultado é zero, não há motivo para fazer as demais verificações
        if ( result.getNumerador().equals(0) ){
            return result;
        }
        
        // Uma fração pode ser simplificada quando numerador e denominador não 
        // são primos entre si
        if( this.isPrimeNumbers(result.getNumerador(), result.getDenominador())){
            return result;
        }
        
        int mdc = this.MDC(result.denominador, result.getNumerador());
        int newNumerador = result.getNumerador()/mdc;
        int newDenominador = result.getDenominador()/mdc;
        return new Fraction(newNumerador, newDenominador);
    }
    
    /**
     * Retorna a fração resultante do cálculo em formato MathTeX amigável
     * Ex: 1/3
     * 
     * @return String
     */
    public String getPrettyMathResult() throws Exception{
       if ( this.getResult().getDenominador().equals(1) ) {
           return this.getResult().getNumerador().toString();
       }
       
       return String.format("%d/%d", this.getResult().getNumerador(), this.getResult().getDenominador());
    }
    
    /**
     * Retorna o resultado da fração em ponto flutuante em formato amigável
     * Exemplo: 0,333333333
     * 
     * @return String
     */
    public String getDecimalResult() throws Exception{
        return this.getRealResult().toString().replace(".", ",");
    }
    
    /**
     * Retorna o resultado da fração em ponto flutuante
     * 
     * @return Double
     */
    public Double getRealResult() throws Exception {
        BigDecimal bigNumerador = new BigDecimal(this.getResult().getNumerador().toString());
        BigDecimal bigDivisor = new BigDecimal(this.getResult().getDenominador().toString());
        return bigNumerador.divide(bigDivisor, 16, RoundingMode.HALF_UP).doubleValue();
    }
    
    /**
     * Retorna a expressão matemática com base neste objeto
     * 
     * @return String
     */
    public String getMathExpression(){
        String exp = String.format("%d/%d", this.getNumerador(), this.getDenominador());
        if ( this.getDenominador().equals(1) ) {
            exp = this.getNumerador().toString();
        }
        
        for ( Fraction frac : this.fracs ) {
            String aux = String.format("%d/%d", frac.getNumerador(), frac.getDenominador());
            if(frac.getDenominador().equals(1)){
                aux = frac.getNumerador().toString();
            }
            
            exp += String.format("%s %s", frac.getOperador(), aux);
        }
        
        return exp;
    }
}
