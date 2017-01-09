package helpers;

/**
 * @author Niklas Karlsson
 * @version 1.0
 * @since 2016-09-15
 */
public class CMDmeny {
  public static void print() {
    System.out.println("what to do? \n" +
        "1 : insertNewUser(....)\n"+
        "2 : deleteUser(....) \n" +
        "3 : InsertNewBike(..) \n"+
        "4 : SelectAvalibleBikes() \n" +
        "5 : SearchUserbyWildcard() \n" +
        "6 : ....");
  }
}
