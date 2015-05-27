// AI tanks must implement this interface
public interface ITank {
		
	// the game world will tell you if you are player 1 or 2
	public void SetPlayerNumber(int playerNumber);
	
	// return -1 to move left, 0 to not move, or 1 to move right
	// IWorld is an interface that describes your view of the world
		public int GetMove(IWorld world);
		
		// called by the game engine to see what the player's shot
		// null means no shot was taken
		public Shot GetShot(IWorld world);
}
