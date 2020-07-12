package main.java.Servers.Europe;

import main.java.Constants.Constants;
import main.java.Utilities.CustomLogger;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

@WebService
public class EuropeanServer {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    // to manage log files
    static FileHandler fileHandler = null;

    /**
     *Recieve - Setup UDP server to recieve requests.
     *
     * @param serverImpl the server
     */
    public static void recieve(EuropeanServerImpl serverImpl) {

        String responseString = "";
        DatagramSocket dataSocket = null;

        try {

            dataSocket = new DatagramSocket(Constants.SERVER_PORT_EUROPE);
            byte[] buffer = new byte[1000];
            LOGGER.info( "Server started..!!!");

            while (true) {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                dataSocket.receive(request);
                String requestMessage = new String(request.getData(),0,request.getLength());

                LOGGER.info("Received UDP request message: " + requestMessage);

                String request_IP = requestMessage.split(":")[0];
                requestMessage = requestMessage.split(":")[1];

                if (requestMessage.split("=")[0].equalsIgnoreCase("username")) {
                    responseString = serverImpl.playerSignOut(requestMessage.split("=")[1],request_IP);
                }else if (requestMessage.equalsIgnoreCase("transferPlayer")){

                    String playerString = new String(request.getData(),0,request.getLength()).split(":")[2];
                    String[] playerArray = playerString.split(",");

                    responseString = serverImpl.createPlayerAccount(playerArray[0],playerArray[1],Integer.parseInt(playerArray[2]),playerArray[3],playerArray[4],String.valueOf(Constants.SERVER_IP_AMERICA));

                } else {
                    responseString = serverImpl.getPlayerStatus("Admin", "Admin", String.valueOf(request.getPort()), false);
                }

                LOGGER.info("Sent UDP response message: " + responseString);
                DatagramPacket reply = new DatagramPacket(responseString.getBytes(), responseString.length(), request.getAddress(), request.getPort());

                dataSocket.send(reply);
            }

        } catch (SocketException e) {
            LOGGER.info("Exception at socket" +e.getLocalizedMessage());
        } catch (IOException e) {
            LOGGER.info("Exception at IO" +e.getLocalizedMessage());
        } finally {
            if (dataSocket != null) dataSocket.close();
            if (fileHandler != null) fileHandler.close();
        }

    }

    public static void main(String args[]) {

        EuropeanServerImpl serverImplementation = new EuropeanServerImpl();
        serverImplementation.serverSetup(LOGGER);

        Thread server_europe = new Thread(()->
        {
            try {
                //setup logger
                setupLogging();
                //UDP setup
                recieve(serverImplementation);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Exception at main" +e.getLocalizedMessage());
            }
        });

        Endpoint endpoint = Endpoint.publish("http://localhost:"+ Constants.SOAP_PORT_EUROPE +"/server", serverImplementation);
        if (endpoint.isPublished()){
            System.out.println("European server started");
            LOGGER.info("********* SERVER ACTIVATED **********");
        }
        server_europe.setName("thread_Europe_server");
        server_europe.start();

    }

    /**
     * setupLogging. - Setup logger for the class
     */
    private static void setupLogging() throws IOException {
        File files = new File(Constants.SERVER_LOG_DIRECTORY);
        if (!files.exists())
            files.mkdirs();
        files = new File(Constants.SERVER_LOG_DIRECTORY+"EUROPE_Server.log");
        if(!files.exists())
            files.createNewFile();
        fileHandler = CustomLogger.setup(files.getAbsolutePath());
    }
}
