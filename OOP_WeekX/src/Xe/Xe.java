package Xe;

public abstract class Xe 
{
	protected int soLuongHang;
	protected int soLitXang;
	
	public Xe(int soLuongHang, int soLitXang)
	{
		this.soLitXang = soLitXang;
		this.soLuongHang = soLuongHang;
	}
	
	public void ThemHang(int soLuongThem)
	{
		this.soLuongHang += soLuongThem ; 
	}
	
	public void DoXang(int soLitXang)
	{
		this.soLitXang += soLitXang ; 
	}
	
	public void LayHang(int soLuongLay)
	{
		this.soLuongHang -= soLuongLay ; 
	}
	
	public boolean daHetXang()
	{
		return this.soLitXang == 0 ? true : false ; 
	}
	
	public int LaySoLuongXang()
	{
		return this.soLitXang ; 
	}
	
	public abstract void Chay(int soKm);
	
	protected abstract boolean isEnoughGas(int soKm);
	
	public static class XeMay extends Xe
	{

		public XeMay(int soLuongHang, int soLitXang) 
		{
			super(soLuongHang, soLitXang);
		}

		@Override
		public void Chay(int soKm) 
		{
			if (this.isEnoughGas(soKm))
				this.soLitXang -= (this.soLuongHang*0.01 + 2)/100 * soKm ;
			else
			{
				throw new RuntimeException("Khong du xang");
			}
		}

		@Override
		protected final boolean isEnoughGas(int soKm) 
		{
			return (this.soLuongHang*0.01 + 2)/100 * soKm <= this.soLitXang ? true : false ; 
		}
		
	}
	
	public static class XeTai extends Xe
	{

		public XeTai(int soLuongHang, int soLitXang)
		{
			super(soLuongHang, soLitXang);
		}

		@Override
		public void Chay(int soKm) 
		{
			if (this.isEnoughGas(soKm))
				this.soLitXang -= (this.soLuongHang*0.01 + 20)/100 * soKm ;
			else
			{
				throw new RuntimeException("Khong du xang");
			}
		}

		@Override
		protected final boolean isEnoughGas(int soKm) 
		{
			// TODO Auto-generated method stub
			return (this.soLuongHang*0.01 + 20)/100 * soKm <= this.soLitXang ? true : false ; 
		}
	}
}
