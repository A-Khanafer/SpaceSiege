package sandbox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

import interfaces.Obstacles;
import obstacles.Rectangle;

public class CreationFichierBinaireObjet {

	
	private static ArrayList<Obstacles>arbreCategories = new ArrayList<Obstacles>();
	
	public static void creationFichierBinaire(String niveau) {
		
		
		
		try {
			
		      File myObj = new File(niveau);
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {//Repete le processus tant qu'il y a une autre ligne
		        String data = myReader.nextLine();//data prend les caractere de la prochaine ligne
		        if(data.startsWith("*")) {// Lorsque la ligne commence avec un * cela signifie categorie
		        	data = data.replace("*","");
		        	if(data.equalsIgnoreCase("rec")) {
		        		arbreCategories.add(new Rectangle(	Double.parseDouble(myReader.nextLine()),
		        											Double.parseDouble(myReader.nextLine()),
		        											Double.parseDouble(myReader.nextLine()),
		        											Double.parseDouble(myReader.nextLine()),
		        											Double.parseDouble(myReader.nextLine())));
		        		
		        		
		        	}else if(data.equalsIgnoreCase("rec")) {
		        		
		        	}else if(data.equalsIgnoreCase("tri")) {
		        		
		        	}else if(data.equalsIgnoreCase("cer")) {
		        		
		        	}else if(data.equalsIgnoreCase("aim")) {
		        		
		        	}else{
		        		
		        	}
		        }
		        else if(data.startsWith("//")) { // Lorsque la ligne commence avec un // cela signifie que le fichier est fini
		        	break;
		        }
		        
		      }	
		      myReader.close();
		      System.out.println(arbreCategories.toString());
		      
//		      try {
//		          FileOutputStream fileOut = new FileOutputStream("binobj.dat");
//		          ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
//		          objectOut.writeObject(arbreCategories); //Ajoute l'arbre
//		          objectOut.close(); 
//		    	} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}

		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		

	}

}
	

