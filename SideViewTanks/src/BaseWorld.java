
public abstract class BaseWorld implements IWorld, IGameWorld {

	public static final double GRAVITY = 10.0;
	public static final int PlayerViewDistance = 2;
	public static final int ShotViewDistance = 4;
	private static final int ENEMYADJUSTMENT = 10;
	private static final int ENEMYWINDOW = 10;
	
	// what the cells in the world data mean...
	public static final int GROUND = -1;
	public static final int AIR = 0;
	//Player 1 = 1
	//Player 2 = 2
	public static final int SHOT = 3;
	public static final int UNKNOWN = 4;

	protected int[][] data; 

	protected int height = 20;
	protected int width = 20;

	protected void ClearWorldToAir() {
		data = new int[this.height][this.width];
	}

	public void ClearShotArcs() {
		for (int row = 0; row < this.GetHeight(); row++)
			for (int col = 0; col < this.GetWidth(); col++) {
				if (this.data[row][col] == SHOT)
					this.data[row][col] = AIR;
			}
	}

	// re-stack the world objects because of gravity..
	@Override
	public void Compact() {
		// start at the leftmost column...
		for (int col = 0; col < this.GetWidth(); col++) {
			CompactColumn(col);
		}
	}

	//  -1     0
	//  -0     0
	//   0 -> -1
	//  -1    -1

	// given a vertical column, compact the elements due to gravity.. (remove all the air gaps)
	protected void CompactColumn(int col) {
		// now, start at the bottom, and shift things downward to remove gaps.
		int[] compacted = new int[this.GetHeight()];
		int cptr = 0;

		// copy the non-AIR elements, in order, into a new column...
		for (int row = 0; row < this.GetHeight(); row++) {
			if ((data[row][col] != AIR) && (data[row][col] != SHOT)) {
				compacted[cptr++] = data[row][col];
			}
			// copy the original shot arc also
			if (data[row][col] == SHOT) {
				compacted[row] = SHOT;
			}
		}

		// replace our column with the new column...
		for (int row = 0; row < this.GetHeight(); row++) {
			data[row][col] = compacted[row];
		}
	}



	@Override
	public int[][] GetWorld() {
		// data.clone() only clones the first dimension of a 2d array... not the inner arrays
		int[][] retval = data.clone();

		for(int i = 0; i < this.GetHeight(); i++) {
			retval[i] = data[i].clone();
		}

		return retval;
	}

	@Override
	public int GetHeight() {
		// TODO Auto-generated method stub
		return this.height;
	}

	@Override
	public int GetWidth() {
		// TODO Auto-generated method stub
		return this.width;
	}

	// return a view of the world limited to what the player should see...
	public IWorld GetWorldViewForPlayer(int playerNumber) {
		IWorld returnValue = null;
		boolean shotsFired = false;

		// build a false world
		returnValue = new FakeWorldView(this.GetHeight(), this.GetWidth());

		int playerRow = 0;
		int playerColumn = 0;

		int enemyRow = 0;
		int enemyColumn = 0;
		
		// determine the number of the enemy player.  Save it for later.
		int enemyPlayerNumber = (playerNumber == 1) ? 2 : 1;
		

		// tell the player their own location
		for (int i = 0; i < this.GetHeight(); i++)
			for (int j = 0; j < this.GetWidth(); j++) {
				if(this.data[i][j] == playerNumber) {
					returnValue.GetWorld()[i][j] = playerNumber;
					// save their location for later..
					playerRow = i;
					playerColumn = j; 
				}

				// if we come across a shot arc, remember it
				if (this.data[i][j] == SHOT) {
					shotsFired = true;
				}

				if (this.data[i][i] == enemyPlayerNumber)
				{
					enemyRow = i;
					enemyColumn = j;
				}
			}

		// tell the player about their immediate surroundings...
		int startRow = Math.max(playerRow - PlayerViewDistance, 0);
		int endRow = Math.min(playerRow + PlayerViewDistance + 1, this.GetHeight() - 1);
		int startCol = Math.max(playerColumn - PlayerViewDistance,0);
		int endCol = Math.min(playerColumn + PlayerViewDistance + 1 ,this.GetWidth() -1);

		// copy a sub-region of the real world into the fake world..
		for (int i = startRow; i < endRow; i++)
			for (int j = startCol; j < endCol; j++) {
				returnValue.GetWorld()[i][j] = this.data[i][j];
			}

		if (shotsFired) {
			// copy a region of shot-arc data..
			startRow = Math.max(playerRow - ShotViewDistance, 0);
			endRow = Math.min(playerRow + ShotViewDistance + 1, this.GetHeight() - 1);
			startCol = Math.max(playerColumn - ShotViewDistance,0);
			endCol = Math.min(playerColumn + ShotViewDistance + 1,this.GetWidth() -1);

			// copy a sub-region of the real world into the fake world..
			// but only if it is a previous shot arc...
			for (int i = startRow; i < endRow; i++)
				for (int j = startCol; j < endCol; j++) {
					if (this.data[i][j] == SHOT)
						returnValue.GetWorld()[i][j] = SHOT;
				}
			
			/// obfuscate location of the enemy 
			int xadjustment = (int)((Math.random() - 0.5) * ENEMYADJUSTMENT);
			int yadjustment = (int)((Math.random() - 0.5) * ENEMYADJUSTMENT);
	
			int fakeRow = enemyRow + xadjustment;
			int fakeColumn = enemyColumn + yadjustment;
			
			// copy a region of data near the enemy's location...
			startRow = Math.max(fakeRow - ENEMYWINDOW, 0);
			endRow = Math.min(fakeRow + ENEMYWINDOW + 1, this.GetHeight() - 1);
			startCol = Math.max(fakeColumn - ENEMYWINDOW,0);
			endCol = Math.min(fakeColumn + ENEMYWINDOW + 1,this.GetWidth() -1);
			
			// copy a sub-region of the real world into the fake world..
			for (int i = startRow; i < endRow; i++)
				for (int j = startCol; j < endCol; j++) {
						returnValue.GetWorld()[i][j] = this.data[i][j];
						if (this.data[i][j] == enemyPlayerNumber)
							returnValue.GetWorld()[i][j] = AIR;
				}

		}

		return returnValue;
	}

	@Override
	public void MovePlayer(int playerNum, int move) {

		// handle the easy case
		if (move == 0)
			return;

		// otherwise
		// find the cell with playerNum
		boolean found = false;
		int playerColumn = 0;
		int playerRow = 0;
		for (playerRow = 0; !found && (playerRow < this.GetHeight()); playerRow++) {
			for (playerColumn = 0; !found && (playerColumn < this.GetWidth()); playerColumn++) {
				if (data[playerRow][playerColumn] == playerNum) {
					found = true;
				}
			}
		}
		playerRow--;
		playerColumn--;

		// moving left?
		if (move == -1) {

			if (playerColumn == 0)
				return; // left edge of world

			// get the left cell
			int targetCell = data[playerRow][playerColumn-1];

			switch (targetCell) {
			case AIR:
				// its air.  they can move
				data[playerRow][playerColumn-1] = playerNum;
				data[playerRow][playerColumn] = AIR; // set old location to air
				// TODO: see if they need to fall..
				this.CompactColumn(playerColumn-1);
				break;
			case 1:
			case 2:
				// its player 1 or 2.  disallow the move
				break;
			case GROUND:
				// its ground..
				// evaluate the cell that is "above" the target cell
				int targetRow = playerRow + 1;
				if (targetRow >= this.GetHeight()) {
					// they are already at the top of the world.. disallow the move
					break;
				}

				targetCell = data[playerRow + 1][playerColumn -1];
				if (targetCell == AIR) {
					// allow the move
					data[playerRow + 1][playerColumn -1] = playerNum;
					data[playerRow][playerColumn] = AIR; // set old location to air
				}
			}
		}

		else {
			//moving right
			if (playerColumn == this.GetWidth() - 1)
				return; // left edge of world

			// get the left cell
			int targetCell = data[playerRow][playerColumn+1];

			switch (targetCell) {
			case AIR:
				// its air.  they can move
				data[playerRow][playerColumn+1] = playerNum;
				data[playerRow][playerColumn] = AIR; // set old location to air
				// TODO: see if they need to fall..
				this.CompactColumn(playerColumn+1);
				break;
			case 1:
			case 2:
				// its player 1 or 2.  disallow the move
				break;
			case GROUND:
				// its ground..
				// evaluate the cell that is "above" the target cell
				int targetRow = playerRow + 1;
				if (targetRow >= this.GetHeight()) {
					// they are already at the top of the world.. disallow the move
					break;
				}

				targetCell = data[playerRow + 1][playerColumn +1];
				if (targetCell == AIR) {
					// allow the move
					data[playerRow + 1][playerColumn +1] = playerNum;
					data[playerRow][playerColumn] = AIR; // set old location to air
				}
			}
		}

	}

	@Override
	public int GetWinner() {
		// TODO Auto-generated method stub
		boolean foundp1 = false;
		boolean foundp2 = false;

		for (int row = 0; row < this.GetHeight(); row++)
			for (int col = 0; col < this.GetWidth(); col++) {
				if (data[row][col] == 1)
					foundp1 = true;
				if (data[row][col] == 2)
					foundp2 = true;
			}

		if (foundp2 == false)
			return 1;

		if (foundp1 == false)
			return 2;

		return 0;
	}

	@Override
	public void DoShot(int playerNum, Shot shot) {
		if (shot == null)
			return;

		// find player row/col

		// TODO: this is cut and pasted from Move above... 
		boolean found = false;
		int playerColumn = 0;
		int playerRow = 0;
		for (playerRow = 0; !found && (playerRow < this.GetHeight()); playerRow++) {
			for (playerColumn = 0; !found && (playerColumn < this.GetWidth()); playerColumn++) {
				if (data[playerRow][playerColumn] == playerNum) {
					found = true;
				}
			}
		}
		playerRow--;
		playerColumn--;

		double shotStartY = playerRow * 10;

		double y = 0;

		double angleRads = Math.toRadians(shot.angle);

		int targetX = 0;
		int targetY = 0;

		// shooting to the right
		if (shot.angle < 90) {
			// how many steps to evaluate?
			for (int i = 1; i < this.GetWidth() - playerColumn; i++) {

				// ballistic math
				y = getYValueForBallisticShot(shot.power, shotStartY, angleRads, i);

				targetX = playerColumn + i;
				targetY = (int)(y / 10.0);

				if (!SetShotArc(targetX, targetY))
					break;
			}
		}
		else if (shot.angle > 90) {
			// shooting to the left...
			for (int i = 1; i < this.GetWidth(); i++) {
				y = getYValueForBallisticShot(shot.power, shotStartY, angleRads, i*-1);
				targetX = playerColumn - i;
				targetY = (int)(y / 10.0);

				if (!SetShotArc(targetX, targetY))
					break;
			}
		}

		// if we do this, shot arcs aren't visible...
		//this.Compact();
	}

	// the shot has crossed through X,Y
	// update the world appropriately..
	// returns: TRUE if the shot should continue travelling
	// FALSE if the shot stops at this position
	protected boolean SetShotArc(int targetX, int targetY) {
		if (
				(targetX >= 0)
				&&
				(targetX < this.GetWidth())
				&&
				(targetY >= 0)
				&&
				(targetY < this.GetHeight())
				)
		{
			if (data[targetY][targetX] == AIR)
			{
				data[targetY][targetX] = SHOT;
				return true;
			}
			else if (data[targetY][targetX] == GROUND)
			{
				data[targetY][targetX] = AIR;
				this.CompactColumn(targetX);
				// if this returns FALSE, a shot will only blow up 1 block
				// if it returns TRUE, a shot can blow up multiple blocks until it goes out of bounds...
				return false;
			}
			else if (data[targetY][targetX] == 2) {
				data[targetY][targetX] = AIR;
				return false;
			}
			else if (data[targetY][targetX] == 1) {
				data[targetY][targetX] = AIR;
				return false;
			}

		}
		return true;
	}

	// calculate the y value for a given shot at a given distance
	protected double getYValueForBallisticShot(double power, double shotStartY,
			double angleRads, int i) {
		double y;
		double x;
		double term1;
		double term2;
		double term2d;
		x = i * 10;
		term1 = x * Math.tan(angleRads);
		term2d = Math.cos(angleRads) * power;
		term2 = (x * x * GRAVITY) / (2 * term2d * term2d);
		y = shotStartY + term1 - term2;

		// x and y are 10x the row and column position...e.g. a cell is 10x10
		return y;
	}

}






