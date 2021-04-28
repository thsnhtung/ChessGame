package com.chess.gui;

import java.awt.Dimension;

import javax.swing.JFrame;


public class Table 
{
	private final JFrame gameFrame;
	private static Dimension OUTER_FRAME_DIMESION = new Dimension(600,600) ; 
	
	public Table()
	{
		this.gameFrame = new JFrame("ChessGame");
		this.gameFrame.setSize(OUTER_FRAME_DIMESION);
		this.gameFrame.setVisible(true);
	}
}
