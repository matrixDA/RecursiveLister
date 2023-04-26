import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class RecursiveListerFrame extends JFrame {
    JPanel mainPnl, centerPnl, bottomPnl, topPnl;

    JLabel titleLbl;
    JScrollPane pane;
    JTextArea textArea;

    JButton quitBtn, searchBtn;

    public RecursiveListerFrame()
    {
        setTitle("Recursive Directory Lister");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();

        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;

        setSize(750, 750);

        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout());

        add(mainPnl);
        createCenterPnl();
        createBottomPnl();
        createTopPnl();
        setVisible(true);
    }

    public void createTopPnl()
    {
        topPnl = new JPanel();
        titleLbl = new JLabel("File Lister");
        titleLbl.setVerticalTextPosition(JLabel.BOTTOM);
        titleLbl.setHorizontalTextPosition(JLabel.CENTER);
        topPnl.add(titleLbl);
        mainPnl.add(topPnl, BorderLayout.NORTH);
    }
    private void createCenterPnl()

    {
        centerPnl = new JPanel();

        textArea = new JTextArea(40, 60);
        pane = new JScrollPane(textArea);

        textArea.setEditable(false);

        centerPnl.add(pane);
        mainPnl.add(centerPnl, BorderLayout.CENTER);
    }


    private void createBottomPnl() {
        bottomPnl = new JPanel();
        bottomPnl.setLayout(new GridLayout(1, 2));

        searchBtn = new JButton("Search Directory");
        quitBtn = new JButton("Quit");

        bottomPnl.add(searchBtn);
        bottomPnl.add(quitBtn);

        mainPnl.add(bottomPnl, BorderLayout.SOUTH);


        quitBtn.addActionListener((ActionEvent ae) -> System.exit(0));

        searchBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Select A Directory: ");

                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                File workingDirectory = new File(System.getProperty("user.dir"));
                chooser.setCurrentDirectory(workingDirectory);


                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File dirChosen = chooser.getSelectedFile();
                    textArea.setText("Chosen Directory:   " + dirChosen + "\n\n");
                    textArea.append("Directory and Sub Directories: " +"\n\n");

                    listFiles(dirChosen);

                } else
                    textArea.append("File not found! Try Again!");
            }
        });

    }




    private void listFiles(File directory) {
        // Get the files in the directory
        File[] files = directory.listFiles();
        if (files != null) {
            // Iterate over the files
            for (File file : files) {
                if (file.isDirectory()) {
                    listFiles(file);
                } else {
                    // If the file is a file, append its path to the JTextArea
                    textArea.append(file.getPath() + "\n");
                }
            }
        }
    }

}
