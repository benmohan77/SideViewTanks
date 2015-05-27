
public interface IGameWorld {
	public void MovePlayer(int playerNum, int move);
	
	public void DoShot(int playerNum, Shot shot);
	
	public void Compact();
	
	public void ClearShotArcs();
	
	public int GetWinner();

	public IWorld GetWorldViewForPlayer(int i);
}
