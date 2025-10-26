
package com.heycream.gui;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CloudScroller {

    public static Group createInfiniteBand(String cloudPath, double stageWidth, double y, double speedPxPerSec) {
    Image img = new Image(CloudScroller.class.getResource(cloudPath).toExternalForm());
    double w = img.getWidth();
    int count = (int) Math.ceil(stageWidth / w) + 3;

    ImageView[] strips = new ImageView[count];
    Group band = new Group();
    for (int i = 0; i < count; i++) {
        ImageView iv = new ImageView(img);
        iv.setY(y);
        iv.setX((i - 1) * w); 
        strips[i] = iv;
        band.getChildren().add(iv);
    }

    final long[] last = { System.nanoTime() };

    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            double dt = (now - last[0]) / 1_000_000_000.0;
            last[0] = now;
            double dx = speedPxPerSec * dt;
            for (ImageView iv : strips) {
                iv.setX(iv.getX() - dx);
            }
            double maxX = Double.NEGATIVE_INFINITY;
            for (ImageView iv : strips) {
                if (iv.getX() > maxX)
                    maxX = iv.getX();
            }
            for (ImageView iv : strips) {
                if (iv.getX() <= -w) { 
                    iv.setX(maxX + w - 1); 
                    maxX = iv.getX();
                }
            }
        }
    };
    timer.start();

    return band;
}


}
