
public class FakeWorldView implements IWorld {

	private int rows = 20;
	private int cols = 20;
	private int data[][];
	
	public FakeWorldView(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		this.data = new int[this.rows][this.cols];
		this.BuildWorld();
	}
	
	private void BuildWorld() {
		// set everything to unknown...
		for (int i = 0; i < this.GetHeight(); i++)
			for (int j = 0; j < this.GetWidth(); j++) {
				this.data[i][j] = BaseWorld.UNKNOWN;
			}
	}
	
	@Override
	public int GetHeight() {
		// TODO Auto-generated method stub
		return this.rows;
	}

	@Override
	public int GetWidth() {
		// TODO Auto-generated method stub
		return this.cols;
	}

	@Override
	public int[][] GetWorld() {
		// TODO Auto-generated method stub
		return this.data;
	}

}
