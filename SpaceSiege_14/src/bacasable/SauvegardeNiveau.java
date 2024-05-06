package bacasable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import obstacles.Cercle;
import obstacles.CercleElectrique;
import obstacles.Epines;
import obstacles.ObstacleHolder;
import obstacles.PlaqueRebondissante;
import obstacles.Rectangle;
import obstacles.Triangle;

/**
 * Cette classe fournit des méthodes pour lire et créer des fichiers de sauvegarde de niveau.
 */
public class SauvegardeNiveau {

    /**
     * Lit un fichier texte contenant les informations sur les obstacles d'un niveau.
     * 
     * @param niveau le chemin vers le fichier texte
     */
    public static void lireFichierTexte(String niveau) {
        try {
            File myObj = new File(niveau);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {//Repete le processus tant qu'il y a une autre ligne
                String data = myReader.nextLine();//data prend les caractere de la prochaine ligne 
                if (data.startsWith("*")) { 
                    data = data.replace("*", "");
                    System.out.println("Forme : " + data);
                } else if (data.startsWith("//")) { 
                    break;
                } else { 

                    System.out.println("( " + data + " : CoinXgauche , " + myReader.nextLine()

                            + " : CoinYgauche , " + myReader.nextLine() + " : largeur " + myReader.nextLine()

                            + " : longueur " + (myReader.nextLine()) + " : angle)");

                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    /**
     * Crée un fichier binaire contenant les informations sur les obstacles d'un niveau.
     * 
     * @param niveau le chemin vers le fichier texte contenant les informations sur les obstacles
     */
    public static void creationFichierBinaire(String niveau) {

        try {

            File myObj = new File(niveau);
            Scanner myReader = new Scanner(myObj);
            ObstacleHolder obstacleHolder = new ObstacleHolder();
            while (myReader.hasNextLine()) {//Repete le processus tant qu'il y a une autre ligne
                String data = myReader.nextLine();//data prend les caractere de la prochaine ligne
                if (data.startsWith("*")) {// Lorsque la ligne commence avec un * cela signifie une forme
                    data = data.replace("*", "");
                    if (data.equalsIgnoreCase("rec")) {
                        obstacleHolder.addObstacle(new Rectangle(Double.parseDouble(myReader.nextLine()),
                                Double.parseDouble(myReader.nextLine()), Double.parseDouble(myReader.nextLine()),
                                Double.parseDouble(myReader.nextLine()), Double.parseDouble(myReader.nextLine())));

                    } else if (data.equalsIgnoreCase("tri")) {
                        obstacleHolder.addObstacle(new Triangle(Double.parseDouble(myReader.nextLine()),
                                Double.parseDouble(myReader.nextLine()), Double.parseDouble(myReader.nextLine()),
                                Double.parseDouble(myReader.nextLine()), Double.parseDouble(myReader.nextLine())));
                    } else if (data.equalsIgnoreCase("cer")) {
                        obstacleHolder.addObstacle(new Cercle(Double.parseDouble(myReader.nextLine()),
                                Double.parseDouble(myReader.nextLine()), Double.parseDouble(myReader.nextLine()),
                                Double.parseDouble(myReader.nextLine()), Double.parseDouble(myReader.nextLine())));

                    } else if (data.equalsIgnoreCase("cerE")) {
                        obstacleHolder.addObstacle(new CercleElectrique(Double.parseDouble(myReader.nextLine()),
                                Double.parseDouble(myReader.nextLine()), Double.parseDouble(myReader.nextLine()),
                                Double.parseDouble(myReader.nextLine()), Double.parseDouble(myReader.nextLine())));

                    } else if (data.equalsIgnoreCase("epi")) {
                        obstacleHolder.addObstacle(new Epines(Double.parseDouble(myReader.nextLine()),
                                Double.parseDouble(myReader.nextLine()), Double.parseDouble(myReader.nextLine()),
                                Double.parseDouble(myReader.nextLine()), Double.parseDouble(myReader.nextLine())));
                    } else if (data.equalsIgnoreCase("pla")) {
                        obstacleHolder.addObstacle(new PlaqueRebondissante(Double.parseDouble(myReader.nextLine()),
                                Double.parseDouble(myReader.nextLine()), Double.parseDouble(myReader.nextLine()),
                                Double.parseDouble(myReader.nextLine()), Double.parseDouble(myReader.nextLine())));
                    } else {

                    }
                } else if (data.startsWith("//")) { // Lorsque la ligne commence avec un // cela signifie que le fichier est fini
                    break;
                }

            }
            myReader.close();
            System.out.println(obstacleHolder.toString());

            try {
                FileOutputStream fileOut = new FileOutputStream("binobj.dat");
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                objectOut.writeObject(obstacleHolder); // Ajoute l'arbre
                objectOut.close();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Lit un fichier binaire contenant les informations sur les obstacles d'un niveau.
     * 
     * @return le conteneur d'obstacles contenant les obstacles du niveau
     */
    public static ObstacleHolder lectureFichierBinaireObjet() {

        try {
            FileInputStream fis = new FileInputStream("binobj.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            ObstacleHolder obstacleHolder = (ObstacleHolder) ois.readObject();
            System.out.println(obstacleHolder.toString());
            ois.close();
            return obstacleHolder;
        } catch (ClassNotFoundException e) {
            System.out.println("Incohérence de classe" + e.toString());
        } catch (IOException e) {
            System.out.println("Erreur à la lecture!" + e.toString());
        }

        return null;
    }

}
