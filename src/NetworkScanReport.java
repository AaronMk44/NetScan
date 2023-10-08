public class NetworkScanReport {
    private Object[] onlineDevices;
    private long taskDuration;

    public NetworkScanReport() {
        this.onlineDevices = null;
        this.taskDuration = 0;
    }

    public NetworkScanReport(Object[] onlineDevices, long taskDuration) {
        this.onlineDevices = onlineDevices;
        this.taskDuration = taskDuration;
    }

    public Object[] getOnlineDevices() {
        return onlineDevices;
    }

    public void setOnlineDevices(Object[] onlineDevices) {
        this.onlineDevices = onlineDevices;
    }

    public long getTaskDuration() {
        return taskDuration;
    }

    public void setTaskDuration(long taskDuration) {
        this.taskDuration = taskDuration;
    }
}
