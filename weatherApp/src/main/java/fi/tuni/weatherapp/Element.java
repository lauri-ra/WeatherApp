package fi.tuni.weatherapp;

import java.util.ArrayList;
import javafx.scene.Node;

public class Element {
    private EventListener _listener;
    private ArrayList<Node> _nodes;
    
    /**
     * Constructor
     */
    public Element() {
        this.setNodes(new ArrayList<>());
    }
    
    /**
     * Returns event listener.
     * @return 
     */
    public EventListener getListener() {
        return this._listener;
    }
    
    /**
     * Sets event listener.
     * @param listener 
     */
    public void setListener(EventListener listener) {
        this._listener = listener;
    }
    
    /**
     * Returns element nodes.
     * @return _nodes
     */
    public ArrayList<Node> getNodes() {
        return this._nodes;
    }
    
    /**
     * Sets nodes.
     * @param nodes 
     */
    public void setNodes(ArrayList<Node> nodes) {
        this._nodes = nodes;
    }
}
