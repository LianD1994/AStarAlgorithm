package heuristic;

import controller.MainPageController;

//2
public class mahattanDistance extends heuristic{

	@Override
	public double calculateHeuristic(int nodeRow, int nodeColumn) {
		return Math.abs(MainPageController.goalRow - nodeRow) + Math.abs(MainPageController.goalColumn - nodeColumn);
	}

}
