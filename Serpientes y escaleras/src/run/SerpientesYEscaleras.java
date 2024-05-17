/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package run;

import java.util.Scanner;
import javax.swing.SwingUtilities;
import clases.Dado;
import clases.Escalera;
import clases.Jugador;
import clases.Serpiente;
import clases.Tablero;
import front.bienvenida;


public class SerpientesYEscaleras {


    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new bienvenida().setVisible(true);
        });
    }

}
