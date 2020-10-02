
package basico ;

public class PruebaPeluqueria { // Clase con programa principal
    
 public static void main ( String [] args ) {
     
   //se crea un objeto Persona y otro Peluquer�a, que se comunican entre s� mediante env�o de mensajes
     
   Persona p=new Persona ( "Pepe" ,19 , ColorPelo.RUBIO, 1.65) ;
   System.out.println(p.toString());
    
   Peluqueria pelu= new Peluqueria();
   Citas cita = new Citas();
   System.out.println ("Cita en la peluqueria: ");
   System.out.println (cita.DarCita(p, pelu));
   pelu.CambiarColorPelo(ColorPelo.CASTA�O, p);
   System.out.println(p.toString());
   cita.anularCita(p, pelu);
 }
 
}
 
 /* Tareas:
 1 Prueba el c�digo de este proyecto y aseg�rate de que lo comprendes todo bien
 2 A�ade a la clase Persona un atributo nuevo: altura
 3 A�ade un m�todo que devuelva si la persona es alta o baja, dependiendo de su altura
 4 A�ade a la clase Persona el m�todo toString para que muestre los valores de los atributos de forma legible. 
 5 Usa el m�todo toString en el main. 
 6 A�ade a la peluqueria un m�todo para cambiar el color de pelo del objeto Persona que reciba como argumento y pru�balo en el main
 7 A�ade a la clase Persona un m�todo anularCita que env�e un mensaje a la Peluquer�a pidiendo anular
 8 A�ade un mensaje a Peluquer�a para anular la cita (el que es invocado en la tarea anterior), que muestre el mensaje en pantalla "cita anulada"
 9 Si te animas a complicar el ejemplo, crea una clase Cita que guarde informaci�n de la persona que pide la cita y la fecha y hora asignadas.
 Al anular una cita, �sta deber� buscarse en la colecci�n de citas y borrarse.
 */
 