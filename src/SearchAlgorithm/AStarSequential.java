package SearchAlgorithm;

import controller.MainPageController;
import heuristic.*;
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
public class AStarSequential extends SearchAlgorithm {

    @Override
    public ArrayList<UCSNode> search(int sRow, int sColumn, GridPane GridMap, Text nodeNumText) {


        map = new int[120][160];
        ObservableList<Node> childrens = GridMap.getChildren();

        for (Node node : childrens) {
            map[GridMap.getRowIndex(node)][GridMap.getColumnIndex(node)] = (int)node.getUserData();
        }


        // TODO For sequential A*
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

        Hashtable<String, UCSNode> closed0 = new Hashtable<>();
        Hashtable<String, UCSNode> closed1 = new Hashtable<>();
        Hashtable<String, UCSNode> closed2 = new Hashtable<>();
        Hashtable<String, UCSNode> closed3 = new Hashtable<>();
        Hashtable<String, UCSNode> closed4 = new Hashtable<>();

        AStarFoundGoal = false;
        AStarGoalNode = null;
        ArrayList<UCSNode> AStarSearchResult = new ArrayList<>();


        // ----------------------------------------------------------
        // StartNode in anchor heuristic with h value 0
        UCSNode startNode0 = new UCSNode(sRow, sColumn, null, 0, 0);

        // second one with euclidean distance
        heuristicFunctionIndex = 1;
        UCSNode startNode1 = new UCSNode(sRow, sColumn, null, 0, heuristic(sRow, sColumn, goalRow, goalColumn, heuristicFunctionIndex, weight, type, GridMap));

        // third one with squared euclidean distance
        heuristicFunctionIndex = 2;
        UCSNode startNode2 = new UCSNode(sRow, sColumn, null, 0, heuristic(sRow, sColumn, goalRow, goalColumn, heuristicFunctionIndex, weight, type, GridMap));

        // fourth
        heuristicFunctionIndex = 3;
        UCSNode startNode3 = new UCSNode(sRow, sColumn, null, 0, heuristic(sRow, sColumn, goalRow, goalColumn, heuristicFunctionIndex, weight, type, GridMap));

        // fifth
        heuristicFunctionIndex = 4;
        UCSNode startNode4 = new UCSNode(sRow, sColumn, null, 0, heuristic(sRow, sColumn, goalRow, goalColumn, heuristicFunctionIndex, weight, type, GridMap));


        // put startNode in the openList
        // ---------------------------------------------------------
        open0.add(startNode0);
        open1.add(startNode1);
        open2.add(startNode2);
        open3.add(startNode3);
        open4.add(startNode4);

        open00.put(sRow + " " + sColumn, startNode0);
        open01.put(sRow + " " + sColumn, startNode1);
        open02.put(sRow + " " + sColumn, startNode2);
        open03.put(sRow + " " + sColumn, startNode3);
        open04.put(sRow + " " + sColumn, startNode4);


        // ----------------------------------------------------------------
        int i = 0;
        //when goal has not been found
        while (!AStarFoundGoal) {
            // compare the 4 other heuristic with anchor heuristic
            for (i = 1; i < 5; i++) {
                // add the smallest node of from each heuristic to an arraylist
                ArrayList<UCSNode> minF = new ArrayList<>();
                minF.add(open0.peek());
                minF.add(open1.peek());
                minF.add(open2.peek());
                minF.add(open3.peek());
                minF.add(open4.peek());

                // If the cheapest node in this heuristic is less than the cheapest node in the first heuristic
                if (minF.get(i).getF() <= w2 * (minF.get(0).getF())) {
                    // If the goal is found
                    if (minF.get(i).getRow() == goalRow && minF.get(i).getColumn() == goalColumn) {
                        AStarFoundGoal = true;
                        AStarGoalNode = minF.get(i);
                        break;
                    } else {
                        if (i == 1) {
                            UCSNode tmpNode = minF.get(i);
                            closed1.put(tmpNode.getRow() + " " + tmpNode.getColumn(), tmpNode);
                            open1.remove(tmpNode);
                            open01.remove(tmpNode.getRow() + " " + tmpNode.getColumn());
                            heuristicFunctionIndex = 1;
                            expandNodeForAStar(tmpNode, open1, closed1, open01, GridMap);
                        }
                        if (i == 2) {
                            UCSNode tmpNode = minF.get(i);
                            closed2.put(tmpNode.getRow() + " " + tmpNode.getColumn(), tmpNode);
                            open2.remove(tmpNode);
                            open02.remove(tmpNode.getRow() + " " + tmpNode.getColumn());
                            heuristicFunctionIndex = 2;
                            expandNodeForAStar(tmpNode, open2, closed2, open02, GridMap);
                        }
                        if (i == 3) {
                            UCSNode tmpNode = minF.get(i);
                            closed3.put(tmpNode.getRow() + " " + tmpNode.getColumn(), tmpNode);
                            open3.remove(tmpNode);
                            open03.remove(tmpNode.getRow() + " " + tmpNode.getColumn());
                            heuristicFunctionIndex = 3;
                            expandNodeForAStar(tmpNode, open3, closed3, open03, GridMap);
                        }
                        if (i == 4) {
                            UCSNode tmpNode = minF.get(i);
                            closed4.put(tmpNode.getRow() + " " + tmpNode.getColumn(), tmpNode);
                            open4.remove(tmpNode);
                            open04.remove(tmpNode.getRow() + " " + tmpNode.getColumn());
                            heuristicFunctionIndex = 4;
                            expandNodeForAStar(tmpNode, open4, closed4, open04, GridMap);
                        }
                    }

                } else if (minF.get(0).getRow() == goalRow && minF.get(0).getColumn() == goalColumn) {
                    AStarFoundGoal = true;
                    AStarGoalNode = minF.get(0);
                    break;
                } else {

                    UCSNode tmpNode = minF.get(0);
                    closed0.put(tmpNode.getRow() + " " + tmpNode.getColumn(), tmpNode);
                    open0.remove(tmpNode);
                    open00.remove(tmpNode.getRow() + " " + tmpNode.getColumn());
                    heuristicFunctionIndex = 0;
                    expandNodeForAStar(tmpNode, open0, closed0, open00, GridMap);
                }
            }

            // TODO TESTING
            System.out.println("closedTable0 size is " + closed0.size());
            System.out.println("closedTable1 size is " + closed1.size());
            System.out.println("closedTable2 size is " + closed2.size());
            System.out.println("closedTable3 size is " + closed3.size());
            System.out.println("closedTable4 size is " + closed4.size());
        }

        //found goal, get the result
        while (AStarGoalNode != null) {

            AStarSearchResult.add(AStarGoalNode);
            AStarGoalNode = AStarGoalNode.getParent();
        }

        int closedSize = closed0.size() + closed1.size()+ closed2.size()+ closed3.size()+ closed4.size();

        nodeNumText.setText("Number of nodes expanded - " + closedSize);
        System.out.println("Sequential Search Found goal");

        return AStarSearchResult;
    }

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
