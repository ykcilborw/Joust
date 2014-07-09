package com.wroblicky.andrew.joust.core.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import com.wroblicky.andrew.joust.core.board.ChessBoard;
import com.wroblicky.andrew.joust.core.board.ChessBoardIterator;
import com.wroblicky.andrew.joust.core.board.Location;
import com.wroblicky.andrew.joust.core.chesspiece.ChessPiece;
import com.wroblicky.andrew.joust.core.game.GameSetup;
import com.wroblicky.andrew.joust.core.general.Util;
import com.wroblicky.andrew.joust.core.move.Turn;

@SuppressWarnings("serial")
public class ChessDisplay extends JFrame implements ActionListener {
	
	private JLayeredPane layeredPane;
	private JPanel chessBoardPanel;

	public ChessDisplay(ChessBoard chessBoard, List<Turn> turns) {
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
		
		ChessBoardIterator chessBoardIterator = chessBoard.iterator();
		while (chessBoardIterator.hasNext()) {
			Location current = chessBoardIterator.next();
			ChessPiece chessPiece = current.getChessPiece();
			if (chessPiece != null) {
				
				Util.print("" + chessPiece.getMySymbol());
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
	
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		JFrame frame = new ChessDisplay(GameSetup.setupDefaultGame().getBoard(), null);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE );
		frame.pack();
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}