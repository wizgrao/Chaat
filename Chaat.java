public class Chaat {
	static Vector<PrintStream> connections;
	public static void main(String[] args) {
		connections = new Vector();
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
		@Override
		public void run() {
			System.out.println("Connected");
			PrintStream out;

			try {
				out = new PrintStream(s.getOutputStream());
				connections.add(out);

				Scanner b = new Scanner(new InputStreamReader(
						s.getInputStream()));

				while (b.hasNextLine()) {
					String sss = b.nextLine();
					for (PrintStream p : connections) {
						p.println(sss);
					}
				}
				s.close();
				out.close();
				s.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}
