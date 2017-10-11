# A* Algorithm
A* search algorithm &amp; its variances 

## Running the application
Create a JavaFX project in your IDE then replace the src folder with the one on this repo.

## Project Description
### 1. Setup
Consider the following problem: an agent operating in a grid-world has to quickly compute a path from the current cell it occupies to a target goal cell. Different cells in the grid have different costs for traversing them. For instance, some may correspond to impassable obstacles, others to flat and easy to traverse regions. There may also be passable but difficult to traverse regions (e.g., rocky, granular terrain or swamps) as well as regions that can accelerate the motion of a character (e.g., highways or navigable regions, etc.).
The characters need to quickly compute a path to their final destination and they have many different alternatives in terms of the type of terrain that they can go over. Following a longer path over an easier to traverse terrain may bring the agent faster to the target destination than a short path over a challenging terrain. The A  algorithm is mainly used in computing such paths. The original A  approach guides the search using an admissible and consistent heuristic. Such a heuristic guarantees that the path returned by A  will be optimal.

### 2. Description of Discretized Maps
* grid maps of dimension **160 columns and 120 rows**. 
* The cost of transitioning between two regular unblocked cells is 1 if the agent moves horizontally or vertically and sqrt(2) if the agent moves diagonally.
* with probability 50% to mark it as a hard to traverse cell. 
* The cost of transitioning into such hard to traverse cells is double the cost of moving over regular unblocked cells, i.e.,
* moving horizontally or vertically between two hard to traverse cells has a cost of 2;
* moving diagonally between two hard to traverse cells has a cost of sqrt(8);
* moving horizontally or vertically between a regular unblocked cell and a hard to traverse cell (in either direction) has a cost of 1.5;
* moving diagonally between a regular unblocked cell and a hard to traverse cell (in either direction) has a cost of [sqrt(2)+sqrt(8)] / 2;
* four paths on the map that allow the agent to move faster
* the cost of this motion is four times less than it would be otherwise (i.e., 0.25 if both cells are regular, 0.5 if both cells are hard to traverse and 0.375 if we are moving between a regular unblocked cell and a hard to traverse cell).
* select randomly 20% of the total number of cells (i.e., 3,840 cells) to mark as blocked. 

### 3. Review of A*
* S denotes the set of vertices.
* Sstart denotes the start vertex, and
* Sgoal denotes the goal vertex.
* c(s, s0 ) is the cost of transitioning between two neighboring vertices s, s0 2 S as defined in the previous section.
* Finally, succ(s) is the set of successors of vertex s, which are those (at most eight) vertices adjacent to vertex s so that the straight line between s and a vertex in succ(s) is unblocked.

```
Main()
  g(Sstart) := 0;
  parent(Sstart) := sstart;
  fringe := emptySet ;
  fringe.Insert(sstart, g(sstart) + h(sstart)); 
  closed := emptySet ;
  while fringe isNotEmpty do
    s := fringe.Pop();
    if s = sgoal then
      return “path found”;
    closed := closed union {s}; 
    foreach s0 in succ(s) do
      if s0 isNotIn closed then
        if s0 isNotIn fringe then
          g(s0) := infinity ;
          parent(s0 ) := NULL; 
        UpdateVertex(s, s0 );
  return “no path found”;
  
UpdateVertex(s,s’)
  if g(s) + c(s, s0 ) < g(s0 ) then
    g(s0 ) := g(s) + c(s, s0 ); 
    parent(s0 ) := s;
    if s0 2 fringe then 0
      fringe.Remove(s ); 
    fringe.Insert(s0 , g(s0 ) + h(s0 ));
```

A*  also maintains two global data structures:
1. First, the fringe (or open list) is a priority queue that contains the vertices that A  considers to expand. A vertex that is or was in the fringe is called generated. The fringe provides the following procedures:
* Procedure fringe.Insert(s,  ) inserts vertex s with key   into the priority queue fringe.
* Procedure fringe.Remove(s) removes vertex s from the priority queue fringe.
* Procedure fringe.Pop() removes a vertex with the smallest key from priority queue fringe and returns it.
2. Second, the closed list is a set that contains the vertices that A  has expanded and ensures that A  expands every vertex at most once.
A  uses a user-provided constant h-value (= heuristic value) h(s) for every vertex s 2 S to focus the search, which is an estimate of the distance to the goal, i.e., an estimate of the distance from vertex s to the goal vertex. A  uses the h-value to calculate an ƒ -value ƒ (s) = g(s) + h(s) for every vertex s, which is an estimate of the distance from the start vertex via vertex s to the goal vertex. Upon initialization, A  assumes the g-value of every vertex to be infinity and the parent of every vertex to NULL [Lines 15-16]. It sets the g-value of the start vertex to zero and the parent of the start vertex to itself [Lines 2-3]. It sets the fringe and closed lists to the empty lists and then inserts the start vertex into the fringe list with its ƒ-value as its priority [4-6]. A  then repeatedly executes the following statements: If the fringe list is empty, then A  reports that there is no path [Line 18]. Otherwise, it identifies a vertex s with the smallest ƒ-value in the fringe list [Line 8]. If this vertex is the goal vertex, then A  reports that it has found a path from the start vertex to the goal vertex [Line 10]. A  then follows the parents from the goal vertex to the start vertex to identify a path from the start vertex to the goal vertex in reverse [not shown in the pseudo-code]. Otherwise, A  removes the vertex from the fringe list [Line 8] and expands it by inserting the vertex into the closed list [Line 11] and then generating each of its unexpanded successors, as follows: A  checks whether the g-value of vertex s plus the straight-line distance from vertex s to vertex s0 is smaller than g-value of vertex s0 [Line 20]. If so, then it sets the g-value of vertex s0 to the g-value of vertex s plus the straight-line distance from vertex s to vertex s0, sets the parent of vertex s0 to vertex s and finally inserts vertex s0 into the fringe list with its ƒ-value as its priority or, if it was there already, changes its priority [Lines 21-25]. It then repeats the procedure. Thus, when A  updates the g-value and parent of an unexpanded successor s0 of vertex s in procedure
UpdateVertex, it considers the path from the start vertex to vertex s [= g(s)] and from vertex s to vertex s0 in a straight0line [= c(s, s0)], resulting in distance g(s) + c(s, s0). A  updates the g-value andparentofvertexs iftheconsideredpathisshorterthantheshortestpathfromthestartvertex to vertex s0 found so far [= g(s0)]. A  with admissible and consistent h-values is guaranteed to find shortest grid paths (optimality criterion). H-values are admissible and consistent (= monotone) if and only if (iff) they satisfy the triangle inequality, that is, iff h(sgoal) = 0 and h(s)  c(s, s0) + h(s0) for all vertices s, s0 2 S with s 6= sgoal and s0 2 succ(s). For example, h-values are consistent if they are all zero, in which case A  degrades to uniform-first search.

### Report
#### Optimization
We first tried to use ArrayList data structure for both closed lists and open lists, which ended up spending lots of times search for the nodes that we want. In order to make our program more efficient, we used two data structures for the Open List. A Priority Queue and a Hash Table. We used the Priority Queue for the O(1) lookup time for the smallest element in the Open List. We used the Hash Table for the O(1) lookup time for any specific object in the Open List. We used Hash Table for the Closed List for achieving O(1) lookup time when avoiding expand the same node. The one that uses Priority Queue and Hash Table was expected to run much faster than using just ArrayList, yet as a result, though it did improve the overall performance, the overall computing time could still go up to minutes depending on algorithm. 

The relative performance among different methods are quite substantial. UCS and Integrated took much longer time than A*, while A* weighted, sequential, and integrated highly relies on the given weight values. Comparing to UCS, which is the slowest but guaranteed to have the optimal solution, depending on the weight values, sequential and integrated provide close-to-optimal solution with a shorter computing time. This is because these two methods are constantly checking between several heuristic methods and the optimal UCS. 

