package model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import view.ViewBoard;

public class Openings {
	private Boolean on_openings;
	private LinkedList<String> moved_list;
	private String moved="";
	public Openings()
	{
		this.moved="";
		this.moved_list=new LinkedList();
		on_openings=true;
	}
	public void setOn_openings(Boolean on_openings) {
		this.on_openings = on_openings;
	}
	public Boolean getOn_openings() {
		return on_openings;
	}
	public String getMoved() {
		return moved;
	}
	public void setMoved(String moved) {
		this.moved += moved;
	}
	public LinkedList<String> getMoved_list() {
		return moved_list;
	}
	public void read_file()
	{
		String url=System.getProperty("user.dir") + "/resources/file_src/openings_code.txt";
		FileInputStream a=null;
		BufferedReader b=null;
		try {
		    a = new FileInputStream(url);
		    b = new BufferedReader(new InputStreamReader(a));
		    String line = b.readLine();
		    while (line != null) {
		    	this.moved_list.add(line);
		        line = b.readLine();
		    }
		} catch (FileNotFoundException ex) 
		{
		    Logger.getLogger(ViewBoard.class.getName())
		    		.log(Level.SEVERE, null, ex);
		} 
		catch (IOException ex) {
		    Logger.getLogger(ViewBoard.class.getName())
		                    .log(Level.SEVERE, null, ex);
		} finally {
		    try {
		        b.close();
		        a.close();
		    } catch (IOException ex) {
		        Logger.getLogger(ViewBoard.class.getName())
		                        .log(Level.SEVERE, null, ex);
		    }
		}
	}
	public void posible_openings()
	{
		LinkedList<Integer> index_remove=new LinkedList();
		for(String i:this.moved_list)
		{
			if(i.length()>=this.moved.length()&&i.substring(this.moved.length()-5, this.moved.length()).equals("-----")&&i.contains(this.moved.substring(0, this.moved.length()-5)))
			{
				this.moved_list.set(this.moved_list.indexOf(i)+1,i.replaceFirst("-----", this.moved.substring(this.moved.length()-5)));
			}
			if(!i.contains(this.moved))
			{
				index_remove.addLast(this.moved_list.indexOf(i));
			}
		}
		int temp=0;
		for(int i:index_remove)
		{
			this.moved_list.remove(i-temp);
			temp++;
		}
		if(this.moved_list.size()==0) this.on_openings=false;
	}
	public String Suggest_move()
	{
		Random rd=new Random();
		int random_move =rd.nextInt(this.moved_list.size());
		int begin=this.moved_list.get(random_move).lastIndexOf(moved)+moved.length();
		return this.moved_list.get(random_move).substring(begin, begin+5);
	}
}
