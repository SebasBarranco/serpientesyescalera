package front;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import clases.Tablero;

public class nombrejugadores extends JFrame {

    private JLabel tituloLabel;
    private JLabel mensajeLabel;
    private JTextField nombreField;
    private JButton continuarButton;
    private int numJugadores; 
    private String[] nombresJugadores; 
    private int jugadorActual; 
    private Tablero t;

    
    public nombrejugadores(int numJugadores, Tablero tablero) {
        super("Insertar Nombres de Jugadores");

        this.numJugadores = numJugadores;
        this.nombresJugadores = new String[numJugadores];
        this.jugadorActual = 0;
        this.numJugadores = 0;
        this.t = tablero; 

        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 200); 
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(220, 255, 220)); 

        
        int actual = jugadorActual+1;
        tituloLabel = new JLabel("Inserta el nombre del jugador " + actual, SwingConstants.CENTER);
        mensajeLabel = new JLabel("", SwingConstants.CENTER);
        nombreField = new JTextField(20);
        continuarButton = new JButton("Continuar");

        setLayout(new BorderLayout());
        JPanel panelCentral = new JPanel(new GridLayout(4, 1, 10, 10)); 
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 
        panelCentral.setBackground(new Color(255, 192, 203)); 
        panelCentral.add(tituloLabel);
        panelCentral.add(mensajeLabel);
        panelCentral.add(nombreField);
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setBackground(new Color(255, 192, 203)); 
        panelBoton.add(continuarButton);
        panelCentral.add(panelBoton);

        add(panelCentral, BorderLayout.CENTER);

     
        continuarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!nombreField.getText().isEmpty()) {
                    nombresJugadores[jugadorActual] = nombreField.getText();
                    t.agregarJugador(nombresJugadores[jugadorActual]);
                    tituloLabel.setText("Inserta el nombre del jugador " + (jugadorActual+2));
                    nombreField.setText(""); 
                    nombreField.requestFocusInWindow(); 
                    jugadorActual++;
                    t.imprimirJugadores();

                    if (jugadorActual < numJugadores) {
                    } else {
                       
                        JOptionPane.showMessageDialog(null, "Todos los nombres han sido ingresados.");
                        for (int i = 0; i < nombresJugadores.length; i++) {
                            System.out.println("Jugador " + (i + 1) + ": " + nombresJugadores[i]);
                        }
                        dispose(); 
                    }
                } else {
                  
                    mensajeLabel.setText("El nombre no puede estar vacío.");
                }
            }
        });
    }
}
