package GUIProcesoPR;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import LogicaPR.*;

public class PanelMenuPR extends JPanel {

	private JLabel lblDescripcion;
	private JLabel lblNombreProceso;
	private JTextField textFieldNombre;
	private JLabel lblTLlegada;
	private JTextField textFieldTLlegada;
	private JLabel lblTRafaga;
	private JTextField textFieldTRafaga;
	private JLabel lblPrioridad;
	private JTextField textFieldPrioridad;
	private JButton btnAgregar;
	private JButton btnSimular;
	private int tiempoFinal = 0;
	private Cola cola1;
	private Cola tempPR;
	private static int diferencia;
	private String nom;
	private Proceso aux;
	private int cont;
	private int contBloq;
	private int tempito;
	private int iniciobl;

	public PanelMenuPR(JTable tabla, JTable diagrama) {
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

		lblPrioridad = new JLabel("Prioridad: ");
		lblPrioridad.setFont(new Font("Gulim", Font.PLAIN, 12));
		lblPrioridad.setBounds(15, 275, 120, 14);
		add(lblPrioridad);

		textFieldPrioridad = new JTextField();
		textFieldPrioridad.setFont(new Font("Gulim", Font.PLAIN, 12));
		textFieldPrioridad.setBounds(15, 300, 120, 18);
		add(textFieldPrioridad);

		cola1 = new Cola();
		tempPR = new Cola();
		cont = 0;
		contBloq = 0;
		tempito = 1;

		btnAgregar = new JButton("Agregar");
		btnAgregar.setFont(new Font("Gulim", Font.PLAIN, 12));
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (textFieldNombre.getText().equals("") || textFieldTLlegada.getText().equals("")
						|| textFieldTRafaga.getText().equals("") || textFieldPrioridad.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "ERROR. \n Por favor Ingrese todos los datos");
				} else {
					// Funcion que agrega un nodo a la cola
					Agregar();
					// Se inserta un nodo a la cola temporal
					// Cola temporal manejara los datos para la simulacion
					tempPR.insertar(Integer.parseInt(textFieldTLlegada.getText()),
							Integer.parseInt(textFieldPrioridad.getText()), cola1.getRaiz().getT_rafaga(),
							cola1.getRaiz().getT_comienzo(), cola1.getRaiz().getT_final(),
							cola1.getRaiz().getT_retorno(), cola1.getRaiz().getT_espera(),
							cola1.getRaiz().getBloqueado(), textFieldNombre.getText());

					agregarTabla(tabla, cola1.getRaiz());

					// Asigno tiempo final para guardar el anterior
					setTiempoFinal(cola1.getRaiz().getT_final());

					textFieldNombre.setText("");
					textFieldTLlegada.setText("");
					textFieldTRafaga.setText("");
					textFieldPrioridad.setText("");
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
				
				agregarTabla(tabla, tempPR.getRaiz());
				System.out.println("Entre aqui?");
				if (diagrama.getRowCount() < 2) {
					for (int i = 0; i < tempPR.getFondo().getT_final() + 1; i++) {
						((DefaultTableModel) diagrama.getModel()).addColumn("" + i);
					}
					// Actualiza
					actualizarPrioridad(tabla);
					tempPR.imprimir();
					Simulacion(diagrama);
				} else {
					// Creea las columnas de la tabla desde n-1
					int col = diagrama.getModel().getColumnCount();
					for (int i = col - 1; i < tempPR.getFondo().getT_final() + 1; i++) {
						((DefaultTableModel) diagrama.getModel()).addColumn("" + i);
					}
					// Actualiza
					actualizarPrioridad(tabla);
					Simulacion(diagrama);
				}
				if (!tempPR.vacia()) {
					SimulacionBloqueo(diagrama);
				}
				JOptionPane.showMessageDialog(null, "Se ha simulado correctamente");
				cont = 0;
			}
		});
		btnSimular.setBounds(15, 350, 120, 22);
		add(btnSimular);

		this.setVisible(true);
	}
	public int llegadaMenor(){
		Proceso auxi = tempPR.getRaiz();
		int aux2 = 0;
		int llegadaMenor= auxi.getT_llegada();
		while(auxi!=tempPR.getFondo()) {
			aux2 = auxi.getSiguiente().getT_llegada();
			System.out.println(aux2);
			if(aux2<llegadaMenor) {
				llegadaMenor=aux2;
			}
			auxi=auxi.getSiguiente();
		}
		return llegadaMenor;
	}
	public void actualizarPrioridad(JTable tabla) {
		
		int aux2 = tempPR.getRaiz().getT_comienzo();
        int aux3;
        int i2 = maxPrioridad();        
        for (int i = 0; i < cont * i2; i++) {

            aux = tempPR.getRaiz();

            if (tempPR.getRaiz().getPrioridad() == 1) {
                //aux3=temp.getSiguiente().getT_final();
            	tempPR.imprimir();
            	tempPR.extraer();

            	editar(tabla, aux);

                tempPR.insertar(aux.getT_llegada(), 0,
                        aux.getT_rafaga(), aux2,
                        aux2 + aux.getT_rafaga(), aux.getT_retorno(),
                        aux.getT_espera(), aux.getBloqueado(),
                        aux.getNombre());

                aux3 = tempPR.getFondo().getT_final();
                aux2 = aux3;
            } else {
            	tempPR.insertar(aux.getT_llegada(), aux.getPrioridad() - 1,
                        aux.getT_rafaga(), aux.getT_comienzo(),
                        aux.getT_final(), aux.getT_retorno(),
                        aux.getT_espera(), aux.getBloqueado(),
                        aux.getNombre());
            	tempPR.extraer();
            }
        }
		
	}

	public int maxPrioridad() {
		int salida = 0;
		Proceso aux;
		aux = tempPR.getRaiz();
		System.out.println(cont);
		for (int i = 0; i < cont; i++) {
			if (aux.getPrioridad() > salida) {
				salida = aux.getPrioridad();
			}
			aux = aux.getSiguiente();
		}
		return salida;
	}

	public void Agregar() {
		// Random de procesos para que se bloquee
		int bloq = (int) ((Math.random() * 1) + 1);
		if (cont == 121) {
			bloq = 5;
		}
		// Inserto datos a la cola
		cola1.insertar(Integer.parseInt(textFieldTLlegada.getText()), Integer.parseInt(textFieldPrioridad.getText()),
				Integer.parseInt(textFieldTRafaga.getText()), getTiempoFinal(), 0, 0, 0, bloq,
				textFieldNombre.getText());
		cola1.getRaiz().setT_final(cola1.getRaiz().getT_comienzo() + cola1.getRaiz().getT_rafaga());
		cola1.getRaiz().setT_retorno(cola1.getRaiz().getT_final() - cola1.getRaiz().getT_llegada());
		cola1.getRaiz().setT_espera(cola1.getRaiz().getT_retorno() - cola1.getRaiz().getT_rafaga());
		// caso en que se bloquee
		if (bloq % 5 == 0) {
			contBloq += 1;
		}
	}

	// agrega n elementos de la cola que le pasen a la tabla
	public void agregarTabla(JTable tabla, Proceso cola) {
		Proceso auxT = cola;
		int col = tabla.getModel().getColumnCount();
		Object[] fila = new Object[col];
		while (auxT != null) {
			fila[0] = auxT.getNombre();
			fila[1] = auxT.getPrioridad();
			fila[2] = auxT.getT_llegada();
			fila[3] = auxT.getT_rafaga();
			fila[4] = auxT.getT_comienzo();
			fila[5] = auxT.getT_final();
			fila[6] = auxT.getT_retorno();
			fila[7] = auxT.getT_espera();
			((DefaultTableModel) tabla.getModel()).addRow(fila);
			auxT = auxT.getSiguiente();
		}

	}
	
	public void editar(JTable tabla, Proceso cola) {
		Proceso auxT = cola;
		int i = 0;
		int col = tabla.getModel().getColumnCount();
		Object[] fila = new Object[col];

		while (auxT != null) {
			if (i < cont) {
				// edito los valores que estaban en la cola
				tabla.setValueAt(auxT.getNombre(), i, 0);
				tabla.setValueAt(auxT.getPrioridad(), i, 1);
				tabla.setValueAt(auxT.getT_llegada(), i, 2);
				tabla.setValueAt(auxT.getT_rafaga(), i, 3);
				tabla.setValueAt(auxT.getT_comienzo(), i, 4);
				tabla.setValueAt(auxT.getT_final(), i, 5);
				tabla.setValueAt(auxT.getT_retorno(), i, 6);
				tabla.setValueAt(auxT.getT_espera(), i, 7);
				i++;
			} else {
				// agrego valores que no estaban en la cola
				fila[0] = auxT.getNombre();
				fila[1] = auxT.getPrioridad();
				fila[2] = auxT.getT_llegada();
				fila[3] = auxT.getT_rafaga();
				fila[4] = auxT.getT_comienzo();
				fila[5] = auxT.getT_final();
				fila[6] = auxT.getT_retorno();
				fila[7] = auxT.getT_espera();
				// agrego nueva fila
				((DefaultTableModel) tabla.getModel()).addRow(fila);
			}

			auxT = auxT.getSiguiente();
		}

	}

	public void Simulacion(JTable diagrama){
		for (int i = 0; i < cont; i++) {

			int col = diagrama.getModel().getColumnCount();
			Object[] fila = new Object[col];
			// Dibuja desde el tiempo de llegada hasta antes de comenzar
			for (int j = tempPR.getRaiz().getT_llegada(); j < tempPR.getRaiz().getT_comienzo(); j++) {
				fila[j + 1] = "--";
			}
			// Verificacion si el sistema esta bloqueado
			if (tempPR.getRaiz().getBloqueado() % 5 == 0 && tempito == 1) {
				//Si el proceso es el ultimo ya no se bloqueara
				if(tempPR.getRaiz().getSiguiente()!=null) {
					//Muestro que sera un proceso bloqueado por el BQ
					fila[0] = tempPR.getRaiz().getNombre()+" BQ";
					bloqueo(fila,diagrama,tempPR.getRaiz());
				}
			} else {
				// En caso de que no se bloquee se dibuja el proceso normal
				fila[0] = tempPR.getRaiz().getNombre();
				for (int j = tempPR.getRaiz().getT_comienzo(); j < tempPR.getRaiz().getT_final(); j++) {
					fila[j + 1] = "XX";
				}
				
				// Añade la fila a la tabla
				
			}
			tempPR.extraer();
			((DefaultTableModel) diagrama.getModel()).addRow(fila);

		}
	}	
	
	
	public void SimulacionBloqueo(JTable diagrama) {
		int col = diagrama.getModel().getColumnCount();
		JOptionPane.showMessageDialog(null, "Se bloqueo el proceso: " + tempPR.getRaiz().getNombre());
		for (int i = 0; i <contBloq; i++) {
			Object[] fila = new Object[col];
			// Muestro que es el restante de un proceso bloqueado
			fila[0] = tempPR.getRaiz().getNombre() + " BQ";
			// Dibuja lineas diferentes ya que es la terminacion de un proceso bloqueado
			for (int j = iniciobl + 1; j <= tempPR.getRaiz().getT_comienzo(); j++) {
				fila[j] = "||";
			}
			// Dibuja el restante del proceso
			for (int j = tempPR.getRaiz().getT_comienzo(); j < tempPR.getRaiz().getT_final(); j++) {
				fila[j + 1] = "XX";
			}
			// Agrega la fila al diagrama de gant
			((DefaultTableModel) diagrama.getModel()).addRow(fila);

			// Se extrae de la cola
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
		tempPR.insertar(cola.getT_llegada(), cola.getPrioridad() , cola.getT_rafaga() - diferencia, tempPR.getFondo().getT_final()-diferencia, 0, 0,
				0, cola.getBloqueado(), cola.getNombre());
		tempPR.getFondo().setT_final(tempPR.getFondo().getT_comienzo() + diferencia);
		tempPR.getFondo().setT_retorno(tempPR.getFondo().getT_final());
		tempPR.getFondo().setT_espera(tempPR.getFondo().getT_comienzo() - cola.getT_final());

		System.out.println(tempPR.getFondo().getT_rafaga());
		// Actualiza a la cola con los nuevos valores que tendran por el bloqueo
		Proceso aux = cola.getSiguiente();
		while (aux != tempPR.getFondo()) {
			aux.setT_comienzo(aux.getT_comienzo() - diferencia);
			aux.setT_final(aux.getT_final() - diferencia);
			aux.setT_retorno(aux.getT_final());
			aux.setT_espera(aux.getT_retorno() - aux.getT_rafaga());
			aux = aux.getSiguiente();
		}
		tempPR.imprimir();
	}

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

	public JLabel getLblPrioridad() {
		return lblPrioridad;
	}

	public void setLblPrioridad(JLabel lblPrioridad) {
		this.lblPrioridad = lblPrioridad;
	}

	public JTextField getTextFieldPrioridad() {
		return textFieldPrioridad;
	}

	public void setTextFieldPrioridad(JTextField textFieldPrioridad) {
		this.textFieldPrioridad = textFieldPrioridad;
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
	public Cola getTempPR() {
		return tempPR;
	}
	public void setTempPR(Cola tempPR) {
		this.tempPR = tempPR;
	}
	public int getCont() {
		return cont;
	}
	public void setCont(int cont) {
		this.cont = cont;
	}
	
	

}
