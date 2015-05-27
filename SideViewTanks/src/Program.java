import java.awt.EventQueue;

import javax.swing.JFrame;

public class Program extends JFrame {
	private static boolean gui = true;

	public static Program p = null;
	public BenGuiPrinter bgp = null;
	
	private boolean initted = false;

	public Program() {
		if (!this.initted) {
			initUI();
		}
	}

	private void initUI() {
		this.bgp = new BenGuiPrinter();
		add(this.bgp);
		setSize(706, 438);
		setResizable(false);
		setTitle("Tank Battle");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		this.initted = true;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Program.p = new Program();
		
		// this sets the world
		IGameWorld world = new AdvancedWorld(40, 70, MapType.SMOOTHEST);

		if (gui) {
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					Program.p.setVisible(true);
				}
			});
		}

		GameSimulator g = new GameSimulator(new BasicTank(), new ColeTank(),
				world, p.bgp);

		g.Doit();
	}

}
