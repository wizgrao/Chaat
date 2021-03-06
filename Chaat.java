import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

public class Chaat {
	static Vector<PrintStream> connections;
	static HashMap<String, String> names;
	public static void main(String[] args) {
		names = new HashMap<>();
		connections = new Vector<>();
		try {
			ServerSocket chatter = new ServerSocket(9090);
			connections.add(System.out);
			while (true) {

				new Thread(new Handler(chatter.accept())).start();;

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	static class Handler implements Runnable {
		Socket s;
		public Handler(Socket s) {
			this.s = s;
		}
		synchronized void say(String message) {
			for (PrintStream p : connections) {
				p.println(message);
			}
		}
		@Override
		public void run() {
			String name = "";

			PrintStream out;

			try {
				out = new PrintStream(s.getOutputStream());

				boolean newName = false;
				Scanner b = new Scanner(new InputStreamReader(
						s.getInputStream()));
				while (!newName) {
					out.println("Enter Username: ");
					name = b.nextLine();
					if (!names.containsKey(name)) {
						names.put(name, name);
						connections.add(out);
						say(name + " connected");
						newName = true;
					}
				}

				while (b.hasNextLine()) {
					String sss = b.nextLine();
					say(name + ": " + sss);
				}
				names.remove(name);
				connections.remove(out);
				say(name + " disconnected");

				b.close();
				out.close();
				s.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}

