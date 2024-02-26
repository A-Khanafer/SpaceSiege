package outils;
/**
 * 
 * Classe qui contient les équations nécéssaires pour calculer 
 * le volume d'eau dans le contenant à un temps précis et la concentration en sel 
 * dans le contenant, en g/litre, à un temps précis.
 *
 * @author Ahmad Khanafer
 *
 */
public class OutilsMath {
	
	/**
	 * Calculer et retourner le volume d'eau dans le contenant à un temps t précis.
	 * 
	 * @param v_0 Le volume d'eau initial dans le contenant, en litres.
	 * @param d Le débit d'entrée d'eau pure, en litre/minute.
	 * @param t Le temps en minute.
	 * @return Le volume d'eau dans le contenant à un temps précis.
	 */
	//AUTEUR : Ahmad Khanafer
	public static double volumeEau(int v_0 , double d , double t) {
		return v_0 + d*t ; 
	}
	/**
	 * Calculer et retourner la concentration en sel, en g/Litre à un temps t précis.
	 * 
	 * @param q La quantité de sel dans le contenant, en grammes.
	 * @param v_T Le volume d'eau dans le contenant au temps t.
	 * @return La concentration de sel dans le contenant.
	 */
	//AUTEUR : Ahmad Khanafer
	 public static double concentrationSel(int q , double v_T ) {
		 return q/ v_T ;
	 }
	 
}

