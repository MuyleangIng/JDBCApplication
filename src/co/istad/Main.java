package co.istad;

import co.istad.model.Topic;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private  static jdbcImpl jdbc = new jdbcImpl();
    private static Scanner input= new Scanner(System.in);
    public static void main(String[] args) throws SQLException {
        int option;
        do{
            System.out.println("1. Insert Topic");
            System.out.println("2. Update Topic");
            System.out.println("3. Select Topic by ID");
            System.out.println("4. Select Topic by Name");
            System.out.println("5. Delete by ID");
            System.out.println("6. Exit");
            System.out.print("Choose option 1-6: ");
            option = input.nextInt();
            switch (option){
                case 1:
                    System.out.println("============INSERT TOPIC============");
                    Topic topic = new Topic();
                    System.out.print("Enter name: ");
                    input.nextLine();
                    topic.setName(input.nextLine());
                    System.out.print("Enter description: ");
                    topic.setDescription(input.nextLine());
                    topic.setStatus(true);
                    insertTopic(topic);
                    System.out.println("Congratulation.. You are Insert successfully.");
                    break;
                case 2:
                    System.out.println("============UPDATE TOPIC============");
                    Topic topicToUpdate = new Topic();
                    System.out.print("Enter id to update: ");
                    topicToUpdate.setId(input.nextInt());
                    System.out.print("Enter new name: ");
                    input.nextLine();
                    topicToUpdate.setName(input.nextLine());
                    System.out.print("Enter new description: ");
                    topicToUpdate.setDescription(input.nextLine());
                    topicToUpdate.setStatus(true);
                    updateTopic(topicToUpdate);
                    System.out.println("Congratulation.. You are update successfully.");
                    break;

                case 3:
                    System.out.println("============Select Topic by ID============");
                    Topic idToselect = new Topic();
                    System.out.print("Enter id to update: ");
                    idToselect.setId(input.nextInt());
                    selectTopicById(idToselect.getId());
                    break;
                case 4:
                    System.out.println("============Select Topic by Name============");
                    Topic nameToSelect = new Topic();
                    System.out.print("Enter new name: ");
                    input.nextLine();
                    nameToSelect.setName(input.nextLine());
                    selectTopicByName(nameToSelect.getName());
                    break;
                case 5:
                    System.out.println("============DELETE BY ID============");
                    Topic topicToDelete = new Topic();
                    System.out.print("Enter id to delete: ");
                    topicToDelete.setId(input.nextInt());
                    deleteTopic(topicToDelete.getId());
                    System.out.println("Congratulation.. You are deleted successfully.");
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Something when wrong!!!");
            }
        }while(option!=6);
        selectTopic();
    }
    private static void insertTopic(Topic topic){
        try(Connection con = jdbc.dataSource().getConnection()){
            String insertSql = "INSERT INTO topics(name,description,status) VALUES (?,?,?)";
            PreparedStatement statement =con.prepareStatement(insertSql);
            statement.setString(1, topic.getName());
            statement.setString(2, topic.getDescription());
            statement.setBoolean(3,topic.getStatus());
            int count = statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static void updateTopic(Topic topic){
        try(Connection con = jdbc.dataSource().getConnection()){
            String updateSql = "UPDATE topics SET name = ?, description = ?, status = ? WHERE id = ?";
            PreparedStatement statement = con.prepareStatement(updateSql);
            statement.setString(1, topic.getName());
            statement.setString(2, topic.getDescription());
            statement.setBoolean(3,topic.getStatus());
            statement.setInt(4, topic.getId());
            int count = statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private static void selectTopic(){
        try (Connection con = jdbc.dataSource().getConnection() )
        {
            System.out.println(con.getSchema());
            String selectSql = "SELECT * FROM  topics";
            PreparedStatement statement= con.prepareStatement(selectSql);
            ResultSet resultSet = statement.executeQuery();
            List<Topic> topics  = new ArrayList<>();
            while (resultSet.next()){
                Integer id=resultSet.getInt("id");
                String name= resultSet.getString("name");
                String description = resultSet.getString("descriptions");
                Boolean status = resultSet.getBoolean("status");
                topics.add(new Topic(id,name,description,status));
            }
            for (Topic topic:topics) {
                System.out.println(topic);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    private static void selectTopicById(int id){
        try (Connection con = jdbc.dataSource().getConnection() )
        {
            String selectSql = "SELECT * FROM  topics WHERE id = ?";
            PreparedStatement statement= con.prepareStatement(selectSql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                String name = resultSet.getString("name");
                String description = resultSet.getString("descriptions");
                Boolean status = resultSet.getBoolean("status");
                System.out.println("Topic with id " + id + ": name=" + name + ", description=" + description + ", status=" + status);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    private static void selectTopicByName(String name){
        try (Connection con = jdbc.dataSource().getConnection() )
        {
            String selectSql = "SELECT * FROM  topics WHERE name = ?";
            PreparedStatement statement= con.prepareStatement(selectSql);
            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                Integer id = resultSet.getInt("id");
                String description = resultSet.getString("descriptions");
                Boolean status = resultSet.getBoolean("status");
                System.out.println("Topic with name " + name + ": id=" + id + ", description=" + description + ", status=" + status);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteTopic(int id){
        try(Connection con = jdbc.dataSource().getConnection()){
            String deleteSql = "DELETE FROM topics WHERE id = ?";
            PreparedStatement statement = con.prepareStatement(deleteSql);
            statement.setInt(1, id);
            int count = statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
