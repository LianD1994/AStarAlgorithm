package SearchAlgorithm;

import controller.MainPageController;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import node.UCSNode;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.PriorityQueue;

import static controller.MainPageController.*;

/**
 * @author Liangyan Ding
 */
public abstract class SearchAlgorithm {

    UCSNode startNode = null;
    boolean AStarFoundGoal = false;
    UCSNode AStarGoalNode;
    static int[][] map;

    Hashtable<String, UCSNode> closedAnchor = new Hashtable<>();
    Hashtable<String, UCSNode> closedInad = new Hashtable<>();

    PriorityQueue<UCSNode> open0 = new PriorityQueue<>();
    PriorityQueue<UCSNode> open1 = new PriorityQueue<>();
    PriorityQueue<UCSNode> open2 = new PriorityQueue<>();
    PriorityQueue<UCSNode> open3 = new PriorityQueue<>();
    PriorityQueue<UCSNode> open4 = new PriorityQueue<>();

    Hashtable<String, UCSNode> open00 = new Hashtable<>();
    Hashtable<String, UCSNode> open01 = new Hashtable<>();
    Hashtable<String, UCSNode> open02 = new Hashtable<>();
    Hashtable<String, UCSNode> open03 = new Hashtable<>();
    Hashtable<String, UCSNode> open04 = new Hashtable<>();

    abstract double heuristic(int nodeRow, int nodeColumn, int goalRow, int goalColumn,
                              int heuristicFunctionIndex, double weight, int type, GridPane GridMap);

    public ArrayList<UCSNode> search(int sRow, int sColumn, GridPane GridMap, Text text) {

        map = new int[120][160];
        ObservableList<Node> childrens = GridMap.getChildren();

        for (Node node : childrens) {
            map[GridMap.getRowIndex(node)][GridMap.getColumnIndex(node)] = (int)node.getUserData();
        }

        Hashtable<String, UCSNode> closedTable = new Hashtable<>();
        Hashtable<String, UCSNode> openTable = new Hashtable<>();
        PriorityQueue<UCSNode> openQueue = new PriorityQueue<>();

        AStarFoundGoal = false;
        AStarGoalNode = null;
        ArrayList<UCSNode> AStarSearchResult = new ArrayList<>();

        // initialize startNode with h value (last parameter)
        startNode = new UCSNode(sRow, sColumn, null, 0, 0);

        closedTable.put(sRow + " " + sColumn, startNode);

        expandNodeForAStar(startNode, openQueue, closedTable, openTable, GridMap);

        //when goal has not been found
        while(!AStarFoundGoal){

            // get the node with smallest F value in openList
            UCSNode tmp = openQueue.peek();

            // if the node with smallest F is Goal, path finding is finished
            if(tmp.getRow() == goalRow && tmp.getColumn() == goalColumn){
                AStarFoundGoal = true;
                AStarGoalNode = tmp;
            }

            // remove it from the openList, add it to the closedList
            closedTable.put(tmp.getRow() + " " + tmp.getColumn(), tmp);
            openQueue.remove(tmp);
            openTable.remove(tmp.getRow() + " " + tmp.getColumn());

            // expand the node with smallest F value until goal node expansion
            if(!AStarFoundGoal){
                expandNodeForAStar(tmp, openQueue, closedTable, openTable, GridMap);
            }

        }

        //found goal
        while(AStarGoalNode != null){

            AStarSearchResult.add(AStarGoalNode);
            AStarGoalNode = AStarGoalNode.getParent();
        }

        System.out.println("Found goal");
        text.setText("Number of nodes expanded  - " + closedTable.size());
        System.out.println("Number of nodes expanded  - " + closedTable.size());
        return AStarSearchResult;
    }

    // SECTION Expand Node For Astar
    // =============================================================================
    public void expandNodeForAStar(UCSNode parentNode, PriorityQueue openQueue, Hashtable closedList, Hashtable openTable, GridPane GridMap){

        int r = parentNode.getRow();
        int c = parentNode.getColumn();
        int tempR;
        int tempC;
        //working on here

        tempR = r-1; //up
        tempC = c;
        if(tempR >= 0) {
            addNodeToAStarList(parentNode, tempR, tempC, openQueue, closedList, openTable, GridMap);
        }

        tempR = r-1; //up left
        tempC = c-1;
        if(tempR >= 0 && tempC >= 0) {
            addNodeToAStarList(parentNode, tempR, tempC, openQueue, closedList, openTable, GridMap);
        }

        tempR = r; //left
        tempC = c-1;
        if(tempC >= 0) {
            addNodeToAStarList(parentNode, tempR, tempC, openQueue, closedList, openTable, GridMap);
        }

        tempR = r+1; //down left
        tempC = c-1;
        if( tempC >= 0 && tempR <= 119) {
            addNodeToAStarList(parentNode, tempR, tempC, openQueue, closedList, openTable, GridMap);
        }

        tempR = r+1; //down
        tempC = c;
        if(tempR <= 119) {
            addNodeToAStarList(parentNode, tempR, tempC, openQueue, closedList, openTable, GridMap);
        }

        tempR = r-1; //up right
        tempC = c+1;
        if(tempR >= 0 && tempC <= 159) {
            addNodeToAStarList(parentNode, tempR, tempC, openQueue, closedList, openTable, GridMap);
        }

        tempR = r; //right
        tempC = c+1;
        if(tempC <= 159) {
            addNodeToAStarList(parentNode, tempR, tempC, openQueue, closedList, openTable, GridMap);
        }

        tempR = r+1; //down right
        tempC = c+1;
        if(tempR <= 119 && tempC <= 159) {
            addNodeToAStarList(parentNode, tempR, tempC, openQueue, closedList, openTable, GridMap);
        }
    }


    public void addNodeToAStarList(UCSNode parentNode, int nodeRow, int nodeColumn, PriorityQueue openQueue, Hashtable closedTable, Hashtable openTable, GridPane GridMap){
        if(getNode(nodeRow, nodeColumn) != 4){

            // This node is just for comparing, using row and column
            // other fields will not be used
            UCSNode compareNode = new UCSNode(nodeRow, nodeColumn, null, 0, 0);

            // if the node is already in closedList, do nothing
            // Used hashtable for O(1) lookup
            // -------------------------------------------------------
            if(closedTable.contains(compareNode)){
                return;
            }

            // if the node is in the openList, update the parent
            // Used hashtable for O(1) lookup
            // --------------------------------------------------------
            if(openTable.contains(compareNode)){

                UCSNode node = (UCSNode) openTable.get(nodeRow + " " + nodeColumn);

                // shorter distance found, update parent
                if (parentNode.getCost()
                        + costBetweenTwoCells(parentNode.getRow(), parentNode.getColumn(), nodeRow, nodeColumn, GridMap)
                        < node.getCost()) {
                    // set new cost
                    node.setCost(parentNode.getCost()
                            + costBetweenTwoCells(parentNode.getRow(), parentNode.getColumn(), nodeRow, nodeColumn, GridMap));
                    // set new parent
                    node.setParent(parentNode);
                    // cannot directly set attribute in Queue, thus create a new one and delete the old one
                    openQueue.remove(node);
                    openQueue.add(node);

                    // just updating the node, no need to modify openTable

                    return;
                }

                return;
            }

            // if node is not in openList or closedList, create a new node and add it to the openList
            // ----------------------------------------------------------------------------
            //g value
            double cost = costBetweenTwoCells(parentNode.getRow(), parentNode.getColumn(), nodeRow, nodeColumn, GridMap);
            double cumulatedCost = parentNode.getCost() + cost;
            UCSNode node = new UCSNode(nodeRow, nodeColumn, parentNode, cumulatedCost, heuristic(nodeRow, nodeColumn, goalRow, goalColumn, MainPageController.heuristicFunctionIndex, weight, type, GridMap));
            openQueue.add(node);
            openTable.put(nodeRow + " " + nodeColumn, node);
        }
    }

    // SECTION cost between two nodes
    // =============================================
    public double costBetweenTwoCells(int c1Row, int c1Column, int c2Row, int c2Column, GridPane GridMap){

        int c1Type;
        int c2Type;
        if(c1Row == startRow && c1Column == startColumn){
            c1Type = startBaseType;
        }else{
            c1Type = getNode(c1Row, c1Column);
        }
        if(c2Row == goalRow && c2Column == goalColumn){
            c2Type = goalBaseType;
        }else{
            c2Type = getNode(c2Row, c2Column);
        }
        if(c1Row == c2Row || c1Column == c2Column){
            if(c1Type == 0 && c2Type == 0){ //both same
                return 1;
            }
            if(c1Type == 1 && c2Type == 1){
                return 2;
            }
            if(c1Type == 2 && c2Type == 2){
                return 0.25;
            }
            if(c1Type == 3 && c2Type == 3){
                return 0.5;
            }
            if((c1Type == 0 && c2Type == 1) || (c1Type == 1 && c2Type == 0)){ //0 and something else
                return 1.5;
            }
            if((c1Type == 0 && c2Type == 2) || (c1Type == 2 && c2Type == 0)){
                return 0.625;
            }
            if((c1Type == 0 && c2Type == 3) || (c1Type == 3 && c2Type == 0)){
                return 0.75;
            }
            if((c1Type == 1 && c2Type == 2) || (c1Type == 2 && c2Type == 1)){ //1 and something else
                return 1.125;
            }
            if((c1Type == 1 && c2Type == 3) || (c1Type == 3 && c2Type == 1)){
                return 1.25;
            }
            if((c1Type == 2 && c2Type == 3) || (c1Type == 3 && c2Type == 2)){ //2 and something else
                return 0.375;
            }

        }else{
            if(c1Type == 0 && c2Type == 0){ //both same
                return Math.sqrt(2);
            }
            if(c1Type == 1 && c2Type == 1){
                return 2*Math.sqrt(2);
            }
            if(c1Type == 2 && c2Type == 2){
                return Math.sqrt(2)/4.0;
            }
            if(c1Type == 3 && c2Type == 3){
                return Math.sqrt(2)/2.0;
            }
            if((c1Type == 0 && c2Type == 1) || (c1Type == 1 && c2Type == 0)){ //0 and something else
                return 1.5*Math.sqrt(2);
            }
            if((c1Type == 0 && c2Type == 2) || (c1Type == 2 && c2Type == 0)){
                return 5.0/8.0*Math.sqrt(2);
            }
            if((c1Type == 0 && c2Type == 3) || (c1Type == 3 && c2Type == 0)){
                return 3.0/4.0*Math.sqrt(2);
            }
            if((c1Type == 1 && c2Type == 2) || (c1Type == 2 && c2Type == 1)){ //1 and something else
                return 9.0/8.0*Math.sqrt(2);
            }
            if((c1Type == 1 && c2Type == 3) || (c1Type == 3 && c2Type == 1)){
                return 5.0/4.0*Math.sqrt(2);
            }
            if((c1Type == 2 && c2Type == 3) || (c1Type == 3 && c2Type == 2)){ //2 and something else
                return 3.0/8.0*Math.sqrt(2);
            }
        }
        return -1;
    }


    public static int getNode(int row, int column) {
        return map[row][column];
    }
}
