// a representation of the world
public interface IWorld {
	
	// get the dimensions of the game world
	public int GetHeight();
	public int GetWidth();
	
	// the cells of the world
	// a cell is
	// 0 - air
	// -1 - ground
	// 1 - player 1
	// 2 - player 2
	// 3 - path of a recent shot
	public int[][] GetWorld();
	
	// the player would like to know their own location in the world...
	//public ? GetMyLocation();
}
