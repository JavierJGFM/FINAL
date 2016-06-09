package vista;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import modelo.*;
public class TableModelArte extends AbstractTableModel {

	private String[] CABECERA= {"ID", "MUSEO", "OBRA", "SIGLO","AUTOR"};
	private String[][] tabla;
	private List<String> campos;
	
	
	public TableModelArte(List<ObraArte> lista, String[] cabecera) {
		tabla= new String[lista.size()][lista.size()*cabecera.length];
		campos= new ArrayList<String>();
		CABECERA=this.CABECERA;
		
		for (int i = 0; i < lista.size(); i++) {
			ObraArte o= lista.get(i);
			
			for (int j = 0; j < cabecera.length; j++) {
				switch (j) {
				case 0:
					campos.add(o.getId());
					break;
				case 1:
					campos.add(o.getMuseo());
					break;
				case 2:
					campos.add(o.getAutor());
					break;
				case 3:
					campos.add(o.getObra());
					break;
				case 4:
					campos.add(o.getSiglo());
					break;
				default:
					break;
				}
				tabla[i][j]= campos.get(j); 
						
			}
			campos.clear();
		}		
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return CABECERA.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return tabla.length;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return tabla[arg0][arg1];
	}

	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return CABECERA[column];
	}

	
	
	public String[] getCABECERA() {
		return CABECERA;
	}

	public void setCABECERA(String[] cABECERA) {
		CABECERA = cABECERA;
	}

	public List<String> getCampos() {
		return campos;
	}

	public void setCampos(List<String> campos) {
		this.campos = campos;
	}
	
	
}
