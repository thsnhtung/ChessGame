package book;

import java.util.Scanner;

public class Novel extends Book
{
	private boolean condition;
	
	public Novel(final String bookId, final String bookName, final int numOfBooks,
			final double bookCost, final String publisher, final boolean condition)
	{
		super(bookId, bookName, numOfBooks, bookCost, publisher);
		this.condition = condition;
	}
	
	public void Nhap()
	{
		super.Nhap(); 
		Scanner sc = new Scanner(System.in) ;
        System.out.print("Nhap dieu kien:");
        this.condition =sc.nextBoolean();
        sc.close();
	}

	@Override
	public double calculateCost() 
	{
		if (this.condition)
			return this.numOfBooks * this.bookCost ; 
		return this.numOfBooks * this.bookCost * 0.2 ;
	}
}
