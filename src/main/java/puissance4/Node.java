package puissance4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.SimpleTimeZone;

public class Node {
    private int player ; // joueur qui a joué pour arriver ici
    private Action action ; // coup joué par ce joueur pour arriver ici

    private State state ; // // etat du jeu actuel
    private Node parent;
    private ArrayList<Node> children = new ArrayList<>();

    private double nbVictories;
    private int nbSimulations;


    public Node(Node parent, Action action) {

        if(parent != null && action != null){
            this.state = parent.getState().cloneState();
            this.action = action;
            this.state.playAnAction(action);
            this.player = parent.otherPlayer();
        }else {
            this.state = null;
            this.action = null;
            this.player = 0;
        }
        this.parent = parent;
        this.nbVictories = 0;
        this.nbSimulations = 0;
    }

    public static Node racineNode(State state){
        Node racine = new Node(null,null);
        racine.state = state.cloneState();
        return racine;
    }

    public Node addChild(Action action){
        Node child = new Node(this,action);
        this.children.add(child);
        return child;
    }

    public int getNbChildren(){
        return this.children.size();
    }

    public State getState() {
        return state;
    }

    public int  otherPlayer(){
        return  1-this.player;
    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("lui     \n");

        str.append(getState());
        for (Node node : this.children) {
            str.append(" child     \n");
            str.append(node.toString());
        }
        return str.toString();
    }

    public Node getParent() {
        return parent;
    }

    public double getNbVictories() {
        return nbVictories;
    }

    public int getNbSimulations() {
        return nbSimulations;
    }

    public int getPlayer() {
        return player;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Node> children) {
        this.children = children;
    }

    public void updateNbSimulations(){
        this.nbSimulations =  this.nbSimulations+1;
    }

    public void updateNbVictories(double value){
        this.nbVictories = this.nbVictories + value;
    }

    public Action getAction() {
        return action;
    }
}
