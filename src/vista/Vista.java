package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.GenericArrayType;
import java.nio.channels.Selector;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.Icon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.ScrollPaneConstants;
/**
 * 
 * @author JGFM
 *@version 0.0
 */

public class Vista{
	
	private JFrame frame;
	public JPanel contentPane;
	public JTable table;
	private JMenuBar menuBar;
	private JMenu mnArchivo;
	private JMenu mnEditar;
	private JMenu mnAcercaDe;
	private JMenuItem mntmInsertarNuevaFila;
	private JMenuItem mntmAbrirCsv;
	private JMenuItem mntmBorrarFila;
	private JButton Button_Modificar;
	private JButton ButtonInsertarNuevo;
	private JButton ButtonBorrar;
	private JMenuItem mntmLimpiarCampos;
	private JMenu mnSqlite;
	private JMenuItem mntmGuardarComo;
	private JMenu mnUtilidades;
	private JMenuItem mntmAbrirEnEl;
	private JMenuItem mntmAutor;
	private JScrollPane scroll;
	private JSplitPane splitPane;
    private JPanel panel;
	private JTextField txtID;
	private JTextField txtMuseo;
	private JTextField txtAutor;
	private JTextField txtObra;
	private JTextField txtSiglo;
	private JLabel txtBarraStatus= new JLabel("BIENVENIDO");
	private Component horizontalGlue;
	private JMenuItem mntmRegistroAnterior;
	private JMenuItem mntmRegistroSiguiente;
	private JLabel labelSize= new JLabel("  0 elementos.");
	private JMenuItem BorrarTabla;
	private JMenuItem mntmCargarDatos;
	private FileFilter filterCSV; 
	private JFileChooser jFileChooserCSV;
	
	public Vista() {
		
		inicializa();
		//frame.pack();		
	}
	 	 
	/**
	 * Create the frame.
	 */
	
	private void inicializa(){
		
		frame= new JFrame("REGISTROS ARTÍSTICOS"); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);	
		frame.add(contentPane);
		frame.setBounds(100, 100, 1000, 500);
		this.getFrame().setVisible(true);

		// ========================================= TABLE CENTER ===================================================== //
		
				table = new JTable();
				table.setFillsViewportHeight(true);
				table.setColumnSelectionAllowed(false);
				table.setRowSelectionAllowed(true);
				table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

				scroll = new JScrollPane(table);
				scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				
				table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent e) {
						txtID.setText(table.getValueAt(table.getSelectedRow(), 0) + "");
						txtAutor.setText(table.getValueAt(table.getSelectedRow(), 2) + "");
						txtObra.setText(table.getValueAt(table.getSelectedRow(), 3) + "");
						txtMuseo.setText(table.getValueAt(table.getSelectedRow(), 1) + "");
						txtSiglo.setText(table.getValueAt(table.getSelectedRow(), 4) + "");
						txtBarraStatus.setText("FILA SELECCIONADA");
					}
				});
					
		// ========================================= BARRA DE MENÚ-- NORTH ===================================================== //
		
				menuBar = new JMenuBar();
		contentPane.add(menuBar, BorderLayout.NORTH);
		mnArchivo = new JMenu("Archivo");
		mnArchivo.setMnemonic('A');
		menuBar.add(mnArchivo);
		
		mntmAbrirCsv = new JMenuItem("Abrir CSV...");	
		mnArchivo.add(mntmAbrirCsv);
		
		mntmCargarDatos = new JMenuItem("Cargar datos");
		mnArchivo.add(mntmCargarDatos);
		
		mntmGuardarComo = new JMenuItem("Guardar como...");
		mnArchivo.add(mntmGuardarComo);
		
		mnEditar = new JMenu("Editar");
		mnEditar.setMnemonic('E');
		menuBar.add(mnEditar);
		
		mntmRegistroAnterior = new JMenuItem("Registro anterior");
		mntmRegistroSiguiente = new JMenuItem("Registro siguiente");
		
		mnEditar.add(mntmRegistroSiguiente);
		mnEditar.add(mntmRegistroAnterior);
		
		mntmLimpiarCampos = new JMenuItem("Limpiar campos...");
		mnEditar.add(mntmLimpiarCampos);
		
		mntmBorrarFila = new JMenuItem("Borrar fila");
		mnEditar.add(mntmBorrarFila);

		mnSqlite = new JMenu("SQLite");
		menuBar.add(mnSqlite);

		BorrarTabla = new JMenuItem("Borrar tabla actual");
		mnSqlite.add(BorrarTabla);

		mnUtilidades = new JMenu("Utilidades");
		menuBar.add(mnUtilidades);

		mntmAbrirEnEl = new JMenuItem("Abrir en el explorador...");
		mnUtilidades.add(mntmAbrirEnEl);

		mnAcercaDe = new JMenu("Acerca de...");
		mnAcercaDe.setMnemonic('R');
		menuBar.add(mnAcercaDe);
		mntmAutor = new JMenuItem("Autor");
		mntmAutor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane jpanel = new JOptionPane();
				jpanel.showMessageDialog(getPanel(),
						"Javier Gª Fdez-Medina. \n             1º DAM. \n       " + " Programación.");
			}
		});
		mnAcercaDe.add(mntmAutor);

		horizontalGlue = Box.createHorizontalGlue();
		menuBar.add(horizontalGlue);

		// ========================================= TEXTSFIELDS-- CENTER // ===================================================== //

		txtBarraStatus.setFont(new Font("Tahoma", Font.PLAIN, 9));
		menuBar.add(txtBarraStatus);

		// ========================================= SPLITPANE-- CENTER  ===================================================== //
		
		panel = new JPanel();
		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane.setResizeWeight(1);
		splitPane.setDividerLocation(1f);
		splitPane.setTopComponent(scroll);
		splitPane.setBottomComponent(panel);
		getContentPane().add(splitPane, BorderLayout.CENTER);

		// ========================================= TEXTSFIELDS-- CENTER // ===================================================== //
		
		txtID = new JTextField();
		panel.add(txtID);
		txtID.setColumns(10);

		txtMuseo = new JTextField();
		panel.add(txtMuseo);
		txtMuseo.setColumns(10);

		txtAutor = new JTextField();
		panel.add(txtAutor);
		txtAutor.setColumns(10);

		txtObra = new JTextField();
		panel.add(txtObra);
		txtObra.setColumns(10);

		txtSiglo = new JTextField();
		panel.add(txtSiglo);
		txtSiglo.setColumns(10);

		labelSize.setVerticalAlignment(SwingConstants.TOP);
		labelSize.setHorizontalAlignment(SwingConstants.RIGHT);

		// ========================================= BOTONES-- SPLITPANE CENTER ===================================================== //
		
		Button_Modificar = new JButton("Modificar");
		panel.add(Button_Modificar);

		ButtonInsertarNuevo = new JButton("Nueva Fila");
		panel.add(ButtonInsertarNuevo);

		ButtonBorrar = new JButton("Borrar");
		panel.add(ButtonBorrar);
		panel.add(labelSize);
		
		// ========================================= FILECHOOSER ===================================================== //
		
	

	}

	// ========================================= GETTERS Y SETTERS ===================================================== //	
	
	
	
	public JPanel getContentPane() {
		return contentPane;
	}

	public void setContentPane(JPanel contentPane) {
		this.contentPane = contentPane;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public void setMenuBar(JMenuBar menuBar) {
		this.menuBar = menuBar;
	}

	public JMenu getMnArchivo() {
		return mnArchivo;
	}

	public void setMnArchivo(JMenu mnArchivo) {
		this.mnArchivo = mnArchivo;
	}

	public JMenu getMnEditar() {
		return mnEditar;
	}

	public void setMnEditar(JMenu mnEditar) {
		this.mnEditar = mnEditar;
	}

	public JMenu getMnAcercaDe() {
		return mnAcercaDe;
	}

	public void setMnAcercaDe(JMenu mnAcercaDe) {
		this.mnAcercaDe = mnAcercaDe;
	}

	public JMenuItem getMntmInsertarNuevaFila() {
		return mntmInsertarNuevaFila;
	}

	public void setMntmInsertarNuevaFila(JMenuItem mntmInsertarNuevaFila) {
		this.mntmInsertarNuevaFila = mntmInsertarNuevaFila;
	}

	public JMenuItem getMntmAbrirCsv() {
		return mntmAbrirCsv;
	}
	

	public void setMntmAbrirCsv(JMenuItem mntmAbrirCsv) {
		this.mntmAbrirCsv = mntmAbrirCsv;
	}

	public JMenuItem getMntmBorrarFila() {
		return mntmBorrarFila;
	}

	public void setMntmBorrarFila(JMenuItem mntmBorrarFila) {
		this.mntmBorrarFila = mntmBorrarFila;
	}

	public JButton getButton_Modificar() {
		return Button_Modificar;
	}

	public void setButton_Modificar(JButton button_Modificar) {
		Button_Modificar = button_Modificar;
	}

	public JButton getButtonInsertarNuevo() {
		return ButtonInsertarNuevo;
	}

	public void setButtonInsertarNuevo(JButton buttonInsertarNuevo) {
		ButtonInsertarNuevo = buttonInsertarNuevo;
	}

	public JButton getButtonBorrar() {
		return ButtonBorrar;
	}

	public void setButtonBorrar(JButton buttonBorrar) {
		ButtonBorrar = buttonBorrar;
	}

	public JMenuItem getMntmLimpiarCampos() {
		return mntmLimpiarCampos;
	}

	public void setMntmLimpiarCampos(JMenuItem mntmLimpiarCampos) {
		this.mntmLimpiarCampos = mntmLimpiarCampos;
	}

	public JMenu getMnSqlite() {
		return mnSqlite;
	}

	public void setMnSqlite(JMenu mnSqlite) {
		this.mnSqlite = mnSqlite;
	}

	public JMenuItem getMntmGuardarComo() {
		return mntmGuardarComo;
	}

	public void setMntmGuardarComo(JMenuItem mntmGuardarComo) {
		this.mntmGuardarComo = mntmGuardarComo;
	}

	public JMenu getMnUtilidades() {
		return mnUtilidades;
	}

	public void setMnUtilidades(JMenu mnUtilidades) {
		this.mnUtilidades = mnUtilidades;
	}

	public JMenuItem getMntmAbrirEnEl() {
		return mntmAbrirEnEl;
	}

	public void setMntmAbrirEnEl(JMenuItem mntmAbrirEnEl) {
		this.mntmAbrirEnEl = mntmAbrirEnEl;
	}

	public JMenuItem getMntmAutor() {
		return mntmAutor;
	}

	public void setMntmAutor(JMenuItem mntmAutor) {
		this.mntmAutor = mntmAutor;
	}

	public FileFilter getFilterCSV() {
		return filterCSV;
	}

	public void setFilterCSV(FileFilter filterCSV) {
		this.filterCSV = filterCSV;
	}

	public JFileChooser getjFileChooserCSV() {
		return jFileChooserCSV;
	}

	public void setjFileChooserCSV(JFileChooser jFileChooserCSV) {
		this.jFileChooserCSV = jFileChooserCSV;
	}

	public JScrollPane getScroll() {
		return scroll;
	}

	public void setScroll(JScrollPane scroll) {
		this.scroll = scroll;
	}

	public JSplitPane getSplitPane() {
		return splitPane;
	}

	public void setSplitPane(JSplitPane splitPane) {
		this.splitPane = splitPane;
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	public JTextField getTxtID() {
		return txtID;
	}

	public void setTxtID(JTextField txtID) {
		this.txtID = txtID;
	}

	public JTextField getTxtMuseo() {
		return txtMuseo;
	}

	public void setTxtMuseo(JTextField txtMuseo) {
		this.txtMuseo = txtMuseo;
	}

	public JTextField getTxtAutor() {
		return txtAutor;
	}

	public void setTxtAutor(JTextField txtAutor) {
		this.txtAutor = txtAutor;
	}

	public JTextField getTxtObra() {
		return txtObra;
	}

	public void setTxtObra(JTextField txtObra) {
		this.txtObra = txtObra;
	}

	public JTextField getTxtSiglo() {
		return txtSiglo;
	}

	public void setTxtSiglo(JTextField txtSiglo) {
		this.txtSiglo = txtSiglo;
	}

	public JLabel getTxtBarraStatus() {
		return txtBarraStatus;
	}

	public void setTxtBarraStatus(JLabel txtBarraStatus) {
		this.txtBarraStatus = txtBarraStatus;
	}

	public Component getHorizontalGlue() {
		return horizontalGlue;
	}

	public void setHorizontalGlue(Component horizontalGlue) {
		this.horizontalGlue = horizontalGlue;
	}

	public JMenuItem getMntmRegistroAnterior() {
		return mntmRegistroAnterior;
	}

	public void setMntmRegistroAnterior(JMenuItem mntmRegistroAnterior) {
		this.mntmRegistroAnterior = mntmRegistroAnterior;
	}

	public JMenuItem getMntmRegistroSiguiente() {
		return mntmRegistroSiguiente;
	}

	public void setMntmRegistroSiguiente(JMenuItem mntmRegistroSiguiente) {
		this.mntmRegistroSiguiente = mntmRegistroSiguiente;
	}

	public JLabel getLabelSize() {
		return labelSize;
	}

	public void setLabelSize(JLabel labelSize) {
		this.labelSize = labelSize;
	}

	public JMenuItem getBorrarTabla() {
		return BorrarTabla;
	}

	public void setBorrarTabla(JMenuItem borrarTabla) {
		BorrarTabla = borrarTabla;
	}

	public JMenuItem getMntmCargarDatos() {
		return mntmCargarDatos;
	}

	public void setMntmCargarDatos(JMenuItem mntmCargarDatos) {
		this.mntmCargarDatos = mntmCargarDatos;
	}

	public JFrame getFrame() {
		return frame;
	}
	
	
	
}

	 
	 

	
	 


