import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.concurrent.ConcurrentSkipListMap;

public class Main {
    public static final int CREATE_ACCOUNT = 1;
    public static final int CONNECT_TO_ACCOUNT = 2;
    public static final int EXIT = 3;
    public static final int PRINT_ALL_CLIENTS = 1;
    public static final int PRINT_CLUB_MEMBERS = 2;
    public static final int PRINT_PURCHASE_CLIENTS = 3;
    public static final int PRINT_PURCHASE_HIGHEST = 4;
    public static final int ADD_PRODUCT = 5;
    public static final int CHANGE_PRODUCT_STATUS = 6;
    public static final int BUY = 7;
    public static final int LOG_OUT = 8;
    public static final int MINIMUM_PRODUCTS_INDEX = 0;
    public static final int END_PURCHASE = -1;

    public static void main(String[] args) {

        //System.out.println(nowDate);


        /*System.out.println(client);
        System.out.println(employee);*/
        Shop shop = new Shop();


        //למחוק
        /*Product [] product1= new Product[5];
        //Cart cart1 = new Cart();
        User[]users = new User[5];
        users[0] = new Client("Nachshon", "Kedar", "Nachshon99", "a123b456",true,0,2.1,null,null);
        users[1] = new Employee("Yossi", "Shitrit", "Yossi10", "a1b2c3d4",true,1,10.5,null,null,"Manager");
        users[2] = new Client("Daniel", "aaaaa", "Dani10", "a123b456",false,0,11.3,null,null);
        users[3] = new Client("aaa", "aaa", "aaa", "aaa",true,0,0,cart1,null);
        users[4] = new Employee("aa", "aa", "aa", "aa",true,0,0,null,null,"Manager");

        Product[] product = new Product[2];
        product[0] = new Product("Milk", 5.5, 10,true);
        product[1] = new Product("Bread", 7, 15,true);
        shop.setProducts(product);

        shop.setUsers(users);*/

        //shop.printProducts();


        /*shop.printAllClients();
        System.out.println("-----");
        shop.printClubMembers();
        System.out.println("-----");
        shop.printClientsMadePurchase();
        System.out.println("-----");
        shop.printClientThatSumPurchaseIsHigher();
        System.out.println("-----");
        shop.printProductsInStuck();*/


        ////למחוק

        Scanner scanner = new Scanner(System.in);

        User user;
        int option;
        int shopping=0;
        do {
            do {
                printMenu();
                System.out.println("What do you want to do? ");
                option = scanner.nextInt();
            }while (option < 1 || option > 3);
            switch (option){
                case CREATE_ACCOUNT:{
                    shop.createUser();
                    break;
                }
                case CONNECT_TO_ACCOUNT:{
                    Cart cart = new Cart();
                    do {
                        user = shop.login();
                        if(user == null){
                            System.out.println("This account doesn't exist!");
                            break;
                        }
                    }while (user == null);
                    if(user == null){
                        break;
                    }
                    System.out.println(user.startShow());

                    int select=0;
                    if (user instanceof Employee) {
                        do {
                            do {
                                printNewMenu();
                                select = scanner.nextInt();
                                scanner.nextLine();
                                switch (select) {
                                    case PRINT_ALL_CLIENTS: {
                                        shop.printAllClients();
                                        break;
                                    }
                                    case PRINT_CLUB_MEMBERS: {
                                        shop.printClubMembers();
                                        break;
                                    }
                                    case PRINT_PURCHASE_CLIENTS: {
                                        shop.printClientsMadePurchase();
                                        break;
                                    }
                                    case PRINT_PURCHASE_HIGHEST: {
                                        shop.printClientThatSumPurchaseIsHigher();
                                        break;
                                    }
                                    case ADD_PRODUCT: {
                                        System.out.println();
                                        shop.addProductToArray();
                                        break;
                                    }
                                    case CHANGE_PRODUCT_STATUS: {
                                        int changeStatus;
                                        if (cart.getProducts().length != 0) {
                                            do {
                                                shop.printProducts();
                                                changeStatus = scanner.nextInt();
                                                scanner.nextLine();
                                            } while (changeStatus < MINIMUM_PRODUCTS_INDEX || changeStatus > cart.getProducts().length);
                                            shop.changeProductStatus(cart.getProducts()[changeStatus]);
                                            System.out.println("Status change succeeded");
                                        } else {
                                            System.out.println("No products in the list!");
                                        }
                                        break;

                                    }
                                    case BUY: {
                                        shop.buy(cart,shop,user);
                                        break;
                                    }
                                }
                                if (select < PRINT_ALL_CLIENTS || select > LOG_OUT) {
                                    System.out.println("Please select a valid option!");
                                }
                            } while (select < PRINT_ALL_CLIENTS || select > LOG_OUT);
                        } while (select != LOG_OUT);
                    }
                    else if(user instanceof Client) {
                        shop.buy(cart,shop,user);
                    }
                    break;
                }
            case EXIT:{
                break;
            }
        }


        }while (option != EXIT);



    }


    private static void printMenu(){
        System.out.println("Press 1 - Create user.");
        System.out.println("Press 2 - Connect to an existing account.");
        System.out.println("Press 3 - Exit.");
    }
    private static void printNewMenu(){
        System.out.println("Press 1 - to print all client list.");
        System.out.println("Press 2 - to print club members.");
        System.out.println("Press 3 - to print clients that purchased at least ones");
        System.out.println("Press 4 - to print the client with the highest purchase rate.");
        System.out.println("Press 5 - to add product.");
        System.out.println("Press 6 - to change product status");
        System.out.println("Press 7 - to make purchase");
        System.out.println("Press 8 - log out");

    }



}