package com.stat;

/**
 * SINGLETON
 *
 * @author Parisi Germán & Bertola Federico
 * @version 1.0
 */
public class CounterStat {

    private static CounterStat me;

    private long countAcceptedClients;
    private long countIgnoredClients;
    private long countSentImages;
    private long countSentObjects;
    private long countSentFiles;
    private long countSentMessages;

    private CounterStat() {

    }

    public static CounterStat getIntance() {
        if (me == null) {
            me = new CounterStat();
        }
        return me;
    }

    public void incrementCountAcceptedClients() {
        this.countAcceptedClients++;
    }

    public void incrementCountIgnoredClients() {
        this.countIgnoredClients++;
    }

    public void incrementCountSentImages() {
        this.countSentImages++;
    }

    public void incrementCountSentObjects() {
        this.countSentObjects++;
    }

    public void incrementCountSentFiles() {
        this.countSentFiles++;
    }

    public void incrementCountSentMessages() {
        this.countSentFiles++;
    }

    public long getCountTotalClients() {
        return this.countAcceptedClients + this.countIgnoredClients;
    }

    public long getCountAcceptedClients() {
        return countAcceptedClients;
    }

    public long getCountIgnoredClients() {
        return countIgnoredClients;
    }

    public long getCountSentImages() {
        return countSentImages;
    }

    public long getCountSentObjects() {
        return countSentObjects;
    }

    public long getCountSentFiles() {
        return countSentFiles;
    }

    public long getCountSentMessages() {
        return countSentMessages;
    }

}
