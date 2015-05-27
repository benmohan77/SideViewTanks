public class BenTank implements ITank {
	private int playerNumber = 1;
	private int movecount = 0;
	private int myAngle;
	@Override
	public void SetPlayerNumber(int playerNumber) {
		// TODO Auto-generated method stub
		this.playerNumber = playerNumber;
	}

	@Override
	public int GetMove(IWorld world) {
		/*
		 * if(playerNumber == 1){ if(movecount < 40){ return 1; } else{ return
		 * -1; } } else{ if(movecount < 40){ return -1; } else{ return 1; } }
		 */
		return 0;

	}

	@Override
	public Shot GetShot(IWorld world) {
		movecount += 1;
		Shot s = new Shot();
		if (playerNumber == 1 && movecount < 2) {
			myAngle = 89;
		} else if (playerNumber == 2 && movecount < 2) {
			myAngle = 91;
		} else if(playerNumber == 1 && movecount >= 2 && myAngle > 0){
			myAngle -= 5;
		} else if(playerNumber == 2 && movecount >= 2 && myAngle < 180){
			myAngle += 5;
		} else if(myAngle <= 0 || myAngle >= 180){
			if(playerNumber == 1){
				myAngle = 89;
			} else {
				myAngle = 91;
			}
		}
		s.angle = myAngle;
		s.power = 60;
		System.out.println(s.angle);
		return s;
	}

}
