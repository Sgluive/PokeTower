package map;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Map {
	
	private List<String> _map;

	public void loadMap(String path)
	{
		BufferedReader buff = null;
		String str = null;

		try {
			buff = new BufferedReader(new FileReader("path_to_file"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			while((str = buff.readLine()) != null){
				_map.add(str);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadSpawnerPos(int x, int y)
	{
		for (y = 0; y < _map.size(); y++)
		{
			if ((x = _map.get(y).indexOf('S')) != -1)
				return;
		}
		x = -1;
		y = -1;
	}
	public void loadPikachuPos(int x, int y)
	{
		for (y = 0; y < _map.size(); y++)
		{
			if ((x = _map.get(y).indexOf('P')) != -1)
				return;
		}
		x = -1;
		y = -1;
	}
		
	public boolean canPutTowerAt(int x, int y)
	{
		if (_map.get(y).charAt(x) == ' ')
			return true;
		return false;		
	}

}
