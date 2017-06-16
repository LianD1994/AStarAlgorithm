package heuristic;

import controller.MainPageController;

//3
public class squaredEuclidean extends heuristic{

	@Override
	public double calculateHeuristic(int nodeRow, int nodeColumn) {
		return (MainPageController.goalRow-nodeRow)*(MainPageController.goalRow-nodeRow) + (MainPageController.goalColumn-nodeColumn)*(MainPageController.goalColumn-nodeColumn);
	}

}
