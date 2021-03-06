import java.util.Scanner;

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
        Shop shop = new Shop();
        Scanner scanner = new Scanner(System.in);
        User user;
        int option;
        do {
            do {
                printMenu();
                System.out.println("What do you want to do? ");
                option = scanner.nextInt();
            }while (option < CREATE_ACCOUNT || option > EXIT );
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
                                        boolean isInStuck = false;
                                        if (shop.getProducts().length != 0) {
                                            do {
                                                shop.printProducts(shop);
                                                changeStatus = scanner.nextInt();
                                                scanner.nextLine();
                                                if (changeStatus < MINIMUM_PRODUCTS_INDEX || changeStatus >= shop.getProducts().length){
                                                    System.out.println("The index is not exist!");
                                                }
                                            } while (((changeStatus < MINIMUM_PRODUCTS_INDEX || changeStatus >= shop.getProducts().length)) || isInStuck);
                                            shop.changeProductStatus(shop.getProducts()[changeStatus]);
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