package br.com.meli.dhprojetointegrador.processamento;

import br.com.meli.dhprojetointegrador.entidades.RuleProcessada;
import br.com.meli.dhprojetointegrador.utils.Constantes;
import br.com.meli.dhprojetointegrador.utils.Util;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.sonar.wsclient.SonarClient;
import org.sonar.wsclient.issue.Issue;
import org.sonar.wsclient.issue.IssueClient;
import org.sonar.wsclient.issue.IssueQuery;
import org.sonar.wsclient.issue.Issues;
import org.sonar.wsclient.rule.Rule;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Classe responsável por todo processamento do Sonar e Criação do arquivo Excel.
 */
public class Processamento {
	
    private SonarClient client;
	private IssueClient issueClient;
	private HSSFSheet sheet;
	private HSSFWorkbook workbook;

	/**
	 * Método principal do projeto. Faz toda a configuração necessária com o sonar e depois gera uma planilha excel.
	 */
	public void gerarArquivoSonar() {

		System.out.println("Setando Configuracoes Iniciais");
		configuracaoInicial();
		String filename = Constantes.CAMINHO_GERACAO_ARQUIVO + Constantes.NOME_ARQUIVO;

		try {
			List<RuleProcessada> listaRuleUnificada = new ArrayList<RuleProcessada>();
			workbook = new HSSFWorkbook();
			sheet = workbook.createSheet("Ocorrências");
			for (int i = 0; i < Constantes.LISTA_SEVERIDADES.length; i++) {

				System.out.println(
						"############# Severidade analisada: " + Constantes.LISTA_SEVERIDADES[i] + " ###########################");
				IssueQuery queryInformacoesPagina = IssueQuery.create();
				queryInformacoesPagina.urlParams().put(Constantes.LABEL_SEVERITIES, Constantes.LISTA_SEVERIDADES[i]);
				System.out.println("Obtendo informações iniciais");
				Issues informacoesIniciais = getIssues(queryInformacoesPagina);

				System.out.println("Obtendo dados do Sonar");
				System.out.println("Aguarde...");
				List<Issues> listIssues = obterDadosSonar(informacoesIniciais, Constantes.LISTA_SEVERIDADES[i]);

				System.out.println("Processando dados do sonar");
				List<RuleProcessada> listaRuleProcessada = processarListaRule(listIssues, informacoesIniciais,
						Constantes.LISTA_SEVERIDADES[i]);

				listaRuleUnificada.addAll(listaRuleProcessada);

			}
			System.out.println("Criando Planilha");
			createExcel(listaRuleUnificada, sheet);

			FileOutputStream fileOut = new FileOutputStream(filename);
			workbook.write(fileOut);
			fileOut.close();
			System.out.println("Arquivo Gerado com Sucesso!");

		} catch (Exception ex) {
			System.out.println(ex);

		}

	}

	/**
	 * Cria o arquivo excel.
	 * @param listRuleProcessada
	 * @param sheet
	 * @return
	 */
	private HSSFWorkbook createExcel(List<RuleProcessada> listRuleProcessada, HSSFSheet sheet) {

		Date dataGeracao = new Date();

		obterCabecalhoRelatorio(sheet);

		if(CollectionUtils.isNotEmpty(listRuleProcessada)) {
			int contador = 1;
			int total = 0;
			for (RuleProcessada ruleProcessada : listRuleProcessada) {
				
				HSSFRow row = sheet.createRow((short) contador++);
				row.createCell(0).setCellValue(ruleProcessada.getDescricaoRule());
				row.createCell(1).setCellValue(ruleProcessada.getQuantidadeOcorrencias());
				total += ruleProcessada.getQuantidadeOcorrencias();
				row.createCell(2).setCellValue(Util.formatarData(dataGeracao));
				row.createCell(3).setCellValue(ruleProcessada.getSeveridade());
			}
		}
		return workbook;
	}

	/**
	 * Obtém o cabeçalho do relatório gerado em Apache POI.
	 * @param sheet
	 */
	private void obterCabecalhoRelatorio(HSSFSheet sheet) {
		HSSFRow rowhead = sheet.createRow((short) 0);
		rowhead.createCell(0).setCellValue(Constantes.DESCRICAO_CABECALHO_COLUNA_1);
		rowhead.createCell(1).setCellValue(Constantes.DESCRICAO_CABECALHO_COLUNA_2);
		rowhead.createCell(2).setCellValue(Constantes.DESCRICAO_CABECALHO_COLUNA_3);
		rowhead.createCell(3).setCellValue(Constantes.DESCRICAO_CABECALHO_COLUNA_4);
	}

	/**
	 * 
	 * @param query
	 * @return
	 */
	private Issues getIssues(IssueQuery query) {
		Issues issues = issueClient.find(query);
		return issues;
	}

	/**
	 * 
	 * @param listIssues
	 * @param keyRule
	 * @return
	 */
	private String getNameRule(List<Issues> listIssues, String keyRule) {
		String name = "";
		for (Issues issueList : listIssues) {
			for (Rule rule : issueList.rules())
				if (rule.key().equalsIgnoreCase(keyRule)) {
					name = rule.name();
					break;
				}

		}
		return name;
	}

	/**
	 * 
	 * @param listIssues
	 * @param issues
	 * @param severidade
	 * @return
	 */
	private List<RuleProcessada> processarListaRule(List<Issues> listIssues, Issues issues, Object severidade) {

		HashMap<String, Integer> mapRuleProcessada = new HashMap<String, Integer>();

		List<RuleProcessada> listaRuleProcessada = new ArrayList<RuleProcessada>();

		mapRuleProcessada = getRules(listIssues, mapRuleProcessada);

		Integer contador = 1;
		for (Issues issueList : listIssues) {
			for (Issue issue : issueList.list()) {

				if (mapRuleProcessada.containsKey(issue.ruleKey())) {
					contador = mapRuleProcessada.get(issue.ruleKey());
					contador++;
					mapRuleProcessada.put(issue.ruleKey(), contador);
				}
			}

		}
		for (String key : mapRuleProcessada.keySet()) {
			RuleProcessada ruleProcessada = obterObjetoRuleProcessada(listIssues, severidade, mapRuleProcessada, key);
			listaRuleProcessada.add(ruleProcessada);
		}
		return listaRuleProcessada;
	}

	/**
	 * 
	 * @param listIssues
	 * @param severidade
	 * @param mapRuleProcessada
	 * @param key
	 * @return
	 */
	private RuleProcessada obterObjetoRuleProcessada(List<Issues> listIssues, Object severidade,
			HashMap<String, Integer> mapRuleProcessada, String key) {
		RuleProcessada ruleProcessada = new RuleProcessada();
		ruleProcessada.setQuantidadeOcorrencias(mapRuleProcessada.get(key));
		ruleProcessada.setDescricaoRule(getNameRule(listIssues, key));
		ruleProcessada.setKeyRule(key);
		ruleProcessada.setSeveridade(severidade.toString());
		return ruleProcessada;
	}

	/**
	 * Obtém dados do Sonar de todas as páginas.
	 * @param informacoesIniciais
	 * @param severidade
	 * @return
	 */
	private List<Issues> obterDadosSonar(Issues informacoesIniciais, String severidade) {

		List<Issues> listIssues = new ArrayList<Issues>();

		IssueQuery queryIssues = IssueQuery.create();
		queryIssues.urlParams().put(Constantes.LABEL_SEVERITIES, severidade);

		for (int i = 1; i <= informacoesIniciais.paging().pages(); i++) {
			queryIssues.urlParams().put("p", i);
			Issues issues = getIssues(queryIssues);
			listIssues.add(issues);
		}
		return listIssues;
	}
	
	/**
	 * Seta as configurações iniciais para conectar ao sonar.
	 */
	private void configuracaoInicial() {

		client = SonarClient.create(Constantes.URL_SONAR);
		client.builder().login(Constantes.USUARIO_ADM_SONAR);
		client.builder().password(Constantes.SENHA_ADM_SONAR);

		issueClient = client.issueClient();
	}

	/**
	 * 
	 * @param listIssues
	 * @param mapRuleProcessada
	 * @return
	 */
	private HashMap<String, Integer> getRules(List<Issues> listIssues,
			HashMap<String, Integer> mapRuleProcessada) {

		if(CollectionUtils.isNotEmpty(listIssues)) {
			for (Issues issueList : listIssues) {
				if(CollectionUtils.isNotEmpty(issueList.list())) {
					for (Issue issue : issueList.list()) {
						if (!mapRuleProcessada.containsKey(issue.ruleKey())) {
							mapRuleProcessada.put(issue.ruleKey(), 0);
						}
					}
				}
			}
		}
		return mapRuleProcessada;

	}

}
