import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class
DigitalLibrarySystem {

    // Shared book data
    static DefaultTableModel model = new DefaultTableModel(
            new String[]{"Book ID","Title","Author","Status"},0);

    public static void main(String[] args) {

        // Sample books
        model.addRow(new Object[]{"101","Java Basics","James Gosling","Available"});
        model.addRow(new Object[]{"102","Python Guide","Guido","Available"});
        model.addRow(new Object[]{"103","C Programming","Dennis Ritchie","Available"});

        new RoleSelection();
    }

    // ROLE SELECTION
    static class RoleSelection extends JFrame {

        RoleSelection(){

            setTitle("Select Role");
            setSize(300,200);
            setLayout(new GridLayout(2,1,10,10));
            setLocationRelativeTo(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);

            JButton admin = new JButton("ADMIN");
            JButton user = new JButton("USER");

            admin.addActionListener(e -> {
                new LoginPage("admin");
                dispose();
            });

            user.addActionListener(e -> {
                new LoginPage("user");
                dispose();
            });

            add(admin);
            add(user);

            setVisible(true);
        }
    }

    // LOGIN PAGE
    static class LoginPage extends JFrame {

        JTextField username;
        JPasswordField password;

        LoginPage(String role){

            setTitle(role.toUpperCase()+" LOGIN");
            setSize(300,180);
            setLayout(new GridLayout(3,2,5,5));
            setLocationRelativeTo(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);

            username = new JTextField();
            password = new JPasswordField();

            JButton login = new JButton("Login");

            login.addActionListener(e -> {

                String u = username.getText();
                String p = new String(password.getPassword());

                if(role.equals("admin") && u.equals("admin") && p.equals("admin")){
                    new AdminPanel();
                    dispose();
                }
                else if(role.equals("user") && u.equals("user") && p.equals("user")){
                    new UserPanel();
                    dispose();
                }
                else{
                    JOptionPane.showMessageDialog(this,"Invalid Login");
                }
            });

            add(new JLabel("Username"));
            add(username);

            add(new JLabel("Password"));
            add(password);

            add(new JLabel());
            add(login);

            setVisible(true);
        }
    }

    // ADMIN PANEL
    static class AdminPanel extends JFrame {

        JTextField id,title,author;
        JTable table;

        AdminPanel(){

            setTitle("Admin Panel");
            setSize(700,400);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);

            id = new JTextField(5);
            title = new JTextField(8);
            author = new JTextField(8);

            JButton add = new JButton("Add");
            JButton delete = new JButton("Delete");
            JButton logout = new JButton("Logout");

            JPanel top = new JPanel();
            top.add(new JLabel("ID")); top.add(id);
            top.add(new JLabel("Title")); top.add(title);
            top.add(new JLabel("Author")); top.add(author);
            top.add(add); top.add(delete); top.add(logout);

            table = new JTable(model);

            add.addActionListener(e -> addBook());
            delete.addActionListener(e -> deleteBook());
            logout.addActionListener(e -> {
                new RoleSelection();
                dispose();
            });

            add(top,BorderLayout.NORTH);
            add(new JScrollPane(table),BorderLayout.CENTER);

            setVisible(true);
        }

        void addBook(){

            if(id.getText().isEmpty() || title.getText().isEmpty() || author.getText().isEmpty()){
                JOptionPane.showMessageDialog(this,"Fill all fields");
                return;
            }

            model.addRow(new Object[]{
                    id.getText(),
                    title.getText(),
                    author.getText(),
                    "Available"
            });

            JOptionPane.showMessageDialog(this,"Book Added");
        }

        void deleteBook(){

            int r = table.getSelectedRow();

            if(r == -1){
                JOptionPane.showMessageDialog(this,"Select Book");
                return;
            }

            model.removeRow(r);

            JOptionPane.showMessageDialog(this,"Book Deleted");
        }
    }

    // USER PANEL
    static class UserPanel extends JFrame {

        JTable table;

        UserPanel(){

            setTitle("User Panel");
            setSize(700,400);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);

            JButton issue = new JButton("Issue Book");
            JButton ret = new JButton("Return Book");
            JButton logout = new JButton("Logout");

            JPanel top = new JPanel();
            top.add(issue);
            top.add(ret);
            top.add(logout);

            table = new JTable(model);

            issue.addActionListener(e -> updateStatus("Issued"));
            ret.addActionListener(e -> updateStatus("Available"));

            logout.addActionListener(e -> {
                new RoleSelection();
                dispose();
            });

            add(top,BorderLayout.NORTH);
            add(new JScrollPane(table),BorderLayout.CENTER);

            setVisible(true);
        }

        void updateStatus(String status){

            int r = table.getSelectedRow();

            if(r == -1){
                JOptionPane.showMessageDialog(this,"Select Book");
                return;
            }

            model.setValueAt(status,r,3);

            JOptionPane.showMessageDialog(this,"Status Updated");
        }
    }
}