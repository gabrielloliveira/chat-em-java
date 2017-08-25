package servidor;

import java.io.*;
import java.net.*;
import java.util.*;

public class Servidor {
    
    static List<PrintStream> listaDeClientes = new ArrayList<>();

    public static void main(String[] args) {
        
        try{
            ServerSocket servidor = new ServerSocket(12345);
            
            while(true){
                Socket cliente = servidor.accept();
                
                new Thread(() -> {
                    try{
                        System.out.println("Nova conexão com o cliente: "
                        + cliente.getInetAddress().getHostAddress());
                        
                        System.out.println("Hostname: "
                        + cliente.getInetAddress().getHostName());
                        
                        PrintStream clienteSaida = 
                                new PrintStream(cliente.getOutputStream());
                        
                        clienteSaida.println("Conectado com sucesso !");
                        listaDeClientes.add(clienteSaida); 
                        
                        Scanner entrada
                                = new Scanner(cliente.getInputStream());
                        
                        while (entrada.hasNext()){
                            String texto = entrada.nextLine();
                            
                            for(PrintStream sc : listaDeClientes){
                                
                                if (sc != clienteSaida){
                                
                                    sc.println(texto);
                                    
                                }
                                
                            }
                            
                        }
                        
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    
                }).start();
            }
            
        }catch (IOException e){
            e.printStackTrace();
        }
        
    }
    
}
