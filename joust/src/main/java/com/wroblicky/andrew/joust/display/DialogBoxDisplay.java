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
			if (chessPiece != null) {
				switch (chessPiece.getMySymbol()) {
					case BLACK_PAWN:	addChessPiece(current, "SnippingPawn.JPG");
										break;
					case WHITE_PAWN:	addChessPiece(current, "SnippingWhitePawn.JPG");
										break;
					case BLACK_ROOK:	addChessPiece(current, "SnippingRook.JPG");
										break;
					case WHITE_ROOK:	addChessPiece(current, "SnippingWhiteRook.JPG");
										break;
					case BLACK_KNIGHT:	addChessPiece(current, "SnippingHorse.JPG");
										break;
					case WHITE_KNIGHT:	addChessPiece(current, "SnippingWhiteHorse.JPG");
										break;
					case BLACK_BISHOP:	addChessPiece(current, "SnippingBishop.JPG");
										break;
					case WHITE_BISHOP:	addChessPiece(current, "SnippingWhiteBishop.JPG");
										break;
					case BLACK_QUEEN:	addChessPiece(current, "SnippingQueen.JPG");
										break;
					case WHITE_QUEEN:	addChessPiece(current, "SnippingWhiteQueen.JPG");
										break;
					case BLACK_KING:	addChessPiece(current, "SnippingKing.JPG");
										break;
					case WHITE_KING:	addChessPiece(current, "SnippingWhiteKing.JPG");
										break;			
				}
			}
		}
		
		// set up next button
		JButton nextButton = new JButton("Next");
	    nextButton.setVerticalTextPosition(AbstractButton.CENTER);
	    nextButton.setHorizontalTextPosition(AbstractButton.CENTER);
	    nextButton.setMnemonic(KeyEvent.VK_D);
	    nextButton.setActionCommand("next");
	    nextButton.addActionListener(this);
	    
	    // set up back button
	    JButton backButton = new JButton("Back");
	    backButton.setVerticalTextPosition(AbstractButton.CENTER);
	    backButton.setHorizontalTextPosition(AbstractButton.CENTER);
	    backButton.setMnemonic(KeyEvent.VK_D);
	    backButton.setActionCommand("back");
	    backButton.addActionListener(this);
	    
	    chessBoardPanel.add(backButton);
		chessBoardPanel.add(nextButton);
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
			Game game = pgnViewer.playNextTurn();
			Turn turn = game.getCurrentTurn();
			for (GameStateChange gameStateChange : turn.getGameStateChanges()) {
				if (gameStateChange instanceof Move) {
					Move move = (Move) gameStateChange;
					Component component = chessBoardPanel.getComponent(
							move.getStart().getComponentNumber());
					JLabel chessPiece = (JLabel) component.getComponentAt(1, 0);
					chessPiece.setVisible(false);
					Location destination = move.getDestination();
					if (destination != null) {
						JPanel jPanel =  (JPanel) chessBoardPanel.getComponent(
								destination.getComponentNumber());
						jPanel.add(chessPiece);
						chessPiece.setVisible(true);
					}
				}
			}
		}
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