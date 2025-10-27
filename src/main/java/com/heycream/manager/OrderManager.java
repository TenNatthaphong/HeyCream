package com.heycream.manager;

import com.heycream.actor.Customer;      // import เฉพาะที่ใช้จาก actor
import com.heycream.model.Order;         // <— ดึง Order จาก model แบบเจาะจง
import com.heycream.model.Cup;           // <— ถ้าไฟล์นี้ใช้ Cup ด้วย ดึงจาก model เช่นกัน
import com.heycream.AbstractAndInterface.*;
import com.heycream.utils.*;

public class OrderManager {
    private final TimeManager timeManager;

    public OrderManager(TimeManager sharedTime) {
        this.timeManager = sharedTime;
    }

    public Order generateOrder() {
        return Randomizer.randomOrder();
    }

    public Customer generateCustomer() {
        Order order = generateOrder();
        String name = Randomizer.randomName();
        CustomerBehavior behavior = Randomizer.randomBehavior();
        return new Customer(name, order, behavior, timeManager.getCurrentMinute());
    }

    public boolean isOrderCorrect(Cup playerCup, Customer customer) {
        return customer.getOrder().checkMatch(playerCup);
    }
}
