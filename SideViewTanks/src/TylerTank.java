public class TylerTank implements ITank {

	private int playerNumber = 1;
	
	@Override
	public int GetMove(IWorld world) {
		if (playerNumber == 1)
			return 1;
		else
			return -1;
	}

	@Override
	public Shot GetShot(IWorld world) {
		Shot s = new Shot();
		s.angle = Math.floor(Math.random()*101);
		s.angle += 30.0*Math.random();
		s.power = 30 + 30*Math.random();
		return s;
	}

	@Override
	public void SetPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}

	
	
}
