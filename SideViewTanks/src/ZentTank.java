import java.awt.Point;


public class ZentTank implements ITank{
	private int timesMoved = 0;
	private int MyPlayer = 0;
	
	@Override
	public void SetPlayerNumber(int playerNumber) {

		MyPlayer = playerNumber;
		
	}

	@Override
	public int GetMove(IWorld world) {
		timesMoved+=1;
		return 0;
	}

	@Override
	public Shot GetShot(IWorld world) {
		
		int[][] copy = world.GetWorld();
		Point myP = new Point();
		Point enP = new Point();
		for(int r = 0;r < world.GetHeight();r++){
			for(int c = 0;c < world.GetWidth();c++){
				if(copy[r][c] == 1){
					if(MyPlayer == 1){
						myP = new Point(c,r);
					} else {
						enP = new Point(c,r);
					}
				}else if(copy[r][c] == 2){
					if(MyPlayer == 2){
						myP = new Point(c,r);
					} else {
						enP = new Point(c,r);
					}
				}
			}
		}
		double x = (enP.getX() - myP.getX())*10;
		double theta = 0;
		if(x < 0){
			theta = 91;
		} else {
			theta = 89;
		}
		
		Shot WinningShot = new Shot();
		WinningShot.angle = theta;
		WinningShot.power = velocityNeeded(enP, myP, Math.toRadians(theta), x);
		
		return WinningShot;
	}
	
	private static double velocityNeeded(Point enemy, Point me,double t,double x){
		double t1 = -10.0*(x*x);
		double t2 = enemy.getY()*10.0-me.getY()*10.0-x*Math.tan(t);
		return Math.sqrt(t1/(2.0*(t2)))/Math.cos(t);
		
	}

}
