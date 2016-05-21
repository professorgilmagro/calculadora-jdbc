/*
 * Classe para modelar um histórico de fração
 */
package model;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Histórico de fração
 * 
 * @author Grupo GRAR (Anne, Gilmar, Ricardo Boreto e Rodrigo Fernandes) <aiec.br>
 */
public final class Historic {
    private int Id;
    private String expressao;
    private String resultado;
    private String simplificado;
    private BigDecimal decimal;
    private String classificacao;
    private Date dataCriacao;
    private Date dataModificacao;

    public Historic(){
        
    }
    
    /**
     * Construtor que permite criar um objeto de histórico com base em uma
     * instância de uma Fração
     * 
     * @param frac 
     */
    public Historic( Fraction frac ) {
        BigDecimal bigDec;
        try {
            bigDec = new BigDecimal(frac.getRealResult());
            this.setExpressao(frac.getMathExpression());
            this.setResultado(frac.getPrettyMathResult());
            this.setDecimal(bigDec);
            this.setSimplificado(frac.getSimplifiedResult().getPrettyMathResult());
            this.setClassificacao(frac.getTypes().toString().replace("[", "").replace("]", ""));
        } catch (Exception ex) {
        }
    }
    
    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getExpressao() {
        return expressao;
    }

    public void setExpressao(String expressao) {
        this.expressao = expressao;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getSimplificado() {
        return simplificado;
    }

    public void setSimplificado(String simplificado) {
        this.simplificado = simplificado;
    }

    public BigDecimal getDecimal() {
        return decimal;
    }

    public void setDecimal(BigDecimal decimal) {
        this.decimal = decimal;
    }

    public String getClassificacao() {
        return this.classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }
    
    public String getDataCriacaoFormatada() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy 'às' HH:mm:ss");
        return format.format(dataCriacao);
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataModificacao() {
        return dataModificacao;
    }

    public void setDataModificacao(Date dataModificacao) {
        this.dataModificacao = dataModificacao;
    }
}
