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
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import com.wroblicky.andrew.joust.Util;
import com.wroblicky.andrew.joust.game.Game;
import com.wroblicky.andrew.joust.game.PGNViewer;
import com.wroblicky.andrew.joust.game.board.ChessBoardIterator;
import com.wroblicky.andrew.joust.game.board.Location;
import com.wroblicky.andrew.joust.game.chesspiece.ChessPiece;
import com.wroblicky.andrew.joust.game.move.GameStateChange;
import com.wroblicky.andrew.joust.game.move.Move;
import com.wroblicky.andrew.joust.game.move.Turn;
import com.wroblicky.andrew.joust.pgn.PGNParser;

/**
 * Manages the ui for displaying a chess game as a dialog box
 *
 * @author Andrew Wroblicky
 *
 */
@SuppressWarnings("serial")
public class DialogBoxDisplay extends JFrame implements ActionListener {

	private final JPanel chessBoardPanel;
	private PGNViewer pgnViewer;

	private DialogBoxDisplay(Game game, PGNViewer pgnViewer) {
		this.pgnViewer = pgnViewer;
		Dimension boardSize = new Dimension(600, 600);

		addMenuBar();

		// setup chess board
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setPreferredSize(new Dimension(680, 680));

		chessBoardPanel = new JPanel();
		chessBoardPanel.setLayout(new GridLayout(8, 8));
		chessBoardPanel.setPreferredSize(boardSize);
		chessBoardPanel.setBounds(0, 0, boardSize.width, boardSize.height);
		chessBoardPanel.setAlignmentY(TOP_ALIGNMENT);
		for (int i = 0; i < 64; i++) {
			JPanel square = new JPanel(new BorderLayout());
			chessBoardPanel.add(square);
			int row = (i / 8) % 2;
			if (row == 0) {
				square.setBackground(i % 2 == 0 ? Color.black : Color.red);
			} else {
				square.setBackground(i % 2 == 0 ? Color.red : Color.black);
			}
		}
		mainPanel.add(chessBoardPanel);

		ChessBoardIterator chessBoardIterator = game.getBoard().iterator();
		while (chessBoardIterator.hasNext()) {
			Location current = chessBoardIterator.next();
			ChessPiece chessPiece = current.getChessPiece();
			addChessPiece(current, chessPiece);
		}

		// end button
		JButton lastButton = new JButton("Last");
		lastButton.setVerticalTextPosition(SwingConstants.CENTER);
		lastButton.setHorizontalTextPosition(SwingConstants.CENTER);
		lastButton.setMnemonic(KeyEvent.VK_L);
		lastButton.setActionCommand("last");
		lastButton.addActionListener(this);

		// next button
		JButton nextButton = new JButton("Next");
		nextButton.setVerticalTextPosition(SwingConstants.CENTER);
		nextButton.setHorizontalTextPosition(SwingConstants.CENTER);
		nextButton.setMnemonic(KeyEvent.VK_N);
		nextButton.setActionCommand("next");
		nextButton.addActionListener(this);

		// back button
		JButton backButton = new JButton("Back");
		backButton.setVerticalTextPosition(SwingConstants.CENTER);
		backButton.setHorizontalTextPosition(SwingConstants.CENTER);
		backButton.setMnemonic(KeyEvent.VK_B);
		backButton.setActionCommand("back");
		backButton.addActionListener(this);

		// start button
		JButton firstButton = new JButton("First");
		firstButton.setVerticalTextPosition(SwingConstants.CENTER);
		firstButton.setHorizontalTextPosition(SwingConstants.CENTER);
		firstButton.setMnemonic(KeyEvent.VK_I);
		firstButton.setActionCommand("first");
		firstButton.addActionListener(this);
		nextButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		firstButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		lastButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		// moveButtonsPanel
		JPanel moveButtonsPanel = new JPanel();
		moveButtonsPanel.setPreferredSize(new Dimension(30, 30));
		moveButtonsPanel.setBackground(Color.GRAY);
		GridLayout gridLayout = new GridLayout();
		gridLayout.setHgap(15);
		moveButtonsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,
				10));
		moveButtonsPanel.setLayout(gridLayout);
		moveButtonsPanel.setAlignmentY(BOTTOM_ALIGNMENT);
		moveButtonsPanel.setPreferredSize(new Dimension(25, 25));
		moveButtonsPanel.add(firstButton, BorderLayout.EAST);
		moveButtonsPanel.add(backButton, BorderLayout.CENTER);
		moveButtonsPanel.add(nextButton, BorderLayout.WEST);
		moveButtonsPanel.add(lastButton, BorderLayout.NORTH);

		// putting it all together
		mainPanel.add(moveButtonsPanel);
		getContentPane().add(mainPanel);
	}

	// http://zetcode.com/tutorials/javaswingtutorial/menusandtoolbars/
	private void addMenuBar() {
		final JMenuBar menubar = new JMenuBar();
		menubar.setBackground(Color.GRAY);

		JMenu file = new JMenu("File");
		file.setBackground(Color.GRAY);
		file.setMnemonic(KeyEvent.VK_F);

		JMenuItem openMenuItem = new JMenuItem("Open");
		openMenuItem.setMnemonic(KeyEvent.VK_O);
		openMenuItem.setToolTipText("Open file");
		openMenuItem.setActionCommand("open");
		openMenuItem.addActionListener(this);

		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.setMnemonic(KeyEvent.VK_E);
		exitMenuItem.setToolTipText("Exit application");
		exitMenuItem.setActionCommand("exit");
		exitMenuItem.addActionListener(this);

		file.add(openMenuItem);
		file.add(new JSeparator());
		file.add(exitMenuItem);
		menubar.add(file);
		setJMenuBar(menubar);
	}

	private void addChessPiece(Location location, ChessPiece chessPiece) {
		if (chessPiece != null) {
			switch (chessPiece.getChessPieceAllegianceType()) {
			case BLACK_PAWN:
				addChessPiece(location, "SnippingPawn.JPG");
				break;
			case WHITE_PAWN:
				addChessPiece(location, "SnippingWhitePawn.JPG");
				break;
			case BLACK_ROOK:
				addChessPiece(location, "SnippingRook.JPG");
				break;
			case WHITE_ROOK:
				addChessPiece(location, "SnippingWhiteRook.JPG");
				break;
			case BLACK_KNIGHT:
				addChessPiece(location, "SnippingHorse.JPG");
				break;
			case WHITE_KNIGHT:
				addChessPiece(location, "SnippingWhiteHorse.JPG");
				break;
			case BLACK_BISHOP:
				addChessPiece(location, "SnippingBishop.JPG");
				break;
			case WHITE_BISHOP:
				addChessPiece(location, "SnippingWhiteBishop.JPG");
				break;
			case BLACK_QUEEN:
				addChessPiece(location, "SnippingQueen.JPG");
				break;
			case WHITE_QUEEN:
				addChessPiece(location, "SnippingWhiteQueen.JPG");
				break;
			case BLACK_KING:
				addChessPiece(location, "SnippingKing.JPG");
				break;
			case WHITE_KING:
				addChessPiece(location, "SnippingWhiteKing.JPG");
				break;
			}
		}
	}

	private void addChessPiece(Location location, String imageName) {
		try {
			BufferedImage myPicture = ImageIO.read(new File(imageName));
			JLabel piece = new JLabel(new ImageIcon(myPicture));
			JPanel panel = (JPanel) chessBoardPanel.getComponent(location
					.getComponentNumber());
			panel.add(piece);
		} catch (IOException e) {
			Util.println("The function addChessPiece had an exception: " + e);
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String which = e.getActionCommand();
		if (which.equals("next")) {
			handleNext();
		} else if (which.equals("back")) {
			handleBack();
		} else if (which.equals("last")) {
			handleLast();
		} else if (which.equals("first")) {
			handleFirst();
		} else if (which.equals("open")) {
			handleOpen();
		} else if (which.equals("exit")) {
			handleExit();
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

	private void handleLast() {
		while (pgnViewer.getGame().isInProgress()) {
			handleNext();
		}
	}

	private void handleFirst() {
		while (pgnViewer.getGame().getRound() > 0) {
			handleBack();
		}
	}

	private void handleOpen() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System
				.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			PGNViewer newPgnViewer = new PGNViewer(
					PGNParser.getPGNGame(selectedFile.getAbsolutePath()));
			newPgnViewer.initializeGame();
			pgnViewer = newPgnViewer;
			this.setTitle(selectedFile.getName());
		}
	}

	private static void handleExit() {
		System.exit(0);
	}

	private void moveChessPiece(Location initial, Location destination) {
		// prepare sourcePanel
		JPanel sourcePanel = (JPanel) chessBoardPanel.getComponent(initial
				.getComponentNumber());
		JLabel chessPiece = (JLabel) sourcePanel.getComponentAt(1, 0);
		chessPiece.setVisible(false);

		// prepare destinationPanel
		JPanel destinationPanel = (JPanel) chessBoardPanel
				.getComponent(destination.getComponentNumber());
		destinationPanel.add(chessPiece);
		chessPiece.setVisible(true);
	}

	private void removeChessPiece(Location location) {
		JPanel sourcePanel = (JPanel) chessBoardPanel.getComponent(location
				.getComponentNumber());
		JLabel chessPiece = (JLabel) sourcePanel.getComponentAt(1, 0);
		chessPiece.setVisible(false);
		sourcePanel.remove(chessPiece);
		sourcePanel.revalidate();
		sourcePanel.repaint();
	}

	public static void start(Game game, PGNViewer pgnViewer) {
		JFrame frame = new DialogBoxDisplay(game, pgnViewer);
		frame.setTitle(pgnViewer.getFilename());
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}