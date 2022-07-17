package assignment2;

import java.util.*;

public class Question5 {
	
	//Driver method
		public static void main(String args[]) {
			
			steepestAscentHillClimbingMain();
			hillClimbingWithSidewaysMoveMain();

		}

		//Method to start Steepest Ascent Hill Climbing Algorithm and loop it for 1000 times
		private static void steepestAscentHillClimbingMain() {
			Board state = new Board();
			int[] currentBoard = new int[8];
			state.setNoOfTries(0.0f);
			state.setGoalReached(false);
			System.out.println("\nSteepest-ascent Hill Climbing : ");
			for (int i = 0; i < 1000; i++) {
				currentBoard = generateRandomBoard(8);
				steepestAscentHillClimbing(currentBoard, state, i);

				if (state.isGoalReached()) {
					state.setNoOfTries(state.getNoOfTries() + 1);
					state.setGoalReached(false);
				}
			}
			float successRate = (state.getNoOfTries() / 1000) * 100;
			System.out.println("\nSteepest-ascent Hill Climbing Results: ");
			System.out.println("Success Rate:: " + successRate + " %");
			System.out.println("Failure Rate:: " + (100.0f - successRate) + " %");
			System.out.println("Success Average: "
					+ String.format("%.00f", state.getSuccessSteps().stream().mapToDouble(a -> a).average().getAsDouble()));
			System.out.println("Failure Average: "
					+ String.format("%.00f", state.getFailureSteps().stream().mapToDouble(a -> a).average().getAsDouble()));

		}

		//Method to start Steepest Ascent Hill Climbing Algorithm with sideways move and loop it for 1000 times
		private static void hillClimbingWithSidewaysMoveMain() {
			Board state = new Board();
			state.setNoOfTries(0.0f);
			state.setGoalReached(false);
			int[] currentBoard = new int[8];
			System.out.println("\nHill Climbing With Sideways Moves: ");
			for (int i = 0; i < 1000; i++) {
				currentBoard = generateRandomBoard(8);
				hillClimbingWithSidewaysMove(currentBoard, state, i);

				if (state.isGoalReached()) {
					state.setNoOfTries(state.getNoOfTries() + 1);
					state.setGoalReached(false);
				}
			}
			float successRate = (state.getNoOfTries() / 1000) * 100;
			System.out.println("\nHill Climbing With Sideways Moves Results: ");
			System.out.println("Success Rate:: " + successRate + " %");
			System.out.println("Failure Rate:: " + (100.0f - successRate) + " %");
			System.out.println("Success Average: "
					+ String.format("%.00f", state.getSuccessSteps().stream().mapToDouble(a -> a).average().getAsDouble()));
			System.out.println("Failure Average: " + String.format("%.00f", (state.getFailureSteps().isEmpty() ? 0
					: state.getFailureSteps().stream().mapToDouble(a -> a).average().getAsDouble())));

		}
		
		// Steepest Ascent Hill Climbing
		private static int[] steepestAscentHillClimbing(int[] currentBoard, Board state, int Try) {

			int currentThreats = calculateHeuristic(currentBoard);
			int min = 0;
			Integer steps = 0;
			System.out.print("\nTry " + (Try + 1) + ": ");
			System.out.println(Arrays.toString(currentBoard));	

			while (null != currentBoard && min < currentThreats) {

				int[][] heuristicBoard = calculatePotentialMoves(currentBoard);
				min = Arrays.stream(heuristicBoard).flatMapToInt(Arrays::stream).min().getAsInt();
				if (min < currentThreats) {

					currentBoard = traverseToNeighbor(min, Try, currentBoard, heuristicBoard);
					if(null != currentBoard) {
					steps++;
					currentThreats = calculateHeuristic(currentBoard);
					heuristicBoard = calculatePotentialMoves(currentBoard);
					min = Arrays.stream(heuristicBoard).flatMapToInt(Arrays::stream).min().getAsInt();
					}
				}
			}
			if (0 == currentThreats) {
				System.out.println("Goal state reached: Yes");
				state.setGoalReached(true);
				state.getSuccessSteps().add(steps);
			} else {
				System.out.println("Goal state reached: No");
				state.getFailureSteps().add(steps);
			}
			return currentBoard;
		}
		
		//Steepest Ascent Hill Climbing with sideways move
		private static int[] hillClimbingWithSidewaysMove(int[] currentBoard, Board state, int Try) {

			int currentThreats = calculateHeuristic(currentBoard);
			int min = 0, sidewaysMovesCounter = 0;
			int steps = 0;

			System.out.print("\nTry " + (Try + 1) +": ");
			System.out.println(Arrays.toString(currentBoard));

			while (null!=currentBoard && min <= currentThreats && 100 >= sidewaysMovesCounter) {
				if (0 == currentThreats) {
					state.setGoalReached(true);
					state.getSuccessSteps().add(steps);
					break;
				} else {

					int[][] heuristicBoard = calculatePotentialMoves(currentBoard);
					min = Arrays.stream(heuristicBoard).flatMapToInt(Arrays::stream).min().getAsInt();
					if (min <= currentThreats) {
						if (min == currentThreats) {
							sidewaysMovesCounter++;
						} else {
							sidewaysMovesCounter = 0;
						}
						currentBoard = traverseToNeighbor(min, Try, currentBoard, heuristicBoard);
						if(null!=currentBoard) {
						steps++;
						currentThreats = calculateHeuristic(currentBoard);
						heuristicBoard = calculatePotentialMoves(currentBoard);
						min = Arrays.stream(heuristicBoard).flatMapToInt(Arrays::stream).min().getAsInt();
						}
					} else {
						state.getFailureSteps().add(steps);
						break;
					}
				}
			}
			if (0 == currentThreats) {
				System.out.println("Goal state reached: Yes");
				state.setGoalReached(true);
				state.getSuccessSteps().add(steps);
			} else {
				System.out.println("Goal state reached: No");
				state.getFailureSteps().add(steps);
			}
			return currentBoard;
		}
		
		//Method to calculate the heuristic values for all possible moves on the board
		private static int[][] calculatePotentialMoves(int[] currentBoard) {
			
			int[][] heuristicBoard = new int[currentBoard.length][currentBoard.length];
			for (int i = 0; i < currentBoard.length; i++) {
				int[] newBoard = currentBoard.clone();
				for (int j = 0; j < currentBoard.length; j++) {
					newBoard[i] = j;
					heuristicBoard[i][j] = calculateHeuristic(newBoard);
					//assigning maximum value to current queen positions, to avoid repeated state generation
					if(i == currentBoard[j]) {
						heuristicBoard[i][j] = 1000;
					}
				}
			}
			return heuristicBoard;
		}

		//Method to move the queen to the selected heuristic
		private static int[] traverseToNeighbor(int min, int printCount, int[] currentBoard, int[][] heuristicBoard) {
			List<int[]> minimumPositions = new ArrayList<>();
			
			int[] cb = new int[currentBoard.length];
			cb = currentBoard.clone();
			
			for (int i = 0; i < heuristicBoard.length; i++) {
				for (int j = 0; j < heuristicBoard.length; j++) {
					if (min == heuristicBoard[i][j] && currentBoard[j]!=i) {
						int[] position = {i, j};
						minimumPositions.add(position);
					}
				}
			}

			// Randomly choosing value for neighbor from best heuristic
			int[] position = minimumPositions.get(new Random().nextInt(minimumPositions.size()));
			cb[position[0]] = position[1];
			
			//checking for duplicate current state
			while (isArrayEqual(cb, currentBoard)) {
				if (minimumPositions.size() == 1) {
					return null;
				} else {
					minimumPositions.remove(minimumPositions.indexOf(position));
					position = minimumPositions.get(new Random().nextInt(minimumPositions.size()));
					cb[position[0]] = position[1];
				}
			}
			return cb;
		}
		
		//Method to calculate heuristic (Here heuristic is the number of queens which can attack each other)
		private static int calculateHeuristic(int[] currentBoard) {
			int threats = 0;
			for (int i = 0; i < currentBoard.length; i++) {
				int currentQueen = currentBoard[i];
				for (int j = i + 1; j < currentBoard.length; j++) {
					int potentialThreatPosition = Math.abs(j - i);
					int distanceInQueens = Math.abs(currentQueen - currentBoard[j]);
					if (currentQueen == currentBoard[j] || potentialThreatPosition == distanceInQueens) {
						threats++;
					}
				}
			}
			return threats;
		}
		
		//Method to check if two chessboard states are same
		private static boolean isArrayEqual(int[] cb, int[] currentBoard) {
			boolean equal = true;
			for (int i = 0; i < currentBoard.length; i++) {
				if(cb[i] != currentBoard[i]) {
					equal = false;
				}
			}
			return equal;
		}

		//Method to generate a random chessboard
		private static int[] generateRandomBoard(int n) {
			return new Random().ints(n, 0, n - 1).toArray();
		}
}
