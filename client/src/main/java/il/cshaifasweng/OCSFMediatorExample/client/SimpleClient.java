package il.cshaifasweng.OCSFMediatorExample.client;

import org.greenrobot.eventbus.EventBus;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.*;

import javax.management.monitor.StringMonitor;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class SimpleClient extends AbstractClient {


	private static final Logger LOGGER =
			Logger.getLogger(Client.class.getName());
	private Client user = null;
	private ChainManager chainManager = null;
	private StoreManager storeManager = null;
	private static SimpleClient client = null;
	public static  Object data;
	public static List<Item> itemList=null;

	public Client getUser() {
		return user;
	}
	public void setUser(Client user) {
		this.user = user;
	}
	public ChainManager getChainManager() {
		return chainManager;
	}
	public void setChainManager(ChainManager chainManager) {
		this.chainManager = chainManager;
	}
	public StoreManager getStoreManager() {
		return storeManager;
	}
	public void setStoreManager(StoreManager storeManager) {
		this.storeManager = storeManager;
	}
	/**
	 * Constructs the client.
	 *
	 * @param host the server's host name.
	 * @param port the port number.
	 */
	public SimpleClient(String host, int port) {
		super(host, port);
	}

	@Override
	protected void connectionEstablished() {
		super.connectionEstablished();
		LOGGER.info("Connected to server.");
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		if (msg.getClass().equals(Message.class)) {
			Message myMsg = (Message) msg;
			System.out.println("message received from server");
			switch (myMsg.getMsg()) {
				case Message.recieveAllItems:
					data = myMsg.getObject();
					try {
						App.setRoot("Catalog");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case Message.deleteProductResponse:
					System.out.println("Product has been deleted");
					RefreshCatalog();
					break;
				case Message.addProductResponse:
					System.out.println("Product has been added");
					RefreshCatalog();
					break;
				case Message.SignUp_C:
					String info=myMsg.getInfo_Msg();
					SignUpEvent event=new SignUpEvent(-11);
					if(info.equals("1")) {
						event.setVal(1);
					}
					else if(info.equals("0")){
						event.setVal(0);
					}
					else event.setVal(-1);
					EventBus.getDefault().post(event);
					break;
				case Message.LoggingIn_C:
					String infoLogIn=myMsg.getInfo_Msg();
					LogInEvent eventLogIn=new LogInEvent(-11);
					if(infoLogIn.equals("1")) {
						this.user=(Client)myMsg.getObject();
						eventLogIn.setVal(1);
					}
					else if(infoLogIn.equals("0")){
						eventLogIn.setVal(0);
					}
					else eventLogIn.setVal(-1);
					EventBus.getDefault().post(eventLogIn);
					break;
				case Message.ManagerLogIn_C:
					String infoLogIn_m=myMsg.getInfo_Msg();
					LogInManagersEvent eventLogIn_m=new LogInManagersEvent(-11);
					if(infoLogIn_m.equals("1")) {
						if(myMsg.getObject().getClass()==ChainManager.class)
							chainManager=(ChainManager) myMsg.getObject();
						else storeManager=(StoreManager)myMsg.getObject();
						eventLogIn_m.setVal(1);
						System.out.println(eventLogIn_m.getVal());
					}
					else if(infoLogIn_m.equals("0")){
						eventLogIn_m.setVal(0);
					}
					else eventLogIn_m.setVal(-1);
					EventBus.getDefault().post(eventLogIn_m);
					break;
				case Message.ManagerSignUp_C:
					String info_s=myMsg.getInfo_Msg();
					SignUpManagersEvent event_ms=new SignUpManagersEvent(-11);
					if(info_s.equals("1")) {
						if(myMsg.getObject().getClass()==ChainManager.class)
							chainManager=(ChainManager) myMsg.getObject();
						else storeManager=(StoreManager)myMsg.getObject();
						event_ms.setVal(1);
					}
					else if(info_s.equals("0")){
						event_ms.setVal(0);
					}
					else event_ms.setVal(-1);
					EventBus.getDefault().post(event_ms);
					break;
				case Message.ItemListForC_C:
					data = myMsg.getObject();
					break;
			}
		}
		else{
			System.out.println("ERROR!!!!");
		}

	}

	@Override
	protected void connectionClosed() {
		super.connectionClosed();
	}


	public void sendMessageToServer(Message message) {
		try {
			this.sendToServer(message);
		} catch (IOException e) {
			System.out.println("Lost connection with server.");
		}
	}

	public static SimpleClient getClient() {
		if (client == null) {
			client = new SimpleClient("localhost", 3000);
		}
		return client;
	}

	private void RefreshCatalog(){
		Message new_msg=new Message(Message.getAllItems);
		try {
			SimpleClient.getClient().sendToServer(new_msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("message sent to server to refresh the catalog page");
	}
/*	private static SimpleClient client = null;

	private SimpleClient(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		if (msg.getClass().equals(Warning.class)) {
			EventBus.getDefault().post(new WarningEvent((Warning) msg));
		}

	}
	
*/

}
