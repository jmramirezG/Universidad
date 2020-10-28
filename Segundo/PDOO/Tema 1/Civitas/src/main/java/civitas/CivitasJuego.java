/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author jmramirez
 */
public class CivitasJuego {
    private int indiceJugadorActual;
    private ArrayList<Jugador> jugadores = new ArrayList<>();
    private GestorEstados gestorEstados;
    private EstadosJuego estado;
    private MazoSorpresas mazoSorpresas;
    private Tablero tablero;
    private final int numCasillaCarcel = 5;
    
    CivitasJuego(String []nombres) {
        for (String nombre : nombres) {
            Jugador jugador = new Jugador(nombre);
            jugadores.add(jugador);
        }
        estado = gestorEstados.estadoInicial();
        indiceJugadorActual = Dado.getInstance().quienEmpieza(jugadores.size());
        inicializaTablero(mazoSorpresas);
        inicializaMazoSorpresas(tablero);
    }
    
    void inicializaTablero(MazoSorpresas mazo) {
        tablero = new Tablero(numCasillaCarcel);
        
        for (int i = 1; i <4; i++) {
            TituloPropiedad calle = new TituloPropiedad("Calle"+i, 100*i, 1+(i/10), 150*i, 250*i, 100*i );
            Casilla casilla = new Casilla(calle);
            tablero.añadeCasilla(casilla);
        }
        
        Casilla impuesto = new Casilla(-600, "Impuestos");
        tablero.añadeCasilla(impuesto);
        
        for (int i = 4; i <7; i++) {
            TituloPropiedad calle = new TituloPropiedad("Calle"+i, 100*i, 1+(i/10), 150*i, 250*i, 100*i );
            Casilla casilla = new Casilla(calle);
            tablero.añadeCasilla(casilla);
        }
        
        tablero.añadeJuez();
        
        Casilla sorpresa1 = new Casilla(mazo, "Sorpresa1");
        tablero.añadeCasilla(sorpresa1);
        
        for (int i = 7; i <10   ; i++) {
            TituloPropiedad calle = new TituloPropiedad("Calle"+i, 100*i, 1+(i/10), 150*i, 250*i, 100*i );
            Casilla casilla = new Casilla(calle);
            tablero.añadeCasilla(casilla);
        }
        
        Casilla sorpresa2 = new Casilla(mazo, "Sorpresa2");
        tablero.añadeCasilla(sorpresa2);
        
        Casilla parking = new Casilla("Parking");
        tablero.añadeCasilla(parking);
        
        for (int i = 10; i <12   ; i++) {
            TituloPropiedad calle = new TituloPropiedad("Calle"+i, 100*i, 1+(i/10), 150*i, 250*i, 100*i );
            Casilla casilla = new Casilla(calle);
            tablero.añadeCasilla(casilla);
        }
        
        Casilla sorpresa3 = new Casilla(mazo, "Sorpresa3");
        tablero.añadeCasilla(sorpresa3);
        
        TituloPropiedad calle12 = new TituloPropiedad("Calle"+12, 100*12, 1+(12/10), 150*12, 250*12, 100*12 );
        Casilla casilla12 = new Casilla(calle12);
        tablero.añadeCasilla(casilla12);
    }
    
    void inicializaMazoSorpresas(Tablero tab) {
        mazoSorpresas = new MazoSorpresas();
        
        Sorpresa ircarcel = new Sorpresa(TipoSorpresa.IRCARCEL, tab);
        mazoSorpresas.alMazo(ircarcel);
        
        Sorpresa pagar500 = new Sorpresa(TipoSorpresa.PAGARCOBRAR, -500, "Pagar 500");
        mazoSorpresas.alMazo(pagar500);
        
        Sorpresa irCasilla12 = new Sorpresa(TipoSorpresa.IRCASILLA, tab, 12);
        mazoSorpresas.alMazo(irCasilla12);
        
        Sorpresa evitarCarcel = new Sorpresa(TipoSorpresa.SALIRCARCEL, mazoSorpresas);
        mazoSorpresas.alMazo(evitarCarcel);
        
        Sorpresa porJugador130 = new Sorpresa(TipoSorpresa.PORJUGADOR, 130, "Pago de 130 de cada jugador al jugador que caiga en esta casilla");
        mazoSorpresas.alMazo(porJugador130);
        
        Sorpresa porCasaHotel60 = new Sorpresa(TipoSorpresa.PORCASAHOTEL, -60, "Paga 60 por cada edificio que tengas");
        mazoSorpresas.alMazo(porCasaHotel60);
    }
    
    void contabilizarPasosPorSalida(Jugador jugadorActual) {
        while (tablero.getPorSalida() != 0) {
            jugadorActual.pasaPorSalida();
        }
    }
    
    void pasarTurno() {
        indiceJugadorActual = (indiceJugadorActual+1)%jugadores.size();
    }
    
    void siguientePasoCompletado(OperacionesJuego operacion) {
        estado = gestorEstados.siguienteEstado(jugadores.get(indiceJugadorActual), estado, operacion);
    }
    
    Boolean construirCasa(int ip) {
        return jugadores.get(indiceJugadorActual).construirCasa(ip);
    }
    
    Boolean construirHotel(int ip) {
        return jugadores.get(indiceJugadorActual).construirHotel(ip);
    }
    
    Boolean vender(int ip) {
        return jugadores.get(indiceJugadorActual).vender(ip);
    }
    
    Boolean hipotecar(int ip) {
        return jugadores.get(indiceJugadorActual).hipotecar(ip);
    }
    
    Boolean cancelarHipoteca(int ip) {
        return jugadores.get(indiceJugadorActual).cancelarHipoteca(ip);
    }
    
    Boolean salirCarcelTirando() {
        return jugadores.get(indiceJugadorActual).salirCarcelTirando();
    }
    
    Boolean salirCarcelPagando() {
        return jugadores.get(indiceJugadorActual).salirCarcelPagando();
    }
    
    Boolean finalDelJuego() {
        for (Jugador jugador : jugadores) {
            if(jugador.enBacarrota()) {
                return true;
            }
        }
        return false;
    }
    
    ArrayList<Jugador> ranking() {
        Collections.sort(jugadores);
        return jugadores;
    }
    
}