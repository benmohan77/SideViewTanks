import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BenGuiPrinter extends JPanel implements IWorldPrinter {
	private Image dirt;
	private Image p1;
	private Image p2;
	private Image shot;
	private Image p1Left;
	private Image p2Left;
	private IWorld myWorld = null;
	private boolean won = false;
	private int winplayer;

	public BenGuiPrinter() {
		setFocusable(true);
		ImageIcon ii = new ImageIcon(this.getClass().getResource("dirt.png"));
		ImageIcon player1 = new ImageIcon(this.getClass().getResource(
				"P1tank.png"));
		ImageIcon player2 = new ImageIcon(this.getClass().getResource(
				"P2tank.png"));
		ImageIcon s = new ImageIcon(this.getClass().getResource("shot.png"));
		ImageIcon player1left = new ImageIcon(this.getClass().getResource("P1tankleft.png"));
		ImageIcon player2left = new ImageIcon(this.getClass().getResource("P2tankleft.png"));

		shot = s.getImage();
		dirt = ii.getImage();
		p1 = player1.getImage();
		p2 = player2.getImage();
		p1Left = player1left.getImage();
		p2Left = player2left.getImage();
		setBackground(Color.CYAN);
		setDoubleBuffered(true);

	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		// Toolkit.getDefaultToolkit().sync();
		
		if (myWorld != null) {
			if (won == true) {
				g.drawString("Player " + winplayer + " Wins", 300, 200);
			}
			int[][] data = myWorld.GetWorld();

			g.setColor(Color.black);
			g.drawRect(24, 24, 26, 26);
			g.drawString("Player 1", 60, 43);
			g.drawRect(24, 59, 26, 26);
			g.drawString("Player 2", 60, 78);

			g.setColor(Color.RED);
			g.fillRect(25, 60, 25, 25);

			g.setColor(Color.GREEN);
			g.fillRect(25, 25, 25, 25);
			for (int i = 0; i < myWorld.GetHeight(); i++) {
				for (int j = myWorld.GetWidth() - 1; j >= 0; j--) {
					switch (data[i][j]) {
					case 0: {
						// technically, redundant
						break;
					}
					case -1: {
						g.drawImage(dirt, j * 10,
								(myWorld.GetHeight() - i) * 10, null);
						break;
					}
					case 1: {
						if(GameSimulator.getP1Direction() == 1){
							g.drawImage(p1, j * 10, (myWorld.GetHeight() - i) * 10,
									null);
						} else {
							g.drawImage(p1Left, j * 10, (myWorld.GetHeight() - i) * 10,
									null);
						}
						
						break;
					}
					case 2: {
						if(GameSimulator.getP2Direction() == 1){
							g.drawImage(p2, j * 10, (myWorld.GetHeight() - i) * 10,
									null);
						} else {
							g.drawImage(p2Left, j * 10, (myWorld.GetHeight() - i) * 10,
									null);
						}
						
						break;
					}
					case 3: {
						g.drawImage(shot, j * 10,
								(myWorld.GetHeight() - i) * 10, null);
						break;
					}
					}

				}
			}
		}

		Toolkit.getDefaultToolkit().sync();

		g.dispose();
	}

	public void Print(IWorld world) {
		this.myWorld = world;
		this.repaint();
	}

	public void PrintWinner(int winner) {
		winplayer = winner;
		won = true;
	}

}
