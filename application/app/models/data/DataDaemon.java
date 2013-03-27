package models.data;

import java.lang.Thread;
import java.lang.InterruptedException;
import java.lang.Comparable;

import java.util.PriorityQueue;
import java.util.Calendar;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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
        if(instance == null) instance = new DataDaemon();
        return instance;
    }

    private final PriorityQueue<TimedTask> queue;
    private final Thread timerThread;

    // Private constructor.
    private DataDaemon() {
        queue = new PriorityQueue<TimedTask>();
        timerThread = new TimerThread();
        timerThread.start();
    }

    /**
     * Runs the provided task at the specified time.
     * @param task What to to.
     * @param date When to do it.
     */
    public void runAt(Task task, Calendar date) {
        queue.add(new TimedTask(date, 0, task));
    }

    /**
     * Runs the provided task every <code>interval</code> seconds, starting at
     * the specified time.
     * @param task What to do.
     * @param interval Interval between two runs.
     * @param date When to start the first run.
     */
    public void repeatedRunAt(Task task, int interval, Calendar date) {
        queue.add(new TimedTask(date, interval, task));
    }

    /**
     * Runs the provided task every <code>interval</code> seconds, starting now.
     * @param task What to do.
     * @param interval Interval between two runs.
     */
    public void repeatedRun(Task task, int interval) {
        queue.add(new TimedTask(Calendar.getInstance(), interval, task));
    }

    /**
     * Runs the provided task at the named occasions, starting at the specified
     * time.
     * @param task What to do.
     * @param time When to do it.
     * @param date When to start doing it.
     */
    public void repeatedRunAt(Task task, Time time, Calendar date) {
        switch(time) {
            case MIDNIGHT:  date.set(Calendar.SECOND, 0);
                            date.set(Calendar.MINUTE, 0);
                            date.set(Calendar.HOUR_OF_DAY, 0);
                            queue.add(new TimedTask(date, 60 * 60 * 24, task));
                            break;
            default:        break;
        }
    }

    /**
     * Runs the provided task at the named occasions, starting now.
     * @param task What to do.
     * @param runs When to do it.
     */
    public void repeatedRun(Task task, Time time) {
        // TODO
    }

    public static interface Task {
        public void run() throws Exception;
    }

    private static class TimedTask implements Comparable<TimedTask>, Runnable {

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
            Calendar newdate = (Calendar) date.clone();
            newdate.add(Calendar.SECOND, interval);
            return new TimedTask(newdate, interval, task);
        }

        /**
         * Returns the time in milliseconds left to the next run.
         * @return Milliseconds left to date.
         */
        public long millisLeft() {
            return date.getTimeInMillis() -
                Calendar.getInstance().getTimeInMillis();
        }

        @Override public int compareTo(TimedTask that) {
            return this.date.compareTo(that.date);
        }

        @Override public void run() {
            try { task.run(); }
            catch(Exception e) { throw new RuntimeException(e); }
        }

    }

    private class TimerThread extends Thread {
        private boolean newTask = false;
        private Executor executor = Executors.newCachedThreadPool();
        public void notifyNewTask() {
            newTask = true;
            notify();
        }
        public void run() {
            TimedTask next = null;
            long left;
            while(true) {
                // Check the next task.
                synchronized(queue) { next = queue.peek(); }
                try {
                    // If there's no task ready, just wait indefinitely.
                    if(next == null) wait();
                    else {
                        // If there's a task ready, wait for it if it isn't
                        // overdue yet.
                        left = next.millisLeft();
                        if(left > 0) wait(left);
                    }
                } catch(InterruptedException e) {
                    // Someone dared interrupt us, just ignore them.
                    continue;
                }
                if(newTask) {
                    // We were notified of a new task, we loop anew.
                    newTask = false;
                    continue;
                }
                // Time for the next task: retrieve, execute and repeat it.
                synchronized(queue) { next = queue.remove(); }
                executor.execute(next);
                next = next.next();
                if(next != null) synchronized(queue) { queue.add(next); }
            }
        }
    }

    public static enum Time {
        ON_THE_HOUR,
        MIDDAY,
        MIDNIGHT
    }

}
