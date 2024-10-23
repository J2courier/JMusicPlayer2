/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package jmusicplayer;

/**
 *
 * @author ADMIN
 */
//task 1: create a grid layout
//task 2: create a program where we add or fetch a song
//task 3: design
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class JMusicPlayer extends JFrame {

    // Helper method to create a JPanel with custom properties
    public static JPanel createPanel(Color bgColor, int width, int height, LayoutManager layout) {
        JPanel panel = new JPanel();
        panel.setBackground(bgColor);
        panel.setLayout(layout);
        panel.setPreferredSize(new Dimension(width, height));
        return panel;
    }

    // Helper method to create a JLabel with custom properties
    public static JLabel createLabel(String text, Color color, int width, int height, Font font) {
        JLabel label = new JLabel();
        label.setText(text);
        label.setForeground(color);
        label.setPreferredSize(new Dimension(width, height));
        label.setFont(font);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBorder(BorderFactory.createLineBorder(new Color(0x2b3f61), 2));
        return label;
    }
    
    public static JButton createBtn (String btn, int width, int height, Color btnColor, Color txtColor, boolean focus){
        JButton button = new JButton(btn);
        button.setPreferredSize(new Dimension(width, height));
        button.setBackground(btnColor);
        button.setForeground(txtColor);
        button.setFocusable(focus);
        return button;
    }

    public JMusicPlayer() {
        ImageIcon musicIcon = new ImageIcon("C:\\Users\\ADMIN\\Documents\\NetBeansProjects\\JMusicPlayer\\src\\jmusicplayer\\uta.jpg");
        setIconImage(musicIcon.getImage());

        JPanel heading = createPanel(new Color(0x152142), 100, 70, new BorderLayout());

        JLabel library = createLabel("Library", new Color(0xFFFFFF), 100, 700, new Font("Cascade Code", Font.BOLD, 18));
        library.setBorder(null);
        heading.add(library, BorderLayout.WEST);

        JPanel body = createPanel(new Color(0x171d2e), 500, 700, new FlowLayout());
        JPanel listDisplay = createPanel(new Color(0x171d2e), 500, 300, new FlowLayout());
        JPanel footerInBody = createPanel(new Color(0x171d2e), 500, 30, new BorderLayout());
//      JPanel rightPanel = createPanel(new Color(0x003891), 80, 700, new FlowLayout());
        JPanel footer = createPanel(new Color(0x152142), 100, 60, new FlowLayout());

        
        JButton addButton = createBtn("Open File", 150, 40, new Color(0x152142), new Color(0xFFFFFF), false);
        JButton removeButton = createBtn("Remove", 150, 40, new Color(0x152142), new Color(0xFFFFFF), false);
        JButton selectButton = createBtn("Select", 150, 40, new Color(0x152142), new Color(0xFFFFFF), false);
        footer.add(addButton);
        footer.add(removeButton);
        footer.add(selectButton);
        body.add(listDisplay);
        body.add(footerInBody, BorderLayout.SOUTH);
        
        
    
        addButton.addActionListener(new ActionListener() {
            int labelCount = 1; 

            @Override
            public void actionPerformed(ActionEvent e) {
        
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null); 

                if (result == JFileChooser.APPROVE_OPTION) {
               
                    File selectedFile = fileChooser.getSelectedFile();
                    JLabel newLabel = createLabel(selectedFile.getName(), new Color(0xFFFFFF), 400, 25, new Font("Cascade Code", Font.PLAIN, 12));
                    listDisplay.add(newLabel);
                    listDisplay.revalidate();
                    listDisplay.repaint();
                }
            }
        });

        JPanel category = createPanel(new Color(0x152142), 100, 25, new GridLayout(0, 3));
        heading.add(category, BorderLayout.SOUTH);

        JLabel defCategory = createLabel("Default", new Color(0xFFFFFF), 100, 25, new Font("Cascade Code", Font.PLAIN, 12));
        JLabel favCategory = createLabel("Favorite", new Color(0xFFFFFF), 100, 25, new Font("Cascade Code", Font.PLAIN, 12));
        JLabel addCategory = createLabel("Add Category +", new Color(0xFFFFFF), 100, 25, new Font("Cascade Code", Font.PLAIN, 12));
        category.add(defCategory);
        category.add(favCategory);
        category.add(addCategory);

        setTitle("Uta");
        setSize(500, 700);
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(500, 700));
        getContentPane().setBackground(new Color(0x171d2e));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(heading, BorderLayout.NORTH);
        add(body, BorderLayout.CENTER);
//        add(rightPanel, BorderLayout.EAST);
        add(footer, BorderLayout.SOUTH); // Add the footer with the button
    }

    public static void main(String[] args) {
        new JMusicPlayer().setVisible(true);
    }
}
