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
public class AStarIntegrated extends SearchAlgorithm{

    @Override
    public ArrayList<UCSNode> search(int sRow, int sColumn, GridPane GridMap, Text nodeNumText) {

        map = new int[120][160];
        ObservableList<Node> childrens = GridMap.getChildren();

        for (Node node : childrens) {
            map[GridMap.getRowIndex(node)][GridMap.getColumnIndex(node)] = (int)node.getUserData();
        }

            open0.clear();
            open1.clear();
            open2.clear();
            open3.clear();
            open4.clear();

            open00.clear();
            open01.clear();
            open02.clear();
            open03.clear();
            open04.clear();

            closedAnchor.clear();
            closedInad.clear();

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


            // Expand each startNode
            // ---------------------------------------------------------
            open0.add(startNode0);
            open1.add(startNode1);
            open2.add(startNode2);
            open3.add(startNode3);
            open4.add(startNode4);

            open00.put(sRow+" "+sColumn, startNode0);
            open01.put(sRow+" "+sColumn, startNode1);
            open02.put(sRow+" "+sColumn, startNode2);
            open03.put(sRow+" "+sColumn, startNode3);
            open04.put(sRow+" "+sColumn, startNode4);


            // ----------------------------------------------------------------
            int i;
            //when goal has not been found
            while(!AStarFoundGoal){
                // compare the 4 other heuristic with anchor heuristic
                for(i=1; i<5; i++){
                    // add the smallest node of from each heuristic to an arraylist
                    ArrayList<UCSNode> minF = new ArrayList<>();
                    minF.add(open0.peek());
                    minF.add(open1.peek());
                    minF.add(open2.peek());
                    minF.add(open3.peek());
                    minF.add(open4.peek());

                    // If the cheapest node in this heuristic is less than the cheapest node in the first heuristic
                    if(minF.get(i).getF() <= w2*(minF.get(0).getF())){
                        // If the goal is found
                        if(minF.get(i).getRow() == goalRow && minF.get(i).getColumn() == goalColumn) {
                            AStarFoundGoal = true;
                            AStarGoalNode = minF.get(i);
                            break;
                        }else{
                            if(i == 1){
                                UCSNode tmpNode = minF.get(i);
                                closedInad.put(tmpNode.getRow() + " " + tmpNode.getColumn(), tmpNode);
                                open0.remove(tmpNode);
                                open00.remove(tmpNode.getRow()+" "+tmpNode.getColumn());
                                open1.remove(tmpNode);
                                open01.remove(tmpNode.getRow()+" "+tmpNode.getColumn());
                                open2.remove(tmpNode);
                                open02.remove(tmpNode.getRow()+" "+tmpNode.getColumn());
                                open3.remove(tmpNode);
                                open03.remove(tmpNode.getRow()+" "+tmpNode.getColumn());
                                open4.remove(tmpNode);
                                open04.remove(tmpNode.getRow()+" "+tmpNode.getColumn());
                                heuristicFunctionIndex = 1;
                                expandNodeForIntegratedAStar(tmpNode, GridMap);
                            }
                            if(i == 2){
                                UCSNode tmpNode = minF.get(i);
                                closedInad.put(tmpNode.getRow() + " " + tmpNode.getColumn(), tmpNode);
                                open0.remove(tmpNode);
                                open00.remove(tmpNode.getRow()+" "+tmpNode.getColumn());
                                open1.remove(tmpNode);
                                open01.remove(tmpNode.getRow()+" "+tmpNode.getColumn());
                                open2.remove(tmpNode);
                                open02.remove(tmpNode.getRow()+" "+tmpNode.getColumn());
                                open3.remove(tmpNode);
                                open03.remove(tmpNode.getRow()+" "+tmpNode.getColumn());
                                open4.remove(tmpNode);
                                open04.remove(tmpNode.getRow()+" "+tmpNode.getColumn());
                                heuristicFunctionIndex = 2;
                                expandNodeForIntegratedAStar(tmpNode, GridMap);
                            }
                            if(i == 3) {
                                UCSNode tmpNode = minF.get(i);
                                closedInad.put(tmpNode.getRow() + " " + tmpNode.getColumn(), tmpNode);
                                open0.remove(tmpNode);
                                open00.remove(tmpNode.getRow()+" "+tmpNode.getColumn());
                                open1.remove(tmpNode);
                                open01.remove(tmpNode.getRow()+" "+tmpNode.getColumn());
                                open2.remove(tmpNode);
                                open02.remove(tmpNode.getRow()+" "+tmpNode.getColumn());
                                open3.remove(tmpNode);
                                open03.remove(tmpNode.getRow()+" "+tmpNode.getColumn());
                                open4.remove(tmpNode);
                                open04.remove(tmpNode.getRow()+" "+tmpNode.getColumn());
                                heuristicFunctionIndex = 3;
                                expandNodeForIntegratedAStar(tmpNode, GridMap);
                            }
                            if(i == 4) {
                                UCSNode tmpNode = minF.get(i);
                                closedInad.put(tmpNode.getRow() + " " + tmpNode.getColumn(), tmpNode);
                                open0.remove(tmpNode);
                                open00.remove(tmpNode.getRow()+" "+tmpNode.getColumn());
                                open1.remove(tmpNode);
                                open01.remove(tmpNode.getRow()+" "+tmpNode.getColumn());
                                open2.remove(tmpNode);
                                open02.remove(tmpNode.getRow()+" "+tmpNode.getColumn());
                                open3.remove(tmpNode);
                                open03.remove(tmpNode.getRow()+" "+tmpNode.getColumn());
                                open4.remove(tmpNode);
                                open04.remove(tmpNode.getRow()+" "+tmpNode.getColumn());
                                heuristicFunctionIndex = 4;
                                expandNodeForIntegratedAStar(tmpNode, GridMap);
                            }
                        }

                    }else if(minF.get(0).getRow() == goalRow && minF.get(0).getColumn() == goalColumn){
                        AStarFoundGoal = true;
                        AStarGoalNode = minF.get(0);
                        break;
                    }else{

                        UCSNode tmpNode = minF.get(0);
                        closedAnchor.put(tmpNode.getRow() + " " + tmpNode.getColumn(), tmpNode);
                        open0.remove(tmpNode);
                        open00.remove(tmpNode.getRow()+" "+tmpNode.getColumn());
                        open1.remove(tmpNode);
                        open01.remove(tmpNode.getRow()+" "+tmpNode.getColumn());
                        open2.remove(tmpNode);
                        open02.remove(tmpNode.getRow()+" "+tmpNode.getColumn());
                        open3.remove(tmpNode);
                        open03.remove(tmpNode.getRow()+" "+tmpNode.getColumn());
                        open4.remove(tmpNode);
                        open04.remove(tmpNode.getRow()+" "+tmpNode.getColumn());
                        heuristicFunctionIndex = 0;
                        expandNodeForIntegratedAStar(tmpNode, GridMap);
                    }
                }

                // TODO TESTING
                System.out.println("closedAnchor size is " + closedAnchor.size());
                System.out.println("closedInad size is " + closedInad.size());

            }

            //found goal, get the result
            while(AStarGoalNode != null){

                AStarSearchResult.add(AStarGoalNode);
                AStarGoalNode = AStarGoalNode.getParent();
            }

            System.out.println("Integrated Search Found goal");

        int closedSize = closedAnchor.size() + closedInad.size();
        nodeNumText.setText("Number of nodes expanded - " + closedSize);
        return AStarSearchResult;

    }

    // SECTION ADD NODE TO INTEGRATED LIST
    // ====================================================================
    public void addNodeToIntegratedList(UCSNode parentNode, int nodeRow, int nodeColumn, GridPane GridMap){

        if((int)SearchAlgorithm.getNode(nodeRow, nodeColumn) != 4){

            // This node is just for comparing, using row and column
            // other fields will not be used
            UCSNode compareNode = new UCSNode(nodeRow, nodeColumn, null, 0, 0);

            // if the node is already in closedList, do nothing
            // Used hashtable for O(1) lookup
            // -------------------------------------------------------
            if(closedAnchor.contains(compareNode)){
                return;
            }

            // if the node is in the anchor openList, update the parent
            // Used hashtable for O(1) lookup
            // --------------------------------------------------------
            heuristicFunctionIndex = 0;
            InsertOrUpdate(open0, open00, compareNode, parentNode, nodeRow, nodeColumn, GridMap);

            if( ! closedInad.contains(compareNode)){

                for(int i=1;i<5;i++) {

                    if(i == 1){
                        heuristicFunctionIndex = 1;
                        InsertOrUpdate(open1, open01, compareNode, parentNode, nodeRow, nodeColumn, GridMap);
                    }

                    if(i == 2){
                        heuristicFunctionIndex = 2;
                        InsertOrUpdate(open2, open02, compareNode, parentNode, nodeRow, nodeColumn, GridMap);
                    }

                    if(i == 3){
                        heuristicFunctionIndex = 3;
                        InsertOrUpdate(open3, open03, compareNode, parentNode, nodeRow, nodeColumn, GridMap);
                    }

                    if(i == 4){
                        heuristicFunctionIndex = 4;
                        InsertOrUpdate(open4, open04, compareNode, parentNode, nodeRow, nodeColumn, GridMap);
                    }
                }
            }
        }
    }

    // SECTION INSERT OR UPDATE
    // ====================================================
    private void InsertOrUpdate(PriorityQueue<UCSNode> openQueue, Hashtable<String, UCSNode> openTable, UCSNode compareNode, UCSNode parentNode, int nodeRow, int nodeColumn, GridPane GridMap){

        // If node is in the openList, update

        UCSNode node = openTable.get(nodeRow + " " + nodeColumn);

        UCSNode node0 = open00.get(nodeRow + " " + nodeColumn);
        UCSNode node1 = open01.get(nodeRow + " " + nodeColumn);
        UCSNode node2 = open02.get(nodeRow + " " + nodeColumn);
        UCSNode node3 = open03.get(nodeRow + " " + nodeColumn);
        UCSNode node4 = open04.get(nodeRow + " " + nodeColumn);

        UCSNode anchorNode = open00.get(nodeRow + " " + nodeColumn);

        // the global g value
        double v = parentNode.getCost()
                + costBetweenTwoCells(parentNode.getRow(), parentNode.getColumn(), nodeRow, nodeColumn, GridMap);

        // if trying to operate on the anchor list
        // DO NOT need to check condition key(s', i) <= w2* key(s', 0)
        // -----------------------------------------------------------
        if(openTable == open00){
            if(open00.contains(compareNode)){
                if (v < node.getCost()) {
                    // set new cost
                    node0.setCost(v);
                    // set new parent
                    node0.setParent(parentNode);
                    // cannot directly set attribute in Queue, thus create a new one and delete the old one
                    open0.remove(node0);
                    open0.add(node0);

                    if(open01.contains(compareNode)){
                        // set new cost
                        node1.setCost(v);
                        // set new parent
                        node1.setParent(parentNode);
                        // cannot directly set attribute in Queue, thus create a new one and delete the old one
                        open1.remove(node1);
                        open1.add(node1);
                    }else{
                        // if node is not in the openList, Insert
                        heuristicFunctionIndex = 1;
                        node = new UCSNode(nodeRow, nodeColumn, parentNode, v, heuristic(nodeRow, nodeColumn, goalRow, goalColumn, heuristicFunctionIndex, weight, type, GridMap));
                        open1.add(node);
                        open01.put(nodeRow + " " + nodeColumn, node);
                    }

                    if(open02.contains(compareNode)){
                        // set new cost
                        node2.setCost(v);
                        // set new parent
                        node2.setParent(parentNode);
                        // cannot directly set attribute in Queue, thus create a new one and delete the old one
                        open2.remove(node2);
                        open2.add(node2);
                    }else{
                        // if node is not in the openList, Insert
                        heuristicFunctionIndex = 2;
                        node = new UCSNode(nodeRow, nodeColumn, parentNode, v, heuristic(nodeRow, nodeColumn, goalRow, goalColumn, heuristicFunctionIndex, weight, type, GridMap));
                        open2.add(node);
                        open02.put(nodeRow + " " + nodeColumn, node);
                    }

                    if(open03.contains(compareNode)){
                        // set new cost
                        node3.setCost(v);
                        // set new parent
                        node3.setParent(parentNode);
                        // cannot directly set attribute in Queue, thus create a new one and delete the old one
                        open3.remove(node3);
                        open3.add(node3);
                    }else{
                        // if node is not in the openList, Insert
                        heuristicFunctionIndex = 3;
                        node = new UCSNode(nodeRow, nodeColumn, parentNode, v, heuristic(nodeRow, nodeColumn, goalRow, goalColumn, heuristicFunctionIndex, weight, type, GridMap));
                        open3.add(node);
                        open03.put(nodeRow + " " + nodeColumn, node);
                    }

                    if(open04.contains(compareNode)){
                        // set new cost
                        node4.setCost(v);
                        // set new parent
                        node4.setParent(parentNode);
                        // cannot directly set attribute in Queue, thus create a new one and delete the old one
                        open4.remove(node4);
                        open4.add(node4);
                    }else{
                        // if node is not in the openList, Insert
                        heuristicFunctionIndex = 4;
                        node = new UCSNode(nodeRow, nodeColumn, parentNode, v, heuristic(nodeRow, nodeColumn, goalRow, goalColumn, heuristicFunctionIndex, weight, type, GridMap));
                        open4.add(node);
                        open04.put(nodeRow + " " + nodeColumn, node);
                    }
                }

            }else{
                // if node is not in the openList, Insert
                heuristicFunctionIndex = 0;
                node = new UCSNode(nodeRow, nodeColumn, parentNode, v, heuristic(nodeRow, nodeColumn, goalRow, goalColumn, heuristicFunctionIndex, weight, type, GridMap));
                open0.add(node);
                open00.put(nodeRow + " " + nodeColumn, node);

                if(open01.contains(compareNode)){
                    // set new cost
                    node1.setCost(v);
                    // set new parent
                    node1.setParent(parentNode);
                    // cannot directly set attribute in Queue, thus create a new one and delete the old one
                    open1.remove(node1);
                    open1.add(node1);
                }else{
                    // if node is not in the openList, Insert
                    heuristicFunctionIndex = 1;
                    node = new UCSNode(nodeRow, nodeColumn, parentNode, v, heuristic(nodeRow, nodeColumn, goalRow, goalColumn, heuristicFunctionIndex, weight, type, GridMap));
                    open1.add(node);
                    open01.put(nodeRow + " " + nodeColumn, node);
                }

                if(open02.contains(compareNode)){
                    // set new cost
                    node2.setCost(v);
                    // set new parent
                    node2.setParent(parentNode);
                    // cannot directly set attribute in Queue, thus create a new one and delete the old one
                    open2.remove(node2);
                    open2.add(node2);
                }else{
                    // if node is not in the openList, Insert
                    heuristicFunctionIndex = 2;
                    node = new UCSNode(nodeRow, nodeColumn, parentNode, v, heuristic(nodeRow, nodeColumn, goalRow, goalColumn, heuristicFunctionIndex, weight, type, GridMap));
                    open2.add(node);
                    open02.put(nodeRow + " " + nodeColumn, node);
                }

                if(open03.contains(compareNode)){
                    // set new cost
                    node3.setCost(v);
                    // set new parent
                    node3.setParent(parentNode);
                    // cannot directly set attribute in Queue, thus create a new one and delete the old one
                    open3.remove(node3);
                    open3.add(node3);
                }else{
                    // if node is not in the openList, Insert
                    heuristicFunctionIndex = 3;
                    node = new UCSNode(nodeRow, nodeColumn, parentNode, v, heuristic(nodeRow, nodeColumn, goalRow, goalColumn, heuristicFunctionIndex, weight, type, GridMap));
                    open3.add(node);
                    open03.put(nodeRow + " " + nodeColumn, node);
                }

                if(open04.contains(compareNode)){
                    // set new cost
                    node4.setCost(v);
                    // set new parent
                    node4.setParent(parentNode);
                    // cannot directly set attribute in Queue, thus create a new one and delete the old one
                    open4.remove(node4);
                    open4.add(node4);
                }else{
                    // if node is not in the openList, Insert
                    heuristicFunctionIndex = 4;
                    node = new UCSNode(nodeRow, nodeColumn, parentNode, v, heuristic(nodeRow, nodeColumn, goalRow, goalColumn, heuristicFunctionIndex, weight, type, GridMap));
                    open4.add(node);
                    open04.put(nodeRow + " " + nodeColumn, node);
                }
            }


        }else{// if not operating on the anchor list
            // NEED TO CHECK node.getF() <= w2 * anchorNode.getF()
            // -------------------------------------------------------------------
            if(openTable.contains(compareNode)) {
                if (v < node.getCost()) {
                    // if condition is satisfied,
                    if (node.getF() <= w2 * anchorNode.getF()) {

                        // set new cost
                        node.setCost(v);
                        // set new parent
                        node.setParent(parentNode);
                        // cannot directly set attribute in Queue, thus create a new one and delete the old one
                        openQueue.remove(node);
                        openQueue.add(node);

                        if(open00.contains(compareNode)) {
                            // set new cost
                            node0.setCost(v);
                            // set new parent
                            node0.setParent(parentNode);
                            // cannot directly set attribute in Queue, thus create a new one and delete the old one
                            open0.remove(node0);
                            open0.add(node0);

                        }else{
                            // if node is not in the openList, Insert
                            heuristicFunctionIndex = 0;
                            node = new UCSNode(nodeRow, nodeColumn, parentNode, v, heuristic(nodeRow, nodeColumn, goalRow, goalColumn, heuristicFunctionIndex, weight, type, GridMap));
                            open0.add(node);
                            open00.put(nodeRow + " " + nodeColumn, node);
                        }

                        if(open01.contains(compareNode)) {
                            // set new cost
                            node1.setCost(v);
                            // set new parent
                            node1.setParent(parentNode);
                            // cannot directly set attribute in Queue, thus create a new one and delete the old one
                            open1.remove(node1);
                            open1.add(node1);

                        }else{
                            // if node is not in the openList, Insert
                            heuristicFunctionIndex = 1;
                            node = new UCSNode(nodeRow, nodeColumn, parentNode, v, heuristic(nodeRow, nodeColumn, goalRow, goalColumn, heuristicFunctionIndex, weight, type, GridMap));
                            open1.add(node);
                            open01.put(nodeRow + " " + nodeColumn, node);
                        }

                        if(open02.contains(compareNode)) {
                            // set new cost
                            node2.setCost(v);
                            // set new parent
                            node2.setParent(parentNode);
                            // cannot directly set attribute in Queue, thus create a new one and delete the old one
                            open2.remove(node2);
                            open2.add(node2);
                        }else{
                            // if node is not in the openList, Insert
                            heuristicFunctionIndex = 2;
                            node = new UCSNode(nodeRow, nodeColumn, parentNode, v, heuristic(nodeRow, nodeColumn, goalRow, goalColumn, heuristicFunctionIndex, weight, type, GridMap));
                            open2.add(node);
                            open02.put(nodeRow + " " + nodeColumn, node);
                        }

                        if(open03.contains(compareNode)) {
                            // set new cost
                            node3.setCost(v);
                            // set new parent
                            node3.setParent(parentNode);
                            // cannot directly set attribute in Queue, thus create a new one and delete the old one
                            open3.remove(node3);
                            open3.add(node3);
                        }else{
                            // if node is not in the openList, Insert
                            heuristicFunctionIndex = 3;
                            node = new UCSNode(nodeRow, nodeColumn, parentNode, v, heuristic(nodeRow, nodeColumn, goalRow, goalColumn, heuristicFunctionIndex, weight, type, GridMap));
                            open3.add(node);
                            open03.put(nodeRow + " " + nodeColumn, node);
                        }

                        if(open04.contains(compareNode)) {
                            // set new cost
                            node4.setCost(v);
                            // set new parent
                            node4.setParent(parentNode);
                            // cannot directly set attribute in Queue, thus create a new one and delete the old one
                            open4.remove(node4);
                            open4.add(node4);

                        }else{
                            // if node is not in the openList, Insert
                            heuristicFunctionIndex = 4;
                            node = new UCSNode(nodeRow, nodeColumn, parentNode, v, heuristic(nodeRow, nodeColumn, goalRow, goalColumn, heuristicFunctionIndex, weight, type, GridMap));
                            open4.add(node);
                            open04.put(nodeRow + " " + nodeColumn, node);
                        }
                    }
                }

            }else{
                // if node is not in the openList, Insert
                node = new UCSNode(nodeRow, nodeColumn, parentNode, v, heuristic(nodeRow, nodeColumn, goalRow, goalColumn, heuristicFunctionIndex, weight, type, GridMap));
                openQueue.add(node);
                openTable.put(nodeRow + " " + nodeColumn, node);

                if(open00.contains(compareNode)) {
                    // set new cost
                    node0.setCost(v);
                    // set new parent
                    node0.setParent(parentNode);
                    // cannot directly set attribute in Queue, thus create a new one and delete the old one
                    open0.remove(node0);
                    open0.add(node0);

                }else{
                    // if node is not in the openList, Insert
                    heuristicFunctionIndex = 0;
                    node = new UCSNode(nodeRow, nodeColumn, parentNode, v, heuristic(nodeRow, nodeColumn, goalRow, goalColumn, heuristicFunctionIndex, weight, type, GridMap));
                    open0.add(node);
                    open00.put(nodeRow + " " + nodeColumn, node);
                }

                if(open01.contains(compareNode)) {
                    // set new cost
                    node1.setCost(v);
                    // set new parent
                    node1.setParent(parentNode);
                    // cannot directly set attribute in Queue, thus create a new one and delete the old one
                    open1.remove(node1);
                    open1.add(node1);

                }else{
                    // if node is not in the openList, Insert
                    heuristicFunctionIndex = 1;
                    node = new UCSNode(nodeRow, nodeColumn, parentNode, v, heuristic(nodeRow, nodeColumn, goalRow, goalColumn, heuristicFunctionIndex, weight, type, GridMap));
                    open1.add(node);
                    open01.put(nodeRow + " " + nodeColumn, node);
                }

                if(open02.contains(compareNode)) {
                    // set new cost
                    node2.setCost(v);
                    // set new parent
                    node2.setParent(parentNode);
                    // cannot directly set attribute in Queue, thus create a new one and delete the old one
                    open2.remove(node2);
                    open2.add(node2);
                }else{
                    // if node is not in the openList, Insert
                    heuristicFunctionIndex = 2;
                    node = new UCSNode(nodeRow, nodeColumn, parentNode, v, heuristic(nodeRow, nodeColumn, goalRow, goalColumn, heuristicFunctionIndex, weight, type, GridMap));
                    open2.add(node);
                    open02.put(nodeRow + " " + nodeColumn, node);
                }

                if(open03.contains(compareNode)) {
                    // set new cost
                    node3.setCost(v);
                    // set new parent
                    node3.setParent(parentNode);
                    // cannot directly set attribute in Queue, thus create a new one and delete the old one
                    open3.remove(node3);
                    open3.add(node3);
                }else{
                    // if node is not in the openList, Insert
                    heuristicFunctionIndex = 3;
                    node = new UCSNode(nodeRow, nodeColumn, parentNode, v, heuristic(nodeRow, nodeColumn, goalRow, goalColumn, heuristicFunctionIndex, weight, type, GridMap));
                    open3.add(node);
                    open03.put(nodeRow + " " + nodeColumn, node);
                }

                if(open04.contains(compareNode)) {
                    // set new cost
                    node4.setCost(v);
                    // set new parent
                    node4.setParent(parentNode);
                    // cannot directly set attribute in Queue, thus create a new one and delete the old one
                    open4.remove(node4);
                    open4.add(node4);

                }else{
                    // if node is not in the openList, Insert
                    heuristicFunctionIndex = 4;
                    node = new UCSNode(nodeRow, nodeColumn, parentNode, v, heuristic(nodeRow, nodeColumn, goalRow, goalColumn, heuristicFunctionIndex, weight, type, GridMap));
                    open4.add(node);
                    open04.put(nodeRow + " " + nodeColumn, node);
                }
            }
        }
    }

    // SECTION EXPAND NODE FOR INTEGRATED ASTAR
    // =======================================================================
    public void expandNodeForIntegratedAStar(UCSNode parentNode, GridPane GridMap){

        int r = parentNode.getRow();
        int c = parentNode.getColumn();
        int tempR;
        int tempC;
        //working on here

        tempR = r-1; //up
        tempC = c;
        if(tempR >= 0) {
            addNodeToIntegratedList(parentNode, tempR, tempC, GridMap);
        }

        tempR = r-1; //up left
        tempC = c-1;
        if(tempR >= 0 && tempC >= 0) {
            addNodeToIntegratedList(parentNode, tempR, tempC, GridMap);
        }

        tempR = r; //left
        tempC = c-1;
        if(tempC >= 0) {
            addNodeToIntegratedList(parentNode, tempR, tempC, GridMap);
        }

        tempR = r+1; //down left
        tempC = c-1;
        if( tempC >= 0 && tempR <= 119) {
            addNodeToIntegratedList(parentNode, tempR, tempC, GridMap);
        }

        tempR = r+1; //down
        tempC = c;
        if(tempR <= 119) {
            addNodeToIntegratedList(parentNode, tempR, tempC, GridMap);
        }

        tempR = r-1; //up right
        tempC = c+1;
        if(tempR >= 0 && tempC <= 159) {
            addNodeToIntegratedList(parentNode, tempR, tempC, GridMap);
        }

        tempR = r; //right
        tempC = c+1;
        if(tempC <= 159) {
            addNodeToIntegratedList(parentNode, tempR, tempC, GridMap);
        }

        tempR = r+1; //down right
        tempC = c+1;
        if(tempR <= 119 && tempC <= 159) {
            addNodeToIntegratedList(parentNode, tempR, tempC, GridMap);
        }
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
