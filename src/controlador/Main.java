package controlador;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import modelo.ObraArte;
import vista.*;


public class Main{

public static void main(String[] args) {
	java.awt.EventQueue.invokeLater(() -> {
		//new Vista().getFrame().setVisible(true);
		new Controlador();
	});
	
}

/*public static void rellenarConScanner(){
	
	File file= new File("Datos/ARTE.csv");
	try(Scanner sc= new Scanner(file);) {
		String cabecero = sc.nextLine(); 
		while(sc.hasNextLine()){
			String[] textoEnLineas = sc.nextLine().split(",");
			listaArte.add(new ObraArte(textoEnLineas[0], textoEnLineas[1], textoEnLineas[2], textoEnLineas[4], textoEnLineas[3]));
		}
		System.out.println(listaArte);
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
*/
}



