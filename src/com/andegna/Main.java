package com.andegna;

import com.andegna.chrono.EthiopianChronology;
import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null,
                "Andegna EthiopianChronology Version: " + EthiopianChronology.VERSION,
                "Andegna Systems", JOptionPane.INFORMATION_MESSAGE);
    }

}
