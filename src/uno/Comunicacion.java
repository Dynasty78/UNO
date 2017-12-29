package uno;

import com.fazecast.jSerialComm.SerialPort;
import java.io.InputStream;
import java.util.ArrayList;

public class Comunicacion {
  
    SerialPort puertoEntrada;
    SerialPort puertoSalida;
    
    public Comunicacion(int entrada, int salida){
        
        puertoEntrada = SerialPort.getCommPorts()[entrada];
        puertoEntrada.setComPortParameters(2400, 8, 0, 1);
        puertoEntrada.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 1, 1);
        puertoEntrada.openPort();
        if(entrada != salida){
           puertoSalida = SerialPort.getCommPorts()[salida];
           puertoSalida.setComPortParameters(2400, 8, 0, 1);
           puertoSalida.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 1, 1);
           puertoSalida.openPort(); 
       }
       else{
          puertoSalida = puertoEntrada;
       }
    }
    
    //Otro metodo para recibir tramas, lo saque de los ejemplos que vienen con la libreria
    //Funciona
    
    public byte[] recibirB(){
         byte[] readBuffer = null;
         try {
                while (puertoEntrada.bytesAvailable() == 0) 
                   Thread.sleep(20);
                
                readBuffer = new byte[puertoEntrada.bytesAvailable()];
                int numRead = puertoEntrada.readBytes(readBuffer, readBuffer.length);
                System.out.print("Read " + numRead + " bytes.");
                             
         } 
         catch (Exception e) {
            e.printStackTrace();
         }
         return readBuffer;
     }
    
    //Metodo que utiliza InputStream regresa un String con la trama recibida
    //Coloque 8 caracteres nada mas por ahora se puede cambiar
   
    public String recibir(){
    InputStream in = puertoEntrada.getInputStream();
    String trama = "";
    try
    {
        for (int j = 0; j < 8; ++j)
            
            trama += (char)in.read();
             in.close();
    } catch (Exception e) { e.printStackTrace(); }
    
    return trama;
    }
     
    //Metodo que envia informacion
     public void enviar(byte[] writeBuffer){
         try {
             puertoSalida.writeBytes(writeBuffer, writeBuffer.length);
         } catch (Exception e) {
             e.printStackTrace();
         }
     }   
     
     // Lista de puertos
     public static ArrayList<String> listaPuertos(){
        
          ArrayList<String> puertos = new ArrayList<String>();
          SerialPort[] comPort = SerialPort.getCommPorts();
          for (int i=0; i<comPort.length; i++){
             puertos.add(comPort[i].getDescriptivePortName());
          }
         return puertos;
     }
}
