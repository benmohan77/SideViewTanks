


public class SloanTank implements ITank
{
	private int playerNumber = 4;
	
	public SloanTank()
	{
	}
	public int GetMove(IWorld world)
	{
		return 0;
	}
	int myX=-1,multi=0;
	public Shot GetShot(IWorld world)
	{
		if(this.myX==-1)
		for(int[] r : world.GetWorld())
			for(int c : r)
				if(c==playerNumber)
					myX = c;
		Shot s = new Shot();
		s.angle = playerNumber==1 ? 45 : 135;
		s.power = this.getPower(BaseWorld.GRAVITY, Math.toRadians(s.angle), world.GetWidth()-1-2*multi++);
		return s;
	}
	public void SetPlayerNumber(int playerNumber)
	{
		this.playerNumber = playerNumber;
	}
	private double getPower(double GRAVITY, double angleRads, int targetX)
	{
		return Math.sqrt((targetX * 10*GRAVITY) 
				/ (2*Math.tan(angleRads)))/Math.cos(angleRads);
	}
}
