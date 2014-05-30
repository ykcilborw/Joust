package com.wroblicky.andrew.joust.core.general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wroblicky.andrew.joust.core.chesspiece.ChessPiece;
import com.wroblicky.andrew.joust.core.general.game.GameSetup;
import com.wroblicky.andrew.joust.pgn.PGNParser;

import fri.patterns.interpreter.parsergenerator.examples.Joust;

/**
 * The interpreter for the Joust language, it is designed to mirror the
 * Matcher class for regexes in Java
 */
public class ChessBoardMatcher {
	GameManagerImpl myGame;
	List<String> myMoves;
	Map<String, ArrayList<ChessPiece>> mySuspects;
	List<String[][]> matchedBoards;
	List<Integer> matchedRounds;
	boolean isSpecial;
	boolean myGreedFlag; // for greedy search when dealing with the very last board
	boolean occursFlag;
	
	// need to rethink
	public ChessBoardMatcher(String pgnGame) {
		myGame = GameSetup.setupDefaultGame();
		myMoves = PGNParser.getPGNGame(pgnGame).getMoves();
		matchedBoards = new ArrayList<String[][]>();
		matchedRounds = new ArrayList<Integer>();
		mySuspects = new HashMap<String, ArrayList<ChessPiece>> ();
		isSpecial = false;
		occursFlag = false;
	}
	
	public ChessBoardMatcher(String pgnGame, String initialConfigFile) {
		myGame = GameSetup.setupSpecialLayout(initialConfigFile);
		myMoves = PGNParser.getPGNGame(pgnGame).getMoves();
		matchedBoards = new ArrayList<String[][]>();
		matchedRounds = new ArrayList<Integer>();
		mySuspects = new HashMap<String, ArrayList<ChessPiece>> ();
		isSpecial = true;
		occursFlag = false;
	}
	
	public ChessBoardMatcher(String pgnGame, String initialConfigFile, boolean special) {
		myGame = GameSetup.setupSpecialLayout(initialConfigFile);
		myMoves = PGNParser.getPGNGame(pgnGame).getMoves();
		matchedBoards = new ArrayList<String[][]>();
		matchedRounds = new ArrayList<Integer>();
		mySuspects = new HashMap<String, ArrayList<ChessPiece>> ();
		isSpecial = special;
		occursFlag = false;
	}
	
	protected GameManagerImpl getGame() {
		return myGame;
	}
	
	protected List<String[][]> getMatchedBoards() {
		return matchedBoards;
	}
	
	protected List<Integer> getMatchedRounds () {
		return matchedRounds;
	}
	
	private List<Object> getAST(String joustProgram) {
		List<Object> ast = null;
		try {
			ast = Joust.getAST(joustProgram);
		} catch (Exception e) {
			System.err.println("Invalid Joust Program");
			System.exit(-1);
		}
		return ast;
	}
	
	public boolean find(String joustProgram){
		matchedBoards = new ArrayList<String[][]>(); // need to reset matchedBoards if it's called n times
		List<Object> ast = getAST(joustProgram);
		System.out.println("ast: " + ast);
		boolean foundMatch = findSequence(ast);
		System.out.println("find foundMatch: " + foundMatch);
		if (foundMatch == true) {
			Util.handleResults(matchedBoards, matchedRounds, this, joustProgram);
		}
		return foundMatch;
	}
	
	// not actually used, could be useful later??
	public boolean findHelper(ArrayList<Object> token) {
		boolean foundMatch = false;
		ArrayList<Object> temp = (ArrayList<Object>) token.get(0);
		if (temp.get(0).equals("through")) {
			foundMatch = findThroughSequence(token);
		} else {
			// it's a sequence
			foundMatch = findSequence(token);
		}
		// Eventually should be printing the matched boards here
		return foundMatch;
	}
		
		
	private boolean findToken(ArrayList<Object> token) {
		//System.out.println("findToken");
		boolean foundMatch = false;
		while (foundMatch == false && myGame.isInProgress() == true) {
			myGame.printBoard2(myGame.getRound());
			//System.out.println("The Positions:");
			//myGame.printMyPositions();
			foundMatch = evalStatement(token);
			//System.out.println("findToken foundMatch: " + foundMatch);
			if (foundMatch == true && token.get(0).equals("occurs") == false) {
				// add board to matched boards as well as round
				String[][] board = Util.getStringBoard(myGame.getActivePieces());
				matchedBoards.add(board);
				int round = myGame.getRound();
				matchedRounds.add(round);
			}
			// no point updating board if we found a match, just return it
			if (isSpecial == false && myMoves.size() != myGame.getRound()) { //&& token.get(0).equals("occurs") == false) {
				myGame.update(myMoves);
			} else {
				// if they are equal need to update myRound still so that end doesn't keep returning true forever
				myGame.setRound(myGame.getRound() + 1);
			}
		}
		//System.out.println("findToken2 foundMatch: " + foundMatch);
		return foundMatch;
	}
	
	private boolean findThroughToken(ArrayList<Object> token) {
		//System.out.println("findThroughToken");
		boolean foundMatch = false;
		while (foundMatch == false && myGame.isInProgress() == true) {
			myGame.printBoard2(myGame.getRound());
			//System.out.println("token: " + token);
			foundMatch = evalStatement(token);
			//System.out.println("findThroughToken foundMatch: " + foundMatch);
			// Since it's find through token include matched board regardless if foundMatch is true
			String[][] board = Util.getStringBoard(myGame.getActivePieces());
			matchedBoards.add(board);
			int round = myGame.getRound();
			matchedRounds.add(round);
			// need to update board so next token has proper configuration
			// However if it's end of game don't worry about it
			if (myMoves.size() != myGame.getRound() ){//&& token.get(0).equals("occurs") == false) {
				myGame.update(myMoves);
			} 
		}
		return foundMatch;
	}
	
	private boolean findUntilToken(ArrayList<Object> token) {
		//System.out.println("findUntilToken");
		boolean foundMatch = false;
		boolean gameStillGoing = true;
		while (foundMatch == false && gameStillGoing == true) {
			myGame.printBoard2(myGame.getRound());
			foundMatch = evalStatement(token);
			//System.out.println("findUntilToken token: " + token);
			//System.out.println("findUntilToken foundMatch: " + foundMatch);
			// only add board if we DIDN'T find a match b/c until
			if (foundMatch == false) {
				String[][] board = Util.getStringBoard(myGame.getActivePieces());
				matchedBoards.add(board);
				int round = myGame.getRound();
				matchedRounds.add(round);
				// Similarly only update board if not a match && not end of game
				if (myMoves.size() - 1 != myGame.getRound()) {
					myGame.update(myMoves);
				} 
				else {
					// end of game
					//System.out.println("findUntilToken myGame progress: " + myGreedFlag);
					if (myGreedFlag == true) {
						foundMatch = true;
					}
				}
			}
			if (myGame.isInProgress() == false) {
				gameStillGoing = false;
			}
			//System.out.println("findUntilToken2 foundMatch: " + foundMatch);
		}
		return foundMatch;
	}
	
	private boolean findSequence(List<Object> sequence) {
		boolean foundMatch = false;
		int size = sequence.size();
		int tokensLeft = size;
		for (int i = 0; i < size; i++) {
			ArrayList<Object> current = (ArrayList<Object>) sequence.get(i);
			//System.out.println("current.get(0): " + current.get(0));
			if (current.get(0).equals("through")) {
				if (findThroughSequence(current) != true) {
					break;
				} else {
					tokensLeft -= 1;
				}
			} else if (current.get(0).equals("until")) {
				if (findUntilSequence(current) != true) {
					break;
				} else {
					tokensLeft -= 1;
				}
			} else if (current.get(0).equals("*")) {
				//System.out.println("current: " + current);
				current.remove(current.get(0));
				current.add(0, "until");
				ArrayList<Object> notStatement = new ArrayList<Object>();
				notStatement.add("not");
				notStatement.add(current.get(1));
				ArrayList<Object> orStatement = new ArrayList<Object>();
				orStatement.add("or");
				orStatement.add(notStatement);
				ArrayList<Object> endStatement = new ArrayList<Object>();
				endStatement.add("greedFlag");
				orStatement.add(endStatement);
				//current.add(2, notStatement);
				current.add(2, orStatement);
				//System.out.println("*current: " + current);
				if (findUntilSequence(current) != true) {
					break;
				} else {
					tokensLeft -= 1;
					// since its through adds one too many boards
					//matchedBoards.remove(matchedBoards.size() - 1);
				}
			} else if (findToken(current) != true) {
				break;
			} else {
				tokensLeft -= 1;
			}
		}
		if (tokensLeft == 0) {
			foundMatch = true;
		}
		//System.out.println("findSequence foundMatch: " + foundMatch);
		return foundMatch;
	}
	
	private boolean findThroughSequence(ArrayList<Object> sequence) {
		//System.out.println("In find through sequence");
		boolean foundMatch = false;
		// first eval left branch
		ArrayList<Object> left = (ArrayList<Object>) sequence.get(1);
		// left could be another through statement so to be safe send it to findHelper
		Object test = left.get(0);
		boolean occursFlagLeft = false;
		boolean leftSuccess = false;
		if (test instanceof String) {
			String test2 = (String) test;
			if (test2.equals("through")) {
				leftSuccess = findThroughSequence(left);
			} else if (test2.equals("until")) {
				leftSuccess = findUntilSequence(left);
			} else if (test2.equals("occurs")) {
				occursFlagLeft = true;
				leftSuccess = findToken(left);
			}else {
				//System.out.println("findThroughSequence left: " + left);
				leftSuccess = findToken(left);
			}
		} else {
			leftSuccess = findSequence(left);
		}
		// check if succeeded otherwise no point
		if (leftSuccess) {
			// now add to matchedBoards and rounds every time a match occurs
			boolean rightSuccess = false;
			ArrayList<Object> right = (ArrayList<Object>) sequence.get(2);
			Object test2 = right.get(0);
			//System.out.println("test2: " + test2);
			String s = (String) test2;
			//System.out.println("through sequence s: " + s);
			if (s.equals("occurs") && occursFlagLeft == false) {
				ArrayList<Object> arg = (ArrayList<Object>) right.get(1);
				rightSuccess = findUntilToken(arg);
			} else if (s.equals("occurs")) {
				// can't have occurs on both sides of through/until statement!!
				System.err.println("Invalid Joust Program. One cannot have occurs statements on both sides of a through statement");
				System.exit(-1);
			} else {
				// right has to be a statement so safe to call findToken
				
					if (occursFlagLeft) {
						rightSuccess = findToken(right);
					} else {
						rightSuccess = findThroughToken(right);
					}
			}
			if (rightSuccess == true) {
					foundMatch = true;
			}
			
			//System.out.println("rightSuccess: " + rightSuccess);
		} 
		return foundMatch;
	}
	
	private boolean findUntilSequence(ArrayList<Object> sequence) {
		//System.out.println("In find through sequence");
		boolean foundMatch = false;
		// first eval left branch
		ArrayList<Object> left = (ArrayList<Object>) sequence.get(1);
		// left could be another through/until statement so to be safe send it to findHelper
		Object test = left.get(0);
		boolean occursFlag2 = false;
		boolean leftSuccess = false;
		if (test instanceof String) {
			String test2 = (String) test;
			if (test2.equals("through")) {
				leftSuccess = findThroughSequence(left);
			} else if (test2.equals("until")) {
				leftSuccess = findUntilSequence(left);
			} else if (test2.equals("occurs")) {
				occursFlag2 = true;
				leftSuccess = findToken(left);
			} else {
				leftSuccess = findToken(left);
			}
		} else {
			leftSuccess = findSequence(left);
		}
		// check if succeeded otherwise no point
		if (leftSuccess) {
			// now add to matchedBoards and rounds every time a match occurs
			ArrayList<Object> right = (ArrayList<Object>) sequence.get(2);
			// right has to be a statement so safe to call findToken
			if (occursFlag2) {
				System.err.println("Invalid Joust program. One cannot have an occurs statement before an until");
				System.exit(-1);
			}
			boolean rightSuccess = findUntilToken(right);
			if (rightSuccess == true) {
				foundMatch = true;
			}
			myGreedFlag = false; // reset state so last board isn't getting  matched for non greedy search
		} 
		return foundMatch;
	}

	private boolean evalStatement(ArrayList<Object> ast) {
		//System.out.println("evalStatement current statement: " + ast);
		boolean toReturn = false;
		Object next = ast.get(0);
		if (next instanceof String) {
			if (next.equals("occupies")) {
				//System.out.println("occupies here");
				String arg1 = (String) ast.get(1);
				String arg2 = (String) ast.get(2);
				//arg2 = arg2.substring(0, arg2.length() -1);
				toReturn = execOccupies(arg1, arg2);
			} else if (next.equals("betweenFile")) {
				String arg1 = (String) ast.get(1);
				String arg2 = (String) ast.get(2);
				String arg3 = (String) ast.get(3);
				ArrayList<Object> left = new ArrayList<Object>();
				left.add("lowerFile");
				left.add(arg2);
				left.add(arg1);
				boolean leftResult = evalStatement(left); 
				//System.out.println("left result: " + leftResult);
				if (leftResult == true) {
					ArrayList<Object> right = new ArrayList<Object>();
					right.add("lowerFile");
					right.add(arg1);
					right.add(arg3);
					boolean rightResult = evalStatement(right);
					if (rightResult) {
						toReturn = true;
					} else {
						toReturn = false;
					}
				} else {
					toReturn = false;
				}
			}  else if (next.equals("captured")) {
				String arg1 = (String) ast.get(1);
				String arg2 = (String) ast.get(2);
				//arg2 = arg2.substring(0, arg2.length() -1);
				toReturn = execCaptured(arg1, arg2);
			} else if (next.equals("sameFile")) {
				String arg1 = (String) ast.get(1);
				String arg2 = (String) ast.get(2);
				//arg2 = arg2.substring(0, arg2.length() -1);
				toReturn = execSameFile(arg1, arg2);
			}  else if (next.equals("higherFile")) {
				String arg1 = (String) ast.get(1);
				String arg2 = (String) ast.get(2);
				//arg2 = arg2.substring(0, arg2.length() -1);
				toReturn = execHigherFile(arg1, arg2);
			}  else if (next.equals("lowerFile")) {
				String arg1 = (String) ast.get(1);
				String arg2 = (String) ast.get(2);
				//arg2 = arg2.substring(0, arg2.length() -1);
				toReturn = execLowerFile(arg1, arg2);
			} else if (next.equals("file")) {
				String arg1 = (String) ast.get(1);
				String arg2 = (String) ast.get(2);
				if (arg2.equals("=") || arg2.equals("<") || arg2.equals(">") || arg2.equals(">=") || arg2.equals("<=")) {
					toReturn = execCompareFile(arg1, arg2, (String) ast.get(3));
				} else {
					toReturn = execFile(arg1, arg2);
				}
			} else if (next.equals("betweenRank")) {
				String arg1 = (String) ast.get(1);
				String arg2 = (String) ast.get(2);
				String arg3 = (String) ast.get(3);
				ArrayList<Object> left = new ArrayList<Object>();
				left.add("lowerRank");
				left.add(arg2);
				left.add(arg1);
				boolean leftResult = evalStatement(left); 
				//System.out.println("left result: " + leftResult);
				if (leftResult == true) {
					ArrayList<Object> right = new ArrayList<Object>();
					right.add("lowerRank");
					right.add(arg1);
					right.add(arg3);
					boolean rightResult = evalStatement(right);
					if (rightResult) {
						toReturn = true;
					} else {
						toReturn = false;
					}
				} else {
					toReturn = false;
				}
			} else if (next.equals("higherRank")) {
				String arg1 = (String) ast.get(1);
				String arg2 = (String) ast.get(2);
				toReturn = execHigherRank(arg1, arg2);
			}  else if (next.equals("lowerRank")) {
				String arg1 = (String) ast.get(1);
				String arg2 = (String) ast.get(2);
				toReturn = execLowerRank(arg1, arg2);
			}	else if (next.equals("sameRank")) {
				String arg1 = (String) ast.get(1);
				String arg2 = (String) ast.get(2);
				//arg2 = arg2.substring(0, arg2.length() -1);
				toReturn = execSameRank(arg1, arg2);
			} else if (next.equals("rank")) {
				String arg1 = (String) ast.get(1);
				String arg2 = (String) ast.get(2);
				if (arg2.equals("=") || arg2.equals("<") || arg2.equals(">")  || arg2.equals(">=") || arg2.equals("<=")) {
					toReturn = execCompareRank(arg1, arg2, (String) ast.get(3));
				} else {
					toReturn = execRank(arg1, arg2);
				}
			} else if (next.equals("relRank")) {
				String arg1 = (String) ast.get(1);
				String arg2 = (String) ast.get(2);
				if (arg2.equals("=") || arg2.equals("<") || arg2.equals(">")  || arg2.equals(">=") || arg2.equals("<=")) {
					toReturn = execCompareRelRank(arg1, arg2, (String) ast.get(3));
				} else {
					toReturn = execRelRank(arg1, arg2);
				}
			} else if (next.equals("canAttack")) {
				String arg1 = (String) ast.get(1);
				String arg2 = (String) ast.get(2);
				//arg2 = arg2.substring(0, arg2.length() -1);
				toReturn = execCanAttack(arg1, arg2);
			} else if (next.equals("isDefended")) {
				String arg1 = (String) ast.get(1);
				//arg1 = arg1.substring(0, arg1.length() -1);
				toReturn = execisDefended(arg1);
			} else if (next.equals("only")) {
				ArrayList<String> args = (ArrayList<String>) ast.get(1);
				toReturn = execOnly(args);
			} else if (next.equals("exists")) {
				ArrayList<String> args = (ArrayList<String>) ast.get(1);
				toReturn = execExists(args);
			} else if (next.equals("sameColor")) {
				ArrayList<String> args = (ArrayList<String>) ast.get(1);
				toReturn = execSameColor(args);
			} else if (next.equals("different")) {
				ArrayList<String> differentSet = (ArrayList<String>) ast.get(1);
				toReturn = execDifferent(differentSet);
			} else if (next.equals("%") || next.equals("wildcard")){
				toReturn = true;
			} else if (next.equals("&&")) {
				ArrayList<Object> leftArg = (ArrayList<Object>) ast.get(1);
				boolean left = evalStatement(leftArg);
				if (left) {
					ArrayList<Object> rightArg = (ArrayList<Object>) ast.get(2);
					boolean right = evalStatement(rightArg);
					if (right) {
						toReturn = true;
					} else {
						toReturn = false;
					}
				} else {
					toReturn = false;
				}
			} else if (next.equals("and")) {
				ArrayList<Object> leftArg = (ArrayList<Object>) ast.get(1);
				boolean left = evalStatement(leftArg);
				if (left) {
					ArrayList<Object> rightArg = (ArrayList<Object>) ast.get(2);
					boolean right = evalStatement(rightArg);
					if (right) {
						toReturn = true;
					} else {
						toReturn = false;
					}
				} else {
					toReturn = false;
				}
			} else if (next.equals("||")) {
				ArrayList<Object> leftArg = (ArrayList<Object>) ast.get(1);
				// TODO need to rethink if cloning is really necessary
				HashMap<String, ArrayList<ChessPiece>> suspects = (HashMap<String, ArrayList<ChessPiece>>) mySuspects;
				Map<String, ArrayList<ChessPiece>> origMySuspects = (Map<String, ArrayList<ChessPiece>>)suspects.clone();
				boolean left = evalStatement(leftArg);
				if (!left) {
					// Need to restore original mySuspects
					mySuspects = origMySuspects;
					ArrayList<Object> rightArg = (ArrayList<Object>) ast.get(2);
					boolean right = evalStatement(rightArg);
					if (right) {
						toReturn = true;
					} else {
						toReturn = false;
					}
				} else {
					toReturn = true;
				}
			}  else if (next.equals("or")) {
				ArrayList<Object> leftArg = (ArrayList<Object>) ast.get(1);
				boolean left = evalStatement(leftArg);
				if (!left) {
					ArrayList<Object> rightArg = (ArrayList<Object>) ast.get(2);
					boolean right = evalStatement(rightArg);
					if (right) {
						toReturn = true;
					} else {
						toReturn = false;
					}
				} else {
					toReturn = true;
				}
			} else if (next.equals("not")) {
				ArrayList<Object> leftArg = (ArrayList<Object>) ast.get(1);
				boolean tobeReversed = evalStatement(leftArg);
				if (tobeReversed) {
					toReturn = false;
				} else {
					toReturn = true;
				}
			} else if (next.equals("end")) {
				toReturn = execEnd();
			} else if (next.equals("start")) {
				toReturn = execStart();
			} else if (next.equals("check")) {
				if (myGame.getCheckOn() == true) {
					toReturn = true;
				} else {
					toReturn = false;
				}
			} else if (next.equals("checkmate")) {
				if (myGame.getCheckMateOn() == true) {
					toReturn = true;
				} else {
					toReturn = false;
				}
			} else if (next.equals("occurs")) {
				ArrayList<Object> arg = (ArrayList<Object>) ast.get(1);
				toReturn = evalStatement(arg);
			} else if (next.equals("greedFlag")) {
				//System.out.println("greed set");
				myGreedFlag = true;
				toReturn = false;
			}
		} 	
		return toReturn;
	}
	
	private boolean execOccupies(String arg1, String arg2) {
		//System.out.println("occup arg1: " + arg1);
		//System.out.println("occup arg2: " + arg2);
		// call variable version
		boolean toReturn = false;
		if (arg1.length() > 1) {
			toReturn = execOccupiesVar(arg1.substring(0, 1), arg1.substring(1), arg2);
		} else {
			ArrayList<ChessPiece> candidates = myGame.getStringToCP().get(arg1);
			for (int i = 0; i < candidates.size(); i++) {
				//System.out.println("candidate: " + candidates.get(i).getmySymbol());
				//System.out.println("candidate loc: " + candidates.get(i).getLocation());
				//System.out.println("candidate pos: " + candidates.get(i).getLocation().getmyAlgebraicLocation());
				if (candidates.get(i).getLocation().getAlgebraicLocation().equals(arg2)) {
						toReturn = true;
						break;
				}
				/*  deprecated version that supported p2 and such, can add back later
				if (candidates.get(i).getLocation().getmyAlgebraicLocation().equals(arg2)) {
					if (arg1Var.equals("")) {
						toReturn = true;
						break;
					} else if (!(arg1Var.equals("")) && Integer.parseInt(arg1Var) == candidates.get(i).getID()) { 
						toReturn = true;
						break;
					}
				}
				*/
			}
		}
		return toReturn;
	}
	
	private boolean execOccupiesVar(String arg1, String arg2, String arg3) {
		//System.out.println("arg1: " + arg1);
		//System.out.println("arg2: " + arg2);
		ArrayList<ChessPiece> candidates = mySuspects.get(arg2);
		ArrayList<ChessPiece> newCandidates = new ArrayList<ChessPiece>();
		if (candidates == null){
			//System.out.println("candidates occupies first");
			candidates = myGame.getStringToCP().get(arg1);
		}
		boolean toReturn = false;
		for (int i = 0; i < candidates.size(); i++) {
			//System.out.println("candidate: " + candidates.get(i).getmySymbol());
			//System.out.println("candidate loc: " + candidates.get(i).getLocation());
			if (candidates.get(i).getLocation().getAlgebraicLocation().equals(arg3)) {
					toReturn = true;
					//System.out.println("newCandidates: " + candidates.get(i).getLocation());
					newCandidates.add(candidates.get(i));
			}
		}
		mySuspects.remove(arg1 + arg2);
		mySuspects.put(arg1 + arg2, newCandidates);
		//System.out.println("occupy var suspects: " + mySuspects.get(arg1 + arg2));
		return toReturn;
	}
	
	
	// rewrite using canReach()
	private boolean execCanAttack(String arg1, String arg2) {
		//System.out.println("canAttack arg1: " + arg1);
		//System.out.println("canAttack arg2: " + arg2);
		boolean toReturn = false;
		if (arg1.length() > 1 || arg2.length() > 1) {
			toReturn = execCanAttackVar(arg1, arg2);
		} else {
			ArrayList<ChessPiece> candidates = myGame.getStringToCP().get(arg1);
			ArrayList<ChessPiece> candidates2 = myGame.getStringToCP().get(arg2);
			for (int i = 0; i < candidates.size(); i++) {
				for (int j = 0; j < candidates2.size(); j++) {
					//System.out.println("canAttack candidate: " + candidates.get(i));
					// Get all of i's possible locations
					// see if any match j's location
					List<Location> possibles = candidates.get(i).getPossibleMoves();
					String enemySpot = candidates2.get(j).getLocation().getAlgebraicLocation();
					for (int k = 0; k < possibles.size(); k++) {
						if (enemySpot.equals(possibles.get(k).getAlgebraicLocation())) {
							toReturn = true;
							break;
						}
					}
					if (toReturn == true) {
						break;
					}
				}
				if (toReturn == true) {
					break;
				}
			}
		}
		return toReturn;
	}
	
	private boolean execCanAttackVar(String arg1, String arg2) {
		//System.out.println("arg1: " + arg1);
		//System.out.println("arg2: " + arg2);
		boolean toReturn = false;
		
		// determine if arg1 is a var and if so what the suspects should be
		ArrayList<ChessPiece> candidates = myGame.getStringToCP().get(arg1);
		ArrayList<ChessPiece> maybeCandidates = null;
		String var = null;
		if (arg1.length() > 1) {
			var = arg1;
			//System.out.println("var: " + var);
			maybeCandidates = mySuspects.get(var);
			//System.out.println("mySuspects: " + mySuspects);
			if (maybeCandidates != null){
				// then there were prior results!
				//System.out.println("candidates1: " + candidates);
				candidates = maybeCandidates;
			} else {
				// a var statement but first time
				//System.out.println("candidates2: " + candidates);
				candidates = myGame.getStringToCP().get(arg1.substring(0, 1));
			}
		}
		//System.out.println("candidates: " + candidates);
		
		// same for arg2
		ArrayList<ChessPiece> candidates2 = myGame.getStringToCP().get(arg2);
		ArrayList<ChessPiece> maybeCandidates2 = null;
		String var2 = null;
		if (arg2.length() > 1) {
			var2 = arg2.substring(1);
			maybeCandidates2 = mySuspects.get(var2);
			if (maybeCandidates2 != null){
				candidates2 = maybeCandidates2;
			} else {
				candidates2 = myGame.getStringToCP().get(arg2.substring(0, 1));
			}
		}
		
		
		ArrayList<ChessPiece> newCandidates = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> newCandidates2 = new ArrayList<ChessPiece>();
		for (int i = 0; i < candidates.size(); i++) {
			for (int j = 0; j < candidates2.size(); j++) {
				// Get all of i's possible locations
				// see if any match j's location
				List<Location> possibles = candidates.get(i).getPossibleMoves();
				String enemySpot = candidates2.get(j).getLocation().getAlgebraicLocation();
				for (int k = 0; k < possibles.size(); k++) {
					if (enemySpot.equals(possibles.get(k).getAlgebraicLocation())) {
						toReturn = true;
						newCandidates.add(candidates.get(i));
						newCandidates2.add(candidates.get(j));
					}
				}
			}
		}
		if (var != null) {
			mySuspects.remove(arg1);
			mySuspects.put(arg1, newCandidates);
		}
		if (var2 != null) {
			mySuspects.remove(arg2);
			mySuspects.put(arg2, newCandidates);
		}
		return toReturn;
	}
	
	private boolean execisDefended(String arg1) {
		//System.out.println("arg1: " + arg1);
		boolean toReturn = false;
		if (arg1.length() > 1) {
			// call variable version
			toReturn = execisDefendedVar(arg1.substring(0, 1), arg1.substring(1));
		} else {
			ArrayList<ChessPiece> candidates = myGame.getStringToCP().get(arg1);
			for (int i = 0; i < candidates.size(); i++) {
				//System.out.println("candidate: " + candidates.get(i).getmySymbol());
				String side = candidates.get(i).getAllegiance().getAllegiance();
				if (side.equals("b")) {
					for (int j = 0; j < myGame.getBlackPieces().size(); j++) {
						Location mySpot = candidates.get(i).getLocation();
						//System.out.println("first: " + myGame.getBlackPieces().get(j).getmySymbol());
						if (myGame.getBlackPieces().get(j).canDefend(mySpot)) {
							toReturn = true;
							break;
						}
					} 
				} else {
					for (int j = 0; j < myGame.getWhitePieces().size(); j++) {
						Location mySpot = candidates.get(i).getLocation();
						//System.out.println("first: " + myGame.getWhitePieces().get(j).getmySymbol());
						if (myGame.getWhitePieces().get(j).canDefend(mySpot)) {
							toReturn = true;
							break;
						}
					}	
				}
				if (toReturn == true) {
					break;
				}
			}
		}
		return toReturn;
	}
	
	private boolean execisDefendedVar(String arg1, String arg2) {
		//System.out.println("arg1: " + arg1);
		boolean toReturn = false;
		ArrayList<ChessPiece> candidates = mySuspects.get(arg1);
		ArrayList<ChessPiece> newCandidates = new ArrayList<ChessPiece>();
		if (candidates == null){
			candidates = myGame.getStringToCP().get(arg1);
		}
		for (int i = 0; i < candidates.size(); i++) {
			//System.out.println("candidate: " + candidates.get(i).getmySymbol());
			String side = candidates.get(i).getAllegiance().getAllegiance();
			if (side.equals("b")) {
				for (int j = 0; j < myGame.getBlackPieces().size(); j++) {
					Location mySpot = candidates.get(i).getLocation();
					//System.out.println("first: " + myGame.getBlackPieces().get(j).getmySymbol());
					if (myGame.getBlackPieces().get(j).canDefend(mySpot)) {
						toReturn = true;
						newCandidates.add(candidates.get(i));
					}
				} 
			} else {
				for (int j = 0; j < myGame.getWhitePieces().size(); j++) {
					Location mySpot = candidates.get(i).getLocation();
					//System.out.println("first: " + myGame.getWhitePieces().get(j).getmySymbol());
					if (myGame.getWhitePieces().get(j).canDefend(mySpot)) {
						toReturn = true;
						newCandidates.add(candidates.get(i));
					}
				}	
			}
		}
		mySuspects.remove(arg1 + arg2);
		mySuspects.put(arg1 + arg2, newCandidates);
		return toReturn;
	}
	
	private boolean execSameFile(String arg1, String arg2) {
		//System.out.println("arg1: " + arg1);
		//System.out.println("arg2: " + arg2);
		boolean toReturn = false;
		if (arg1.length() > 1 || arg2.length() > 1) {
			toReturn = execSameFileVar(arg1, arg2);
		} else {
			ArrayList<ChessPiece> candidates = myGame.getStringToCP().get(arg1);
			ArrayList<ChessPiece> candidates2 = myGame.getStringToCP().get(arg2);
			for (int i = 0; i < candidates.size(); i++) {
				for (int j = 0; j < candidates2.size(); j++) {
					String file1 = candidates.get(i).getLocation().getFile();
					//System.out.println("file1: " + file1);
					String file2 = candidates2.get(j).getLocation().getFile();
					//System.out.println("file2: " + file2);
					if (file1.equals(file2)) {
						toReturn = true;
						break;
					}
				}
				if (toReturn == true) {
					break;
				}
				
			}
		}
		return toReturn;	
	}
	
	private boolean execSameFileVar(String arg1, String arg2) {
		//System.out.println("arg1: " + arg1);
		//System.out.println("arg2: " + arg2);
		boolean toReturn = false;
		// determine if arg1 is a var and if so what the suspects should be
		ArrayList<ChessPiece> candidates = myGame.getStringToCP().get(arg1);
		ArrayList<ChessPiece> maybeCandidates = null;
		String var = null;
		if (arg1.length() > 1) {
			var = arg1;
			//System.out.println("var: " + var);
			maybeCandidates = mySuspects.get(var);
			//System.out.println("mySuspects: " + mySuspects);
			if (maybeCandidates != null){
				// then there were prior results!
				//System.out.println("candidates1: " + candidates);
				candidates = maybeCandidates;
			} else {
				// a var statement but first time
				//System.out.println("candidates2: " + candidates);
				candidates = myGame.getStringToCP().get(arg1.substring(0, 1));
			}
		}
		//System.out.println("candidates: " + candidates);
		
		// same for arg2
		ArrayList<ChessPiece> candidates2 = myGame.getStringToCP().get(arg2);
		ArrayList<ChessPiece> maybeCandidates2 = null;
		String var2 = null;
		if (arg2.length() > 1) {
			var2 = arg2.substring(1);
			maybeCandidates2 = mySuspects.get(var2);
			if (maybeCandidates2 != null){
				candidates2 = maybeCandidates2;
			} else {
				candidates2 = myGame.getStringToCP().get(arg2.substring(0, 1));
			}
		}
		ArrayList<ChessPiece> newCandidates = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> newCandidates2 = new ArrayList<ChessPiece>();
		for (int i = 0; i < candidates.size(); i++) {
			for (int j = 0; j < candidates2.size(); j++) {
				String file1 = candidates.get(i).getLocation().getFile();
				//System.out.println("file1: " + file1);
				String file2 = candidates2.get(j).getLocation().getFile();
				//System.out.println("file2: " + file1);
				if (file1.equals(file2)) {
					toReturn = true;
					newCandidates.add(candidates.get(i));
					newCandidates2.add(candidates2.get(j));
				}
			}
		}
		if (var != null) {
			mySuspects.remove(arg1);
			mySuspects.put(arg1, newCandidates);
		}
		if (var2 != null) {
			mySuspects.remove(arg2);
			mySuspects.put(arg2, newCandidates);
		}
		return toReturn;	
	}
	
	private boolean execHigherFile(String arg1, String arg2) {
		//System.out.println("arg1: " + arg1);
		//System.out.println("arg2: " + arg2);
		boolean toReturn = false;
		if (arg1.length() > 1 || arg2.length() > 1) {
			toReturn = execHigherFileVar(arg1, arg2);
		} else {
			ArrayList<ChessPiece> candidates = myGame.getStringToCP().get(arg1);
			ArrayList<ChessPiece> candidates2 = myGame.getStringToCP().get(arg2);
			for (int i = 0; i < candidates.size(); i++) {
				for (int j = 0; j < candidates2.size(); j++) {
					String file1 = candidates.get(i).getLocation().getFile();
					//System.out.println("file1: " + file1);
					String file2 = candidates2.get(j).getLocation().getFile();
					//System.out.println("file2: " + file1);
					if (Util.fileToNum(file1) > Util.fileToNum(file2)) {
						toReturn = true;
						break;
					}
				}
				if (toReturn == true) {
					break;
				}
				
			}
		}
		return toReturn;	
	}
	
	private boolean execHigherFileVar(String arg1, String arg2) {
		boolean toReturn = false;
		// determine if arg1 is a var and if so what the suspects should be
		ArrayList<ChessPiece> candidates = myGame.getStringToCP().get(arg1);
		ArrayList<ChessPiece> maybeCandidates = null;
		String var = null;
		if (arg1.length() > 1) {
			var = arg1;
			//System.out.println("var: " + var);
			maybeCandidates = mySuspects.get(var);
			//System.out.println("mySuspects: " + mySuspects);
			if (maybeCandidates != null){
				// then there were prior results!
				//System.out.println("candidates1: " + candidates);
				candidates = maybeCandidates;
			} else {
				// a var statement but first time
				//System.out.println("candidates2: " + candidates);
				candidates = myGame.getStringToCP().get(arg1.substring(0, 1));
			}
		}
		//System.out.println("candidates: " + candidates);
		// same for arg2
		ArrayList<ChessPiece> candidates2 = myGame.getStringToCP().get(arg2);
		ArrayList<ChessPiece> maybeCandidates2 = null;
		String var2 = null;
		if (arg2.length() > 1) {
			var2 = arg2.substring(1);
			maybeCandidates2 = mySuspects.get(var2);
			if (maybeCandidates2 != null){
				candidates2 = maybeCandidates2;
			} else {
				candidates2 = myGame.getStringToCP().get(arg2.substring(0, 1));
			}
		}
		ArrayList<ChessPiece> newCandidates = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> newCandidates2 = new ArrayList<ChessPiece>();
		for (int i = 0; i < candidates.size(); i++) {
			for (int j = 0; j < candidates2.size(); j++) {
				String file1 = candidates.get(i).getLocation().getFile();
				//System.out.println("file1: " + file1);
				String file2 = candidates2.get(j).getLocation().getFile();
				//System.out.println("file2: " + file1);
				if (Util.fileToNum(file1) > Util.fileToNum(file2)) {
					toReturn = true;
					newCandidates.add(candidates.get(i));
					newCandidates2.add(candidates2.get(j));
				}
			}
		}
		if (var != null) {
			mySuspects.remove(arg1);
			mySuspects.put(arg1, newCandidates);
		}
		if (var2 != null) {
			mySuspects.remove(arg2);
			mySuspects.put(arg2, newCandidates);
		}
		return toReturn;
	}
	
	private boolean execLowerFile(String arg1, String arg2) {
		//System.out.println("lower file");
		boolean toReturn = false;
		if (arg1.length() > 1 || arg2.length() > 1) {
			toReturn = execLowerFileVar(arg1, arg2);
		} else {
			ArrayList<ChessPiece> candidates = myGame.getStringToCP().get(arg1);
			ArrayList<ChessPiece> candidates2 = myGame.getStringToCP().get(arg2);
			for (int i = 0; i < candidates.size(); i++) {
				for (int j = 0; j < candidates2.size(); j++) {
					String file1 = candidates.get(i).getLocation().getFile();
					//System.out.println("file1: " + file1);
					//System.out.println("file1: " + Util.fileToNum(file1));
					String file2 = candidates2.get(j).getLocation().getFile();
					//System.out.println("file2: " + file2);
					//System.out.println("file2: " + Util.fileToNum(file2));
					if (Util.fileToNum(file1) < Util.fileToNum(file2)) {
						toReturn = true;
						break;
					}
				}
				if (toReturn == true) {
					break;
				}
				
			}
		}
		return toReturn;
	}
	
	private boolean execLowerFileVar(String arg1, String arg2) {
		boolean toReturn = false;
		// determine if arg1 is a var and if so what the suspects should be
		ArrayList<ChessPiece> candidates = myGame.getStringToCP().get(arg1);
		ArrayList<ChessPiece> maybeCandidates = null;
		String var = null;
		if (arg1.length() > 1) {
			var = arg1;
			//System.out.println("var: " + var);
			maybeCandidates = mySuspects.get(var);
			//System.out.println("mySuspects: " + mySuspects);
			if (maybeCandidates != null){
				// then there were prior results!
				//System.out.println("candidates1: " + candidates);
				candidates = maybeCandidates;
			} else {
				// a var statement but first time
				//System.out.println("candidates2: " + candidates);
				candidates = myGame.getStringToCP().get(arg1.substring(0, 1));
			}
		}
		//System.out.println("candidates: " + candidates);
		// same for arg2
		ArrayList<ChessPiece> candidates2 = myGame.getStringToCP().get(arg2);
		ArrayList<ChessPiece> maybeCandidates2 = null;
		String var2 = null;
		if (arg2.length() > 1) {
			var2 = arg2.substring(1);
			maybeCandidates2 = mySuspects.get(var2);
			if (maybeCandidates2 != null){
				candidates2 = maybeCandidates2;
			} else {
				candidates2 = myGame.getStringToCP().get(arg2.substring(0, 1));
			}
		}
		ArrayList<ChessPiece> newCandidates = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> newCandidates2 = new ArrayList<ChessPiece>();
		for (int i = 0; i < candidates.size(); i++) {
			for (int j = 0; j < candidates2.size(); j++) {
				String file1 = candidates.get(i).getLocation().getFile();
				//System.out.println("file1: " + file1);
				String file2 = candidates2.get(j).getLocation().getFile();
				//System.out.println("file2: " + file1);
				if (Util.fileToNum(file1) < Util.fileToNum(file2)) {
					toReturn = true;
					newCandidates.add(candidates.get(i));
					newCandidates2.add(candidates2.get(j));
				}
			}
		}
		if (var != null) {
			mySuspects.remove(arg1);
			mySuspects.put(arg1, newCandidates);
		}
		if (var2 != null) {
			mySuspects.remove(arg2);
			mySuspects.put(arg2, newCandidates);
		}
		return toReturn;
	}
	
	private boolean execFile(String arg1, String arg2) {
		//System.out.println("arg1: " + arg1);
		//System.out.println("arg2: " + arg2);
		boolean toReturn = false;
		if (arg1.length() > 1) {
			toReturn = execFileVar(arg1.substring(0, 1), arg1.substring(1), arg2);
		} else {
			ArrayList<ChessPiece> candidates = myGame.getStringToCP().get(arg1);
			for (int i = 0; i < candidates.size(); i++) {
				//System.out.println("candidate: " + candidates.get(i).getmySymbol());
				if (candidates.get(i).getLocation().getFile().equals(arg2)) {
					toReturn = true;
					break;
				}
		}
		}
		return toReturn;
	}
	
	private boolean execFileVar(String arg1, String arg2, String arg3) {
		//System.out.println("arg1: " + arg1);
		//System.out.println("arg2: " + arg2);
		ArrayList<ChessPiece> candidates = mySuspects.get(arg2);
		ArrayList<ChessPiece> newCandidates = new ArrayList<ChessPiece>();
		if (candidates == null){
			candidates = myGame.getStringToCP().get(arg1);
		}
		boolean toReturn = false;
		for (int i = 0; i < candidates.size(); i++) {
			//System.out.println("candidate: " + candidates.get(i).getmySymbol());
			if (candidates.get(i).getLocation().getFile().equals(arg3)) {
				toReturn = true;
				newCandidates.add(candidates.get(i));
			}
		}
		mySuspects.remove(arg1 + arg2);
		mySuspects.put(arg1 + arg2, newCandidates);
		return toReturn;
		
	}
	
	private boolean execCompareFile(String arg1, String arg2, String arg3) {
		boolean toReturn = false;
		//System.out.println("execRank arg1: " + arg1);
		//System.out.println("execRank arg2: " + arg2);
		if (arg1.length() > 1) {
			toReturn = execCompareFileVar(arg1.substring(0, 1), arg3, arg1.substring(1), arg2);
		} else {
			ArrayList<ChessPiece> candidates = myGame.getStringToCP().get(arg1);
			if (arg2.equals("=")) {
				for (int i = 0; i < candidates.size(); i++) {
					//System.out.println("candidate: " + candidates.get(i).getmySymbol());
					//System.out.println("candidate pos: " + candidates.get(i).getLocation().getmyAlgebraicLocation());
					if (candidates.get(i).getLocation().getFile().equals(arg3)) {
						toReturn = true;
						break;
					}
				}
			} else if (arg2.equals("<")) {
				for (int i = 0; i < candidates.size(); i++) {
					//System.out.println("candidate: " + candidates.get(i).getmySymbol());
					//System.out.println("candidate pos: " + candidates.get(i).getLocation().getmyAlgebraicLocation());
					if (Util.fileToNum(candidates.get(i).getLocation().getFile()) < Util.fileToNum(arg3)) {
						toReturn = true;
						break;
					}
				}
			} else if (arg2.equals(">")) {
				for (int i = 0; i < candidates.size(); i++) {
					//System.out.println("candidate: " + candidates.get(i).getmySymbol());
					//System.out.println("candidate pos: " + candidates.get(i).getLocation().getmyAlgebraicLocation());
					if (Util.fileToNum(candidates.get(i).getLocation().getFile()) > Util.fileToNum(arg3)) {
						toReturn = true;
						break;
					}
				}
			}  else if (arg2.equals("<=")) {
				for (int i = 0; i < candidates.size(); i++) {
					//System.out.println("candidate: " + candidates.get(i).getmySymbol());
					//System.out.println("candidate pos: " + candidates.get(i).getLocation().getmyAlgebraicLocation());
					if (Util.fileToNum(candidates.get(i).getLocation().getFile()) <= Util.fileToNum(arg3)) {
						toReturn = true;
						break;
					}
				}
			} 
			else if (arg2.equals(">=")) {
				for (int i = 0; i < candidates.size(); i++) {
					//System.out.println("candidate: " + candidates.get(i).getmySymbol());
					//System.out.println("candidate pos: " + candidates.get(i).getLocation().getmyAlgebraicLocation());
					if (Util.fileToNum(candidates.get(i).getLocation().getFile()) >= Util.fileToNum(arg3)) {
						toReturn = true;
						break;
					}
				}
			} 
		}
		return toReturn;
	}
	
	private boolean execCompareFileVar(String arg1, String arg2, String arg3, String comp) {
		//System.out.println("execRankVar arg1: " + arg1);
		//System.out.println("execRankVar arg2: " + arg2);
		//System.out.println("execRankVar arg3: " + arg3);
		boolean toReturn = false;
		ArrayList<ChessPiece> candidates = mySuspects.get(arg3);
		ArrayList<ChessPiece> newCandidates = new ArrayList<ChessPiece>();
		if (candidates == null){
			candidates = myGame.getStringToCP().get(arg1);
		}
		if (comp.equals("=")) {
			for (int i = 0; i < candidates.size(); i++) {
				//System.out.println("candidate: " + candidates.get(i).getmySymbol());
				//System.out.println("candidate pos: " + candidates.get(i).getLocation().getmyAlgebraicLocation());
				if (candidates.get(i).getLocation().getRank().equals(arg2)) {
					toReturn = true;
					newCandidates.add(candidates.get(i));
				}
			}
		} else if (comp.equals("<")) {
			for (int i = 0; i < candidates.size(); i++) {
				//System.out.println("candidate: " + candidates.get(i).getmySymbol());
				//System.out.println("candidate pos: " + candidates.get(i).getLocation().getmyAlgebraicLocation());
				if (Util.fileToNum(candidates.get(i).getLocation().getRank()) < Util.fileToNum(arg2)) {
					toReturn = true;
					newCandidates.add(candidates.get(i));
				}
			}
		} else if (comp.equals(">")) {
			for (int i = 0; i < candidates.size(); i++) {
				//System.out.println("candidate: " + candidates.get(i).getmySymbol());
				//System.out.println("candidate pos: " + candidates.get(i).getLocation().getmyAlgebraicLocation());
				if (Util.fileToNum(candidates.get(i).getLocation().getRank()) > Util.fileToNum(arg2)) {
					toReturn = true;
					newCandidates.add(candidates.get(i));
				}
			}
		} else if (comp.equals(">=")) {
			for (int i = 0; i < candidates.size(); i++) {
				//System.out.println("candidate: " + candidates.get(i).getmySymbol());
				//System.out.println("candidate pos: " + candidates.get(i).getLocation().getmyAlgebraicLocation());
				if (Util.fileToNum(candidates.get(i).getLocation().getRank()) >= Util.fileToNum(arg2)) {
					toReturn = true;
					newCandidates.add(candidates.get(i));
				}
			}
		} else if (comp.equals("<=")) {
			for (int i = 0; i < candidates.size(); i++) {
				//System.out.println("candidate: " + candidates.get(i).getmySymbol());
				//System.out.println("candidate pos: " + candidates.get(i).getLocation().getmyAlgebraicLocation());
				if (Util.fileToNum(candidates.get(i).getLocation().getRank()) <= Util.fileToNum(arg2)) {
					toReturn = true;
					newCandidates.add(candidates.get(i));
				}
			}
		}
		mySuspects.remove(arg1 + arg3);
		mySuspects.put(arg1 + arg3, newCandidates);
		return toReturn;
	}
	
	private boolean execHigherRank(String arg1, String arg2) {
		//System.out.println("arg1: " + arg1);
		//System.out.println("arg2: " + arg2);
		boolean toReturn = false;
		if (arg1.length() > 1 || arg2.length() > 1) {
			toReturn = execHigherRankVar(arg1, arg2);
		} else {
			ArrayList<ChessPiece> candidates = myGame.getStringToCP().get(arg1);
			ArrayList<ChessPiece> candidates2 = myGame.getStringToCP().get(arg2);
			for (int i = 0; i < candidates.size(); i++) {
				for (int j = 0; j < candidates2.size(); j++) {
					String rank1 = candidates.get(i).getLocation().getRank();
					//System.out.println("file1: " + rank1);
					String rank2 = candidates2.get(j).getLocation().getRank();
					//System.out.println("file2: " + rank2);
					if (Integer.parseInt(rank1) > Integer.parseInt(rank2)) {
						toReturn = true;
						break;
					}
				}
				if (toReturn == true) {
					break;
				}
				
			}
		}
		return toReturn;	
	}
	
	private boolean execHigherRankVar(String arg1, String arg2) {
		boolean toReturn = false;
		// determine if arg1 is a var and if so what the suspects should be
		ArrayList<ChessPiece> candidates = myGame.getStringToCP().get(arg1);
		ArrayList<ChessPiece> maybeCandidates = null;
		String var = null;
		if (arg1.length() > 1) {
			var = arg1;
			//System.out.println("var: " + var);
			maybeCandidates = mySuspects.get(var);
			//System.out.println("mySuspects: " + mySuspects);
			if (maybeCandidates != null){
				// then there were prior results!
				//System.out.println("candidates1: " + candidates);
				candidates = maybeCandidates;
			} else {
				// a var statement but first time
				//System.out.println("candidates2: " + candidates);
				candidates = myGame.getStringToCP().get(arg1.substring(0, 1));
			}
		}
		//System.out.println("candidates: " + candidates);
		// same for arg2
		ArrayList<ChessPiece> candidates2 = myGame.getStringToCP().get(arg2);
		ArrayList<ChessPiece> maybeCandidates2 = null;
		String var2 = null;
		if (arg2.length() > 1) {
			var2 = arg2.substring(1);
			maybeCandidates2 = mySuspects.get(var2);
			if (maybeCandidates2 != null){
				candidates2 = maybeCandidates2;
			} else {
				candidates2 = myGame.getStringToCP().get(arg2.substring(0, 1));
			}
		}
		ArrayList<ChessPiece> newCandidates = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> newCandidates2 = new ArrayList<ChessPiece>();
		for (int i = 0; i < candidates.size(); i++) {
			for (int j = 0; j < candidates2.size(); j++) {
				String rank1 = candidates.get(i).getLocation().getRank();
				//System.out.println("rank1: " + rank1);
				String rank2 = candidates2.get(j).getLocation().getRank();
				//System.out.println("rank2: " + rank2);
				if (Integer.parseInt(rank1) > Integer.parseInt(rank2)) {
					toReturn = true;
					newCandidates.add(candidates.get(i));
					newCandidates2.add(candidates2.get(j));
				}
			}
		}
		if (var != null) {
			mySuspects.remove(arg1);
			mySuspects.put(arg1, newCandidates);
		}
		if (var2 != null) {
			mySuspects.remove(arg2);
			mySuspects.put(arg2, newCandidates);
		}
		return toReturn;
	}
	
	private boolean execLowerRank(String arg1, String arg2) {
		//System.out.println("arg1: " + arg1);
		//System.out.println("arg2: " + arg2);
		boolean toReturn = false;
		if (arg1.length() > 1 || arg2.length() > 1) {
			toReturn = execLowerRankVar(arg1, arg2);
		} else {
			ArrayList<ChessPiece> candidates = myGame.getStringToCP().get(arg1);
			ArrayList<ChessPiece> candidates2 = myGame.getStringToCP().get(arg2);
			for (int i = 0; i < candidates.size(); i++) {
				for (int j = 0; j < candidates2.size(); j++) {
					String rank1 = candidates.get(i).getLocation().getRank();
					//System.out.println("file1: " + rank1);
					String rank2 = candidates2.get(j).getLocation().getRank();
					//System.out.println("file2: " + rank2);
					if (Integer.parseInt(rank1) < Integer.parseInt(rank2)) {
						toReturn = true;
						break;
					}
				}
				if (toReturn == true) {
					break;
				}
				
			}
		}
		return toReturn;	
	}
	
	private boolean execLowerRankVar(String arg1, String arg2) {
		boolean toReturn = false;
		// determine if arg1 is a var and if so what the suspects should be
		ArrayList<ChessPiece> candidates = myGame.getStringToCP().get(arg1);
		ArrayList<ChessPiece> maybeCandidates = null;
		String var = null;
		if (arg1.length() > 1) {
			var = arg1;
			//System.out.println("var: " + var);
			maybeCandidates = mySuspects.get(var);
			//System.out.println("mySuspects: " + mySuspects);
			if (maybeCandidates != null){
				// then there were prior results!
				//System.out.println("candidates1: " + candidates);
				candidates = maybeCandidates;
			} else {
				// a var statement but first time
				//System.out.println("candidates2: " + candidates);
				candidates = myGame.getStringToCP().get(arg1.substring(0, 1));
			}
		}
		//System.out.println("candidates: " + candidates);
		// same for arg2
		ArrayList<ChessPiece> candidates2 = myGame.getStringToCP().get(arg2);
		ArrayList<ChessPiece> maybeCandidates2 = null;
		String var2 = null;
		if (arg2.length() > 1) {
			var2 = arg2.substring(1);
			maybeCandidates2 = mySuspects.get(var2);
			if (maybeCandidates2 != null){
				candidates2 = maybeCandidates2;
			} else {
				candidates2 = myGame.getStringToCP().get(arg2.substring(0, 1));
			}
		}
		ArrayList<ChessPiece> newCandidates = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> newCandidates2 = new ArrayList<ChessPiece>();
		for (int i = 0; i < candidates.size(); i++) {
			for (int j = 0; j < candidates2.size(); j++) {
				String rank1 = candidates.get(i).getLocation().getRank();
				//System.out.println("rank1: " + rank1);
				String rank2 = candidates2.get(j).getLocation().getRank();
				//System.out.println("rank2: " + rank2);
				if (Integer.parseInt(rank1) < Integer.parseInt(rank2)) {
					toReturn = true;
					newCandidates.add(candidates.get(i));
					newCandidates2.add(candidates2.get(j));
				}
			}
		}
		if (var != null) {
			mySuspects.remove(arg1);
			mySuspects.put(arg1, newCandidates);
		}
		if (var2 != null) {
			mySuspects.remove(arg2);
			mySuspects.put(arg2, newCandidates);
		}
		return toReturn;
	}
	
	private boolean execSameRank(String arg1, String arg2) {
		//System.out.println("arg1: " + arg1);
		//System.out.println("arg2: " + arg2);
		boolean toReturn = false;
		if (arg1.length() > 1 || arg2.length() > 1) {
			toReturn = execSameRankVar(arg1, arg2);
		} else {
			ArrayList<ChessPiece> candidates = myGame.getStringToCP().get(arg1);
			ArrayList<ChessPiece> candidates2 = myGame.getStringToCP().get(arg2);
			
			for (int i = 0; i < candidates.size(); i++) {
				for (int j = 0; j < candidates2.size(); j++) {
					String rank1 = candidates.get(i).getLocation().getRank();
					//System.out.println("rank1: " + rank1);
					String rank2 = candidates2.get(j).getLocation().getRank();
					//System.out.println("rank2: " + rank2);
					if (rank1.equals(rank2)) {
						toReturn = true;
						break;
					}
				}
				if (toReturn == true) {
					break;
				}
				
			}
		}
		return toReturn;	
	}
	
	private boolean execSameRankVar(String arg1, String arg2) {
		//System.out.println("arg1: " + arg1);
		//System.out.println("arg2: " + arg2);
		boolean toReturn = false;
		ArrayList<ChessPiece> candidates = myGame.getStringToCP().get(arg1);
		ArrayList<ChessPiece> maybeCandidates = null;
		String var = null;
		if (arg1.length() > 1) {
			var = arg1;
			//System.out.println("var: " + var);
			maybeCandidates = mySuspects.get(var);
			//System.out.println("mySuspects: " + mySuspects);
			if (maybeCandidates != null){
				// then there were prior results!
				//System.out.println("candidates1: " + candidates);
				candidates = maybeCandidates;
			} else {
				// a var statement but first time
				//System.out.println("candidates2: " + candidates);
				candidates = myGame.getStringToCP().get(arg1.substring(0, 1));
			}
		}
		//System.out.println("candidates: " + candidates);
		
		// same for arg2
		ArrayList<ChessPiece> candidates2 = myGame.getStringToCP().get(arg2);
		ArrayList<ChessPiece> maybeCandidates2 = null;
		String var2 = null;
		if (arg2.length() > 1) {
			var2 = arg2.substring(1);
			maybeCandidates2 = mySuspects.get(var2);
			if (maybeCandidates2 != null){
				candidates2 = maybeCandidates2;
			} else {
				candidates2 = myGame.getStringToCP().get(arg2.substring(0, 1));
			}
		}
		ArrayList<ChessPiece> newCandidates = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> newCandidates2 = new ArrayList<ChessPiece>();		
		for (int i = 0; i < candidates.size(); i++) {
			for (int j = 0; j < candidates2.size(); j++) {
				String rank1 = candidates.get(i).getLocation().getRank();
				//System.out.println("file1: " + rank1);
				String rank2 = candidates2.get(j).getLocation().getRank();
				//System.out.println("file2: " + rank2);
				if (rank1.equals(rank2)) {
					toReturn = true;
					newCandidates.add(candidates.get(i));
					newCandidates2.add(candidates2.get(j));
				}
			}
			
		}
		if (var != null) {
			mySuspects.remove(arg1);
			mySuspects.put(arg1, newCandidates);
		}
		if (var2 != null) {
			mySuspects.remove(arg2);
			mySuspects.put(arg2, newCandidates);
		}
		return toReturn;	
	}
	
	private boolean execRank(String arg1, String arg2) {
		//System.out.println("execRank arg1: " + arg1);
		//System.out.println("execRank arg2: " + arg2);
		boolean toReturn = false;
		if (arg1.length() > 1) {
			toReturn = execRankVar(arg1.substring(0, 1), arg2, arg1.substring(1));
		} else {
			ArrayList<ChessPiece> candidates = myGame.getStringToCP().get(arg1);
			for (int i = 0; i < candidates.size(); i++) {
				//System.out.println("candidate: " + candidates.get(i).getmySymbol());
				//System.out.println("candidate pos: " + candidates.get(i).getLocation().getmyAlgebraicLocation());
				if (candidates.get(i).getLocation().getRank().equals(arg2)) {
					toReturn = true;
					break;
				}
			}
		}
		return toReturn;
	}
	
	private boolean execRankVar(String arg1, String arg2, String arg3) {
		//System.out.println("execRankVar arg1: " + arg1);
		//System.out.println("execRankVar arg2: " + arg2);
		//System.out.println("execRankVar arg3: " + arg3);
		boolean toReturn = false;
		ArrayList<ChessPiece> candidates = mySuspects.get(arg3);
		ArrayList<ChessPiece> newCandidates = new ArrayList<ChessPiece>();
		if (candidates == null){
			candidates = myGame.getStringToCP().get(arg1);
		}		
		for (int i = 0; i < candidates.size(); i++) {
			//System.out.println("candidate: " + candidates.get(i).getmySymbol());
			//System.out.println("candidate pos: " + candidates.get(i).getLocation().getmyAlgebraicLocation());
			if (candidates.get(i).getLocation().getRank().equals(arg2)) {
				toReturn = true;
				newCandidates.add(candidates.get(i));
			}
		}
		mySuspects.remove(arg1 + arg3);
		mySuspects.put(arg1 + arg3, newCandidates);
		return toReturn;
	}
	
	private boolean execCompareRank(String arg1, String arg2, String arg3) {
		boolean toReturn = false;
		//System.out.println("execCRank arg1: " + arg1);
		//System.out.println("execCRank arg2: " + arg2);
		//System.out.println("execCRank arg3: " + arg3);
		if (arg1.length() > 1) {
			toReturn = execCompareRankVar(arg1.substring(0, 1), arg3, arg1.substring(1), arg2);
		} else {
			ArrayList<ChessPiece> candidates = myGame.getStringToCP().get(arg1);
			if (arg2.equals("=")) {
				for (int i = 0; i < candidates.size(); i++) {
					//System.out.println("candidate: " + candidates.get(i).getmySymbol());
					//System.out.println("candidate pos: " + candidates.get(i).getLocation().getmyAlgebraicLocation());
					if (candidates.get(i).getLocation().getRank().equals(arg3)) {
						toReturn = true;
						break;
					}
				}
			} else if (arg2.equals("<")) {
				for (int i = 0; i < candidates.size(); i++) {
					//System.out.println("candidate: " + candidates.get(i).getmySymbol());
					//System.out.println("candidate pos: " + candidates.get(i).getLocation().getmyAlgebraicLocation());
					if (Integer.parseInt(candidates.get(i).getLocation().getRank()) < Integer.parseInt(arg3)) {
						toReturn = true;
						break;
					}
				}
			} else if (arg2.equals(">")) {
				for (int i = 0; i < candidates.size(); i++) {
					//System.out.println("candidate: " + candidates.get(i).getmySymbol());
					//System.out.println("candidate pos: " + candidates.get(i).getLocation().getmyAlgebraicLocation());
					if (Integer.parseInt(candidates.get(i).getLocation().getRank()) > Integer.parseInt(arg3)) {
						toReturn = true;
						break;
					}
				}
			} else if (arg2.equals(">=")) {
				for (int i = 0; i < candidates.size(); i++) {
					//System.out.println("candidate: " + candidates.get(i).getmySymbol());
					//System.out.println("candidate pos: " + candidates.get(i).getLocation().getmyAlgebraicLocation());
					if (Integer.parseInt(candidates.get(i).getLocation().getRank()) >= Integer.parseInt(arg3)) {
						toReturn = true;
						break;
					}
				}
			} else if (arg2.equals("<=")) {
				for (int i = 0; i < candidates.size(); i++) {
					//System.out.println("candidate: " + candidates.get(i).getmySymbol());
					//System.out.println("candidate pos: " + candidates.get(i).getLocation().getmyAlgebraicLocation());
					if (Integer.parseInt(candidates.get(i).getLocation().getRank()) <= Integer.parseInt(arg3)) {
						toReturn = true;
						break;
					}
				}
			}
		}
		return toReturn;
	}
	
	private boolean execCompareRankVar(String arg1, String arg2, String arg3, String comp) {
		//System.out.println("execRankVar arg1: " + arg1);
		//System.out.println("execRankVar arg2: " + arg2);
		//System.out.println("execRankVar arg3: " + arg3);
		boolean toReturn = false;
		ArrayList<ChessPiece> candidates = mySuspects.get(arg3);
		ArrayList<ChessPiece> newCandidates = new ArrayList<ChessPiece>();
		if (candidates == null){
			candidates = myGame.getStringToCP().get(arg1);
		}
		if (comp.equals("=")) {
			for (int i = 0; i < candidates.size(); i++) {
				//System.out.println("candidate: " + candidates.get(i).getmySymbol());
				//System.out.println("candidate pos: " + candidates.get(i).getLocation().getmyAlgebraicLocation());
				if (candidates.get(i).getLocation().getRank().equals(arg2)) {
					toReturn = true;
					newCandidates.add(candidates.get(i));
				}
			}
		} else if (comp.equals("<")) {
			for (int i = 0; i < candidates.size(); i++) {
				//System.out.println("candidate: " + candidates.get(i).getmySymbol());
				//System.out.println("candidate pos: " + candidates.get(i).getLocation().getmyAlgebraicLocation());
				if (Integer.parseInt(candidates.get(i).getLocation().getRank()) < Integer.parseInt(arg2)) {
					toReturn = true;
					newCandidates.add(candidates.get(i));
				}
			}
		} else if (comp.equals(">")) {
			for (int i = 0; i < candidates.size(); i++) {
				//System.out.println("candidate: " + candidates.get(i).getmySymbol());
				//System.out.println("candidate pos: " + candidates.get(i).getLocation().getmyAlgebraicLocation());
				if (Integer.parseInt(candidates.get(i).getLocation().getRank()) > Integer.parseInt(arg2)) {
					toReturn = true;
					newCandidates.add(candidates.get(i));
				}
			}
		} else if (comp.equals(">=")) {
			for (int i = 0; i < candidates.size(); i++) {
				//System.out.println("candidate: " + candidates.get(i).getmySymbol());
				//System.out.println("candidate pos: " + candidates.get(i).getLocation().getmyAlgebraicLocation());
				if (Integer.parseInt(candidates.get(i).getLocation().getRank()) >= Integer.parseInt(arg2)) {
					toReturn = true;
					newCandidates.add(candidates.get(i));
				}
			}
		} else if (comp.equals("<=")) {
			for (int i = 0; i < candidates.size(); i++) {
				//System.out.println("candidate: " + candidates.get(i).getmySymbol());
				//System.out.println("candidate pos: " + candidates.get(i).getLocation().getmyAlgebraicLocation());
				if (Integer.parseInt(candidates.get(i).getLocation().getRank()) <= Integer.parseInt(arg2)) {
					toReturn = true;
					newCandidates.add(candidates.get(i));
				}
			}
		}
		mySuspects.remove(arg1 + arg3);
		mySuspects.put(arg1 + arg3, newCandidates);
		return toReturn;
	}
	
	private boolean execRelRank(String arg1, String arg2) {
		//System.out.println("execRank arg1: " + arg1);
		//System.out.println("execRank arg2: " + arg2);
		boolean toReturn = false;
		if (arg1.length() > 1) {
			toReturn = execRelRankVar(arg1.substring(0, 1), arg2, arg1.substring(1));
		} else {
			ArrayList<ChessPiece> candidates = myGame.getStringToCP().get(arg1);
			for (int i = 0; i < candidates.size(); i++) {
				//System.out.println("candidate: " + candidates.get(i).getmySymbol());
				//System.out.println("candidate pos: " + candidates.get(i).getLocation().getmyAlgebraicLocation());
				if (candidates.get(i).getRelativeRank().equals(arg2)) {
					toReturn = true;
					break;
				}
			}
		}
		return toReturn;
	}
	
	private boolean execRelRankVar(String arg1, String arg2, String arg3) {
		//System.out.println("execRankVar arg1: " + arg1);
		//System.out.println("execRankVar arg2: " + arg2);
		//System.out.println("execRankVar arg3: " + arg3);
		boolean toReturn = false;
		ArrayList<ChessPiece> candidates = mySuspects.get(arg3);
		ArrayList<ChessPiece> newCandidates = new ArrayList<ChessPiece>();
		if (candidates == null){
			candidates = myGame.getStringToCP().get(arg1);
		}		
		for (int i = 0; i < candidates.size(); i++) {
			//System.out.println("candidate: " + candidates.get(i).getmySymbol());
			//System.out.println("candidate pos: " + candidates.get(i).getLocation().getmyAlgebraicLocation());
			if (candidates.get(i).getRelativeRank().equals(arg2)) {
				toReturn = true;
				newCandidates.add(candidates.get(i));
			}
		}
		mySuspects.remove(arg1 + arg3);
		mySuspects.put(arg1 + arg3, newCandidates);
		return toReturn;
	}
	
	private boolean execCompareRelRank(String arg1, String arg2, String arg3) {
		boolean toReturn = false;
		//System.out.println("execCRank arg1: " + arg1);
		//System.out.println("execCRank arg2: " + arg2);
		//System.out.println("execCRank arg3: " + arg3);
		if (arg1.length() > 1) {
			toReturn = execCompareRankVar(arg1.substring(0, 1), arg3, arg1.substring(1), arg2);
		} else {
			ArrayList<ChessPiece> candidates = myGame.getStringToCP().get(arg1);
			if (arg2.equals("=")) {
				for (int i = 0; i < candidates.size(); i++) {
					//System.out.println("candidate: " + candidates.get(i).getmySymbol());
					//System.out.println("candidate pos: " + candidates.get(i).getLocation().getmyAlgebraicLocation());
					if (candidates.get(i).getRelativeRank().equals(arg3)) {
						toReturn = true;
						break;
					}
				}
			} else if (arg2.equals("<")) {
				for (int i = 0; i < candidates.size(); i++) {
					//System.out.println("candidate: " + candidates.get(i).getmySymbol());
					//System.out.println("candidate pos: " + candidates.get(i).getLocation().getmyAlgebraicLocation());
					if (Integer.parseInt(candidates.get(i).getRelativeRank()) < Integer.parseInt(arg3)) {
						toReturn = true;
						break;
					}
				}
			} else if (arg2.equals(">")) {
				for (int i = 0; i < candidates.size(); i++) {
					//System.out.println("candidate: " + candidates.get(i).getmySymbol());
					//System.out.println("candidate pos: " + candidates.get(i).getLocation().getmyAlgebraicLocation());
					if (Integer.parseInt(candidates.get(i).getRelativeRank()) > Integer.parseInt(arg3)) {
						toReturn = true;
						break;
					}
				}
			} else if (arg2.equals(">=")) {
				for (int i = 0; i < candidates.size(); i++) {
					//System.out.println("candidate: " + candidates.get(i).getmySymbol());
					//System.out.println("candidate pos: " + candidates.get(i).getLocation().getmyAlgebraicLocation());
					if (Integer.parseInt(candidates.get(i).getRelativeRank()) >= Integer.parseInt(arg3)) {
						toReturn = true;
						break;
					}
				}
			} else if (arg2.equals("<=")) {
				for (int i = 0; i < candidates.size(); i++) {
					//System.out.println("candidate: " + candidates.get(i).getmySymbol());
					//System.out.println("candidate pos: " + candidates.get(i).getLocation().getmyAlgebraicLocation());
					if (Integer.parseInt(candidates.get(i).getRelativeRank()) <= Integer.parseInt(arg3)) {
						toReturn = true;
						break;
					}
				}
			}
		}
		return toReturn;
	}
	
	private boolean execCompareRelRankVar(String arg1, String arg2, String arg3, String comp) {
		//System.out.println("execRankVar arg1: " + arg1);
		//System.out.println("execRankVar arg2: " + arg2);
		//System.out.println("execRankVar arg3: " + arg3);
		boolean toReturn = false;
		ArrayList<ChessPiece> candidates = mySuspects.get(arg3);
		ArrayList<ChessPiece> newCandidates = new ArrayList<ChessPiece>();
		if (candidates == null){
			candidates = myGame.getStringToCP().get(arg1);
		}
		if (comp.equals("=")) {
			for (int i = 0; i < candidates.size(); i++) {
				//System.out.println("candidate: " + candidates.get(i).getmySymbol());
				//System.out.println("candidate pos: " + candidates.get(i).getLocation().getmyAlgebraicLocation());
				if (candidates.get(i).getRelativeRank().equals(arg2)) {
					toReturn = true;
					newCandidates.add(candidates.get(i));
				}
			}
		} else if (comp.equals("<")) {
			for (int i = 0; i < candidates.size(); i++) {
				//System.out.println("candidate: " + candidates.get(i).getmySymbol());
				//System.out.println("candidate pos: " + candidates.get(i).getLocation().getmyAlgebraicLocation());
				if (Integer.parseInt(candidates.get(i).getRelativeRank()) < Integer.parseInt(arg2)) {
					toReturn = true;
					newCandidates.add(candidates.get(i));
				}
			}
		} else if (comp.equals(">")) {
			for (int i = 0; i < candidates.size(); i++) {
				//System.out.println("candidate: " + candidates.get(i).getmySymbol());
				//System.out.println("candidate pos: " + candidates.get(i).getLocation().getmyAlgebraicLocation());
				if (Integer.parseInt(candidates.get(i).getRelativeRank()) > Integer.parseInt(arg2)) {
					toReturn = true;
					newCandidates.add(candidates.get(i));
				}
			}
		} else if (comp.equals(">=")) {
			for (int i = 0; i < candidates.size(); i++) {
				//System.out.println("candidate: " + candidates.get(i).getmySymbol());
				//System.out.println("candidate pos: " + candidates.get(i).getLocation().getmyAlgebraicLocation());
				if (Integer.parseInt(candidates.get(i).getRelativeRank()) >= Integer.parseInt(arg2)) {
					toReturn = true;
					newCandidates.add(candidates.get(i));
				}
			}
		} else if (comp.equals("<=")) {
			for (int i = 0; i < candidates.size(); i++) {
				//System.out.println("candidate: " + candidates.get(i).getmySymbol());
				//System.out.println("candidate pos: " + candidates.get(i).getLocation().getmyAlgebraicLocation());
				if (Integer.parseInt(candidates.get(i).getRelativeRank()) <= Integer.parseInt(arg2)) {
					toReturn = true;
					newCandidates.add(candidates.get(i));
				}
			}
		}
		mySuspects.remove(arg1 + arg3);
		mySuspects.put(arg1 + arg3, newCandidates);
		return toReturn;
	}
	
	
	private boolean execOnly(ArrayList<String> args) {
		// Need to have error handling for only(Qx, Qx). Doesn't make sense!
		ArrayList<String> seenVars = new ArrayList<String>();
		for (int m = 0; m < args.size(); m++) {
			if (args.get(m).length() > 1) {
				if (seenVars.contains(args.get(m).substring(1))) {
					System.err.println("Invalid only expression. Each variable may appear only once in an only expression as all vars must refer to a unique piece");
					System.exit(-1);
				} else {
					seenVars.add(args.get(m));
				}
			}
		}
		boolean toReturn = false;
		ArrayList<ChessPiece> pieces = (ArrayList<ChessPiece>) myGame.getActivePieces();
		//System.out.println("pieces: " + pieces);
		//System.out.println("args: " + args);
		if (pieces.size() == args.size()) {
			toReturn = execOnlyHelper(pieces, args);
		}
		//System.out.println("pieces: " + pieces);
		return toReturn;	
	}
	
	private boolean execOnlyHelper(ArrayList<ChessPiece> gamePieces, ArrayList<String> queryPieces) {
		//System.out.println("pieces: " + gamePieces);
		//System.out.println("args: " + queryPieces);
		boolean toReturn = false;
		if (queryPieces.size() == 0 && gamePieces.size() == 0) {
			toReturn = true;
		} else {
			for (int i = 0; i < queryPieces.size(); i++) {
				String query = queryPieces.get(i);
				if (query.length() > 1) {
					// have to make sure it's in my suspects and my pieces.
					for (int j = 0; j < gamePieces.size(); j++) {
						ChessPiece game = gamePieces.get(j);
						if (query.substring(0, 1).equals(game.toString())) {
							ArrayList<ChessPiece> candidates = mySuspects.get(query);
							if (candidates != null) {
								for (int z = 0; z < candidates.size(); z++) {
									// have to also make sure a candidate matches
									if (candidates.get(z).getID() == game.getID()) {
										ArrayList<ChessPiece> gameClone = (ArrayList<ChessPiece>) gamePieces.clone();
										ArrayList<String> queryClone = (ArrayList<String>) queryPieces.clone();
										gameClone.remove(game);
										queryClone.remove(query);
										toReturn = execOnlyHelper(gameClone, queryClone);
										break;
									}
								}
							} else {
								// var exp but first time
								ArrayList<ChessPiece> gameClone = (ArrayList<ChessPiece>) gamePieces.clone();
								ArrayList<String> queryClone = (ArrayList<String>) queryPieces.clone();
								gameClone.remove(game);
								queryClone.remove(query);
								toReturn = execOnlyHelper(gameClone, queryClone);
								break;
							}
						}
					}
					
				}
				for (int j = 0; j < gamePieces.size(); j++) {
					ChessPiece game = gamePieces.get(j);
					if (query.equals(game.toString())) {
						ArrayList<ChessPiece> gameClone = (ArrayList<ChessPiece>) gamePieces.clone();
						ArrayList<String> queryClone = (ArrayList<String>) queryPieces.clone();
						gameClone.remove(game);
						queryClone.remove(query);
						toReturn = execOnlyHelper(gameClone, queryClone);
						break;
					}
				}
				if (toReturn == true) {
					break;
				}
			}
		}
		return toReturn;
		
	}
	
	private boolean execOnlyVar(ArrayList<String> args) {
		boolean toReturn = false;
		ArrayList<ChessPiece> pieces = (ArrayList<ChessPiece>) myGame.getActivePieces();
		if (pieces.size() == args.size()) {
			for (int i = 0; i < pieces.size(); i++) {
				String temp = pieces.get(i).getMySymbol();
				for (int j = 0; j < args.size(); j++) {
					String temp2 = args.get(i);
					if (temp.equals(temp2)) {
						args.remove(temp2);
						break;
					}
				}
			}
		}
		if (args.size() == 0) {
			toReturn = true;
		}
		return toReturn;	
	}
	
	
	// returns true if there is an ordering such that all args could be the same color
	private boolean execSameColor(ArrayList<String> args) {
		// get first thing
		// iterate through all possibilities
		// if could
		boolean blackPossible = true;
		boolean whitePossible = true;
		boolean toReturn = false;
		for (int i = 0; i < args.size(); i++) {
			ArrayList<ChessPiece> candidates = myGame.getStringToCP().get(args.get(i));
			ArrayList<ChessPiece> maybeCandidates = null;
			String var = null;
			if (args.get(i).length() > 1) {
				var = args.get(i);
				//System.out.println("var: " + var);
				maybeCandidates = mySuspects.get(var);
				//System.out.println("samecolor maybeCandidates: " + maybeCandidates);
				if (maybeCandidates != null){
					// then there were prior results!
					//System.out.println("candidates1: " + candidates);
					candidates = maybeCandidates;
				} else {
					// a var statement but first time
					//System.out.println("candidates2: " + candidates);
					candidates = myGame.getStringToCP().get(args.get(i).substring(0, 1));
				}
			}
			
			boolean currBlackPossible = false;
			boolean currWhitePossible = false;
			for (int j = 0; j < candidates.size(); j++) {
				//System.out.println("samecolor col: " + candidates.get(j).getColor());
				//System.out.println("samecolor loc: " + candidates.get(j).getLocation());
				if (candidates.get(j).isBlack()) {
					currBlackPossible = true;
				} else {
					currWhitePossible = true;
				}
			}
			// if none of poss for a piece can be black then all the parameters can't be black now can they?
			if (currBlackPossible == false) {
				blackPossible = currBlackPossible;
			}
			if (currWhitePossible == false) {
				whitePossible = currWhitePossible;
			}
			//System.out.println("currBlackPossible: " + blackPossible);
			//System.out.println("currWhitePossible: " + whitePossible);
		}
		if (blackPossible || whitePossible) {
			toReturn = true;
		} else {
			toReturn = false;
		}
		//System.out.println("end BlackPossible: " + blackPossible);
		//System.out.println("end WhitePossible: " + whitePossible);
		//System.out.println("toReturn: " + toReturn);
		return toReturn;
	}
	
	private boolean execExists(ArrayList<String> args) {
		boolean goodSoFar = false;
		for (int i = 0; i < args.size(); i++) {
			ArrayList<ChessPiece> candidates = myGame.getStringToCP().get(args.get(i));
			if (candidates.size() > 0) {
				goodSoFar = true;
			}
		}
		return goodSoFar;
	}
	
	private boolean execCaptured(String arg1, String arg2) {
		//System.out.println("exec captured: " + arg1 + ", " + arg2);
		boolean toReturn = false;
		boolean didCapture = false;
		boolean gotCaptured = false;
		ChessPiece capturer = myGame.getCapturer();
		String capturerColor = "";
		ChessPiece captured = myGame.getCaptured();
		String capturedColor = "";
		if (capturer == null || captured == null) {
			//System.out.println("capturer null: " + myGame.getCapturer());
			//System.out.println("captured null: " + myGame.getCaptured());
		} else if (arg1.length() > 1 || arg2.length() > 1) {
			//System.out.println("capturer len: " + myGame.getCapturer().getmySymbol());
			//System.out.println("captured len: " + myGame.getCaptured().getmySymbol());
			toReturn = execCapturedVar(arg1, arg2);
		} else {
			capturerColor = capturer.getAllegiance().getAllegiance();
			capturedColor = captured.getAllegiance().getAllegiance();
			ArrayList<ChessPiece> candidates = myGame.getStringToCP().get(arg1);
			ArrayList<ChessPiece> candidates2 = myGame.getStringToCP().get(arg2);
			if ((arg1.equals("g") || arg1.equals("G"))) {
				didCapture = true;
			}
			if ((arg2.equals("g") || arg2.equals("G"))) {
				gotCaptured = true;
			}
			if ((arg1.equals("l") || arg1.equals("L")) && capturerColor.equals("w")) {
				didCapture = true;
			}
			if ((arg2.equals("l") || arg2.equals("L")) && capturedColor.equals("w")) {
				gotCaptured = true;
			}
			if ((arg1.equals("d") || arg1.equals("D")) && capturerColor.equals("b")) {
				didCapture = true;
			}
			if ((arg2.equals("d") || arg2.equals("D")) && capturedColor.equals("b")) {
				gotCaptured = true;
			}
			if (arg1.equals("1") || arg1.equals("2") || arg1.equals("3") ||
					arg1.equals("4") || arg1.equals("5") || arg1.equals("6")) {
					// see if generic type and specific type match
					if (arg1.equals("1") && capturer.getMyType().equals("Pawn")) {
						didCapture = true;
					} else if (arg1.equals("2") && capturer.getMyType().equals("Rook")) {
						didCapture = true;
					} else if (arg1.equals("3") && capturer.getMyType().equals("Horse")) {
						didCapture = true;
					} else if (arg1.equals("4") && capturer.getMyType().equals("Bishop")) {
						didCapture = true;
					} else if (arg1.equals("5") && capturer.getMyType().equals("Queen")) {
						didCapture = true;
					} else if (arg1.equals("6") && capturer.getMyType().equals("King")) {
						didCapture = true;
					}
			}
			if (arg2.equals("1") || arg2.equals("2") || arg2.equals("3") ||
					arg2.equals("4") || arg2.equals("5") || arg2.equals("6")) {
					// see if generic type and specific type match
					if (arg2.equals("1") && captured.getMyType().equals("Pawn")) {
						gotCaptured = true;
					} else if (arg1.equals("2") && captured.getMyType().equals("Rook")) {
						gotCaptured = true;
					} else if (arg2.equals("3") && captured.getMyType().equals("Horse")) {
						gotCaptured = true;
					} else if (arg2.equals("4") && captured.getMyType().equals("Bishop")) {
						gotCaptured = true;
					} else if (arg2.equals("5") && captured.getMyType().equals("Queen")) {
						gotCaptured = true;
					} else if (arg2.equals("6") && captured.getMyType().equals("King")) {
						gotCaptured = true;
					}
			}
			//System.out.println("capturer: " + myGame.getCapturer().getmySymbol());
			//System.out.println("captured: " + myGame.getCaptured().getmySymbol());
			if (myGame.getCapturer().getMySymbol().equals(arg1)) {
				didCapture = true;
			}
			if (myGame.getCaptured().getMySymbol().equals(arg2)) {
				gotCaptured = true;
			}
		}
		toReturn = didCapture && gotCaptured;
		return toReturn;
	}
	
	public boolean execCapturedVar(String arg1, String arg2) {
		boolean toReturn = false;
		String sub = arg1.substring(0, 1);
		String sub2 = arg2.substring(0, 1);
		ArrayList<ChessPiece> candidates = myGame.getStringToCP().get(arg1);
		ArrayList<ChessPiece> maybeCandidates = null;
		String var = null;
		if (arg1.length() > 1) {
			var = arg1;
			//System.out.println("var: " + var);
			maybeCandidates = mySuspects.get(var);
			//System.out.println("mySuspects: " + mySuspects);
			if (maybeCandidates != null){
				// then there were prior results!
				//System.out.println("candidates1: " + candidates);
				candidates = maybeCandidates;
			} else {
				// a var statement but first time
				//System.out.println("candidates2: " + candidates);
				candidates = myGame.getStringToCP().get(arg1.substring(0, 1));
			}
		}
		//System.out.println("candidates: " + candidates);
		
		// same for arg2
		ArrayList<ChessPiece> candidates2 = myGame.getStringToCP().get(arg2);
		ArrayList<ChessPiece> maybeCandidates2 = null;
		String var2 = null;
		if (arg2.length() > 1) {
			var2 = arg2.substring(1);
			maybeCandidates2 = mySuspects.get(var2);
			if (maybeCandidates2 != null){
				candidates2 = maybeCandidates2;
			} else {
				candidates2 = myGame.getStringToCP().get(arg2.substring(0, 1));
			}
		}
		ArrayList<ChessPiece> newCandidates = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> newCandidates2 = new ArrayList<ChessPiece>();	
		boolean didCapture = false;
		boolean gotCaptured = false;
		ChessPiece capturer = myGame.getCapturer();
		ChessPiece captured = myGame.getCaptured();
		if ((arg1.equals("g") || arg1.equals("G"))) {
			didCapture = true;
		}
		if ((arg2.equals("g") || arg2.equals("G"))) {
			gotCaptured = true;
		}
		if ((arg1.equals("l") || arg1.equals("L")) && capturer.isWhite()) {
			didCapture = true;
		}
		if ((arg2.equals("l") || arg2.equals("L")) && captured.isWhite()) {
			gotCaptured = true;
		}
		if ((arg1.equals("d") || arg1.equals("D")) && capturer.isBlack()) {
			didCapture = true;
		}
		if ((arg2.equals("d") || arg2.equals("D")) && captured.isBlack()) {
			gotCaptured = true;
		}
		if (arg1.equals("1") || arg1.equals("2") || arg1.equals("3") ||
				arg1.equals("4") || arg1.equals("5") || arg1.equals("6")) {
				// see if generic type and specific type match
				if (arg1.equals("1") && capturer.getMyType().equals("Pawn")) {
					didCapture = true;
				} else if (arg1.equals("2") && capturer.getMyType().equals("Rook")) {
					didCapture = true;
				} else if (arg1.equals("3") && capturer.getMyType().equals("Horse")) {
					didCapture = true;
				} else if (arg1.equals("4") && capturer.getMyType().equals("Bishop")) {
					didCapture = true;
				} else if (arg1.equals("5") && capturer.getMyType().equals("Queen")) {
					didCapture = true;
				} else if (arg1.equals("6") && capturer.getMyType().equals("King")) {
					didCapture = true;
				}
		}
		if (arg2.equals("1") || arg2.equals("2") || arg2.equals("3") ||
				arg2.equals("4") || arg2.equals("5") || arg2.equals("6")) {
				// see if generic type and specific type match
				if (arg2.equals("1") && captured.getMyType().equals("Pawn")) {
					gotCaptured = true;
				} else if (arg1.equals("2") && captured.getMyType().equals("Rook")) {
					gotCaptured = true;
				} else if (arg2.equals("3") && captured.getMyType().equals("Horse")) {
					gotCaptured = true;
				} else if (arg2.equals("4") && captured.getMyType().equals("Bishop")) {
					gotCaptured = true;
				} else if (arg2.equals("5") && captured.getMyType().equals("Queen")) {
					gotCaptured = true;
				} else if (arg2.equals("6") && captured.getMyType().equals("King")) {
					gotCaptured = true;
				}
		}
		
		for (int i = 0; i < candidates.size(); i++) {
			if (myGame.getCapturer().getMySymbol().equals(sub)) {
				didCapture = true;
			}
		}
		for (int j = 0; j < candidates2.size(); j++) {
			if (myGame.getCapturer().getMySymbol().equals(sub2)) {
				gotCaptured = true;
			}
		}
		return toReturn;
	}
	
	private boolean execDifferent(ArrayList<String> args) {
		boolean toReturn = false;
		// get var and see if anything different for all next vars
		if (args.size() <= 1) {
			toReturn = true;
		} else {
			String current = args.get(0);
			ArrayList<ChessPiece> suspects = mySuspects.get(current);
			if (suspects == null) {
				// then no current restrictions on that type
				suspects = myGame.getStringToCP().get(current.substring(0, 1));
			}
			// find an x and see if there is a valid sequencing for rest of variables if it's true
			for (int i = 1; i < args.size(); i++) {
				String contrast = args.get(i);
				ChessPiece c = suspects.get(i);
				ArrayList<ChessPiece> suspects2 = mySuspects.get(contrast);
				if (suspects2 == null) {
					// then no current restrictions on that type
					suspects2 = myGame.getStringToCP().get(contrast.substring(0, 1));
				}
				for (int j = 0; j < suspects2.size(); j++) {
					ChessPiece c2 = suspects2.get(j);
					if (!(c.equals(c2))) {
						ArrayList<String> copy = (ArrayList<String>) args.clone();
						copy.remove(0);
						boolean maybe = execDifferent(copy);
						if (maybe == true) {
							toReturn = true;
							break;
						}
					}
				}
				if (toReturn == true) {
					break;
				}
			}
		}
		return toReturn;
	}
	
	private boolean execEnd() {
		int numMoves = myMoves.size() - 1;
		//System.out.println("execEnd myRound: " + myGame.getmyRound());
		//System.out.println("execEnd myMoves: " + numMoves);
		boolean toReturn = false;
		if (myGame.getRound() == numMoves) {
			// last move
			toReturn = true;
		}
		//System.out.println("toReturn: " + toReturn);
		return toReturn;
	}
	
	private boolean execStart() {
		//System.out.println("myRound: " + myGame.getmyRound());
		boolean toReturn = false;
		if (myGame.getRound() == 0) {
			toReturn = true;
		}
		return toReturn;
	}	
}
