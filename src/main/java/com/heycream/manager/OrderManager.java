package com.heycream.manager;

import com.heycream.actor.Customer;
import com.heycream.model.Cup;
import com.heycream.model.Order;
import com.heycream.utils.Randomizer;

/**
 * OrderManager — จัดการระบบคำสั่งซื้อ, ตรวจความถูกต้อง, และเก็บสถิติการเสิร์ฟ
 */
public class OrderManager {

    private int totalServes = 0;   // จำนวนที่เสิร์ฟทั้งหมด (ทั้งถูกและผิด)
    private int correctServes = 0; // จำนวนที่เสิร์ฟถูก
    private int totalOrders = 0;   // จำนวนออเดอร์ที่สร้าง (ใช้ตอน spawn customer)

    public OrderManager() {}

    // =========================================================
    // 🔹 Generate random order (เพิ่มตัวนับรวม)
    // =========================================================
    public Order generateOrder() {
        totalOrders++;
        return Randomizer.randomOrder();
    }

    // =========================================================
    // 🔹 ตรวจว่า player ทำออเดอร์ถูกไหม
    // =========================================================
    public boolean checkMatch(Cup servedCup, Order requested) {
        if (requested == null || servedCup == null) return false;
        boolean result = requested.checkMatch(servedCup);
        recordServe(result);
        return result;
    }

    // =========================================================
    // 🔹 บันทึกผลการเสิร์ฟ
    // =========================================================
    public void recordServe(boolean correct) {
        totalServes++;
        if (correct) correctServes++;
    }

    // =========================================================
    // 🔹 เช็คว่าพร้อมเสิร์ฟหรือยัง (มี scoop ครบหรือไม่)
    // =========================================================
    public boolean canServe(Cup prepared) {
        if (prepared == null) return false;
        if (prepared.getType() == null) return false;
        if (prepared.getSize() == null) return false;

        int required = prepared.getSize().getMaxScoops();
        return prepared.getScoops() != null && prepared.getScoops().size() >= required;
    }

    // =========================================================
    // 🔹 Getters สำหรับ popup / summary
    // =========================================================
    public int getTotalServeCount() { return totalServes; }
    public int getCorrectServeCount() { return correctServes; }
    public int getTotalOrderCount() { return totalOrders; }

    // =========================================================
    // 🔹 Reset สถิติ (ใช้ตอนเริ่มวันใหม่)
    // =========================================================
    public void resetStats() {
        totalServes = 0;
        correctServes = 0;
        totalOrders = 0;
    }

    // =========================================================
    // 🔹 ตรวจสอบการเสิร์ฟเฉพาะ (ใช้ภายนอกได้)
    // =========================================================
    public boolean isOrderCorrect(Cup playerCup, Customer customer) {
        if (playerCup == null || customer == null) return false;
        Order order = customer.getOrder();
        if (order == null) return false;
        boolean correct = order.checkMatch(playerCup);
        recordServe(correct);
        return correct;
    }
}
