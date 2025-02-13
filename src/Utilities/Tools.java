package Utilities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Tools {

    // method loads image and scales it using scale_image
    public BufferedImage load_image(String filePath){

        try(InputStream is = getClass().getResourceAsStream(filePath)) {
            if (is == null) {
                throw new IllegalArgumentException("File was not found: " + filePath);
            }

            return ImageIO.read(is);

        } catch (IOException e){
            throw new RuntimeException("Error while loading picture: " + filePath, e);
        }
    }

    // method scales image before it is applied
    public BufferedImage scale_image(BufferedImage original, int width, int height){

        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height, null);
        g2.dispose();

        return scaledImage;
    }

    public BufferedImage mirror_image(BufferedImage image) {
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-image.getWidth(), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return op.filter(image, null);
    }
}
