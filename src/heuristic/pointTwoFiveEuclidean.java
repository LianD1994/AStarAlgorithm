package heuristic;

import controller.MainPageController;

/**
 * @author Liangyan Ding
 */
public class pointTwoFiveEuclidean extends heuristic {
    @Override
    public double calculateHeuristic(int nodeRow, int nodeColumn) {
        return 0.25*Math.sqrt((nodeRow - MainPageController.goalRow)*(nodeRow - MainPageController.goalRow) + (nodeColumn - MainPageController.goalColumn)*(nodeColumn - MainPageController.goalColumn));
    }
}
