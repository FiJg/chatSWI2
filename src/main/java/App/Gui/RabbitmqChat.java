package App.Gui;

import org.springframework.boot.SpringApplication;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;


public class RabbitmqChat {



    public static void main(String[] args) {

        SpringApplication.run(RabbitmqChat.class , args);

/*
        java.awt.EventQueue.invokeLater(() -> {
            try {
                FrameContainers.initLoginFrame();
                FrameContainers.loginFrame.setVisible(true);
                FrameContainers.loginFrame.setTitle("Demo Chat");
                FrameContainers.loginFrame.setIconImage(ImageIO.read(new File("more/chaticon.png")));

            } catch (IOException ex) {
                Logger.getLogger(RabbitMqChat.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

 */
    }

}