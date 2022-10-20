package  application;

import application.DeliveryGuy.DeliverySession;
import static application.Main.clock;
import application.MyGoogleMap.Position;
import application.Package.Dish;
import application.Package.PackageOrder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

class PickUpOperator {

    private static IntegerProperty totalPickUpOrder;
    private static ArrayList<PickUpOrder> allPresetPickUpOrders;
    private static ArrayList<Package> partnerPackages;
    private static ArrayList<DeliveryGuy> allDeliveryGuys;
    private static ArrayList<PickUpOrder> allPickUpOrders;
    private static StringProperty log;
    private static StringProperty process;
    private static MyGoogleMap masterMap;

    public PickUpOperator() {
        PickUpOperator.totalPickUpOrder = new SimpleIntegerProperty(0);

        // set partner partner packages (read now & update later)
        PickUpOperator.partnerPackages = new ArrayList<>();
        readPartnerPackages();

        // set & update master map (read now & update now)
        PickUpOperator.masterMap = new MyGoogleMap();
        masterMap.updateMap();

        // set all delivery guys (read now & update later)
        PickUpOperator.allDeliveryGuys = new ArrayList<>();
        readAllDeliveryGuys();

        // set all preset PickUp orders
        PickUpOperator.allPresetPickUpOrders = new ArrayList<>();
        readAllPresetPickUpOrders();

        // initalize PickUp orders
        PickUpOperator.allPickUpOrders = new ArrayList<>();

        // set log (read now & update later)
        log = new SimpleStringProperty("");
        readLog();

        // set process (update later)
        process = new SimpleStringProperty("");
    }

    /**
     * Allocate PickUp orders to delivery men by their empty slots
     */
    public static void allocateDeliveryByFinishTime(PickUpOrder pOrder) {
        // find earliest delivery end time among all delivery guys
        String earliest = "23:59";
        for (DeliveryGuy deliveryGuy : PickUpOperator.getAllDeliveryGuys()) {
            if (deliveryGuy.getAllDeliverySession().isEmpty()) {
                earliest = clock.getTime();
                break;
            } else {
                String deliveryEndTime = deliveryGuy.getAllDeliverySession().get(deliveryGuy.getAllDeliverySession().size() - 1).getDeliveryEndTime();
                if (SimulatedTime.compareStringTime(earliest, deliveryEndTime) > 0) {
                    earliest = deliveryGuy.getAllDeliverySession().get(deliveryGuy.getAllDeliverySession().size() - 1).getDeliveryEndTime();
                }
            }
        }

        // find delivery men with earliest delivery end time
        ArrayList<DeliveryGuy> menWithEarliest = new ArrayList<>();
        if (earliest.equals(clock.getTime())) {
            for (DeliveryGuy deliveryGuy : PickUpOperator.getAllDeliveryGuys()) {
                if (deliveryGuy.getAllDeliverySession().isEmpty()) {
                    menWithEarliest.add(deliveryGuy);
                }
            }
        } else {
            for (DeliveryGuy deliveryGuy : PickUpOperator.getAllDeliveryGuys()) {
                String prevDeliveryEndTime = deliveryGuy.getAllDeliverySession().get(deliveryGuy.getAllDeliverySession().size() - 1).getDeliveryEndTime();
                if (earliest.equals(prevDeliveryEndTime)) {
                    menWithEarliest.add(deliveryGuy);
                }
            }
        }

        // if more than 1 man has earliest delivery end time, allocate delivery by closest distance ONLY to these men
        if (menWithEarliest.size() > 1) {
            PickUpOperator.allocateDeliveryByDistance(menWithEarliest, pOrder);
        } else {
            // only 1 man has earliest delivery end time
            DeliveryGuy theGuy = null;
            if (earliest.equals(clock.getTime())) {
                for (DeliveryGuy deliveryGuy : PickUpOperator.getAllDeliveryGuys()) {
                    if (deliveryGuy.getAllDeliverySession().isEmpty()) {
                        theGuy = deliveryGuy;
                        break;
                    }
                }
            } else {
                for (DeliveryGuy deliveryGuy : PickUpOperator.getAllDeliveryGuys()) {
                    String prevDeliveryEndTime = deliveryGuy.getAllDeliverySession().get(deliveryGuy.getAllDeliverySession().size() - 1).getDeliveryEndTime();
                    if (earliest.equals(prevDeliveryEndTime)) {
                        theGuy = deliveryGuy;
                        break;
                    }
                }
            }

            int goToBranchDuration = 0;
            if (theGuy.getAllDeliverySession().size() >= 1) {
                goToBranchDuration = MyGoogleMap.getTravelDuration(
                        theGuy.getAllDeliverySession().get(theGuy.getAllDeliverySession().size() - 1).getDeliveryEndPosition(),
                        pOrder.getBranchLocation());
            } else {
                goToBranchDuration = MyGoogleMap.getTravelDuration(theGuy.getCurrentPosition(), pOrder.getBranchLocation());
            }
            int deliverDuration = MyGoogleMap.getTravelDuration(pOrder.getBranchLocation(), pOrder.getDeliveryLocation());
            String startTime = SimulatedTime.getTimeAfter(earliest, goToBranchDuration);
            String endTime = SimulatedTime.getTimeAfter(startTime, deliverDuration);
            theGuy.getAllDeliverySession().add(new DeliverySession(pOrder, startTime, endTime));

            // update process
            PickUpOperator.appendToProcess(String.format("Delivery man %d at %s takes the order of customer %d at branch of %s at %s.",
                    theGuy.getDeliveryGuyId(),
                    theGuy.getCurrentPosition(),
                    pOrder.getCustomerId(),
                    pOrder.getPackageName(),
                    pOrder.getBranchLocation().toString()));

        }
    }

    /**
     * Allocate PickUp orders to Delivery Men by their distance with branch &
     * customer
     *
     * @param guys List of delivery guys with same previous order finish
     * delivery time
     * @param order Order to be allocated to one of these delivery guys
     */
    public static void allocateDeliveryByDistance(ArrayList<DeliveryGuy> guys, PickUpOrder pOrder) {
        // get min distance for delivery guy to get to packages branch & then customer
        int minDistance = Integer.MAX_VALUE;
        for (DeliveryGuy guy : guys) {
            int distance = MyGoogleMap.getDistance(guy.getCurrentPosition(), pOrder.getBranchLocation());
            minDistance = minDistance > distance ? distance : minDistance;
        }

        // allocate to first delivery guy with min distance
        for (DeliveryGuy deliveryGuy : guys) {
            int distance = MyGoogleMap.getDistance(deliveryGuy.getCurrentPosition(), pOrder.getBranchLocation());
            if (minDistance == distance) {
                String earliest = deliveryGuy.getAllDeliverySession().isEmpty()
                        ? clock.getTime()
                        : deliveryGuy.getAllDeliverySession().get(deliveryGuy.getAllDeliverySession().size() - 1).getDeliveryEndTime();

                int goToBranchDuration = MyGoogleMap.getTravelDuration(deliveryGuy.getCurrentPosition(), pOrder.getBranchLocation());
                int deliverDuration = MyGoogleMap.getTravelDuration(pOrder.getBranchLocation(), pOrder.getDeliveryLocation());

                String startTime = SimulatedTime.getTimeAfter(earliest, goToBranchDuration);
                String endTime = SimulatedTime.getTimeAfter(startTime, deliverDuration);

                deliveryGuy.getAllDeliverySession().add(new DeliverySession(pOrder, startTime, endTime));

                // update process
                PickUpOperator.appendToProcess(String.format("Delivery man %d at %s takes the order of customer %d at branch of %s at %s.",
                        deliveryGuy.getDeliveryGuyId(),
                        deliveryGuy.getCurrentPosition(),
                        pOrder.getCustomerId(),
                        pOrder.getPackageName(),
                        pOrder.getBranchLocation().toString()));

                break;
            }
        }
    }

    /**
     * Allocate PickUp orders to packages by distance
     */
    public static void allocateOrderByDistance(PickUpOrder pOrder) {
        // find closest branch
        int smallestDistance = Integer.MAX_VALUE;
        if (!PickUpOperator.getPartnerPackages().isEmpty()) {
            for (Package packages : PickUpOperator.getPartnerPackages()) {
                if (pOrder.getPackageName().equals(packages.getName())) {
                    int distance = MyGoogleMap.getDistance(pOrder.getDeliveryLocation(), packages.getPosition());
                    smallestDistance = smallestDistance > distance ? distance : smallestDistance;
                }
            }
        }

        // allocate to closest branch
        if (!PickUpOperator.getPartnerPackages().isEmpty()) {
            for (Package packages : PickUpOperator.getPartnerPackages()) {
                if (pOrder.getPackageName().equals(packages.getName())) {
                    int distance = MyGoogleMap.getDistance(pOrder.getDeliveryLocation(), packages.getPosition());
                    if (smallestDistance == distance) {
                        // tell crabfoodorder where will it take its food from
                        pOrder.setBranchLocation(packages.getPosition());

                        // make allocation
                        String startTime = packages.getNextOrderStartPrepTime();
                        String endTime = SimulatedTime.getTimeAfter(startTime, pOrder.getCookTime());
//                    System.out.println(startTime + " " + endTime);
                        packages.getAllPackageOrders().add(packages.new PackageOrder(startTime,
                                endTime,
                                pOrder.getCustomerId()));

                        // update process
                        PickUpOperator.appendToProcess(String.format("Branch of %s at %s takes the order.",
                                pOrder.getPackageName(),
                                pOrder.getBranchLocation()));

                        break;
                        /**
                         * if one or more branch have same distance, maybe we
                         * could allocate by time, but for now, just break loop
                         */
                    }
                }
            }
        }
    }

    /**
     * Add new strings to process
     *
     * @param lineToAppend
     */
    public static void appendToProcess(String lineToAppend) {
        // append internally to log
        process.set(process.concat(clock.getTime() + " " + lineToAppend).get() + "\n");
    }

    /**
     * load previously saved "log.txt"
     */
    public static void readLog() {
        try {
            Scanner s = new Scanner(new FileInputStream("application-io/log.txt"));
            while (s.hasNextLine()) {
                log.set(log.concat(s.nextLine() + "\n").get());
            }
        } catch (FileNotFoundException ex) {
            System.out.println("\"log.txt\" not found.");
        }
    }

    /**
     * Add new strings to log file
     *
     * @param lineToAppend
     */
    public static void appendToLog(String lineToAppend) {
        // append internally to log
        log.set(log.concat(lineToAppend + "\n").get());

        // append externally to "log.txt"
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileOutputStream("application-io/log.txt", true));
            pw.print(lineToAppend);
        } catch (FileNotFoundException ex) {
            System.out.println("\"log.txt\" not found.");
        } finally {
            pw.close();
        }
    }

    /**
     * update partner packages to "partner-packages.txt"
     */
    public static void updatePartnerPackages() {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileOutputStream("application-io/partner-packages.txt"));
            ArrayList<String> printedRes = new ArrayList<>();
            for (Package packages : PickUpOperator.getPartnerPackages()) {
                if (!printedRes.contains(packages.getName())) {
                    printedRes.add(packages.getName());
                    pw.println(packages.getName());
                    pw.print(Package.toTxtPositions(packages.getName()));
                    pw.print(Package.toTxtDishes(packages.getName()) + "\n");
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("\"partner-packages.txt\" not found.");
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    /**
     * load previously saved "partner-packages.txt"
     */
    public static void readPartnerPackages() {
        if (!PickUpOperator.getPartnerPackages().isEmpty()) {
            PickUpOperator.getPartnerPackages().clear();
        }

        try {
            Scanner s = new Scanner(new FileInputStream("application-io/partner-packages.txt"));

            while (s.hasNextLine()) {
                Package packages = new Package();

                // read packages name
                String packageName = s.nextLine();

                // read packages map symbol
                Character packageMapSymbol = packageName.charAt(0);

                // read packages positions & dishes
                ArrayList<Position> packagePositions = new ArrayList<>();
                ArrayList<Dish> dishes = new ArrayList<>();
                int posCount = 0;
                while (s.hasNextLine()) {
                    String input = s.nextLine();

                    if (Pattern.matches("(\\s)*([0-9])+(\\s)+([0-9])+(\\s)*", input)) {
                        String[] coordinateStr = input.trim().split("\\s");
                        int posX = Integer.parseInt(coordinateStr[0]);
                        int posY = Integer.parseInt(coordinateStr[1]);
                        packagePositions.add(new Position(posX, posY));
                        posCount++;
                    } else {
                        if (!input.isEmpty()) {
                            dishes.add(new Dish(input, Integer.parseInt(s.nextLine())));
                        } else {
                            break;
                        }
                    }
                }

                // after reading, set name, map symbol, positions & dishes
                for (int i = 0; i < posCount; i++) {
                    partnerPackages.add(new Package(packageName,
                            packageMapSymbol, packagePositions.get(i),
                            (ArrayList<Dish>) dishes.clone()));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("\"partner-packages.txt\" not found.");
        }
    }

    /**
     * load previously saved "delivery-guy.txt"
     */
    public static void readAllDeliveryGuys() {
        if (!PickUpOperator.getAllDeliveryGuys().isEmpty()) {
            PickUpOperator.getAllDeliveryGuys().clear();
        }

        try {
            Scanner s = new Scanner(new FileInputStream("application-io/delivery-guy.txt"));

            int numDeliveryGuy = 0;
            while (s.hasNextInt()) {
                numDeliveryGuy = s.nextInt();
            }

            for (int i = 1; i <= numDeliveryGuy; i++) {
                DeliveryGuy deliveryGuy = new DeliveryGuy(i);
                allDeliveryGuys.add(deliveryGuy);
            }

            DeliveryGuy.initPosition();
        } catch (FileNotFoundException ex) {
            System.out.println("\"delivery-guy.txt\" not found.");
        }
    }

    /**
     * Update "delivery-guy.txt"
     */
    public static void updateAllDeliveryGuys() {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileOutputStream("application-io/delivery-guy.txt"));
            pw.print(allDeliveryGuys.size());
            clock.resetTime();
        } catch (FileNotFoundException ex) {
            System.out.println("\"log.txt\" not found.");
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    /**
     * Load preset PickUp orders from "application-order.txt" Time must be sorted
     */
    public static void readAllPresetPickUpOrders() {
        if (!PickUpOperator.getAllPresetPickUpOrders().isEmpty()) {
            PickUpOperator.getAllPresetPickUpOrders().clear();
        }

        try {
            Scanner s = new Scanner(new FileInputStream("application-io/preset-application-order.txt"));

            while (s.hasNextLine()) {
                PickUpOrder pickUpOrder = new PickUpOrder();

                // read order time
                String orderTime = SimulatedTime.parseTimeToString(s.nextLine());

                // read packages
                String packageName = s.nextLine();

                // read dish orders & quantity
                HashMap<String, Integer> dishOrders = new HashMap<>();

                // read delivery location
                Position deliveryLocation = new Position();

                while (s.hasNextLine()) {
                    String input = s.nextLine();
                    if (Pattern.matches("((\\s)*[A-Za-z]+(\\s)*)+((\\s)+[0-9]+(\\s)*)$", input)) {
                        String dishName = input.replaceFirst("[0-9]+(\\s)*$", "").trim();
                        int quanitity = Integer.parseInt(input.replaceAll("\\D+", ""));

                        dishOrders.put(dishName, quanitity);
                    } else if (Pattern.matches("((\\s)*[A-Za-z]+(\\s)*)+", input)) {
                        dishOrders.put(input.trim(), 1);
                    } else if (Pattern.matches("(\\s)*([0-9])+(\\s)+([0-9])+(\\s)*", input)) {
                        String[] coordinateStr = input.trim().split("\\s");
                        int posX = Integer.parseInt(coordinateStr[0]);
                        int posY = Integer.parseInt(coordinateStr[1]);
                        deliveryLocation.setPosition(posX, posY);
                    } else if (input.isEmpty()) {
                        break;
                    }
                }

                pickUpOrder.setPackageName(packageName);
                pickUpOrder.setDishOrders(dishOrders);
                pickUpOrder.setDeliveryLocation(deliveryLocation);
                pickUpOrder.setCookTime(pickUpOrder.calculateCookTime());
                pickUpOrder.setOrderTime(orderTime);

                allPresetPickUpOrders.add(pickUpOrder);
            }
        } catch (FileNotFoundException e) {
            System.out.println("\"application-order.txt\" not found.");
        }
    }

    /**
     * sort PickUp orders whenever any new order is inserted
     */
    public static void sortCfOrders() {
        if (!allPickUpOrders.isEmpty()) {
            // sort allPickUpOrders by order time 
            ArrayList<String> timeList = new ArrayList<>();
            for (PickUpOrder pOrder : allPickUpOrders) {
                if (!timeList.contains(pOrder.getOrderTime())) {
                    timeList.add(pOrder.getOrderTime());
                }
            }
            PickUpOperator.selectionSort(timeList);

            ArrayList<PickUpOrder> sortedCfOrders = new ArrayList<>();
            Iterator itrTimeList = timeList.iterator();
            while (itrTimeList.hasNext()) {
                String time = (String) itrTimeList.next();
                for (PickUpOrder pOrder : allPickUpOrders) {
                    if (pOrder.getOrderTime().equals(time)) {
                        sortedCfOrders.add(pOrder);
                    }
                }
                itrTimeList.remove();
            }

            allPickUpOrders.clear();
            allPickUpOrders = sortedCfOrders;

            // rearrange customerId according to order time
            ArrayList<Integer> sortedCusId = new ArrayList<>();
            for (PickUpOrder pOrder : allPickUpOrders) {
                sortedCusId.add(pOrder.getCustomerId());
            }

            Collections.sort(sortedCusId);

            int i = 0;
            for (PickUpOrder pOrder : allPickUpOrders) {
                pOrder.setCustomerId(sortedCusId.get(i));
                i++;
            }
        }
    }

    public static void selectionSort(ArrayList<String> timeList) {
        for (int i = 0; i < timeList.size() - 1; i++) {
            // find min in the list[i...list.length-1]
            String currentMin = timeList.get(i);
            int currentMinIndex = i;
            for (int j = i + 1; j < timeList.size(); j++) {
                if (SimulatedTime.compareStringTime(currentMin, timeList.get(j)) > 0) {
                    currentMin = timeList.get(j);
                    currentMinIndex = j;
                }
            }

            // swap timeList[i] with timeList[currentMinIndex] if necessary;
            if (currentMinIndex != i) {
                timeList.set(currentMinIndex, timeList.get(i));
                timeList.set(i, currentMin);
            }
        }
    }

    public static IntegerProperty getTotalPickUpOrder() {
        return totalPickUpOrder;
    }

    public static void setTotalPickUpOrder(IntegerProperty totalPickUpOrder) {
        PickUpOperator.totalPickUpOrder = totalPickUpOrder;
    }

    public static ArrayList<PickUpOrder> getAllPresetPickUpOrders() {
        return allPresetPickUpOrders;
    }

    public static void setAllPresetPickUpOrders(ArrayList<PickUpOrder> allPresetPickUpOrders) {
        PickUpOperator.allPresetPickUpOrders = allPresetPickUpOrders;
    }

    public static StringProperty getProcess() {
        return process;
    }

    public static void setProcess(StringProperty process) {
        PickUpOperator.process = process;
    }

    public static StringProperty getLog() {
        return log;
    }

    public static void setLog(StringProperty log) {
        PickUpOperator.log = log;
    }

    public static MyGoogleMap getMasterMap() {
        return masterMap;
    }

    public static void setMasterMap(MyGoogleMap masterMap) {
        PickUpOperator.masterMap = masterMap;
    }

    public static ArrayList<Package> getPartnerPackages() {
        return partnerPackages;
    }

    public static void setPartnerPackages(ArrayList<Package> partnerPackages) {
        PickUpOperator.partnerPackages = partnerPackages;
    }

    public static ArrayList<PickUpOrder> getAllPickUpOrders() {
        return allPickUpOrders;
    }

    public static void setAllPickUpOrders(ArrayList<PickUpOrder> allPickUpOrders) {
        PickUpOperator.allPickUpOrders = allPickUpOrders;
    }

    public static ArrayList<DeliveryGuy> getAllDeliveryGuys() {
        return allDeliveryGuys;
    }

    public static void setAllDeliveryGuys(ArrayList<DeliveryGuy> allDeliveryGuys) {
        PickUpOperator.allDeliveryGuys = allDeliveryGuys;
    }

    public static class PickUpOrder {

        // if not stated "later", then set upon creation
        private Integer customerId; // set later
        private String orderTime;
        private String packageName;
        private HashMap<String, Integer> dishOrders;
        private Position deliveryLocation;
        private Position branchLocation; // set later
        private StringProperty status; // set later
        private int cookTime;

        public PickUpOrder(String packageName, HashMap<String, Integer> dishOrders, Position deliveryLocation) {
            this.customerId = -1;
            this.orderTime = clock.getTime();
            this.packageName = packageName;
            this.dishOrders = dishOrders;
            this.deliveryLocation = deliveryLocation;
            this.branchLocation = new Position(0, 0);
            this.status = new SimpleStringProperty("no status");
            this.cookTime = -1;
        }

        public PickUpOrder() {
            this.customerId = -1;
            this.orderTime = clock.getTime();
            this.packageName = "no name";
            this.dishOrders = new HashMap<>();
            this.deliveryLocation = new Position(0, 0);
            this.branchLocation = new Position(0, 0);
            this.status = new SimpleStringProperty("no status");
            this.cookTime = -1;
        }

        public Position getBranchLocation() {
            return branchLocation;
        }

        public void setBranchLocation(Position branchLocation) {
            this.branchLocation = branchLocation;
        }

        public StringProperty getStatus() {
            return status;
        }

        public void setStatus(StringProperty status) {
            this.status = status;
        }

        public int calculateCookTime() {
            int duration = -1;
            for (Package packages : PickUpOperator.getPartnerPackages()) {
                if (packageName.trim().equals(packages.getName().trim())) {
                    for (Map.Entry dish : dishOrders.entrySet()) {
                        int timeNeeded = packages.getCookTime(dish.getKey().toString()) * Integer.parseInt(dish.getValue().toString());
                        if (duration < timeNeeded) {
                            duration = timeNeeded;
                        }
                        duration = duration < timeNeeded ? timeNeeded : duration;
                    }
                    break;
                }
            }
            return duration;
        }

        public int getCookTime() {
            return cookTime;
        }

        public void setCookTime(int cookTime) {
            this.cookTime = cookTime;
        }

        public String toString() {
            String result = "";
            result += customerId + "\n";
            result += orderTime + "\n";
            result += packageName + "\n";
            for (Map.Entry<String, Integer> entry : dishOrders.entrySet()) {
                result += entry.getKey() + " ";
                result += entry.getValue() + "\n";
            }
            result += deliveryLocation + "\n";
            return result;
        }

        /**
         * Just in case if need to write to "preset-application-order.txt"
         *
         * @return PickUpOrder string
         */
        public String toTxtString() {
            String result = "";
            result += orderTime + "\n";
            result += packageName + "\n";
            for (Map.Entry<String, Integer> entry : dishOrders.entrySet()) {
                result += entry.getKey() + " ";
                result += entry.getValue() + "\n";
            }
            result += deliveryLocation + "\n";
            return result;
        }

        public Integer getCustomerId() {
            return customerId;
        }

        public void setCustomerId(Integer customerId) {
            this.customerId = customerId;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public HashMap<String, Integer> getDishOrders() {
            return dishOrders;
        }

        public void setDishOrders(HashMap<String, Integer> dishOrders) {
            this.dishOrders = dishOrders;
        }

        public Position getDeliveryLocation() {
            return deliveryLocation;
        }

        public void setDeliveryLocation(Position deliveryLocation) {
            this.deliveryLocation = deliveryLocation;
        }

    }
}
