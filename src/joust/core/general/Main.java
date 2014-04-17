package joust.core.general;


public class Main {
	
	public static void main(String[] args) {
		if (args.length > 3 || args.length <= 1) {
			System.out.println("Invalid number of args");
			System.exit(1);
		}
		if (args.length == 3) {
			String chessGame = args[0];  //a pgn file
			String initialConfig = args[1];
			String joustProgram = args[2];
			ChessBoardMatcher j = new ChessBoardMatcher(chessGame, initialConfig, false);
			boolean foundMatch = j.find(joustProgram);
			System.out.println("foundMatch: " + foundMatch);
		} else {
			String chessGame = args[0];  //a pgn file
			String joustProgram = args[1];
			ChessBoardMatcher j = new ChessBoardMatcher(chessGame);
			boolean foundMatch = j.find(joustProgram);
			System.out.println("foundMatch: " + foundMatch);
		}
	}
	
	// for debugging purposes
	public static boolean run(String pgnFile, String joustProgram) {
			ChessBoardMatcher j = new ChessBoardMatcher(pgnFile);
			return j.find(joustProgram);
	}
	
	// for debugging purposes
	public static boolean run(String pgnFile, String initialConfig, String joustProgram) {
		ChessBoardMatcher j = new ChessBoardMatcher(pgnFile, initialConfig);
		return j.find(joustProgram);
	}

}
