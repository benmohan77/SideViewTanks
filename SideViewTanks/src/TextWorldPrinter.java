public class TextWorldPrinter implements IWorldPrinter {

	@Override
	public void Print(IWorld world) {

		char c = ' ';
		int[][] data = world.GetWorld();

		System.out.println();
		
		// print out the world
		for (int i = world.GetHeight() - 1; i >= 0; i--) {
			System.out.print('|');
			for (int j = 0; j < world.GetWidth(); j++) {
				c = ' ';
				// switch is a multi-level if-then-else construct
				switch (data[i][j]) {
				case 0: {
					// technically, redundant
					c = ' ';
					break;
				}
				case -1: {
					c = 'X';
					break;
				}
				case 1: {
					c = '1';
					break;
				}
				case 2: {
					c = '2';
					break;
				}
				case 3: {
					c = '*';
					break;
				}
				}
				System.out.print(c);
			}
			System.out.println('|');
		}
		System.out.println();
	}

	@Override
	public void PrintWinner(int winner) {
		// TODO Auto-generated method stub
		System.out.println("Game Over.  Player " + winner + " wins");
	}

}
