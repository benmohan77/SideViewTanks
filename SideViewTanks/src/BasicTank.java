
public class BasicTank implements ITank {

	private int playerNumber = 1;
	
	@Override
	public int GetMove(IWorld world) {

//		// hey is someone shooting at me?
//		int data[][] = world.GetWorld();
//		
//		for (int row = 0; row < world.GetHeight(); row++)
//			for (int col = 0; col < world.GetWidth(); col++) {
//				if (data[row][col] == BaseWorld.SHOT) {
//					System.out.println("DUDE WHERES MY SHOT");
//				}
//			}
		
		if (playerNumber == 1)
			return 1;
		else
			return -1;
	}

	@Override
	public Shot GetShot(IWorld world) {
		// TODO Auto-generated method stub
		Shot s = new Shot();
		if (playerNumber == 1) 
			s.angle = 35;
		else
			s.angle = 115;

		s.angle += 30.0*Math.random();
		
		s.power = 30 + 30*Math.random();
		
		return s;
	}

	@Override
	public void SetPlayerNumber(int playerNumber) {
		// TODO Auto-generated method stub
		this.playerNumber = playerNumber;
	}

	
	
}
