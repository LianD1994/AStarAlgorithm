package node;

public class UCSNode implements Comparable<UCSNode> {
	
	int row, column;
	UCSNode parent;
	double cost;
	double f;
	
	public UCSNode(int row, int column, UCSNode parent, double cost, double h){
		this.row = row;
		this.column = column;
		this.parent = parent;
		this.cost = cost;
		this.f = cost + h;
	}
	
	public void setParent(UCSNode parent){
		this.parent = parent;
	}
	
	public UCSNode getParent(){
		return this.parent;
	}
	
	public void setCost(double cost){
		this.cost = cost;
	}
	
	public int getRow(){
		return this.row;
	}
	
	public int getColumn(){
		return this.column;
	}
	
	public double getCost(){
		return this.cost;
	}

	public double getF() { return this.f; }

	@Override
	public boolean equals(Object o){
		UCSNode node = (UCSNode) o;
		return this.getRow() == node.getRow() && this.getColumn() == node.getColumn();
	}

	@Override
	public int compareTo(UCSNode o) {
		double cost1 = this.getF();
		double cost2 = o.getF();

		if(cost1 > cost2){
			return 1;
		}

		if(cost1 < cost2){
			return -1;
		}

		return 0;

	}
}
