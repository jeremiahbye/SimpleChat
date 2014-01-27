package me.chris.SimpleChat;

import java.util.ArrayList;

public class CustomStringList extends ArrayList<String>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8614307338968587810L;

	@Override
	public boolean contains(Object o)
	{
		String paramStr = (String) o;
		for (String s : this)
		{
			if (paramStr.equalsIgnoreCase(s))
				return true;
		}
		return false;
	}
}
