package spawner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Spawner {
	
	private List<Wave> _waves;
	
	public Spawner()
	{
		this._waves = new ArrayList<Wave>();
	}
	
	public void loadSpawner(InputStream inputStream)
	{
		BufferedReader buff = null;
		InputStreamReader ipsr = null;
		String str = null;
		Wave wave = null;

		ipsr = new InputStreamReader(inputStream);
		buff = new BufferedReader(ipsr);
		try {
			while((str = buff.readLine()) != null){
				if (str.contains("WAVE"))
				{
					wave = new Wave();
					this._waves.add(wave);
				}
				else
				{
					if (wave != null)
						wave.addItem(str);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Wave pop()
	{
		Wave wave;
		
		wave = this._waves.get(0);
		if (wave != null)
			this._waves.remove(0);
		return wave;
	}
	
	public boolean isEmpty()
	{
		return (this._waves.isEmpty());
	}

}
