package fi.tuni.weatherapp;

import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import javafx.scene.control.ScrollPane;

public final class View {
    private Stage _stage;
    private FlowPane _container;
    private ArrayList<Element> _elements;
    private TopMenu _topMenu;
    private Graph _graph;
    private BottomMenu _bottomMenu;
    
    /**
     * Constructor
     */
    public View() {
        this.setContainer(new FlowPane());
        this.setElements(new ArrayList());
        this._buildTopMenu();
        this._buildGraph();
        this._buildBottomMenu();
        this._buildContainer();
    }
    
    /**
     * Returns stage.
     * @return _stage 
     */
    public Stage getStage() {
        return this._stage;
    }
    
    /**
     * Sets stage.
     * @param stage 
     */
    public void setStage(Stage stage) {
        this._stage = stage;
    }
    
    /**
     * Returns container containing all elements and their containers.
     * @return _container 
     */
    public FlowPane getContainer() {
        return this._container;
    }
    
    /**
     * Sets container.
     * @param container 
     */
    public void setContainer(FlowPane container) {
        this._container = container;
    }
    
    /**
     * Returns list of elements.
     * @return _elements 
     */
    public ArrayList<Element> getElements() {
       return this._elements;
    }
    
    /**
     * Sets elements.
     * @param elements 
     */
    public void setElements(ArrayList<Element> elements) {
        this._elements = elements;
    }
    
    /**
     * Returns top menu view.
     * @return _topMenu
     */
    public TopMenu getTopMenu() {
        return this._topMenu;
    }
    
    /**
     * Sets top menu view.
     * @param view 
     */
    public void setTopMenu(TopMenu view) {
        this._topMenu = view;
    }
    
    /**
     * Returns graph view.
     * @return _graph
     */
    public Graph getGraph() {
        return this._graph;
    }
    
    /**
     * Sets graph view.
     * @param view 
     */
    public void setGraph(Graph view) {
        this._graph = view;
    }
    
    /**
     * Returns bottom menu view.
     * @return _bottomMenu
     */
    public BottomMenu getBottomMenu() {
        return this._bottomMenu;
    }
    
    /**
     * Sets bottom menu.
     * @param view 
     */
    public void setBottomMenu(BottomMenu view) {
        this._bottomMenu = view;
    }
    
    /**
     * Builds top menu (coordinates, start date, end date, forecast, apply
     * and clear buttons).
     */
    private void _buildTopMenu() {
        this.setTopMenu(new TopMenu());
        getElements().add(this.getTopMenu());
    }
    
    /**
     * Builds graph(s).
     */
    private void _buildGraph() {
        this.setGraph(new Graph());
        getElements().add(this.getGraph());
    }
    
    /**
     * Builds bottom menu (weather, road condition, data and settings 
     * buttons).
     */
    private void _buildBottomMenu() {
        this.setBottomMenu(new BottomMenu());
        getElements().add(this.getBottomMenu());
    }
    
    /**
     * Builds contents of the container by going through each element and
     * its nodes (individual containers that include the actual UI).
     */
    private void _buildContainer() {
        this.getContainer().setStyle("-fx-background-color: #B8E2EB;");
        
        for (Element element : getElements()) {
            for (Node node : element.getNodes()) {
                getContainer().getChildren().add(node);
            }
        }       
    }

    /**
     * Renders the final view.
     */
    public void render() {
        //Scene scene = new Scene(this.getContainer(), 1050, 950);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(this.getContainer());
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane, 1050, 950);
        
        this.getStage().setScene(scene);
        this.getStage().setMinWidth(1050);
        this.getStage().setMinHeight(980);
        
        getStage().show();
    }
    
}
