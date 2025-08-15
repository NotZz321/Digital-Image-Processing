import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ExamImageManager {
    public int width;
    public int height;
    public int bitDepth;

    private BufferedImage img;
    private BufferedImage original;

    public ExamImageManager() {
    }

    public ExamImageManager(BufferedImage image) {
        width = image.getWidth();
        height = image.getHeight();
        bitDepth = image.getColorModel().getPixelSize();

        img = new BufferedImage(width, height, image.getType());
        original = new BufferedImage(width, height, img.getType());
    }

    public boolean read(String fileName){
        try {
            img = ImageIO.read(new File(fileName));
            
            width = img.getWidth();
            height = img.getHeight();
            bitDepth = img.getColorModel().getPixelSize();

            original = new BufferedImage(width, height, img.getType());
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    original.setRGB(x, y, img.getRGB(x, y));
                }
            }
            System.out.println("Image " + fileName + " with "+ width + " x " + height + " pixels(" + bitDepth + " bits per pixel) has been read!");
            return true;
        } catch (IOException e) {
            System.out.println(e);
            return false;   
        } 
    }

    public boolean write(String fileName) {
        try {
            ImageIO.write(img, "bmp", new File(fileName));
            System.out.println("Image " + fileName + " has been written!");

            return true;
        } catch (IOException e) {
            System.out.println(e);
            return false;
        } catch (NullPointerException e) {
            System.out.println(e);
            return false;
        }
    }

    public void cloneImg(BufferedImage image) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int color = image.getRGB(x, y);
                img.setRGB(x, y, color);
                original.setRGB(x, y, color);
            }
        }
    }

    public BufferedImage getImage() {
        return img;
    }

    public void setRGB(int x , int y, int rgb) {
        img.setRGB(x, y, rgb);
    }

    public int getRGB(int x, int y) {
        return img.getRGB(x, y);
    }

    public void restoreToOriginal() {
        width = original.getWidth();
        height = original.getHeight();

        img = new BufferedImage(width, height, img.getType());
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                img.setRGB(x, y, original.getRGB(x, y));
            }
        }
    }

    public void averagingFilter(int size) {
        if (img == null) return;

        if (size % 2 == 0) {
            System.out.println("Size Invalid: must be odd number!");
            return;
        }

        BufferedImage tempBuf = new BufferedImage(width, height, img.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int sumRed = 0, sumGreen = 0, sumBlue = 0;

                for (int i = y - size / 2; i <= y + size / 2; i++) {
                    for (int j = x - size / 2; j <= x + size / 2; j++) {
                        if (i >= 0 && i < height && j >= 0 && j < width) {
                            int color = img.getRGB(j, i);
                            int r = (color >> 16) & 0xff;
                            int g = (color >> 8) & 0xff;
                            int b = color & 0xff;

                            sumRed += r;
                            sumGreen += g;
                            sumBlue += b;
                        }
                    }
                }

                sumRed /= (size * size);
                sumRed = sumRed > 255? 255: sumRed;
                sumRed = sumRed < 0? 0: sumRed;

                sumGreen /= (size * size);
                sumGreen = sumGreen > 255? 255: sumGreen;
                sumGreen = sumGreen < 0? 0: sumGreen;

                sumBlue /= (size * size);
                sumBlue = sumBlue > 255? 255: sumBlue;
                sumBlue = sumBlue < 0? 0: sumBlue;

                int newColor = (sumRed << 16) | (sumGreen << 8) | sumBlue;
                
                tempBuf.setRGB(x, y, newColor);
            }
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                img.setRGB(x, y, tempBuf.getRGB(x, y));
            }
        }
    }

    public void gaussianFilter(int size, double sigma) {
        if (img == null) return;

        if (size % 2 == 0) {
            System.out.println("Size Invalid: must be odd number!");
            return;
        }

        double[][] kernel = gaussianKernel(size, sigma);
        BufferedImage tempBuf = new BufferedImage(width, height, img.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++){
                double sumR = 0, sumG = 0, sumB = 0;

                int curColor = img.getRGB(x, y);
                int curR = (curColor >> 16) & 0xff;
                int curG = (curColor >> 8) & 0xff;
                int curB = curColor & 0xff;

                for (int i = y - size / 2; i <= y + size / 2; i++) {
                    for (int j = x - size / 2; j <= x + size / 2; j++) {
                        if (i >= 0 && i < height && j >= 0 && j < width) {
                            int color = img.getRGB(j, i);
                            int r = (color >> 16) & 0xff;
                            int g = (color >> 8) & 0xff;
                            int b = color & 0xff;

                            int kernelY = i - (y - size / 2);
                            int kernelX = j - (x - size / 2);
                            double weight = kernel[kernelY][kernelX];

                            sumR += r * weight;
                            sumG += g * weight;
                            sumB += b * weight;
                        }
                    }   
                }

                int newR = (int)sumR;
                newR = newR > 255? 255: newR;
                newR = newR < 0? 0: newR;

                int newG = (int)sumG;
                newG = newG > 255? 255: newG;
                newG = newG < 0? 0: newG;

                int newB = (int)sumB;
                newB = newB > 255? 255: newB;
                newB = newB < 0? 0: newB;
                
                int newColor = (newR << 16) | (newG << 8) | newB;
                tempBuf.setRGB(x, y, newColor);
            }
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                img.setRGB(x, y, tempBuf.getRGB(x, y));
            }
        }
    }

    private double[][] gaussianKernel(int size, double sigma) {
        double[][] kernel = new double[size][size];
        double sum = 0.0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int x = j - size / 2;
                int y = i - size / 2;
                
                double exp = -(x * x + y * y) / (2 * sigma * sigma);
                double value = Math.exp(exp) / (2 * Math.PI * sigma * sigma);

                kernel[i][j] = value;
                sum += value;
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                kernel[i][j] /= sum;
            }
        }

        return kernel;
    }

    public void medianFilter(int size) {
        if (img == null) return;

        if (size % 2 == 0) {
            System.out.println("Size Invalid: must be odd number");
            return;
        }

        BufferedImage tempBuf = new BufferedImage(width, height, img.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int[] kernelRed = new int[size * size];
                int[] kernelGreen = new int[size * size];
                int[] kernelBlue = new int[size * size];

                for (int i = y - size / 2; i <= y + size / 2; i++) {
                    for (int j = x - size / 2; j <= x + size / 2; j++) {
                        int r, g, b;

                        if (i >= 0 && i < height && j >= 0 && j < width) {
                            int color = img.getRGB(j, i);
                            r = (color >> 16) & 0xff;
                            g = (color >> 8) & 0xff;
                            b = color & 0xff;

                            kernelRed[(i - (y - size / 2)) * size + (j - (x - size / 2))] = r;
                            kernelGreen[(i - (y - size / 2)) * size + (j - (x - size / 2))] = g;
                            kernelBlue[(i - (y - size / 2)) * size + (j - (x - size / 2))] = b;
                        }
                    }   
                }

                for (int i = 0; i < size * size - 1; i++) {
                    for (int j = 0; j < size * size - i - 1; j++) {
                        int temp;

                        if (kernelRed[j] > kernelRed[j + 1]) {
                            temp = kernelRed[j];
                            kernelRed[j] = kernelRed[j + 1];
                            kernelRed[j + 1] = temp;
                        }

                        if (kernelGreen[j] > kernelGreen[j + 1]) {
                            temp = kernelGreen[j];
                            kernelGreen[j] = kernelGreen[j + 1];
                            kernelGreen[j + 1] = temp;
                        }

                        if (kernelBlue[j] > kernelBlue[j + 1]) {
                            temp = kernelBlue[j];
                            kernelBlue[j] = kernelBlue[j + 1];
                            kernelBlue[j + 1] = temp;
                        }
                    }
                }

                int newColor = (kernelRed[(size * size) / 2 + 1] << 16) | (kernelGreen[(size * size) / 2 + 1] << 8) | (kernelBlue[(size * size) / 2 + 1]);

                tempBuf.setRGB(x, y, newColor);
            }
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                img.setRGB(x, y, tempBuf.getRGB(x, y));
            }
        }
    }

    public void unsharpMasking(int k) {
        if (img == null) return;

        int size = 3;
        ExamImageManager tempBur = new ExamImageManager(img);

        tempBur.cloneImg(img);

        tempBur.averagingFilter(size);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                int colorOriginal = img.getRGB(x, y);
                int rO = (colorOriginal >> 16) & 0xff;
                int gO = (colorOriginal >> 8) & 0xff;
                int bO = colorOriginal & 0xff;

                int colorBur = tempBur.getRGB(x, y);
                int rB = (colorBur >> 16) & 0xff;
                int gB = (colorBur >> 8) & 0xff;
                int bB = colorBur & 0xff;

                int maskR = rO - rB;
                int maskG = gO - gB;
                int maskB = bO - bB;
                
                int unsharpR = rO + k * maskR;
                unsharpR = unsharpR > 255? 255: unsharpR;
                unsharpR = unsharpR < 0? 0: unsharpR;

                int unsharpG = gO + k * maskG;
                unsharpG = unsharpG > 255? 255: unsharpG;
                unsharpG = unsharpG < 0? 0: unsharpG;

                int unsharpB = bO + k * maskB;
                unsharpB = unsharpB > 255? 255: unsharpB;
                unsharpB = unsharpB < 0? 0: unsharpB;

                colorOriginal = (unsharpR << 16) | (unsharpG << 8) | unsharpB;

                img.setRGB(x, y, colorOriginal);
            }
        }
    }

    public void alphaTrimmedFilter(int size, int d) {
        if (img == null) return;

        if (size % 2 == 0) {
            System.out.println("Size Invalid: must be odd number!");
            return;
        }

        BufferedImage tempBuf = new BufferedImage(width, height, img.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int[] kernelRed = new int[size * size];
                int[] kernelGreen = new int[size * size];
                int[] kernelBlue = new int[size * size];

                for (int i = y - size / 2; i <= y + size / 2; i++) {
                    for (int j = x - size / 2; j <= x + size / 2; j++)  {
                        int r, g, b, k;

                        if (i >= 0 && i < height && j >= 0 && j < width) {
                            int color = img.getRGB(j, i);
                            r = (color >> 16) & 0xff;
                            g = (color >> 8) & 0xff;
                            b = color & 0xff;

                            kernelRed[(i - (y - size / 2)) * size + (j - (x - size / 2))] = r;
                            kernelGreen[(i - (y - size / 2)) * size + (j - (x - size / 2))] = g;
                            kernelBlue[(i - (y - size / 2)) * size + (j - (x - size / 2))] = b;
                         }
                    }
                }

                for (int i = 0; i < size * size - 1; i++) {
                    for (int j = 0; j < size * size - i - 1; j++) {
                        int temp;

                        if (kernelRed[j] > kernelRed[j + 1]) {
                            temp = kernelRed[j];
                            kernelRed[j] = kernelRed[j + 1];
                            kernelRed[j + 1] = temp;
                        }

                        if (kernelGreen[j] > kernelGreen[j + 1]) {
                            temp = kernelGreen[j];
                            kernelGreen[j] = kernelGreen[j + 1];
                            kernelGreen[j + 1] = temp;
                        }

                        if (kernelBlue[j] > kernelBlue[j + 1]) {
                            temp = kernelBlue[j];
                            kernelBlue[j] = kernelBlue[j + 1];
                            kernelBlue[j + 1] = temp;
                        }
                    }
                }

                int remainingPixel = size * size - d;
                int red = 0, green = 0, blue = 0;

                for (int i = 0; i < remainingPixel; i++) {
                    red += kernelRed[(d / 2) + i];
                    green += kernelGreen[(d / 2) + i];
                    blue += kernelBlue[(d / 2) + i];
                }

                red /= remainingPixel;
                red = red > 255? 255: red;
                red = red < 0? 0: red;

                green /= remainingPixel;
                green = green > 255? 255: green;
                green = green < 0? 0: green;

                blue /= remainingPixel;
                blue = blue > 255? 255: blue;
                blue = blue < 0? 0: blue;

                int newColor = (red << 16) | (green << 8) |  blue;

                tempBuf.setRGB(x, y, newColor);
            }
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                img.setRGB(x, y, tempBuf.getRGB(x, y));
            }
        }
    }

    public void laplacian() {
        if (img == null) return;

        int[][] filter = {
            {0, 1, 0},
            {1, -4, 1},
            {0, 1, 4}
        };

        BufferedImage tempBuf = new BufferedImage(width, height, img.getType());

        grayscaleHistogramEqualisation();
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double sum = 0;

                for (int filterY = -1; filterY <= 1; filterY++) {
                    for (int filterX = -1; filterX <= 1; filterX++) {
                        int i = y + filterY;
                        int j = x + filterX;

                        if (i >= 0 && i < height && j >= 0 && j < width) {
                            int color = img.getRGB(j, i);
                            sum += color * filter[filterY + 1][filterX + 1]; // shift
                        }
                    }
                }

                int edge = (int)sum;
                edge = edge > 255? 255: edge;
                edge = edge < 0? 0: edge;

                int newColor = (edge << 16) | (edge << 8) | edge;

                tempBuf.setRGB(x, y, newColor);
            }
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                img.setRGB(x, y, tempBuf.getRGB(x, y));
            }
        }
    }

    public void grayscaleHistogramEqualisation() {
        if (img == null) return;

        int[] histogram = new int[256];

        for (int i = 0; i < 256; i++) {
            histogram[i] = 0;
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int color = img.getRGB(x, y);
                int r = (color >> 16) & 0xff;
                int g = (color >> 8) & 0xff;
                int b = color & 0xff;


                int gray = (int)(0.2126*r + 0.7152*g + 0.0722*b);


                histogram[gray]++;

            }
        }

        int[] histogramCDF = new int [256];
        int cdfMin = 0;

        for (int i = 0; i < 256; i++) {
            histogramCDF[i] = 0;
        }

        for (int i = 0; i < 256; i++) {
            if (i == 0) histogramCDF[i] = histogram[i];
            else histogramCDF[i] = histogramCDF[i-1] + histogram[i];
            if (histogram[i] > 0 && cdfMin == 0)    cdfMin = i;
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
            int color = img.getRGB(x, y);

            int r = (color >> 16) & 0xff;
            int g = (color >> 8) & 0xff;
            int b = color & 0xff;


            int gray = (int)(0.2126*r + 0.7152*g + 0.0722*b);


            gray = (int)Math.round(255.0*(histogramCDF[gray] - cdfMin)/(width*height-cdfMin));
            gray = gray > 255? 255: gray;
            gray = gray < 0? 0: gray;


            color = (gray << 16) | (gray << 8) | gray;
            img.setRGB(x, y, color);
            }
        }
    }

    public int[] getGrayscaleHistogram(){
        if (img == null) return null;

        grayscaleHistogramEqualisation();

        int[] histogram = new int[256];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int color = img.getRGB(x, y);
                int gray = color & 0xff;

                histogram[gray]++;
            }
        }
        restoreToOriginal();

        return histogram;
    }
    
    public void writeHistogramToCSV(int[] histogram, String fileName) {
        try {
            FileWriter fw = new FileWriter(fileName);

            for (int i = 0; i < histogram.length; i++) {
                fw.write(histogram[i] + ",");
            }
            fw.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void fillColor(int startX, int startY, int endX, int endY,int newR, int newG, int newB, int targetR, int targetG, int targetB, int tolerance) {
        if (img == null) return;

        if (startX < 0 || startX >= width || endX < 0 || endX > width || startX > endX) return;
        if (startY < 0 || startY >= height || endY < 0 || endY > height || startY > endY) return;

        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                int color = img.getRGB(x, y);

                int curR = (color >> 16) & 0xff;
                int curG = (color >> 8) & 0xff;
                int curB = color & 0xff;


                if ((curR >= targetR - tolerance && curR <= targetR + tolerance)
                && (curG >= targetG - tolerance && curG <= targetG + tolerance)
                && (curB >= targetB - tolerance && curB <= targetB + tolerance)) {
                    int newColor = (newR << 16) | (newG << 8) | newB;

                    img.setRGB(x, y, newColor);
                }
            }
        }
    }

    public void pixelate(int blockSize) {
        if (img == null) return;
    
        if (blockSize <= 1) return;
    
    BufferedImage tempBuf = new BufferedImage(width, height, img.getType());
    
    for (int y = 0; y < height; y += blockSize) {
        for (int x = 0; x < width; x += blockSize) {
            
            long sumR = 0, sumG = 0, sumB = 0;
            int count = 0;
            
            for (int i = y; i < Math.min(y + blockSize, height); i++) {
                for (int j = x; j < Math.min(x + blockSize, width); j++) {
                    int color = img.getRGB(j, i);
                    sumR += (color >> 16) & 0xff;
                    sumG += (color >> 8) & 0xff;
                    sumB += color & 0xff;
                    count++;
                }
            }
            
            int avgR = (int)(sumR / count);
            int avgG = (int)(sumG / count);
            int avgB = (int)(sumB / count);
            int avgColor = (avgR << 16) | (avgG << 8) | avgB;
            
            for (int i = y; i < Math.min(y + blockSize, height); i++) {
                for (int j = x; j < Math.min(x + blockSize, width); j++) {
                    tempBuf.setRGB(j, i, avgColor);
                }
            }
        }
    }
  
    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
            img.setRGB(x, y, tempBuf.getRGB(x, y));
        }
    }
}
}
