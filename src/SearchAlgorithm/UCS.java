package SearchAlgorithm;

import javafx.scene.layout.GridPane;

/**
 * @author Liangyan Ding
 */
public class UCS extends SearchAlgorithm{

    @Override
    double heuristic(int nodeRow, int nodeColumn, int goalRow, int goalColumn, int heuristicFunctionIndex, double weight, int type, GridPane GridMap) {
        return 0;
    }
}
