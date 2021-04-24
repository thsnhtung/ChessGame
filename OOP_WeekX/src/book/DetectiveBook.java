package book;

import java.util.Scanner;

public class DetectiveBook extends Book
{
	private double taxFee ;
	
	protected DetectiveBook(String bookId, String bookName, int numOfBooks, double bookCost, String publisher, double taxFee) 
	{
		super(bookId, bookName, numOfBooks, bookCost, publisher);
		this.taxFee = taxFee;
	}

	@Override
	public double calculateCost() 
	{
		
		return 0;
	}
	
	public void Nhap()
	{
		super.Nhap(); 
		Scanner sc = new Scanner(System.in) ;
        System.out.print("Nhap thuế:");
        this.taxFee =sc.nextDouble();
        sc.close();
		
	}

}
