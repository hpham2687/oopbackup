package main;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class SwitchButton extends StackPane {
    private final Rectangle back = new Rectangle(30, 10, Color.RED);
    private final Button button = new Button();
    private String buttonStyleOff = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 0.2, 0.0, 0.0, 2); -fx-background-color: WHITE;";
    private String buttonStyleOn = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 0.2, 0.0, 0.0, 2); -fx-background-color: #00893d;";
    private boolean state;
    private final BooleanProperty isOpen = new SimpleBooleanProperty();
    
    
    
    public final BooleanProperty selectedProperty() {
        return this.isOpen;
    }


public final void setSelected(final boolean on) {
    this.selectedProperty().set(on);
}

public final boolean isSelected() {
    return this.selectedProperty().get();
}

    public boolean getState() {
		return state;
	}

	public String getButtonStyleOff() {
		return buttonStyleOff;
	}

	public void setButtonStyleOff(String buttonStyleOff) {
		this.buttonStyleOff = buttonStyleOff;
	}

	public String getButtonStyleOn() {
		return buttonStyleOn;
	}

	public void setButtonStyleOn(String buttonStyleOn) {
		this.buttonStyleOn = buttonStyleOn;
	}

	public Rectangle getBack() {
		return back;
	}

	public Button getButton() {
		return button;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	private void init() {
        getChildren().addAll(back, button);
        setMinSize(30, 15);
        back.maxWidth(30);
        back.minWidth(30);
        back.maxHeight(10);
        back.minHeight(10);
        back.setArcHeight(back.getHeight());
        back.setArcWidth(back.getHeight());
        back.setFill(Color.valueOf("#ced5da"));
        Double r = 2.0;
        button.setShape(new Circle(r));
        setAlignment(button, Pos.CENTER_LEFT);
        button.setMaxSize(15, 15);
        button.setMinSize(15, 15);
        button.setStyle(buttonStyleOff);
    }

    public SwitchButton() {
        init();
        EventHandler<Event> click = new EventHandler<Event>() {
            @Override
            public void handle(Event e) {
                if (state) {
                    button.setStyle(buttonStyleOff);
                    back.setFill(Color.valueOf("#ced5da"));
                    setAlignment(button, Pos.CENTER_LEFT);
                    state = false;
                    setSelected(false);
                } else {
                    button.setStyle(buttonStyleOn);
                    back.setFill(Color.valueOf("#80C49E"));
                    setAlignment(button, Pos.CENTER_RIGHT);
                    state = true;
                    setSelected(true);

                    
                }
            }
        };

        button.setFocusTraversable(false);
        setOnMouseClicked(click);
        button.setOnMouseClicked(click);
    }

	public SwitchButton(EventHandler<Event> eventHandler) {
        init();
        
        button.setFocusTraversable(false);
        setOnMouseClicked(eventHandler);
        button.setOnMouseClicked(eventHandler);
		// TODO Auto-generated constructor stub
	}
}