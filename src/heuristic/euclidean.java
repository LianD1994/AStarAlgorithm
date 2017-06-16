package heuristic;

import controller.MainPageController;

//1
public class euclidean extends heuristic{

	@Override
	public double calculateHeuristic(int nodeRow, int nodeColumn) {
		return Math.sqrt((nodeRow - MainPageController.goalRow)*(nodeRow - MainPageController.goalRow) + (nodeColumn - MainPageController.goalColumn)*(nodeColumn - MainPageController.goalColumn));
	}
	
}
