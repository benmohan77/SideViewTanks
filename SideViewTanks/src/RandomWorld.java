
public class RandomWorld extends BaseWorld {

	public RandomWorld() {
		this.BuildWorld();
	}
	
	public RandomWorld(int h, int w) {
		this.height = h;
		this.width = w;
		this.BuildWorld();
	}
	
		public void BuildWorld() {
			ClearWorldToAir();
			
		// loop that goes entire length of multi-dim array
		
		for( int row = 0; row < this.GetHeight() - 1; row++)
			for (int col = 0; col < this.GetWidth(); col++) {
				// randomly generate -1 or 0..
				this.data[row][col] = ((int)Math.round(Math.random())) - 1;
			}
		
		// compact
		this.Compact();

		// put tanks a random distance
		int pos = 0 + (int)((this.GetWidth() / 2)* Math.random()); 
		data[this.GetHeight()-1][pos] = 1;
		
		pos = (this.GetWidth() / 2) + (int)((this.GetWidth() / 2)* Math.random());
		data[this.GetHeight()-1][pos] = 2;

		// compact
		this.Compact();
	}
}
