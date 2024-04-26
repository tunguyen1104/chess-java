package view;

public class ThreadDialog extends Thread{
	private DialogPromotion a;
	public ThreadDialog(DialogPromotion b)
	{
		this.a=b;
	}
	public void run()
	{
		try {
			sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.a.setVisible(true);
	}
}
