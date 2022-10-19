package application;

import application.PackageOperator.PackageOrder;
import static application.Main.clock;
import application.MyGoogleMap.Position;
import java.util.ArrayList;

class DeliveryGuy {

    private int deliveryGuyId;
    private Position originalPosition;
    private Position currentPosition;
    private ArrayList<DeliverySession> allDeliverySession;

    public DeliveryGuy(int deliveryGuyId, Position currentPosition) {
        this.deliveryGuyId = deliveryGuyId;
        this.originalPosition = new Position(0, 0);
        this.currentPosition = currentPosition;
        this.allDeliverySession = new ArrayList<>();
    }

    public DeliveryGuy(int deliveryGuyId) {
        this.deliveryGuyId = deliveryGuyId;
        this.originalPosition = new Position(0, 0);
        this.currentPosition = new Position(0, 0);
        this.allDeliverySession = new ArrayList<>();
    }

    public static void initPosition() {
        int x = 0;
        int y = 0;
        int count = 0;
        for (Package packages : PackageOperator.getPartnerPackage()) {
            x += packages.getPosition().getPosX();
            y += packages.getPosition().getPosY();
            count++;
        }

        x = x / count;
        y = y / count;

        for (DeliveryGuy guy : PackageOperator.getAllDeliveryGuys()) {
            guy.setCurrentPosition(new Position(x, y));
            guy.setOriginalPosition(new Position(x, y));
        }
    }

    /**
     * update all delivery guy's position every simulated clock tick
     */
    public static void updateAllDeliveryGuyPos() {
        if (!PackageOperator.getAllDeliveryGuys().isEmpty()) {
            for (DeliveryGuy guy : PackageOperator.getAllDeliveryGuys()) {
                if (!guy.getAllDeliverySession().isEmpty()) {
                    DeliverySession session = guy.getAllDeliverySession().get(0);
                    if (SimulatedTime.compareStringTime(clock.getTime(), session.getDeliveryStartTime()) < 0) {
                        // move to restaurant branch to "fetch prepared order"
                        if (guy.getCurrentPosition().getPosX() - session.getPackageOrderTBD().getBranchLocation().getPosX() != 0) {
                            if (guy.getCurrentPosition().getPosX() - session.getPackageOrderTBD().getBranchLocation().getPosX() < 0) {
                                guy.getCurrentPosition().setPosX(guy.getCurrentPosition().getPosX() + 1);
                            } else if (guy.getCurrentPosition().getPosX() - session.getPackageOrderTBD().getBranchLocation().getPosX() > 0) {
                                guy.getCurrentPosition().setPosX(guy.getCurrentPosition().getPosX() - 1);
                            }
                        } else if (guy.getCurrentPosition().getPosY() - session.getPackageOrderTBD().getBranchLocation().getPosY() != 0) {
                            if (guy.getCurrentPosition().getPosY() - session.getPackageOrderTBD().getBranchLocation().getPosY() < 0) {
                                guy.getCurrentPosition().setPosY(guy.getCurrentPosition().getPosY() + 1);
                            } else if (guy.getCurrentPosition().getPosY() - session.getPackageOrderTBD().getBranchLocation().getPosY() > 0) {
                                guy.getCurrentPosition().setPosY(guy.getCurrentPosition().getPosY() - 1);
                            }
                        }
                    } else if (SimulatedTime.compareStringTime(clock.getTime(), session.getDeliveryStartTime()) >= 0
                            && SimulatedTime.compareStringTime(clock.getTime(), session.getDeliveryEndTime()) < 0) {
                        // move from restaurant branch to delivery location
                        if (guy.getCurrentPosition().getPosX() - session.getDeliveryEndPosition().getPosX() != 0) {
                            if (guy.getCurrentPosition().getPosX() - session.getDeliveryEndPosition().getPosX() < 0) {
                                guy.getCurrentPosition().setPosX(guy.getCurrentPosition().getPosX() + 1);
                            } else if (guy.getCurrentPosition().getPosX() - session.getDeliveryEndPosition().getPosX() > 0) {
                                guy.getCurrentPosition().setPosX(guy.getCurrentPosition().getPosX() - 1);
                            }
                        } else if (guy.getCurrentPosition().getPosY() - session.getDeliveryEndPosition().getPosY() != 0) {
                            if (guy.getCurrentPosition().getPosY() - session.getDeliveryEndPosition().getPosY() < 0) {
                                guy.getCurrentPosition().setPosY(guy.getCurrentPosition().getPosY() + 1);
                            } else if (guy.getCurrentPosition().getPosY() - session.getDeliveryEndPosition().getPosY() > 0) {
                                guy.getCurrentPosition().setPosY(guy.getCurrentPosition().getPosY() - 1);
                            }
                        }
                    }
                } else {
                    // move back to midpoint of all restaurants
                    if (guy.getCurrentPosition().getPosX() - guy.getOriginalPosition().getPosX() != 0) {
                        if (guy.getCurrentPosition().getPosX() - guy.getOriginalPosition().getPosX() < 0) {
                            guy.getCurrentPosition().setPosX(guy.getCurrentPosition().getPosX() + 1);
                        } else if (guy.getCurrentPosition().getPosX() - guy.getOriginalPosition().getPosX() > 0) {
                            guy.getCurrentPosition().setPosX(guy.getCurrentPosition().getPosX() - 1);
                        }
                    } else if (guy.getCurrentPosition().getPosY() - guy.getOriginalPosition().getPosY() != 0) {
                        if (guy.getCurrentPosition().getPosY() - guy.getOriginalPosition().getPosY() < 0) {
                            guy.getCurrentPosition().setPosY(guy.getCurrentPosition().getPosY() + 1);
                        } else if (guy.getCurrentPosition().getPosY() - guy.getOriginalPosition().getPosY() > 0) {
                            guy.getCurrentPosition().setPosY(guy.getCurrentPosition().getPosY() - 1);
                        }
                    }

                }
            }
        }
    }

    @Override
    public String toString() {
        return String.format("Delivery Man ID: %d\nCurrent Position: %s", deliveryGuyId, currentPosition.toString());
    }

    public Position getOriginalPosition() {
        return originalPosition;
    }

    public void setOriginalPosition(Position originalPosition) {
        this.originalPosition = originalPosition;
    }

    public int getDeliveryGuyId() {
        return deliveryGuyId;
    }

    public void setDeliveryGuyId(int deliveryGuyId) {
        this.deliveryGuyId = deliveryGuyId;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Position currentPosition) {
        this.currentPosition = currentPosition;
    }

    public ArrayList<DeliverySession> getAllDeliverySession() {
        return allDeliverySession;
    }

    public void setAllDeliverySession(ArrayList<DeliverySession> allDeliverySession) {
        this.allDeliverySession = allDeliverySession;
    }

    static class DeliverySession {

        private PackageOrder PackageOrderTBD;
        private Position deliveryStartPosition;
        private Position deliveryEndPosition;
        private String deliveryStartTime;
        private String deliveryEndTime;
        private int deliveryDuration;

        public DeliverySession(PackageOrder PackageOrderTBD, String deliveryStartTime, String deliveryEndTime) {
            this.PackageOrderTBD = PackageOrderTBD;
            this.deliveryStartPosition = PackageOrderTBD.getBranchLocation();
            this.deliveryEndPosition = PackageOrderTBD.getDeliveryLocation();
            this.deliveryStartTime = deliveryStartTime;
            this.deliveryEndTime = deliveryEndTime;
            this.deliveryDuration = SimulatedTime.differenceTime(deliveryStartTime, deliveryEndTime);
        }

        public int getDeliveryDuration() {
            return deliveryDuration;
        }

        public void setDeliveryDuration(int deliveryDuration) {
            this.deliveryDuration = deliveryDuration;
        }

        public PackageOrder getPackageOrderTBD() {
            return PackageOrderTBD;
        }

        public void setPackageOrderTBD(PackageOrder PackageOrderTBD) {
            this.PackageOrderTBD = PackageOrderTBD;
        }

        public Position getDeliveryStartPosition() {
            return deliveryStartPosition;
        }

        public void setDeliveryStartPosition(Position deliveryStartPosition) {
            this.deliveryStartPosition = deliveryStartPosition;
        }

        public Position getDeliveryEndPosition() {
            return deliveryEndPosition;
        }

        public void setDeliveryEndPosition(Position deliveryEndPosition) {
            this.deliveryEndPosition = deliveryEndPosition;
        }

        public String getDeliveryStartTime() {
            return deliveryStartTime;
        }

        public void setDeliveryStartTime(String deliveryStartTime) {
            this.deliveryStartTime = deliveryStartTime;
        }

        public String getDeliveryEndTime() {
            return deliveryEndTime;
        }

        public void setDeliveryEndTime(String deliveryEndTime) {
            this.deliveryEndTime = deliveryEndTime;
        }

    }
}
