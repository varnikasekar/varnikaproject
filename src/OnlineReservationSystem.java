import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class OnlineReservationSystem {

    JFrame frame;
    JTextField userField;
    JPasswordField passField;
    String loggedUser="";

    JTextField passengerField, trainField, fromField, toField, dateField;
    JTextField pnrField;

        // STORE USERS
    HashMap<String,String> users = new HashMap<>();

        // STORE RESERVATIONS
    ArrayList<Reservation> reservations = new ArrayList<>();

        public OnlineReservationSystem(){

            frame = new JFrame("Online Reservation System");
            frame.setSize(600,500);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            loginPage();
            frame.setVisible(true);
        }

        // LOGIN PAGE
        void loginPage(){

            frame.getContentPane().removeAll();

            JPanel panel = new JPanel(null);

            JLabel title = new JLabel("Login Page");
            title.setBounds(250,30,200,30);

            JLabel userLabel = new JLabel("Username:");
            userLabel.setBounds(150,120,100,30);

            userField = new JTextField();
            userField.setBounds(250,120,180,30);

            JLabel passLabel = new JLabel("Password:");
            passLabel.setBounds(150,170,100,30);

            passField = new JPasswordField();
            passField.setBounds(250,170,180,30);

            JButton loginBtn = new JButton("Login");
            loginBtn.setBounds(250,240,100,35);

            JButton signupBtn = new JButton("Sign Up");
            signupBtn.setBounds(250,290,100,35);

            loginBtn.addActionListener(e -> loginUser());
            signupBtn.addActionListener(e -> signupUser());

            panel.add(title);
            panel.add(userLabel);
            panel.add(userField);
            panel.add(passLabel);
            panel.add(passField);
            panel.add(loginBtn);
            panel.add(signupBtn);

            frame.add(panel);
            frame.revalidate();
            frame.repaint();
        }

        // SIGNUP
        void signupUser(){

            String username = userField.getText();
            String password = String.valueOf(passField.getPassword());

            if(users.containsKey(username)){
                JOptionPane.showMessageDialog(frame,"Username already exists");
            }else{
                users.put(username,password);
                JOptionPane.showMessageDialog(frame,"Signup successful!");
            }
        }

        // LOGIN
        void loginUser(){

            String username = userField.getText();
            String password = String.valueOf(passField.getPassword());

            if(users.containsKey(username) && users.get(username).equals(password)){

                loggedUser = username;

                JOptionPane.showMessageDialog(frame,"Welcome "+loggedUser);

                showMainMenu();

            }else{
                JOptionPane.showMessageDialog(frame,"Invalid Login");
            }
        }

        // MAIN MENU
        void showMainMenu(){

            frame.getContentPane().removeAll();

            JPanel panel = new JPanel(null);

            JLabel welcome = new JLabel("Welcome "+loggedUser);
            welcome.setBounds(220,50,200,30);

            JButton reserveBtn = new JButton("Reservation Form");
            reserveBtn.setBounds(200,150,200,50);

            JButton cancelBtn = new JButton("Cancellation Form");
            cancelBtn.setBounds(200,230,200,50);

            reserveBtn.addActionListener(e -> showReservationForm());
            cancelBtn.addActionListener(e -> showCancellationForm());

            panel.add(welcome);
            panel.add(reserveBtn);
            panel.add(cancelBtn);

            frame.add(panel);
            frame.revalidate();
            frame.repaint();
        }

        // RESERVATION FORM
        void showReservationForm(){

            frame.getContentPane().removeAll();

            JPanel panel = new JPanel(null);

            JLabel title = new JLabel("Reservation Form");
            title.setBounds(220,30,200,30);
            panel.add(title);

            passengerField = new JTextField();
            trainField = new JTextField();
            fromField = new JTextField();
            toField = new JTextField();
            dateField = new JTextField();

            panel.add(new JLabel("Passenger Name")).setBounds(100,100,120,30);
            passengerField.setBounds(250,100,200,30);
            panel.add(passengerField);

            panel.add(new JLabel("Train Name")).setBounds(100,150,120,30);
            trainField.setBounds(250,150,200,30);
            panel.add(trainField);

            panel.add(new JLabel("From Station")).setBounds(100,200,120,30);
            fromField.setBounds(250,200,200,30);
            panel.add(fromField);

            panel.add(new JLabel("To Station")).setBounds(100,250,120,30);
            toField.setBounds(250,250,200,30);
            panel.add(toField);

            panel.add(new JLabel("Journey Date")).setBounds(100,300,120,30);
            dateField.setBounds(250,300,200,30);
            panel.add(dateField);

            JButton submitBtn = new JButton("Reserve Ticket");
            submitBtn.setBounds(300,360,150,40);

            JButton backBtn = new JButton("Back");
            backBtn.setBounds(100,360,100,40);

            submitBtn.addActionListener(e -> reserveTicket());

            // BACK BUTTON
            backBtn.addActionListener(e -> showMainMenu());

            panel.add(submitBtn);
            panel.add(backBtn);

            frame.add(panel);
            frame.revalidate();
            frame.repaint();
        }

        // PNR GENERATE
        int generatePNR(){

            Random r = new Random();
            return 100000 + r.nextInt(900000);
        }

        // RESERVE
        void reserveTicket(){

            int pnr = generatePNR();

            Reservation res = new Reservation(
                    pnr,
                    passengerField.getText(),
                    trainField.getText(),
                    fromField.getText(),
                    toField.getText(),
                    dateField.getText()
            );

            reservations.add(res);

            JOptionPane.showMessageDialog(frame,
                    "Ticket Reserved!\nPNR: "+pnr);
        }

        // CANCELLATION FORM
        void showCancellationForm(){

            frame.getContentPane().removeAll();

            JPanel panel = new JPanel(null);

            panel.add(new JLabel("Enter PNR")).setBounds(150,150,100,30);

            pnrField = new JTextField();
            pnrField.setBounds(250,150,200,30);
            panel.add(pnrField);

            JButton cancelBtn = new JButton("Cancel Ticket");
            cancelBtn.setBounds(220,220,160,40);

            cancelBtn.addActionListener(e -> cancelTicket());

            panel.add(cancelBtn);

            frame.add(panel);
            frame.revalidate();
            frame.repaint();
        }

        // CANCEL
        void cancelTicket(){

            int pnr = Integer.parseInt(pnrField.getText());

            for(Reservation r : reservations){

                if(r.pnr == pnr){

                    reservations.remove(r);

                    JOptionPane.showMessageDialog(frame,"Ticket Cancelled");

                    return;
                }
            }

            JOptionPane.showMessageDialog(frame,"Invalid PNR");
        }

        // RESERVATION CLASS
        class Reservation{

            int pnr;
            String passenger, train, from, to, date;

            Reservation(int pnr,String passenger,String train,String from,String to,String date){

                this.pnr = pnr;
                this.passenger = passenger;
                this.train = train;
                this.from = from;
                this.to = to;
                this.date = date;
            }
        }

        public static void main(String[] args){

            new OnlineReservationSystem();
        }
    }