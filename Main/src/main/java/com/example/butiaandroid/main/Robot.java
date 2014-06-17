package com.example.butiaandroid.main;


import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

//import org.apache.commons.net.telnet.EchoOptionHandler;
//import org.apache.commons.net.telnet.InvalidTelnetOptionException;
//import org.apache.commons.net.telnet.SuppressGAOptionHandler;
//import org.apache.commons.net.telnet.TelnetClient;
//import org.apache.commons.net.telnet.TerminalTypeOptionHandler;

public class Robot  extends Thread {

    private static Robot miRobot = null;

    public static final String ERROR_SENSOR_READ = "-1";
    public static final int ERROR_SENSOR_READ_VALUE = -1;
    public static final String BOBOT_HOST = "192.168.1.33";
    public static final int BOBOT_PORT = 2009;

    private String host;
    private int port;
    private String portStreaming;

    private Socket client;
    private PrintWriter out;
    private BufferedReader in;
    private String version;
    private boolean streaming;

    public String getHost() {
        return host;
    }

    public void conectar(String host, int port) throws Exception {
        this.host = host;
        this.port = port;
        reconnect();
    }

    public boolean isStreaming() {
        return streaming;
    }

    public void setStreaming(boolean streaming) {
        this.streaming = streaming;
    }

    public String getPortStreaming() {
        return portStreaming;
    }

    public void setPortStreaming(String portStreaming) {
        this.portStreaming = portStreaming;
    }

    public static Robot getInstance() {
        if (miRobot == null) {
            miRobot = new Robot();
        }
        return miRobot;
    }


    private Robot() {
    }



    /**
     * Executes a command in butia.
     * stores the response to this.mensaje.
     *
     * @param msg message to be execute
     * @return Robot.ERROR_SENSOR_READ on failure
     */
    private synchronized String doCommand(String msg) {
        //se tiene que ejecutar en un hilo aparte, por eso en el 2012 no deberia andar nada..
        String respuesta = Robot.ERROR_SENSOR_READ;
        try {

            // Log.w("Robot doCommand()", "Mensaje: " + msg);

            this.out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(this.client.getOutputStream())), true);
            this.out.println(msg);
            this.in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
            respuesta = this.in.readLine();

            if ( (respuesta == null) || (respuesta.equals("")) ) {
                respuesta = Robot.ERROR_SENSOR_READ;
            }

            //Log.w("Robot doCommand()", "Respuesta: " + respuesta);

        } catch (Exception e) {
          //  Log.d("ROBOT", e.getMessage());
            respuesta = Robot.ERROR_SENSOR_READ;
        }

        return respuesta;
    }

    /**
     * connect o reconnect the bobot
     *
     * @return Robot.ERROR_SENSOR_READ on failure
     */
    private String reconnect() throws Exception {

        String respuesta = "0";
        this.close();
        try {
            InetAddress serverAddr = InetAddress.getByName(this.host);
            this.client = new Socket(serverAddr, this.port);


            Log.w("Robot reconnect()", "Paso por reconnect va a LIST");

            // bobot server instance is running, but we have to check for new or remove hardware
            this.doCommand("LIST");

        } catch (Exception e) {
            Log.e("ROBOT reconect()", e.getMessage());
            respuesta = Robot.ERROR_SENSOR_READ;
            throw new Exception("Error al conectar");
        }

        return respuesta;
    }

    /**
     * ask bobot for refresh is state of devices connected
     *
     * @return Robot.ERROR_SENSOR_READ on failure
     */
    private String refresh() {
        String msg = "REFRESH";

        Log.w("Robot refresh()", "Paso por refresh va a enviar mensaje" + msg);

        // bobot server instance is running, but we have to check for new or remove hardware
        String respuesta = this.doCommand(msg);
//              if (respuesta.equals(Robot.ERROR_SENSOR_READ)) {
//                      respuesta = this.reconnect();
//              }

        return respuesta;
    }

    /**
     * close the comunication with the bobot
     *
     * @return Robot.ERROR_SENSOR_READ on failure
     */
    public String close() {

        String respuesta = Robot.ERROR_SENSOR_READ;
        try {
            if (this.client != null) {

                this.client.close();
                respuesta = "0";
            }

        } catch (Exception e) {
            Log.e("ROBOT close()", e.getMessage());
            respuesta = Robot.ERROR_SENSOR_READ;
        }

        return respuesta;
    }

    /*****************************************************************
     * Operations to the principal module
     *****************************************************************/

//      /**
//       * open the module "modulename"
//       * @return Robot.ERROR_SENSOR_READ on failure
//       */
//      private String openModule(String modulename) {
//
//              String msg = "OPEN " + modulename;
//              return this.doCommand(msg);
//
//      }

    /**
     * call the module "modulename"
     *
     * @return Robot.ERROR_SENSOR_READ on failure
     */
    private String callModule(String modulename, String function, String params) {

        String msg = "CALL " + modulename + " " + function;
        if (params != "") {
            msg += " " + params;
        }
        return this.doCommand(msg);

    }

    /**
     * Close bobot service
     *
     * @return Robot.ERROR_SENSOR_READ on failure
     */
    public String closeService() {

        String msg = "QUIT";
        return this.doCommand(msg);

    }


    /*****************************************************************
     * Useful functions
     *****************************************************************/

    /**
     * returns if the module_name is present
     */
    public boolean isPresent(String module_name) {

        boolean resultado = false;
        String[] module_list = this.getModulesList();

        if (module_list != null) {
            for(int i = 0; i < module_list.length; i++) {
                if (module_list[i].equals(module_name)) {
                    resultado = true;
                }
            }
        }

        return resultado;
    }

    /**
     * returns a list of modules
     *
     * @return null on failure
     */
    public String[] getModulesList() {

        String  modulos = doCommand("LIST");
        if ( ! ((modulos == null) || (modulos.equals(Robot.ERROR_SENSOR_READ)))) {
            return modulos.split(",");
        }
        else {
            return null;
        }

    }

    /**
     * loopBack: send a message to butia and wait to recibe the same
     *
     * @return Robot.ERROR_SENSOR_READ on failure
     */
    public String loopBack(String data) {

        String msg = "lback send " + data;
        return this.doCommand(msg);
    }

    /*****************************************************************
     * Operations for motors driver
     *****************************************************************/

    /**
     * @return Robot.ERROR_SENSOR_READ on failure
     */
    public String set2MotorSpeed(String leftSense, String leftSpeed, String rightSense, String rightSpeed) {

        String msg = leftSense + " " + leftSpeed + " " + rightSense + " " + rightSpeed;
        return this.callModule("motors", "setvel2mtr", msg);

    }

    /**
     * @return Robot.ERROR_SENSOR_READ on failure
     */
    public String setMotorSpeed(String idMotor, String sense, String speed) {

        String msg = idMotor + " " + sense + " " + speed;
        return this.callModule("motors", "setvelmtr", msg);

    }

    /*****************************************************************
     * Operations for ax driver
     *****************************************************************/

    /**
     * @param idMotor
     * @return Robot.ERROR_SENSOR_READ on failure
     */
    public String wheelMode(String idMotor) {

        String msg = idMotor;
        return this.callModule("ax", "wheelMode", msg);

    }

    /**
     * @param idMotor
     * @param min:    valor minimo 0
     * @param max:    valor maximo 1023
     * @return Robot.ERROR_SENSOR_READ on failure
     */
    public String jointMode(String idMotor, String min, String max) {

        String msg = idMotor + " " + min + " " + max;
        return this.callModule("ax", "jointMode", msg);

    }

    /**
     * @param idMotor
     * @param pos
     * @return Robot.ERROR_SENSOR_READ on failure
     */
    public String setPosition(String idMotor, String pos) {

        String msg = idMotor + " " + pos;
        return this.callModule("ax", "setPosition", msg);

    }

    /**
     * @param idMotor
     * @return Robot.ERROR_SENSOR_READ on failure
     */
    public String getPosition(String idMotor) {

        String msg = idMotor;
        return this.callModule("ax", "getPosition", msg);

    }


    /*****************************************************************
     * Operations for butia.lua driver
     *****************************************************************/

    /**
     * # returns the approximate charge of the battery
     *
     * @return Robot.ERROR_SENSOR_READ_VALUE on failure
     */
    public int getBatteryCharge() {

        int carga = Robot.ERROR_SENSOR_READ_VALUE;
        try {
            String valor = this.callModule("butia", "getVolt", "");
            carga = Integer.parseInt(valor);

        } catch (Exception e) {
            Log.e("ROBOT getBatteryCharge()", e.getMessage());
        }

        return carga;
    }


    /**
     * returns the firmware version
     *
     * @return Robot.ERROR_SENSOR_READ on failure
     */
    public int getFirmwareVersion(){

        String valor;
        valor = this.callModule("admin", "getVersion", "");
        return Integer.parseInt(valor);

    }


    /*************** SENSORS *************************************************/

    /**
     * return the value of button: 1 if pressed, 0 otherwise
     *
     * @param number
     * @return Robot.ERROR_SENSOR_READ_VALUE on failure
     */
    public int getButton(String number) {

        String valor;
        valor = this.callModule("button:" + number, "getValue", "");
        return Integer.parseInt(valor);
    }

    /**
     * return the value en ambient light sensor
     *
     * @param number
     * @return Robot.ERROR_SENSOR_READ_VALUE on failure
     */
    public int getLight(String number) {

        String valor;
        valor = this.callModule("light:" + number, "getValue", "");
        return Integer.parseInt(valor);
    }

    /**
     * return the value of the distance sensor
     *
     * @param number
     * @return Robot.ERROR_SENSOR_READ_VALUE on failure
     */
    public int getDistance(String number) {

        String distancia;
        distancia = this.callModule("distanc:" + number, "getValue", "");
        return Integer.parseInt(distancia);
    }

    /**
     * return the value of the grayscale sensor
     *
     * @param number
     * @return Robot.ERROR_SENSOR_READ_VALUE on failure
     */
    public int getGray(String number) {

        String valor;
        valor = this.callModule("grey:" + number, "getValue", "");
        return Integer.parseInt(valor);
    }

    /**
     * return the value of the temperature sensor
     *
     * @param number
     * @return Robot.ERROR_SENSOR_READ_VALUE on failure
     */
    public int getTemperature(String number) {

        String temperatura;
        temperatura = this.callModule("temp:" + number, "getValue", "");
        return Integer.parseInt(temperatura);
    }


    /**
     * return the value of the resistance sensor
     *
     * @param number
     * @return Robot.ERROR_SENSOR_READ_VALUE on failure
     */
    public int getResistance(String number) {

        String valor;
        valor = this.callModule("res:" + number, "getValue", "");
        return Integer.parseInt(valor);
    }


    /**
     * with 1 turn led on, 0 off
     *
     * @param number
     * @param on_off = 0 or 1
     * @return Robot.ERROR_SENSOR_READ on failure
     */
    public String setLed(String number, String on_off) {

        return this.callModule("led:" + number, "turn", on_off);

    }



////////////////////////////////////////////////////////
    volatile boolean on = false;
    volatile String msg = null;

    @Override
    public void run() {
        while (on) {
            if (!TextUtils.isEmpty(msg)) {
                this.callModule("motors", "setvel2mtr", msg);
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.d("Robot", "deja de enviar velocidades");
    }

    public void setOff() {
        this.on = false;
    }


    public void set2MotorMsg(String leftSense, String leftSpeed, String rightSense, String rightSpeed) {
        msg = leftSense + " " + leftSpeed + " " + rightSense + " " + rightSpeed;
    }


    public void start2MotorThread() {
        if (!on) {
            on = true;
            msg = null;
            Thread thread = new Thread(this);
            thread.start();
            Log.d("Robot", "nuevo hilo");

        }
    }



    public String getModuleValue (String module) {
        String v = callModule(module, "getValue", "");
        if ("-1".equals(v)){
            return "";
        }else{
            return v;
        }
    }


}
