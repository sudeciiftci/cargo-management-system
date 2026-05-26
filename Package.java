public abstract class Package {

    private String senderName;
    private String receiverName;
    private double weight;
    private double distance;
    private String trackingNumber;
    private static int count = 1000;

    public Package(String senderName, String receiverName, double weight, double distance) {
        count++;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.weight = weight;
        this.distance = distance;
        this.trackingNumber = "PKG" + count;
    }

    public String getSenderName() {
        return senderName;
    }


    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }


    public String getReceiverName() {
        return receiverName;
    }


    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }


    public double getWeight() {
        return weight;
    }


    public void setWeight(double weight) {
        this.weight = weight;
    }


    public double getDistance() {
        return distance;
    }


    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    abstract double calculatePrice();
    abstract int getDeliveryDays();
}
