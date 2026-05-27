import java.util.ArrayList;

public class PackageService {

    ArrayList<Package> list = new ArrayList<>();

    public boolean isValidPackage(String senderName, String receiverName, double weight, double distance){

        if(senderName.isEmpty() || receiverName.isEmpty() || weight <= 0 || distance <= 0){
            return false;
        }
        return true;
        
    }

    public boolean createPackage(String senderName, String receiverName, double weight, double distance, String packageType){

        if(!isValidPackage(senderName, receiverName, weight, distance)){
            return false;
        }

        switch(packageType){
            case "Standard Package":
                StandardPackage standardPackage = new StandardPackage(senderName, receiverName, weight, distance);
                list.add(standardPackage);
                break;
            case "Express Package":
                ExpressPackage expressPackage = new ExpressPackage(senderName, receiverName, weight, distance);
                list.add(expressPackage);
                break;
            case "Fragile Package":
                FragilePackage fragilePackage = new FragilePackage(senderName, receiverName, weight, distance);
                list.add(fragilePackage);
                break;
            default:
                return false;
        }

        return true;
    }

    public ArrayList<Package> getAllPackages(){
        return list;
    }

}