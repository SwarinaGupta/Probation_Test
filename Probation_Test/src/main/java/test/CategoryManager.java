package test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryManager 
{
	private Connection conn;
	
	//	Calling connection object for maintaining the connection with database
    public CategoryManager(Connection conn) {
        this.conn = conn;
    }
    
    //Function for adding new Category to table
    public void addNewCategory(Category category) {
    	try {
    		//Insert query to insert the data in Database
            String sql = "INSERT INTO categories (name) VALUES (?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, category.getName());
            preparedStatement.executeUpdate();
         } catch (SQLException e) {
        	 System.err.println("Error adding category: " + e.getMessage());
        }
    }
    
    //Function for fetching all the data of categories from database
    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM categories";
        try (
        	PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Category category = new Category(id, name);
                categories.add(category);
            }
        } catch (SQLException e) {
        	System.err.println("Error fetching categories: " + e.getMessage());
        }
        return categories;
    }
}
