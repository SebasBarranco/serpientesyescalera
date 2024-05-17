package front;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import clases.Tablero;

public class bienvenida extends JFrame {

    private JLabel bienvenidaLabel;
    private JLabel instruccionesLabel;
    private JLabel jugadoresLabel;
    private JTextField jugadoresField;
    private JLabel casillasLabel;
    private JTextField casillasField;
    private JTextField serpientesField;
    private JLabel serpientesLabel;
    private JLabel escalerasLabel;
    private JTextField escalerasField;
    private JButton agregarSerpienteButton;
    private JButton agregarEscaleraButton;
    private JButton continuarButton;

    private List<int[]> serpientes;
    private List<int[]> escaleras;

    public bienvenida() {
        super("Inicio del Juego");
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 600); 
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(255, 192, 203)); 

       
        serpientes = new ArrayList<>();
        escaleras = new ArrayList<>();

        bienvenidaLabel = new JLabel("Escaleras y Serpientes", SwingConstants.CENTER);
        bienvenidaLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 40)); 
        instruccionesLabel = new JLabel("Configura tu juego", SwingConstants.CENTER);
        jugadoresLabel = new JLabel("Numero de jugadores:");
        jugadoresField = new JTextField(10);
        casillasLabel = new JLabel("Elige la dificultad:");
        casillasField = new JTextField(10);
        serpientesLabel = new JLabel("Añade serpientes Cabeza: 77 + (-) + Cola: 4 :");
        serpientesField = new JTextField(10);
        agregarSerpienteButton = new JButton("Agregar");

        escalerasLabel = new JLabel("Añade escalera Abajo: 6 + (-) + Arriba: 80 :");
        escalerasField = new JTextField(10);
        agregarEscaleraButton = new JButton("Agregar");
        continuarButton = new JButton("Continuar");
        continuarButton.setBackground(new Color(255, 192, 203)); 
        continuarButton.setForeground(Color.WHITE); 

     
        setLayout(new BorderLayout());
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 
        panelCentral.setBackground(new Color(255, 192, 203)); 

        bienvenidaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentral.add(bienvenidaLabel);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 10))); 

        instruccionesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentral.add(instruccionesLabel);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 10))); 

        JPanel panelJugadores = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelJugadores.setBackground(new Color(255, 192, 203)); 
        panelJugadores.add(jugadoresLabel);
        panelJugadores.add(jugadoresField);
        panelCentral.add(panelJugadores);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel panelCasillas = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelCasillas.setBackground(new Color(255, 192, 203)); 
        panelCasillas.add(casillasLabel);
        panelCasillas.add(casillasField);
        panelCentral.add(panelCasillas);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 10))); 

        JPanel panelSerpientes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelSerpientes.setBackground(new Color(255, 192, 203));
        panelSerpientes.add(serpientesLabel);
        panelSerpientes.add(serpientesField);
        panelSerpientes.add(agregarSerpienteButton);
        panelCentral.add(panelSerpientes);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 10))); 

        JPanel panelEscaleras = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelEscaleras.setBackground(new Color(255, 192, 203));
        panelEscaleras.add(escalerasLabel);
        panelEscaleras.add(escalerasField);
        panelEscaleras.add(agregarEscaleraButton);
        panelCentral.add(panelEscaleras);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 10))); 
        continuarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentral.add(continuarButton);

        add(panelCentral, BorderLayout.CENTER);

  
        agregarSerpienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String coordenadas = serpientesField.getText();
                if (!coordenadas.isEmpty()) {
                    String[] pos = coordenadas.split("-");
                    if (pos.length == 2) {
                        int inicioPos = Integer.parseInt(pos[0].trim());
                        int finPos = Integer.parseInt(pos[1].trim());
                        serpientes.add(new int[]{inicioPos, finPos});
                        serpientesField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Acuerdate, es asi (60-3).");
                    }
                }
            }
        });

        
        agregarEscaleraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String coordenadas = escalerasField.getText();
                if (!coordenadas.isEmpty()) {
                    String[] pos = coordenadas.split("-");
                    if (pos.length == 2) {
                        int inicioPos = Integer.parseInt(pos[0].trim());
                        int finPos = Integer.parseInt(pos[1].trim());
                        escaleras.add(new int[]{inicioPos, finPos});
                        escalerasField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Acuerdate, es asi (4-77).");
                    }
                }
            }
        });

        
        continuarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                int numJugadores = Integer.parseInt(jugadoresField.getText());
                int numCasillas = Integer.parseInt(casillasField.getText());
                Tablero t = new Tablero(numCasillas * numCasillas, numJugadores);

                
                for (int[] serpiente : serpientes) {
                    t.agregarSerpiente(serpiente[0], serpiente[1]);
                }

               
                for (int[] escalera : escaleras) {
                    t.agregarEscalera(escalera[0], escalera[1]);
                }

                t.rellenarTablero();
                t.imprimirTablero();

               
                tablero tablero = new tablero(numCasillas, numCasillas, t);
                tablero.setVisible(true);

                
                nombrejugadores nombresJugadores = new nombrejugadores(numJugadores, t);
                nombresJugadores.setVisible(true);
               
                dispose();
            }
        });
    }
}
