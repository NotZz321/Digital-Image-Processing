import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class Lab1_DIP{
    public static void main(String[] args) {
        // BufferedImage collect data of image
        BufferedImage img = null;        
        File f = null;

        f = new File("images/mandril.bmp");

        try {
            img = ImageIO.read(f);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int width = img.getWidth();
        int height = img.getHeight();
        int bitDepth = img.getColorModel().getPixelSize();

        // System.out.println(img.getWidth());
        // System.out.println(img.getHeight());
        // System.out.println(img.getColorModel().getPixelSize());

        // int size = width * height * bitDepth / 8;
        // System.out.println(size + " bytes");


        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                
                int color = img.getRGB(x, y);
                int b = color & 0xff;
                int g = (color >> 8) & 0xff;
                int r = (color >> 16) & 0xff;
                
                color = 0;
                color = (r << 16) | (g) | b;
                // shift bit >> , <<
                img.setRGB(x, y, color);
            }
        }
        

        f = new File("images/out.bmp");
        try {
            ImageIO.write(img, "bmp", f);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }    
    }
}