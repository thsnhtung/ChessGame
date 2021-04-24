package book;

import java.util.Scanner;

public abstract class Book 
{
	protected String bookId; 
	protected String bookName;
	protected int numOfBooks; 
	protected double bookCost;
	protected String publisher;
	
	protected Book(final String bookId, final String bookName, final int numOfBooks,
					final double bookCost, final String publisher)
	{
		this.bookId = bookId;
		this.bookName = bookName;
		this.numOfBooks = numOfBooks;
		this.bookCost = bookCost ;
		this.publisher = publisher;
	}
	
	public void Nhap()
	{
		Scanner sc = new Scanner(System.in) ;
        System.out.print("Ma sách");
        this.bookId = sc.nextLine();
        System.out.print("Tên sách");
        this.bookName = sc.nextLine();
        System.out.print("đơn giá:");
        this.bookCost = sc.nextDouble();
        System.out.print("Số lượng:");
        this.numOfBooks = sc.nextInt();
        sc.close();
	}	
	public abstract double calculateCost();
}
