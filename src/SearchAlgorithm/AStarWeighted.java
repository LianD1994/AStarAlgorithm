package SearchAlgorithm;

import controller.MainPageController;
import heuristic.*;
import javafx.scene.layout.GridPane;

/**
 * @author Liangyan Ding
 */
public class AStarWeighted extends SearchAlgorithm {

    @Override
    double heuristic(int nodeRow, int nodeColumn, int goalRow, int goalColumn, int heuristicFunctionIndex, double weight, int type, GridPane GridMap) {
        // Euclidean distance
        if(heuristicFunctionIndex == -1){
            System.out.println("Please choose a heuristic function first");
            return -1;
        }
        if(heuristicFunctionIndex == 0){
            return 0;
        }
        if(heuristicFunctionIndex == 1){
            euclidean euclidean = new euclidean();
            return weight*euclidean.calculateHeuristic(nodeRow, nodeColumn);
        }
        if(heuristicFunctionIndex == 2){
            mahattanDistance manhattanDistance = new mahattanDistance();
            return weight*manhattanDistance.calculateHeuristic(nodeRow, nodeColumn);
        }
        if(heuristicFunctionIndex == 3){
            squaredEuclidean squaredEuclidean = new squaredEuclidean();
            return weight*squaredEuclidean.calculateHeuristic(nodeRow, nodeColumn);
        }
        if(heuristicFunctionIndex == 4){
            MainPageController.type = SearchAlgorithm.getNode(nodeRow,nodeColumn);
            typeOfNode typeOfNode = new typeOfNode();
            return weight*typeOfNode.calculateHeuristic(nodeRow, nodeColumn);
        }
        if(heuristicFunctionIndex == 5){
            pointTwoFiveEuclidean Euclidean = new pointTwoFiveEuclidean();
            return weight * Euclidean.calculateHeuristic(nodeRow, nodeColumn);
        }

        return -1;
    }
}
