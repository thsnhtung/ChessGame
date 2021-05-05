package com.chess.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
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
	private final GameHistoryPanel gameHistoryPanel ;
	private final TakenPiecesPanel takenPiecesPanel;

	
	
	private final BoardPanel boardPanel ; 	
	private final MoveLog moveLog;
	
	private final Color lightTileColor = Color.decode("#F8FACD");
    private final Color darkTileColor = Color.decode("#95111");
	private Board chessBoard;
	private static String defaultPieceImagePath = "C:\\Users\\Asus\\Desktop\\ChessGame\\art\\holywarriors\\";
	private Tile sourceTile;
	private Tile destinationTile; 
	private Piece humanMovedPiece;
	private BoardDirection boardDirection;
	private boolean highLightLegalMoves;
	
	
	
	private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(600,600) ; 
	private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
	private final static Dimension TILE_PANEL_DIMENSION = new  Dimension(10, 10);
	
	private static final Table INSTANCE = new Table(false);
	
	public Table(boolean visible)
	{
		this.gameFrame = new JFrame("ChessGame");
		this.gameFrame.setLayout(new BorderLayout());
		final JMenuBar tableMenuBar = createTableMenuBar();
		this.gameFrame.setJMenuBar(tableMenuBar);
		this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
		this.chessBoard = Board.createStandardBoard();
		this.gameHistoryPanel = new GameHistoryPanel();
		this.takenPiecesPanel = new TakenPiecesPanel();		
		this.boardPanel = new BoardPanel();
		this.boardDirection = BoardDirection.NORMAL;
		this.moveLog = new MoveLog();
		
		this.highLightLegalMoves = true ; 

		this.gameFrame.add(this.takenPiecesPanel, BorderLayout.WEST);
		this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
		this.gameFrame.add(this.gameHistoryPanel, BorderLayout.EAST);
		center(this.gameFrame);
        this.gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.gameFrame.setVisible(visible);
	}
	
	
	public static Table get() 
	{
        return INSTANCE;
    }
	
//	private MoveLog getMoveLog() 
//	{
//        return this.moveLog;
//    }
	
	private static void center(final JFrame frame) 
	{
        final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        final int w = frame.getSize().width;
        final int h = frame.getSize().height;
        final int x = (dim.width - w) / 2;
        final int y = (dim.height - h) / 2;
        frame.setLocation(x, y);
    }
	
	private JMenu createPreferencesMenu()
	{
		final JMenu preferencesMenu = new JMenu("Preferences");
		final JMenuItem flipBoardMenuItem = new JMenuItem("Flip Board");
		flipBoardMenuItem.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				boardDirection = boardDirection.opposite();
				boardPanel.drawBoard(chessBoard);
				boardDirection = boardDirection.opposite();		
			}
			
		});
		
		preferencesMenu.add(flipBoardMenuItem);
		
		preferencesMenu.addSeparator();
		
		final JCheckBoxMenuItem legalMoveHighLighterCheckBox = new JCheckBoxMenuItem("Highlight Legal Moves", this.highLightLegalMoves);
		
		
		legalMoveHighLighterCheckBox.addActionListener(new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e) 
					{
						highLightLegalMoves= legalMoveHighLighterCheckBox.isSelected();
						
					}
			
				}
		);
			
		preferencesMenu.add(legalMoveHighLighterCheckBox);
		
		return preferencesMenu;
	}

	private JMenuBar createTableMenuBar() 
	{
		final JMenuBar tableMenuBar = new JMenuBar();
		tableMenuBar.add(createFileMenu());
		tableMenuBar.add(createPreferencesMenu());
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
	
	
//	private void undoAllMoves() 
//	{
////        for(int i = Table.get().getMoveLog().size() - 1; i >= 0; i--) {
////            final Move lastMove = Table.get().getMoveLog().removeMove(Table.get().getMoveLog().size() - 1);
////            this.chessBoard = this.chessBoard.currentPlayer().unMakeMove(lastMove).getToBoard();
////        }
////        this.computerMove = null;
////        Table.get().getMoveLog().clear();
////        Table.get().getGameHistoryPanel().redo(chessBoard, Table.get().getMoveLog());
////        Table.get().getTakenPiecesPanel().redo(Table.get().getMoveLog());
////        Table.get().getBoardPanel().drawBoard(chessBoard);
////        Table.get().getDebugPanel().redo();
//    }
	
	
	

	public static class MoveLog
	{
		private final List<Move> moves;
		
		MoveLog()
		{
			this.moves = new ArrayList<>() ; 
		}
		
		public List<Move> getMoves()
		{
			return this.moves ;
		}
		
		public void addMove(final Move move)
		{
			this.moves.add(move);
		}
		
		public int size()
		{
			return this.moves.size();
		}
		
		public void clear()
		{
			this.moves.clear();
		}
		
		public Move removeMove(int index)
		{
			return this.moves.remove(index);
		}
		
	}
	
	private class BoardPanel extends JPanel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
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
			setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			setBackground(Color.decode("#FB4726"));
			validate();
		}

		public void drawBoard(final Board board) 
		{
			removeAll();
			for (final TilePanel tilePanel : boardDirection.traverse(boardTiles))
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
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
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
								sourceTile = null ;
								destinationTile = null ;
								humanMovedPiece = null;
								
							}
							else if (javax.swing.SwingUtilities.isLeftMouseButton(e))
							{
								if (sourceTile == null)
								{
									sourceTile = chessBoard.getTile(tileId);
									humanMovedPiece = sourceTile.getPiece();
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
									System.out.println(transition.getMoveStatus().toString());
									if (transition.getMoveStatus().isDone())
									{
										chessBoard = transition.getTransitionBoard();
										moveLog.addMove(move);
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
										gameHistoryPanel.redo(chessBoard, moveLog);
										takenPiecesPanel.redo(moveLog);
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
		
		private Collection<Move> pieceLegalMoves(final Board board)
		{
			if (humanMovedPiece != null && humanMovedPiece.getPieceAlliance() == board.currentPlayer().getAlliance())
			{
				if (humanMovedPiece.getPieceType().isKing())
					return Collections.unmodifiableCollection(Stream.concat(humanMovedPiece.CalculateLegalMoves(board).stream(),
							board.currentPlayer().calculateKingCastles(board.currentPlayer().getOpponent().getLegalMoves()).stream()).collect(Collectors.toList()));
				return humanMovedPiece.CalculateLegalMoves(board);
			}
			return Collections.emptyList();
		}
		
		public void drawTile(final Board board) 
		{
			assignTileColor();
			assignTilePieceIcon(board);
			highlightTileBorder(board);
			highlightLegals(board);
			validate();
			repaint();
			
		}
		
		private void highlightTileBorder(final Board board) 
		{
            if(humanMovedPiece != null &&
               humanMovedPiece.getPieceAlliance() == board.currentPlayer().getAlliance() &&
               humanMovedPiece.getPiecePosition() == this.tileId) {
                setBorder(BorderFactory.createLineBorder(Color.cyan));
            } else {
                setBorder(BorderFactory.createLineBorder(Color.GRAY));
            }
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
		
		
		private void highlightLegals (final Board board)
		{
			if (highLightLegalMoves)
			{
				for (final Move move : pieceLegalMoves(board))
				{
					if (move.getDestinationCoordinate() == this.tileId)
					{
						try
						{
							add(new JLabel(new ImageIcon(ImageIO.read(new File ("C:\\Users\\Asus\\Desktop\\ChessGame\\art\\misc\\green_dot.png")))));
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				}
			}
		}
		
		
		
	}
	
	
	
	public enum BoardDirection
	{
		NORMAL
		{
			@Override
			List<TilePanel> traverse(List<TilePanel> boardTiles) 
			{
				return boardTiles;
			}

			@Override
			BoardDirection opposite() 
			{
				return FLIPPED;
			}
		},		
		FLIPPED
		{
			@Override
			List<TilePanel> traverse(List<TilePanel> boardTiles) 
			{
				Collections.reverse(boardTiles);
				return boardTiles;
			}

			@Override
			BoardDirection opposite() 
			{
				return NORMAL;
			}
		};		
		abstract List<TilePanel> traverse(final List<TilePanel> boardTiles);
		abstract BoardDirection opposite();
			
	}
}
