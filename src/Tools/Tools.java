package Tools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Tools {

    // method loads image and scales it using scale_image
    public BufferedImage load_image(String filePath, int width, int height){

        try(InputStream is = getClass().getResourceAsStream(filePath)) {
            if (is == null) {
                throw new IllegalArgumentException("File was not found: " + filePath);
            }

            BufferedImage br = ImageIO.read(is);
            return scale_image(br, width, height);

        } catch (IOException e){
            throw new RuntimeException("Error while loading picture: " + filePath, e);
        }
    }

    // method scales image before it is applied
    private BufferedImage scale_image(BufferedImage original, int width, int height){

        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height, null);
        g2.dispose();

        return scaledImage;
    }
}
