package GUI;

import Logica.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class PanelMenu extends JPanel {

	private JLabel lblDescripcion;
	private JLabel lblNombreProceso;
	private JTextField textFieldNombre;
	private JLabel lblTLlegada;
	private JTextField textFieldTLlegada;
	private JLabel lblTRafaga;
	private JTextField textFieldTRafaga;
	private JButton btnAgregar;
	private JButton btnSimular;
	private int tiempoFinal = 0;
	private Cola cola1;
	private Cola temp;
	private String nom;
	private Proceso aux;
	private int cont;
	private int contBloq;
	private int tempito;

	public PanelMenu(JTable tabla, JTable diagrama) {
		this.setLayout(null);
		this.setBorder(new TitledBorder("Menu"));
		this.setBounds(5, 5, 205, 390);

		String texto = "<html><body> Por medio de este programa puede simular "
				+ " el tiempo de ejecucion de procesos. <br>" + " Acontinuacion puede ingresar mas procesos"
				+ " o realizar la Simulacion.  </body></html>";

		lblDescripcion = new JLabel();
		lblDescripcion.setFont(new Font("Gulim", Font.PLAIN, 12));
		lblDescripcion.setBounds(15, 15, 175, 100);
		lblDescripcion.setText(texto);
		add(lblDescripcion);

		lblNombreProceso = new JLabel("Nombre Proceso: ");
		lblNombreProceso.setFont(new Font("Gulim", Font.PLAIN, 12));
		lblNombreProceso.setBounds(15, 125, 120, 14);
		add(lblNombreProceso);

		textFieldNombre = new JTextField();
		textFieldNombre.setFont(new Font("Gulim", Font.PLAIN, 12));
		textFieldNombre.setBounds(15, 150, 120, 18);
		add(textFieldNombre);

		lblTLlegada = new JLabel("Tiempo de Llegada: ");
		lblTLlegada.setFont(new Font("Gulim", Font.PLAIN, 12));
		lblTLlegada.setBounds(15, 175, 120, 14);
		add(lblTLlegada);

		textFieldTLlegada = new JTextField();
		textFieldTLlegada.setFont(new Font("Gulim", Font.PLAIN, 12));
		textFieldTLlegada.setBounds(15, 200, 120, 18);
		add(textFieldTLlegada);

		lblTRafaga = new JLabel("Tiempo de Rafaga: ");
		lblTRafaga.setFont(new Font("Gulim", Font.PLAIN, 12));
		lblTRafaga.setBounds(15, 225, 120, 14);
		add(lblTRafaga);

		textFieldTRafaga = new JTextField();
		textFieldTRafaga.setFont(new Font("Gulim", Font.PLAIN, 12));
		textFieldTRafaga.setBounds(15, 250, 120, 18);
		add(textFieldTRafaga);

		cola1 = new Cola();
		temp = new Cola();
		cont = 0;
		contBloq = 0;
		tempito = 1;

		btnAgregar = new JButton("Agregar");
		btnAgregar.setFont(new Font("Gulim", Font.PLAIN, 12));
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (textFieldNombre.getText().equals("") || textFieldTLlegada.getText().equals("")
						|| textFieldTRafaga.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "ERROR. \n Por favor Ingrese todos los datos");
				} else {

					Agregar();

					temp.insertar(Integer.parseInt(textFieldTLlegada.getText()), cola1.getRaiz().getT_rafaga(),
							cola1.getRaiz().getT_comienzo(), cola1.getRaiz().getT_final(),
							cola1.getRaiz().getT_retorno(), cola1.getRaiz().getT_espera(),
							cola1.getRaiz().getBloqueado(), textFieldNombre.getText());

					int col = tabla.getModel().getColumnCount();

					Object[] fila = new Object[col];
					fila[0] = cola1.getRaiz().getNombre();
					fila[1] = cola1.getRaiz().getT_llegada();
					fila[2] = cola1.getRaiz().getT_rafaga();
					fila[3] = cola1.getRaiz().getT_comienzo();
					fila[4] = cola1.getRaiz().getT_final();
					fila[5] = cola1.getRaiz().getT_retorno();
					fila[6] = cola1.getRaiz().getT_espera();

					((DefaultTableModel) tabla.getModel()).addRow(fila);

					setTiempoFinal(cola1.getRaiz().getT_final());

					textFieldNombre.setText("");
					textFieldTLlegada.setText("");
					textFieldTRafaga.setText("");

					cola1.extraer();
					// JOptionPane.showMessageDialog(null, "Se han registrado los datos con exito");

					for (int i = 1; i < diagrama.getColumnCount(); i++) {
						diagrama.removeColumnSelectionInterval(0, i);
					}
					cont += 1;
				}
			}
		});
		btnAgregar.setBounds(15, 325, 120, 22);
		add(btnAgregar);

		btnSimular = new JButton("Simular");
		btnSimular.setFont(new Font("Gulim", Font.PLAIN, 12));
		btnSimular.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//borrarTabla(tabla);
				if (diagrama.getColumnCount() < 2) {
					// Actualiza valores de la tabla
					actualizar(tabla);
					editar(tabla, temp.getRaiz());
					// Creea las columnas de la tabla desde 0
					for (int i = 0; i < temp.getFondo().getT_final() + 1; i++) {
						((DefaultTableModel) diagrama.getModel()).addColumn("" + i);
					}

					Simulacion(diagrama);
					//SimulacionRC(diagrama);

				} else {
					// Actualiza valores de la tabla
					actualizar(tabla);
					editar(tabla, temp.getRaiz());
					// Creea las columnas de la tabla desde n-1

					int col = diagrama.getModel().getColumnCount();
					for (int i = col - 1; i < temp.getFondo().getT_final() + 1; i++) {
						((DefaultTableModel) diagrama.getModel()).addColumn("" + i);
					}
					
					// Actualizar
					Simulacion(diagrama);

					
				}
				// En caso de que haya bloqueados entra y dibuja los proceso que faltaron por
				// terminar
				if (!temp.vacia()) {
					int col = diagrama.getModel().getColumnCount();
					System.out.println("Columnasss: \t"+col);
					for (int i = 0; i < contBloq; i++) {
						Object[] fila = new Object[col];
						fila[0] = nom;
						// Dibuja lineas diferentes ya que es la terminacion de un proceso bloqueado
						for (int j = 1; j <= temp.getRaiz().getT_comienzo(); j++) {
							fila[j] = "||";
						}
						// Dibuja el restante del proceso
						for (int j = temp.getRaiz().getT_comienzo(); j < temp.getRaiz().getT_final(); j++) {
							fila[j+1] = "XX";
						}

						((DefaultTableModel) diagrama.getModel()).addRow(fila);

						temp.extraer();
					}
				}
				JOptionPane.showMessageDialog(null, "Se ha simulado correctamente.");
				cont = 0;

			}
		});
		btnSimular.setBounds(15, 350, 120, 22);
		add(btnSimular);

		this.setVisible(true);
	}

	public void actualizar(JTable tabla) {
		System.out.println("Opero el Round RObin");
		Proceso aux3 = temp.getRaiz();
		Proceso auxAntes = temp.getRaiz();
		int quantum = 4;
		String [] nombres = new String[50];
		int [] rafagas = new int[50]; 
		int [] esperas = new int[50]; 
		int dif =0;
		int anterior=0;
		int help=0;
		for (int i = 0; i < cont; i++) {
			
			if (aux3.getT_rafaga() > quantum) {
				System.out.println("|------RAFAGA + 4-----|"+(i+1));
				//(quantum * (cont + i)) - dif
				if(anterior<4) {
					aux3.setT_comienzo(dif);
				}else {
					aux3.setT_comienzo(auxAntes.getT_final());
				}
				
				nombres[help]=aux3.getNombre();	
				rafagas[help]=aux3.getT_rafaga()-quantum;
				aux3.setT_rafaga(quantum);
				
				aux3.setT_final(aux3.getT_comienzo() + aux3.getT_rafaga());
				aux3.setT_retorno(aux3.getT_final() - aux3.getT_llegada());
				aux3.setT_espera(aux3.getT_retorno() - aux3.getT_rafaga());
				
				esperas[help]=aux3.getT_final();
				anterior=aux3.getT_final();
				help+=1;
				auxAntes=aux3;
			} else {
				System.out.println("|------RAFAGA PQUE-----|");
				aux3.setT_comienzo(auxAntes.getT_final());
				if(i==0) {
					aux3.setT_comienzo(0);
				}
				aux3.setT_final(aux3.getT_comienzo() + aux3.getT_rafaga());
				aux3.setT_retorno(aux3.getT_final() - aux3.getT_llegada());
				aux3.setT_espera(aux3.getT_retorno() - aux3.getT_rafaga());
				dif =aux3.getT_final();
				anterior=aux3.getT_rafaga();
				auxAntes=aux3;
			}
			aux3 = aux3.getSiguiente();
			temp.imprimir();
		}
		for (int i = 0; i < help; i++) {
			//(quantum * (cont + i)) - dif
			System.out.println(nombres[i]);
			AddRR(rafagas[i],nombres[i],quantum,esperas[i]);
			agregarTabla(tabla, temp.getFondo());
			sumEsperas(nombres[i],help);
		}
		temp.imprimir();
		cont+=help;
		System.out.println(cont);
		Proceso repetir = temp.getRaiz();
		for (int i = 0; i < cont; i++) {
			System.out.println("ENTRE A RECOMPARAR AMIX \t"+repetir.getT_rafaga());
			if(repetir.getT_rafaga()>4) {
				actualizar(tabla);
			}
			repetir= repetir.getSiguiente();
		}	
	}
	
	public void sumEsperas(String nombre,int help) {
		int espera = 0;
		int contador=0;
		Proceso esp = temp.getRaiz();
		while(esp!=null){
			System.out.println("ENTRE A SUMAR ESPERAS \t"+nombre+"\t:"+esp.getNombre());
			if(esp.getNombre()==nombre) {
				System.out.println(esp.getT_espera());
				espera=espera+esp.getT_espera();
				contador+=1;
				if(contador>=3) {
					esp.setT_espera(espera);
				}
			}
			esp= esp.getSiguiente();
		}
	}
	
	public void AddRR(int rafaga,String nombre,int quantum,int espera) {
		temp.insertar(0, rafaga ,temp.getFondo().getT_final(),0,
				0,0,0, nombre);
		temp.getFondo().setT_final(temp.getFondo().getT_comienzo() + rafaga);
		temp.getFondo().setT_retorno(temp.getFondo().getT_final());
		temp.getFondo().setT_espera(temp.getFondo().getT_comienzo() - espera);
	}

	public void Agregar() {
		// Random de procesos para que se bloquee
		int bloq = (int) ((Math.random() * 1) + 1);
		if (cont==1) {
			bloq=5;
		}
		// Inserto datos a la cola
		cola1.insertar(Integer.parseInt(textFieldTLlegada.getText()), Integer.parseInt(textFieldTRafaga.getText()),
				getTiempoFinal(), 0, 0, 0, bloq, textFieldNombre.getText());
		cola1.getRaiz().setT_final(cola1.getRaiz().getT_comienzo() + cola1.getRaiz().getT_rafaga());
		cola1.getRaiz().setT_retorno(cola1.getRaiz().getT_final() - cola1.getRaiz().getT_llegada());
		cola1.getRaiz().setT_espera(cola1.getRaiz().getT_retorno() - cola1.getRaiz().getT_rafaga());
		// caso en que se bloquee
		if (bloq % 5 == 0) {
			contBloq += 1;
		}
	}
	
	public void borrarTabla(JTable tabla) {

		
		for(int i=0;i<cont;i++) {
			tabla.setValueAt("Hola", i, 0);
			aux=aux.getSiguiente();
		}
		
	}

	public void editar(JTable tabla, Proceso cola) {
		Proceso auxT =  cola;
		int i = 0;
		int col = tabla.getModel().getColumnCount();
		Object[] fila = new Object[col];
		while (auxT != null) {
			if (i < cont) {
				tabla.setValueAt(auxT.getNombre(), i, 0);
				tabla.setValueAt(auxT.getT_llegada(), i, 1);
				tabla.setValueAt(auxT.getT_rafaga(), i, 2);
				tabla.setValueAt(auxT.getT_comienzo(), i, 3);
				tabla.setValueAt(auxT.getT_final(), i, 4);
				tabla.setValueAt(auxT.getT_retorno(), i, 5);
				tabla.setValueAt(auxT.getT_espera(), i, 6);
				i++;
			} else {
				fila[0] = auxT.getNombre();
				fila[1] = auxT.getT_llegada();
				fila[2] = auxT.getT_rafaga();
				fila[3] = auxT.getT_comienzo();
				fila[4] = auxT.getT_final();
				fila[5] = auxT.getT_retorno();
				fila[6] = auxT.getT_espera();

				((DefaultTableModel) tabla.getModel()).addRow(fila);
			}

			auxT = auxT.getSiguiente();
		}

	}
	
	public void agregarTabla(JTable tabla, Proceso cola) {
		Proceso auxT =  cola;
		int col = tabla.getModel().getColumnCount();
		Object[] fila = new Object[col];
		while (auxT != null) {
				fila[0] = auxT.getNombre();
				fila[1] = auxT.getT_llegada();
				fila[2] = auxT.getT_rafaga();
				fila[3] = auxT.getT_comienzo();
				fila[4] = auxT.getT_final();
				fila[5] = auxT.getT_retorno();
				fila[6] = auxT.getT_espera();
				((DefaultTableModel) tabla.getModel()).addRow(fila);
			auxT = auxT.getSiguiente();
		}

	}
	
	public void Simulacion(JTable diagrama){
		for (int i = 0; i < cont; i++) {

			int col = diagrama.getModel().getColumnCount();
			Object[] fila = new Object[col];
			fila[0] = temp.getRaiz().getNombre();
			// Dibuja desde el tiempo de llegada hasta antes de comenzar
			for (int j = temp.getRaiz().getT_llegada(); j < temp.getRaiz().getT_comienzo(); j++) {
				fila[j + 1] = "--";
			}
			
			// Verificacion si el sistema esta bloqueado
			if (temp.getRaiz().getBloqueado() % 5 == 0 && tempito == 1) {
				if(temp.getRaiz().getSiguiente()!=null) {
					bloqueo(fila,diagrama,temp.getRaiz());	
				}
			} else {
				// En caso de que no se bloquee se dibuja el proceso normal
				for (int j = temp.getRaiz().getT_comienzo(); j < temp.getRaiz().getT_final(); j++) {
					fila[j + 1] = "XX";
				}
				temp.extraer();
				// Añade la fila a la tabla
				
			}
			((DefaultTableModel) diagrama.getModel()).addRow(fila);
			diagrama.setVisible(false);
			diagrama.setVisible(true);
		}
	}	
	
	public void bloqueo(Object[] fila,JTable diagrama,Proceso cola) {
		tempito = 0;
		int diferencia = cola.getT_rafaga()/2;
		System.out.println("Estoy bloqueando a "+cola.getNombre());
		// Dibuja proceso hasta la mitad por el bloqueo
		cola.setT_final(cola.getT_final()-diferencia);
		for (int j = cola.getT_comienzo(); j <cola.getT_final(); j++) {
			fila[j + 1] = "XX";
		}
		temp.insertar(cola.getT_llegada(), cola.getT_rafaga()-diferencia,
				temp.getFondo().getT_final(),0,0, 0,cola.getBloqueado(), cola.getNombre());
		temp.getFondo().setT_final(temp.getFondo().getT_comienzo() + diferencia);
		temp.getFondo().setT_retorno(temp.getFondo().getT_final());
		temp.getFondo().setT_espera(temp.getFondo().getT_comienzo() - cola.getT_final());
		
		Proceso aux = cola.getSiguiente();
		while(aux!=null){
			aux.setT_comienzo(aux.getT_comienzo()-diferencia);
			aux.setT_final(aux.getT_final()-diferencia);
			aux.setT_retorno(aux.getT_final());
			aux.setT_espera(aux.getT_retorno()-aux.getT_rafaga());
			aux=aux.getSiguiente();
		}
		//((DefaultTableModel) diagrama.getModel()).addRow(fila);
		
		temp.imprimir();
		temp.extraer();
	}
	
//	private void esperarXsegundos(int segundos) {
//		try {
//			Thread.sleep(segundos * 100);
//		} catch (InterruptedException ex) {
//			Thread.currentThread().interrupt();
//		}
//	}
//	


	public JLabel getLblDescripcion() {
		return lblDescripcion;
	}

	public void setLblDescripcion(JLabel lblDescripcion) {
		this.lblDescripcion = lblDescripcion;
	}

	public JLabel getLblNombreProceso() {
		return lblNombreProceso;
	}

	public void setLblNombreProceso(JLabel lblNombreProceso) {
		this.lblNombreProceso = lblNombreProceso;
	}

	public JTextField getTextFieldNombre() {
		return textFieldNombre;
	}

	public void setTextFieldNombre(JTextField textFieldNombre) {
		this.textFieldNombre = textFieldNombre;
	}

	public JLabel getLblTLlegada() {
		return lblTLlegada;
	}

	public void setLblTLlegada(JLabel lblTLlegada) {
		this.lblTLlegada = lblTLlegada;
	}

	public JTextField getTextFieldTLlegada() {
		return textFieldTLlegada;
	}

	public void setTextFieldTLlegada(JTextField textFieldTLlegada) {
		this.textFieldTLlegada = textFieldTLlegada;
	}

	public JLabel getLblTRafaga() {
		return lblTRafaga;
	}

	public void setLblTRafaga(JLabel lblTRafaga) {
		this.lblTRafaga = lblTRafaga;
	}

	public JTextField getTextFieldTRafaga() {
		return textFieldTRafaga;
	}

	public void setTextFieldTRafaga(JTextField textFieldTRafaga) {
		this.textFieldTRafaga = textFieldTRafaga;
	}

	public JButton getBtnAgregar() {
		return btnAgregar;
	}

	public void setBtnAgregar(JButton btnAgregar) {
		this.btnAgregar = btnAgregar;
	}

	public JButton getBtnSimular() {
		return btnSimular;
	}

	public void setBtnSimular(JButton btnSimular) {
		this.btnSimular = btnSimular;
	}

	public int getTiempoFinal() {
		return tiempoFinal;
	}

	public void setTiempoFinal(int tiempoFinal) {
		this.tiempoFinal = tiempoFinal;
	}

	public Cola getCola1() {
		return cola1;
	}

	public void setCola1(Cola cola1) {
		this.cola1 = cola1;
	}

}
