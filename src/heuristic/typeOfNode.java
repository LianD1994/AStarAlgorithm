package heuristic;

import controller.MainPageController;

/**
 * @author Liangyan Ding
 */
public class typeOfNode extends heuristic{


    @Override
    public double calculateHeuristic(int nodeRow, int nodeColumn) {
        if(MainPageController.type == 0){
            return 4;
        }else if(MainPageController.type == 1){
            return 8;
        }else if(MainPageController.type == 2){
            return 1;
        }else{
            return 2;
        }
    }
}
