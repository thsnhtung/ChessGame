package GUI;
import javax.swing.*;

public class Table
{
	private final JFrame gameFrame; 
	
	public Table()
	{
		this.gameFrame = new JFrame("ChessBoard") ; 
		this.gameFrame.setVisible(true);
		this.gameFrame.setSize(800,800);
		this.gameFrame.setLocationRelativeTo(null);
	}
	
	
	public static void main(String[]args)
	{
		Table board = new Table() ; 
	}
}
