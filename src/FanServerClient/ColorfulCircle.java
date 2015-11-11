/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FanServerClient;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/**
 *
 * @author Felix
 */
public class ColorfulCircle extends Circle {

    public Rotate rotation;
    public Timeline animation;
    private KeyFrame initialRotation;
    private KeyFrame endingRotation;
    private int rotationLength = 1000;
    public Circle circle;

    public ColorfulCircle(int radius, int x, int y) {
        // Create colorful circle
        super(radius,
                new LinearGradient(0f, 1f, 1f, 0f, true, CycleMethod.NO_CYCLE,
                        new Stop(0, Color.web("#f8bd55")),
                        new Stop(0.14f, Color.web("#c0fe56")),
                        new Stop(0.28f, Color.web("#5dfbc1")),
                        new Stop(0.43f, Color.web("#64c2f8")),
                        new Stop(0.57f, Color.web("#be4af7")),
                        new Stop(0.71f, Color.web("#ed5fc2")),
                        new Stop(0.85f, Color.web("#ef504c")),
                        new Stop(1, Color.web("#f2660f"))));

        // Set center X, Y
        setTranslateX(x);
        setTranslateY(y);

        // Set up rotation in circle 
        rotation = new Rotate(360,
                getCenterX(),
                getCenterY());
        getTransforms().add(rotation);

        // Set a blur effect on each layer
        setEffect(new GaussianBlur(1));

        // Create an animation to rotate circle 360 degrees each second
        animation = new Timeline();

        initialRotation = new KeyFrame(Duration.ZERO, // set start position at 0s
                new KeyValue(rotation.angleProperty(), 0)
        );
        endingRotation = new KeyFrame(new Duration(rotationLength), // set start position at 0s
                new KeyValue(rotation.angleProperty(), 360)
        );

        animation.getKeyFrames().addAll(initialRotation, endingRotation);
        animation.setCycleCount(Timeline.INDEFINITE);

    }

    public void stop() {
        animation.stop();
    }

    public void play() {
        animation.play();
    }

}
