package emse.ismin.minesweeper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class implements the Controller for the application
 */
public class Controller implements ActionListener {

    public static final int QUIT = 0;
    private Gui gui;
    private int type; //type d'evenement (QUIT, ...)

    /**
     * Creates a Controller according to the given type and GUI
     * @param type type you want to use
     * @param gui gui you want to listen to
     */
    public Controller(int type, Gui gui) {
        this.gui = gui;
        this.type = type;
    }

    /**
     * Creates a Controller for the given GUI
     * @param gui GUI you want to control
     */
    public Controller(Gui gui) {
        this.gui = gui;
    }

    /**
     * Manages events occurring on the GUI
     * @param e <code>ActionEvent</code>
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object cmd = e.getSource();
        if (cmd.equals(gui.getQuitButton()) || cmd.equals(gui.getmQuit())) {
            if(JOptionPane.showConfirmDialog(null, "Are you sure ?", "Exit Confimation", JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION) {
                System.exit(0);
            }
        }
        else if(cmd.equals(gui.getnewGameButton()) || cmd.equals(gui.getmNew())) {
            gui.newGame();;
        }
        else if(cmd.equals(gui.getEasyRadio())) {
            gui.newGame(Level.EASY);
        }
        else if(cmd.equals(gui.getMediumRadio())) {
            gui.newGame(Level.MEDIUM);
        }
        else if(cmd.equals(gui.getHardRadio())) {
            gui.newGame(Level.HARD);
        }
        else if(cmd.equals(gui.getmInfo())) {
            JOptionPane.showMessageDialog(null, "Author: Alexandre Ladrière\nDate: September 2019\nLanguage: Java", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
        else if(cmd.equals(gui.getmLicense())) {
            JOptionPane.showMessageDialog(null, "MIT License\n" +
                    "\n" +
                    "Copyright (c) 2019 Alexandre Ladrière\n" +
                    "\n" +
                    "Permission is hereby granted, free of charge, to any person obtaining a copy\n" +
                    "of this software and associated documentation files (the \"Software\"), to deal\n" +
                    "in the Software without restriction, including without limitation the rights\n" +
                    "to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\n" +
                    "copies of the Software, and to permit persons to whom the Software is\n" +
                    "furnished to do so, subject to the following conditions:\n" +
                    "\n" +
                    "The above copyright notice and this permission notice shall be included in all\n" +
                    "copies or substantial portions of the Software.\n" +
                    "\n" +
                    "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n" +
                    "IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n" +
                    "FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE\n" +
                    "AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n" +
                    "LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n" +
                    "OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE\n" +
                    "SOFTWARE.", "License", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private void restart(Level level) {
        //gui.getMinesweeper().setField(new Field(level));
        //gui.getMinesweeper().setContentPane(new Gui(gui.getMinesweeper()));
    }
}

