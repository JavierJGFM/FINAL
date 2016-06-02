package controlador;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream.GetField;
import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFrame;

import javax.swing.JFileChooser;

import modelo.*;
import vista.TableModelArte;
import vista.Vista;

public class Controlador {
	
	private final String[] CABECERA= {"ID", "MUSEO", "OBRA", "AUTOR","SIGLO"};
	private List<ObraArte> listaArte;
	private Vista vista;	
		
public Controlador() {
	vista= new Vista(); 
	listaArte= new ArrayList<>();
	
	rellenarDatos();
}

public void rellenarDatos(){	
	
vista.getMntmAbrirCsv().addActionListener(r->{	
	
	System.out.println("Funciona");
	JFileChooser jchooser= new JFileChooser();
	vista.setjFileChooserCSV(jchooser);
	int returnJChooser= jchooser.showOpenDialog(vista.getContentPane());
	if (returnJChooser==JFileChooser.APPROVE_OPTION){
		File file= new File(jchooser.getSelectedFile(),"");
		Scanner sc;
		try {
			sc = new Scanner(file);
			String cabecero = sc.nextLine(); 
			while(sc.hasNextLine()){ 
				System.out.println(sc.nextLine());
				String[] textoEnLineas = sc.nextLine().split(",");
			
				//System.out.println((textoEnLineas[0] + textoEnLineas[1]+ textoEnLineas[2]+ textoEnLineas[3]+textoEnLineas[4]));
				//listaArte.add(new ObraArte(textoEnLineas[0], textoEnLineas[1], textoEnLineas[2], textoEnLineas[3], textoEnLineas[4]));
			}
			System.out.println(listaArte);
		} catch (FileNotFoundException e) {	
			e.printStackTrace();
		}
		}		
});	


}



}










