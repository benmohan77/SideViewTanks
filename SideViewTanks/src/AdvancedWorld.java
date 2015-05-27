
public class AdvancedWorld extends BaseWorld
{
	//	public static void main (String[] args) throws Exception
	//	{
	//		AdvancedWorld map = new AdvancedWorld(10, 20, MapType.HILL);
	//		System.out.println(map.toString());
	//	}
	private boolean[][] points;
	int groundHeight;
	public AdvancedWorld(int mapheight, int mapwidth, MapType type)
	{
		this(mapheight, mapwidth, type, 1.0/3.0);
	}
	public AdvancedWorld(int mapheight, int mapwidth, MapType type, int groundHeight)
	{
		this.height = mapheight;
		this.width = mapwidth;
		this.points = new boolean[width][height];
		this.generate(type,groundHeight);
	}
	public AdvancedWorld(int mapheight, int mapwidth, MapType type, double landheightPercentage)
	{
		this(mapheight, mapwidth, type, (int)(landheightPercentage*mapheight));
	}
	public MapType type;
	/**
	 * Generates terrain.<br>
	 */
	public void generate(MapType type, int groundHeight)
	{
		this.groundHeight = groundHeight;
		this.type = type;
		for(int x=0; x<this.width; ++x)
		{
			rand = Math.random();
			for(int y=0; y<this.height; ++y)
			{
				this.points[x][y] = getSetValue(x, y);
			}
		}
		this.settle();
		this.convert();
	}
	private double rand = Math.random();
	private boolean getSetValue(int x, int y)
	{
		//System.out.println("x="+x+" y="+y);
		switch(type)
		{
		case FLAT:
			return y<groundHeight;
		case RANDOM:
			return Math.random()<.5;
		case SMOOTHEST:
			if(x%4==2)
				return points[x-1][y];
		case SMOOTHER:
			if(x%2==1)
				return points[x-1][y];
		case SMOOTH:
			return (y==0) || (x==0 && y<groundHeight) || (x!=0 && (y<count(x-1)-1 || (y==count(x-1) && rand<1.0/3.0) || (y==count(x-1)+1 && rand<2.0/3.0)));
		case SLOPE_INCLINE:
			return ((double)y/(height-1))<=((double)x/width);
		case SLOPE_DECLINE:
			return ((double)y/(height-1))>((double)x/width);
		case HILL:
			return (x<width/2 && ((double)y/(height-1))<=(((double)x+1)/width)) || (x>=width/2 && ((double)y/(height-1))>((double)x/width));
		case VALLEY:
			return (x<width/2 && ((double)y/(height-1))>(((double)x+1)/width)) || (x>=width/2 && ((double)y/(height-1))<=((double)x/width));
		default:
			return false;
		}
	}
	/**Settles terrain*/
	public void settle()
	{
		for(int x=0; x<this.points.length; ++x)
		{
			int count = count(x);
			for(int y=0; y<this.points[x].length; ++y)
			{
				points[x][y] = y<count;
			}
		}
	}
	private void convert()
	{
		ClearWorldToAir();
		for(int x=0; x<width; ++x)
			for(int y=0; y<height; ++y)
				data[y][x] = points[x][y] ? GROUND :AIR;
		data[this.GetHeight()-1][1] = 1;
		data[this.GetHeight()-1][this.GetWidth() - 2] = 2;
	}
	private int count(int x)
	{
		int count = 0;
		for(boolean y : points[x])
			if(y) ++count;
		return count;
	}
	@Override
	public String toString()
	{
		String s="";
		for(int y=this.points[0].length-1; y>=0; --y)
		{
			for(int x=0; x<this.points.length; ++x)
			{
				s += this.data[x][y]==GROUND ? "#" : " ";
			}
			s+=System.lineSeparator();
		}
		return s;
	}
}
enum MapType
{
	FLAT, RANDOM, SMOOTH, SMOOTHER, SMOOTHEST, SLOPE_INCLINE, SLOPE_DECLINE, HILL, VALLEY;
}
