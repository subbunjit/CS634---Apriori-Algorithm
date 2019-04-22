import java.io.*;
import java.util.*;
import java.util.Map.*;

public class Apriori
{
		//Concatenating each item specified in the data to the HashMap
		public static boolean additems(HashMap<String,Integer>items,String itemset_arr[],double transactions,double support,TreeSet<String>itemset,HashMap<String,ArrayList<Integer>>find_item,int addindex)
		{
		String strarray []= new String[itemset.size()];	
		itemset.toArray(strarray);
		itemset.removeAll(itemset);
		items.clear();
		if(addindex==0)
		{
			String addelement = "";
			for(int i=0;i<strarray.length;i++)
			{
				for(int j=i+1;j<strarray.length;j++)
				{
					addelement = strarray[i]+","+strarray[j];
					itemset.add(addelement);
					items.put(addelement, 2);
				}
			}
		}
		else
		{			
			int ctr = 0;
			HashMap <Integer,String> strmap = new HashMap<Integer,String>();
			TreeSet <String> strset = new TreeSet<String>();
			String addelement = "";
			for(int i=0;i<strarray.length;i++)
			{
				addelement =strarray[i]+",";
				for(int j=i+1;j<strarray.length;j++)
				{
					addelement+=strarray[j];
					strmap.put(ctr, addelement);
					addelement=strarray[i]+",";
					ctr++;
				}				
			}
			for(int i=0;i<strmap.size();i++)
			{
				String line = strmap.get(i);
				String[] lineSplit = line.split(",");
				for(int j=0;j<lineSplit.length;j++)
				{
					strset.add(lineSplit[j]);
				}
				if(strset.size() == addindex+2)
				{
					String str_arr[] = new String[strset.size()];
					strset.toArray(str_arr);
					String extra="";
					for(int k=0;k<strset.size();k++)
					{
						extra+=str_arr[k]+",";
					}
					strset.removeAll(strset);
					extra = extra.substring(0, extra.length() - 1);
					itemset.add(extra);
					items.put(extra, 2);
				}
				else
					strset.removeAll(strset);
			}
		}
			if(items.size() == 0)
			return false;
			else
			return true;
		}
		//Function to calculate Support value of each Item in the specified data
		public static void calculatesupport(HashMap<String,Integer>items,String addarr[],double transactions,double support,TreeSet<String>itemset)
		{
			for(int i=0;i<addarr.length;i++)
			{
				if((items.get(addarr[i])/transactions)<support)
				{
					items.remove(addarr[i]);
					itemset.remove(addarr[i]);
				}
			}
		}
		//Function to scan the items in the given data
		public static void itemscan(HashMap<String,Integer>items,String addarr[],double transactions,double support,TreeSet<String>itemset,HashMap<String,ArrayList<Integer>>find_item)
		{
		String[] split_line = null;
		for(int i=0;i<addarr.length;i++)
		{
			split_line = addarr[i].split(",");
			ObjList obj[] = new ObjList[split_line.length];
			for(int j=0;j<split_line.length;j++)
			{
				obj[j] = new ObjList();
				obj[j].setListVariable(find_item.get(split_line[j]));
			}
			for(int k=1;k<obj.length;k++)
			{
				obj[0].objlist.retainAll(obj[k].objlist);
			}
			items.put(addarr[i], obj[0].objlist.size());
		 }
		}
		//Function to extract input from the file
		public static void Inputfilecheck(double supp_val, double con_val)
		{
		double transactions = 0;
		String read_line;
		HashMap <String,Integer> items = new HashMap<String,Integer>();
		TreeSet<String> itemset = new TreeSet<String>();
		HashMap <String,ArrayList<Integer>> find_item = new HashMap<String,ArrayList<Integer>>();
		TreeSet <String> associations = new TreeSet<String>();
		HashMap <String,Integer> frequentitem = new HashMap<String,Integer>();
		int ctr = 1;
		BufferedReader bf = null;
		try
		{
			bf = new BufferedReader(new FileReader("D:\\grocerystore.txt"));
			try
			{
				while((read_line = bf.readLine())!=null)
				{	
					String[] split = read_line.split(",");
					for(int i=0;i<split.length;i++)
					{
						if(items.containsKey(split[i]))
						{
							items.put(split[i], items.get(split[i])+1);
							ArrayList <Integer> arraylist = new ArrayList<Integer>();
							arraylist = find_item.get(split[i]);
							arraylist.add(ctr);
							find_item.put(split[i], arraylist);
						}
						else
						{
							items.put(split[i],1);
							ArrayList <Integer> arraylist = new ArrayList<Integer>();
							arraylist.add(ctr);
							find_item.put(split[i],arraylist);
							itemset.add(split[i]);
						}
					}
					ctr++;
					transactions++;
				 }
				String itemset_arr [] = new String[itemset.size()];
				itemset.toArray(itemset_arr);
				calculatesupport(items,itemset_arr,transactions,supp_val,itemset);
				frequentitem.putAll(items);
				boolean return_value = true;
				int addindex = 0;
				while(return_value == true)
				{
					return_value = additems(items,itemset_arr,transactions,supp_val,itemset,find_item,addindex);
					String addarr[] = new String[itemset.size()];
					itemset.toArray(addarr);
					itemscan(items,addarr,transactions,supp_val,itemset,find_item);
					calculatesupport(items,addarr,transactions,supp_val,itemset);
					frequentitem.putAll(items);
					addindex++;
				}
				System.out.println();
				System.out.println("Frequent Items:");
				System.out.println();
				for (Entry<String, Integer> entry : frequentitem.entrySet()) 
				{
					itemset.add(entry.getKey());
					double val = 0;
					val = (double)(entry.getValue())/transactions;
					System.out.println("ITEMSET: " + entry.getKey() + " FREQUENCY VALUE : " + entry.getValue()+ " SUPPORT VALUE : " + val);
				};		
				String add_arr[] = new String[itemset.size()];
				itemset.toArray(add_arr);
				String association_arr[] = new String[itemset.size()];
				itemset.toArray(association_arr);
				System.out.println();				
				HashMap<String,Integer> association_rules = new HashMap<String,Integer>();
				for(int i=0;i<itemset.size();i++)
				{
					String[] split_line = add_arr[i].split(",");
					if(split_line.length > 1)//Association rules cannot be created for individual item sets
					{
						for(int j=0;j<split_line.length;j++)
						{
							for(int k=j+1;k<split_line.length;k++)
							{
								association_rules.put(split_line[j]+","+split_line[k],2);
								association_rules.put(split_line[j], 2);
								association_rules.put(split_line[k], 2);				
							}
						}
						association_rules.remove(association_arr[i]);
						String string1 = association_arr[i];
						for (Entry<String, Integer> entry : association_rules.entrySet()) 
						{
							String string2 = entry.getKey();
							String string3 = entry.getKey()+",";
							String string4 = ","+entry.getKey();
							String string5 = ","+entry.getKey()+",";
							if(string1.toLowerCase().contains(string5.toLowerCase()))
							{
								string1 = string1.replace(string5, ",");
							}
							else if(string1.toLowerCase().contains(string3.toLowerCase()))
							{
								string1 = string1.replace(string3, "");
							}
							else if(string1.toLowerCase().contains(string4.toLowerCase()))
							{
								string1 = string1.replace(string4, "");
							}
							else if(string1.toLowerCase().contains(string2.toLowerCase()))
							{
								string1 = string1.replace(string2, "");
							}
							else
							{
								String[] comma_splitter = string2.split(",");
								for(int m=0;m<comma_splitter.length;m++)
								{
									if(string1.contains(","+comma_splitter[m]+","))
									{
										string1 = string1.replace(","+comma_splitter[m]+",", ",");
									}
									else if(string1.contains(comma_splitter[m]+","))
									{
										string1 = string1.replace(comma_splitter[m]+",", "");
									}
									else if(string1.contains(","+comma_splitter[m]))
									{
										string1 = string1.replace(","+comma_splitter[m], "");
									}
									else if(string1.contains(comma_splitter[m]))
									{
										string1 = string1.replace(comma_splitter[m], "");
									}
									else
									{
									    System.out.println("Kindly check the specified Input!  "+association_arr[i]);
									}
								}								
							}							
							if(string1.length()==0)
							{
								System.out.println("Empty String!");
							}
							double supportcount_item = frequentitem.get(association_arr[i]);
							double supportcount_set = frequentitem.get(entry.getKey());
							if((supportcount_item/supportcount_set)>=con_val)
							{
								associations.add(entry.getKey() + " ---> " + string1);
							}
							string1 = association_arr[i];
						};
					}
					association_rules.clear();					
				}
				System.out.println("Association Rules:");
				System.out.println();
				String allrules[] = new String[associations.size()];
				associations.toArray(allrules);
				for(int i=0;i<allrules.length;i++)
				{
					System.out.println(allrules[i]);
				}
			}
			catch (IOException e)
			{
				System.out.println("Error in Reading the File!");
				System.exit(0);
			}
		  }
			catch(FileNotFoundException fe)
			{
			System.out.println("File Name is not valid!");
			System.exit(0);
			}
		}
		//Main Function Declaration
		public static void main(String args[])
		{
		double supp_val = new Double(args[0]);
		double con_val = new Double(args[1]);
		System.out.println("Minimum Support = "+ supp_val +"%");
		System.out.println("Minimum Confidence = "+ con_val +"%");
		supp_val = supp_val/100;
		con_val = con_val/100;
		Inputfilecheck(supp_val,con_val);
		System.out.println();
		}
}
