package io.github.gafarrell.koth;

import org.bukkit.Server;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class KothTimer implements Runnable{

    private static volatile KothTimer KothTimerInstance;

    private long LastTimeMillis = -1;

    // Constructor ensures singleton scheduler.
    private KothTimer() {
        LastTimeMillis = System.currentTimeMillis();
        if (KothTimerInstance != null) throw new RuntimeException("User getInstance() method to get the singleton scheduler!");
    }

    // Sync access to scheduler to avoid double instantiating
    public synchronized static KothTimer GetInstance()
    {
        if (KothTimerInstance == null) {
            synchronized (KothTimer.class){
                if (KothTimerInstance == null) KothTimerInstance = new KothTimer();
            }
        }
        return KothTimerInstance;
    }


    @Override
    public void run() {
        if (LastTimeMillis > 0)
        {
            long deltaTime = System.currentTimeMillis()-LastTimeMillis;
            KothStorage.gameLoop(deltaTime);
        }

        LastTimeMillis = System.currentTimeMillis();
    }
}
