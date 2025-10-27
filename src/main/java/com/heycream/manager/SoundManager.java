package com.heycream.manager;

public class SoundManager {

    private static boolean muted = false;

    public static boolean isMuted() { return muted; }
    public static void setMuted(boolean m) { muted = m; }
    public static void toggleMute() { muted = !muted; }

    public void playCash() {
        if (muted) return;
        System.out.println("[SOUND] cash");
        // TODO: play actual audio clip here
    }

    public void playOrderBell() {
        if (muted) return;
        System.out.println("[SOUND] order_bell");
        // TODO: play actual audio clip here
    }
}
