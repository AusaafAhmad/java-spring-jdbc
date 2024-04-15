package metal.project;

import java.util.Scanner;
import metal.project.User;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * Hello world!
 *
 */
public class App 
{
	//SPRING JDBC DRIVER and other Static codes
    static String configString = "metal/project/Config.xml";
    
    static ApplicationContext context = new ClassPathXmlApplicationContext(configString);
    
    static JdbcTemplate template  = (JdbcTemplate)context.getBean("template");
    
    static Scanner scanner = new Scanner(System.in);
	
    static String activeuser;

	//Main functions, Calls the Main Menu
    public static void main( String[] args )
    {
    	mainmenu();
    }

	//User Menu, After Successful Login
    public static void usermenu() {
    	boolean back = false;
		while(!back) {
			
			System.out.println("\n\n\n\n=====USER MENU=====");
	     	   System.out.println("1. Show Profile");
	            System.out.println("2. Edit Bio");
	            System.out.println("3. Delete Account");
	            System.out.println("4. Back");

	            System.out.print("Enter your choice: ");
	            String ch = scanner.nextLine();
	            int choice = Integer.parseInt(ch);
	            
	            switch (choice) {
				case 1:
					User val = getUser();
					val.display();
					break;
				case 2:
					System.out.println("Enter New Bio: ");
					String new_bio =  scanner.nextLine();
					updateUser(new_bio);
					break;
				case 3:
					deleteUser();
					back = true;
					break;
				case 4:
					back = true;
					break;

				default:
					System.out.println("Option Not Found");
					break;
				}
		}
	}

	//Main Menu, Gets Called in Main function at Start
    public static void mainmenu() {
    	while (true) {
     	   System.out.println("\n\n\n\n=====MENU=====");
     	   System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Quit");

            System.out.print("Enter your choice: ");
            String ch = scanner.nextLine();
            int choice = Integer.parseInt(ch);
            
            switch (choice) {
 		case 1:
 			System.out.print("Enter username: ");
 	        String username = scanner.nextLine();
 	        System.out.print("Enter Password: ");
 	        String password = scanner.nextLine();

			// Password Check
 	        String retrivedPassword = getPassword(username);
 	        if(retrivedPassword==null) {
 	        	System.out.println("User or password incorrect!!!");
 	        	break;
 	        }
 	        if(password.equals(retrivedPassword))
 	        {
 	        	activeuser = username;
 	        	usermenu();
 	        	break;
 	        }
 	        else 
				System.out.println("User or password incorrect!!!");
 			break;
 		case 2:
 			System.out.print("Enter username: ");
 	        String new_user = scanner.nextLine();
 	        System.out.print("Enter Password: ");
 	        String new_pass = scanner.nextLine();
 	        System.out.print("Enter Name: ");
	        String new_name = scanner.nextLine();
	        System.out.print("Enter Bio: ");
 	        String new_bio = scanner.nextLine();
 	        
 	        setUser(new_user, new_pass, new_name, new_bio);
 			break;
 		case 3:
 			scanner.close();
 			((ClassPathXmlApplicationContext)context).close();
 			System.exit(0);
 			break;
 		
 		default:
 			System.out.println("Option Not Found");
 			break;
 		}
 	}
     	
    }

	// Register Function
    public static void setUser(String user,String pass,String name,String bio) {
    	String query = "insert into user values(?,?,?,?)";
    	int val =  template.update(query,user,pass,name,bio);
    	if(val==1) {
    		System.out.println("User Added Successfully");
    	}
	}

	// Login Function, For Password Checking
    public static String getPassword(String username) {
		String query = "select password from user where username=?";
		String password = (String) template.queryForObject(
	            query, new Object[] { username }, String.class);
		
		return password;
    	
	}

	// Delete User Function
    public static void deleteUser() {
    	String query = "delete from user where username=?";
    	int val =  template.update(query,activeuser);
    	if(val==1) {
    		System.out.println("User Deleted Successfully");
    	}
	}

	//Update Bio Function
    public static void updateUser(String bio) {
    	String query = "update user set bio=? where username=?";
    	int val =  template.update(query,bio,activeuser);
    	if(val==1) {
    		System.out.println("Bio Updated Successfully");
    	}
	}

	// Show user Details Function
    public static User getUser() {
    	String query = "select * from user where username=?";
    	return template.queryForObject(query, new Object[]{activeuser}, (rs, rowNum) ->
        new User(
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("bio")
        ));
	}
}
