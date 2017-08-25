package cliente;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) {
        try{
            Scanner teclado = new Scanner(System.in);
            System.out.println("Digite seu nome: ");
            String nome = teclado.nextLine();
            
            Socket conexaoServidor = new Socket("IP do servidor", 12345);
            
            new Thread(() ->{
                try{
                    
                    Scanner servidorEntrada = 
                            new Scanner(conexaoServidor.getInputStream());
                    
                    while(servidorEntrada.hasNext()){
                        String texto = servidorEntrada.nextLine();
                        System.out.println(texto);
                    }
                    
                }catch (IOException e){
                    e.printStackTrace();
                }
            }).start();
            
            PrintStream servidorSaida = 
                    new PrintStream(conexaoServidor.getOutputStream());
            
            while(teclado.hasNextLine()){
                servidorSaida.println(nome + " : "+ teclado.nextLine());
            }
            
        }catch (IOException e){
            e.printStackTrace();
        }
        
    }
    
}
