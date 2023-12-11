import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class client {
    private JFrame frame;
    private JTextField nTextField;
    private JTextField rTextField;
    private JLabel resultLabel;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                client window = new client();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public client() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(4, 2));

        JLabel nLabel = new JLabel("Masukan n:");
        frame.getContentPane().add(nLabel);

        nTextField = new JTextField();
        frame.getContentPane().add(nTextField);

        JLabel rLabel = new JLabel("Masukan r:");
        frame.getContentPane().add(rLabel);

        rTextField = new JTextField();
        frame.getContentPane().add(rTextField);

        JButton calculateButton = new JButton("Hitung Permutasi");
        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculatePermutation();
            }
        });
        frame.getContentPane().add(calculateButton);

        resultLabel = new JLabel("Result: ");
        frame.getContentPane().add(resultLabel);
    }

    private void calculatePermutation() {
        try (Socket socket = new Socket("localhost", 9999);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())
        ) {
            // Get n and r from the text fields
            int n = Integer.parseInt(nTextField.getText());
            int r = Integer.parseInt(rTextField.getText());

            // Send the request to the server
            oos.writeObject(new PermutationRequest(n, r));

            // Receive the result from the server
            int result = ois.readInt();

            // Display the result
            resultLabel.setText("Result: " + result);
        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace();
        }
    }
}
