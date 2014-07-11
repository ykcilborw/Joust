package com.wroblicky.andrew.joust.core.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import com.wroblicky.andrew.joust.ChessBoardMatcher;
import com.wroblicky.andrew.joust.Util;

/* All code in this class taken from http://roseindia.net/java/example/java/swing/chess-application-swing.shtml
 * Deprecated and being transitioned to DialogBoxDisplay
 */
@SuppressWarnings("serial")
public class ChessGameDemo extends JFrame implements MouseListener, MouseMotionListener, ActionListener {
	JLayeredPane layeredPane;
	JPanel chessBoard;
	JLabel chessPiece;
	int xAdjustment;
	int yAdjustment;
	int index;
	List<JLabel> capturedPieces;
	List<String[][]> myBoards;
	ChessBoardMatcher myJouster;
	String myProgram;
	
	public ChessGameDemo(List<String[][]> boards, ChessBoardMatcher j, String joustProgram) {
		Dimension boardSize = new Dimension(600, 600);
		index = 0;
		myBoards = boards;
		myJouster = j;
		myProgram = joustProgram;
		capturedPieces = new ArrayList<JLabel>();
		JButton b1 = new JButton("Next");
	    b1.setVerticalTextPosition(AbstractButton.CENTER);
	    b1.setHorizontalTextPosition(AbstractButton.CENTER); //aka LEFT, for left-to-right locales
	    b1.setMnemonic(KeyEvent.VK_D);
	    b1.setActionCommand("next");
	    b1.addActionListener(this);
	    
	    JButton b2 = new JButton("Back");
	    b2.setVerticalTextPosition(AbstractButton.CENTER);
	    b2.setHorizontalTextPosition(AbstractButton.CENTER); //aka LEFT, for left-to-right locales
	    b2.setMnemonic(KeyEvent.VK_D);
	    b2.setActionCommand("back");
	    b2.addActionListener(this);
	    
	    JButton b3 = new JButton("Find");
	    b3.setVerticalTextPosition(AbstractButton.CENTER);
	    b3.setHorizontalTextPosition(AbstractButton.CENTER); //aka LEFT, for left-to-right locales
	    b3.setMnemonic(KeyEvent.VK_D);
	    b3.setActionCommand("find");
	    b3.addActionListener(this);
	    
	    /*
	    JTextField textField = new JTextField(80);
	    textField.setActionCommand("text");
	    textField.setText("Enter Query");
	    textField.addActionListener(this);
	    */
		
		//  Use a Layered Pane for this this application
		layeredPane = new JLayeredPane();
		getContentPane().add(layeredPane);
		layeredPane.setPreferredSize(boardSize);
		layeredPane.addMouseListener(this);
		layeredPane.addMouseMotionListener(this);
		
		
		
		//Add a chess board to the Layered Pane 
		chessBoard = new JPanel();
		layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
		chessBoard.setLayout( new GridLayout(9, 8) );
		chessBoard.setPreferredSize( boardSize );
		chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);
		for (int i = 0; i < 64; i++) {
			JPanel square = new JPanel( new BorderLayout() );
			chessBoard.add( square );
			
			
			
			int row = (i / 8) % 2;
			if (row == 0)
				square.setBackground( i % 2 == 0 ? Color.black : Color.red );
			else
				square.setBackground( i % 2 == 0 ? Color.red : Color.black );
		}
		
		String[][] initialBoard = boards.get(0);
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				int a = Util.reverseNum(y);
				//System.out.print("x: " + x);
				int componentNumber = a*8 + x;
				if (initialBoard[x][y].equals("K")) {
					JLabel piece = new JLabel( new ImageIcon("SnippingWhiteKing.JPG") );
					JPanel panel = (JPanel)chessBoard.getComponent(componentNumber);
					panel.add(piece);
				}
				if (initialBoard[x][y].equals("k")) {
					JLabel piece = new JLabel( new ImageIcon("SnippingKing.JPG") );
					JPanel panel = (JPanel)chessBoard.getComponent(componentNumber);
					panel.add(piece);
				}
				if (initialBoard[x][y].equals("Q")) {
					JLabel piece = new JLabel( new ImageIcon("SnippingWhiteQueen.JPG") );
					JPanel panel = (JPanel)chessBoard.getComponent(componentNumber);
					panel.add(piece);
				}
				if (initialBoard[x][y].equals("q")) {
					JLabel piece = new JLabel( new ImageIcon("SnippingQueen.JPG") );
					JPanel panel = (JPanel)chessBoard.getComponent(componentNumber);
					panel.add(piece);
				}
				if (initialBoard[x][y].equals("B")) {
					JLabel piece = new JLabel( new ImageIcon("SnippingWhiteBishop.JPG") );
					JPanel panel = (JPanel)chessBoard.getComponent(componentNumber);
					panel.add(piece);
				}
				if (initialBoard[x][y].equals("b")) {
					JLabel piece = new JLabel( new ImageIcon("SnippingBishop.JPG") );
					JPanel panel = (JPanel)chessBoard.getComponent(componentNumber);
					panel.add(piece);
				}
				if (initialBoard[x][y].equals("N")) {
					JLabel piece = new JLabel( new ImageIcon("SnippingWhiteHorse.JPG") );
					JPanel panel = (JPanel)chessBoard.getComponent(componentNumber);
					panel.add(piece);
				}
				if (initialBoard[x][y].equals("n")) {
					JLabel piece = new JLabel( new ImageIcon("SnippingHorse.JPG") );
					JPanel panel = (JPanel)chessBoard.getComponent(componentNumber);
					panel.add(piece);
				}
				if (initialBoard[x][y].equals("R")) {
					JLabel piece = new JLabel( new ImageIcon("SnippingWhiteRook.JPG") );
					JPanel panel = (JPanel)chessBoard.getComponent(componentNumber);
					panel.add(piece);
				}
				if (initialBoard[x][y].equals("r")) {
					JLabel piece = new JLabel( new ImageIcon("SnippingRook.JPG") );
					JPanel panel = (JPanel)chessBoard.getComponent(componentNumber);
					panel.add(piece);
				}
				if (initialBoard[x][y].equals("P")) {
					JLabel piece = new JLabel( new ImageIcon("SnippingWhitePawn.JPG") );
					JPanel panel = (JPanel)chessBoard.getComponent(componentNumber);
					panel.add(piece);
				}
				if (initialBoard[x][y].equals("p")) {
					JLabel piece = new JLabel( new ImageIcon("SnippingPawn.JPG") );
					JPanel panel = (JPanel)chessBoard.getComponent(componentNumber);
					panel.add(piece);
				}
			}
		}
		chessBoard.add(b1);
		chessBoard.add(b2);
		chessBoard.add(b3);
//		chessBoard.add(textField);
		
	}
	
	public void mousePressed(MouseEvent e) {
		chessPiece = null;
		Component c =  chessBoard.findComponentAt(e.getX(), e.getY());
		if (c instanceof JPanel)
			return;
		Point parentLocation = c.getParent().getLocation();
		xAdjustment = parentLocation.x - e.getX();
		yAdjustment = parentLocation.y - e.getY();
		chessPiece = (JLabel)c;
		chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
		chessPiece.setSize(chessPiece.getWidth(), chessPiece.getHeight());
		layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER);
	}
	
	//Move the chess piece around
	public void mouseDragged(MouseEvent me) {
		if (chessPiece == null) 
			return;
		chessPiece.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);
	}
	
	//Drop the chess piece back onto the chess board
	public void mouseReleased(MouseEvent e) {
		if(chessPiece == null)
			return;
		chessPiece.setVisible(false);
		Component c =  chessBoard.findComponentAt(e.getX(), e.getY());
		
		if (c instanceof JLabel) {
			Container parent = c.getParent();
			parent.remove(0);
			parent.add( chessPiece );
		} else {
			Container parent = (Container)c;
			parent.add( chessPiece );
		}
		
		chessPiece.setVisible(true);
	}
	
	public void mouseClicked(MouseEvent e) {
	}
	
	public void mouseMoved(MouseEvent e) {
	}
	
	public void mouseEntered(MouseEvent e) {
	}
	
	public void mouseExited(MouseEvent e) {
	}
	
	 public void actionPerformed(ActionEvent e) {
		//System.out.println("e: " + e.getActionCommand());
		String which = e.getActionCommand();
		if(which.equals("next")) {
			index += 1;
			//System.out.println("index: " + index);
			if (index > myBoards.size() - 1) {
				System.err.println("No more matched boards!");
				index = myBoards.size() - 1;
			} else {
				//System.out.println("index: " + index);
				String[][] current = myBoards.get(index - 1);
				String[][] next = myBoards.get(index);
				if (index < myBoards.size() - 1) {
					//index += 1;
				} else {
					index = myBoards.size() - 1;
				}
				// find square of component that moved
				int x = 0;
				int y = 0;
				int newX = 0;
				int newY = 0;
				boolean wasCapture = false;
				boolean wasCastle = false;
				// first check if special castling case happened
				/*for (int m = 0; m < myCastles.size(); m++) {
					CastleMove temp = myCastles.get(m);
					//System.out.println("demo round: " + temp.getRound());
					//System.out.println("demo index: " + index);
					if (temp.getRound() + 1 == index) {
						//System.out.println("index = demo");
						wasCastle = true;
						if (temp.getColor().equals("b") && temp.getKingOrQueen().equals("king")) {
							// handle rook move first
							x = 7;
							y = 7;
							newX = 5;
							newY = 7;
							int a = Util.reverseNum(y);
							int a2 = Util.reverseNum(newY);
							//System.out.println("x: " + x);
							//System.out.println("y: " + y);
							Component c2 = chessBoard.getComponent(a*8 + x);
							//System.out.println("result: " + (a*8 + x));
							JLabel piece = (JLabel) c2.getComponentAt(1, 0);
							piece.setVisible(false);
							Component c =  chessBoard.getComponent(a2*8 + newX);			
							if (c instanceof JLabel) {
								Container parent = c.getParent();
								parent.remove(0);
								parent.add( piece );
							} else {
								JPanel parent = (JPanel) c;
								if (wasCapture) {
									JLabel capturedPiece = (JLabel) c.getComponentAt(1, 0);
									capturedPieces.add(capturedPiece);
									parent.remove(0);
								}
								parent.add( piece );
							}
							piece.setVisible(true);
							
							// handle king move
							x = 4;
							y = 7;
							newX = 6;
							newY = 7;
							a = Util.reverseNum(y);
							a2 = Util.reverseNum(newY);
							//System.out.println("x: " + x);
							//System.out.println("y: " + y);
							c2 = chessBoard.getComponent(a*8 + x);
							//System.out.println("result: " + (a*8 + x));
							piece = (JLabel) c2.getComponentAt(1, 0);
							piece.setVisible(false);
							c =  chessBoard.getComponent(a2*8 + newX);			
							if (c instanceof JLabel) {
								Container parent = c.getParent();
								parent.remove(0);
								parent.add( piece );
							} else {
								JPanel parent = (JPanel) c;
								if (wasCapture) {
									JLabel capturedPiece = (JLabel) c.getComponentAt(1, 0);
									capturedPieces.add(capturedPiece);
									parent.remove(0);
								}
								parent.add( piece );
							}
							piece.setVisible(true);
							
							
						} else if (temp.getColor().equals("w") && temp.getKingOrQueen().equals("king")) {
							// move rook first
							x = 7;
							y = 0;
							newX = 5;
							newY = 0;
							int a = Util.reverseNum(y);
							int a2 = Util.reverseNum(newY);
							//System.out.println("x: " + x);
							//System.out.println("y: " + y);
							Component c2 = chessBoard.getComponent(a*8 + x);
							//System.out.println("result: " + (a*8 + x));
							JLabel piece = (JLabel) c2.getComponentAt(1, 0);
							piece.setVisible(false);
							Component c =  chessBoard.getComponent(a2*8 + newX);			
							if (c instanceof JLabel) {
								Container parent = c.getParent();
								parent.remove(0);
								parent.add( piece );
							} else {
								//System.out.println("here");
								JPanel parent = (JPanel) c;
								if (wasCapture) {
									JLabel capturedPiece = (JLabel) c.getComponentAt(1, 0);
									capturedPieces.add(capturedPiece);
									parent.remove(0);
								}
								parent.add( piece );
							}
							piece.setVisible(true);
							
							x = 4;
							y = 0;
							newX = 6;
							newY = 0;
							a = Util.reverseNum(y);
							a2 = Util.reverseNum(newY);
							//System.out.println("x: " + x);
							//System.out.println("y: " + y);
							c2 = chessBoard.getComponent(a*8 + x);
							//System.out.println("result: " + (a*8 + x));
							piece = (JLabel) c2.getComponentAt(1, 0);
							piece.setVisible(false);
							c =  chessBoard.getComponent(a2*8 + newX);			
							if (c instanceof JLabel) {
								Container parent = c.getParent();
								parent.remove(0);
								parent.add( piece );
							} else {
								//System.out.println("here");
								JPanel parent = (JPanel) c;
								if (wasCapture) {
									JLabel capturedPiece = (JLabel) c.getComponentAt(1, 0);
									capturedPieces.add(capturedPiece);
									parent.remove(0);
								}
								parent.add( piece );
							}
							piece.setVisible(true);
							
						} if (temp.getColor().equals("b") && temp.getKingOrQueen().equals("queen")) {
							x = 0;
							y = 7;
							newX = 3;
							newY = 7;
							int a = Util.reverseNum(y);
							int a2 = Util.reverseNum(newY);
							//System.out.println("x: " + x);
							//System.out.println("y: " + y);
							Component c2 = chessBoard.getComponent(a*8 + x);
							//System.out.println("result: " + (a*8 + x));
							JLabel piece = (JLabel) c2.getComponentAt(1, 0);
							piece.setVisible(false);
							Component c =  chessBoard.getComponent(a2*8 + newX);			
							if (c instanceof JLabel) {
								Container parent = c.getParent();
								parent.remove(0);
								parent.add( piece );
							} else {
								//System.out.println("here");
								JPanel parent = (JPanel) c;
								if (wasCapture) {
									JLabel capturedPiece = (JLabel) c.getComponentAt(1, 0);
									capturedPieces.add(capturedPiece);
									parent.remove(0);
								}
								parent.add( piece );
							}
							piece.setVisible(true);
							
							x = 4;
							y = 7;
							newX = 2;
							newY = 7;
							a = Util.reverseNum(y);
							a2 = Util.reverseNum(newY);
							//System.out.println("x: " + x);
							//System.out.println("y: " + y);
							c2 = chessBoard.getComponent(a*8 + x);
							//System.out.println("result: " + (a*8 + x));
							piece = (JLabel) c2.getComponentAt(1, 0);
							piece.setVisible(false);
							c =  chessBoard.getComponent(a2*8 + newX);			
							if (c instanceof JLabel) {
								Container parent = c.getParent();
								parent.remove(0);
								parent.add( piece );
							} else {
								//System.out.println("here");
								JPanel parent = (JPanel) c;
								if (wasCapture) {
									JLabel capturedPiece = (JLabel) c.getComponentAt(1, 0);
									capturedPieces.add(capturedPiece);
									parent.remove(0);
								}
								parent.add( piece );
							}
							piece.setVisible(true);
							
						} if (temp.getColor().equals("w") && temp.getKingOrQueen().equals("queen")) {
							x = 0;
							y = 0;
							newX = 3;
							newY = 0;
							int a = Util.reverseNum(y);
							int a2 = Util.reverseNum(newY);
							//System.out.println("x: " + x);
							//System.out.println("y: " + y);
							Component c2 = chessBoard.getComponent(a*8 + x);
							//System.out.println("result: " + (a*8 + x));
							JLabel piece = (JLabel) c2.getComponentAt(1, 0);
							piece.setVisible(false);
							Component c =  chessBoard.getComponent(a2*8 + newX);			
							if (c instanceof JLabel) {
								Container parent = c.getParent();
								parent.remove(0);
								parent.add( piece );
							} else {
								//System.out.println("here");
								JPanel parent = (JPanel) c;
								if (wasCapture) {
									JLabel capturedPiece = (JLabel) c.getComponentAt(1, 0);
									capturedPieces.add(capturedPiece);
									parent.remove(0);
								}
								parent.add( piece );
							}
							piece.setVisible(true);
							
							x = 4;
							y = 0;
							newX = 2;
							newY = 0;
							a = Util.reverseNum(y);
							a2 = Util.reverseNum(newY);
							//System.out.println("x: " + x);
							//System.out.println("y: " + y);
							c2 = chessBoard.getComponent(a*8 + x);
							//System.out.println("result: " + (a*8 + x));
							piece = (JLabel) c2.getComponentAt(1, 0);
							piece.setVisible(false);
							c =  chessBoard.getComponent(a2*8 + newX);			
							if (c instanceof JLabel) {
								Container parent = c.getParent();
								parent.remove(0);
								parent.add( piece );
							} else {
								//System.out.println("here");
								JPanel parent = (JPanel) c;
								if (wasCapture) {
									JLabel capturedPiece = (JLabel) c.getComponentAt(1, 0);
									capturedPieces.add(capturedPiece);
									parent.remove(0);
								}
								parent.add( piece );
							}
							piece.setVisible(true);
						}
					}
				} */
				
				if (wasCastle == false) {
					for (int i = 0; i < 8; i++) {
						for (int j = 0; j < 8; j++) {
							String currS = current[i][j];
							String nextS = next[i][j];
							if (currS.equals(nextS) == false) {
								if (nextS.equals("-")) {
									//System.out.println("currS: " + currS);
									//System.out.println("nextS: " + nextS);
									x = i;
									y = j;
								} else {
									// can't use - b/c what if captured a piece
									if (currS.equals("-")) {
										// new pos
										newX = i;
										newY = j;
									} else {
										wasCapture = true;
										newX = i;
										newY = j;
									}
								}
							}
						}
					}
					int a = Util.reverseNum(y);
					int a2 = Util.reverseNum(newY);
					//System.out.println("x: " + x);
					//System.out.println("y: " + y);
					Component c2 = chessBoard.getComponent(a*8 + x);
					//System.out.println("result: " + (a*8 + x));
					JLabel piece = (JLabel) c2.getComponentAt(1, 0);
					piece.setVisible(false);
					Component c =  chessBoard.getComponent(a2*8 + newX);			
					if (c instanceof JLabel) {
						Container parent = c.getParent();
						parent.remove(0);
						parent.add( piece );
					} else {
						//System.out.println("here");
						JPanel parent = (JPanel) c;
						if (wasCapture) {
							JLabel capturedPiece = (JLabel) c.getComponentAt(1, 0);
							capturedPieces.add(capturedPiece);
							parent.remove(0);
						}
						parent.add( piece );
					}
					piece.setVisible(true);
				}
			}
		} else if(which.equals("back")) {  // go back
			System.out.println("index: " + index);
				if (index < 1) {
					System.out.println("Can't go further back!");
				} else if (index > myBoards.size() - 1) {
					 index -= 1;
				} else {
					String[][] previous = myBoards.get(index - 1);
					String[][] current = myBoards.get(index);
					//index -= 1;
				    if (index > 0) {
						index -= 1;
					}
					// find square of component that moved
					int x = 0;
					int y = 0;
					int newX = 0;
					int newY = 0;
					boolean wasCapture = false;
					boolean wasCastle = false;
					// first check if special castling case happened
					/*for (int m = 0; m < myCastles.size(); m++) {
						CastleMove temp = myCastles.get(m);
						//System.out.println("back demo round: " + temp.getRound());
						//System.out.println("back demo index: " + index);
						if (temp.getRound() == index) {
							//System.out.println("index = demo");
							wasCastle = true;
							if (temp.getColor().equals("b") && temp.getKingOrQueen().equals("king")) {
								// handle rook move first
								x = 5;
								y = 7;
								newX = 7;
								newY = 7;
								int a = Util.reverseNum(y);
								int a2 = Util.reverseNum(newY);
								//System.out.println("x: " + x);
								//System.out.println("y: " + y);
								Component c2 = chessBoard.getComponent(a*8 + x);
								//System.out.println("result: " + (a*8 + x));
								JLabel piece = (JLabel) c2.getComponentAt(1, 0);
								piece.setVisible(false);
								Component c =  chessBoard.getComponent(a2*8 + newX);			
								if (c instanceof JLabel) {
									Container parent = c.getParent();
									parent.remove(0);
									parent.add( piece );
								} else {
									
									JPanel parent = (JPanel) c;
									if (wasCapture) {
										//System.out.println("back captured");
										JPanel parent2 = (JPanel) c2;
										JLabel piece2 = capturedPieces.get(capturedPieces.size() - 1);
										capturedPieces.remove(capturedPieces.size() - 1);
										parent2.add(piece2);
									}
									parent.add( piece );
								}
								piece.setVisible(true);
								
								// handle king move
								x = 6;
								y = 7;
								newX = 4;
								newY = 7;
								a = Util.reverseNum(y);
								a2 = Util.reverseNum(newY);
								//System.out.println("x: " + x);
								//System.out.println("y: " + y);
								c2 = chessBoard.getComponent(a*8 + x);
								//System.out.println("result: " + (a*8 + x));
								piece = (JLabel) c2.getComponentAt(1, 0);
								piece.setVisible(false);
								c =  chessBoard.getComponent(a2*8 + newX);			
								if (c instanceof JLabel) {
									Container parent = c.getParent();
									parent.remove(0);
									parent.add( piece );
								} else {
									//System.out.println("here");
									JPanel parent = (JPanel) c;
									if (wasCapture) {
										parent.remove(0);
									}
									parent.add( piece );
								}
								piece.setVisible(true);
								
								
							} else if (temp.getColor().equals("w") && temp.getKingOrQueen().equals("king")) {
								// move rook first
								x = 5;
								y = 0;
								newX = 7;
								newY = 0;
								int a = Util.reverseNum(y);
								int a2 = Util.reverseNum(newY);
								//System.out.println("x: " + x);
								//System.out.println("y: " + y);
								Component c2 = chessBoard.getComponent(a*8 + x);
								//System.out.println("result: " + (a*8 + x));
								JLabel piece = (JLabel) c2.getComponentAt(1, 0);
								piece.setVisible(false);
								Component c =  chessBoard.getComponent(a2*8 + newX);			
								if (c instanceof JLabel) {
									Container parent = c.getParent();
									parent.remove(0);
									parent.add( piece );
								} else {
									//System.out.println("here");
									JPanel parent = (JPanel) c;
									if (wasCapture) {
										parent.remove(0);
									}
									parent.add( piece );
								}
								piece.setVisible(true);
								
								x = 6;
								y = 0;
								newX = 4;
								newY = 0;
								a = Util.reverseNum(y);
								a2 = Util.reverseNum(newY);
								//System.out.println("x: " + x);
								//System.out.println("y: " + y);
								c2 = chessBoard.getComponent(a*8 + x);
								//System.out.println("result: " + (a*8 + x));
								piece = (JLabel) c2.getComponentAt(1, 0);
								piece.setVisible(false);
								c =  chessBoard.getComponent(a2*8 + newX);			
								if (c instanceof JLabel) {
									Container parent = c.getParent();
									parent.remove(0);
									parent.add( piece );
								} else {
									//System.out.println("here");
									JPanel parent = (JPanel) c;
									if (wasCapture) {
										parent.remove(0);
									}
									parent.add( piece );
								}
								piece.setVisible(true);
								
							} if (temp.getColor().equals("b") && temp.getKingOrQueen().equals("queen")) {
								x = 3;
								y = 7;
								newX = 0;
								newY = 7;
								int a = Util.reverseNum(y);
								int a2 = Util.reverseNum(newY);
								//System.out.println("x: " + x);
								//System.out.println("y: " + y);
								Component c2 = chessBoard.getComponent(a*8 + x);
								//System.out.println("result: " + (a*8 + x));
								JLabel piece = (JLabel) c2.getComponentAt(1, 0);
								piece.setVisible(false);
								Component c =  chessBoard.getComponent(a2*8 + newX);			
								if (c instanceof JLabel) {
									Container parent = c.getParent();
									parent.remove(0);
									parent.add( piece );
								} else {
									//System.out.println("here");
									JPanel parent = (JPanel) c;
									if (wasCapture) {
										parent.remove(0);
									}
									parent.add( piece );
								}
								piece.setVisible(true);
								
								x = 2;
								y = 7;
								newX = 4;
								newY = 7;
								a = Util.reverseNum(y);
								a2 = Util.reverseNum(newY);
								//System.out.println("x: " + x);
								//System.out.println("y: " + y);
								c2 = chessBoard.getComponent(a*8 + x);
								System.out.println("result: " + (a*8 + x));
								piece = (JLabel) c2.getComponentAt(1, 0);
								piece.setVisible(false);
								c =  chessBoard.getComponent(a2*8 + newX);			
								if (c instanceof JLabel) {
									Container parent = c.getParent();
									parent.remove(0);
									parent.add( piece );
								} else {
									//System.out.println("here");
									JPanel parent = (JPanel) c;
									if (wasCapture) {
										parent.remove(0);
									}
									parent.add( piece );
								}
								piece.setVisible(true);
								
							} if (temp.getColor().equals("w") && temp.getKingOrQueen().equals("queen")) {
								x = 3;
								y = 0;
								newX = 0;
								newY = 0;
								int a = Util.reverseNum(y);
								int a2 = Util.reverseNum(newY);
								//System.out.println("x: " + x);
								//System.out.println("y: " + y);
								Component c2 = chessBoard.getComponent(a*8 + x);
								System.out.println("result: " + (a*8 + x));
								JLabel piece = (JLabel) c2.getComponentAt(1, 0);
								piece.setVisible(false);
								Component c =  chessBoard.getComponent(a2*8 + newX);			
								if (c instanceof JLabel) {
									Container parent = c.getParent();
									parent.remove(0);
									parent.add( piece );
								} else {
									//System.out.println("here");
									JPanel parent = (JPanel) c;
									if (wasCapture) {
										parent.remove(0);
									}
									parent.add( piece );
								}
								piece.setVisible(true);
								
								x = 2;
								y = 0;
								newX = 4;
								newY = 0;
								a = Util.reverseNum(y);
								a2 = Util.reverseNum(newY);
								//System.out.println("x: " + x);
								//System.out.println("y: " + y);
								c2 = chessBoard.getComponent(a*8 + x);
								System.out.println("result: " + (a*8 + x));
								piece = (JLabel) c2.getComponentAt(1, 0);
								piece.setVisible(false);
								c =  chessBoard.getComponent(a2*8 + newX);			
								if (c instanceof JLabel) {
									Container parent = c.getParent();
									parent.remove(0);
									parent.add( piece );
								} else {
									//System.out.println("here");
									JPanel parent = (JPanel) c;
									if (wasCapture) {
										parent.remove(0);
									}
									parent.add( piece );
								}
								piece.setVisible(true);
							}
						}
					} */
					
					int capturednewX = 0;
					int capturednewY = 0;
					if (wasCastle == false) {
						for (int i = 0; i < 8; i++) {
							for (int j = 0; j < 8; j++) {
								String currS = current[i][j];
								String previousS = previous[i][j];
								if (currS.equals(previousS) == false) {
									if (previousS.equals("-")) {
										// then the piece moved from previous to current
										// with capture this doesn't happen
										// thus need to move it away from here
										x = i;
										y = j;
									}  else if (previousS.equals("-") != true) {
										// this is true of where the attacking piece used to be & where the attacking piece went
										// if it's where it used to be, the nothing there and computer will be unhappy b/c can't access
										// that location
										if (currS.equals("-")) {
											// then this is where the attacking piece moved from
											// want attacking piece to move back here
											newX = i;
											newY = j;
										} else {
											//System.out.println("next capture");
											wasCapture = true;
											capturednewX = i;
											capturednewY = i;
											x = i;
											y = j;
										}
										
									} 
								}
							}
						}
						int a = Util.reverseNum(y);
						int a2 = Util.reverseNum(newY);
						//System.out.println("back x: " + x);
						//System.out.println("back y: " + y);
						Component c2 = chessBoard.getComponent(a*8 + x); // trying to get the piece that needs to be moved
						//System.out.println("result: " + (a*8 + x));
						JLabel tobeMoved = (JLabel) c2.getComponentAt(1, 0);
						tobeMoved.setVisible(false);
						Component c =  chessBoard.getComponent(a2*8 + newX);			
						if (c instanceof JLabel) {
							Container parent = c.getParent();
							parent.remove(0);
							parent.add( tobeMoved );
						} else {
							JPanel parent = (JPanel) c;
							if (wasCapture) {
								JPanel panel = (JPanel)chessBoard.getComponent(a*8 + x);
								JLabel backonBoard = capturedPieces.get(capturedPieces.size() -1);
								panel.add(backonBoard);
								capturedPieces.remove(backonBoard);
							}
							parent.add( tobeMoved );
						}
						tobeMoved.setVisible(true);
					
					
					/*
					if (wasCastle == false) {
						for (int i = 0; i < 8; i++) {
							for (int j = 0; j < 8; j++) {
								String currS = current[i][j];
								String previousS = previous[i][j];
								if (currS.equals(previousS) == false) {
									if (previousS.equals("-")) {
										// then the piece moved from previous to current
										// with capture this doesn't happen
										x = i;
										y = j;
									}  else if (previousS.equals("-") != true) {
										// then a piece that was at next was taken by a piece that moved there
										// this is true of where the attacking piece used to be & where the attacking piece went
										// if it's where it used to be, the nothing there and computer will be unhappy b/c can't access
										// that location
										System.out.println("next capture");
										wasCapture = true;
										x = i;
										y = j;
									}  else {
										// then this is where new piece moved
										if (currS.equals("-")) {
											// new pos
											newX = i;
											newY = j;
										} 
										//else {
											// must've been capture
										//	wasCapture = true;
										//	newX = i;
										//	newY = j;
										//}
									}
								}
							}
						}
						int a = Util.reverseNum(y);
						int a2 = Util.reverseNum(newY);
						System.out.println("back x: " + x);
						System.out.println("back y: " + y);
						Component c2 = chessBoard.getComponent(a*8 + x); // trying to get the piece that needs to be moved
						System.out.println("result: " + (a*8 + x));
						JLabel piece = (JLabel) c2.getComponentAt(1, 0);
						piece.setVisible(false);
						Component c =  chessBoard.getComponent(a2*8 + newX);			
						if (c instanceof JLabel) {
							Container parent = c.getParent();
							parent.remove(0);
							parent.add( piece );
						} else {
							JPanel parent = (JPanel) c;
							parent.add( piece );
						}
						piece.setVisible(true);
				*/
				}
				
				}
		}	else { // it's a find
			myJouster.find(myProgram);
		}
		
		
		
		/*
		Component c2 = chessBoard.findComponentAt(0,0);
		JLabel piece = (JLabel) c2;
		piece.setVisible(false);
		Component c =  chessBoard.getComponent(40);			
		if (c instanceof JLabel) {
			Container parent = c.getParent();
			parent.remove(0);
			parent.add( piece );
		} else {
			JPanel parent = (JPanel) c;
			parent.add( piece );
		}
		piece.setVisible(true);
		*/
 }
	/*
	public static void main(String[] args) {
		JFrame frame = new ChessGameDemo();
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE );
		frame.pack();
		frame.setResizable(true);
		frame.setLocationRelativeTo( null );
		frame.setVisible(true);
	}
	*/
	
	public static void start(List<String[][]> boards, ChessBoardMatcher j, String joustProgram) {
		JFrame frame = new ChessGameDemo(boards, j, joustProgram);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE );
		frame.pack();
		frame.setResizable(true);
		frame.setLocationRelativeTo( null );
		frame.setVisible(true);
	}
}