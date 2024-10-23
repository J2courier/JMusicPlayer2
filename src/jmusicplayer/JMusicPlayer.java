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
//task 4: search algorithm
//task 5: remove button
//task 6: save added file
//task 7: UI for play/pause song
//task 8: make it organize
//task 9: make the song playable


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.*;

public class JMusicPlayer extends JFrame {

    private List<String> musicLibrary; // List of songs added
    private Clip clip; // Clip to handle WAV playback
    private File currentSong; // Currently selected song file
    
    public static JPanel createPanel(Color bgColor, int width, int height, LayoutManager layout) {
        JPanel panel = new JPanel();
        panel.setBackground(bgColor);
        panel.setLayout(layout);
        panel.setPreferredSize(new Dimension(width, height));
        return panel;
    }

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
    
    public void songFrame(String songName) {
        JFrame songFrame = new JFrame("Song: " + songName);
        ImageIcon songDefaultIcon = new ImageIcon("C:\\Users\\ADMIN\\Documents\\NetBeansProjects\\JMusicPlayer\\src\\jmusicplayer\\uta.jpg");
        songFrame.setIconImage(songDefaultIcon.getImage());
        songFrame.setTitle(songName);
        songFrame.setSize(500, 700);
        songFrame.setLayout(new BorderLayout());
        songFrame.setMinimumSize(new Dimension(500, 700));
        songFrame.setLocationRelativeTo(null);
        songFrame.getContentPane().setBackground(new Color(0x171d2e));
        songFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      
        
        JLabel songLabel = new JLabel(songName, SwingConstants.CENTER);
        songLabel.setForeground(Color.WHITE);
        songLabel.setFont(new Font("Cascade Code", Font.BOLD, 16));
        JPanel topPanel = createPanel(new Color(0x171d2e), 500, 50, new BorderLayout());
//        JPanel rightPanel = createPanel(new Color(0x52fc03), 50, 700, new BorderLayout());
        JPanel centerPanel = createPanel(new Color(0x152142), 200, 200, new BorderLayout());
//        JPanel leftPanel = createPanel(new Color(0x03dffc), 50, 700, new BorderLayout());
        JPanel botPanel = createPanel(new Color(0x152142), 500, 60, new FlowLayout());
        JPanel songDuration = createPanel(new Color(0x171d2e), 100, 100, new FlowLayout());
        JPanel centerPanelLeft = createPanel(new Color(0x171d2e), 50, 700,new BorderLayout());
        JPanel centerPanelRight = createPanel(new Color(0x171d2e), 50, 700,new BorderLayout());
        JButton prevBtn = createBtn("Prev", 150, 40, new Color(0x152142), new Color(0xFFFFFF), false );
        JButton pauseBtn = createBtn("Pause", 150, 40, new Color(0x152142), new Color(0xFFFFFF), false );
        JButton nextBtn = createBtn("Next", 150, 40, new Color(0x152142), new Color(0xFFFFFF), false );
        botPanel.add(prevBtn);
        botPanel.add(pauseBtn);
        botPanel.add(nextBtn);
        
        JLabel centerPanelImage = new JLabel(new ImageIcon("C:\\Users\\ADMIN\\Documents\\NetBeansProjects\\JMusicPlayer\\src\\jmusicplayer\\uta.jpg"));
        
        songFrame.add(topPanel, BorderLayout.NORTH);
        songFrame.add(centerPanel, BorderLayout.CENTER);
        songFrame.add(botPanel, BorderLayout.SOUTH);
        songDuration.add(songLabel, BorderLayout.CENTER); 
        centerPanel.add(songDuration, BorderLayout.SOUTH);
        centerPanel.add(centerPanelLeft, BorderLayout.EAST);
        centerPanel.add(centerPanelRight, BorderLayout.WEST);
        centerPanel.add(centerPanelImage);
            
        songFrame.setVisible(true);
    }

    public JMusicPlayer() {
        musicLibrary = new ArrayList<>(); 
        ImageIcon musicIcon = new ImageIcon("C:\\Users\\ADMIN\\Documents\\NetBeansProjects\\JMusicPlayer\\src\\jmusicplayer\\uta.jpg");
        setIconImage(musicIcon.getImage());
        JPanel heading = createPanel(new Color(0x152142), 100, 70, new BorderLayout());
        JLabel library = createLabel("Library", new Color(0xFFFFFF), 100, 700, new Font("Cascade Code", Font.BOLD, 18));
        library.setBorder(null);
        heading.add(library, BorderLayout.WEST);
        JPanel body = createPanel(new Color(0x171d2e), 500, 700, new FlowLayout());
        JPanel listDisplay = createPanel(new Color(0x171d2e), 500, 300, new FlowLayout());
        JPanel footerInBody = createPanel(new Color(0x171d2e), 500, 30, new BorderLayout());
        JPanel footer = createPanel(new Color(0x152142), 100, 60, new FlowLayout());
        JButton addButton = createBtn("Open File", 150, 40, new Color(0x152142), new Color(0xFFFFFF), false);
        JButton removeButton = createBtn("Remove", 150, 40, new Color(0x152142), new Color(0xFFFFFF), false);
        JButton searchButton = createBtn("Search", 150, 40, new Color(0x152142), new Color(0xFFFFFF), false);
        footer.add(addButton);
        footer.add(removeButton);
        footer.add(searchButton);
        body.add(listDisplay);
        body.add(footerInBody, BorderLayout.SOUTH);
             
        
         addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    if (selectedFile.getName().toLowerCase().endsWith(".wav")) {
                        musicLibrary.add(selectedFile.getName());
                        JLabel newLabel = createLabel(selectedFile.getName(), new Color(0xFFFFFF), 400, 25, new Font("Cascade Code", Font.PLAIN, 12));

                        // When the label is clicked, play the WAV song
                        newLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                            @Override
                            public void mouseClicked(java.awt.event.MouseEvent e) {
                                songFrame(newLabel.getText());
                                playWav(selectedFile); // Play the WAV file
                                
                            }
                        });

                        listDisplay.add(newLabel);
                        listDisplay.revalidate();
                        listDisplay.repaint();
                    } else {
                        JOptionPane.showMessageDialog(null, "Unsupported file format. Please select a WAV file.");
                    }
                }
            }
        });
        
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchQuery = JOptionPane.showInputDialog(JMusicPlayer.this, "Enter the song name to search:", "Search", JOptionPane.PLAIN_MESSAGE);
                if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                    int index = searchMusic(searchQuery);
                    if (index != -1) {
                        JOptionPane.showMessageDialog(JMusicPlayer.this, "Found: " + musicLibrary.get(index) + " at index " + index);
                    } else {
                        JOptionPane.showMessageDialog(JMusicPlayer.this, "Music not found.");
                    }
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
        add(footer, BorderLayout.SOUTH);
    }
    
    
    //search algorithm is still in development
    public int searchMusic(String query) { 
        for (int i = 0; i < musicLibrary.size(); i++) {
            if (musicLibrary.get(i).equalsIgnoreCase(query)) {
                return i; 
            }
        }
        return -1; 
    }
    
    private void playWav(File songFile) {
        try {
            if (clip != null && clip.isOpen()) {
                clip.stop();
                clip.close();
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(songFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new JMusicPlayer().setVisible(true);
    }
}
