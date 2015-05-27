
public class FlatWorld extends BaseWorld {


	public FlatWorld() {
		this.BuildWorld();
	}
	
	public FlatWorld(int h, int w) {
		this.height = h;
		this.width = w;
		this.BuildWorld();
	}
	
	public void BuildWorld() {
		ClearWorldToAir();
		
		// first, set everything to AIR
		// not needed, Air == 0, the default
		
		// then, build a flat ground, as wide as the map
		// and about 25% high
		
		int groundHeight = Math.min(4, this.GetHeight() / 4);
		
		// for each row, set all the column values to ground
		for (int i = 0; i < groundHeight; i++) {
			for (int j = 0; j < this.GetWidth(); j++) {
				data[i][j] = -1; // GROUND
			}
		}
		
		// now, we need to place the tanks
		// player 1 should be on row 5, column 1
		data[groundHeight][1] = 1;
		
		
		
		// player 2 should be on row 5, column edge - 1
		data[groundHeight][this.GetWidth() - 2] = 2;

		
	}


}
		
	

