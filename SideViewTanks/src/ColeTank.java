
public class ColeTank implements ITank {

	private int playerNumber = 1;
	
	@Override
	public int GetMove(IWorld world) {
		// TODO Auto-generated method stub
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
			s.angle = 1;
		else
			s.angle =179;

	
		
		s.power = 300;
		return s;
	}

	@Override
	public void SetPlayerNumber(int playerNumber) {
		// TODO Auto-generated method stub
		this.playerNumber = playerNumber;
	}

	
	
}
