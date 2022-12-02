package game;

import java.awt.Color;

/**
 * Purpose: This class is to contain all constants using throughout the game
 * File name: GameBasic.java
 * Course: CST8221 JAP, Lab Section: 301
 * Date: 4 Dec 2022
 * Prof: Paulo Sousa
 * Assignment: A12
 * Compiler: Eclipse IDE - 2021-09 (4.21.0)
 * Identification [Ngoc Phuong Khanh Le, 041004318], [Dan McCue, 040772626]
 */

/**
 * Class Name: GameBasic.java
 * Constants list: DEFAULT_DIM, DEFAULT_ICON, WIN_ICON, LOSE_ICON, WIN, DEFAULT_COLOR, ZERO_COLOR, DEFAULT_GAMECONFIG, IS_RECEIVED
 * STATE_EMPTY, STATE_SOLUTION, STATE_SHUFFLE, PROTOCOL_SEPARATOR, PROTOCOL_SPACE, ARRAY_SEPARATOR, PROTOCOL_CONNECT,
 * PROTOCOL_SENDGAME, PROTOCOL_RECEIVEGAME, PROTOCOL_SENDDATA, PROTOCOL_END, DEFAULT_USER, DEFAULT_PORT, DEFAULT_SERVER
 * Purpose: This class is contain all constants using throughout the game
 * @author Ngoc Phuong Khanh Le, Dan McCue
 * @version 3
 * @see game
 * @since 4.21.0
 */
public class GameBasic {
	/**
	 * Static variable for default dimension
	 */
	public final static int DEFAULT_DIM = 3;
	/**
	 * Static string for default icon
	 */
	public final static String DEFAULT_ICON = "default";
	/**c
	 * Static string for default win icon
	 */
	public final static String WIN_ICON = "win";
	/**
	 * Static string for default lose icon
	 */
	public final static String LOSE_ICON = "lose";
	/**
	 * Static string for default win int
	 */
	public final static int WIN = -1;
	/**
	 * Static string for default white color
	 */
	public final static Color DEFAULT_COLOR = Color.WHITE;
	/**
	 * Static string for default black color for 0 button
	 */
	public final static Color ZERO_COLOR = Color.BLACK;
	/**
	 * State for empty buttons
	 */
	public final static int STATE_EMPTY = 0;
	/**
	 * State for solution buttons
	 */
	public final static int STATE_SOLUTION = 1;
	/**
	 * State for shuffled buttons
	 */
	public final static int STATE_SHUFFLE = 2;
	/**
	 * Separator using for some protocols
	 */
	public final static String PROTOCOL_SEPARATOR = "#";
	/**
	 * Separator for game config in send and receive game config
	 */
	public final static String PROTOCOL_SPACE = " ";
	/**
	 * Separator for game config in send and receive game config
	 */
	public final static String ARRAY_SEPARATOR = ",";
	/**
	 * Protocol for connections
	 */
	public final static String PROTOCOL_CONNECT = "P0";
	/**
	 * Protocol for sending game config
	 */
	public final static String PROTOCOL_SENDGAME = "P1";
	/**
	 * Protocol for receiving game config
	 */
	public final static String PROTOCOL_RECEIVEGAME = "P2";
	/**
	 * Protocol for sending game data
	 */
	public final static String PROTOCOL_SENDDATA = "P3";
	/**
	 * Protocol for ending connections
	 */
	public final static String PROTOCOL_END = "P4";
	/**
	 * Default username
	 */
	public final static String DEFAULT_USER = "Eri";
	/**
	 * Default server 
	 */
	public final static String DEFAULT_SERVER = "localhost";
	/**
	 * Default port 
	 */
	public final static String DEFAULT_PORT = "1111";
	/**
	 * Default game config 
	 */
	public final static String DEFAULT_GAMECONFIG = "3 Number 8,4,5,3,7,0,1,6,2";
	/**
	 * Check if game config is received from server or not
	 */
	public final static boolean RECEIVED_CONFIG = true;
}
