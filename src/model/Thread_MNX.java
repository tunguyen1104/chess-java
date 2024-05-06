package model;

import view.ViewBoard;

public class Thread_MNX extends Thread {
	ViewBoard s;
	String ts;
	boolean wait;
	public Thread_MNX(ViewBoard j,boolean w)
	{
		this.s=j;
		this.wait=w;
	}
	public void run()
	{
		if(!this.wait)
		{
			try {
				Thread.currentThread().sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else 
		{
			try {
				Thread.currentThread().sleep(1400);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}			
		this.ts=this.s.getMy_board().minimax_alpha_beta(4, -1000000, 1000000, false);
		this.s.getStrSaveData().append(this.s.convert(ts,false));
		if((char)this.s.convert(ts,false).charAt(6)=='#')
		{
			for(int i=0;i<this.s.getStrSaveData().length();i+=14)
			{
				if(i+14<=this.s.getStrSaveData().length())
				{
					this.s.getStrSaveData().insert(i+14, "\n");
					i++;
				}
			}
			String result=this.s.getMy_board().convertDate()
					+ "\nPvC\n" +
	                        "3" +
	                        "\nCheckMate\n" +
	                        "White win\n"+
					this.s.getStrSaveData();
			JDBCConnection.insertHistory(result);
			System.out.println(result);
		}
		this.s.make_move_animated(ts);
	}
}

