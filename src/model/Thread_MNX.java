package model;

import view.DialogEndGame;
import view.GamePVC;
import view.ViewBoard;

public class Thread_MNX extends Thread {
	GamePVC s;
	String ts;
	boolean wait;
	public Thread_MNX(GamePVC j,boolean w)
	{
		this.s=j;
		this.wait=w;
	}
	public String getTs() {
		return ts;
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
		if(this.s.getCnt_move()>=3) 
		{
			this.s.getPanel().getMy_board().setDepth(4);
			this.s.getPanel().getMy_board().setRange_each_depth(60);
			if(this.s.getPanel().getMy_board().rating_material(true)<1750) 
			{
				this.s.getPanel().getMy_board().setDepth(5);
				this.s.getPanel().getMy_board().setRange_each_depth(120);
			}
		}
		this.ts=this.s.getPanel().getMy_board().minimax_alpha_beta(this.s.getPanel().getMy_board().getDepth(), -1000000, 1000000, false);
		this.s.getPanel().getStrSaveData().append(this.s.getPanel().convert(ts,false));
		if((char)this.s.getPanel().convert(ts,false).charAt(6)=='#')
		{
			for(int i=0;i<this.s.getPanel().getStrSaveData().length();i+=14)
			{
				if(i+14<=this.s.getPanel().getStrSaveData().length())
				{
					this.s.getPanel().getStrSaveData().insert(i+14, "\n");
					i++;
				}
			}
			String result=this.s.getPanel().getMy_board().convertDate()
					+ "\nPvC\n" +
	                        "3" +
	                        "\nCheckMate\n" +
	                        "Black win\n"+
					this.s.getPanel().getStrSaveData();
			JDBCConnection.insertHistory(result);
			this.s.TurnEndGameLog();
			ReadImage.sound.playMusic(1);
			System.out.println(result);
		}
		this.s.textArea.setText(this.s.textArea.getText()+ts+"\n");
		this.s.getPanel().make_move_animated(ts);
	}
}

