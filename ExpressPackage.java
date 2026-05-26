public class ExpressPackage extends Package{

    public ExpressPackage(String senderName, String receiverName, double weight, double distance) {
        super(senderName, receiverName, weight, distance);
    }

    @Override
    double calculatePrice() {
        return getWeight() * getDistance() * 0.05 * 1.30;
    }

    @Override
    int getDeliveryDays() {
        return 1;
    }
    
}
