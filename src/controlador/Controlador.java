package controlador;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.lang.reflect.GenericArrayType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.text.StyledEditorKit.ForegroundAction;

import conexion.Conexion;
import excepciones.SQLExceptionMe;
import jdbc.Actualizar;
import jdbc.BorrarDatos;
import jdbc.CrearTablaHistorial;
import jdbc.CrearTablas;
import jdbc.CrearTriggers;
import jdbc.CrearVista;
import jdbc.InsertarDatos;
import jdbc.SeleccionarDatos;
import jdbc.SeleccionarDatosTablaHistorial;
import jdbc.SeleccionarDatosVista;
import conexion.Conexion;

import javax.swing.JFileChooser;

import modelo.*;
import pdf.GenerarPDF;
import vista.TableModelArte;
import vista.TableModelArteVista;
import vista.TableModelTableHistorial;
import vista.Vista;

/**
 * 
 * @author JGFM
 *
 */
public class Controlador {
	
	private final static  String[] CABECERA= {"ID", "MUSEO", "OBRA", "AUTOR","SIGLO"};
	private static List<ObraArte> listaArte;
	private static List<ObjetoHistorial> listaHistorial;
	private static Vista vista;	
	private static Connection con = Conexion.getConexion();
	private static TableModelArte tableModelArte;
	private static TableModelArteVista tableModelArteVista;
	private static TableModelTableHistorial modelTableHistorial;
	private ObraArteDAOImpSQlite obraArteDAO;
	

public Controlador() {
	vista= new Vista(); 
	rellenarDatos();
	borrarTablaCompleta();
	actualizarTabla();
	insertarObra();
	borrarDatos();
	eventoAnterior();
	eventoSiguiente();
	generarPDF();
	limpiarCampos();
	comprobarCampos();
	actualizarRegistro();
	cerrarPrograma();
	activarVista();
	historialTrigger();
}
/**
 * 
 */

		//==================================CARGANDO DATOS: DE CSV A BBDD========================================//

public void rellenarDatos(){	
	//======================================ELEGIMOS MEDIANTE FILECHOOSER EL CSV==============================================//
	vista.getMntmAbrirCsv().addActionListener(r->{	
	JFileChooser jchooser= new JFileChooser();
	vista.setjFileChooserCSV(jchooser);
	listaArte= new ArrayList<ObraArte>();
	//==================================CREAMOS LAS TABLAS EN LA BASE DE DATOS SQLite========================================//
	CrearTablaHistorial.crearTablaObraArte(con);
	CrearTablas.crearTablaObraArte(con);
	CrearTriggers.createTrigger(con);
	int returnJChooser= jchooser.showOpenDialog(vista.getContentPane());
	if (returnJChooser==JFileChooser.APPROVE_OPTION){
		File file= new File(jchooser.getSelectedFile(),"");
		Scanner sc;
		
		try {
			sc = new Scanner(file);
			String cabecero = sc.nextLine(); 
			while(sc.hasNextLine()){ 
				String[] textoEnLineas = sc.nextLine().split(",");
				listaArte.add(new ObraArte(textoEnLineas[0], textoEnLineas[1], textoEnLineas[2], textoEnLineas[4], textoEnLineas[3]));
			}			
		} catch (FileNotFoundException | NoSuchElementException o) {	
			JOptionPane.showMessageDialog(vista.getFrame(), "Archivo de datos incorrecto", "Error de lectura", JOptionPane.ERROR_MESSAGE);
			
		}
		
		
		//====================CARGAMOS LOS DATOS EN LA TABLA Y BORRAMOS LA LISTA, CARGANDO LA DE LA BD=================//
		
		InsertarDatos.addListaRegistroArte(con, listaArte);
		listaArte.removeAll(listaArte);
		listaArte=SeleccionarDatos.getTodosRegistros(con);
		vista.getTable().setModel(new TableModelArte(listaArte, CABECERA));
		
		vista.getLabelSize().setText(vista.getTable().getRowCount()+" elementos.");
		vista.getTxtBarraStatus().setText("TABLA CREADA");
		vista.getButton_Modificar().setEnabled(true);
		vista.getButtonBorrar().setEnabled(true);
		vista.getButtonInsertarNuevo().setEnabled(true);
		vista.getMntmBorrarFila().setEnabled(true);
		//comprobarID();
		}
});			
}

				//==================================BORRANDO TABLA ENTERA ========================================//

public void borrarTablaCompleta() {
	vista.getBorrarTabla().addActionListener(r->{
		File bd= new File("Database.db");
		if(bd.exists() && bd.length()>10){
			BorrarDatos.borraTablaEntera(con);
			vista.getTxtBarraStatus().setText("TABLA ELIMINADA");
			tableModelArte=null;
			autoRefresh();
		}else{
			vista.getTxtBarraStatus().setText("NO HAY NINGUNA TABLA QUE ELIMINAR");
		}
	});
	}
	
			//==================================REFRESCANDO TABLAS========================================//

public void actualizarTabla(){
	vista.getRefrescarTabla().addActionListener(r->{
		listaArte=SeleccionarDatos.getTodosRegistros(con);
		vista.getTable().setModel(new TableModelArte(listaArte, CABECERA));
		vista.getLabelSize().setText(vista.getTable().getRowCount()+" elementos.");
		vista.getTxtBarraStatus().setText("TABLA ACTUALIZADA");
		vista.getButton_Modificar().setEnabled(true);
		vista.getButtonBorrar().setEnabled(true);
		vista.getButtonInsertarNuevo().setEnabled(true);
		vista.getMntmBorrarFila().setEnabled(true);
	});
	vista.getMntmCargarDatos().addActionListener(r->{
		listaArte=SeleccionarDatos.getTodosRegistros(con);
		vista.getTable().setModel(new TableModelArte(listaArte, CABECERA));
		vista.getLabelSize().setText(vista.getTable().getRowCount()+" elementos.");
		vista.getTxtBarraStatus().setText("TABLA ACTUALIZADA");
		vista.getButton_Modificar().setEnabled(true);
		vista.getButtonBorrar().setEnabled(true);
		vista.getButtonInsertarNuevo().setEnabled(true);
		vista.getMntmBorrarFila().setEnabled(true);
	});
}

		//==================================INSERTANDO DATOS========================================//

public void insertarObra(){
	vista.getButtonInsertarNuevo().addActionListener(r->{
		ObraArte a= (new ObraArte(vista.getTxtID().getText(), vista.getTxtMuseo().getText(), vista.getTxtAutor().getText(), vista.getTxtSiglo().getText(), vista.getTxtObra().getText()));
		if(a.getId().equals("") /*&& comprobarID()*/){
			vista.getTxtBarraStatus().setText("NO PUEDE AÑADIR REGISTROS CON CAMPOS VACIOS O IDS REPETIDOS ");
		}		
		else{
			obraArteDAO= new ObraArteDAOImpSQlite();
			obraArteDAO.insertarObraArte(con,a);
			vista.getLabelSize().setText(vista.getTable().getRowCount()+" elementos.");
			vista.getTxtBarraStatus().setText("REGISTRO AÑADIDO");
			autoRefresh();
		}
	});	
}
			//==================================BORRANDO REGISTRO========================================//

public void borrarDatos(){
	vista.getButtonBorrar().addActionListener(r->{
		if(vista.getTxtID().getText().equals("")){
			vista.getTxtBarraStatus().setText("ID INCORRECTO. ESCRIBA UN ID CORRECTO PARA BORRARLO");
		}
		else{
			obraArteDAO= new ObraArteDAOImpSQlite();
			obraArteDAO.borrarObraArte(con, (new ObraArte(vista.getTxtID().getText(), vista.getTxtMuseo().getText(), vista.getTxtAutor().getText(), vista.getTxtSiglo().getText(), vista.getTxtObra().getText())));
			vista.getLabelSize().setText(vista.getTable().getRowCount()+" elementos.");
			vista.getTxtBarraStatus().setText("REGISTRO BORRADO");
			
			autoRefresh();
			vista.getTable().repaint();
		}
	});
	vista.getMntmBorrarFila().addActionListener(r->{
		if(vista.getTxtID().getText().equals("")){
			vista.getTxtBarraStatus().setText("ID INCORRECTO. ESCRIBA UN ID CORRECTO PARA BORRARLO");
		}
		else{
			obraArteDAO= new ObraArteDAOImpSQlite();
			obraArteDAO.borrarObraArte(con, (new ObraArte(vista.getTxtID().getText(), vista.getTxtMuseo().getText(), vista.getTxtAutor().getText(), vista.getTxtSiglo().getText(), vista.getTxtObra().getText())));
			vista.getLabelSize().setText(vista.getTable().getRowCount()+" elementos.");
			vista.getTxtBarraStatus().setText("REGISTRO BORRADO");	
			autoRefresh();
			vista.getTable().repaint();
		}
	});
	
}

			//==================================ACTUALIZAR REGISTRO========================================//

public void actualizarRegistro(){
	vista.getButton_Modificar().addActionListener(r->{
		if(vista.getTxtID().getText().equals("")){
			vista.getTxtBarraStatus().setText("ESCRIBA EL ID QUE QUIERE MODIFICAR");
		}
		else{
			obraArteDAO= new ObraArteDAOImpSQlite();
			obraArteDAO.ModificarObraArte(con, (new ObraArte(vista.getTxtID().getText(), vista.getTxtMuseo().getText(), vista.getTxtAutor().getText(), vista.getTxtSiglo().getText(), vista.getTxtObra().getText())));;
			vista.getTxtBarraStatus().setText("REGISTRO MODIFICADO ");
			autoRefresh();
			vista.getTable().repaint();
		}
	});
}
			//=======================MÉTODOS AUXILIARES ESTÁTICOS ==================================//

		//autorefreca la tabla.

public static void autoRefresh(){
	tableModelArte=null;
	listaArte=SeleccionarDatos.getTodosRegistros(con);
	vista.getTable().setModel(new TableModelArte(listaArte, CABECERA));
	vista.getLabelSize().setText(vista.getTable().getRowCount()+" elementos.");
}

		//comprueba que el ID no exista a la hora de añadir un registro.
public static boolean comprobarID(){
	boolean condicion=false;
	for (ObraArte o2 : listaArte) {
		if(vista.getTxtID().getText().equals(o2.getId())){
			condicion=true;
		}
	}
	return condicion;
}

		//comprueba que los campos a añadir a la BD no estén vacios.
public static boolean comprobarCampos(){
	boolean condicion=false;
	if(vista.getTxtID().getText().equals(" ") && vista.getTxtMuseo().getText().equals(" ") && vista.getTxtAutor().getText().equals(" ") && vista.getTxtObra().getText().equals(" ") && vista.getTxtSiglo().getText().equals(" ")){
		condicion=true;
	}
	return condicion;
}

		//limpia los campos de texto y los deja con cadena vacia
public static void limpiarCampos(){
	vista.getTxtID().setText("");
	vista.getTxtMuseo().setText("");
	vista.getTxtAutor().setText("");
	vista.getTxtSiglo().setText("");
	vista.getTxtObra().setText("");
	
	vista.getMntmLimpiarCampos().addActionListener(r->{
		vista.getTxtID().setText("");
	vista.getTxtMuseo().setText("");
	vista.getTxtAutor().setText("");
	vista.getTxtSiglo().setText("");
	vista.getTxtObra().setText("");
	vista.getTxtBarraStatus().setText("CAMPOS LIMPIADOS - ");
	});
	
}


	//============================================EVENTOS ANTERIOR Y SIGUIENTE=====================================================//

public void eventoAnterior(){
	vista.getMntmRegistroAnterior().addActionListener(r->{
		
		if (vista.getTable() != null) {
			if (vista.getTable().getSelectedRow()>= 1)
				vista.getTable().setRowSelectionInterval(vista.getTable().getSelectedRow() -1, vista.getTable().getSelectedRow() -1);	
	}
	});
}	
public void eventoSiguiente(){
	vista.getMntmRegistroSiguiente().addActionListener(r -> {
		
		if(vista.getTable()!=null){
			if(vista.getTable().getSelectedRow()<listaArte.size()-1)
				vista.getTable().setRowSelectionInterval(vista.getTable().getSelectedRow() +1, vista.getTable().getSelectedRow()+1);
			
		}		
		});

}

	//============================================GENERADOR DE PDF=====================================================//

public void generarPDF(){
	vista.getMntmGenerarPDF().addActionListener(r->{
		JFileChooser ficheroPDF= new JFileChooser();
		vista.setJfFileChooserPDF(ficheroPDF);
	     int returnVal= ficheroPDF.showSaveDialog(vista.getContentPane());
	     if(returnVal == JFileChooser.APPROVE_OPTION){
	     GenerarPDF.CrearDocumentoPDF(ficheroPDF.getSelectedFile(), listaArte);
	    	 }
		});
}

//===========================================SALIR DEL PROGRAMA=====================================================//

	public void cerrarPrograma(){
		vista.getMntmGuardarComo().addActionListener(r->{
			System.exit(0);
		});
	}
	

	//===========================================CREAR Y VER VISTA=====================================================//
	
public void activarVista(){
	vista.getVerVistaAutorObra().addActionListener(r->{
		CrearVista.crearVIstaAutorObra(con);
		vista.getTable().removeAll();
		listaArte= new ArrayList<ObraArte>();
		listaArte.removeAll(listaArte);
		listaArte=SeleccionarDatosVista.getTodosRegistros(con);
		tableModelArteVista= new TableModelArteVista(listaArte, CABECERA);
		vista.getTable().setModel(tableModelArteVista);
		vista.getTxtBarraStatus().setText("VISTA CREADA. DICHA VISTA NO ES EDITABLE.");
		vista.getButton_Modificar().setEnabled(false);
		vista.getButtonBorrar().setEnabled(false);
		vista.getMntmBorrarFila().setEnabled(false);
		vista.getButtonInsertarNuevo().setEnabled(false);
		vista.getLabelSize().setText(vista.getTable().getRowCount()+" elementos.");
		
	});
}
	
//==========================================ACTIVANDO TRIGGER====================================================//

public void historialTrigger(){
	CrearTriggers.createTrigger(con);
	vista.getVerTablaHistorial().addActionListener(r->{
		CrearTriggers.createTrigger(con);
		vista.getTable().removeAll();
		listaHistorial= new ArrayList<ObjetoHistorial>();
		listaHistorial.removeAll(listaHistorial);
		listaHistorial=SeleccionarDatosTablaHistorial.getTodosRegistros(con);
		modelTableHistorial= new TableModelTableHistorial(listaHistorial, CABECERA);
		vista.getTable().setModel(modelTableHistorial);
		vista.getTxtBarraStatus().setText("HISTORIAL DE REGISTROS ELIMINADOS. NO EDITABLE.");
		vista.getButton_Modificar().setEnabled(false);
		vista.getButtonBorrar().setEnabled(false);
		vista.getMntmBorrarFila().setEnabled(false);
		vista.getButtonInsertarNuevo().setEnabled(false);
		vista.getLabelSize().setText(vista.getTable().getRowCount()+" elementos.");
	});
	
}


}

