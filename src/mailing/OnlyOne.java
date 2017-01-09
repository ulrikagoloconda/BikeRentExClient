package mailing;

import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;

/**
 * @author Niklas Karlsson
 * @version 1.0
 * @since 2016-09-15
 */
public interface OnlyOne {

  static boolean closeThis() {
    // TODO Auto-generated method stub
    final int PORT = 9999;
    ServerSocket socket;

    //private static void checkIfRunning() {
    try {
      //Bind to localhost adapter with a zero connection queue
      socket = new ServerSocket(PORT, 0, InetAddress.getByAddress(new byte[]{127, 0, 0, 1}));
    } catch (BindException e) {
      return true;
    } catch (IOException e) {
      System.err.println("Unexpected error.");
      e.printStackTrace();
      return true;
    }
    System.out.println("program startad OK!");
    return true;
  }
}
