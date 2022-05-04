package br.com.meli.dhprojetointegrador.utils;

/**
 * Classe de constantes utilizadas no projeto.
 */
public class Constantes {
	
	/**
	 * Constantes gerais do projeto.
	 */
	
	/** Constante que define o formato de data dd/MM/yyyy. */
	public static final String FORMATO_DATA_DD_MM_YYYY ="dd/MM/yyyy";
	
	public static final String DESCRICAO_CABECALHO_COLUNA_1 = "Rule";
	public static final String DESCRICAO_CABECALHO_COLUNA_2 = "Quantidade Ocorrências";
	public static final String DESCRICAO_CABECALHO_COLUNA_3 = "Data de Geração Relatório";
	public static final String DESCRICAO_CABECALHO_COLUNA_4 = "Severidade";
	
	
	
	/**
	 * Constantes com as configurações para conectar ao sonar e rodar o relatório.
	 */
	
	/**
	 *  Lista que define quais as severidades serão analisadas.
	 *  Carregar com os possíveis valores. Pode colocar um ou mais de um
	 *  ("BLOCKER | CRITICAL | MAJOR | MINOR | INFO")
	 */
	public static final String[] LISTA_SEVERIDADES = { "BLOCKER", "CRITICAL", "MAJOR" };
	/** Constante que define o caminho que o arquivo xls será gerado. */
	public static final String CAMINHO_GERACAO_ARQUIVO = "/Usuários/davifernande/Mesa/resultsSonar";
	/** Constante que define o nome do arquivo. */
	public static final String NOME_ARQUIVO = "RelatorioSonar.xls";
	/** Constante que define a url do sonar. */
	public static final String URL_SONAR = "http://127.0.0.1:9000/";
	/** Constante que define o usuário de ADM do sonar. */
	public static final String USUARIO_ADM_SONAR = "admin";
	/** Constante que define a senha de ADM do sonar. */
	public static final String SENHA_ADM_SONAR = "agmtech100r";
	
	public static final String LABEL_SEVERITIES = "severities"; //Não mexer

}
