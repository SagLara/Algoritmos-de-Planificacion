package GUI;

import Logica.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import GUIProcesosSO.PanelMenu;
import GUIProcesosSO.VentanaProcesos;

public class PanelMenuRR extends JPanel {

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
	private Cola tempRR;
	private String nom;
	private Proceso aux;
	private int cont;
	private int contBloq;
	private int tempito;
	private int iniciobl;
	private int limit;
	private PanelMenu vtFifo;

	public PanelMenuRR(JTable tabla, JTable diagrama) {
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

		textFieldTLlegada = new JTextField("0");
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
		tempRR = new Cola();
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
					// Funcion que agrega un nodo a la cola
					Agregar();
					// Se inserta un nodo a la cola temporal
					// Cola temporal manejara los datos para la simulacion
					tempRR.insertar(Integer.parseInt(textFieldTLlegada.getText()), cola1.getRaiz().getT_rafaga(),
							cola1.getRaiz().getT_comienzo(), cola1.getRaiz().getT_final(),
							cola1.getRaiz().getT_retorno(), cola1.getRaiz().getT_espera(),
							cola1.getRaiz().getBloqueado(), textFieldNombre.getText());

					agregarTabla(tabla, cola1.getRaiz());

					// Asigno tiempo final para guardar el anterior
					setTiempoFinal(cola1.getRaiz().getT_final());

					textFieldNombre.setText("");
					textFieldTLlegada.setText("0");
					textFieldTRafaga.setText("");
					// Extraigo de la cola que uso para asignar valores
					cola1.extraer();
					// JOptionPane.showMessageDialog(null, "Se han registrado los datos con exito");
					// Sumo contador para saber cuantos proces se agregaron
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

				if (diagrama.getColumnCount() < 2) {
					// Actualiza valores de la tabla
					actualizar(tabla);
					editar(tabla, tempRR.getRaiz());
					// Creea las columnas de la tabla desde 0
					for (int i = 0; i < tempRR.getFondo().getT_final() + 1; i++) {
						((DefaultTableModel) diagrama.getModel()).addColumn("" + i);
					}
					// Actualiza
					Simulacion(diagrama);
				} else {
					// Actualiza valores de la tabla
					actualizar(tabla);
					editar(tabla, tempRR.getRaiz());
					// Creea las columnas de la tabla desde n-1

					int col = diagrama.getModel().getColumnCount();
					for (int i = col - 1; i < tempRR.getFondo().getT_final() + 1; i++) {
						((DefaultTableModel) diagrama.getModel()).addColumn("" + i);
					}

					// Actualiza
					Simulacion(diagrama);

				}
				// En caso de que haya bloqueados entra y dibuja los proceso que faltaron por
				// terminar
				if (!tempRR.vacia()) {
					tempRR.setRaiz(tempRR.getFondo());
					SimulacionBloqueo(diagrama);
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
		Proceso aux3 = tempRR.getRaiz();
		Proceso auxAntes = tempRR.getRaiz();
		int quantum = 4;
		String[] nombres = new String[50];
		int[] rafagas = new int[50];
		int[] esperas = new int[50];
		int dif = 0;
		int anterior = 0;
		int help = 0;
		for (int i = 0; i < cont; i++) {

			if (aux3.getT_rafaga() > quantum) {
				// hice un print en vez del comentario jeje
				System.out.println("|------RAFAGA + 4-----|" + (i + 1));
				// (quantum * (cont + i)) - dif
				if (anterior < 4) {
					aux3.setT_comienzo(dif);
				} else {
					aux3.setT_comienzo(auxAntes.getT_final());
				}
				// Guardo nombres y rafagas de los procesos que se repetiran
				nombres[help] = aux3.getNombre();
				rafagas[help] = aux3.getT_rafaga() - quantum;
				// La rafaga del proceso cambia a ser el quantum
				aux3.setT_rafaga(quantum);
				// Se actualizan valores de tiempo final retorno y espera
				aux3.setT_final(aux3.getT_comienzo() + aux3.getT_rafaga());
				aux3.setT_retorno(aux3.getT_final() - aux3.getT_llegada());
				aux3.setT_espera(aux3.getT_retorno() - aux3.getT_rafaga());
				// Se agregan esperas a los proces que se repetiran
				esperas[help] = aux3.getT_final();
				// Se guarda el tiempo final del proceso anterior
				anterior = aux3.getT_final();
				// sumara procesos que tengan que repetirse
				help += 1;
				auxAntes = aux3;
			} else {
				// x2
				System.out.println("|------RAFAGA PQUE-----|");
				// se actualizan con los nuevos valores de los procesos
				aux3.setT_comienzo(auxAntes.getT_final());
				if (i == 0) {
					aux3.setT_comienzo(0);
				}
				aux3.setT_final(aux3.getT_comienzo() + aux3.getT_rafaga());
				aux3.setT_retorno(aux3.getT_final() - aux3.getT_llegada());
				aux3.setT_espera(aux3.getT_retorno() - aux3.getT_rafaga());
				// valor en que comenzara un proceso en caso de que el anterior no se haya
				// repetido
				dif = aux3.getT_final();
				anterior = aux3.getT_rafaga();
				auxAntes = aux3;
			}
			aux3 = aux3.getSiguiente();
			// Imprimo cambios de la cola
			tempRR.imprimir();
		}
		for (int i = 0; i < help; i++) {
			// Agrego los valores que re repitieron por la rafaga>quantum a la cola
			System.out.println(nombres[i]);
			AddRR(rafagas[i], nombres[i], quantum, esperas[i]);
			agregarTabla(tabla, tempRR.getFondo());
			sumEsperas(nombres[i], help);
		}
		tempRR.imprimir();
		// añado nuevos valores a contador global de procesos
		cont += help;
		System.out.println(cont);
		// revisa si en la cola algun proceso necesita repetirse mas
		Proceso repetir = tempRR.getRaiz();
		for (int i = 0; i < cont; i++) {
			System.out.println("ENTRE A RECOMPARAR AMIX \t" + repetir.getT_rafaga());
			if (repetir.getT_rafaga() > 4) {
				// si se tiene que repetir se llama a la funcion recursiva para que vuelva y
				// haga el proceso
				actualizar(tabla);
			}
			repetir = repetir.getSiguiente();
		}
	}

	public void sumEsperas(String nombre, int help) {
		int espera = 0;
		int contador = 0;
		Proceso esp = tempRR.getRaiz();
		// Suma las esperas de los procesos que se tuvieron que repetir mas de 1 vez
		while (esp != null) {
			System.out.println("ENTRE A SUMAR ESPERAS \t" + nombre + "\t:" + esp.getNombre());
			if (esp.getNombre() == nombre) {
				System.out.println(esp.getT_espera());
				espera = espera + esp.getT_espera();
				contador += 1;
				if (contador >= 3) {
					esp.setT_espera(espera);
				}
			}
			esp = esp.getSiguiente();
		}
	}

	// Agrega un nodo a la cola temporal para el algoritmo Round robin
	public void AddRR(int rafaga, String nombre, int quantum, int espera) {
		tempRR.insertar(0, rafaga, tempRR.getFondo().getT_final(), 0, 0, 0, 0, nombre);
		tempRR.getFondo().setT_final(tempRR.getFondo().getT_comienzo() + rafaga);
		tempRR.getFondo().setT_retorno(tempRR.getFondo().getT_final());
		tempRR.getFondo().setT_espera(tempRR.getFondo().getT_comienzo() - espera);
	}

	public void Agregar() {
		// Random de procesos para que se bloquee
		int bloq = (int) ((Math.random() * 1) + 1);
		if (cont == 1) {
			bloq = 5;
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
	// esto no se si funciona jeje

//	public void borrarTabla(JTable tabla) {
//
//		
//		for(int i=0;i<cont;i++) {
//			tabla.setValueAt("Hola", i, 0);
//			aux=aux.getSiguiente();
//		}
//		
//	}

	public void editar(JTable tabla, Proceso cola) {
		Proceso auxT = cola;
		int i = 0;
		int col = tabla.getModel().getColumnCount();
		Object[] fila = new Object[col];

		while (auxT != null) {
			if (i < cont) {
				// edito los valores que estaban en la cola
				tabla.setValueAt(auxT.getNombre(), i, 0);
				tabla.setValueAt(auxT.getT_llegada(), i, 1);
				tabla.setValueAt(auxT.getT_rafaga(), i, 2);
				tabla.setValueAt(auxT.getT_comienzo(), i, 3);
				tabla.setValueAt(auxT.getT_final(), i, 4);
				tabla.setValueAt(auxT.getT_retorno(), i, 5);
				tabla.setValueAt(auxT.getT_espera(), i, 6);
				i++;
			} else {
				// agrego valores que no estaban en la cola
				fila[0] = auxT.getNombre();
				fila[1] = auxT.getT_llegada();
				fila[2] = auxT.getT_rafaga();
				fila[3] = auxT.getT_comienzo();
				fila[4] = auxT.getT_final();
				fila[5] = auxT.getT_retorno();
				fila[6] = auxT.getT_espera();
				// agrego nueva fila
				((DefaultTableModel) tabla.getModel()).addRow(fila);
			}

			auxT = auxT.getSiguiente();
		}

	}

	// agrega n elementos de la cola que le pasen a la tabla
	public void agregarTabla(JTable tabla, Proceso cola) {
		Proceso auxT = cola;
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

	public void Simulacion(JTable diagrama) {
		for (int i = 0; i < cont; i++) {
			System.out.println(tempRR.getRaiz().getT_comienzo());
			if (tempRR.getRaiz().getT_final()< 20) {
				int col = diagrama.getModel().getColumnCount();
				Object[] fila = new Object[col];
				// Dibuja desde el tiempo de llegada hasta antes de comenzar
				for (int j = tempRR.getRaiz().getT_llegada(); j < tempRR.getRaiz().getT_comienzo(); j++) {
					fila[j + 1] = "--";
				}
				//limit+=temp.getRaiz().getT_comienzo();
				// Verificacion si el sistema esta bloqueado
				if (tempRR.getRaiz().getBloqueado() % 5 == 0 && tempito == 1) {
					// Si el proceso es el ultimo ya no se bloqueara
					if (tempRR.getRaiz().getSiguiente() != null) {
						// Muestro que sera un proceso bloqueado por el BQ
						fila[0] = tempRR.getRaiz().getNombre() + " BQ";
						bloqueo(fila, diagrama, tempRR.getRaiz());

					}
				} else {
					// En caso de que no se bloquee se dibuja el proceso normal
					fila[0] = tempRR.getRaiz().getNombre();
					for (int j = tempRR.getRaiz().getT_comienzo(); j < tempRR.getRaiz().getT_final(); j++) {
						fila[j + 1] = "XX";
					}
					tempRR.extraer();

					
					// Añade la fila a la tabla
				}
				//tempRR.extraer();
				((DefaultTableModel) diagrama.getModel()).addRow(fila);
			} else {
				System.out.println("Entre here");
				VentanaProcesos vt=new VentanaProcesos();
				vt.setVisible(true);
				//Para que se quede el bloqueadin
				tempRR.getFondo().setT_comienzo(tempRR.getRaiz().getT_comienzo());
				tempRR.getFondo().setT_final(tempRR.getFondo().getT_comienzo()+tempRR.getFondo().getT_rafaga());
				tempRR.imprimir();
				
				int com=0;
				int conti=0;
				Proceso auxito= tempRR.getRaiz();
				while(auxito!=tempRR.getFondo()) {
					vt.getPanelmenu().getTemp().insertar(auxito.getT_llegada(), auxito.getT_rafaga(),
							0, 0, 0, 0, auxito.getBloqueado(), auxito.getNombre());
					vt.getPanelmenu().getTemp().getFondo().setT_comienzo(com);
					vt.getPanelmenu().getTemp().getFondo().setT_final(com+auxito.getT_rafaga());
					com=vt.getPanelmenu().getTemp().getFondo().getT_final();
					vt.getPanelmenu().getTemp().getFondo().setT_retorno(com);
					vt.getPanelmenu().getTemp().getFondo().setT_retorno(com-auxito.getT_rafaga());
					auxito=auxito.getSiguiente();
					conti+=1;
				}
				vt.getPanelmenu().setCont(conti);
				System.out.println("Antes del break");
				vt.getPanelmenu().getTemp().imprimir();
				break;

			}
		}
		
	}

	public void SimulacionBloqueo(JTable diagrama) {
		int col = diagrama.getModel().getColumnCount();
		JOptionPane.showMessageDialog(null, "Se bloqueo el proceso: " + tempRR.getRaiz().getNombre());
		for (int i = 0; i < contBloq; i++) {
			Object[] fila = new Object[col];
			// Muestro que es el restante de un proceso bloqueado
			fila[0] = tempRR.getRaiz().getNombre() + " BQ";
			// Dibuja lineas diferentes ya que es la terminacion de un proceso bloqueado
			for (int j = iniciobl + 1; j <= tempRR.getRaiz().getT_comienzo(); j++) {
				fila[j] = "||";
			}
			// Dibuja el restante del proceso
			for (int j = tempRR.getRaiz().getT_comienzo(); j < tempRR.getRaiz().getT_final(); j++) {
				fila[j + 1] = "XX";
			}
			// Agrega la fila al diagrama de gant
			((DefaultTableModel) diagrama.getModel()).addRow(fila);

			// Se extrae de la cola
			tempRR.extraer();
		}
	}

	public void bloqueo(Object[] fila, JTable diagrama, Proceso cola) {
		tempito = 0;
		int diferencia = cola.getT_rafaga() / 2;
		System.out.println("Estoy bloqueando a " + cola.getNombre());
		// Dibuja proceso hasta la mitad por el bloqueo
		cola.setT_final(cola.getT_final() - diferencia);
		for (int j = cola.getT_comienzo(); j < cola.getT_final(); j++) {
			fila[j + 1] = "XX";
		}
		iniciobl = cola.getT_final();
		// inserta valor de rafaga restante al final de la cola
		tempRR.insertar(cola.getT_llegada(), cola.getT_rafaga() - diferencia, tempRR.getFondo().getT_final()-diferencia, 0, 0, 0,
				cola.getBloqueado(), cola.getNombre());
		tempRR.getFondo().setT_final(tempRR.getFondo().getT_comienzo() + diferencia);
		tempRR.getFondo().setT_retorno(tempRR.getFondo().getT_final());
		tempRR.getFondo().setT_espera(tempRR.getFondo().getT_comienzo() - cola.getT_final());
		
		System.out.println(tempRR.getFondo().getT_rafaga());
		// Actualiza a la cola con los nuevos valores que tendran por el bloqueo
		Proceso aux = cola.getSiguiente();
		while (aux != tempRR.getFondo()) {
			aux.setT_comienzo(aux.getT_comienzo() - diferencia);
			aux.setT_final(aux.getT_final() - diferencia);
			aux.setT_retorno(aux.getT_final());
			aux.setT_espera(aux.getT_retorno() - aux.getT_rafaga());
			aux = aux.getSiguiente();
		}
		tempRR.imprimir();
		tempRR.extraer();
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

	public Cola getTempRR() {
		return tempRR;
	}

	public void setTempRR(Cola tempRR) {
		this.tempRR = tempRR;
	}

	

}