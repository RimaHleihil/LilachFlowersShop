package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

import java.io.IOException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/*public class SimpleServer extends AbstractServer {

	public SimpleServer(int port) {
		super(port);
		
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		String msgString = msg.toString();
		if (msgString.startsWith("#warning")) {
			Warning warning = new Warning("Warning from server!");
			try {
				client.sendToClient(warning);
				System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}*/


public class SimpleServer  extends AbstractServer {
	SessionFactory sessionFactory = getSessionFactory();
	private static Session session;

/*
	public SimpleServer(int port) {
		super(port);

	}
*/


	/**
	 * Constructs a new server.
	 *
	 * @param port the port number on which to listen.
	 */
	public SimpleServer(int port) {
		super(port);
		SessionFactory sessionFactory = getSessionFactory();
		session = sessionFactory.openSession();
		session.beginTransaction();
		intitializeDataBase();
		session.getTransaction().commit();
	}


	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) throws IOException {
		Message message = (Message) msg;
		System.out.println("Message received");
		switch (message.getMsg()) {
			case Message.getAllItems:
				List<Item> items = getAllItems();
				Message response = new Message(Message.recieveAllItems, items);
				try{
					client.sendToClient(response);

				} catch(IOException e) {
					e.printStackTrace();
				}
//                session.close(); // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ DELETE WHEN ERROR @@@@@@@@@@@@@@@@@
				break;
			case Message.updateItem:
				session = sessionFactory.openSession();
				session.beginTransaction();
				Item item = (Item)message.getObject();
				updateItem(item);
				session.getTransaction().commit();
				items = getAllItems();
				Message response1 = new Message(Message.recieveAllItems, items);
				client.sendToClient(response1);
//                session.close();
				break;
			case Message.deleteProduct:
				Message delResponse = new Message(Message.deleteProductResponse);
				try{
					session = sessionFactory.openSession();
					session.beginTransaction();
					Item m = (Item) message.getObject();
					m.setImgURL(null);
					session.delete(m);
					session.flush();
					session.getTransaction().commit(); // Save everything
					client.sendToClient(delResponse);
//                    session.close();
				} catch (Exception ex) {
					if (session != null) {
						session.getTransaction().rollback();
					}
					System.err.println("An error occurred, changes have been rolled back.");

					ex.printStackTrace();

				}
				break;
			case Message.addProduct:
				Message addResponse = new Message(Message.addProductResponse);
				try {
					session = sessionFactory.openSession();
					session.beginTransaction();
					Item m = (Item) message.getObject();
					session.save(m);
					session.flush();
					session.getTransaction().commit();
					client.sendToClient(addResponse);
				} catch (Exception ex) {
					if (session != null) {
						session.getTransaction().rollback();
					}
					System.err.println("An error occurred, changes have been rolled back.");

					ex.printStackTrace();

				}
//                session.close();//When triggered back it will cause problem in saving the item properly in the table
				break;
			case Message.LoggingIn_S:
				try{
					SessionFactory sessionFactory = getSessionFactory();
					session = sessionFactory.openSession();
					session.beginTransaction();
					client.sendToClient(LogIn((String[])message.getObject()));
				}
				catch (Exception ex) {
					if (session != null) {
						session.getTransaction().rollback();
					}
					System.err.println("An error occurred, changes have been rolled back.");
					ex.printStackTrace();
				}
				break;
			case Message.SignUp_S:
				try {
					SessionFactory sessionFactory = getSessionFactory();
					session = sessionFactory.openSession();
					session.beginTransaction();
					Message resm = SignUp((String[]) message.getObject());
					client.sendToClient(resm);
				}
				catch (Exception ex) {
					if (session != null) {
						session.getTransaction().rollback();
					}
					System.err.println("An error occurred, changes have been rolled back.");
					ex.printStackTrace();
				}
				break;
            /*case Message.Add2Basket_S:// adding an item for the item list for the wanted client
                Message Add2BRes = new Message(Message.Add2Basket_C);
                try {
                    session = sessionFactory.openSession();
                    session.beginTransaction();
                    Item m = (Item) message.getObject();
                    Client c=GetClientById(message.getInfo_Msg());//getting the client by the id we sent
                    if (c!=null)
                    {
                        c.AddItem(m);// adding the item
                        session.save(c);
                        session.getTransaction().commit();
                        session.close();

                        System.err.println("success "+m.getName());
                    }
                    client.sendToClient(Add2BRes);
                } catch (Exception ex) {
                    if (session != null) {
                        session.getTransaction().rollback();
                    }
                    System.err.println("An error occurred, changes have been rolled back.");
                    ex.printStackTrace();
                }*/
			//session.close();
            /*case Message.ItemListForC_S:
                Client c=(Client) message.getObject();
                List<Item> itemsL=Collections.synchronizedList(c.getItemList());
                Message intemsCres = new Message(Message.ItemListForC_C, itemsL);
                try{
                    client.sendToClient(intemsCres);

                } catch(IOException e) {
                    e.printStackTrace();
                }
               // session.close();
                break;*/
            /*case Message.SaveOrder_S:
                try {
                    SessionFactory sessionFactory = getSessionFactory();
                    session = sessionFactory.openSession();
                    session.beginTransaction();
                    SaveNew((Order_1)message.getObject());
                }
                catch (Exception ex) {
                    if (session != null) {
                        session.getTransaction().rollback();
                    }
                    System.err.println("An error occurred, changes have been rolled back.");
                    ex.printStackTrace();
                }
                break;*/
			default:
				throw new IllegalStateException("Unexpected value: " + message.getMsg());
		}
	}

	private static Client GetClientById(String id)
	{
		List<Client> clientList=getAllClients();
		for(Client c:clientList)
		{
			if(id.equals(Integer.toString(c.getId())))
				return c;
		}
		return null;
	}
	private static Message LogIn (String [] data) {
		System.out.println("\n\n\n\n LOG in function \n\n\n\n");
		Message resMSG=new Message(Message.LoggingIn_C,"-11");
		List<Client> clients=getAllClients();
		if(clients.isEmpty())
		{
			//Something is wrong
			System.out.println("\n\n\n\n the data is empty :) \n\n\n\n");

			return resMSG;
		}
		else {
			for (Client c:clients)
			{
				if(c.getUsername().equals(data[0])&& c.getPassword().equals(data[1]))
				{
					System.out.println("found the user!!");
					resMSG.setObject(c);
					resMSG.setInfo_Msg("1");//found the user
					return resMSG;
				}
			}
			//return resMSG;
		}
		System.out.println("After the for");
		System.out.println("Something Wrong Can Not find The User");
		resMSG.setInfo_Msg("0");//does not exist!
		return resMSG;
	}

	private static Message SignUp (String [] data) {
		//System.out.println("\n\n\n\n sign in function \n\n\n\n");
		Message resMSG=new Message(Message.SignUp_C,"-11");
		List<Client> clients=getAllClients();
		if(clients.isEmpty())
		{
			Client newClient=new Client(data[0],data[1],data[2]);
			SaveNew(newClient);
			resMSG.setObject(newClient);
			resMSG.setInfo_Msg("1");//sign up successfully
			//System.out.println("sign up successfully" + resMSG.getInfo_Msg());
			return resMSG;
		}
		else {
			for (Client c:clients)
			{
				if(c.getUsername().equals(data[1])||c.getID().equals(data[0])||c.getPassword().equals(data[2]))
				{
					//System.out.println("already exist!!");
					resMSG.setInfo_Msg("0");//already exist!!
					return resMSG;
				}
			}
			System.out.println("After the for");
			Client newClient=new Client(data[0],data[1],data[2]);
			SaveNew(newClient);
			resMSG.setObject(newClient);
			resMSG.setInfo_Msg("1");//sign up successfully
			//return resMSG;
		}
		return resMSG;
	}
	public static <T> void SaveNew(T type)
	{
		try {
			session.save(type);
			session.flush();
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			if(session != null)
			{
				session.getTransaction().rollback();
			}
			e.printStackTrace();
		}

	}
	private void sendRefreshcatlogevent() {
		System.out.println("test test new function");
		try {
			Message msg = new Message(Message.getAllItems);
			this.sendToAllClients(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static List<Item> getAllItems(){
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Item> query = builder.createQuery(Item.class);
		query.from(Item.class);
		List<Item> data = session.createQuery(query).getResultList();
		return data;
	}
	/*    public static <T> List<T> getAll(Class<T> object) {
           // System.out.println("get all");
            CriteriaBuilder builder = session.getCriteriaBuilder();
           // System.out.println("get all 1");
            CriteriaQuery<T> criteriaQuery = builder.createQuery();
           // System.out.println("get all 2");
            Root<T> rootEntry = criteriaQuery.from(object);
           // System.out.println("get all 3");
            CriteriaQuery<T> allCriteriaQuery = criteriaQuery.select(rootEntry);
            //System.out.println("get all 4");
            TypedQuery<T> allQuery = session.createQuery(allCriteriaQuery);
           // System.out.println("get all 5");
            return allQuery.getResultList();
        }*/
	public static  List<Client> getAllClients(){
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Client> query = builder.createQuery(Client.class);
		Root<Client> root = query.from(Client.class);
		query.select(root);
		List<Client> data = session.createQuery(query).getResultList();
		return data;
	}
	public static  List<User> getAllUsers(){
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<User> query = builder.createQuery(User.class);
		query.from(User.class);
		List<User> data = session.createQuery(query).getResultList();
		return data;
	}
	public static void updateItem(Item item) {
		try {
			System.out.println(item.getPrice());
			item.setPrice(item.getPrice());
			session.update(item);
			session.flush();
			System.out.println("FLush");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}


	}
	static void intitializeDataBase(){

       Item item1 = new Item("Festive Gladioli bouquet",349,"Bouquet","https://florabloom.co.il/wp-content/uploads/2020/07/MG_1293-2-Edit_websize-1.jpg");
        session.save(item1);
        session.flush();
        Item item2= new Item("Miss Lizzie Bouquet",318,"Bouquet","https://florabloom.co.il/wp-content/uploads/2021/11/MG_1123-2-Edit_websize-1.jpg");
        session.save(item2);
        session.flush();
        Item item3= new Item("Red Sun Bouquet",268,"Bouquet","https://florabloom.co.il/wp-content/uploads/2020/08/MG_1205-2-Edit_websize.jpg");
        session.save(item3);
        session.flush();
        Item item4=new Item("Pure White Bouquet",295,"Bouquet","https://florabloom.co.il/wp-content/uploads/2021/08/MG_1238-2-Edit_websize-1.jpg");
        session.save(item4);
        session.flush();
        Item item5=new Item("Lady Lillia Bouquet",315,"Bouquet","https://florabloom.co.il/wp-content/uploads/2021/11/MG_1167-2-Edit_websize-1.jpg");
        session.save(item5);
        session.flush();
        Item item6=new Item("Red Roses",213,"Bouquet","https://florabloom.co.il/wp-content/uploads/2020/07/MG_4288-Edit_websize-1-1.jpg");
        session.save(item6);
        session.flush();
        Item item7=new Item("Autumn Bouquet",347,"Bouquet","https://florabloom.co.il/wp-content/uploads/2021/11/MG_1114-2-Edit_websize-3.jpg");
        session.save(item7);
        session.flush();
        Item item8=new Item("Romantic Chrysanthemum Bouquet",299,"Bouquet","https://florabloom.co.il/wp-content/uploads/2021/11/MG_4327-1_websize.jpg");
        session.save(item8);
        session.flush();
        Item item9=new Item("Sunflowers Bouquet",255,"Bouquet","https://florabloom.co.il/wp-content/uploads/2021/11/MG_4308-1_websize.jpg");
        session.save(item9);
        session.flush();
        Item item10=new Item("Lizi Boxt",279,"Bouquet","https://florabloom.co.il/wp-content/uploads/2020/08/MG_1095-2-Edit_websize-1.jpg");
        session.save(item10);
        session.flush();
        Item item11=new Item("Calthea",63,"tree","https://florabloom.co.il/wp-content/uploads/2020/11/MG_1248-2-Edit_websize.jpg");
        session.save(item11);
        session.flush();
        Item item12=new Item("Fiddle-leaf fig",60,"tree","https://florabloom.co.il/wp-content/uploads/2020/11/WhatsApp-Image-2020-11-05-at-10.59.56-1.jpeg");
        session.save(item12);
        session.flush();
        Item item13=new Item("Red Antorium",79,"tree","https://florabloom.co.il/wp-content/uploads/2020/07/WhatsApp-Image-2020-11-05-at-10.24.25.jpeg");
        session.save(item13);
        session.flush();
        Item item14=new Item("Caltheaa",65,"tree","https://florabloom.co.il/wp-content/uploads/2021/08/B8A1FC70-DF9C-4734-AA2A-A3594AB96C98.jpg");
        session.save(item14);
        session.flush();
/*        Client c=new Client("2128965","yusra","pass7!");
        session.save(c);
        session.flush();
        System.out.println("Finished initialize");*/

	}
	public static Session getSession() {
		return session;
	}

	private static SessionFactory getSessionFactory() throws HibernateException {
		Configuration configuration = new Configuration();
		// Add ALL of your entities here. You can also try adding a whole package.
		configuration.addAnnotatedClass(Item.class);
		configuration.addAnnotatedClass(User.class);
		configuration.addAnnotatedClass(Client.class);
		configuration.addAnnotatedClass(Message.class);
		configuration.addAnnotatedClass(PersonalDesign.class);
		// configuration.addAnnotatedClass(Manager.class);
		configuration.addAnnotatedClass(Order.class);
		//......a problem in biulding the table.
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties())
				.build();

		return configuration.buildSessionFactory(serviceRegistry);
	}


}

