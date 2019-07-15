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
    private JLabel lblPrioridad;
    private JTextField textFieldPrioridad;
    private JButton btnAgregar;
    private JButton btnSimular;
    private int tiempoFinal = 0;
    private Cola cola1;
    private Cola temp;
    private Cola bloqueado;
    private static int diferencia;
    private String nom;
    private Proceso aux;
    private int cont;
    private int contBloq;
    private int tempito;
    private int quantum;

    public PanelMenu(JTable tabla, JTable diagrama) {
        this.setLayout(null);
        this.setBorder(new TitledBorder("Menu"));
        this.setBounds(5, 5, 205, 390);

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

        cola1 = new Cola();
        temp = new Cola();
        bloqueado = new Cola();
        cont = 0;
        contBloq = 0;
        tempito = 1;
        quantum=4;

        btnAgregar = new JButton("Agregar");
        btnAgregar.setFont(new Font("Gulim", Font.PLAIN, 12));
        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (textFieldNombre.getText().equals("") || textFieldTLlegada.getText().equals("") || textFieldTRafaga.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "ERROR. \n Por favor Ingrese todos los datos");
                } else {
                    int bloq = (int) ((Math.random() * 100) + 1);

                    cola1.insertar(Integer.parseInt(textFieldTLlegada.getText()), Integer.parseInt(textFieldTRafaga.getText()), getTiempoFinal(),
                            0, 0, 0, bloq, textFieldNombre.getText());
                    cola1.getRaiz().setT_final(cola1.getRaiz().getT_comienzo() + cola1.getRaiz().getT_rafaga());
                    cola1.getRaiz().setT_retorno(cola1.getRaiz().getT_final() - cola1.getRaiz().getT_llegada());
                    cola1.getRaiz().setT_espera(cola1.getRaiz().getT_retorno() - cola1.getRaiz().getT_rafaga());

                    if (bloq % 4 == 0) {
                        diferencia = cola1.getRaiz().getT_rafaga();
                        contBloq += 1;
                        nom = textFieldNombre.getText();
                    }
                    
                    temp.insertar(Integer.parseInt(textFieldTLlegada.getText()), 
                            cola1.getRaiz().getT_rafaga(), cola1.getRaiz().getT_comienzo(),
                            cola1.getRaiz().getT_final(), cola1.getRaiz().getT_retorno(),
                            cola1.getRaiz().getT_espera(), cola1.getRaiz().getBloqueado(),
                            textFieldNombre.getText());

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
                    System.out.println(bloq);
                    //JOptionPane.showMessageDialog(null, "Se han registrado los datos con exito");			

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

				if (diagrama.getColumnCount() < 2) {
					// Creea las columnas de la tabla desde 0
					for (int i = 0; i < temp.getFondo().getT_final() + 1; i++) {
						((DefaultTableModel) diagrama.getModel()).addColumn("" + i);
					}
					// Actualiza tabla con prioridad simula
					actualizar(tabla,temp);

					Simulacion(diagrama);

					JOptionPane.showMessageDialog(null, "Se ha simulado correctamente");
				} else {
					// Creea las columnas de la tabla desde n-1
					int col = diagrama.getModel().getColumnCount();
					for (int i = col - 1; i < temp.getFondo().getT_final() + 1; i++) {
						((DefaultTableModel) diagrama.getModel()).addColumn("" + i);
					}
					// Actualiza tabla con prioridad simula
					actualizar(tabla,temp);
					Simulacion(diagrama);

					JOptionPane.showMessageDialog(null, "Se ha simulado correctamente.");
				}
				// En caso de que haya bloqueados entra y dibuja los proceso que faltaron por
				// terminar
				if (!temp.vacia()) {
					int col = diagrama.getModel().getColumnCount();
					for (int i = 0; i < contBloq; i++) {
						Object[] fila = new Object[col];
						fila[0] = nom;
						System.out.println("COmienzo bloq" + temp.getRaiz().getT_comienzo());
						// Dibuja lineas diferentes ya que es la terminacion de un proceso bloqueado
						for (int j = 1; j <= temp.getRaiz().getT_comienzo(); j++) {
							fila[j] = "||";
						}
						// Dibuja el restante del proceso
						for (int j = temp.getRaiz().getT_comienzo(); j <= temp.getRaiz().getT_final(); j++) {
							fila[j] = "XX";
						}

						((DefaultTableModel) diagrama.getModel()).addRow(fila);

						temp.extraer();
					}
				}
				cont = 0;

			}
		});
		btnSimular.setBounds(15, 350, 120, 22);
		add(btnSimular);

		this.setVisible(true);
	}

    public void actualizar(JTable tabla, Proceso cola) {
    	Proceso auxT=cola;
    	int col=tabla.getModel().getColumnCount();
    	Object[] fila = new Object[col];
		while(auxT!=null){

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
	public void actualizarTabla(JTable tabla, Proceso cola) {
		
		// Actualiza la tabla con los nuevos valores
		Proceso auxT = cola;
		int col = tabla.getModel().getColumnCount();
		Object[] fila = new Object[col];
		while(auxT!=null){

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
	
	public int rafagaCorta(int rafaga) {
		aux=temp.getRaiz();
		int rafagaCorta=aux.getT_rafaga();
		while(aux.getSiguiente()!=null) {
			aux=aux.getSiguiente();
			if(rafaga<aux.getT_rafaga()) {
				rafagaCorta=rafaga;
				break;
			}
		}
		return rafagaCorta;
	}

//	public void Agregar() {
//		// Random de procesos para que se bloquee
//		int bloq = (int) ((Math.random() * 100) + 1);
//		// Inserto datos a la cola
//		cola1.insertar(Integer.parseInt(textFieldTLlegada.getText()), Integer.parseInt(textFieldTRafaga.getText()),
//				getTiempoFinal(), 0, 0, 0, bloq, textFieldNombre.getText());
//		cola1.getRaiz().setT_final(cola1.getRaiz().getT_comienzo() + cola1.getRaiz().getT_rafaga());
//		cola1.getRaiz().setT_retorno(cola1.getRaiz().getT_final() - cola1.getRaiz().getT_llegada());
//		cola1.getRaiz().setT_espera(cola1.getRaiz().getT_retorno() - cola1.getRaiz().getT_rafaga());
//		// caso en que se bloquee
//		if (bloq % 5 == 0) {
//			diferencia = cola1.getRaiz().getT_rafaga();
//			contBloq += 1;
//			nom = textFieldNombre.getText();
//		}
//	}

	public void Simulacion(JTable diagrama) {
		for (int i = 0; i <= cont; i++) {

			int col = diagrama.getModel().getColumnCount();
			Object[] fila = new Object[col];
			fila[0] = temp.getRaiz().getNombre();
			// Dibuja desde el tiempo de llegada hasta antes de comenzar
			for (int j = temp.getRaiz().getT_llegada(); j < temp.getRaiz().getT_comienzo(); j++) {
				fila[j + 1] = "--";
			}
			// Verificacion si el sistema esta bloqueado
			if (temp.getRaiz().getBloqueado() % 5 == 0 && tempito == 1) {
				tempito = 0;
				diferencia = temp.getRaiz().getT_rafaga() / 2;
				System.out.println(diferencia);
				// Dibuja proceso hasta la mitad por el bloqueo
				for (int j = temp.getRaiz().getT_comienzo(); j <= temp.getRaiz().getT_final() - diferencia; j++) {
					fila[j + 1] = "XX";
				}
				// Se guarda el Proceso que quedo bloqueado
				aux = temp.getRaiz();
				// Se expulsa el proceso bloqueado
				temp.extraer();
				for (int j = i; j < cont - 1; j++) {
					if (!temp.vacia()) {
						// AJUSTAR QUE EMPIECE ANTES
						// Nueva tabla con bloqueados
						temp.insertar(temp.getRaiz().getT_llegada(), temp.getRaiz().getT_rafaga(),
								temp.getRaiz().getT_comienzo() - diferencia, temp.getRaiz().getT_final() - diferencia,
								temp.getRaiz().getT_retorno(), temp.getRaiz().getT_espera(),
								temp.getRaiz().getBloqueado(), temp.getRaiz().getNombre());
						aux = aux.getSiguiente();
						temp.extraer();

					}

				}
				temp.insertar(aux.getT_llegada(), aux.getT_rafaga(), (col - 1) - diferencia, (col - 1),
						aux.getT_retorno(), aux.getT_espera(), aux.getBloqueado(), aux.getNombre());
				((DefaultTableModel) diagrama.getModel()).addRow(fila);

			} else {
				// En caso de que no se bloquee se dibuja el proceso normal
				for (int j = temp.getRaiz().getT_comienzo(); j <= temp.getRaiz().getT_final(); j++) {
					fila[j + 1] = "XX";
				}
				temp.extraer();
			}
			// Añade la fila a la tabla
			((DefaultTableModel) diagrama.getModel()).addRow(fila);
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

}
