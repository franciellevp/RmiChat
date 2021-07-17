/**
 * 
 */
/**
 * @author flan
 *
 */
module ChatRMI {
	requires java.rmi;
	exports chat;
	exports chat.Server;
	exports chat.User;
	exports chat.Room;
	exports chat.main;
}