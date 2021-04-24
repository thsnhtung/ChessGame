package Xe;


import java.util.ArrayList;

import Xe.Xe.XeMay;
import Xe.Xe.XeTai;

public class RunC 
{
	public static void main (String []args)
	{
		XeMay x1 = new XeMay(100,100) ;
		System.out.println(x1.soLuongHang);
		x1.Chay(100);
		System.out.println(x1.soLitXang);
		ArrayList<Xe> arrXe = new ArrayList<>() ;
		XeTai x2 = new XeTai(200, 200) ;
		arrXe.add(x1) ; 
		arrXe.add(x2); 

		for (final Xe xe: arrXe)
		{
			System.out.println(xe.LaySoLuongXang());
		}
		
	}
}
