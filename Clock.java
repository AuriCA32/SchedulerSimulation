public class Clock implements Runnable{

    int tick_duration; // in nanoseconds
    int total_ticks;

    public Clock(int tick) {
        tick_duration = tick;
        total_ticks = 0;
    }

    public void run() {
        try {
            sleep(tick_duration);
            total_ticks++;
        } catch (InterruptedException e) { }
    }

    public int getTotalTicks(){
        return total_ticks;
    }

}