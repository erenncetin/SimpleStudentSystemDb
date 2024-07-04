

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class UserInterface extends JFrame {

    private DataBase db;


    // Constructor
    public UserInterface() {
        this.db = new DataBase();
    }
    
    // front ve backendin ayarlandığı kısım
    public void initialize() {

        // General Properties of Frame
        setTitle("Student Register System");
        setSize(600,800);
        setVisible(true);
        setMinimumSize(new Dimension(400,500));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Right side of the frame 
        JPanel rightPanel = new JPanel(new BorderLayout());
        String[] columns = {"ID","Name","Deparment"};
        DefaultTableModel model = new DefaultTableModel(columns,0);
        JTable table = new JTable(model);
        db.select(model);
        
        rightPanel.add(new JScrollPane(table),BorderLayout.CENTER);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));

        // Left side of Frame
        JPanel leftPanel = new JPanel();
        JPanel studentInfoPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createEmptyBorder(200,50,350,100));
        studentInfoPanel.setLayout(new GridLayout(6,1,10,10));
        studentInfoPanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,80));

        JLabel idLabel = new JLabel("StudentId: ");
        JTextField idText = new JTextField();
        
        JLabel nameLabel = new JLabel("StudentName: ");
        JTextField nameText = new JTextField();

        JLabel departmentLabel = new JLabel("Department: ");
        JTextField departmentText = new JTextField();

        studentInfoPanel.add(idLabel);
        studentInfoPanel.add(idText);
        studentInfoPanel.add(nameLabel);
        studentInfoPanel.add(nameText);
        studentInfoPanel.add(departmentLabel);
        studentInfoPanel.add(departmentText);

        leftPanel.add(studentInfoPanel,BorderLayout.NORTH);

        // Buttons in the Left side of Frame
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1,3,10,10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,80));

        JButton insertButton = new JButton("Insert");
        insertButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (db.insert(Integer.valueOf(idText.getText()), nameText.getText(), departmentText.getText()) != 1) {
                        JOptionPane.showMessageDialog(UserInterface.this,"Student Id cannot be duplicate or There is a server error !!","OK",JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        JOptionPane.showMessageDialog(UserInterface.this,"The entered student is added successfully.");
                    }
                } 
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(UserInterface.this, "Please write number for StudentId part  !!","OK",JOptionPane.ERROR_MESSAGE);
                }
                idText.setText("");
                nameText.setText("");
                departmentText.setText("");
                model.setRowCount(0);
                db.select(model);
            }          
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    int checkStudent = db.selectSpecificStudent(Integer.parseInt(idText.getText()));
                    if (db.delete(Integer.parseInt(idText.getText())) != 1) {
                        JOptionPane.showMessageDialog(UserInterface.this,"There is a server error !!","OK",JOptionPane.ERROR_MESSAGE);                        
                    }
                    else {
                        if (checkStudent != 1) {
                            JOptionPane.showMessageDialog(UserInterface.this,"Student Id cannot be found !!","OK",JOptionPane.ERROR_MESSAGE);  
                        }
                        else {
                            JOptionPane.showMessageDialog(UserInterface.this,"The entered student is deleted successfully.");
                        }
                    }                    
                } 
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(UserInterface.this, "Please write number for StudentId part  !!","OK",JOptionPane.ERROR_MESSAGE); 
                }
                idText.setText("");
                nameText.setText("");
                departmentText.setText("");
                model.setRowCount(0);
                db.select(model);
            }            
        });

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int checkStudent = db.selectSpecificStudent(Integer.parseInt(idText.getText()));
                    if (db.update(Integer.valueOf(idText.getText()), nameText.getText(), departmentText.getText()) != 1) {
                        JOptionPane.showMessageDialog(UserInterface.this,"There is a server error !!","OK",JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        if (checkStudent != 1) {
                            JOptionPane.showMessageDialog(UserInterface.this,"Student Id cannot be found !!","OK",JOptionPane.ERROR_MESSAGE);  
                        }
                        else {
                            JOptionPane.showMessageDialog(UserInterface.this,"The entered student is updated successfully.");
                        }
                    }
                } 
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(UserInterface.this, "Please write number for StudentId part  !!","OK",JOptionPane.ERROR_MESSAGE);
                }
                idText.setText("");
                nameText.setText("");
                departmentText.setText("");
                model.setRowCount(0);
                db.select(model);
            } 
            
        });

        buttonPanel.add(insertButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);

        leftPanel.add(buttonPanel,BorderLayout.SOUTH);


        // Split hole frame
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setResizeWeight(0.5); // Divider in the middle dynamically

        add(splitPane);

        validate();
        repaint();


    }
}
