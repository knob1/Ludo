package ApplicationLogic;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 * @author Sebastian Römer
 *
 */
public class div {
	
	private static int 	g_id=0;
	private String 		divId;
	private String 		closeTag = "</div>";
	
	private static int 	g_startRed_id=4001;
	private static int 	g_startGreen_id=1001;
	private static int 	g_startBlue_id=3001;
	private static int 	g_startYellow_id=2001;
	
	private static int 	g_stairsRed_id=401;
	private static int 	g_stairsGreen_id=101;
	private static int 	g_stairsBlue_id=301;
	private static int 	g_stairsYellow_id=201;
	
	private String 		chip ="";
	private String 		chipColor = "";
	private int 		chipId;
	
	public div(){
		this.divId = "f"+Integer.toString(g_id);
		g_id++;
	}
	
	public div(String color){
		if(color.equals("red")){
			this.divId = "f"+Integer.toString(g_startRed_id);
			g_startRed_id++;
		}
		if(color.equals("green")){
			this.divId = "f"+Integer.toString(g_startGreen_id);
			g_startGreen_id++;
		}
		if(color.equals("blue")){
			this.divId = "f"+Integer.toString(g_startBlue_id);
			g_startBlue_id++;
		}
		if(color.equals("yellow")){
			this.divId = "f"+Integer.toString(g_startYellow_id);
			g_startYellow_id++;
		}
	}
	
	public div(String color,int z){
		if(color.equals("red")){
			this.divId = "f"+Integer.toString(g_stairsRed_id);
			g_stairsRed_id++;
		}
		if(color.equals("green")){
			this.divId = "f"+Integer.toString(g_stairsGreen_id);
			g_stairsGreen_id++;
		}
		if(color.equals("blue")){
			this.divId = "f"+Integer.toString(g_stairsBlue_id);
			g_stairsBlue_id++;
		}
		if(color.equals("yellow")){
			this.divId = "f"+Integer.toString(g_stairsYellow_id);
			g_stairsYellow_id++;
		}
	}
	

	public String getDivId(){
		return divId;
	}
	
	public String getChip(){
		return chip;
	}
	
	public void setChip(){
		this.chip = "";
	}
	
	public void setChip(String chipColor, Chip c){
		
		int mergeCount = c.getMergeCount();
		String count = "0";
		if(mergeCount == 0){
			count = "0";
		}
		else if (mergeCount == 1){
			count = "2";
		}
		else if (mergeCount == 2){
			count = "3";
		}
		else if (mergeCount == 3){
			count = "4";
		}
		
		if(chipColor.equals("red")){
			this.chip = "<form action=\"moveChip\"><input type=\"hidden\" value=\""+chipColor+"\" name=\"Color\"><input type=\"hidden\"><input type=\"hidden\" value=\""+divId+"\" name=\"divId\"><input type=\"image\" src=\"rchip"+count+".png\" alt=\"Absenden\" border=\"0\"width=\"35\" height=\"35\"></form>";
			this.chipColor = "red";
		}
		if(chipColor.equals("green")){
			this.chip = "<form action=\"moveChip\"><input type=\"hidden\" value=\""+chipColor+"\" name=\"Color\"><input type=\"hidden\"><input type=\"hidden\" value=\""+divId+"\" name=\"divId\"><input type=\"image\" src=\"gchip"+count+".png\" alt=\"Absenden\" border=\"0\"width=\"35\" height=\"35\"></form>";
			this.chipColor = "green";
		}
		if(chipColor.equals("blue")){
			this.chip = "<form action=\"moveChip\"><input type=\"hidden\" value=\""+chipColor+"\" name=\"Color\"><input type=\"hidden\"><input type=\"hidden\" value=\""+divId+"\" name=\"divId\"><input type=\"image\" src=\"bchip"+count+".png\" alt=\"Absenden\" border=\"0\"width=\"35\" height=\"35\"></form>";
			this.chipColor = "blue";
		}
		if(chipColor.equals("yellow")){
			this.chip = "<form action=\"moveChip\"><input type=\"hidden\" value=\""+chipColor+"\" name=\"Color\"><input type=\"hidden\"><input type=\"hidden\" value=\""+divId+"\" name=\"divId\"><input type=\"image\" src=\"ychip"+count+".png\" alt=\"Absenden\" border=\"0\"width=\"35\" height=\"35\"></form>";
			this.chipColor = "yellow";
		}
		
	}
	
	public void setChip(int chipId,String chipColor){
		if(chipColor.equals("red")){
			this.chip = "<form action=\"moveChip\"><input type=\"hidden\" value=\""+chipColor+"\" name=\"Color\"><input type=\"hidden\" value=\""+chipId+"\" name=\"ChipID\"><input type=\"hidden\" value=\""+divId+"\" name=\"divId\"><input type=\"image\" src=\"rchip.png\" alt=\"Absenden\" border=\"0\"width=\"35\" height=\"35\"></form>";
			this.chipColor = "red";
			this.chipId = chipId;
		}
		if(chipColor.equals("green")){
			this.chip = "<form action=\"moveChip\"><input type=\"hidden\" value=\""+chipColor+"\" name=\"Color\"><input type=\"hidden\" value=\""+chipId+"\" name=\"ChipID\"><input type=\"hidden\" value=\""+divId+"\" name=\"divId\"><input type=\"image\" src=\"gchip.png\" alt=\"Absenden\" border=\"0\"width=\"35\" height=\"35\"></form>";
			this.chipColor = "green";
			this.chipId = chipId;
		}
		if(chipColor.equals("blue")){
			this.chip = "<form action=\"moveChip\"><input type=\"hidden\" value=\""+chipColor+"\" name=\"Color\"><input type=\"hidden\" value=\""+chipId+"\" name=\"ChipID\"><input type=\"hidden\" value=\""+divId+"\" name=\"divId\"><input type=\"image\" src=\"bchip.png\" alt=\"Absenden\" border=\"0\"width=\"35\" height=\"35\"></form>";
			this.chipColor = "blue";
			this.chipId = chipId;
		}
		if(chipColor.equals("yellow")){
			this.chip = "<form action=\"moveChip\"><input type=\"hidden\" value=\""+chipColor+"\" name=\"Color\"><input type=\"hidden\" value=\""+chipId+"\" name=\"ChipID\"><input type=\"hidden\" value=\""+divId+"\" name=\"divId\"><input type=\"image\" src=\"ychip.png\" alt=\"Absenden\" border=\"0\"width=\"35\" height=\"35\"></form>";
			this.chipColor = "yellow";
			this.chipId = chipId;
		}
		
	}
	
	public void addChipLink(String chipColor){
		
	}
	
	public void removeChipLink(){

	}
	
	public void clear(){
		chip ="";
		chipColor = "";
		chipId= 0;
	}
	
	public int getChipId(){
		return chipId;
	}
	
	public String getChipColor(){
		return chipColor;
	}
	
	public String getCloseTag() {
		return closeTag;
	}
	
	public String toString(){
		if (chip.equals("")){
			return "<div id=\""+divId+"\">"+getCloseTag()+"";
		}
		else{
			return "<div id=\""+divId+"\">"+chip+""+getCloseTag()+"";
		}
	}
}
