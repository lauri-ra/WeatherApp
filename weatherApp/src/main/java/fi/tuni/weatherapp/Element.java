package fi.tuni.weatherapp;

import java.util.ArrayList;
import javafx.scene.Node;

public class Element {
    private ArrayList<Node> _nodes;
    
    /**
     * Constructor
     */
    public Element() {
        this.setNodes(new ArrayList<>());
    }
    
    /**
     * Returns the element's nodes.
     * @return _nodes
     */
    public ArrayList<Node> getNodes() {
        return this._nodes;
    }
    
    public void setNodes(ArrayList<Node> nodes) {
        this._nodes = nodes;
    }
}
