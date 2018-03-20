
public class Expert {

	public static void main(String[] args) {
try {
			
			ProcessBuilder pb = new ProcessBuilder("python", "./src/main/python/svr.py");
Process p = pb.start();
p.waitFor();
System.out.println("OK");




		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
