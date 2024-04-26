package model;

import view.ViewBoard;

public class Thread_MNX extends Thread {
	ViewBoard s;
	String ts;
	public Thread_MNX(ViewBoard j)
	{
		this.s=j;
	}
	public void run()
	{
		try {
			Thread.currentThread().sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.ts=this.s.getMy_board().minimax_alpha_beta(4, -1000000, 1000000, false);
		this.s.make_move_animated(ts);
	}
}
