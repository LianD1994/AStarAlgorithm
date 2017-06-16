package SearchAlgorithm;

import controller.MainPageController;
import heuristic.*;
import javafx.scene.layout.GridPane;

/**
 * @author Liangyan Ding
 */
public class AStar extends SearchAlgorithm {

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
            return euclidean.calculateHeuristic(nodeRow, nodeColumn);
        }
        if(heuristicFunctionIndex == 2){
            mahattanDistance manhattanDistance = new mahattanDistance();
            return manhattanDistance.calculateHeuristic(nodeRow, nodeColumn);
        }
        if(heuristicFunctionIndex == 3){
            squaredEuclidean squaredEuclidean = new squaredEuclidean();
            return squaredEuclidean.calculateHeuristic(nodeRow, nodeColumn);
        }
        if(heuristicFunctionIndex == 4){
            MainPageController.type = (int)getNode(nodeRow,nodeColumn);
            typeOfNode typeOfNode = new typeOfNode();
            return typeOfNode.calculateHeuristic(nodeRow, nodeColumn);
        }
        if(heuristicFunctionIndex == 5){
            pointTwoFiveEuclidean Euclidean = new pointTwoFiveEuclidean();
            return Euclidean.calculateHeuristic(nodeRow, nodeColumn);
        }

        return -1;
    }

}
