public class FragilePackage extends Package{

    public FragilePackage(String senderName, String receiverName, double weight, double distance) {
        super(senderName, receiverName, weight, distance);
    }

    @Override
    double calculatePrice() {
        double base = getWeight() * getDistance() * 0.05;

        if(getWeight() <= 5){
            return base;
        }
        return base + 50;
    }

    @Override
    int getDeliveryDays() {
        return 5;
    }
    
}
