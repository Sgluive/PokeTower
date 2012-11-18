package spawner;

import java.util.ArrayList;
import java.util.List;

public class Wave {
	
	private List<WaveItem> _items;
	
	public Wave()
	{
		this._items = new ArrayList<WaveItem>();
	}
	
	public void addItem(String line)
	{
		WaveItem item = new WaveItem(line);
		this._items.add(item);
	}
	
	public WaveItem pop()
	{
		WaveItem item;
		
		item = this._items.get(0);
		if (item != null)
			this._items.remove(0);
		return item;
	}
	
	public boolean isEmpty()
	{
		return (this._items.isEmpty());
	}

}
