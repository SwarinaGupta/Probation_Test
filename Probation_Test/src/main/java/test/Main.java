package test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
public class Main
{
	public static void main(String[] args) {
		//Data foe connecting to database
        String Url = "jdbc:postgresql://localhost:5433/Probation_Test";
        String User = "postgres";
        String Password = "root";

            try{
                Class.forName("org.postgresql.Driver");
                //Used for building the connection to database
                Connection connection=DriverManager.getConnection(Url, User, Password);
                if(connection!=null)
                    System.out.println("Connection OK");
                else
                    System.out.println("Connection Failed");
                
                //Initializing the objects of all class
                CategoryManager categoryManager = new CategoryManager(connection);
                ItemManager itemManager = new ItemManager(connection);
                Statistics stats = new Statistics(connection);
                
                while (true) {
                	//Providing options for user
                    System.out.println("1. Add a new category");
                    System.out.println("2. List all categories");
                    System.out.println("3. Add a new item associated with category");
                    System.out.println("4. List all items in a specific category");
                    System.out.println("5. Display the statistics");
                    System.out.println("6. Exit");
                    
                    Scanner scanner = new Scanner(System.in);
    	            int choice = scanner.nextInt();
    	            switch (choice) 
    	            {
    	            
    	            //For adding a new category
    	            case 1:
            	        System.out.print("Enter Category name: ");
            	        String categoryName = scanner.next();
                        Category newCategory = new Category(0, categoryName);
                        categoryManager.addNewCategory(newCategory);
                        System.out.println("Category added successfully.");
                        break;
                        
                    //for displaying the list of all data in categories 
    	            case 2:
                        List<Category> categories = categoryManager.getCategories();
                        for (Category category : categories) {
                            System.out.println(category.getId() + " - " + category.getName());
                        }
                        break;
                        
                   //for adding a new data associating with category id
    	            case 3:
                        System.out.print("Enter the item name: ");
                        String itemName = scanner.next();
                        System.out.print("Enter the item description: ");
                        String description = scanner.next();
                        System.out.print("Enter the associated category ID: ");
                        int categoryId = scanner.nextInt();
                        Item newItem = new Item(0, itemName, description, categoryId);
                        itemManager.addItemWithCategory(newItem);
                        System.out.println("Item added successfully.");
                        break;
                        
                    //Getting the list of items based on their category id
    	            case 4:
                        System.out.print("Enter the category ID to list items: ");
                        int CategoryId = scanner.nextInt();
                        List<Item> itemsInCategory = itemManager.getItems(CategoryId);
                        for (Item item : itemsInCategory) {
                            System.out.println(item.getId() + "," + item.getName() + "," + item.getDescription());
                        }
                        break;
                        
                    //for displaying the statistics (to get the total number of items and number of items in each category
                    case 5:
                        stats.displayStatistics();
                        break;
                        
                    case 6:
                        connection.close();
                        System.exit(0);
                        
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }    
            }               
            }
            catch(Exception e)
                {
            	System.err.println("Database connection error: " + e.getMessage());
                }       
}
}