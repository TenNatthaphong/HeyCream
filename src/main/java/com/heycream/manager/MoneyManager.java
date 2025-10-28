package com.heycream.manager;

import com.heycream.actor.Customer;
import com.heycream.AbstractAndInterface.CustomerBehavior;

public class MoneyManager {
    private int total;

    public int getTotal() { return total; }
    public void addMoney(int amount) { total += amount; }

    public int calculateReward(Customer customer, boolean correct) {
        return calculateReward(customer, correct, 1.0);
    }
    public int calculateReward(Customer customer, boolean correct, double patienceFrac) {
        // คุณปรับ base ได้ตามบาลานซ์เกม
        int base = 50;
        CustomerBehavior b = customer.getBehavior();

        double tipMult = b.getTipBonus();  // VIP 1.5, Calm 1.0, Rude 0.8 (ตามไฟล์ behavior ที่คุณให้มา)
        if (!correct) {
            // ถ้าเสิร์ฟผิด: ปรับเป็นหักครึ่งค่าฐาน (หรือปรับตามที่ต้องการ)
            int penalty = (int)Math.round(-0.5 * base);
            return penalty;
        }
        int reward = (int)Math.round(base * tipMult * clamp01(patienceFrac));
        return Math.max(reward, 0);
    }

    private static double clamp01(double v) {
        if (v < 0) return 0;
        if (v > 1) return 1;
        return v;
    }
}
