package spawner;

public class WaveItem {
	
	private int _timeToSpawn;
	private int _number;
	private String _name;
	
	WaveItem(String line)
	{
		String caracs[] = line.split("\\|");;

		this._timeToSpawn = Integer.parseInt(caracs[0]);
		this._name = caracs[1];
		this._number = Integer.parseInt(caracs[2]);
	}
	
	public int getTimeToSpawn()
	{
		return this._timeToSpawn;
	}
	
	public int getNumber()
	{
		return this._number;
	}
	
	public String getName()
	{
		return this._name;
	}

}
