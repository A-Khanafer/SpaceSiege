package sandbox;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LireFichierTexte {

	public static void lireFichierTexte(String niveau) {	
		try {
		      File myObj = new File(niveau);
		      //Prend le fichier texte
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {//Repete le processus tant qu'il y a une autre ligne
		        String data = myReader.nextLine();//data prend les caractere de la prochaine ligne 
		        
		        
		        if(data.startsWith("*")) { // Lorsque la ligne commence avec un * cela signifie categorie
		        	data = data.replace("*", "");
		        	System.out.println("Forme : " + data);
		        }
		        else if(data.startsWith("//")) { // Lorsque la ligne commence avec un // cela signifie que le fichier est fini
		        	break;
		        }
		        else { // Sinon signifie des inventions
		        	System.out.println("( "+ data +" : CoinXgauche , " + myReader.nextLine() + " : CoinYgauche , " + myReader.nextLine() + " : largeur " + myReader.nextLine() + " : longueur " + myReader.nextLine() + " : angle)");
		        }
		      }	
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
	}
}
