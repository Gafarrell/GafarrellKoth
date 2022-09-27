package io.github.gafarrell.koth;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class KothTimer extends BukkitRunnable{

    private static volatile KothTimer KothTimerInstance;

    private final ArrayList<KothController> ActiveKoths = new ArrayList<>();
    private float LastTimeMillis = 0;

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

    public ArrayList<KothController> GetActiveKoths()
    {
        return ActiveKoths;
    }

    /**
     * Add KoTH to queue, but does not start it.
     * @param controller KoTH Activity to queue
     */
    public void QueueKoth(KothController controller)
    {
        if (!ActiveKoths.contains(controller)){
            ActiveKoths.add(controller);
        }
    }

    /**
     * Starts the given KoTH.
     * @param controller KoTH Activity to Start
     */
    public void StartKoth(KothController controller){
        if (ActiveKoths.contains(controller)){
            controller.start();
        }
        else {
            ActiveKoths.add(controller);
            controller.start();
        }
    }

    /**
     * Pauses the KoTH given.
     * @param controller KoTH to Pause
     */
    public void PauseKoth(KothController controller){
        if (ActiveKoths.contains(controller)){
            controller.Pause();
        }
    }



    @Override
    public void run() {
        ActiveKoths.forEach(kothController -> {
            if (LastTimeMillis > 0f)
            {
                float deltaTime = System.currentTimeMillis()-LastTimeMillis;
                kothController.adjustDuration(deltaTime);
            }

            if (!kothController.isActive()){
                kothController.concludeKoth();
            }
        });
        LastTimeMillis = System.currentTimeMillis();
    }
}
