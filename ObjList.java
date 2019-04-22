import java.util.ArrayList;

public class ObjList 
{
	public ArrayList <Integer>objlist;
	public void setListVariable(ArrayList<Integer> temp)
	{
		objlist = new ArrayList<Integer>();
		for(int i=0;i<temp.size();i++)
		{
			objlist.add(temp.get(i));
		}
	}
}