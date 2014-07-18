package com.wroblicky.andrew.joust.display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.wroblicky.andrew.joust.Util;
import com.wroblicky.andrew.joust.game.Game;
import com.wroblicky.andrew.joust.game.PGNViewer;
import com.wroblicky.andrew.joust.game.board.ChessBoardIterator;
import com.wroblicky.andrew.joust.game.board.Location;
import com.wroblicky.andrew.joust.game.chesspiece.ChessPiece;
import com.wroblicky.andrew.joust.game.move.GameStateChange;
import com.wroblicky.andrew.joust.game.move.Move;
import com.wroblicky.andrew.joust.game.move.Turn;

/**
 * Manages the ui for displaying a chess game as a dialog box
 * 
 * @author Andrew Wroblicky
 *
 */
@SuppressWarnings("serial")
public class DialogBoxDisplay extends JFrame implements ActionListener {
	
	private JLayeredPane layeredPane;
	private JPanel chessBoardPanel;
	private PGNViewer pgnViewer;

	private DialogBoxDisplay(Game game, PGNViewer pgnViewer) {
		this.pgnViewer = pgnViewer;
		Dimension boardSize = new Dimension(600, 600);
		
		layeredPane = new JLayeredPane();
		getContentPane().add(layeredPane);
		layeredPane.setPreferredSize(boardSize);
		
		addMenuBar();
		
		// setup chess board
		chessBoardPanel = new JPanel();
		layeredPane.add(chessBoardPanel, JLayeredPane.DEFAULT_LAYER);
		chessBoardPanel.setLayout( new GridLayout(9, 8) );
		chessBoardPanel.setPreferredSize( boardSize );
		chessBoardPanel.setBounds(0, 0, boardSize.width, boardSize.height);
		for (int i = 0; i < 64; i++) {
			JPanel square = new JPanel( new BorderLayout() );
			chessBoardPanel.add( square );
			int row = (i / 8) % 2;
			if (row == 0)
				square.setBackground( i % 2 == 0 ? Color.black : Color.red );
			else
				square.setBackground( i % 2 == 0 ? Color.red : Color.black );
		}
		
		ChessBoardIterator chessBoardIterator = game.getBoard().iterator();
		while (chessBoardIterator.hasNext()) {
			Location current = chessBoardIterator.next();
			ChessPiece chessPiece = current.getChessPiece();
			addChessPiece(current, chessPiece);
		}
		
		// fast forward button
		JButton fastForwardButton = new JButton(">>");
		fastForwardButton.setVerticalTextPosition(AbstractButton.CENTER);
	    fastForwardButton.setHorizontalTextPosition(AbstractButton.CENTER);
	    fastForwardButton.setMnemonic(KeyEvent.VK_D);
	    fastForwardButton.setActionCommand("fastForwardButton");
	    fastForwardButton.addActionListener(this);

		// next button
		JButton nextButton = new JButton(">");
	    nextButton.setVerticalTextPosition(AbstractButton.CENTER);
	    nextButton.setHorizontalTextPosition(AbstractButton.CENTER);
	    nextButton.setMnemonic(KeyEvent.VK_D);
	    nextButton.setActionCommand("next");
	    nextButton.addActionListener(this);
	    
	    // back button
	    JButton backButton = new JButton("<");
	    backButton.setVerticalTextPosition(AbstractButton.CENTER);
	    backButton.setHorizontalTextPosition(AbstractButton.CENTER);
	    backButton.setMnemonic(KeyEvent.VK_D);
	    backButton.setActionCommand("back");
	    backButton.addActionListener(this);
	    
	    // rewind button
	    JButton rewindButton = new JButton("<<");
	    rewindButton.setVerticalTextPosition(AbstractButton.CENTER);
	    rewindButton.setHorizontalTextPosition(AbstractButton.CENTER);
	    rewindButton.setMnemonic(KeyEvent.VK_D);
	    rewindButton.setActionCommand("rewind");
	    rewindButton.addActionListener(this);
	    
	    chessBoardPanel.add(rewindButton);
	    chessBoardPanel.add(backButton);
		chessBoardPanel.add(nextButton);
		chessBoardPanel.add(fastForwardButton);
	}
	
	// http://zetcode.com/tutorials/javaswingtutorial/menusandtoolbars/
	private void addMenuBar() {
		JMenuBar menubar = new JMenuBar();
		JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
        
        JMenuItem eMenuItem = new JMenuItem("Exit");
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.setToolTipText("Exit application");
        
        file.add(eMenuItem);
        menubar.add(file);
        setJMenuBar(menubar);
	}
	
	private void addChessPiece(Location location, ChessPiece chessPiece) {
		if (chessPiece != null) {
			switch (chessPiece.getChessPieceAllegianceType()) {
				case BLACK_PAWN:	addChessPiece(location, "SnippingPawn.JPG");
									break;
				case WHITE_PAWN:	addChessPiece(location, "SnippingWhitePawn.JPG");
									break;
				case BLACK_ROOK:	addChessPiece(location, "SnippingRook.JPG");
									break;
				case WHITE_ROOK:	addChessPiece(location, "SnippingWhiteRook.JPG");
									break;
				case BLACK_KNIGHT:	addChessPiece(location, "SnippingHorse.JPG");
									break;
				case WHITE_KNIGHT:	addChessPiece(location, "SnippingWhiteHorse.JPG");
									break;
				case BLACK_BISHOP:	addChessPiece(location, "SnippingBishop.JPG");
									break;
				case WHITE_BISHOP:	addChessPiece(location, "SnippingWhiteBishop.JPG");
									break;
				case BLACK_QUEEN:	addChessPiece(location, "SnippingQueen.JPG");
									break;
				case WHITE_QUEEN:	addChessPiece(location, "SnippingWhiteQueen.JPG");
									break;
				case BLACK_KING:	addChessPiece(location, "SnippingKing.JPG");
									break;
				case WHITE_KING:	addChessPiece(location, "SnippingWhiteKing.JPG");
									break;
			}
		}
	}
	
	private void addChessPiece(Location location, String imageName) {
		try {
			BufferedImage myPicture = ImageIO.read(new File(imageName));
			JLabel piece = new JLabel( new ImageIcon(myPicture) );
			JPanel panel = (JPanel)chessBoardPanel.getComponent(location.getComponentNumber());
			panel.add(piece);
		} catch (IOException e) {
			Util.print("io: " + e);
			e.printStackTrace();
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		String which = e.getActionCommand();
		if(which.equals("next")) {
			handleNext();
		} else if(which.equals("back")) {
			handleBack();
		} else if (which.equals("fastforward")) {
			// TODO
		} else if (which.equals("rewind")) {
			// TODO
		}
	}
	
	private void handleNext() {
		Game game = pgnViewer.playNextTurn();
		Turn turn = game.getCurrentTurn();
		for (GameStateChange gameStateChange : turn.getGameStateChanges()) {
			if (gameStateChange instanceof Move) {
				Move move = (Move) gameStateChange;
				if (move.getDestination() == null) {
					removeChessPiece(move.getStart());
				} else {
					moveChessPiece(move.getStart(), move.getDestination());
				}
			}
		}
	}
	
	private void handleBack() {
		Game game = pgnViewer.getGame();
		Turn turn = game.getCurrentTurn();
		pgnViewer.undoCurrentTurn();
		for (GameStateChange gameStateChange : turn.getGameStateChanges()) {
			if (gameStateChange instanceof Move) {
				Move move = (Move) gameStateChange;
				if (move.getDestination() == null) {
					addChessPiece(move.getStart(), move.getChessPiece());
				} else {
					moveChessPiece(move.getDestination(), move.getStart());
				}
			}
		}
	}
	
	private void moveChessPiece(Location initial, Location destination) {
		// prepare sourcePanel
		JPanel sourcePanel = (JPanel) chessBoardPanel.getComponent(
				initial.getComponentNumber());
		JLabel chessPiece = (JLabel) sourcePanel.getComponentAt(1, 0);
		chessPiece.setVisible(false);
		
		// prepare destinationPanel
		JPanel destinationPanel =  (JPanel) chessBoardPanel.getComponent(
				destination.getComponentNumber());
		destinationPanel.add(chessPiece);
		chessPiece.setVisible(true);
	}
	
	private void removeChessPiece(Location location) {
		JPanel sourcePanel = (JPanel) chessBoardPanel.getComponent(
				location.getComponentNumber());
		JLabel chessPiece = (JLabel) sourcePanel.getComponentAt(1, 0);
		chessPiece.setVisible(false);
		sourcePanel.remove(chessPiece);
		sourcePanel.revalidate();
		sourcePanel.repaint();
	}
	
	public static void start(Game game, PGNViewer pgnViewer) {
		JFrame frame = new DialogBoxDisplay(game, pgnViewer);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}