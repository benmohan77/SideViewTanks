
public class GameSimulator {
	// this is the main game loop
	public static final double MAX_SHOT_POWER = 70.0;
	
	private ITank p1;
	private ITank p2;
	private IGameWorld gameWorld;
	private IWorld playerWorld;
	private IWorldPrinter myPrinter;
	private static int p1Direction;
	private static int p2Direction;

	public GameSimulator (ITank player1, ITank player2, IGameWorld world, IWorldPrinter printer) {
		this.p1 = player1;
		this.p2 = player2;
		this.gameWorld = world;
		this.playerWorld = (IWorld)world;
		this.myPrinter = printer;
	}
	public static int getP1Direction(){
		return p1Direction;
	}
	public static int getP2Direction(){
		return p2Direction;
	}
	public void Doit() {

		int move = 0;

		Shot shot;

		this.p1.SetPlayerNumber(1);
		this.p2.SetPlayerNumber(2);

		ITank[] players = new ITank[2];
		players[0] = this.p1;
		players[1] = this.p2;

		int winner = 0;

		IWorld fakeWorld = null;
		
		while (winner == 0) {
			for (int i = 0; i < 2; i++) {

				fakeWorld = gameWorld.GetWorldViewForPlayer(i+1);

				move = players[i].GetMove(fakeWorld);
				gameWorld.MovePlayer(i+1, move);
				myPrinter.Print(playerWorld);

				// this is needed to make the tanks face the correct direction in the gui printer
				if(i == 0){
					p1Direction = move;
				} else if( i == 1){
					p2Direction = move;
				}

				shot = players[i].GetShot(fakeWorld);
				shot.power = Math.abs(shot.power);
				
				// clamp the shot power
				shot.power = Math.min(shot.power, MAX_SHOT_POWER);
				
				gameWorld.ClearShotArcs();
				
				gameWorld.DoShot(i+1, shot);
				myPrinter.Print(playerWorld);

				// sleep long enough to show the frame
				try{
					Thread.sleep(200);
				}catch(Exception e)
				{
					System.out.println("Exception caught");
				}

				// erase the shot arc and apply gravity
				gameWorld.Compact();
				
				myPrinter.Print(playerWorld);

				// TODO: do i need to check right after the first shot ?
				//is the game over?
				winner = gameWorld.GetWinner();
				if (winner > 0) {
					break;
				}
			}
		}
		myPrinter.PrintWinner(winner);
	}
}
