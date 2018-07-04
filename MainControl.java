public class MainControl {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		jwcWin jwcwin=new jwcWin();
		jsWin jswin=new jsWin();
		xsWin xswin=new xsWin();
		Drive drive=new Drive();
		RegisterWindow rwin=new RegisterWindow(jwcwin,jswin,xswin);
		jwcwin.setconnection(drive.getconnection());
		jswin.setconnection(drive.getconnection());
		xswin.setconnection(drive.getconnection());
		rwin.setconnection(drive.getconnection());
		
	}
}
