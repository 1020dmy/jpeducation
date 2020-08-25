package com.mantis.im_service.ui.module;

public class RecordRobot {
    private String isRobot;//是否为机器人
    private String isComplete;//是否完成对话
    private String ball;//说话权是否在访客手中
    private long lastTime;//收到消息的时间

    public String getIsRobot() {
        return isRobot;
    }

    public void setIsRobot(String isRobot) {
        this.isRobot = isRobot;
    }

    public String getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(String isComplete) {
        this.isComplete = isComplete;
    }

    public String getBall() {
        return ball;
    }

    public void setBall(String ball) {
        this.ball = ball;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    @Override
    public String toString() {
        return "RecordRobot{" +
                "isRobot='" + isRobot + '\'' +
                ", isComplete='" + isComplete + '\'' +
                ", ball='" + ball + '\'' +
                ", lastTime=" + lastTime +
                '}';
    }
}
