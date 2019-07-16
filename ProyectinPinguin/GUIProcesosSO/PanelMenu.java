package GUIProcesosSO;

import Logica.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import GUIProcesoPR.VentanaProcesosPR;


public class PanelMenu extends JPanel{

	private JLabel lblDescripcion;
	private JLabel lblNombreProceso;
	private JTextField textFieldNombre;
	private JLabel lblTLlegada;
	private JTextField textFieldTLlegada;
	private JLabel lblTRafaga;
	private JTextField textFieldTRafaga;
	private JButton btnAgregar;
	private JButton btnSimular;
	private int tiempoFinal=0;
	private Cola cola1;
	private Cola temp;
	private int cont;
	private int tempito=1;
	private int contBloq;
	private int iniciobl;
	
	public PanelMenu(JTable tabla,JTable diagrama) {
		this.setLayout(null);
		this.setBorder(new TitledBorder("Menu"));
		this.setBounds(5,5,195,340);
			
		String texto = "<html><body> Por medio de este programa puede simular "
				+ " el tiempo de ejecucion de procesos. <br>"
				+ " Acontinuacion puede ingresar mas procesos"
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
		
		cola1=new Cola();
		temp =new Cola();
		cont=0;
		btnAgregar = new JButton("Agregar");
		btnAgregar.setFont(new Font("Gulim", Font.PLAIN, 12));
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (textFieldNombre.getText().equals("") || textFieldTLlegada.getText().equals("")|| textFieldTRafaga.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "ERROR. \n Por favor Ingrese todos los datos");
				}else{
					
					Agregar();
					
					temp.insertar(Integer.parseInt(textFieldTLlegada.getText()),Integer.parseInt(textFieldTRafaga.getText()),
							getTiempoFinal(),cola1.getRaiz().getT_final(),cola1.getRaiz().getT_retorno(),cola1.getRaiz().getT_espera(),
							cola1.getRaiz().getBloqueado(),textFieldNombre.getText());

					agregarTabla(tabla,cola1.getRaiz());
					
					setTiempoFinal(cola1.getRaiz().getT_final());
					
					textFieldNombre.setText("");
					textFieldTLlegada.setText("");
					textFieldTRafaga.setText("");
					
					cola1.extraer();
					
					cont+=1;
				}
			}});
		btnAgregar.setBounds(15, 275, 120, 22);
		add(btnAgregar);
		
		btnSimular = new JButton("Simular");
		btnSimular.setFont(new Font("Gulim", Font.PLAIN, 12));
		btnSimular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				temp.imprimir();
				agregarTabla(tabla, temp.getRaiz());
				System.out.println("Entre aqui?");
				
				
				
				if(diagrama.getRowCount()<2) {
					for(int i=0;i<temp.getFondo().getT_final()+1;i++) {
						((DefaultTableModel) diagrama.getModel()).addColumn(""+i);
					}
					Simulacion(diagrama);
				}else {
					// Creea las columnas de la tabla desde n-1
					int col = diagrama.getModel().getColumnCount();
					for (int i = col - 1; i < temp.getFondo().getT_final() + 1; i++) {
						((DefaultTableModel) diagrama.getModel()).addColumn("" + i);
					}
					// Actualiza
					Simulacion(diagrama);
				}
				if(!temp.vacia()) {
					temp.setRaiz(temp.getFondo());
					SimulacionBloqueo(diagrama);
				}
				JOptionPane.showMessageDialog(null, "Se ha simulado correctamente");
				cont=0;
				
			}});
		btnSimular.setBounds(15, 310, 120, 22);
		add(btnSimular);
		
		this.setVisible(true);
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
	
	//agrega n elementos de la cola que le pasen a la tabla
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
	
	public void bloqueo(Object[] fila,JTable diagrama,Proceso cola) {
		tempito = 0;
		int diferencia = cola.getT_rafaga()/2;
		System.out.println("Estoy bloqueando a "+cola.getNombre());
		// Dibuja proceso hasta la mitad por el bloqueo
		cola.setT_final(cola.getT_final()-diferencia);
		iniciobl=cola.getT_final();
		for (int j = cola.getT_comienzo(); j <cola.getT_final(); j++) {
			fila[j + 1] = "XX";
		}
		//inserta valor de rafaga restante al final de la cola
		temp.insertar(cola.getT_llegada(), cola.getT_rafaga()-diferencia,
				temp.getFondo().getT_final(),0,0,0,cola.getBloqueado(), cola.getNombre());
		temp.getFondo().setT_final(temp.getFondo().getT_comienzo() + diferencia);
		temp.getFondo().setT_retorno(temp.getFondo().getT_final());
		temp.getFondo().setT_espera(temp.getFondo().getT_comienzo() - cola.getT_final());
		//Actualiza a la cola con los nuevos valores que tendran por el bloqueo
		Proceso aux = cola.getSiguiente();
		while(aux!=null){
			aux.setT_comienzo(aux.getT_comienzo()-diferencia);
			aux.setT_final(aux.getT_final()-diferencia);
			aux.setT_retorno(aux.getT_final());
			aux.setT_espera(aux.getT_retorno()-aux.getT_rafaga());
			aux=aux.getSiguiente();
		}
		
		
		temp.imprimir();
		contBloq+=1;
	}
	
	public void Simulacion(JTable diagrama){

		for (int i = 0; i < cont; i++) {
		
			if (temp.getRaiz().getT_final()< 20) {
			
			int col = diagrama.getModel().getColumnCount();
			Object[] fila = new Object[col];
			// Dibuja desde el tiempo de llegada hasta antes de comenzar
			for (int j = temp.getRaiz().getT_llegada(); j < temp.getRaiz().getT_comienzo(); j++) {
				fila[j + 1] = "--";
			}
			// Verificacion si el sistema esta bloqueado
			if (temp.getRaiz().getBloqueado() % 5 == 0 && tempito == 1) {
				//Si el proceso es el ultimo ya no se bloqueara
				if(temp.getRaiz().getSiguiente()!=null) {
					//Muestro que sera un proceso bloqueado por el BQ
					fila[0] = temp.getRaiz().getNombre()+" BQ";
					bloqueo(fila,diagrama,temp.getRaiz());
				}
			} else {
				// En caso de que no se bloquee se dibuja el proceso normal
				fila[0] = temp.getRaiz().getNombre();
				for (int j = temp.getRaiz().getT_comienzo(); j < temp.getRaiz().getT_final(); j++) {
					fila[j + 1] = "XX";
				}
				
				// Añade la fila a la tabla
				
			}
			temp.extraer();
			((DefaultTableModel) diagrama.getModel()).addRow(fila);
			}else {
				VentanaProcesosPR vt=new VentanaProcesosPR();
				vt.setVisible(true);
				//Para que se quede el bloqueadin
				temp.getFondo().setT_comienzo(temp.getRaiz().getT_comienzo());
				temp.getFondo().setT_final(temp.getFondo().getT_comienzo()+temp.getFondo().getT_rafaga());
				temp.imprimir();
				
				int com=0;
				int conti=0;
				Proceso auxito= temp.getRaiz();
				while(auxito!=temp.getFondo()) {
					vt.getPanelmenu().getTempPR().insertar(auxito.getT_llegada(),1, auxito.getT_rafaga(),
							0, 0, 0, 0, auxito.getBloqueado(), auxito.getNombre());
					vt.getPanelmenu().getTempPR().getFondo().setT_comienzo(com);
					vt.getPanelmenu().getTempPR().getFondo().setT_final(com+auxito.getT_rafaga());
					com=vt.getPanelmenu().getTempPR().getFondo().getT_final();
					vt.getPanelmenu().getTempPR().getFondo().setT_retorno(com);
					vt.getPanelmenu().getTempPR().getFondo().setT_retorno(com-auxito.getT_rafaga());
					auxito=auxito.getSiguiente();
					conti+=1;
				}
				vt.getPanelmenu().setCont(conti);
				vt.getPanelmenu().getTempPR().imprimir();
				break;
			}
		}
	}	
	
	public void SimulacionBloqueo(JTable diagrama) {
		int col = diagrama.getModel().getColumnCount();
		JOptionPane.showMessageDialog(null, "Se bloqueo el proceso: "+temp.getRaiz().getNombre());
		for (int i = 0; i < contBloq; i++) {
			Object[] fila = new Object[col];
			//Muestro que es el restante de un proceso bloqueado 
			fila[0] = temp.getRaiz().getNombre()+" BQ";
			// Dibuja lineas diferentes ya que es la terminacion de un proceso bloqueado
			for (int j = iniciobl+1; j <= temp.getRaiz().getT_comienzo(); j++) {
				fila[j] = "||";
			}
			// Dibuja el restante del proceso
			for (int j = temp.getRaiz().getT_comienzo(); j < temp.getRaiz().getT_final(); j++) {
				fila[j+1] = "XX";
			}
			//Agrega la fila al diagrama de gant
			((DefaultTableModel) diagrama.getModel()).addRow(fila);
			
			//Se extrae de la cola
			temp.extraer();
		}
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

	public Cola getTemp() {
		return temp;
	}

	public void setTemp(Cola temp) {
		this.temp = temp;
	}

	public int getCont() {
		return cont;
	}

	public void setCont(int cont) {
		this.cont = cont;
	}
	
	
	
	
}
