package br.com.meli.dhprojetointegrador.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class Util.
 */
public class Util {

	/**
	 * Formata uma data no formato dd/MM/yyyy.
	 * @param date data a ser formatada
	 * @return uma String com a data formatada
	 */
	public static String formatarData(Date date) {
		String dataFormatada = "";
		if (date != null) {
			dataFormatada = new SimpleDateFormat(Constantes.FORMATO_DATA_DD_MM_YYYY).format(date);
		}
		return dataFormatada;
	}

}
