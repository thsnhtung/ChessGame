package com.chess.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.MoveTransition;


public class Table 
{
	private final JFrame gameFrame;
	private final BoardPanel boardPanel ; 	
	private final Color lightTileColor = Color.decode("#F8FACD");
    private final Color darkTileColor = Color.decode("#95111");
	private Board chessBoard;
	private static String defaultPieceImagePath = "C:\\Users\\Asus\\Desktop\\ChessGame\\art\\holywarriors\\";
	private Tile sourceTile;
	private Tile destinationTile; 
	private Piece humanMovedPiece;
	
	
	private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(600,600) ; 
	private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
	private final static Dimension TILE_PANEL_DIMENSION = new  Dimension(10, 10);
	
	public Table()
	{
		this.gameFrame = new JFrame("ChessGame");
		this.gameFrame.setLayout(new BorderLayout());
		this.chessBoard = Board.createStandardBoard();
		
		final JMenuBar tableMenuBar = createTableMenuBar();
		this.gameFrame.setJMenuBar(tableMenuBar);
		this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
		
		this.boardPanel = new BoardPanel();
		this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
		this.gameFrame.setVisible(true);
	}

	private JMenuBar createTableMenuBar() 
	{
		final JMenuBar tableMenuBar = new JMenuBar();
		tableMenuBar.add(createFileMenu());
		return tableMenuBar;
	}

	private JMenu createFileMenu() 
	{
		final JMenu fileMenu = new JMenu("File");
		
		final JMenuItem openPGN = new JMenuItem("Load PGN File");
		
		openPGN.addActionListener(new ActionListener()	
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				System.out.println("Open up that PGN file");
				
			}
		});
		
		fileMenu.add(openPGN);
		
		final JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e) 
					{
						System.exit(0);

					}
			
				});
		
		fileMenu.add(exitMenuItem);
		return fileMenu;
	}
	
	private class BoardPanel extends JPanel
	{
		final List<TilePanel> boardTiles;
		
		BoardPanel()
		{
			super(new GridLayout(8,8));
			this.boardTiles = new ArrayList<>();
			
			for (int i = 0 ; i < BoardUtils.NUM_TILES; i ++)
			{
				final TilePanel tilePanel  = new TilePanel(this, i) ;
				this.boardTiles.add(tilePanel);
				add(tilePanel);
			}
			
			setPreferredSize(BOARD_PANEL_DIMENSION);
			validate();
		}

		public void drawBoard(final Board board) 
		{
			removeAll();
			for (final TilePanel tilePanel : boardTiles)
			{
				tilePanel.drawTile(board);
				add(tilePanel);
			}
			
			validate();
			repaint();
		}
	}
	
	private class TilePanel extends JPanel
	{
		private final int tileId;
		
		TilePanel(final BoardPanel boardPanel,
				  final int tileId)
		{
			super(new GridBagLayout());
			this.tileId = tileId;
			setPreferredSize(TILE_PANEL_DIMENSION);
			assignTileColor();
			assignTilePieceIcon(chessBoard);
			
			
			addMouseListener(new MouseListener()
					{
		
						@Override
						public void mouseClicked(MouseEvent e) 
						{
							if (javax.swing.SwingUtilities.isRightMouseButton(e))
							{
								System.out.print("Right \t");
								System.out.println(tileId);
								sourceTile = null ;
								destinationTile = null ;
								humanMovedPiece = null;
								
							}
							else if (javax.swing.SwingUtilities.isLeftMouseButton(e))
							{
								System.out.print("Left \t");
								System.out.println(tileId);
								if (sourceTile == null)
								{
									sourceTile = chessBoard.getTile(tileId);
									humanMovedPiece = sourceTile.getPiece();
									System.out.println(humanMovedPiece.toString());
									if (humanMovedPiece == null)
									{
										sourceTile = null ;
									}
								}	
								else
								{
									destinationTile = chessBoard.getTile(tileId);
									final Move move = Move.MoveFactory.createMove(chessBoard, sourceTile.getTileCoordinate(), destinationTile.getTileCoordinate()) ;
									final MoveTransition transition = chessBoard.currentPlayer().makeMove(move) ; 
									System.out.print("Status: ");
									System.out.println(transition.getMoveStatus().isDone());
									if (transition.getMoveStatus().isDone())
									{
										chessBoard = transition.getTransitionBoard();
										// add the move that made the move log
									}
										
									sourceTile = null;
									destinationTile = null;
									humanMovedPiece = null ;
								}
									
								SwingUtilities.invokeLater(new Runnable() 
								{

									@Override
									public void run() 
									{
										System.out.println(chessBoard);
										boardPanel.drawBoard(chessBoard);
									}
										
								});
							}
							
						}

						@Override
						public void mousePressed(MouseEvent e) 
						{
							// TODO Auto-generated method stub
							
						}

						@Override
						public void mouseReleased(MouseEvent e) 
						{
							// TODO Auto-generated method stub
							
						}

						@Override
						public void mouseEntered(MouseEvent e) 
						{
			
							
						}

						@Override
						public void mouseExited(MouseEvent e) 
						{
	
							
						}
					});
			
			validate();
		}
		public void drawTile(final Board board) 
		{
			assignTileColor();
			assignTilePieceIcon(board);
			validate();
			repaint();
			
		}
		private void assignTileColor() 
		{
			if (BoardUtils.EIGHTH_RANK[this.tileId]
			 || BoardUtils.SIXTH_RANK[this.tileId]
			 || BoardUtils.FOURTH_RANK[this.tileId]
			 || BoardUtils.SECOND_RANK[this.tileId])
			{
				setBackground(this.tileId % 2 == 0 ? lightTileColor : darkTileColor);
			}
			else if (BoardUtils.SEVENTH_RANK[this.tileId] || 
					 BoardUtils.FIFTH_RANK[this.tileId] ||
					 BoardUtils.THIRD_RANK[this.tileId] ||
					 BoardUtils.FIRST_RANK[this.tileId])
			{
				setBackground(this.tileId % 2 != 0 ? lightTileColor : darkTileColor);
			}
			
		}
		
		private void assignTilePieceIcon(final Board board) 
		{
			this.removeAll();
			
			if (board.getTile(this.tileId).isTileOccupied())
			{
				try 
				{
					final BufferedImage image = ImageIO.read(new File(defaultPieceImagePath + board.getTile(this.tileId).getPiece().getPieceAlliance().toString().substring(0, 1)
							+ board.getTile(this.tileId).getPiece().toString() + ".gif"));
					add(new JLabel(new ImageIcon(image)));
				}catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
