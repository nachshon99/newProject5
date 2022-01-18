import java.util.Date;

public class Employee extends Client {
    private String rank;

    public Employee(String firstName, String lastName, String username, String password, boolean isClubMember, int purchases, double costAllPurchases, Date lastPurchases, String rank){
        super(firstName, lastName, username,password,isClubMember,purchases,costAllPurchases, lastPurchases);
        this.rank = rank;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String startShow(){
        String output = super.startShow().substring(0,super.startShow().indexOf('!')) + "(" + this.rank + ")!\n";
        return output;
    }

    public String toString() {
        return super.toString();
    }
}
