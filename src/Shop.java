import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Shop {
    private User[]users;
    private Product[]products;

    public static final int EMPLOYEE_CHOOSE = 1;
    public static final int CLIENT_CHOOSE = 2;
    public static final int ADD_TO_LENGTH_ARRAY = 1;
    public static final String REGULAR_EMPLOYEE = "Regular employee";
    public static final String MANAGER = "Manager";
    public static final String MANAGEMENT_TEAM = "Management team";
    public static final int INT_REGULAR_EMPLOYEE = 1;
    public static final int INT_MANAGER = 2;
    public static final int INT_MANAGEMENT_TEAM = 3;
    public static final int MIN_LENGTH_PASSWORD = 6;
    public static final int MANAGEMENT_TEAM_DISCOUNT_PERCENTAGE = 30;
    public static final int MANAGER_DISCOUNT_PERCENTAGE = 20;
    public static final int REGULAR_DISCOUNT_PERCENTAGE = 10;
    public static final int MAX_PERCENT = 100;
    public static final int MIN_PERCENT = 1;
    public static final int NOT_CORRECT_LENGTH = 0;
    public static final int MINIMUM_PRICE = 0;
    public static final int INITIALIZE_PURCHASE = 0;
    public static final double INITIALIZE_COST = 0.0F;
    public static final int MINIMUM_PRODUCTS = 0;

    public Shop(){
        this.users = new User[0];
        this.products= new Product[0];
    }
    public void createUser(){
        Scanner scanner = new Scanner(System.in);
        int typeOfUser;
        do {
            System.out.println("Which type of user do you want?");
            System.out.println("Press 1: for Employee");
            System.out.println("Press 2: for Client");
            typeOfUser = scanner.nextInt();
            scanner.nextLine();
        }while (typeOfUser < EMPLOYEE_CHOOSE || typeOfUser > CLIENT_CHOOSE);
        switch (typeOfUser){
            case EMPLOYEE_CHOOSE:{
                String firstName = getFirstNameFromUser();
                String lastName = getLastNameFromUser();
                String username = getUsernameFromUser();
                String password = getPasswordFromUser();
                String rank = getRankFromUser();
                AddEmployeeToArray(firstName,lastName,username,password,rank);
                System.out.println("Signed successfully!");
                break;

            }
            case CLIENT_CHOOSE:{
                String firstName = getFirstNameFromUser();
                String lastName = getLastNameFromUser();
                String username = getUsernameFromUser();
                String password = getPasswordFromUser();
                System.out.println("Are you a club member? (True/ False)");
                boolean clubMember = scanner.nextBoolean();
                AddClientToArray(firstName,lastName,username,password,clubMember);
                System.out.println("Signed successfully!");
                break;
            }
        }

    }

    //???????????????? ???? ??????????
    public void printAllClients(){
        System.out.println("This is all the clients:");
        for (int i = 0; i < this.users.length; i++) {
            System.out.println(i+1 +":" +this.users[i].toString());
            System.out.println();
        }
    }

    public void printClubMembers(){
        boolean isExistClubMembers = false;
        System.out.println("Club members:");
        for (int i = 0, j = 1; i < this.users.length; i++) {
            if(((Client) users[i]).isClubMember()){
                System.out.println(j +": " +users[i].toString());
                System.out.println();
                j++;
                isExistClubMembers = true;
            }
        }
        if (!isExistClubMembers){
            System.out.println("No club members!");
        }
    }

    public void printClientsMadePurchase(){
        boolean madePurchase = false;
        System.out.println("Clients that made a purchase:");
        for (int i = 0,j = 0; i < this.users.length; i++) {
            if(users[i] instanceof Client){
                if(((Client) users[i]).getPurchases() > 0){
                    System.out.println(j+": " +users[i].toString());
                    System.out.println();
                    j++;
                    madePurchase = true;
                }
            }
        }
        if(!madePurchase){
            System.out.println("Non of the clients made purchase!");
        }
    }

    public void printClientThatSumPurchaseIsHigher(){
        String clientName = null;
        double higherPurchase;
        higherPurchase = ((Client)users[0]).getCostAllPurchases();
        clientName = users[0].toString();
        System.out.println("This is the client that the sum of purchase is higher");
        for (int i = 1; i < this.users.length; i++) {
            if(((Client) users[i]).getCostAllPurchases() > higherPurchase){
                higherPurchase = ((Client) users[i]).getCostAllPurchases();
                clientName = users[i].toString();
            }
        }
        System.out.println(clientName);
    }

    public void changeProductStatus(Product product){
        product.setExist(!(product.isExist()));
    }

    public void buy(Cart cart, Shop shop, User user){
        Scanner scanner = new Scanner(System.in);
        int selectProduct;
        int countProductsIsExist;
        int countOfThisProduct;
        double priceToPay;
        boolean isNotInStuck = false;

        do {
            do {
                isNotInStuck = false;
                System.out.println("(Press -1 to end the purchase)\n");
                countProductsIsExist = shop.printProductsInStuckAndReturnCountProductsIsExist();
                if(countProductsIsExist == 0){
                    System.out.println("No products!");
                }
                selectProduct = scanner.nextInt();
                scanner.nextLine();
                if((selectProduct < Main.MINIMUM_PRODUCTS_INDEX || selectProduct > countProductsIsExist ) && selectProduct != Main.END_PURCHASE){
                    System.out.println("The index not exist!");
                }
                else if(selectProduct == Main.END_PURCHASE){
                    break;
                }
                else if(selectProduct >= Main.MINIMUM_PRODUCTS_INDEX && selectProduct < countProductsIsExist) {
                    if (!shop.getProducts()[selectProduct].isExist()) {
                        System.out.println("The item is not in stuck!");
                        isNotInStuck = true;
                    }
                }
            }while ((selectProduct < Main.MINIMUM_PRODUCTS_INDEX || selectProduct > countProductsIsExist) || isNotInStuck);
            if(selectProduct == Main.END_PURCHASE){
                System.out.println("The purchase end");
                break;
            }

            do {
                System.out.println("How many units from this product? ");
                countOfThisProduct = scanner.nextInt();
                scanner.nextLine();
            }while (countOfThisProduct < MINIMUM_PRODUCTS);
            //?????????????? ???? ????????
            for (int i = 0; i < countOfThisProduct; i++) {
                cart.setProducts(cart.addToCart(shop.getProducts()[selectProduct]));
            }
            System.out.println("--------------");
            System.out.println("Shopping list:\n");
            for (int i = 0; i < cart.getProducts().length; i++) {
                System.out.println(cart.getProducts()[i]);

            }
            System.out.println("End list");
            System.out.println("--------------");
            ((Client)user).getCart().setProducts(cart.getProducts());
            System.out.println("Current Price:" + cart.sumPrices(user) + "$");
            System.out.println();

        }while (selectProduct != Main.END_PURCHASE);

        ((Client) user).setPurchases(((Client) user).getPurchases() + 1);
        Date nowDate = Calendar.getInstance().getTime();
        ((Client) user).setLastPurchases(nowDate);
        priceToPay = cart.sumPrices(user);
        if(user instanceof Employee){
            if(((Employee) user).getRank().equals(MANAGEMENT_TEAM)) {
                priceToPay = calculatePercent(priceToPay,MANAGEMENT_TEAM_DISCOUNT_PERCENTAGE);
            }
            else if(((Employee) user).getRank().equals(MANAGER)) {
                priceToPay = calculatePercent(priceToPay, MANAGER_DISCOUNT_PERCENTAGE);
            }
            else if(((Employee) user).getRank().equals(REGULAR_EMPLOYEE)) {
                priceToPay = calculatePercent(priceToPay, REGULAR_DISCOUNT_PERCENTAGE);
            }
        }

        System.out.println("The price is: " + priceToPay + "$");
        ((Client) user).setCostAllPurchases(((Client) user).getCostAllPurchases() +priceToPay);
        Product[]newProducts = new Product[0];
        cart.setProducts(newProducts);
    }

    private double calculatePercent(double price, int percent){
        price -= (price/100)*percent;
        return price;
    }

    public User login(){
        Scanner scanner = new Scanner(System.in);
        User user = null;
        int choose;
        do {
            System.out.println("Choose type account: (Press 1 - Employee , 2 - Client)");
            choose = scanner.nextInt();
            scanner.nextLine();//???????? ?????? - ?????????? ???????? ?????????? ???????? ???????????? ???????? ?????????? ???????????? ?????? ?? ?????? ???????? ???????????? ???????? ???????? ???????? ?????? ???????? ???? ???????????? ???????? ??????????????
            if(choose < EMPLOYEE_CHOOSE || choose > CLIENT_CHOOSE){
                System.out.println("The option you choose is invalid");
            }
        }while (choose < EMPLOYEE_CHOOSE || choose > CLIENT_CHOOSE);

        switch (choose){
            case EMPLOYEE_CHOOSE:{
                user = isExistAccount(this.users,true);
                break;
            }

            case CLIENT_CHOOSE:{
                user = isExistAccount(this.users,false);
                break;
            }
        }
        return user;

    } //login

    private String getFirstNameFromUser(){
        Scanner scanner = new Scanner(System.in);
        String firstName = null;
        boolean validName;
        do {
            System.out.println("Enter your first name: ");
            firstName = scanner.nextLine();
            validName = isValidName(firstName);
            if(!validName){
                System.out.println("The name is invalid");
            }
        } while (!validName);
        return firstName;
    }
    private String getLastNameFromUser(){
        Scanner scanner = new Scanner(System.in);
        String lastName = null;
        boolean validLastName;
        do {
            System.out.println("Enter your last name: ");
            lastName = scanner.nextLine();
            validLastName = isValidName(lastName);
            if(!validLastName){
                System.out.println("The last name is invalid");
            }
        }while (!validLastName);
        return lastName;
    }
    private String getUsernameFromUser(){
        Scanner scanner = new Scanner(System.in);
        String username = null;
        boolean checkUsername;
        do {
            System.out.println("Enter your username: ");
            username = scanner.nextLine();
            checkUsername = isUsernameExist(username);
            if(checkUsername){
                System.out.println("The username is busy!");
            }
        }while (checkUsername || username.length() == NOT_CORRECT_LENGTH);
        return username;
    }
    private String getPasswordFromUser() {
        Scanner scanner = new Scanner(System.in);
        String password = null;
        boolean strongPassword;
        do {
            System.out.println("Enter your password: ");
            password = scanner.nextLine();
            strongPassword = isStrongPassword(password);
            if(!strongPassword){
                System.out.println("The password is not strong!");
            }
        }while (!strongPassword);
        return password;
    }
    private String getRankFromUser(){
        Scanner scanner = new Scanner(System.in);
        int choose;
        String rank = null;
        do {
            System.out.println("Choose your rank");
            System.out.println("1 - Regular \n2 - Manager\n3 - Management team");
            choose = scanner.nextInt();
        }while (choose < INT_REGULAR_EMPLOYEE || choose > INT_MANAGEMENT_TEAM);
        switch (choose){
            case INT_REGULAR_EMPLOYEE:{
                rank = REGULAR_EMPLOYEE;
                break;
            }
            case INT_MANAGER:{
                rank = MANAGER;
                break;
            }
            case INT_MANAGEMENT_TEAM:{
                rank = MANAGEMENT_TEAM;
                break;
            }
        }
        return rank;

    }


    private String inputUsername(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username: ");
        String username = scanner.nextLine();
        return username;
    }

    private String inputPassword(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter password: ");
        String password = scanner.nextLine();
        return password;
    }

    public void addProductToArray(){
        Scanner scanner = new Scanner(System.in);
        String productName;
        double price;
        int discountPercent;
        do {
            System.out.println("Enter product name: ");
            productName = scanner.nextLine();
        }while (productName.length() == NOT_CORRECT_LENGTH);
        do {
            System.out.println("Enter price: ");
            price = scanner.nextDouble();
        }while (price <= MINIMUM_PRICE);
        do {
            System.out.println("Enter club member discount percentage: ");
            discountPercent = scanner.nextInt();
        }while (discountPercent < MIN_PERCENT || discountPercent > MAX_PERCENT);
        Product newProduct = new Product(productName,price,discountPercent,true);
        Product[] products= new Product[this.products.length +1];
        for (int i = 0; i < this.products.length; i++){
            products[i] = this.products[i];
        }
        products[products.length-1] = newProduct;
        this.products = products;
    }

    public void printProducts(Shop shop){
        System.out.println("Choose product number to change product status: ");
        for (int i = 0, j = 1; i < shop.products.length;i++){
            System.out.println("(Press " + i +")" +"\n" +j +") " + this.products[i]);
            j++;
        }
    }

    //????????
    public int printProductsInStuckAndReturnCountProductsIsExist(){
        int lengthProductsArray = 0;
        if(products.length > NOT_CORRECT_LENGTH) {
            for (int i = 0, j = 1; i < products.length; i++) {
                if(products[i].isExist()){
                    System.out.println(j+":" +"(Press " + i + " to add to cart)\n" + products[i]);
                    lengthProductsArray = j;
                    j++;
                }
            }
        }
        return lengthProductsArray;

    }

    private User isExistAccount(User[]users, boolean isEmployee){
        User user = null;
        String inputUsername = inputUsername();
        String inputPassword = inputPassword();
        for (int i = 0; i < this.users.length; i++) {
            if(this.users[i] instanceof Employee && isEmployee){
                if(this.users[i].getUsername().equals(inputUsername)){
                    if(this.users[i].getPassword().equals(inputPassword)){
                        user = users[i];
                        break;
                    }

                }
            }else if(this.users[i] instanceof Client && !isEmployee){
                if(this.users[i].getUsername().equals(inputUsername)){
                    if(this.users[i].getPassword().equals(inputPassword)){
                        user = users[i];
                        break;
                    }
                }
            }
        }
        return user;
    }

    private void AddClientToArray(String firstName, String lastName, String username, String password, boolean isClubMember) {
        User[] newArray = new User[this.users.length + ADD_TO_LENGTH_ARRAY];
        for (int i = 0; i < this.users.length;i++){
            newArray[i] = this.users[i];
        }
        User userToAdd = new Client(firstName,lastName,username,password, isClubMember,INITIALIZE_PURCHASE,INITIALIZE_COST,null);
        newArray[this.users.length] = userToAdd;
        this.users = newArray;
    }

    private void AddEmployeeToArray(String firstName, String lastName, String username, String password,String rank) {
        User[] newArray = new User[this.users.length + ADD_TO_LENGTH_ARRAY];
        for (int i = 0; i < this.users.length;i++){
            newArray[i] = this.users[i];
        }
        User userToAdd = new Employee(firstName,lastName,username,password,false,INITIALIZE_PURCHASE,INITIALIZE_COST,null, rank);
        newArray[this.users.length] = userToAdd;
        this.users = newArray;
    }

    private boolean isStrongPassword(String password){
        boolean strong = false;
        if(password.length() >= MIN_LENGTH_PASSWORD){
            strong = true;
        }
        return strong;
    }

    private boolean isValidName(String name){
        boolean valid = false;
        for (int i = 0; i < name.length();i++){
            char currentChar = name.charAt(i);
            if(Character.isAlphabetic(currentChar)){
                valid = true;
            }else {
                valid = false;
                break;
            }
        }
        return valid;
    }

    private boolean isUsernameExist(String usernameToCheck){
        boolean exist = false;
        if(usernameToCheck.length() != 0) { // ???????? ???? ???????????? ?????????? ???? ??????
            if (this.users.length != 0){ //???????? ???? ???????? ?????????? ???????? ??0
                for (int i = 0; i < this.users.length; i++) {
                    User currentUser = this.users[i];
                    if (currentUser.getUsername().equals(usernameToCheck)) {
                        exist = true;
                        break;
                    }
                }
            }

        }
        else {
            System.out.println("You must to insert correct username!");
        }
        return exist;
    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Product[] getProducts() {
        return products;
    }

    public void setProducts(Product[] products) {
        this.products = products;
    }
}
