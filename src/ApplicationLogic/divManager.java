package ApplicationLogic;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 * @author Sebastian Römer
 *
 */
public class divManager {
	
	private static divManager instance = null;
	ArrayList<div> divList = new ArrayList<div>();
	
	private divManager()
	{}
	
	public void createEmptyLudoBoard(){
		for(int i = 0; i<52; i++){
			div j = new div();
			divList.add(i, j);
		}
		for(int i = 0; i<4;i++){
			div sr = new div("yellow");
			divList.add(i, sr);
		}
		for(int i = 0; i<4;i++){
			div sr = new div("blue");
			divList.add(i, sr);
		}
		for(int i = 0; i<4;i++){
			div sr = new div("green");
			divList.add(i, sr);
		}
		for(int i = 0; i<4;i++){
			div sr = new div("red");
			divList.add(i, sr);
		}
	
	for(int i = 0; i<5;i++){   //stairs for green player
		div sr = new div("green",1);
		divList.add(i, sr);
	}
	
	for(int i = 0; i<5;i++){
		div sr = new div("yellow",1);
		divList.add(i, sr);
	}
	for(int i = 0; i<5;i++){
		div sr = new div("blue",1);
		divList.add(i, sr);
	}
	for(int i = 0; i<5;i++){
		div sr = new div("red",1);
		divList.add(i, sr);
	}
		

	}
	
	public void createBoard(){
		ArrayList<Player> playerList = Manager.getInstance().getPlayers();
		
		Iterator<div> divIt = divManager.getInstance().divList.iterator();
		Iterator<Player> playerIt = playerList.iterator();
		
		System.out.println(divList);
		while(divIt.hasNext()){
			div actualDiv = divIt.next();
			actualDiv.setChip();
		}
		divIt = divManager.getInstance().divList.iterator();
		
		while(playerIt.hasNext())
		{
			Player p = playerIt.next();
			ArrayList<Chip> chips = p.getChips();
			Iterator<Chip> chipsIt = chips.iterator();
			
			while(chipsIt.hasNext())
			{
				Chip c = chipsIt.next();
				String color = Manager.stringFColor(c.getColor());
				
				if(c.getStatus()==0)
				{
					String freeHome = findFreeHome(c.getColor(), p.getChips().indexOf(c));
					divIt = divManager.getInstance().divList.iterator();
					
					while(divIt.hasNext())
					{
						div actualDiv = divIt.next();
						
						// Prüft ob Chip Position mit der aktuellen Div Position übereinstimmt				
						if(c.getPosition()==-1)
						{
							if(actualDiv.getDivId().equals(freeHome))
							{
								actualDiv.setChip(color, c);
								break;
							}
						}
					}
				}
				else if(c.getStatus()==1)
				{
					divIt = divManager.getInstance().divList.iterator();
					
					while(divIt.hasNext())
					{
						div actualDiv = divIt.next();
						
						// Prüft ob Chip Position mit der aktuellen Div Position übereinstimmt
						if(("f"+Integer.toString(c.getPosition())).equals(actualDiv.getDivId()))
						{
							actualDiv.setChip(color, c);
							break;
						}
					}	
				}
				else if(c.getStatus()==2)
				{
					divIt = divManager.getInstance().divList.iterator();
					
					while(divIt.hasNext())
					{
						div actualDiv = divIt.next();
						
						int position = 100 * c.getColor() + (c.getPosition()+1);
						
						// Prüft ob Chip Position mit der aktuellen Div Position übereinstimmt
						if(("f"+Integer.toString(position)).equals(actualDiv.getDivId()))
						{
							actualDiv.setChip(color, c);
							break;
						}
					}
				}
			}
		}
	}
	
	public void addChipToDiv(String divId, int chipId, String chipColor){
		for(div d: divManager.getInstance().divList){
			if(d.getDivId().equals(divId)){
				d.setChip(chipId, chipColor);
			}
		}
	}
	
/*	public void kickOrMergeChip(String divId, String chipColor){
		Iterator<div> divIt = divManager.getInstance().divList.iterator();
		
		while(divIt.hasNext()){
			div actualDiv = divIt.next();
			
			if(actualDiv.getDivId().equals(divId)){
				if(!(actualDiv.getChip().equals(""))){
					if(actualDiv.getChipColor().equals(chipColor)){
						actualDiv.setChip();
					}
					else {
						actualDiv.setChip();
						divManager.getInstance().addChipToDiv(divManager.getInstance().findFreeHome(chipColor), actualDiv.getChipId(), actualDiv.getChipColor());
					}
				}
			}
		}
	}*/
	
/*	public void moveChipToDiv(String divId, int chipId, String chipColor){ 
		Iterator<div> divIt = divManager.getInstance().divList.iterator();
		divManager.getInstance().kickOrMergeChip(divId, chipColor);
		
		while(divIt.hasNext()){
			div actualDiv = divIt.next();
			
			if(actualDiv.getChipId()==chipId){
				if(actualDiv.getChipColor().equals(chipColor)){
					actualDiv.setChip();
					divManager.getInstance().addChipToDiv(divId, chipId, chipColor);
				}
			}
		}
	}*/
	
	
	//ich wars -> ruben
	//PREVERSION
	//public String findFreeHome(int color){
	public String findFreeHome(int color, int iiPCA){//index in player chip array
		String chipColor = Integer.toString(color);
		Iterator<div> divIt = divManager.getInstance().divList.iterator();
		while (divIt.hasNext()){
			div actualDiv = divIt.next();
			if(actualDiv.getDivId().length()>4){
				if(actualDiv.getDivId().substring(1, 2).equals(chipColor)){
					//PREVERSION
					/*if(actualDiv.getChip().equals("")){
						return actualDiv.getDivId();						
					}*/
					if(actualDiv.getChip().equals("") && iiPCA==0){
						return actualDiv.getDivId();						
					}
					else{
						iiPCA--;
						continue;
					}
				}
			}
		}
		return null;
	}
	
	public static void main(String args[]){
		divManager.getInstance().createEmptyLudoBoard();
		divManager.getInstance().addChipToDiv("f1001", 1, "red");
		divManager.getInstance().addChipToDiv("f1002", 2, "red");
		divManager.getInstance().addChipToDiv("f1003", 3, "red");
		divManager.getInstance().addChipToDiv("f1004", 4, "red");
		divManager.getInstance().addChipToDiv("f2001", 1, "green");
		divManager.getInstance().addChipToDiv("f2002", 2, "green");
		divManager.getInstance().addChipToDiv("f2003", 3, "green");
		divManager.getInstance().addChipToDiv("f2004", 4, "green");
		divManager.getInstance().addChipToDiv("f3001", 1, "blue");
		divManager.getInstance().addChipToDiv("f3002", 2, "blue");
		divManager.getInstance().addChipToDiv("f3003", 3, "blue");
		divManager.getInstance().addChipToDiv("f3004", 4, "blue");
		divManager.getInstance().addChipToDiv("f4001", 1, "yellow");
		divManager.getInstance().addChipToDiv("f4002", 2, "yellow");
		divManager.getInstance().addChipToDiv("f4003", 3, "yellow");
		divManager.getInstance().addChipToDiv("f4004", 4, "yellow");
		
		for(div d: divManager.getInstance().divList){
			System.out.println(d.toString());
		}
	}
	
	public static divManager getInstance()
	{
		if (instance == null) 
		{
			instance = new divManager();
		}
		return instance;
	}
}
