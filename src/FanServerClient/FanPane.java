/*
 * Notes: 
 *  Element positioning is pretty weak. Probably need a more robust system.
 *  No fan animation currently
 *  Not connected to the fan object yet.
 *  temperatureTF event handler not setup yet.
 *  Knob not added in yet.
 */
package FanServerClient;

import javafx.animation.Animation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Felix
 */
public class FanPane extends FlowPane {

    private static final double WIDTH = 343, HEIGHT = 768;
    private static int nextId = 0;
    private int id;

    private Fan fan;

    ColorfulCircle circle;
    TextField temperatureTF;
    Slider speedSlider;

    public FanPane() {
        // Create an individual ID for Fan Pane
        id = ++nextId;

        // Create a maximum width and height for predictable columns
        setMaxWidth(WIDTH);
        setMaxHeight(HEIGHT);

        // Create fan object
        fan = new Fan();

        // Create rotating circle
        int radius = (int) ((WIDTH - 10) / 2.8);
        circle = new ColorfulCircle(radius, (int) (WIDTH / 2), (int) (HEIGHT / 4));

        // Create and style temperature label
        Label temperatureLabel = new Label("Temperature");
        temperatureLabel.setTranslateX((int) (WIDTH / 2) - 150);
        temperatureLabel.setTranslateY((int) (HEIGHT / 4) + 200);
        temperatureLabel.setStyle("-fx-background-color: green");

        // Create temperature text field
        temperatureTF = new TextField();
        temperatureTF.setTranslateX((int) (WIDTH / 2) - 70);
        temperatureTF.setTranslateY((int) (HEIGHT / 4) + 200);
        temperatureTF.setText("" + fan.getTemperature());

        // Create and style speed label
        Label speedLabel = new Label("Speed");
        speedLabel.setTranslateX((int) (WIDTH / 2) - 150);
        speedLabel.setTranslateY((int) (HEIGHT / 4) + 240);
        speedLabel.setStyle("-fx-background-color: green");

        // Create speed slider
        speedSlider = new Slider(0, 100, 0);
        speedSlider.setTranslateX((int) (WIDTH / 2) -70);
        speedSlider.setTranslateY((int) (HEIGHT / 4) + 240);

        // Create background fill
        Rectangle background = new Rectangle(WIDTH, HEIGHT, Color.BLACK);
        background.setStroke(Color.GREEN);

        // create main content
        Group group = new Group(
                background,
                circle,
                temperatureLabel,
                temperatureTF,
                speedLabel,
                speedSlider
        );

        // Add main content to pane
        getChildren().add(group);

        // Event handler: 
        //   Inputs:    Temperature Data
        //   Performs:  Turn On/Off/Changes Speed of Fan animation
        //   Outputs:   None
        temperatureTF.setOnAction((ActionEvent e) -> {
            System.out.println(id + ": " + temperatureTF.getText());

            // Update Temperature
            fan.setTemperature(Double.parseDouble(temperatureTF.getText()));

            updateCircleAnimation();
        });

        // Event handler: 
        //   Inputs:    Fan Speed
        //   Performs:  Turn On/Off/Changes Speed of Fan animation
        //   Outputs:   None
        speedSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                    Number old_val, Number new_val) {

                System.out.println(id + ": " + speedSlider.getValue());

                // Update Fan Speed
                fan.setSpeed(speedSlider.getValue());
                updateCircleAnimation();
            }
        });
    }

    private void updateCircleAnimation() {
        
        // If animation is stopped then start if
        if (Animation.Status.STOPPED == circle.animation.getStatus()
                && fan.getSpeed() > 0.0
                && fan.getTemperature() >= fan.getMinStartingTemp()) {
            circle.animation.play();
        } 
        // Needs speed 0 or temp below min running temp to stop
        else if (Animation.Status.RUNNING == circle.animation.getStatus()
                && (fan.getSpeed() == 0.0
                || fan.getTemperature() < fan.getMinRunningTemp())) {
            circle.animation.stop();
        } 
        // Otherwise, increase or decrease animation speed 
        else {
            circle.animation.setRate(fan.getSpeed() / 50);
        }
    }

    // Fan
    public Fan getFan() {
        return fan;
    }

    public void setFan(Fan fan) {
        this.fan = fan;
        temperatureTF.setText("" + fan.getTemperature());
        speedSlider.setValue(fan.getSpeed());
    }
}
