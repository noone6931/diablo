package org.example.diablo.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

public class TimeWheelUtils {

    static long l1;
    public static void main(String[] args) {
        TimeWheel timeWheel = new TimeWheel();
        timeWheel.addJob(new Job(1)); // 10秒后执行
        timeWheel.addJob(new Job(5)); // 20秒后执行
        timeWheel.addJob(new Job(10)); // 70秒后执行，即在10秒位置再次执行
        l1 = System.currentTimeMillis();

    }
    public static class TimeWheel {
        Map<Integer, List<Job>> ringMap;

        private int currentSecond;
        public TimeWheel() {
            // 初始化时间轮
            // 60个槽位代表60秒钟
            ringMap = new HashMap<>();
            for (int i = 0; i < 60; i++) {
                ringMap.put(i, new ArrayList<>());
            }
            currentSecond = 0;
            start();

        }

        private void start() {
            Thread timerThread = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(1000); // 模拟秒的流逝
                        tick();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            timerThread.start();
        }

        private void tick() {
            List<Job> jobsToRun = ringMap.get(currentSecond);
            if (jobsToRun != null) {
                for (Job job : new ArrayList<>(jobsToRun)) {
                    job.run();
                }
                jobsToRun.clear(); // 清除已触发的任务
            }
            currentSecond = (currentSecond + 1) % 60; // 秒针前进
        }

        public void addJob(Job job) {
            int slot = (currentSecond + job.getDelay()) % 60;
            List<Job> jobs = ringMap.get(slot);
            jobs.add(job);
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Job implements Runnable {

        private int delay;

        @Override
        public void run() {
            long l2 = System.currentTimeMillis();
            System.out.println("触发任务了, "+(l2-l1)/1000 + "秒");
        }
    }
}
