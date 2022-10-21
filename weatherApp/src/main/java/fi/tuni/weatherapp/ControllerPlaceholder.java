package fi.tuni.weatherapp;

public class ControllerPlaceholder {
    
    private ViewPlaceholder view;
    private ModelPlaceholder model;

    public ControllerPlaceholder(ViewPlaceholder view, ModelPlaceholder model) {
        this.view = view;
        this.model = model;
    }
    
    public void Begin(){
        view.OpenView();
    }
    
    public void TestController(){        
        
        String newMesssage = String.format("%s, Number: %d", model.getStr(), model.getNumber());
        view.UpdateMessage(newMesssage);
    }

}
