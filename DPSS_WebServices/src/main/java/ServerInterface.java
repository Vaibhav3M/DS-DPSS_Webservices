package main.java;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface ServerInterface {

    //Player methods

    /**
     * Create player and add to hashtable.
     *
     * @return Return the status
     */

    @WebMethod
    public String createPlayerAccount(String FirstName, String LastName, float Age, String Username, String Password, String IPAddress);

    /**
     * Player sign in method.
     *
     * @param Username  the username
     * @param Password  the password
     * @param IPAddress the ip address
     * @return Return the status
     */
    @WebMethod
    public String playerSignIn (String Username, String Password, String IPAddress);

    /**
     * Player sign out method.
     *
     * @param Username  the username
     * @param IPAddress the ip address
     * @return Return the status
     */
    @WebMethod
    public String playerSignOut (String Username, String IPAddress);

    /**
     * Transfers player account between servers
     *
     * @param Username  the username
     * @param Password the ip address
     * @param OldIPAddress user's current IP address
     * @param NewIPAddress the new IP address
     * @return Returns the transfer status
     */
    @WebMethod
    public String transferAccount(String Username, String Password, String OldIPAddress, String NewIPAddress);


    // Admin method

    /**
     * Gets player status from server and other servers using UDP.
     *
     * @param AdminUsername     the admin username
     * @param AdminPassword     the admin password
     * @param IPAddress         the ip address
     * @param checkOtherServers the check other servers
     * @return the player status
     */
    @WebMethod
    public String getPlayerStatus (String AdminUsername, String AdminPassword, String IPAddress, Boolean checkOtherServers);


    /**
     * Suspends the user account, if account exists on the server
     *
     * @param AdminUsername     the admin username
     * @param AdminPassword     the admin password
     * @param AdminIP         the admin ip address
     * @param UsernameToSuspend the user to suspend
     * @return Returns the suspend status
     */
    @WebMethod
    public String suspendAccount(String AdminUsername, String AdminPassword, String AdminIP, String UsernameToSuspend);

}

