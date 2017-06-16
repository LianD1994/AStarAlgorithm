package controller;

import SearchAlgorithm.*;
import heuristic.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import node.UCSNode;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class MainPageController {

    public static int startRow; //these are used to restore blocks replaced by start and goal
    public static int startColumn;
    public static int goalRow;
    public static int goalColumn;
    public static int startBaseType;
    public static int goalBaseType;
    public static int heuristicFunctionIndex = -1;
    public static double weight = 0;
    public static double w2 = 0;

    public static int type;
    static int startType;
    static int goalType;

    @FXML
    TextField weightInput;
    @FXML
    TextField weight2Input, indexInput;
    @FXML
    GridPane GridMap;
    @FXML
    GridPane startsAndGoals;
    @FXML
    Button CreateButton;
    @FXML
    Text rowText, columnText, fText, gText, hText, nodeNumText, lengthText, timeText, displayInfo;

    boolean mapExist = false;
    boolean startExist = false;
    boolean previousStartExist = false;
    boolean pathExist = false;
    ArrayList<Integer> startRows = new ArrayList<>();
    ArrayList<Integer> startColumns = new ArrayList<>();
    ArrayList<Integer> goalRows = new ArrayList<>();
    ArrayList<Integer> goalColumns = new ArrayList<>();
    ArrayList<Pane> nodesToBeDeleted = new ArrayList<>();
    Random rnd = new Random();

    // used for program execution time
    long startTime = 0;
    long endTime = 0;

    @FXML
    void OnSequentialButtonClicked() {
        // record start time
        startTime = System.currentTimeMillis();

        if (!startExist) {
            displayInfo.setText("Create start and goal first");
            System.out.println("Create start and goal first");
            return;
        }

        if (!isDouble(weightInput.getText())) {
            displayInfo.setText("Weight has to be a double input");
            System.out.println("Weight has to be a double input");
            return;
        }

        weight = Double.parseDouble(weightInput.getText());

        if (!isDouble(weight2Input.getText())) {
            displayInfo.setText("Weight2 has to be a double input");
            System.out.println("Weight2 has to be a double input");
            return;
        }

        w2 = Double.parseDouble(weight2Input.getText());

        if (pathExist) {
            int counter = 0;
            while (counter < nodesToBeDeleted.size()) {
                GridMap.getChildren().remove(nodesToBeDeleted.get(counter));
                counter++;
            }
            pathExist = false;
            nodesToBeDeleted.clear();
        }

        AStarSequential sequential = new AStarSequential();
        ArrayList<UCSNode> result = sequential.search(startRow, startColumn, GridMap, nodeNumText);

        nodesToBeDeleted.clear();
        int counter = 0;
        while (counter < result.size()) {
            int row = result.get(counter).getRow();
            int column = result.get(counter).getColumn();
            double g = result.get(counter).getCost();
            double h = heuristic(result.get(counter).getRow(), result.get(counter).getColumn(), goalRow, goalColumn);
            double f = g + h;
            Pane pane = new Pane();
            pane.setStyle("-fx-background-color: #00FF00; -fx-border-color: black; -fx-border-width:0.1;");
            pane.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {//event when node is clicked
                        rowText.setText("Row: " + row);
                        columnText.setText("Column: " + column);
                        gText.setText("g: " + g);
                        hText.setText("h: " + h);
                        fText.setText("f: " + f);
                        System.out.println("type: " + getNodeByRowColumnIndex(row, column, GridMap).getUserData());
                    }
            ); //eventhandler ends here
            GridMap.add(pane, result.get(counter).getColumn(), result.get(counter).getRow());
            nodesToBeDeleted.add(pane);
            counter++;
        }

        // record end time
        endTime = System.currentTimeMillis();

        lengthText.setText("Path length - " + result.get(0).getCost());
        timeText.setText("Total execution time - " + (endTime - startTime) + "ms");

        System.out.println("Path length - " + result.get(0).getCost());
        System.out.println("Total execution time: " + (endTime - startTime) + "ms");

        pathExist = true;
        heuristicFunctionIndex = -1;
    }

    @FXML
    void OnIntegratedButtonClicked() {
        // record start time
        startTime = System.currentTimeMillis();

        if (!startExist) {
            displayInfo.setText("Create start and goal first");
            System.out.println("Create start and goal first");
            return;
        }

        if (!isDouble(weightInput.getText())) {
            displayInfo.setText("Weight has to be a double input");
            System.out.println("Weight has to be a double input");
            return;
        }

        weight = Double.parseDouble(weightInput.getText());

        if (!isDouble(weight2Input.getText())) {
            displayInfo.setText("Weight2 has to be a double input");
            System.out.println("Weight2 has to be a double input");
            return;
        }

        w2 = Double.parseDouble(weight2Input.getText());

        if (pathExist) {
            int counter = 0;
            while (counter < nodesToBeDeleted.size()) {
                GridMap.getChildren().remove(nodesToBeDeleted.get(counter));
                counter++;
            }
            pathExist = false;
            nodesToBeDeleted.clear();
        }

        AStarIntegrated integrated = new AStarIntegrated();
        ArrayList<UCSNode> result = integrated.search(startRow, startColumn, GridMap, nodeNumText);

        nodesToBeDeleted.clear();
        int counter = 0;
        while (counter < result.size()) {
            int row = result.get(counter).getRow();
            int column = result.get(counter).getColumn();
            double g = result.get(counter).getCost();
            double h = heuristic(result.get(counter).getRow(), result.get(counter).getColumn(), goalRow, goalColumn);
            double f = g + h;
            Pane pane = new Pane();
            pane.setStyle("-fx-background-color: #FF0000; -fx-border-color: black; -fx-border-width:0.1;");
            pane.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {//event when node is clicked
                        rowText.setText("Row: " + row);
                        columnText.setText("Column: " + column);
                        gText.setText("g: " + g);
                        hText.setText("h: " + h);
                        fText.setText("f: " + f);
                        System.out.println("type: " + getNodeByRowColumnIndex(row, column, GridMap).getUserData());
                    }
            ); //eventhandler ends here
            GridMap.add(pane, result.get(counter).getColumn(), result.get(counter).getRow());
            nodesToBeDeleted.add(pane);
            counter++;
        }

        // record end time
        endTime = System.currentTimeMillis();

        lengthText.setText("Path length - " + result.get(0).getCost());
        timeText.setText("Total execution time - " + (endTime - startTime) + "ms");

        System.out.println("Path length - " + result.get(0).getCost());
        System.out.println("Total execution time: " + (endTime - startTime) + "ms");

        pathExist = true;
        heuristicFunctionIndex = -1;
    }

    @FXML
    void OnUCSButtonClicked() {
        // record start time
        startTime = System.currentTimeMillis();
        heuristicFunctionIndex = 0;

        if (!startExist) {
            System.out.println("Create start and goal first");
            heuristicFunctionIndex = -1;
            return;
        }

        if (pathExist) {
            int counter = 0;
            while (counter < nodesToBeDeleted.size()) {
                GridMap.getChildren().remove(nodesToBeDeleted.get(counter));
                counter++;
            }
            pathExist = false;
            nodesToBeDeleted.clear();
        }

        ArrayList<UCSNode> result;

        UCS ucs = new UCS();
        result = ucs.search(startRow, startColumn, GridMap, nodeNumText);

        nodesToBeDeleted.clear();
        int counter = 0;
        while (counter < result.size()) {
            int row = result.get(counter).getRow();
            int column = result.get(counter).getColumn();
            double g = result.get(counter).getCost();
            double h = 0;
            double f = g + h;
            Pane pane = new Pane();
            pane.setStyle("-fx-background-color: #CC00CC; -fx-border-color: black; -fx-border-width:0.1;");
            pane.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {//event when node is clicked
                        rowText.setText("Row: " + row);
                        columnText.setText("Column: " + column);
                        gText.setText("g: " + g);
                        hText.setText("h: " + h);
                        fText.setText("f: " + f);
                        System.out.println("type: " + getNodeByRowColumnIndex(row, column, GridMap).getUserData());
                    }
            ); //eventhandler ends here
            GridMap.add(pane, result.get(counter).getColumn(), result.get(counter).getRow());
            nodesToBeDeleted.add(pane);
            counter++;
        }

        // record end time
        endTime = System.currentTimeMillis();

        lengthText.setText("Path length - " + result.get(0).getCost());
        timeText.setText("Total execution time - " + (endTime - startTime) + "ms");

        System.out.println("Path length - " + result.get(0).getCost());
        System.out.println("Total execution time: " + (endTime - startTime) + "ms");

        pathExist = true;
        heuristicFunctionIndex = -1;
    }

    @FXML
    void OnAStarButtonClicked() {
        // record start time
        startTime = System.currentTimeMillis();

        if (!startExist) {
            System.out.println("Create start and goal first");
            return;
        }

        if (heuristicFunctionIndex == -1) {
            displayInfo.setText("Please choose a heuristic function first");
            System.out.println("Please choose a heuristic function first");
            return;
        }

        weight = 1;

        if (pathExist) {
            int counter = 0;
            while (counter < nodesToBeDeleted.size()) {
                GridMap.getChildren().remove(nodesToBeDeleted.get(counter));
                counter++;
            }
            pathExist = false;
            nodesToBeDeleted.clear();
        }

        ArrayList<UCSNode> result;
        AStar astar = new AStar();
        result = astar.search(startRow, startColumn, GridMap, nodeNumText);

        nodesToBeDeleted.clear();
        int counter = 0;
        while (counter < result.size()) {
            int row = result.get(counter).getRow();
            int column = result.get(counter).getColumn();
            double g = result.get(counter).getCost();
            double h = heuristic(result.get(counter).getRow(), result.get(counter).getColumn(), goalRow, goalColumn);
            double f = g + h;
            Pane pane = new Pane();
            pane.setStyle("-fx-background-color: #FF8000; -fx-border-color: black; -fx-border-width:0.1;");
            pane.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {//event when node is clicked
                        rowText.setText("Row: " + row);
                        columnText.setText("Column: " + column);
                        gText.setText("g: " + g);
                        hText.setText("h: " + h);
                        fText.setText("f: " + f);
                        System.out.println("type: " + getNodeByRowColumnIndex(row, column, GridMap).getUserData());
                    }
            ); //eventhandler ends here
            GridMap.add(pane, result.get(counter).getColumn(), result.get(counter).getRow());
            nodesToBeDeleted.add(pane);
            counter++;
        }

        // record end time
        endTime = System.currentTimeMillis();

        lengthText.setText("Path length - " + result.get(0).getCost());
        timeText.setText("Total execution time - " + (endTime - startTime) + "ms");

        System.out.println("Path length - " + result.get(0).getCost());
        System.out.println("Total execution time: " + (endTime - startTime) + "ms");
        pathExist = true;
        heuristicFunctionIndex = -1;
    }

    @FXML
    void OnAStarWeightedButtonClicked() {
        // record start time
        startTime = System.currentTimeMillis();

        if (!startExist) {
            System.out.println("Create start and goal first");
            return;
        }

        if (heuristicFunctionIndex == -1) {
            displayInfo.setText("Please choose a heuristic function first");
            System.out.println("Please choose a heuristic function first");
            return;
        }

        if (!isDouble(weightInput.getText())) {
            System.out.println("Weight has to be a double input");
            return;
        }

        weight = Double.parseDouble(weightInput.getText());

        if (pathExist) {
            int counter = 0;
            while (counter < nodesToBeDeleted.size()) {
                GridMap.getChildren().remove(nodesToBeDeleted.get(counter));
                counter++;
            }
            pathExist = false;
            nodesToBeDeleted.clear();
        }

        AStarWeighted astarw = new AStarWeighted();
        ArrayList<UCSNode> result = astarw.search(startRow, startColumn, GridMap, nodeNumText);

        nodesToBeDeleted.clear();
        int counter = 0;
        while (counter < result.size()) {
            int row = result.get(counter).getRow();
            int column = result.get(counter).getColumn();
            double g = result.get(counter).getCost();
            double h = heuristic(result.get(counter).getRow(), result.get(counter).getColumn(), goalRow, goalColumn);
            double f = g + h;
            Pane pane = new Pane();
            pane.setStyle("-fx-background-color: #FF8000; -fx-border-color: black; -fx-border-width:0.1;");
            pane.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {//event when node is clicked
                        rowText.setText("Row: " + row);
                        columnText.setText("Column: " + column);
                        gText.setText("g: " + g);
                        hText.setText("h: " + h);
                        fText.setText("f: " + f);
                        System.out.println("type: " + getNodeByRowColumnIndex(row, column, GridMap).getUserData());
                    }
            ); //eventhandler ends here
            GridMap.add(pane, result.get(counter).getColumn(), result.get(counter).getRow());
            nodesToBeDeleted.add(pane);
            counter++;
        }

        // record end time
        endTime = System.currentTimeMillis();

        lengthText.setText("Path length - " + result.get(0).getCost());
        timeText.setText("Total execution time: " + (endTime - startTime) + "ms");

        System.out.println("Path length - " + result.get(0).getCost());
        System.out.println("Total execution time: " + (endTime - startTime) + "ms");

        pathExist = true;
        heuristicFunctionIndex = -1;
    }

    @FXML
    void OnSaveMapButtonClicked() throws IOException { //working, save map

        System.out.println("Start saving map");

        //check if a map has been generated
        if (!mapExist) {
            System.out.println("There's no map to save");
            return;
        }

        //get input for file name
        System.out.println("Please type file name to save");
        Scanner userInput = new Scanner(System.in);
        String input = userInput.nextLine();
        if (input.isEmpty()) {
            System.out.println("Empty string, file doesn't exist");
            return;
        }

        //save the map into arraylist
        ArrayList<Integer> saveFile = new ArrayList<>();
        int counter = 0;
        while (counter < 19200) {
            int r = counter / 160;
            int c = counter % 160;
            int type = (int) getNodeByRowColumnIndex(r, c, GridMap).getUserData();
            System.out.println(r + "," + c);
            saveFile.add(counter, type);
            counter++;
        }

        //save file as user named it
        FileOutputStream fos = new FileOutputStream(input);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(saveFile);
        oos.close();

        System.out.println("Finish saving map");
    }

    @FXML
    void OnLoadMapButtonClicked() throws ClassNotFoundException, IOException { //debug

        System.out.println("Start loading map");

        //get input for file name
        System.out.println("Please type file name to read");
        Scanner userInput = new Scanner(System.in);
        String input = userInput.nextLine();
        if (input.isEmpty()) {
            System.out.println("Empty string, file doesn't exist");
            ;
            return;
        }

        //read file into arraylist
        ArrayList<Integer> savedMap;
        FileInputStream fis = new FileInputStream(input);
        ObjectInputStream ois = new ObjectInputStream(fis);
        savedMap = (ArrayList<Integer>) ois.readObject();
        ois.close();

        //fill in startAndGoals gridpane
        for (int c = 1; c < 11; c++) {
            final int temp = c - 1;
            String a = Integer.toString(c);
            Text text = new Text("   " + a + "  ");
            text.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {//event when node is clicked
                        if (startExist) { //starts have been created
                            if (pathExist) {
                                int counter = 0;
                                while (counter < nodesToBeDeleted.size()) {
                                    GridMap.getChildren().remove(nodesToBeDeleted.get(counter));
                                    counter++;
                                }
                                pathExist = false;
                                nodesToBeDeleted.clear();
                            }
                            if (previousStartExist) { //delete previous one and add new ones

                                //delete previous start and replace it with what it was
                                GridMap.getChildren().remove(getNodeByRowColumnIndex(startRow, startColumn, GridMap));
                                Pane rsPane = new Pane();
                                rsPane.setUserData(startType);
                                if (startType == 0) {
                                    rsPane.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width:0.1;");
                                } else if (startType == 1) {
                                    rsPane.setStyle("-fx-background-color: #C0C0C0; -fx-border-color: black; -fx-border-width:0.1;");
                                } else if (startType == 2 || startType == 3) {
                                    rsPane.setStyle("-fx-background-color: #0080FF; -fx-border-color: black; -fx-border-width:0.1;");
                                } else if (startType == 5) {
                                    rsPane.setStyle("-fx-background-color: #00FF00; -fx-border-color: black; -fx-border-width:0.1;");
                                } else if (startType == 6) {
                                    rsPane.setStyle("-fx-background-color: #FF0000; -fx-border-color: black; -fx-border-width:0.1;");
                                }
                                GridMap.add(rsPane, startColumn, startRow);

                                //start
                                Pane sPane = new Pane();
                                sPane.setUserData(5); //5 and green for start
                                sPane.setStyle("-fx-background-color: #00FF00; -fx-border-color: black; -fx-border-width:0.1;");
                                int sc = startColumns.get(temp);
                                int sr = startRows.get(temp);
                                startRow = sr;
                                startColumn = sc;
                                startType = (int) getNodeByRowColumnIndex(sr, sc, GridMap).getUserData();
                                GridMap.getChildren().remove(getNodeByRowColumnIndex(sr, sc, GridMap));
                                GridMap.add(sPane, sc, sr);

                                //delete previous goal and replace it with what it was
                                GridMap.getChildren().remove(getNodeByRowColumnIndex(goalRow, goalColumn, GridMap));
                                Pane gsPane = new Pane();
                                gsPane.setUserData(goalType);
                                if (goalType == 0) {
                                    gsPane.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width:0.1;");
                                } else if (goalType == 1) {
                                    gsPane.setStyle("-fx-background-color: #C0C0C0; -fx-border-color: black; -fx-border-width:0.1;");
                                } else if (goalType == 2 || goalType == 3) {
                                    gsPane.setStyle("-fx-background-color: #0080FF; -fx-border-color: black; -fx-border-width:0.1;");
                                } else if (goalType == 5) {
                                    gsPane.setStyle("-fx-background-color: #00FF00; -fx-border-color: black; -fx-border-width:0.1;");
                                } else if (goalType == 6) {
                                    gsPane.setStyle("-fx-background-color: #FF0000; -fx-border-color: black; -fx-border-width:0.1;");
                                }
                                GridMap.add(gsPane, goalColumn, goalRow);

                                //goal
                                Pane gPane = new Pane();
                                gPane.setUserData(6); //6 and red for goal
                                gPane.setStyle("-fx-background-color: #FF0000; -fx-border-color: black; -fx-border-width:0.1;");
                                int gc = goalColumns.get(temp);
                                int gr = goalRows.get(temp);
                                goalRow = gr;
                                goalColumn = gc;
                                goalType = (int) getNodeByRowColumnIndex(gr, gc, GridMap).getUserData();
                                GridMap.getChildren().remove(getNodeByRowColumnIndex(gr, gc, GridMap));
                                GridMap.add(gPane, gc, gr);

                                System.out.println("Clicked");

                            } else { //add new ones

                                //start
                                Pane sPane = new Pane();
                                sPane.setUserData(5); //5 for start
                                sPane.setStyle("-fx-background-color: #00FF00; -fx-border-color: black; -fx-border-width:0.1;");
                                int sc = startColumns.get(temp);
                                int sr = startRows.get(temp);
                                startRow = sr;
                                startColumn = sc;
                                startType = (int) getNodeByRowColumnIndex(sr, sc, GridMap).getUserData();
                                GridMap.getChildren().remove(getNodeByRowColumnIndex(sr, sc, GridMap));
                                GridMap.add(sPane, sc, sr);

                                //goal
                                Pane gPane = new Pane();
                                gPane.setUserData(6); //6 for goal
                                gPane.setStyle("-fx-background-color: #FF0000; -fx-border-color: black; -fx-border-width:0.1;");
                                int gc = goalColumns.get(temp);
                                int gr = goalRows.get(temp);
                                goalRow = gr;
                                goalColumn = gc;
                                goalType = (int) getNodeByRowColumnIndex(gr, gc, GridMap).getUserData();
                                GridMap.getChildren().remove(getNodeByRowColumnIndex(gr, gc, GridMap));
                                GridMap.add(gPane, gc, gr);

                                previousStartExist = true;
                                System.out.println("Clicked");
                            }
                            System.out.println("Start:" + startRow + "," + startColumn);
                            System.out.println("Goal:" + goalRow + "," + goalColumn);
                        } else {
                            System.out.println("Please create starts and goals first");
                        }
                    }
            ); //eventhandler ends here

            startsAndGoals.add(text, c - 1, 0);
        }
        startsAndGoals.setAlignment(Pos.CENTER_RIGHT);

        //clear the map
        GridMap.getChildren().clear();
        //load the map from arraylist
        int counter = 0;
        while (counter < 19200) {
            int type = savedMap.get(counter);
            int r = counter / 160;
            int c = counter % 160;
            Pane pane = new Pane();
            if (type == 0) {
                pane.setUserData(0);
                pane.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width:0.1;");
            } else if (type == 1) {
                pane.setUserData(1);
                pane.setStyle("-fx-background-color: #C0C0C0; -fx-border-color: black; -fx-border-width:0.1;");
            } else if (type == 2) {
                pane.setUserData(2);
                pane.setStyle("-fx-background-color: #0080FF; -fx-border-color: black; -fx-border-width:0.1;");
            } else if (type == 3) {
                pane.setUserData(3);
                pane.setStyle("-fx-background-color: #0080FF; -fx-border-color: black; -fx-border-width:0.1;");
            } else if (type == 4) {
                pane.setUserData(4);
                pane.setStyle("-fx-background-color: #000000; -fx-border-color: black; -fx-border-width:0.1;");
            }
            GridMap.add(pane, c, r);
            System.out.println(r + "," + c);
            counter++;

        }

        mapExist = true;
        pathExist = false;
        System.out.println("Finish loading map");
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: #CCCCCC; -fx-border-color: black; -fx-border-width:0.1;");

        return;

    }

    //heuristic
    @FXML
    void OnChangeHeuristicButtonClicked() {

        displayInfo.setText("Please type index of heuristic function");
        System.out.println("Please type index of heuristic function");

        String input = indexInput.getText();

        if (!isInteger(input)) {
            displayInfo.setText("Input must be integer");
            System.out.println("Input must be integer");
            return;
        }

        int index = Integer.parseInt(input);
        if (index < 1 || index > 5) {
            displayInfo.setText("Input must be integer between 1 and 5");
            System.out.println("Input must be integer between 1 and 5");
            return;
        }

        heuristicFunctionIndex = index;

        displayInfo.setText("Successfully set heuristic function, index: " + index);
        System.out.println("Successfully set heuristic function, index: " + index);

    }

    @FXML
    void OnCreateStartButtonClicked() { //create start, goal sets

        //clear up previous starts and goals
        startRows.clear();
        startColumns.clear();
        goalRows.clear();
        goalColumns.clear();

        if (mapExist) { //check if the map is already generated
            System.out.println("Start building starts and goals");
            for (int a = 0; a < 10; a++) { //10 sets
                while (true) {
                    int c = rnd.nextInt(40); //left, right most 20
                    int r = rnd.nextInt(40); //up, down most 20
                    if (c >= 20) {
                        c = c + 120;
                    }
                    if (r >= 20) {
                        r = r + 80;
                    }
                    if ((int) getNodeByRowColumnIndex(r, c, GridMap).getUserData() == 4) {
                        continue;
                    } else {
                        startBaseType = (int) getNodeByRowColumnIndex(r, c, GridMap).getUserData();
                        startRows.add(r);
                        startColumns.add(c);
                        startRow = r;
                        startColumn = c;
                        break;
                    }
                }
                while (true) {
                    int c = rnd.nextInt(40);
                    int r = rnd.nextInt(40);
                    if (c >= 20) {
                        c = c + 120;
                    }
                    if (r >= 20) {
                        r = r + 80;
                    }
                    double distance = Math.sqrt((startRow - r) * (startRow - r) + (startColumn - c) * (startColumn - c));
                    if ((int) getNodeByRowColumnIndex(r, c, GridMap).getUserData() == 4) {
                        continue;
                    } else if (distance < 100) {
                        continue;
                    } else {
                        goalBaseType = (int) getNodeByRowColumnIndex(r, c, GridMap).getUserData();
                        goalRows.add(r);
                        goalColumns.add(c);
                        goalRow = r;
                        goalColumn = c;
                        break;
                    }
                }

            }

            //set startExist to true
            startExist = true;

            //set pathExist to false
            pathExist = false;

        } else { //if map has not been created yet
            System.out.println("Please create map first");
        }

    }

    @FXML
    void OnClearStartButtonClicked() {
        if (startRows.isEmpty()) {
            return;
        }
        //delete previous start and replace it with what it was
        GridMap.getChildren().remove(getNodeByRowColumnIndex(startRow, startColumn, GridMap));
        Pane rsPane = new Pane();
        rsPane.setUserData(startType);
        if (startType == 0) {
            rsPane.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width:0.1;");
        } else if (startType == 1) {
            rsPane.setStyle("-fx-background-color: #C0C0C0; -fx-border-color: black; -fx-border-width:0.1;");
        } else if (startType == 2 || startType == 3) {
            rsPane.setStyle("-fx-background-color: #0080FF; -fx-border-color: black; -fx-border-width:0.1;");
        } else if (startType == 5) {
            rsPane.setStyle("-fx-background-color: #00FF00; -fx-border-color: black; -fx-border-width:0.1;");
        } else if (startType == 6) {
            rsPane.setStyle("-fx-background-color: #FF0000; -fx-border-color: black; -fx-border-width:0.1;");
        }
        GridMap.add(rsPane, startColumn, startRow);

        //delete previous goal and replace it with what it was
        GridMap.getChildren().remove(getNodeByRowColumnIndex(goalRow, goalColumn, GridMap));
        Pane gsPane = new Pane();
        gsPane.setUserData(goalType);
        if (goalType == 0) {
            gsPane.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width:0.1;");
        } else if (goalType == 1) {
            gsPane.setStyle("-fx-background-color: #C0C0C0; -fx-border-color: black; -fx-border-width:0.1;");
        } else if (goalType == 2 || goalType == 3) {
            gsPane.setStyle("-fx-background-color: #0080FF; -fx-border-color: black; -fx-border-width:0.1;");
        } else if (goalType == 5) {
            gsPane.setStyle("-fx-background-color: #00FF00; -fx-border-color: black; -fx-border-width:0.1;");
        } else if (goalType == 6) {
            gsPane.setStyle("-fx-background-color: #FF0000; -fx-border-color: black; -fx-border-width:0.1;");
        }
        GridMap.add(gsPane, goalColumn, goalRow);

        startRows.clear();
        startColumns.clear();
        goalRows.clear();
        goalColumns.clear();
        startExist = false;
        pathExist = false;
        System.out.println("Starts and goals deleted");
    }

    @FXML
    void OnCreateButtonClicked() { //create map

        //clear
        GridMap.getChildren().clear();

        System.out.println("Start building map");

        //fill in startAndGoals gridpane
        for (int c = 1; c < 11; c++) {
            final int temp = c - 1;
            String a = Integer.toString(c);
            Text text = new Text(a);
            text.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {//event when node is clicked
                        if (startExist) { //starts have been created
                            if (previousStartExist) { //delete previous one and add new ones
                                if (pathExist) {
                                    int counter = 0;
                                    while (counter < nodesToBeDeleted.size()) {
                                        GridMap.getChildren().remove(nodesToBeDeleted.get(counter));
                                        counter++;
                                    }
                                    pathExist = false;
                                    nodesToBeDeleted.clear();
                                }
                                //delete previous start and replace it with what it was
                                GridMap.getChildren().remove(getNodeByRowColumnIndex(startRow, startColumn, GridMap));
                                Pane rsPane = new Pane();
                                rsPane.setUserData(startType);
                                if (startType == 0) {
                                    rsPane.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width:0.1;");
                                } else if (startType == 1) {
                                    rsPane.setStyle("-fx-background-color: #C0C0C0; -fx-border-color: black; -fx-border-width:0.1;");
                                } else if (startType == 2 || startType == 3) {
                                    rsPane.setStyle("-fx-background-color: #0080FF; -fx-border-color: black; -fx-border-width:0.1;");
                                } else if (startType == 5) {
                                    rsPane.setStyle("-fx-background-color: #00FF00; -fx-border-color: black; -fx-border-width:0.1;");
                                } else if (startType == 6) {
                                    rsPane.setStyle("-fx-background-color: #FF0000; -fx-border-color: black; -fx-border-width:0.1;");
                                }
                                GridMap.add(rsPane, startColumn, startRow);

                                //start
                                Pane sPane = new Pane();
                                sPane.setUserData(5); //5 and green for start
                                sPane.setStyle("-fx-background-color: #00FF00; -fx-border-color: black; -fx-border-width:0.1;");
                                int sc = startColumns.get(temp);
                                int sr = startRows.get(temp);
                                startRow = sr;
                                startColumn = sc;
                                startType = (int) getNodeByRowColumnIndex(sr, sc, GridMap).getUserData();
                                GridMap.getChildren().remove(getNodeByRowColumnIndex(sr, sc, GridMap));
                                GridMap.add(sPane, sc, sr);

                                //delete previous goal and replace it with what it was
                                GridMap.getChildren().remove(getNodeByRowColumnIndex(goalRow, goalColumn, GridMap));
                                Pane gsPane = new Pane();
                                gsPane.setUserData(goalType);
                                if (goalType == 0) {
                                    gsPane.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width:0.1;");
                                } else if (goalType == 1) {
                                    gsPane.setStyle("-fx-background-color: #C0C0C0; -fx-border-color: black; -fx-border-width:0.1;");
                                } else if (goalType == 2 || goalType == 3) {
                                    gsPane.setStyle("-fx-background-color: #0080FF; -fx-border-color: black; -fx-border-width:0.1;");
                                } else if (goalType == 5) {
                                    gsPane.setStyle("-fx-background-color: #00FF00; -fx-border-color: black; -fx-border-width:0.1;");
                                } else if (goalType == 6) {
                                    gsPane.setStyle("-fx-background-color: #FF0000; -fx-border-color: black; -fx-border-width:0.1;");
                                }
                                GridMap.add(gsPane, goalColumn, goalRow);

                                //goal
                                Pane gPane = new Pane();
                                gPane.setUserData(6); //6 and red for goal
                                gPane.setStyle("-fx-background-color: #FF0000; -fx-border-color: black; -fx-border-width:0.1;");
                                int gc = goalColumns.get(temp);
                                int gr = goalRows.get(temp);
                                goalRow = gr;
                                goalColumn = gc;
                                goalType = (int) getNodeByRowColumnIndex(gr, gc, GridMap).getUserData();
                                GridMap.getChildren().remove(getNodeByRowColumnIndex(gr, gc, GridMap));
                                GridMap.add(gPane, gc, gr);

                                System.out.println("Clicked");

                            } else { //add new ones

                                //start
                                Pane sPane = new Pane();
                                sPane.setUserData(5); //5 for start
                                sPane.setStyle("-fx-background-color: #00FF00; -fx-border-color: black; -fx-border-width:0.1;");
                                int sc = startColumns.get(temp);
                                int sr = startRows.get(temp);
                                startRow = sr;
                                startColumn = sc;
                                startType = (int) getNodeByRowColumnIndex(sr, sc, GridMap).getUserData();
                                GridMap.getChildren().remove(getNodeByRowColumnIndex(sr, sc, GridMap));
                                GridMap.add(sPane, sc, sr);

                                //goal
                                Pane gPane = new Pane();
                                gPane.setUserData(6); //6 for goal
                                gPane.setStyle("-fx-background-color: #FF0000; -fx-border-color: black; -fx-border-width:0.1;");
                                int gc = goalColumns.get(temp);
                                int gr = goalRows.get(temp);
                                goalRow = gr;
                                goalColumn = gc;
                                goalType = (int) getNodeByRowColumnIndex(gr, gc, GridMap).getUserData();
                                GridMap.getChildren().remove(getNodeByRowColumnIndex(gr, gc, GridMap));
                                GridMap.add(gPane, gc, gr);

                                previousStartExist = true;
                                System.out.println("Clicked");
                            }
                        } else {
                            System.out.println("Please create starts and goals first");
                        }
                    }
            ); //eventhandler ends here

            startsAndGoals.add(text, c - 1, 0);
        }
        startsAndGoals.setAlignment(Pos.CENTER_RIGHT);

        //fill in white cells
        for (int c = 0; c < 160; c++) {
            for (int r = 0; r < 120; r++) {
                Pane pane = new Pane();
                pane.setUserData(0); //0 for unblocked
                pane.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width:0.1;");
                GridMap.add(pane, c, r);
            }
        }

        //hard to traverse
        int[][] gray = new int[8][2];
        int i = 0;
        while (i < 8) {
            int x = rnd.nextInt(160);
            int y = rnd.nextInt(120);
            for (int j = 0; j < i; j++) {
                if (gray[j][0] == x && gray[j][1] == y) {
                    continue;
                }
            }
            gray[i][0] = x;
            gray[i][1] = y;
            i++;
        }

        int[][] boundries = new int[8][4];
        for (int j = 0; j < 8; j++) {
            if (gray[j][0] < 15) {
                boundries[j][0] = 0;
                boundries[j][1] = gray[j][0] + 15;
            } else if (gray[j][0] > 145) {
                boundries[j][0] = gray[j][0] - 15;
                boundries[j][1] = 160;
            } else {
                boundries[j][0] = gray[j][0] - 15;
                boundries[j][1] = gray[j][0] + 15;
            }
            if (gray[j][1] < 15) {
                boundries[j][2] = 0;
                boundries[j][3] = gray[j][1] + 15;
            } else if (gray[j][1] > 105) {
                boundries[j][2] = gray[j][1] - 15;
                boundries[j][3] = 120;
            } else {
                boundries[j][2] = gray[j][1] - 15;
                boundries[j][3] = gray[j][1] + 15;
            }
        }

        i = 0;
        while (i < 8) {
            for (int c = boundries[i][0]; c < boundries[i][1]; c++) {
                for (int r = boundries[i][2]; r < boundries[i][3]; r++) {
                    int random = rnd.nextInt(2);
                    if (random == 0) {
                        Pane pane = new Pane();
                        pane.setUserData(1); //1 for hard to traverse
                        pane.setStyle("-fx-background-color: #C0C0C0; -fx-border-color: black; -fx-border-width:0.1;");
                        GridMap.getChildren().remove(getNodeByRowColumnIndex(r, c, GridMap));
                        GridMap.add(pane, c, r);
                    }
                }
            }
            i++;
        }

        //highway, add retry times
        int counter = 0;

        System.out.println("Start building roads");
        for (int j = 0; j < 4; j++) {
            int x = rnd.nextInt(4);
            //1 up, 2 right, 3 down, 4 left
            if (x == 0) { //top
                int y = rnd.nextInt(160);
                if (counter > 10000) { //retry times
                    System.out.println("10000 retries failed, rebuild map and roads");
                    OnCreateButtonClicked();
                    return;
                }
                if (y == 0 || y == 159) {
                    j--;
                    counter++;
                    continue;
                }
                if (createRoads(0, y, 3)) {
                    continue;
                } else {
                    j--;
                    counter++;
                    continue;
                }
            } else if (x == 1) { //bottom
                int y = rnd.nextInt(160);
                if (y == 0 || y == 159) {
                    j--;
                    counter++;
                    continue;
                }
                if (createRoads(119, y, 1)) {
                    continue;
                } else {
                    j--;
                    counter++;
                    continue;
                }
            } else if (x == 2) { //left
                int y = rnd.nextInt(120);
                if (y == 0 || y == 119) {
                    j--;
                    counter++;
                    continue;
                }
                if (createRoads(y, 0, 2)) {
                    continue;
                } else {
                    j--;
                    counter++;
                    continue;
                }
            } else if (x == 3) { //right
                int y = rnd.nextInt(120);
                if (y == 0 || y == 119) {
                    j--;
                    counter++;
                    continue;
                }
                if (createRoads(y, 159, 4)) {
                    continue;
                } else {
                    j--;
                    counter++;
                    continue;
                }
            }
        }

        //create blocked cells
        for (int b = 0; b < 3840; b++) {
            int c = rnd.nextInt(160);
            int r = rnd.nextInt(120);
            if ((int) getNodeByRowColumnIndex(r, c, GridMap).getUserData() == 2 || (int) getNodeByRowColumnIndex(r, c, GridMap).getUserData() == 3) {
                b--;
                continue;
            } else {
                Pane pane = new Pane();
                pane.setUserData(4); //4 for blocked
                pane.setStyle("-fx-background-color: #000000; -fx-border-color: black; -fx-border-width:0.1;");
                GridMap.getChildren().remove(getNodeByRowColumnIndex(r, c, GridMap));
                GridMap.add(pane, c, r);
            }
        }

        mapExist = true;
        pathExist = false;


    } //end of create

    // SECTION GET NODE BY ROW COLUMN INDEX
    // ==============================================
    public Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if (gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }

    public int getTurn(int direction) {
        int turn1 = 2;
        int turn2 = 4;
        Random rnd = new Random();
        int prob = rnd.nextInt(10);
        if (direction == 1 || direction == 3) {
            if (prob < 6) {
                //direction doesnt change
                return direction;
            } else if (prob > 7) {
                return turn1;
            } else {
                return turn2;
            }
        } else if (direction == 2 || direction == 4) {
            turn1 = 1;
            turn2 = 3;
            if (prob < 6) {
                //direction doesnt change
                return direction;
            } else if (prob > 7) {
                return turn1;
            } else {
                return turn2;
            }
        }

        return -1;
    }

    public void deleteBadRoad(ArrayList<Integer> rows, ArrayList<Integer> columns, ArrayList<Integer> types) {
        for (int b = 0; b < rows.size(); b++) {
            if (types.get(b) == 0) {
                Pane pane = new Pane();
                pane.setUserData(0);
                pane.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: black; -fx-border-width:0.1;");
                GridMap.getChildren().remove(getNodeByRowColumnIndex(rows.get(b), columns.get(b), GridMap));
                GridMap.add(pane, columns.get(b), rows.get(b));
            } else if (types.get(b) == 1) {
                Pane pane = new Pane();
                pane.setUserData(1);
                pane.setStyle("-fx-background-color: #C0C0C0; -fx-border-color: black; -fx-border-width:0.1;");
                GridMap.getChildren().remove(getNodeByRowColumnIndex(rows.get(b), columns.get(b), GridMap));
                GridMap.add(pane, columns.get(b), rows.get(b));
            } else if (types.get(b) == 2) {
                Pane pane = new Pane();
                pane.setUserData(2);
                pane.setStyle("-fx-background-color: #0080FF; -fx-border-color: black; -fx-border-width:0.1;");
                GridMap.getChildren().remove(getNodeByRowColumnIndex(rows.get(b), columns.get(b), GridMap));
                GridMap.add(pane, columns.get(b), rows.get(b));
            } else if (types.get(b) == 3) {
                Pane pane = new Pane();
                pane.setUserData(3);
                pane.setStyle("-fx-background-color: #0080FF; -fx-border-color: black; -fx-border-width:0.1;");
                GridMap.getChildren().remove(getNodeByRowColumnIndex(rows.get(b), columns.get(b), GridMap));
                GridMap.add(pane, columns.get(b), rows.get(b));
            }
        }
        return;
    }

    public boolean createRoads(int rowGiven, int columnGiven, int directionGiven) {
        Random rnd = new Random();
        int x = rnd.nextInt(4);
        int row;
        int column;
        int direction;
        boolean good = true;
        int rowDirection = 0;
        int columnDirection = 0;
        ArrayList<Integer> columns = new ArrayList<>();
        ArrayList<Integer> rows = new ArrayList<>();
        ArrayList<Integer> types = new ArrayList<Integer>();
        row = rowGiven;
        column = columnGiven;
        direction = directionGiven;
        if ((int) getNodeByRowColumnIndex(row, column, GridMap).getUserData() != 2 && (int) getNodeByRowColumnIndex(row, column, GridMap).getUserData() != 3) {
            //start here//
            if (direction == 1) {
                rowDirection = -1;
                columnDirection = 0;
            } else if (direction == 2) {
                rowDirection = 0;
                columnDirection = 1;
            } else if (direction == 3) {
                rowDirection = 1;
                columnDirection = 0;
            } else if (direction == 4) {
                rowDirection = 0;
                columnDirection = -1;
            }
            //first 20
            for (int a = 0; a < 20; a++) {
                if ((int) getNodeByRowColumnIndex(row, column, GridMap).getUserData() != 2 && (int) getNodeByRowColumnIndex(row, column, GridMap).getUserData() != 3) {
                    columns.add(column);
                    rows.add(row);
                    int baseType = (int) getNodeByRowColumnIndex(row, column, GridMap).getUserData();
                    types.add(baseType);
                    Pane pane = new Pane();
                    if (baseType == 0) { //2 for roads on unblocked
                        pane.setUserData(2);
                    } else {
                        pane.setUserData(3); //3 for roads on hard to traverse
                    }
                    pane.setStyle("-fx-background-color: #0080FF; -fx-border-color: black; -fx-border-width:0.1;");
                    GridMap.getChildren().remove(getNodeByRowColumnIndex(row, column, GridMap));
                    GridMap.add(pane, column, row);
                } else {
                    good = false;
                    break;
                }
                row = row + rowDirection;
                column = column + columnDirection;
            }
            row = row - rowDirection;
            column = column - columnDirection;
            if (!good) {
                deleteBadRoad(rows, columns, types);
                return false;
            }

            //turns
            int a = 0;
            direction = getTurn(direction);
            if (direction == 1) {
                rowDirection = -1;
                columnDirection = 0;
            } else if (direction == 2) {
                rowDirection = 0;
                columnDirection = 1;
            } else if (direction == 3) {
                rowDirection = 1;
                columnDirection = 0;
            } else if (direction == 4) {
                rowDirection = 0;
                columnDirection = -1;
            }

            while (row > 0 && row < 119 && column > 0 && column < 159) {

                //update to the next block
                row = row + rowDirection;
                column = column + columnDirection;

                //check
                if ((int) getNodeByRowColumnIndex(row, column, GridMap).getUserData() != 2 && (int) getNodeByRowColumnIndex(row, column, GridMap).getUserData() != 3) {
                    columns.add(column);
                    rows.add(row);
                    int baseType = (int) getNodeByRowColumnIndex(row, column, GridMap).getUserData();
                    types.add(baseType);
                    Pane pane = new Pane();
                    if (baseType == 0) { //2 for roads on unblocked
                        pane.setUserData(2);
                    } else {
                        pane.setUserData(3); //3 for roads on hard to traverse
                    }
                    pane.setStyle("-fx-background-color: #0080FF; -fx-border-color: black; -fx-border-width:0.1;");
                    GridMap.getChildren().remove(getNodeByRowColumnIndex(row, column, GridMap));
                    GridMap.add(pane, column, row);
                } else {
                    good = false;
                    break;
                }

                a++;
                if (a == 20) { //20 cells
                    a = 0;
                    direction = getTurn(direction);
                    if (direction == 1) {
                        rowDirection = -1;
                        columnDirection = 0;
                    } else if (direction == 2) {
                        rowDirection = 0;
                        columnDirection = 1;
                    } else if (direction == 3) {
                        rowDirection = 1;
                        columnDirection = 0;
                    } else if (direction == 4) {
                        rowDirection = 0;
                        columnDirection = -1;
                    }
                }

            } //while
            if (!good) {
                deleteBadRoad(rows, columns, types);
                return false;
            }
            if (rows.size() < 100) { //check length here
                deleteBadRoad(rows, columns, types);
                return false;
            }
            System.out.println(rows.size());
            return true;

        } else {
            return false;
        }
    }

    // SECTION HEURISTIC
    public double heuristic(int nodeRow, int nodeColumn, int goalRow, int goalColumn) {
        // Euclidean distance
        if (heuristicFunctionIndex == -1) {
            System.out.println("Please choose a heuristic function first");
            return -1;
        }
        if (heuristicFunctionIndex == 0) {
            return 0;
        }
        if (heuristicFunctionIndex == 1) {
            euclidean euclidean = new euclidean();
            return weight * euclidean.calculateHeuristic(nodeRow, nodeColumn);
        }
        if (heuristicFunctionIndex == 2) {
            mahattanDistance manhattanDistance = new mahattanDistance();
            return weight * manhattanDistance.calculateHeuristic(nodeRow, nodeColumn);
        }
        if (heuristicFunctionIndex == 3) {
            squaredEuclidean squaredEuclidean = new squaredEuclidean();
            return weight * squaredEuclidean.calculateHeuristic(nodeRow, nodeColumn);
        }
        if (heuristicFunctionIndex == 4) {
            type = (int) getNodeByRowColumnIndex(nodeRow, nodeColumn, GridMap).getUserData();
            typeOfNode typeOfNode = new typeOfNode();
            return weight * typeOfNode.calculateHeuristic(nodeRow, nodeColumn);
        }
        if(heuristicFunctionIndex == 5){
            pointTwoFiveEuclidean Euclidean = new pointTwoFiveEuclidean();
            return weight * Euclidean.calculateHeuristic(nodeRow, nodeColumn);
        }

        return -1;
    }

    //for checking weight input for A* weighted
    public boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
}