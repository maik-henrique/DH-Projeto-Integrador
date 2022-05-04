package br.com.meli.dhprojetointegrador.entidades;

import java.io.Serializable;

/**
 * Class RuleProcessada.
 */

public class RuleProcessada implements Serializable {

    private static final long serialVersionUID = 1L;

    private String descricaoRule;
    private String keyRule;
    private int quantidadeOcorrencias;
    private String severidade;

	public String getDescricaoRule() {
		return descricaoRule;
	}

	public void setDescricaoRule(String descricaoRule) {
		this.descricaoRule = descricaoRule;
	}

	public String getKeyRule() {
		return keyRule;
	}

	public void setKeyRule(String keyRule) {
		this.keyRule = keyRule;
	}

	public int getQuantidadeOcorrencias() {
		return quantidadeOcorrencias;
	}

	public void setQuantidadeOcorrencias(int quantidadeOcorrencias) {
		this.quantidadeOcorrencias = quantidadeOcorrencias;
	}

	public String getSeveridade() {
		return severidade;
	}

	public void setSeveridade(String severidade) {
		this.severidade = severidade;
	}
	
	
	
}
