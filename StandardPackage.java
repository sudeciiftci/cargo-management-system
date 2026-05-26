public class StandardPackage extends Package{

    public StandardPackage(String senderName, String receiverName, double weight, double distance) {
        super(senderName, receiverName, weight, distance);
    }

    @Override
    double calculatePrice() {
        return getWeight() * getDistance() * 0.05;
    }
    
    @Override
    int getDeliveryDays() {
        return 3;
    }
    
}
