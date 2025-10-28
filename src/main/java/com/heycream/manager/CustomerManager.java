package com.heycream.manager;

import com.heycream.actor.Customer;
import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class CustomerManager {
    private final Pane customerLayer;
    private final UIManager uiManager;

    private ImageView currentCustomerView;
    private Customer currentCustomer;

    // Patience bar
    private Rectangle patienceBack;
    private Rectangle patienceFill;
    private Timeline patienceTick;
    private long arrivalEpochMs;
    private int maxPatienceSec;

    public CustomerManager(Pane customerLayer, UIManager uiManager) {
        this.customerLayer = customerLayer;
        this.uiManager = uiManager;
    }

    public ImageView getCurrentCustomerView() { return currentCustomerView; }
    public Customer getCurrentCustomer() { return currentCustomer; }

    public void spawnCustomer(Customer customer, Runnable onFinish) {
        if (currentCustomerView != null) {
            System.out.println("⚠ Customer already exists, skipping spawn");
            return;
        }

        currentCustomer = customer;

        String path = "/com/heycream/assets/Customer" + customer.getName() + ".png";
        Image image = new Image(path);
        ImageView view = new ImageView(image);
        view.setFitHeight(200);
        view.setPreserveRatio(true);
        view.setLayoutY(280);

        double startX = -200;
        double endX = 420;

        view.setLayoutX(startX);
        customerLayer.getChildren().add(view);
        currentCustomerView = view;

        // ตั้งค่า patience
        maxPatienceSec = (customer.getBehavior() != null) ? customer.getBehavior().getPatienceSeconds() : 20;
        arrivalEpochMs = System.currentTimeMillis();
        createPatienceBar();   // วางแถบที่ตำแหน่งที่ระบุ
        startPatienceTick();   // เริ่มอัปเดต

        TranslateTransition enter = new TranslateTransition(Duration.seconds(2), view);
        enter.setFromX(0);
        enter.setToX(endX - startX);
        enter.setInterpolator(Interpolator.EASE_OUT);
        enter.setOnFinished(e -> {
            if (onFinish != null) onFinish.run();
        });
        enter.play();
    }

    public void leaveScene(Runnable onExit) {
        if (currentCustomerView == null) {
            if (onExit != null) onExit.run();
            return;
        }

        // หยุดและล้าง patience bar
        stopPatienceTick();
        removePatienceBar();

        TranslateTransition exit = new TranslateTransition(Duration.seconds(1.0), currentCustomerView);
        exit.setToX(900 - currentCustomerView.getLayoutX());
        exit.setInterpolator(Interpolator.EASE_IN);
        exit.setOnFinished(e -> {
            customerLayer.getChildren().remove(currentCustomerView);
            currentCustomerView = null;
            currentCustomer = null;
            if (onExit != null) onExit.run();
        });
        exit.play();
    }

    // ---------------- Patience bar ----------------

    private void createPatienceBar() {
        // พิกัดตามที่กำหนด: x=810, y=480, w=20, h=50
        double x = 810, y = 480, w = 20, h = 50;

        patienceBack = new Rectangle(x, y, w, h);
        patienceBack.setArcWidth(6);
        patienceBack.setArcHeight(6);
        patienceBack.setFill(Color.web("#eeeeee"));
        patienceBack.setStroke(Color.web("#666666"));

        patienceFill = new Rectangle(x, y, w, h);
        patienceFill.setArcWidth(6);
        patienceFill.setArcHeight(6);
        patienceFill.setFill(Color.web("#51d88a")); // เขียว
        patienceFill.setStroke(Color.TRANSPARENT);

        customerLayer.getChildren().addAll(patienceBack, patienceFill);
        updatePatienceFill(); // วาดครั้งแรก
    }

    private void removePatienceBar() {
        if (patienceBack != null) customerLayer.getChildren().remove(patienceBack);
        if (patienceFill != null) customerLayer.getChildren().remove(patienceFill);
        patienceBack = null;
        patienceFill = null;
    }

    private void startPatienceTick() {
        stopPatienceTick();
        patienceTick = new Timeline(new KeyFrame(Duration.seconds(0.2), e -> updatePatienceFill()));
        patienceTick.setCycleCount(Animation.INDEFINITE);
        patienceTick.play();
    }

    private void stopPatienceTick() {
        if (patienceTick != null) {
            patienceTick.stop();
            patienceTick = null;
        }
    }

    private void updatePatienceFill() {
        if (patienceFill == null || patienceBack == null || maxPatienceSec <= 0) return;

        double ratio = getPatienceRatio();
        ratio = Math.max(0, Math.min(1, ratio));

        double fullH = patienceBack.getHeight();
        double x = patienceBack.getX();
        double y = patienceBack.getY();

        // เติมจากล่างขึ้นบนตามสัดส่วน (สีเปลี่ยนตามน้อยลง)
        double filledH = fullH * ratio;
        patienceFill.setX(x);
        patienceFill.setY(y + (fullH - filledH));
        patienceFill.setWidth(patienceBack.getWidth());
        patienceFill.setHeight(filledH);

        // เขียว → เหลือง → แดง
        if (ratio > 0.6) patienceFill.setFill(Color.web("#51d88a"));
        else if (ratio > 0.3) patienceFill.setFill(Color.web("#ffd166"));
        else patienceFill.setFill(Color.web("#ef476f"));
    }

    
    public double getPatienceRatio() {
        if (maxPatienceSec <= 0) return 1.0;
        long elapsedSec = (System.currentTimeMillis() - arrivalEpochMs) / 1000;
        long left = Math.max(0, maxPatienceSec - elapsedSec);
        return Math.min(1.0, left / (double) maxPatienceSec);
    }
}
