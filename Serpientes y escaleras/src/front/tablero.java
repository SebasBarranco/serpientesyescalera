package front;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import clases.Escalera;
import clases.Serpiente;
import clases.Tablero;

public class tablero extends JFrame {

    private JPanel tableroPanel;
    private JButton dadoButton;
    private JButton historialButton;
    private JTextArea historialTextArea;
    private JScrollPane historialScrollPane;
    private Tablero tablero;
    private JPanel[][] casillas;
    private boolean juegoTerminado;
    private int turno;

    private JLabel turnoContadorLabel;
    private JLabel jugadorTurnoLabel;
    private JLabel rondaLabel;

    private JPanel[] fichasJugadores;
    private Color[] coloresFichas = {Color.ORANGE, Color.PINK, Color.GREEN, Color.YELLOW};

    public tablero(int filas, int columnas, Tablero tablero) {
        super("Tablero");

        this.tablero = tablero;
        this.casillas = new JPanel[filas][columnas];
        this.juegoTerminado = false;
        this.turno = 0;
        tablero.rellenarTablero();

        turnoContadorLabel = new JLabel("Turno: 0");
        jugadorTurnoLabel = new JLabel("Turno del Jugador: ");
        rondaLabel = new JLabel("Ronda: 0");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 800);
        setLocationRelativeTo(null);

        tableroPanel = new JPanel(new GridLayout(filas, columnas));
        tableroPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tableroPanel.setBackground(new Color(220, 220, 220));

        // Crear las casillas del tablero con números o imágenes
        for (int fila = filas - 1; fila >= 0; fila--) {
            for (int columna = 0; columna < columnas; columna++) {
                int numeroCasilla;
                if (fila % 2 == 0) {
                    numeroCasilla = (fila * columnas) + columna + 1;
                } else {
                    numeroCasilla = ((fila + 1) * columnas) - columna;
                }

                JPanel casillaPanel = new JPanel(new BorderLayout());
                casillaPanel.setPreferredSize(new Dimension(50, 50));
                casillaPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                if (fila % 2 == 0) {
                    casillaPanel.setBackground(new Color(255, 255, 255));
                } else {
                    casillaPanel.setBackground(new Color(1,1,1));
                }

                Object objeto = tablero.getJuego()[numeroCasilla - 1];
                if (objeto instanceof Serpiente) {
                    ImageIcon icono = ((Serpiente) objeto).getIcon();
                    JLabel imagenLabel = new JLabel(escalarImagen(icono));
                    imagenLabel.setVerticalAlignment(SwingConstants.CENTER);
                    imagenLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    casillaPanel.add(imagenLabel, BorderLayout.CENTER);
                } else if (objeto instanceof Escalera) {
                    ImageIcon icono = ((Escalera) objeto).getIcon();
                    JLabel imagenLabel = new JLabel(escalarImagen(icono));
                    imagenLabel.setVerticalAlignment(SwingConstants.CENTER);
                    imagenLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    casillaPanel.add(imagenLabel, BorderLayout.CENTER);
                } else {
                    JLabel numeroLabel = new JLabel(String.valueOf(numeroCasilla), SwingConstants.CENTER);
                    numeroLabel.setVerticalAlignment(SwingConstants.CENTER);
                    numeroLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    casillaPanel.add(numeroLabel, BorderLayout.CENTER);
                }

                casillas[fila][columna] = casillaPanel;
                tableroPanel.add(casillaPanel);
            }
        }

        // Crear el botón para la imagen del dado
        ImageIcon dadoIcon = escalarImagen(new ImageIcon(getClass().getResource("/Imagenes/dado.png")));
        dadoButton = new JButton(dadoIcon);

        dadoButton.setPreferredSize(new Dimension(dadoIcon.getIconWidth(), dadoIcon.getIconHeight()));
        dadoButton.setBorder(BorderFactory.createEmptyBorder());
        dadoButton.setContentAreaFilled(false);

        dadoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarMovimientoJugador();
                turno++;
            }
        });

        
        historialButton = new JButton("Historial");
        historialButton.setEnabled(false);
        historialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                historialTextArea.setText(tablero.obtenerHistorialJuego());
            }
        });

        historialTextArea = new JTextArea(10, 30);
        historialTextArea.setEditable(false);
        historialScrollPane = new JScrollPane(historialTextArea);

        setLayout(new BorderLayout());
        add(tableroPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(Box.createVerticalGlue());

        buttonPanel.add(turnoContadorLabel);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(jugadorTurnoLabel);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(rondaLabel);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        buttonPanel.add(dadoButton); // Agregar la etiqueta del dado al panel de botones

        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(historialButton);
        buttonPanel.add(Box.createVerticalGlue());

        add(buttonPanel, BorderLayout.EAST);
        add(historialScrollPane, BorderLayout.SOUTH);

        fichasJugadores = new JPanel[tablero.getJugadores().length];
        for (int i = 0; i < tablero.getJugadores().length; i++) {
            fichasJugadores[i] = new JPanel();
            fichasJugadores[i].setBackground(coloresFichas[i]);
            fichasJugadores[i].setPreferredSize(new Dimension(20, 20));
            casillas[0][0].add(fichasJugadores[i]);
            casillas[0][0].revalidate();
            casillas[0][0].repaint();
        }
    }

    private void realizarMovimientoJugador() {
        int jugadorActual = tablero.getTurnoActual();
        int lanzamiento = tablero.realizarLanzamiento();
        moverFichaJugador(jugadorActual, lanzamiento);
        ImageIcon dadoIcon = escalarImagen(new ImageIcon(dadoFase(lanzamiento)));
        dadoButton.setIcon(dadoIcon);
        actualizarEtiquetas();
    }

    private void moverFichaJugador(int jugador, int lanzamiento) {
        int posicionActual = tablero.getJugadores()[jugador].getPosicionActual();
        tablero.moverJugador(lanzamiento, tablero.getJugadores()[jugador]);
        int nuevaPosicion = tablero.getJugadores()[jugador].getPosicionActual();
        actualizarFichaEnInterfaz(jugador, posicionActual, nuevaPosicion);

        if (tablero.getJugadores()[jugador].isGanador()) {
            juegoTerminado = true;
            dadoButton.setEnabled(false);
            historialButton.setEnabled(true);
            mostrarMensajeGanador(tablero.getJugadores()[jugador].getNombre());
        } else {
            tablero.siguienteTurno();
        }
    }

    private void actualizarFichaEnInterfaz(int jugador, int posicionAnterior, int nuevaPosicion) {
        int filaAnterior = (posicionAnterior - 1) / 10;
        int columnaAnterior = (posicionAnterior % 10);

        int filaNueva = (nuevaPosicion - 1) / 10;
        int columnaNueva = (nuevaPosicion - 1) % 10;

        // Restaurar el fondo original de la casilla anterior
        JPanel casillaAnterior = casillas[filaAnterior][columnaAnterior];
        if (filaAnterior % 2 == 0) {
            casillaAnterior.setBackground(new Color(173, 216, 230));
        } else {
            casillaAnterior.setBackground(new Color(255, 193, 128));
        }

        // Eliminar la ficha del jugador de la casilla anterior
        casillaAnterior.remove(fichasJugadores[jugador]);
        casillaAnterior.revalidate();
        casillaAnterior.repaint();

        // Agregar la ficha del jugador a la nueva casilla
        JPanel nuevaCasilla = casillas[filaNueva][columnaNueva];
        nuevaCasilla.add(fichasJugadores[jugador]);
        nuevaCasilla.revalidate();
        nuevaCasilla.repaint();
    }

    private void mostrarMensajeGanador(String nombreJugador) {
        String mensaje = "¡El jugador " + nombreJugador + " ha ganado el juego!";
        JOptionPane.showMessageDialog(this, mensaje, "Juego terminado", JOptionPane.INFORMATION_MESSAGE);
    }

    private ImageIcon escalarImagen(ImageIcon icon) {
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }

    private void actualizarEtiquetas() {
        turnoContadorLabel.setText("Turno: " + turno);
        jugadorTurnoLabel.setText("Turno del Jugador: " + tablero.getJugadores()[tablero.getTurnoActual()].getNombre());
        rondaLabel.setText("Ronda: " + (turno / tablero.getJugadores().length));
    }

    private URL dadoFase(int lanzamiento) {
        if (lanzamiento == 1) {
            return getClass().getResource("/Imagenes/dado1.png");
        } else if (lanzamiento == 2) {
            return getClass().getResource("/Imagenes/dado2.png");
        } else if (lanzamiento == 3) {
            return getClass().getResource("/Imagenes/dado3.png");
        } else if (lanzamiento == 4) {
            return getClass().getResource("/Imagenes/dado4.png");
        } else if (lanzamiento == 5) {
            return getClass().getResource("/Imagenes/dado5.png");
        } else {
            return getClass().getResource("/Imagenes/dado6.png");
        }
    }
}
