package application;

import static application.Main.clock;
import application.MyGoogleMap.Position;
import java.util.ArrayList;

class Package {

    private String name;
    private Character mapSymbol;
    private Position position;
    private ArrayList<Dish> allAvailableDishes;
    private ArrayList<PackageOrder> allPackageOrders;

    public Package() {
        this.name = "name not set";
        this.mapSymbol = '#';
        this.position = new Position(0, 0);
        this.allAvailableDishes = new ArrayList<>();
        this.allPackageOrders = new ArrayList<>();
    }

    public Package(String name) {
        this.name = name;
        this.mapSymbol = name.charAt(0);
        this.position = new Position(0, 0);
        this.allAvailableDishes = new ArrayList<>();
        this.allPackageOrders = new ArrayList<>();
    }

    public Package(String name, Character mapSymbol, Position position, ArrayList<Dish> allAvailableDishes) {
        this.name = name;
        this.mapSymbol = mapSymbol;
        this.position = position;
        this.allAvailableDishes = allAvailableDishes;
        this.allPackageOrders = new ArrayList<>();
    }

    /**
     *
     * @param restaurantName
     * @return all branch positions of given restaurant
     */
    public static String toTxtPositions(String packageName) {
        String result = "";
        if (!PackageOperator.getPartnerPackage().isEmpty()) {
            for (Package packages : PackageOperator.getPartnerPackage()) {
                if (packages.getName().equals(packageName)) {
                    String pos = packages.getPosition().toString();
                    pos = pos.substring(1, pos.length() - 1).replaceFirst(",", "");
                    result += pos + "\n";
                }
            }
        }
//        if (!allAvailableDishes.isEmpty()) {
//            for (Dish dish : allAvailableDishes) {
//                result += "\n" + dish.getName();
//                result += "\n" + dish.getFoodPrepareDuration();
//                result += "\n";
//            }
//        }
        return result;
    }

    public static String toTxtDishes(String packageName) {
        String result = "";
        if (!PackageOperator.getPartnerPackage().isEmpty()) {
            for (Package packages : PackageOperator.getPartnerPackage()) {
                if (packages.getName().equals(packageName)) {
                    if (!packages.getAllAvailableDishes().isEmpty()) {
                        for (Dish dish : packages.getAllAvailableDishes()) {
                            result += dish.getName() + "\n";
                            result += dish.getFoodPrepareDuration() + "\n";
                        }
                        break;
                    }
                }
            }
        }
        return result;
    }

    /**
     * if the time now is earlier than the finish prep time of the previous
     * order return the finish prep time + 1 min, else return the time now
     *
     * @return the preparation start time of the next order
     */
    public String getNextOrderStartPrepTime() {
        if (!allPackageOrders.isEmpty()) {
            if (SimulatedTime.compareStringTime(clock.getTime(), allPackageOrders.get(allPackageOrders.size() - 1).getEndTime()) <= 0) {
//                return SimulatedTime.addStringTime(allRestaurantOrders.get(allRestaurantOrders.size() - 1).getEndTime(), "00:01");
                return allPackageOrders.get(allPackageOrders.size() - 1).getEndTime();
            } else {
                return clock.getTime();
            }
        } else {
            return clock.getTime();
        }
    }

    public int getCookTime(String dishName) {
        for (Dish dish : allAvailableDishes) {
            if (dishName.trim().equals(dish.getName().trim())) {
                return dish.getFoodPrepareDuration();
            }
        }
        return -1;
    }

    public ArrayList<PackageOrder> getAllRestaurantOrders() {
        return allPackageOrders;
    }

    public void setAllPackageOrders(ArrayList<PackageOrder> allPackageOrders) {
        this.allPackageOrders = allPackageOrders;
    }

    public Character getMapSymbol() {
        return mapSymbol;
    }

    public void setMapSymbol(Character mapSymbol) {
        this.mapSymbol = mapSymbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public ArrayList<Dish> getAllAvailableDishes() {
        return allAvailableDishes;
    }

    public void setAllAvailableDishes(ArrayList<Dish> allAvailableDishes) {
        this.allAvailableDishes = allAvailableDishes;
    }

    class PackageOrder {

        private String startTime = "-1";
        private String endTime = "-1";
        private int duration = SimulatedTime.differenceTime(startTime, endTime);
        private int customerId = -1;

        public PackageOrder(String startTime, String endTime, int customerId) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.customerId = customerId;
        }

        public int getCustomerId() {
            return customerId;
        }

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }
    }

    static class Dish {

        private String name;
        private int foodPrepareDuration;

        Dish(String name, int foodPrepareDuration) {
            this.name = name;
            this.foodPrepareDuration = foodPrepareDuration;
        }

        public Dish(String name) {
            this.name = name;
            this.foodPrepareDuration = -1;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getFoodPrepareDuration() {
            return foodPrepareDuration;
        }

        public void setFoodPrepareDuration(int foodPrepareDuration) {
            this.foodPrepareDuration = foodPrepareDuration;
        }
    }
}
