package controlador;

import java.io.File;
import java.io.FileNotFoundException;
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
	Vista v= new Vista(); 
}

/*public void rellenarDatos(){
	
	
vista.getMntmAbrirCsv().addActionListener(r->{
		
		jFileChooserCSV= new JFileChooser();
		int returnJChooser= jFileChooserCSV.showOpenDialog(vista.getContentPane());
		if (returnJChooser==JFileChooser.APPROVE_OPTION){
		File file= new File(jFileChooserCSV.getSelectedFile(),"");
		Scanner sc;
		try {
			sc = new Scanner(file);
			String cabecero = sc.nextLine(); 
			while(sc.hasNextLine()){
				String[] textoEnLineas = sc.nextLine().split(",");
				listaArte.add(new ObraArte(textoEnLineas[0], textoEnLineas[1], textoEnLineas[2], textoEnLineas[3], textoEnLineas[4]));
			}
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		}		
});	
	

}
*/


}










