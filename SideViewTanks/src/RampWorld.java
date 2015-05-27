
public class RampWorld extends BaseWorld {

	public RampWorld() {
		this.BuildWorld();
	}
	
	public RampWorld(int h, int w) {
		this.height = h;
		this.width = w;
		this.BuildWorld();
	}
	
	public void BuildWorld() {
		this.ClearWorldToAir();
		
		// a ramp world is shaped like a big pyramid
		// first, set the bottom 20% to solid ground
		
		int groundHeight = this.GetHeight() / 5;
		
		// for each row, set all the column values to ground
		for (int i = 0; i < groundHeight; i++) {
			for (int j = 0; j < this.GetWidth(); j++) {
				data[i][j] = GROUND; 
			}
		}
		
		// now, start the ramp...
		int rowAdd = 0;
		for (rowAdd = 0; rowAdd < this.GetWidth() / 2; rowAdd++) {
			for (int col = rowAdd*3; col < this.GetWidth() /2 ; col++) { 
				data[groundHeight + rowAdd][col] = GROUND;
			}
		}

		//FINISH THE RAMP
//		for (int col = this.GetWidth() -1; col > this.GetWidth() / 2; col--) {
//			for (rowAdd = 0; rowAdd < this.GetWidth() / 2; rowAdd++) {
//				data[groundHeight + rowAdd][col] = GROUND;
//			}
//		}
//		
		// now, we need to place the tanks
		// player 1 should be on row 5, column 1
		data[this.GetHeight() - 1][1] = 1;
		
		this.CompactColumn(1);
		
		// player 2 should be on row 5, column edge - 1
		data[this.GetHeight() - 1][this.GetWidth() - 2] = 2;
		this.CompactColumn(this.GetWidth() -2);
	}
	
}
