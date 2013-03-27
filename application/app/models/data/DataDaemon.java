package models.data;

import java.lang.Thread;
import java.lang.InterruptedException;
import java.lang.Comparable;

import java.util.PriorityQueue;
import java.util.Calendar;
import java.util.Set;

/**
 * Runs pieces of code at a set time or specified interval.
 * @Author Felix Van der Jeugt
 */
public class DataDaemon {

    // Singleton instance.
    private static DataDaemon instance;

    /**
     * Retrieve the DataDaemon singleton.
     * @return The DataDaemon
     */
    public static DataDaemon getInstance() {
        if(instance != null) return instance;
        return new DataDaemon();
    }

    private PriorityQueue<Task> queue;
    private Thread timerThread;

    // Private constructor.
    private DataDaemon() {
        queue = new PriorityQueue<Task>();
        timerThread = new TimerThread();
        timerThread.start();
    }

    /**
     * Runs the provided task at the specified time.
     * @param task What to to.
     * @param date When to do it.
     */
    public void runAt(Task task, Calendar date) {
        // TODO
    }

    /**
     * Runs the provided task every <code>interval</code> seconds, starting at
     * the specified time.
     * @param task What to do.
     * @param interval Interval between two runs.
     * @param date When to start the first run.
     */
    public void repeatedRunAt(Task task, int interval, Calendar date) {
        // TODO
    }

    /**
     * Runs the provided task every <code>interval</code> seconds, starting now.
     * @param task What to do.
     * @param interval Interval between two runs.
     */
    public void repeatedRun(Task task, int interval) {
        // TODO
    }

    /**
     * Runs the provided task at the named occasions, starting at the specified
     * time.
     * @param task What to do.
     * @param runs When to do it.
     * @param date When to start doing it.
     */
    public void repeatedRunAt(Task task, Set<Time> runs, Calendar date) {
        // TODO
    }

    /**
     * Runs the provided task at the named occasions, starting now.
     * @param task What to do.
     * @param runs When to do it.
     */
    public void repeatedRun(Task task, Set<Time> runs) {
        // TODO
    }

    public static interface Task {
        public void run() throws Exception;
    }

    private static class TimedTask implements Comparable<TimedTask> {

        /** The time at which this task is set to run. */
        public final Calendar date; // Next run.
        private int interval;       // Interval between runs, <= 0 if none.
        private Task task;          // The task.

        /** Creates a new TimedTask with the given settings. */
        public TimedTask(Calendar date, int interval, Task task) {
            this.date = date;
            this.interval = interval;
            this.task = task;
        }

        /**
         * Creates a new task to be run after the interval, or null if this
         * task is not to be repeated.
         * @return The next run of this task.
         */
        public TimedTask next() {
            if(interval <= 0) return null;
            Calendar newdate = date.clone();
            newdate.add(Calendar.SECOND, interval);
            return new TimedTask(newdate, interval, task);
        }

        @Override public int compareTo(TimedTask that) {
            return this.date.compareTo(that.date);
        }

    }

    private static class TimerThread extends Thread {
        public void run() {
            TimerTask next = null;
            while(true) {
                synchronized(queue) { next = queue.peek(); }
                try {
                    if(next == null) wait();
                    else wait(next.millisLeft()); // TODO implement millisLeft()
                } catch(InterruptedException e) {
                    continue;
                }
                // TODO execute the next task on another thread. (pool?)
            }
        }
    }

    public static enum Time {
        ON_THE_HOUR,
        MIDDAY,
        MIDNIGHT
    }

}
