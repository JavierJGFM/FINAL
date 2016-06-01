package controlador;

import vista.*;


public class Main{
public static void main(String[] args) {
	java.awt.EventQueue.invokeLater(() -> {
		new Vista().getFrame().setVisible(true);
	});
	
}
}

