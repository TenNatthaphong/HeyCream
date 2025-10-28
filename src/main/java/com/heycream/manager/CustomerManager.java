package com.heycream.manager;

import com.heycream.actor.Customer;
import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class CustomerManager {
    private final Pane customerLayer;
    private final UIManager uiManager;
    private ImageView currentCustomerView;
    private Customer currentCustomer;

    // ===== Patience Bar =====
    private static final double PAT_W = 20;
    private static final double PAT_H = 50;
    private static final double PAT_X = 520; // วางด้านขวาของตัวละคร
    private static final double PAT_Y = 280;

    private Pane patienceHost;         // 👉 host ของแถบ (จะชี้ไปที่ itemLayer)
    private Rectangle patienceBg;
    private Rectangle patienceFill;
    private Timeline patienceTicker;
    private double patienceRatio = 1.0;

    // callback เมื่อ “ลูกค้าออกจากฉากแล้ว” (ใช้ spawn คนใหม่)
    private Runnable onCustomerLeft;

    public CustomerManager(Pane customerLayer, UIManager uiManager) {
        this.customerLayer = customerLayer;
        this.uiManager = uiManager;// ✅ ต้องกำหนดค่าใน constructor นี้
    }

    public ImageView getCurrentCustomerView() { return currentCustomerView; }
    public Customer getCurrentCustomer() { return currentCustomer; }

    public void setPatienceHost(Pane host) { this.patienceHost = (host != null ? host : customerLayer); }

    public void setOnCustomerLeft(Runnable cb) { this.onCustomerLeft = cb; }

    public double getPatienceRatio() { return patienceRatio; }

    // ===== Spawn / Leave =====

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

    double startX = -200;
    double endX   = 420;
    view.setLayoutX(startX);
    view.setLayoutY(280);

    customerLayer.getChildren().add(view);
    currentCustomerView = view;
    currentCustomer.setImageView(view);

    // ✨ ลูกค้าเดินเข้ามาตรงกลาง
    TranslateTransition enter = new TranslateTransition(Duration.seconds(2), view);
    enter.setFromX(0);
    enter.setToX(endX - startX);
    enter.setInterpolator(Interpolator.EASE_OUT);

    enter.setOnFinished(e -> {
        System.out.println("✅ " + customer.getName() + " entered the scene.");

        // ▶▶▶ สร้าง patience bar หลังจากลูกค้าหยุดตรงกลางแล้ว
        createPatienceBar();
        int baseSeconds = customer.getBehavior().getPatienceSeconds();
        startPatience(baseSeconds);

        if (onFinish != null) onFinish.run();
    });

    enter.play();
}


public void leaveScene(Runnable onExit) {
    if (currentCustomerView == null) {
        if (onExit != null) onExit.run();
        return;
    }

    // 🧭 ก่อนลูกค้าเริ่มเดินออก ให้หยุดและ fade-out patience bar
    stopPatience();
    disposePatienceBar();

    // 🧹 เคลียร์ของทั้งหมดจาก itemLayer (ผ่าน itemManager)
    

    // 🚶‍♂️ ลูกค้าเดินออกจากฉาก
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



    // ===== Patience Helpers =====

    private void createPatienceBar() {
    disposePatienceBar(); // กันค้างจากลูกค้าคนก่อน

    // พื้นหลัง (สีซีด + ขอบเข้ม + ขอบมน)
    patienceBg = new Rectangle(PAT_W, PAT_H);
    patienceBg.setFill(Color.web("#ffd6d6"));
    patienceBg.setStroke(Color.web("#7a2121"));
    patienceBg.setStrokeWidth(2.4);
    patienceBg.setArcWidth(10);
    patienceBg.setArcHeight(10);
    patienceBg.setLayoutX(PAT_X);
    patienceBg.setLayoutY(PAT_Y);

    // แถบสีเขียว (ขอบมนเหมือนกัน)
    patienceFill = new Rectangle(PAT_W, PAT_H);
    patienceFill.setFill(Color.web("#34c759"));
    patienceFill.setArcWidth(10);
    patienceFill.setArcHeight(10);
    patienceFill.setLayoutX(PAT_X);
    patienceFill.setLayoutY(PAT_Y);

    patienceRatio = 1.0;

    Pane host = (patienceHost != null ? patienceHost : customerLayer);
    host.getChildren().addAll(patienceBg, patienceFill);

    // ให้อยู่หน้าสุดในเลเยอร์นั้น
    patienceBg.toFront();
    patienceFill.toFront();
}


    private void startPatience(int totalSeconds) {
    stopPatience();
    if (totalSeconds <= 0) totalSeconds = 1;

    final int fps = 20;
    final double dt = 1.0 / (totalSeconds * fps);

    patienceTicker = new Timeline(new KeyFrame(Duration.millis(1000.0 / fps), ev -> {
        patienceRatio -= dt;
        if (patienceRatio < 0.0) patienceRatio = 0.0;

        // คำนวณความสูงที่เหลือ
        double h = PAT_H * patienceRatio;

        // 🔽 ทำให้ "ส่วนบน" หายไปก่อน: ขยับ Y ลงตามความสูงที่หาย
        patienceFill.setHeight(h);
        patienceFill.setLayoutY(PAT_Y + (PAT_H - h));

        if (patienceRatio <= 0.0) {
            stopPatience();
            String phrase = (currentCustomer != null)
                    ? currentCustomer.getBehavior().getReactionPhrase(false)
                    : "Time's up!";
            uiManager.showSpeechBubble(phrase, () -> leaveScene(null));
        }
    }));
    patienceTicker.setCycleCount(Animation.INDEFINITE);
    patienceTicker.play();
}


    private void stopPatience() {
        if (patienceTicker != null) {
            patienceTicker.stop();
            patienceTicker = null;
        }
    }

    private void disposePatienceBar() {
        stopPatience();
        if (patienceHost != null) {
            if (patienceFill != null) patienceHost.getChildren().remove(patienceFill);
            if (patienceBg   != null) patienceHost.getChildren().remove(patienceBg);
        } else {
            if (patienceFill != null) customerLayer.getChildren().remove(patienceFill);
            if (patienceBg   != null) customerLayer.getChildren().remove(patienceBg);
        }
        patienceFill = null;
        patienceBg = null;
        patienceRatio = 1.0;
    }
}
