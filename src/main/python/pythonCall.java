 import java.io.*;
 
class pythonCall{
public static void main(String a[]){
try{
 
ProcessBuilder pb = new ProcessBuilder("python","predict.py");
Process p = pb.start();
 
BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
System.out.println("OK!!");
}catch(Exception e){System.out.println(e);}
}
}
