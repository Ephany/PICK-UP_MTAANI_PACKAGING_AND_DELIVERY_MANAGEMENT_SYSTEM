package application;

//Describes the imports below
/*
 *The first three lines of code import various classes that are needed for the program to run. The PickUpOrder class is used to represent an order from a crab food packages. The DeliverySession class is used to manage the delivery of orders. The Position class is used to represent a position on a map.
 *The next three lines of code define some variables that will be used throughout the program. The first variable, order, is an instance of the PickUpOrder class. The second variable, deliverySession, is an instance of the DeliverySession class. The third variable, position, is an instance of the Position class.
 *The next four lines of code define a list of dishes that can be ordered from the crab food packages. The list is represented by an instance of the ArrayList class. The list is populated with instances of the Dish class.
 *The next line of code defines a map of delivery times for each dish. The map is represented by an instance of the HashMap class. The map is populated with key-value pairs, where the key is a dish and the value is the delivery time for that dish.
 *The next line of code defines an observable list of orders. The list is represented by an instance of the ObservableList class. The list is populated with instances of the PickUpOrder class.
 *The next line of code defines an observable map of delivery times. The map is represented by an instance of the ObservableMap class. The map is populated with key-value pairs, where the key is a dish and the value is the delivery time for that dish.
 *The next line of code defines an integer property that represents the number of orders in the list. The property is represented by an instance of the IntegerProperty class.
 *The next line of code defines a string property that represents the list of orders as a string. The property is represented by an instance of the StringProperty class.
 *The next line of code defines a change listener that is used to update the string property when the list of orders changes. The listener is an instance of the ChangeListener class.
 *The next line of code defines a list change listener that is used to update the integer property when the list of orders changes. The listener is an instance of the ListChangeListener class.
 *The next line of code defines a map change listener that is used to update the delivery times when the map of delivery times changes. The listener is an instance of the MapChangeListener class.
 *The next line of code defines a button that is used to add an order to the list. The button is an instance of the Button class.
 *The next line of code defines a label that is used to display the number of orders in the list. The label is an instance of the Label class.
 *The next line of code defines a list view that is used to display the list of orders. The list view is an instance of the ListView class.
 *The next line of code defines a scroll pane that is used to scroll the list view. The scroll pane is an instance of the ScrollPane class.
 *The next line of code defines a combo box that is used to select a dish from the list of dishes. The combo box is an instance of the ComboBox class.
 *The next two lines of code set the alignment and padding for the button.
 *The next two lines of code set the alignment and padding for the label.
 *The next two lines of code set the alignment and padding for the list view.
 *The next two lines of code set the alignment and padding for the scroll pane.
 *The next two lines of code set the alignment and padding for the combo box.
 *The next line of code adds the button, label, list view, scroll pane, and combo box to the scene.
 *The next line of code sets the title of the stage.
 *The next line of code sets the scene of the stage.
 *The next line of code shows the stage.
 *The next line of code waits for the stage to be closed before exiting the program. 
 */
import application.PickUpOperator.PickUpOrder;
import application.DeliveryGuy.DeliverySession;
import application.MyGoogleMap.Position;
import application.Package.Dish;
import application.Package.PackageOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/* Describes the function and code below.
 * The first line of code creates a public class called Main that extends the Application class. 
 * This means that the Main class inherits all the functionality of the Application class.
 * The second and third lines of code create IntegerProperty variables called stageHeight and stageWidth. 
 * These variables hold the height and width of the stage, respectively.
 * The fourth line of code sets the value of the stageHeight variable to 828. 
 * This is the height of the stage in pixels.
 * The fifth line of code sets the value of the stageWidth variable to 1500. 
 * This is the width of the stage in pixels.
 */
public class Main extends Application {

    IntegerProperty stageHeight = new SimpleIntegerProperty(828);
    IntegerProperty stageWidth = new SimpleIntegerProperty(1500);

    /* Describes the function below
     *public: This is an access modifier, which specifies who can access a certain class, method, or variable. 
     *static: This means that the method can be called without creating an instance of the class. 
     *void: This specifies that the method does not return a value. 
     *main: This is the name of the method. 
     *String[]: This specifies that the method takes in an array of strings as an argument. 
     *launch: This calls the launch method, which starts the JavaFX application.
     */
    public static void main(String[] args) {
        launch(args);
    }

    
    /*Describes the codes below.
     *This code creates a new SimulatedTime object and assigns it to the clock variable. This SimulatedTime object will be used to track the current time in the simulation.
     *Next, a new PickUpOperator object is created and assigned to the operator variable. This PickUpOperator object will be used to manage the crab food orders in the simulation.
     *The closeTime variable is initialized to "21:20", which represents the closing time for the crab food packages.
     *Finally, the code creates several Scene objects. These Scene objects will be used to represent the different screens (or "scenes") in the simulation.
     *The txtTimeStamp Text object is used to display the current time on the menu scene.
     *The tableOS TableView object is used to display the status of all outstanding orders on the menu scene.
     *The pOrderList ObservableList is used to store the data that is displayed in the tableOS TableView.
     *The txtareaPackageName TextArea object is used to display the name of the packages that is being edited on the edit packages scene.
     *The obsListPackage ObservableList is used to store the names of all the packages in the simulation. This list is displayed on the edit packages scene.
     *The resToEdit StringProperty is used to store the name of the packages that is currently being edited. This property is bound to the txtareaPackageName TextArea object.
     *The gridPackageLoc GridPane object is used to display the locations of the packages on the edit packages scene.
     *The obsListDishes ObservableList is used to store the names of all the dishes in the simulation. This list is displayed on the edit dish scene.
     *The dishToEdit StringProperty is used to store the name of the dish that is currently being edited. This property is bound to the txtareaDishName TextArea object.
     *The listDishes ListView object is used to display the list of dishes on the edit dish scene.
     *The txtareaDishName TextArea object is used to display the name of the dish that is being edited on the edit dish scene.
     *The spinnerDishPrepTime Spinner object is used to display and edit the prep time of the dish that is being edited on the edit dish scene.
     *The flagAddRes boolean variable is used to track whether a new packages is being added or an existing packages is being edited.
     *The flagAddDish boolean variable is used to track whether a new dish is being added or an existing dish is being edited.
     *The btnSC Button object is used to simulate the arrival of a customer on the simulate customer scene.
     *The txtOrderTime Text object is used to display the order time of the customer on the simulate customer scene.
     *The gridMap GridPane object is used to display the map of the packages on the view map scene.
     */
    // make clock
    public volatile static SimulatedTime clock = new SimulatedTime();

    // make PickUp operator
    public static PickUpOperator operator = new PickUpOperator();

    // closing time
    String closeTime = "21:20";

    // all scenes
    Scene sceneMenu, sceneMR, sceneMD, sceneVOL, sceneSC, sceneVM, sceneER, sceneEDs, sceneED;

    // Menu time stamp
    Text txtTimeStamp = new Text();

    // status table
    TableView tableOS = new TableView();

    // list to update status table
    private ObservableList<PickUpOrder> pOrderList = FXCollections.observableArrayList();

    // Text area to put name of selected packages to edit
    TextArea txtareaPackageName = new TextArea();

    // Package list in sceneMR
    ObservableList<String> obsListPackage = FXCollections.observableArrayList();

    // Package chosen to edit in sceneER
    StringProperty resToEdit = new SimpleStringProperty("");

    // Locations of packages chosen to edit in sceneER
    GridPane gridPackageLoc = new GridPane();

    // Observable dish list in sceneEDs
    ObservableList<String> obsListDishes = FXCollections.observableArrayList();

    // Actual name dish to edit
    StringProperty dishToEdit = new SimpleStringProperty("");

    // Dish list in makeSceneEDs
    ListView<String> listDishes = new ListView(obsListDishes);

    // Text area to put name of selected dish to edit
    TextArea txtareaDishName = new TextArea();

    // Temporary list of dishes to be added into new packages
    ArrayList<Dish> dishesToAddTemp = new ArrayList<>();

    // Spinner to put dish prep time of dish to edit
    Spinner spinnerDishPrepTime = new Spinner(1, 60, 5);

    // Flag to indicate add operation on packages
    boolean flagAddRes = false;

    // Flag to indicate add operation on dish
    boolean flagAddDish = false;

    // Button to simulate customer
    Button btnSC = new Button("Simulate Customer");

    // Order time in simulate customer
    Text txtOrderTime = new Text();

    // Grid area for view map
    GridPane gridMap = new GridPane();
    
    /*Describe the function below.
 *The start function is used to start the primaryStage. 
 *The thread timeThread is used to create a new thread. 
 *The updater is used to update the stage. 
 *The if statement is used to check if the clock is equal to 00:00. 
 *The PickUpOperator is used to get the process and set it to an empty string. 
 *The PickUpOperator is used to append the string "A new day has started!" to the process. 
 *The pOrderList is used to clear the status table. The logHeader is used to create a new log section. 
 *The btnSC is used to enable the simulate customer button. The PickUpOperator is used to get the totalPickUpOrder and set it to 0. 
 *The PickUpOperator is used to clear the allPresetPickUpOrders. The PickUpOperator is used to read the allPresetPickUpOrders.
 */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Thread timeThread = new Thread(new Runnable() {

            @Override
            public void run() {
                Runnable updater = new Runnable() {

                    @Override
                    public void run() {
                        // things to do when new day comes
                        if (clock.getTime().equals("00:00")) {
                            // new day
                            PickUpOperator.getProcess().set("");
                            PickUpOperator.appendToProcess("A new day has started!");

                            // reset status table
                            pOrderList.clear();

                            // new log section
                            String logHeader = String.format("\n\n| %11s | %10s | %21s | %14s | %16s | %16s | %14s | %-20s | %6s ",
                                    "Customer ID", "Order Time", "Finished Cooking Time",
                                    "Delivered Time", "Cooking Duration", "Deliver Duration",
                                    "Total Duration", "Package", "Branch");
                            PickUpOperator.appendToLog(logHeader);

                            // enable simulate customer button
                            btnSC.setDisable(false);

                            // partially reset program
                            PickUpOperator.getTotalPickUpOrder().setValue(0);

                            PickUpOperator.getAllPresetPickUpOrders().clear();
                            PickUpOperator.readAllPresetPickUpOrders();

                            PickUpOperator.getAllPickUpOrders().clear();
                        }

                        if (clock.getTime().equals(closeTime)) {
                            btnSC.setDisable(true);
                            PickUpOperator.appendToProcess("PickUp is done for the day. Stopped accepting new orders.");
                        }

                        if (SimulatedTime.compareStringTime(clock.getTime(), closeTime) < 0) {
                            // check for start of preset PickUpOrders to add into main list when time comes
                            if (!PickUpOperator.getAllPresetPickUpOrders().isEmpty()) {
                                Iterator itrCfOrder = PickUpOperator.getAllPresetPickUpOrders().iterator();
                                while (itrCfOrder.hasNext()) {
                                    PickUpOrder pOrder = (PickUpOrder) itrCfOrder.next();
                                    if (pOrder.getOrderTime().equals(clock.getTime())) {
                                        // update variables in preset PickUp order
                                        pOrder.getStatus().setValue("New order");
                                        pOrder.setCustomerId(PickUpOperator.getTotalPickUpOrder().get() + 1);

                                        // update total number of PickUp orders
                                        PickUpOperator.getTotalPickUpOrder().set(PickUpOperator.getTotalPickUpOrder().get() + 1);

                                        // add it to main list of PickUp orders
                                        PickUpOperator.getAllPickUpOrders().add(pOrder);
                                        PickUpOperator.sortCfOrders();

                                        // drop it from the preset list
                                        itrCfOrder.remove();
                                    }
                                }
                            }

                            // check for start of PickUpOrders (this is the main list)
                            if (!PickUpOperator.getAllPickUpOrders().isEmpty()) {
                                for (PickUpOrder pOrder : PickUpOperator.getAllPickUpOrders()) {
                                    if (pOrder.getOrderTime().equals(clock.getTime())) {
                                        // update process
                                        String processOrder = String.format("Customer %d at %s wants to order ",
                                                pOrder.getCustomerId(), pOrder.getDeliveryLocation().toString());
                                        int count = 0;
                                        for (Map.Entry mapElement : pOrder.getDishOrders().entrySet()) {
                                            if (count == pOrder.getDishOrders().size() - 1) {
                                                processOrder += pOrder.getDishOrders().size() == 1 ? "" : " & ";
                                                processOrder += mapElement.getValue() + " " + mapElement.getKey() + " ";
                                            } else {
                                                processOrder += mapElement.getValue() + " " + mapElement.getKey();
                                                processOrder += count == pOrder.getDishOrders().size() - 2 ? "" : ", ";
                                            }
                                            count++;
                                        }
                                        processOrder += "from " + pOrder.getPackageName() + ".";
                                        PickUpOperator.appendToProcess(processOrder);

                                        // allocate order
                                        pOrder.getStatus().setValue("New order");
                                        PickUpOperator.allocateOrderByDistance(pOrder);

                                        // add to status table
                                        if (!pOrderList.contains(pOrder)) {
                                            pOrderList.add(pOrder);
                                        }
                                    }
                                }
                            }
                        }

                        // check for start & end of food preparation at all packages branches
                        if (!PickUpOperator.getPartnerPackages().isEmpty()) {
                            for (Package packages : PickUpOperator.getPartnerPackages()) {
                                if (!packages.getAllPackageOrders().isEmpty()) {
                                    Iterator itrROrder = packages.getAllPackageOrders().iterator();
                                    while (itrROrder.hasNext()) {
                                        PackageOrder rOrder = (PackageOrder) itrROrder.next();
                                        if (rOrder.getStartTime().equals(clock.getTime())) {
                                            // update process
                                            PickUpOperator.appendToProcess(
                                                    String.format("Branch of %s at %s starts preparing order of customer %d.",
                                                            packages.getName(),
                                                            packages.getPosition(),
                                                            rOrder.getCustomerId()));

                                            // update status table
                                            if (!PickUpOperator.getAllPickUpOrders().isEmpty()) {
                                                for (PickUpOrder pOrder : PickUpOperator.getAllPickUpOrders()) {
                                                    if (pOrder.getCustomerId() == rOrder.getCustomerId()) {
                                                        pOrder.getStatus().setValue("Preparing...");
                                                    }
                                                }
                                            }
                                        } else if (rOrder.getEndTime().equals(clock.getTime())) {
                                            // remove packages order
                                            itrROrder.remove();

                                            // update process
                                            PickUpOperator.appendToProcess(
                                                    String.format("Branch of %s at %s finishes preparing order of customer %d.",
                                                            packages.getName(),
                                                            packages.getPosition(),
                                                            rOrder.getCustomerId()));

                                            // pass to delivery man (via delivery man allocating algo)
                                            for (PickUpOrder pOrder : PickUpOperator.getAllPickUpOrders()) {
                                                if (pOrder.getCustomerId() == rOrder.getCustomerId()) {
                                                    PickUpOperator.allocateDeliveryByFinishTime(pOrder);
                                                }
                                            }

                                            // update status table
                                            if (!PickUpOperator.getAllPickUpOrders().isEmpty()) {
                                                for (PickUpOrder pOrder : PickUpOperator.getAllPickUpOrders()) {
                                                    if (pOrder.getCustomerId() == rOrder.getCustomerId()) {
                                                        pOrder.getStatus().setValue("Prepared");
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // check for start & end of delivery at all delivery guys
                        if (!PickUpOperator.getAllDeliveryGuys().isEmpty()) {
                            for (DeliveryGuy deliveryGuy : PickUpOperator.getAllDeliveryGuys()) {
                                if (!deliveryGuy.getAllDeliverySession().isEmpty()) {
                                    Iterator itrSession = deliveryGuy.getAllDeliverySession().iterator();
                                    while (itrSession.hasNext()) {
                                        DeliverySession session = (DeliverySession) itrSession.next();
                                        if (session.getDeliveryStartTime().equals(clock.getTime())) {
                                            // update process
                                            PickUpOperator.appendToProcess(
                                                    String.format("Delivery man %d at %s starts delivering order to customer %d at %s.",
                                                            deliveryGuy.getDeliveryGuyId(),
                                                            deliveryGuy.getCurrentPosition().toString(),
                                                            session.getPickUpOrderTBD().getCustomerId(),
                                                            session.getDeliveryEndPosition()));

                                            // update status table
                                            if (!PickUpOperator.getAllPickUpOrders().isEmpty()) {
                                                for (PickUpOrder pOrder : PickUpOperator.getAllPickUpOrders()) {
                                                    if (pOrder.getCustomerId() == session.getPickUpOrderTBD().getCustomerId()) {
                                                        pOrder.getStatus().setValue("Delivering...");
                                                    }
                                                }
                                            }
                                        } else if (session.getDeliveryEndTime().equals(clock.getTime())) {
                                            PickUpOrder endCfOrder = session.getPickUpOrderTBD();

                                            // update process
                                            PickUpOperator.appendToProcess(
                                                    String.format("Delivery man %d at %s finishes delivering order to customer %d at %s.",
                                                            deliveryGuy.getDeliveryGuyId(),
                                                            deliveryGuy.getCurrentPosition().toString(),
                                                            session.getPickUpOrderTBD().getCustomerId(),
                                                            session.getDeliveryEndPosition()));

                                            // update status table
                                            if (!PickUpOperator.getAllPickUpOrders().isEmpty()) {
                                                for (PickUpOrder pOrder : PickUpOperator.getAllPickUpOrders()) {
                                                    if (pOrder.getCustomerId().equals(endCfOrder.getCustomerId())) {
                                                        pOrder.getStatus().setValue("Delivered");
                                                    }
                                                }
                                            }

                                            // key in to log 
                                            PickUpOperator.appendToLog(
                                                    String.format("\n| %-11s | %-10s | %-21s | %-14s | %-16s | %-16s | %-14s | %-20s | %-6s ",
                                                            endCfOrder.getCustomerId(), endCfOrder.getOrderTime(),
                                                            SimulatedTime.getTimeAfter(endCfOrder.getOrderTime(), endCfOrder.getCookTime()),
                                                            session.getDeliveryEndTime(), endCfOrder.getCookTime(),
                                                            session.getDeliveryDuration(),
                                                            SimulatedTime.differenceTime(endCfOrder.getOrderTime(), session.getDeliveryEndTime()),
                                                            endCfOrder.getPackageName(),
                                                            endCfOrder.getBranchLocation()));

                                            // remove session
                                            itrSession.remove();

                                            // remove from main PickUp order list
                                            if (!PickUpOperator.getAllPickUpOrders().isEmpty()) {
                                                Iterator itrAllCfOrders = PickUpOperator.getAllPickUpOrders().iterator();
                                                while (itrAllCfOrders.hasNext()) {
                                                    PickUpOrder pOrder = (PickUpOrder) itrAllCfOrders.next();
                                                    if (pOrder.getCustomerId().equals(endCfOrder.getCustomerId())) {
                                                        itrAllCfOrders.remove();
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // update menu time stamp
                        txtTimeStamp.setText(clock.getTime());

                        // update order time in simulate customer
                        txtOrderTime.setText(clock.getTime());

                        // make delivery men move
                        DeliveryGuy.updateAllDeliveryGuyPos();

                        // increase 1 second
                        clock.tick();
                    }
                };

                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    }

                    Platform.runLater(updater);
                }
            }

        });
        timeThread.setDaemon(true);
        timeThread.start();

        // MENU
        makeSceneMenu(primaryStage);

        // MANAGE RESTAURANT
        makeSceneMR(primaryStage);

        // MANAGE DELIVERY
        makeSceneMD(primaryStage);

        // VIEW ORDER LOG
        makeSceneVOL(primaryStage);

        //VIEW MAP
        makeSceneVM(primaryStage);

        // SIMULATE CUSTOMER
        makeSceneSC(primaryStage);

        // EDIT RESTAURANT
        makeSceneER(primaryStage);

        // EDIT DISHES
        makeSceneEDs(primaryStage);

        // EDIT DISH
        makeSceneED(primaryStage);

        // primary stage property
        primaryStage.setMinHeight(876);
        primaryStage.setMinWidth(802);
        primaryStage.setScene(sceneMenu);
        primaryStage.setTitle("PickUp");
//        primaryStage.setOnCloseRequest(fn -> {});
        primaryStage.show();
    }

    /* Describe and explain the function below.
     * This code creates a scene for the PickUpOperator to manage the packages, delivery, view order log, and view map. 
     * The first four lines create buttons for the PickUpOperator to manage the packages, delivery, view order log, and view map. 
     * The next four lines set the maximum size for the buttons. 
     * The next four lines set an action for each button. The first button sets the scene to the scene for managing packages. The second button sets the scene to the scene for managing delivery. The third button sets the scene to the scene for viewing the order log. The fourth button sets the scene to the scene for viewing the map. 
     * The next four lines create a grid for the map. 
     * The next line adds the tiles to the map. 
     * The next line colors the tiles grey. 
     */
    private void makeSceneMenu(Stage primaryStage) {
        // Manage Packages, Manage Delivery, View Order Log
        Button btnMR = new Button("Manage Packages");
        btnMR.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnMR.setOnAction(fn -> primaryStage.setScene(sceneMR));

        Button btnMD = new Button("Manage Delivery");
        btnMD.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnMD.setOnAction(fn -> primaryStage.setScene(sceneMD));

        Button btnVOL = new Button("View Order Log");
        btnVOL.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnVOL.setOnAction(fn -> primaryStage.setScene(sceneVOL));

        Button btnVM = new Button("View Map");
        btnVM.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnVM.setOnAction(fn -> {
            ArrayList<Position> branchLoc = new ArrayList<>();
            if (!PickUpOperator.getPartnerPackages().isEmpty()) {
                for (Package packages : PickUpOperator.getPartnerPackages()) {
                    branchLoc.add(packages.getPosition());
                }
            }

            for (int i = 0; i < PickUpOperator.getMasterMap().getWidth(); i++) {
                for (int j = 0; j < PickUpOperator.getMasterMap().getHeight(); j++) {
                    Tile tile = new Tile(String.valueOf(PickUpOperator.getMasterMap().getSymbolAt(i, j)));
                    GridPane.setConstraints(tile, i, j);
                    tile.colorTileGrey();
                    gridMap.getChildren().addAll(tile);

                    for (Position pos : branchLoc) {
                        if (i == pos.getPosX() && j == pos.getPosY()) {
                            tile.colorTileLightGrey();
                        }
                    }
                }
            }
            primaryStage.setScene(sceneVM);
        });

        btnSC.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnSC.setOnAction(fn -> primaryStage.setScene(sceneSC));

        txtTimeStamp.setTextAlignment(TextAlignment.CENTER);
        txtTimeStamp.setFont(Font.font("Monospace", 30));

        // #
        VBox layoutMenuLeft = new VBox(10, btnMR, btnMD, btnVOL, btnVM, btnSC, txtTimeStamp);
        layoutMenuLeft.setAlignment(Pos.TOP_CENTER);

        // Process Log
        TextArea txtareaPL = new TextArea();
        txtareaPL.setMinSize(500, 400);
        txtareaPL.setEditable(false);
        txtareaPL.setFont(Font.font("Monospace", 20));
        txtareaPL.textProperty().bind(PickUpOperator.getProcess());
        PickUpOperator.getProcess().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                txtareaPL.selectPositionCaret(txtareaPL.getLength());
                txtareaPL.deselect();
            }
        });

        // Order Status
        TableColumn<PickUpOrder, Integer> colCustomerId = new TableColumn<>("Customer ID");
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        TableColumn<PickUpOrder, String> colOrderTime = new TableColumn<>("Order Time");
        colOrderTime.setCellValueFactory(new PropertyValueFactory<>("orderTime"));

        TableColumn<PickUpOrder, String> colPackage = new TableColumn<>("Package");
        colPackage.setCellValueFactory(new PropertyValueFactory<>("packageName"));

        TableColumn<PickUpOrder, Position> colBranch = new TableColumn<>("Branch");
        colBranch.setCellValueFactory(new PropertyValueFactory<>("branchLocation"));

        TableColumn<PickUpOrder, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(cd -> cd.getValue().getStatus());

        tableOS.setMinSize(500, 400);
        tableOS.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableOS.getColumns().addAll(colCustomerId, colOrderTime, colPackage, colBranch, colStatus);
        tableOS.setItems(pOrderList);
        pOrderList.addListener(new ListChangeListener<PickUpOrder>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends PickUpOrder> c) {
                tableOS.scrollTo(pOrderList.size() - 1);
            }
        });

        // #
        VBox layoutMenuRight = new VBox(10, txtareaPL, tableOS);
        VBox.setVgrow(txtareaPL, Priority.ALWAYS);
        VBox.setVgrow(tableOS, Priority.ALWAYS);

        // ##
        GridPane layoutMenu = new GridPane();
        GridPane.setVgrow(layoutMenuRight, Priority.ALWAYS);
        GridPane.setHgrow(layoutMenuRight, Priority.ALWAYS);
        GridPane.setConstraints(layoutMenuLeft, 0, 0);
        GridPane.setConstraints(layoutMenuRight, 1, 0);
        layoutMenu.setPadding(new Insets(10, 10, 10, 10));
        layoutMenu.setHgap(10);
        layoutMenu.getChildren().addAll(layoutMenuLeft, layoutMenuRight);

        sceneMenu = new Scene(layoutMenu, stageWidth.getValue(), stageHeight.getValue());
    }

    /* Describes the function below.
     * The code below creates a button that, when clicked, will allow the user to edit a selected packages. It also sets up the scene for the edit packages page, including the text field for the packages name and the position of the packages.
     * The code first creates a list of all the packages that the user can choose from. It then checks if the list of packages is empty. If it is not, it adds all the packages to the list.
     * Next, it creates a button labeled "Edit". When this button is clicked, it will take the user to the edit packages page.
     * On the edit packages page, the text field for the packages name will be populated with the name of the packages that was selected. The position of the packages will also be displayed.
     */
    private void makeSceneMR(Stage primaryStage) {
        // Package List
        ListView listPackage = new ListView(obsListPackage);
        if (!PickUpOperator.getPartnerPackages().isEmpty()) {
            for (Package packages : PickUpOperator.getPartnerPackages()) {
                if (!obsListPackage.contains(packages.getName())) {
                    obsListPackage.add(packages.getName());
                }
            }
        }

        // Buttons
        Button btnMR_EDIT = new Button("Edit");
        btnMR_EDIT.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnMR_EDIT.setOnAction(fn -> {
            if (listPackage.getSelectionModel().getSelectedItem() != null) {
                // record selected packages to edit to be passed to edit packages
                resToEdit.setValue(listPackage.getSelectionModel().getSelectedItem().toString());

                // get ready sceneER (edit name)
                txtareaPackageName.setText(resToEdit.getValue());

                // get ready sceneER (edit branch locations)
                ArrayList<Position> branchLoc = new ArrayList<>(); // branch of packages to edit
                ArrayList<Position> otherBranchLoc = new ArrayList<>(); // branch of packages besides the one to edit
                if (!resToEdit.getValue().equals("") && resToEdit.getValue() != null) {
                    if (!PickUpOperator.getPartnerPackages().isEmpty()) {
                        for (Package packages : PickUpOperator.getPartnerPackages()) {
                            if (packages.getName().equals(resToEdit.getValue())) {
                                branchLoc.add(packages.getPosition());
                            } else if (!packages.getName().equals(resToEdit.getValue())) {
                                otherBranchLoc.add(packages.getPosition());
                            }
                        }
                    }
                }

                gridPackageLoc.getChildren().clear();
                for (int i = 0; i < PickUpOperator.getMasterMap().getWidth(); i++) {
                    for (int j = 0; j < PickUpOperator.getMasterMap().getHeight(); j++) {
                        Tile tile = new Tile(String.valueOf(PickUpOperator.getMasterMap().getSymbolAt(i, j)));
                        GridPane.setConstraints(tile, i, j);
                        gridPackageLoc.getChildren().addAll(tile);

                        for (Position pos : branchLoc) {
                            if (i == pos.getPosX() && j == pos.getPosY()) {
                                tile.colorTileBlue();
                            }
                        }

                        for (Position pos : otherBranchLoc) {
                            if (i == pos.getPosX() && j == pos.getPosY()) {
                                tile.colorTileGrey();
                            }
                        }
                    }
                }

                branchLoc.clear();
                otherBranchLoc.clear();

                // get ready sceneEDs
                if (!PickUpOperator.getPartnerPackages().isEmpty()) {
                    for (Package packages : PickUpOperator.getPartnerPackages()) {
                        if (packages.getName().equals(resToEdit.getValue())) {
                            if (!packages.getAllAvailableDishes().isEmpty()) {
                                for (Dish dish : packages.getAllAvailableDishes()) {
                                    obsListDishes.add(dish.getName());
                                }
                            }
                            break;
                        }
                    }
                }

                primaryStage.setScene(sceneER);
            }
        });

        Button btnMR_DELETE = new Button("Delete");
        btnMR_DELETE.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnMR_DELETE.setOnAction(fn -> {
            if (listPackage.getSelectionModel().getSelectedItem() != null) {
                // for now, remove only from observable list
                obsListPackage.remove(listPackage.getSelectionModel().getSelectedItem().toString());
            }
        });

        Button btnMR_ADD = new Button("Add");
        btnMR_ADD.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnMR_ADD.setOnAction(fn -> {
            // clear selected item 
            if (listPackage.getSelectionModel().getSelectedItem() != null) {
                listPackage.getSelectionModel().clearSelection();
            }

            // get ready sceneER (add branch locations)
            ArrayList<Position> branchLoc = new ArrayList<>(); // branch of packages to add
            if (!PickUpOperator.getPartnerPackages().isEmpty()) {
                for (Package packages : PickUpOperator.getPartnerPackages()) {
                    branchLoc.add(packages.getPosition());
                }
            }
            gridPackageLoc.getChildren().clear();
            for (int i = 0; i < PickUpOperator.getMasterMap().getWidth(); i++) {
                for (int j = 0; j < PickUpOperator.getMasterMap().getHeight(); j++) {
                    Tile tile = new Tile(String.valueOf(PickUpOperator.getMasterMap().getSymbolAt(i, j)));
                    GridPane.setConstraints(tile, i, j);
                    gridPackageLoc.getChildren().addAll(tile);

                    for (Position pos : branchLoc) {
                        if (i == pos.getPosX() && j == pos.getPosY()) {
                            tile.colorTileGrey();
                        }
                    }
                }
            }
            branchLoc.clear();

            flagAddRes = true;

            primaryStage.setScene(sceneER);
        });

        Button btnMR_DONE = new Button("Done");
        btnMR_DONE.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnMR_DONE.setOnAction(fn -> {
            if (!obsListPackage.isEmpty()) {
                // clear selected items
                if (listPackage.getSelectionModel().getSelectedItem() != null) {
                    listPackage.getSelectionModel().clearSelection();
                }

                // if obsListPackage does not contain certain packages, delete them
                if (!PickUpOperator.getPartnerPackages().isEmpty()) {
                    Iterator itrAllPartnerPackage = PickUpOperator.getPartnerPackages().iterator();
                    while (itrAllPartnerPackage.hasNext()) {
                        Package packages = (Package) itrAllPartnerPackage.next();
                        if (!obsListPackage.contains(packages.getName())) {
                            itrAllPartnerPackage.remove();
                        }
                    }
                }

                // update stuff to txt and read again to restart program
                PickUpOperator.getTotalPickUpOrder().setValue(0);

                PickUpOperator.updatePartnerPackages();
                PickUpOperator.readPartnerPackages();

                PickUpOperator.getMasterMap().updateMap();

                PickUpOperator.getAllDeliveryGuys().clear();
                PickUpOperator.readAllDeliveryGuys();

                PickUpOperator.getAllPresetPickUpOrders().clear();
                PickUpOperator.readAllPresetPickUpOrders();

                PickUpOperator.getAllPickUpOrders().clear();

                clock.resetTime();

                primaryStage.setScene(sceneMenu);
            }
        });

        Button btnMR_CANCEL = new Button("Cancel");
        btnMR_CANCEL.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnMR_CANCEL.setOnAction(fn -> {
            // clear selected items
            if (listPackage.getSelectionModel().getSelectedItem() != null) {
                listPackage.getSelectionModel().clearSelection();
            }

            // reset the list of packages
            obsListPackage.clear();
            if (!PickUpOperator.getPartnerPackages().isEmpty()) {
                for (Package packages : PickUpOperator.getPartnerPackages()) {
                    if (!obsListPackage.contains(packages.getName())) {
                        obsListPackage.add(packages.getName());
                    }
                }
            }

            // update stuff to txt and read again to restart program
            // SPECIAL CASE, since there might be edits in internal scenes
            PickUpOperator.getTotalPickUpOrder().setValue(0);

            PickUpOperator.updatePartnerPackages();
            PickUpOperator.readPartnerPackages();

            PickUpOperator.getMasterMap().updateMap();

            PickUpOperator.getAllDeliveryGuys().clear();
            PickUpOperator.readAllDeliveryGuys();

            PickUpOperator.getAllPresetPickUpOrders().clear();
            PickUpOperator.readAllPresetPickUpOrders();

            PickUpOperator.getAllPickUpOrders().clear();

            clock.resetTime();

            primaryStage.setScene(sceneMenu);
        });

        // #
        HBox layoutMRBottom = new HBox(10, btnMR_CANCEL, btnMR_DELETE, btnMR_EDIT, btnMR_ADD, btnMR_DONE);
        layoutMRBottom.setAlignment(Pos.CENTER);

        // ##
        GridPane layoutMR = new GridPane();
        GridPane.setVgrow(listPackage, Priority.ALWAYS);
        GridPane.setHgrow(listPackage, Priority.ALWAYS);
        GridPane.setConstraints(listPackage, 0, 0);
        GridPane.setConstraints(layoutMRBottom, 0, 1);
        layoutMR.setPadding(new Insets(10, 10, 10, 10));
        layoutMR.setVgap(10);
        layoutMR.getChildren().addAll(listPackage, layoutMRBottom);

        sceneMR = new Scene(layoutMR, stageWidth.getValue(), stageHeight.getValue());
    }

    /* Describes the functions below.
     * The code above creates a scene for the "Make Delivery" menu. This scene allows the user to specify the number of delivery men for the packages. 
     * The first line creates a label for the number of delivery men. 
     * The second line creates a spinner for the number of delivery men. The spinner has a range of 1 to 100, and the default value is 1. 
     * The third line adds the spinner to a horizontal box layout. 
     * The fourth line creates a button labeled "Done". 
     * The fifth line sets the maximum size for the button. 
     * The sixth line adds an event handler to the button. When the button is clicked, the event handler clears the list of all delivery guys, then loops through the specified number of delivery men and adds them to the list. Finally, it updates the partner packages, the master map, and the list of all delivery guys.
     */
    private void makeSceneMD(Stage primaryStage) {
        // Number of Delivery Man
        Label labelNumDeliveryMan = new Label("Number of Delivery Man : ");

        Spinner spinnerNumDeliveryMan = new Spinner(1, 100, 1);
        spinnerNumDeliveryMan.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
        spinnerNumDeliveryMan.setEditable(true);
        spinnerNumDeliveryMan.getValueFactory().setValue(PickUpOperator.getAllDeliveryGuys().size());

        // #
        HBox layoutMDTop = new HBox(10, labelNumDeliveryMan, spinnerNumDeliveryMan);
        layoutMDTop.setAlignment(Pos.CENTER);

        // Button
        Button btnMD_DONE = new Button("Done");
        btnMD_DONE.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnMD_DONE.setOnAction(fn -> {

            PickUpOperator.getAllDeliveryGuys().clear();
            for (int i = 1; i <= Integer.parseInt(spinnerNumDeliveryMan.getValue().toString()); i++) {
                PickUpOperator.getAllDeliveryGuys().add(new DeliveryGuy(i));
            }

            // update stuff to txt and read again to restart program
            PickUpOperator.getTotalPickUpOrder().setValue(0);

            PickUpOperator.updatePartnerPackages();
            PickUpOperator.readPartnerPackages();

            PickUpOperator.getMasterMap().updateMap();

            PickUpOperator.updateAllDeliveryGuys();
            PickUpOperator.readAllDeliveryGuys();

            PickUpOperator.getAllPresetPickUpOrders().clear();
            PickUpOperator.readAllPresetPickUpOrders();

            PickUpOperator.getAllPickUpOrders().clear();

            clock.resetTime();

            primaryStage.setScene(sceneMenu);
        });

        Button btnMD_CANCEL = new Button("Cancel");
        btnMD_CANCEL.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnMD_CANCEL.setOnAction(fn -> {
            spinnerNumDeliveryMan.getValueFactory().setValue(PickUpOperator.getAllDeliveryGuys().size());
            primaryStage.setScene(sceneMenu);
        });

        // #
        HBox layoutMDBottom = new HBox(10, btnMD_CANCEL, btnMD_DONE);
        layoutMDBottom.setAlignment(Pos.CENTER);

        // ##
        GridPane layoutMD = new GridPane();
        GridPane.setVgrow(layoutMDTop, Priority.ALWAYS);
        GridPane.setHgrow(layoutMDTop, Priority.ALWAYS);
        GridPane.setConstraints(layoutMDTop, 0, 0);
        GridPane.setConstraints(layoutMDBottom, 0, 1);
        layoutMD.setPadding(new Insets(10, 10, 10, 10));
        layoutMD.getChildren().addAll(layoutMDTop, layoutMDBottom);

        sceneMD = new Scene(layoutMD, stageWidth.getValue(), stageHeight.getValue());
    }

    /* Describes the function below.
     * The method above is used to create a scene for the Order Log of the application. 
     * The scene consists of a TextArea which displays the order log and a Button which leads the user back to the main menu. 
     */
    private void makeSceneVOL(Stage primaryStage) {
    	/*
    	 * This part of the code creates the TextArea where the order log will be displayed. 
    	 * The TextArea is set to be uneditable and the font is set to be Monospace. 
    	 * The order log is binded to the TextArea so that any changes to the order log will be displayed on the TextArea. 
    	 */
        // Order Log
        TextArea txtareaOrderLog = new TextArea();
        txtareaOrderLog.setEditable(false);
        txtareaOrderLog.setFont(Font.font("Monospace", 15));
        txtareaOrderLog.textProperty().bind(PickUpOperator.getLog());
        PickUpOperator.getLog().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                txtareaOrderLog.selectPositionCaret(txtareaOrderLog.getLength());
                txtareaOrderLog.deselect();
            }
        });
        
        //This part of the code creates the Button which leads the user back to the main menu. 
        // Button
        Button btnVOL_BACK = new Button("Back");
        btnVOL_BACK.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnVOL_BACK.setOnAction(fn -> primaryStage.setScene(sceneMenu));

        // This part of the code creates a HBox which contains the Button created earlier. The HBox is aligned at the center. 
        HBox layoutVOLBottom = new HBox(btnVOL_BACK);
        layoutVOLBottom.setAlignment(Pos.CENTER);

        /*
         * This part of the code creates a GridPane which contains the TextArea and the HBox created earlier. 
         * The TextArea and HBox are set to be at column 0, row 0 and column 0, row 1 respectively. 
         * There is a vertical gap of 10 set for the GridPane and padding of 10 is set for all sides.  
         */
        GridPane layoutVOL = new GridPane();
        GridPane.setVgrow(txtareaOrderLog, Priority.ALWAYS);
        GridPane.setHgrow(txtareaOrderLog, Priority.ALWAYS);
        GridPane.setConstraints(txtareaOrderLog, 0, 0);
        GridPane.setConstraints(layoutVOLBottom, 0, 1);
        layoutVOL.setVgap(10);
        layoutVOL.setPadding(new Insets(10, 10, 10, 10));
        layoutVOL.getChildren().addAll(txtareaOrderLog, layoutVOLBottom);
        
        //This line of code creates the scene for the Order Log using the GridPane created earlier. The scene is set to be the same width and height as the stage.
        sceneVOL = new Scene(layoutVOL, stageWidth.getValue(), stageHeight.getValue());
    }

    /* Describes the functions below.
     * The first line creates a new Button object with the text "Back". The second line sets the maximum size of the button to the maximum possible value, so that it will fill up the entire space allocated to it. The third line sets an onAction event handler that will change the scene back to the menu scene when the button is clicked.
     * The next section creates a HBox layout containing the button. The HBox is then centered using the setAlignment method.
     * Next, a ScrollPane object is created to hold the gridMap. The ScrollPane is set to always grow in both the vertical and horizontal directions.
     * Finally, a GridPane is created to hold the ScrollPane and the HBox. The ScrollPane is added to the first row and column, and the HBox is added to the second row and column. The layout is given some vertical spacing between elements, and padding is added to all sides.
     * The last line creates a new Scene object with the layout as the root element, and the scene is set to the stage.
     */
    private void makeSceneVM(Stage primaryStage) {
        // Button
        Button btnVM_BACK = new Button("Back");
        btnVM_BACK.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnVM_BACK.setOnAction(fn -> primaryStage.setScene(sceneMenu));

        // #
        HBox layoutVMBottom = new HBox(btnVM_BACK);
        layoutVMBottom.setAlignment(Pos.CENTER);

        ScrollPane gridPad = new ScrollPane(gridMap);

        // ##
        GridPane layoutVM = new GridPane();
        GridPane.setVgrow(gridPad, Priority.ALWAYS);
        GridPane.setHgrow(gridPad, Priority.ALWAYS);
        GridPane.setConstraints(gridPad, 0, 0);
        GridPane.setConstraints(layoutVMBottom, 0, 1);
        layoutVM.setVgap(10);
        layoutVM.setPadding(new Insets(10, 10, 10, 10));
        layoutVM.getChildren().addAll(gridPad, layoutVMBottom);

        sceneVM = new Scene(layoutVM, stageWidth.getValue(), stageHeight.getValue());
    }

    /* Describes the functions below.
     * The code creates a table that displays the ordered dish and its quantity. 
     * The table is made up of two columns, one for the dish and one for the quantity. 
     * The table is populated by an observable map that is populated by the user selecting a dish and its quantity. 
     * The code also includes a button that allows the user to remove a dish from the table. When the button is clicked, the dish that is currently selected in the table is removed from the map.
     */
    private void makeSceneSC(Stage primaryStage) {
        // Ordered dish & its quantity to be put into tableSC
        ObservableMap<String, Integer> mapSC = FXCollections.observableHashMap();
        ObservableList<String> mapSCkeys = FXCollections.observableArrayList();

        mapSC.addListener((MapChangeListener.Change<? extends String, ? extends Integer> change) -> {
            boolean removed = change.wasRemoved();
            if (removed != change.wasAdded()) {
                // no put for existing key
                if (removed) {
                    mapSCkeys.remove(change.getKey());
                } else {
                    mapSCkeys.add(change.getKey());
                }
            }
        });

        // Your Orders
        Label labelYourOrders = new Label("Your orders");
        // 
        TableColumn<String, String> colSCDish = new TableColumn<>("Dish");
        colSCDish.setCellValueFactory(cd -> Bindings.createStringBinding(() -> cd.getValue()));

        TableColumn<String, Integer> colSCQuantity = new TableColumn<>("Quantity");
        colSCQuantity.setCellValueFactory(cd -> Bindings.valueAt(mapSC, cd.getValue()));

        TableView<String> tableSC = new TableView<>(mapSCkeys);
        tableSC.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableSC.getColumns().setAll(colSCDish, colSCQuantity);
        //
        Button btnSC_REMOVE = new Button("Remove");
        btnSC_REMOVE.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnSC_REMOVE.setOnAction(fn -> {
            mapSC.remove(tableSC.getSelectionModel().getSelectedItem());
        });

        // #
        VBox layoutSCTopRight = new VBox(10, labelYourOrders, tableSC, btnSC_REMOVE);
        layoutSCTopRight.setAlignment(Pos.CENTER);
        VBox.setVgrow(tableSC, Priority.ALWAYS);

        // Customer ID
        Label labelCustomerID = new Label("Customer ID : ");

        Text txtCustomerID = new Text();
        txtCustomerID.textProperty().bind(PickUpOperator.getTotalPickUpOrder().add(1).asString());

        // Order Time
        Label labelOrderTime = new Label("Order Time : ");

        // Package
        Label labelPackage = new Label("Package : ");

        ComboBox comboPackage = new ComboBox();
        comboPackage.setPromptText("Pick a packages");
        comboPackage.setPrefSize(450, 10);
        for (Package packages : PickUpOperator.getPartnerPackages()) {
            if (!comboPackage.getItems().contains(packages.getName())) {
                comboPackage.getItems().add(packages.getName());
            }
        }

        // Dish & Quantity
        Label labelDish = new Label("Dish : ");

        ComboBox comboDish = new ComboBox();
        comboDish.setPromptText("Pick a dish");
        comboDish.setPrefSize(450, 10);

        Label labelQuantity = new Label("Quantity : ");

        Spinner spinnerQuantity = new Spinner(1, 20, 1);
        spinnerQuantity.setEditable(true);
        spinnerQuantity.setPrefSize(450, 10);

        Button btnSC_ADD = new Button("Add");
        btnSC_ADD.setPrefSize(75, 75);
        btnSC_ADD.setOnAction(fn -> {
            if (comboDish.getValue() != null) {
                mapSC.put(comboDish.getValue().toString(),
                        Integer.parseInt(spinnerQuantity.getValue().toString()));
            }
        });

        // listeners
        comboPackage.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // reset dish
                if (comboPackage.getSelectionModel().getSelectedItem() != null) {
                    for (Package packages : PickUpOperator.getPartnerPackages()) {
                        if (comboPackage.getSelectionModel().getSelectedItem().toString().equals(packages.getName())) {
                            comboDish.getItems().clear();
                            for (Dish dish : packages.getAllAvailableDishes()) {
                                comboDish.getItems().add(dish.getName());
                            }
                        }
                    }
                }

                //reset spinner
                spinnerQuantity.getValueFactory().setValue(1);

                //clear order hash map
                mapSC.clear();
            }
        });

        comboDish.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //reset spinner
                if (mapSC.containsKey(comboDish.getSelectionModel().getSelectedItem())) {
                    spinnerQuantity.getValueFactory().setValue(mapSC.get(comboDish.getSelectionModel().getSelectedItem()));
                } else {
                    spinnerQuantity.getValueFactory().setValue(1);
                }
            }
        });

        // Delivery Location
        Label labelDeliveryLoc = new Label("Delivery Location : ");
        Label labelX = new Label("X : ");
        Spinner spinnerX = new Spinner(0, 100, 1);
        spinnerX.setEditable(true);
        Label labelY = new Label("Y : ");
        Spinner spinnerY = new Spinner(0, 100, 1);
        spinnerY.setEditable(true);

        HBox coordinateLabels = new HBox(10, labelX, spinnerX, labelY, spinnerY);
        coordinateLabels.setAlignment(Pos.CENTER);

        // #
        GridPane layoutSCTopLeft = new GridPane();
        GridPane.setConstraints(labelCustomerID, 0, 0, 1, 1, HPos.RIGHT, VPos.CENTER);
        GridPane.setConstraints(txtCustomerID, 1, 0);
        GridPane.setConstraints(labelOrderTime, 0, 1, 1, 1, HPos.RIGHT, VPos.CENTER);
        GridPane.setConstraints(txtOrderTime, 1, 1);
        GridPane.setConstraints(labelPackage, 0, 2, 1, 1, HPos.RIGHT, VPos.CENTER);
        GridPane.setConstraints(comboPackage, 1, 2);
        GridPane.setConstraints(labelDish, 0, 3, 1, 1, HPos.RIGHT, VPos.CENTER);
        GridPane.setConstraints(comboDish, 1, 3);
        GridPane.setConstraints(btnSC_ADD, 2, 3, 1, 2);
        GridPane.setConstraints(labelQuantity, 0, 4, 1, 1, HPos.RIGHT, VPos.CENTER);
        GridPane.setConstraints(spinnerQuantity, 1, 4);
        GridPane.setConstraints(labelDeliveryLoc, 0, 5, 1, 1, HPos.RIGHT, VPos.CENTER);
        GridPane.setConstraints(coordinateLabels, 1, 5);
        layoutSCTopLeft.getChildren().addAll(labelCustomerID, txtCustomerID,
                labelOrderTime, txtOrderTime,
                labelPackage, comboPackage,
                labelDish, comboDish, btnSC_ADD,
                labelQuantity, spinnerQuantity,
                labelDeliveryLoc, coordinateLabels);
        layoutSCTopLeft.setVgap(10);
        layoutSCTopLeft.setHgap(10);
        layoutSCTopLeft.setAlignment(Pos.CENTER);

        // Button
        Button btnSC_DONE = new Button("Done");
        btnSC_DONE.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnSC_DONE.setOnAction(fn -> {
            if (comboPackage.getSelectionModel().getSelectedItem() != null && !mapSC.isEmpty()) {
                // add crabfood order to all crabfood orders
                PickUpOrder pOrder = new PickUpOrder();
                pOrder.setPackageName(comboPackage.getSelectionModel().getSelectedItem().toString());
                HashMap<String, Integer> dishOrders = new HashMap<>();
                dishOrders.putAll(mapSC);
                pOrder.setDishOrders(dishOrders);
                pOrder.setDeliveryLocation(new Position(
                        Integer.parseInt(spinnerX.getValue().toString()),
                        Integer.parseInt(spinnerY.getValue().toString())));
                pOrder.setCookTime(pOrder.calculateCookTime());
                pOrder.setCustomerId(PickUpOperator.getTotalPickUpOrder().get() + 1);
                PickUpOperator.getTotalPickUpOrder().set(PickUpOperator.getTotalPickUpOrder().get() + 1);
                PickUpOperator.getAllPickUpOrders().add(pOrder);
                PickUpOperator.sortCfOrders();

                // reset all components
                comboPackage.getSelectionModel().clearSelection();
                comboDish.getItems().clear();
                spinnerQuantity.getValueFactory().setValue(1);
                spinnerX.getValueFactory().setValue(1);
                spinnerY.getValueFactory().setValue(1);
                mapSC.clear();
                primaryStage.setScene(sceneMenu);
            }
        });

        Button btnSC_CANCEL = new Button("Cancel");
        btnSC_CANCEL.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnSC_CANCEL.setOnAction(fn -> {
            primaryStage.setScene(sceneMenu);
            comboPackage.getSelectionModel().clearSelection();
            comboDish.getSelectionModel().clearSelection();
            spinnerQuantity.getValueFactory().setValue(1);
            spinnerX.getValueFactory().setValue(1);
            spinnerY.getValueFactory().setValue(1);
            mapSC.clear();
        });

        // #
        HBox layoutSCBottom = new HBox(10, btnSC_CANCEL, btnSC_DONE);
        layoutSCBottom.setAlignment(Pos.CENTER);

        GridPane layoutSC = new GridPane();
        GridPane.setConstraints(layoutSCTopLeft, 0, 0);
        GridPane.setConstraints(layoutSCTopRight, 1, 0);
        GridPane.setConstraints(layoutSCBottom, 0, 1, 2, 1);
        GridPane.setHgrow(layoutSCTopLeft, Priority.ALWAYS);
        GridPane.setVgrow(layoutSCTopLeft, Priority.ALWAYS);
        GridPane.setHgrow(layoutSCTopRight, Priority.ALWAYS);
        GridPane.setVgrow(layoutSCTopRight, Priority.ALWAYS);
        layoutSC.setVgap(10);
        layoutSC.setHgap(10);
        layoutSC.setPadding(new Insets(10, 10, 10, 10));
        layoutSC.getChildren().addAll(layoutSCTopLeft, layoutSCTopRight, layoutSCBottom);

        sceneSC = new Scene(layoutSC, stageWidth.getValue(), stageHeight.getValue());
    }

    /* Describes the functions below.
     * The first section of code creates a label for the packages name, followed by a text area for the user to input the name of the packages. 
     * The second section creates a label for the packages location, followed by a grid pane for the user to input the location of the packages. 
     * The third section creates a label for the dishes served at the packages, followed by a button to edit the dishes. 
     * The final section of code creates a grid pane to hold all of the elements, and sets the alignment of the labels.
     * The makeSceneER() method is responsible for creating the scene for the Edit Package page. This page allows the user to edit the name, location, and dishes served at a packages.
     */
    private void makeSceneER(Stage primaryStage) {
        // Package Name
        Label labelPackageName = new Label("Name : ");

        txtareaPackageName.setPrefHeight(txtareaPackageName.DEFAULT_PREF_ROW_COUNT);
        txtareaPackageName.setPrefWidth(500);

        // Package Location
        Label labelPackageLoc = new Label("Package Location : ");

        gridPackageLoc.setPrefSize(700, 600);
        gridPackageLoc.setMaxSize(700, 600);

        ScrollPane gridPad = new ScrollPane(gridPackageLoc);
        gridPad.setMaxSize(700, 600);

        // Dishes
        Label labelDishes = new Label("Dishes : ");

        Button btnER_EDs = new Button("Edit Dishes");
        btnER_EDs.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnER_EDs.setOnAction(fn -> primaryStage.setScene(sceneEDs));

        // #
        GridPane layoutERTop = new GridPane();
        GridPane.setConstraints(labelPackageName, 0, 0);
        GridPane.setConstraints(txtareaPackageName, 1, 0);
        GridPane.setConstraints(labelPackageLoc, 0, 1);
        GridPane.setConstraints(gridPad, 1, 1);
        GridPane.setConstraints(labelDishes, 0, 2);
        GridPane.setConstraints(btnER_EDs, 1, 2);
        GridPane.setHalignment(labelPackageName, HPos.RIGHT);
        GridPane.setHalignment(labelPackageLoc, HPos.RIGHT);
        GridPane.setHalignment(labelDishes, HPos.RIGHT);
        layoutERTop.setVgap(10);
        layoutERTop.getChildren().addAll(labelPackageName, txtareaPackageName,
                labelPackageLoc, gridPad,
                labelDishes, btnER_EDs);

        layoutERTop.setAlignment(Pos.CENTER);

        // Button
        Button btnER_DONE = new Button("Done");
        btnER_DONE.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnER_DONE.setOnAction(fn -> {
            // flag to indicate if user inputted location(s)
            boolean hasPos = false;
            for (Node tileObj : gridPackageLoc.getChildren()) {
                Tile tile = (Tile) tileObj;
                if (tile.isBlue()) {
                    hasPos = true;
                    break;
                }
            }

            if (!txtareaPackageName.getText().isEmpty() && hasPos && !obsListDishes.isEmpty()) {
                if (flagAddRes) {
                    String packageName = "";
                    Character packageMapSymbol = '0';

                    // read packages name
                    packageName = txtareaPackageName.getText();

                    // read packages map symbol
                    packageMapSymbol = packageName.charAt(0);

                    // read packages positions 
                    ArrayList<Position> resLoc = new ArrayList<>();
                    for (Node tileObj : gridPackageLoc.getChildren()) {
                        Tile tile = (Tile) tileObj;
                        if (tile.isBlue()) {
                            resLoc.add(new Position(GridPane.getColumnIndex(tileObj),
                                    GridPane.getRowIndex(tileObj)));
                        }
                    }

                    // read packages dishes
                    ArrayList<Dish> dishes = new ArrayList<>(dishesToAddTemp);
                    dishesToAddTemp.clear();
                    
                    // after reading, set name, map symbol, positions & dishes
                    for (int i = 0; i < resLoc.size(); i++) {
                        PickUpOperator.getPartnerPackages().add(new Package(
                                packageName,
                                packageMapSymbol,
                                resLoc.get(i),
                                (ArrayList<Dish>) dishes.clone()));
                    }

                    flagAddRes = false;
                } else {
                    // update packages name
                    if (!PickUpOperator.getPartnerPackages().isEmpty()) {
                        for (Package packages : PickUpOperator.getPartnerPackages()) {
                            if (packages.getName().equals(resToEdit.getValue())) {
                                packages.setName(txtareaPackageName.getText());
                            }
                        }
                    }
                    // update packages locations
                    ArrayList<Position> resLoc = new ArrayList<>();
                    for (Node tileObj : gridPackageLoc.getChildren()) {
                        Tile tile = (Tile) tileObj;
                        if (tile.isBlue()) {
                            resLoc.add(new Position(GridPane.getColumnIndex(tileObj),
                                    GridPane.getRowIndex(tileObj)));
                        }
                    }
                    String packageName = "";
                    Character packageMapSymbol = '0';
                    ArrayList<Dish> dishes = new ArrayList<>();
                    if (!PickUpOperator.getPartnerPackages().isEmpty()) {
                        Iterator itrRes = PickUpOperator.getPartnerPackages().iterator();
                        while (itrRes.hasNext()) {
                            Package res = (Package) itrRes.next();
                            if (res.getName().equals(txtareaPackageName.getText())) {
                                packageName = res.getName();
                                packageMapSymbol = res.getMapSymbol();
                                dishes = (ArrayList<Dish>) res.getAllAvailableDishes().clone();
                                itrRes.remove();
                            }
                        }
                    }
                    if (!resLoc.isEmpty()) {
                        for (int i = 0; i < resLoc.size(); i++) {
                            PickUpOperator.getPartnerPackages().add(new Package(packageName,
                                    packageMapSymbol, resLoc.get(i),
                                    (ArrayList<Dish>) dishes.clone()));
                        }
                    }
                }

                // reset sceneMR
                obsListPackage.clear();
                if (!PickUpOperator.getPartnerPackages().isEmpty()) {
                    for (Package packages : PickUpOperator.getPartnerPackages()) {
                        if (!obsListPackage.contains(packages.getName())) {
                            obsListPackage.add(packages.getName());
                        }
                    }
                }

                // reset input fields
                txtareaPackageName.clear();
                for (Object tileObj : gridPackageLoc.getChildren()) {
                    Tile tile = (Tile) tileObj;
                    if (!tile.getText().equals("0")) {
                        tile.colorTileNull();
                    } else {
                        tile.colorTileGrey();
                    }
                }

                // update map as soon as map changes
                PickUpOperator.getMasterMap().updateMap();

                // clear stuff in edit
                resToEdit.setValue("");
                obsListDishes.clear();

                primaryStage.setScene(sceneMR);
            }
        });

        Button btnER_CANCEL = new Button("Cancel");
        btnER_CANCEL.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnER_CANCEL.setOnAction(fn -> {
            // reset input fields
            txtareaPackageName.clear();
            for (Object tileObj : gridPackageLoc.getChildren()) {
                Tile tile = (Tile) tileObj;
                if (!tile.getText().equals("0")) {
                    tile.colorTileNull();
                } else {
                    tile.colorTileGrey();
                }
            }

            // clear stuff in edit
            resToEdit.setValue("");
            obsListDishes.clear();
            dishesToAddTemp.clear();

            primaryStage.setScene(sceneMR);
        });

        // #
        HBox layoutERBottom = new HBox(10, btnER_CANCEL, btnER_DONE);
        layoutERBottom.setAlignment(Pos.CENTER);

        // ##
        GridPane layoutER = new GridPane();
        GridPane.setConstraints(layoutERTop, 0, 0);
        GridPane.setConstraints(layoutERBottom, 0, 1);
        GridPane.setVgrow(layoutERTop, Priority.ALWAYS);
        GridPane.setHgrow(layoutERTop, Priority.ALWAYS);
        layoutER.setPadding(new Insets(10, 10, 10, 10));
        layoutER.getChildren().addAll(layoutERTop, layoutERBottom);

        sceneER = new Scene(layoutER, stageWidth.getValue(), stageHeight.getValue());
    }

    /* Describes the functions below.
     * This code creates a button that, when clicked, will delete the selected item from the list of dishes.
     * The button has the text "Delete" and is set to the maximum size possible.
     * When the button is clicked, it will check to see if there is a selected item in the list of dishes. If there is, it will delete that item.
     */
    private void makeSceneEDs(Stage primaryStage) {
        // Buttons
        Button btnEDs_EDIT = new Button("Edit");
        btnEDs_EDIT.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnEDs_EDIT.setOnAction(fn -> {
            if (listDishes.getSelectionModel().getSelectedItem() != null) {
                // take down dish name to edit for sceneED use
                dishToEdit.setValue(listDishes.getSelectionModel().getSelectedItem().toString());

                // put the dish's name to be edit in sceneED
                txtareaDishName.setText(dishToEdit.getValue());

                // put the dish's prep time to be edit in sceneED
                if (!PickUpOperator.getPartnerPackages().isEmpty()) {
                    for (Package packages : PickUpOperator.getPartnerPackages()) {
                        if (!packages.getAllAvailableDishes().isEmpty()) {
                            for (Dish dish : packages.getAllAvailableDishes()) {
                                if (dish.getName().equals(dishToEdit.getValue())) {
                                    spinnerDishPrepTime.getValueFactory().setValue(dish.getFoodPrepareDuration());
                                    break;
                                }
                            }
                        }
                    }
                }

                primaryStage.setScene(sceneED);
            }
        });

        Button btnEDs_DELETE = new Button("Delete");
        btnEDs_DELETE.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnEDs_DELETE.setOnAction(fn -> {
            if (listDishes.getSelectionModel().getSelectedItem() != null) {
                // for now, remove only from observable list
                obsListDishes.remove(listDishes.getSelectionModel().getSelectedItem().toString());
            }
        });

        Button btnEDs_ADD = new Button("Add");
        btnEDs_ADD.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnEDs_ADD.setOnAction(fn -> {
            // clear selected item 
            if (listDishes.getSelectionModel().getSelectedItem() != null) {
                listDishes.getSelectionModel().clearSelection();
            }

            flagAddDish = true;

            primaryStage.setScene(sceneED);
        });

        Button btnEDs_CANCEL = new Button("Cancel");
        btnEDs_CANCEL.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnEDs_CANCEL.setOnAction(fn -> {
            // clear selected items
            if (listDishes.getSelectionModel().getSelectedItem() != null) {
                listDishes.getSelectionModel().clearSelection();
            }

            // reset the dishes of packages in edit
            obsListDishes.clear();
            if (!PickUpOperator.getPartnerPackages().isEmpty()) {
                for (Package packages : PickUpOperator.getPartnerPackages()) {
                    if (!obsListDishes.contains(packages.getName())) {
                        obsListDishes.add(packages.getName());
                    }
                }
            }

            primaryStage.setScene(sceneER);
        });

        Button btnEDs_DONE = new Button("Done");
        btnEDs_DONE.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnEDs_DONE.setOnAction(fn -> {
            if (!obsListDishes.isEmpty()) {
                // clear selected items
                if (listDishes.getSelectionModel().getSelectedItem() != null) {
                    listDishes.getSelectionModel().clearSelection();
                }

                // if obsListDishes does not contain certain dishes, remove them
                if (!PickUpOperator.getPartnerPackages().isEmpty()) {
                    for (Package packages : PickUpOperator.getPartnerPackages()) {
                        if (!packages.getAllAvailableDishes().isEmpty()) {
                            Iterator itrAllAvailableDishes = packages.getAllAvailableDishes().iterator();
                            while (itrAllAvailableDishes.hasNext()) {
                                Dish dish = (Dish) itrAllAvailableDishes.next();
                                if (!obsListDishes.contains(dish.getName())) {
                                    itrAllAvailableDishes.remove();
                                }
                            }
                        }
                    }
                }

                primaryStage.setScene(sceneER);
            }
        });

        // #
        HBox layoutEDsBottom = new HBox(10, btnEDs_CANCEL, btnEDs_DELETE, btnEDs_EDIT, btnEDs_ADD, btnEDs_DONE);
        layoutEDsBottom.setAlignment(Pos.CENTER);

        // ##
        GridPane layoutEDs = new GridPane();
        GridPane.setVgrow(listDishes, Priority.ALWAYS);
        GridPane.setHgrow(listDishes, Priority.ALWAYS);
        GridPane.setConstraints(listDishes, 0, 0);
        GridPane.setConstraints(layoutEDsBottom, 0, 1);
        layoutEDs.setPadding(new Insets(10, 10, 10, 10));
        layoutEDs.setVgap(10);
        layoutEDs.getChildren().addAll(listDishes, layoutEDsBottom);

        sceneEDs = new Scene(layoutEDs, stageWidth.getValue(), stageHeight.getValue());
    }

    /* Describes the functions below.
     * The code above creates a scene for the "Edit Dish" window. 
     * It includes a label for the dish name, a text area for the user to input the dish name, a label for the dish preparation time, and a spinner for the user to select the dish preparation time. 
     * There is also a "Done" button to confirm the edits.
     * When the "Done" button is clicked, the code checks to see if the text area for the dish name is empty. 
     * If it is not empty, the code saves the edits and closes the window. 
     * If it is empty, an error message is displayed.
     */
    private void makeSceneED(Stage primaryStage) {
        // Dish Name
        Label labelDishName = new Label("Dish Name : ");

        txtareaDishName.setPrefHeight(txtareaDishName.DEFAULT_PREF_ROW_COUNT);
        txtareaDishName.setPrefWidth(500);
        txtareaDishName.setPromptText("Enter dish name");

        // Dish Prep Time
        Label labelDishPrepTime = new Label("Dish Preparation Time : ");

        spinnerDishPrepTime.setPrefWidth(500);
        spinnerDishPrepTime.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
        spinnerDishPrepTime.setEditable(true);

        // #
        GridPane layoutEDTop = new GridPane();
        GridPane.setConstraints(labelDishName, 0, 0);
        GridPane.setConstraints(txtareaDishName, 1, 0);
        GridPane.setConstraints(labelDishPrepTime, 0, 1);
        GridPane.setConstraints(spinnerDishPrepTime, 1, 1);
        GridPane.setHalignment(labelDishName, HPos.RIGHT);

        layoutEDTop.setVgap(10);
        layoutEDTop.setHgap(10);
        layoutEDTop.getChildren().addAll(labelDishName, txtareaDishName, labelDishPrepTime, spinnerDishPrepTime);
        layoutEDTop.setAlignment(Pos.CENTER);

        // Button
        Button btnED_DONE = new Button("Done");
        btnED_DONE.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnED_DONE.setOnAction(fn -> {
            if (!txtareaDishName.getText().isEmpty()) {

                if (flagAddDish) {
                    // create a new dish with the written dish & time
                    dishesToAddTemp.add(new Dish(txtareaDishName.getText(),
                            Integer.parseInt(spinnerDishPrepTime.getValue().toString())));
                    System.out.println(txtareaDishName.getText());
                    obsListDishes.add(txtareaDishName.getText());
                    System.out.println(obsListDishes);
                    
                    flagAddDish = false;
                } else {
                    // edit dish, update dish name & dish prep time
                    if (!PickUpOperator.getPartnerPackages().isEmpty()) {
                        for (Package packages : PickUpOperator.getPartnerPackages()) {
                            if (packages.getName().equals(resToEdit.getValue())) {
                                if (!packages.getAllAvailableDishes().isEmpty()) {
                                    for (Dish dish : packages.getAllAvailableDishes()) {
                                        if (dish.getName().equals(dishToEdit.getValue())) {
                                            dish.setName(txtareaDishName.getText());
                                            dish.setFoodPrepareDuration(Integer.parseInt(spinnerDishPrepTime.getValue().toString()));
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // read the updated dishes to display in sceneEDs
                obsListDishes.clear();
                if (!PickUpOperator.getPartnerPackages().isEmpty()) {
                    for (Package packages : PickUpOperator.getPartnerPackages()) {
                        if (packages.getName().equals(resToEdit.getValue())) {
                            if (!packages.getAllAvailableDishes().isEmpty()) {
                                for (Dish dish : packages.getAllAvailableDishes()) {
                                    obsListDishes.add(dish.getName());
                                }
                            }
                            break;
                        }
                    }
                }

                // reset input fields
                txtareaDishName.clear();
                spinnerDishPrepTime.getValueFactory().setValue(5);

                // clear stuff in edit
                dishToEdit.setValue("");

                primaryStage.setScene(sceneEDs);
            }
        });

        Button btnED_CANCEL = new Button("Cancel");
        btnED_CANCEL.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnED_CANCEL.setOnAction(fn -> {
            // reset input fields
            txtareaDishName.clear();
            spinnerDishPrepTime.getValueFactory().setValue(5);

            // clear stuff in edit
            dishToEdit.setValue("");

            if (flagAddDish) {
                flagAddDish = false;
            }

            primaryStage.setScene(sceneEDs);
        });

        // #
        HBox layoutEDBottom = new HBox(10, btnED_CANCEL, btnED_DONE);
        layoutEDBottom.setAlignment(Pos.CENTER);

        // ##
        GridPane layoutED = new GridPane();
        GridPane.setVgrow(layoutEDTop, Priority.ALWAYS);
        GridPane.setHgrow(layoutEDTop, Priority.ALWAYS);
        GridPane.setConstraints(layoutEDTop, 0, 0);
        GridPane.setConstraints(layoutEDBottom, 0, 1);
        layoutED.setPadding(new Insets(10, 10, 10, 10));
        layoutED.getChildren().addAll(layoutEDTop, layoutEDBottom);
        sceneED = new Scene(layoutED, stageWidth.getValue(), stageHeight.getValue());
    }
    
    /* Describes the functions below.
     * The code creates a class called Tile, which inherits from the StackPane class. This class is used to represent each individual tile in the game. 
     * The first two lines create a Rectangle object and a Text object. The Rectangle object represents the border of the tile, while the Text object represents the value of the tile. 
     * The next two lines set the alignment of the tile to the center, and add the Rectangle and Text objects to the tile. 
     * The setOnMouseClicked method sets an event handler for when the tile is clicked. This event handler will change the color of the tile depending on its current color. 
     * The isBlue and isGrey methods are used to check the color of the tile. These methods will be used later in the code.
     */
    private class Tile extends StackPane {

        Text text;
        Rectangle border;

        public Tile(String value) {
            border = new Rectangle(50, 50);
            border.setFill(null);
            border.setStroke(Color.BLACK);

            text = new Text();
            text.setText(value);
            text.setFont(Font.font(30));

            setAlignment(Pos.CENTER);
            getChildren().addAll(border, text);

            setOnMouseClicked(evt -> {
                if (border.getFill() == null) {
                    colorTileBlue();
                } else if (border.getFill() == Color.CORNFLOWERBLUE) {
                    border.setFill(null);
                }
            });
        }

        public boolean isBlue() {
            return border.getFill() == Color.CORNFLOWERBLUE;
        }

        public boolean isGrey() {
            return border.getFill() == Color.GREY;
        }

        public void colorTileNull() {
            border.setFill(null);
        }

        public void colorTileBlue() {
            border.setFill(Color.CORNFLOWERBLUE);
        }

        public void colorTileGrey() {
            border.setFill(Color.GREY);
        }

        public void colorTileLightGrey() {
            border.setFill(Color.LIGHTGRAY);
        }

        public String getText() {
            return text.toString();
        }
    }

}