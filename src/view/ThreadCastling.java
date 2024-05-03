package view;

public class ThreadCastling extends Thread{
	private ViewBoard a;
	private String code_move;
	public ThreadCastling(ViewBoard b, String code)
	{
		this.a=b;
		code_move=code;
		
	}
	public void run()
	{
		if(code_move.equals("*7_ks"))
		{
			this.a.make_move_animated((char)code_move.charAt(1)+"4"+(char)code_move.charAt(1)+"6 ");
			try {
				sleep(800);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.a.make_move_animated((char)code_move.charAt(1)+"7"+(char)code_move.charAt(1)+"5 ");
			try {
				sleep(1200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			if(code_move.substring(3).equals("qs"))
			{
				this.a.make_move_animated((char)code_move.charAt(1)+"4"+(char)code_move.charAt(1)+"2 ");
				try {
					sleep(800);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.a.make_move_animated((char)code_move.charAt(1)+"0"+(char)code_move.charAt(1)+"3 ");
			}
			if(code_move.substring(3).equals("ks"))
			{
				this.a.make_move_animated((char)code_move.charAt(1)+"4"+(char)code_move.charAt(1)+"6 ");
				try {
					sleep(800);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.a.make_move_animated((char)code_move.charAt(1)+"7"+(char)code_move.charAt(1)+"5 ");
			}
		}
	}

}
