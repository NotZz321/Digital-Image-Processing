
import java.awt.Point;

public class DIP_Lab {

    public static void main(String[] args) {
        ImageManager im = new ImageManager();
        
        // Mandril
        im.read("images/mandril.bmp");

        // Mandril black&white
        // im.read("images/mandrilB.bmp");

        // Rect 1
        // im.read("images/Rect1.bmp");

        // Rect 2
        // im.read("images/Rect2.bmp");

        // Rect 3
        // im.read("images/Rect3.bmp");

        // quest1(im);
        // quest2(im);
        // quest3(im);
        // quest4(im);
        // quest5(im);
        // quest6(im);
        // quest7(im);
        // quest8_Rect1(im);
        // quest8_Rect2(im);
        // quest8_Rect3(im);
        // quest9(im);
        // quest10(im);
        // quest11(im);
        // quest12(im);
        // quest13(im);
        quest14(im);
    }

    public static void quest1(ImageManager im) {
        im.write("images/cloneMandril.bmp");
    }

    public static void quest2(ImageManager im) {
        im.convertToGreenBlue();
        im.write("images/greenBlueMandril.bmp");
    }

    public static void quest3(ImageManager im) {
        im.convertToGrayscale();
        im.write("images/grayscaleMandril.bmp");
    }

    public static void quest4(ImageManager im) {
        im.powerLaw(1, 2.2);
        im.write("images/power2.2Mandril.bmp");

        im.restoreToOriginal();

        im.powerLaw(1, 0.4);
        im.write("images/power0.4Mandril.bmp");
    }

    public static void quest5(ImageManager im){
        im.adjustContrast(100);
        im.write("images/contrast100Mandril.bmp");

        im.restoreToOriginal();

        im.adjustContrast(-100);
        im.write("images/contrast-100Mandril.bmp");
    }

    public static void quest6(ImageManager im) {
        im.medianFilter(3); // 3 x 3
        im.write("images/median3x3Mandril.bmp");

        im.restoreToOriginal();

        im.medianFilter(7); // 7 x 7
        im.write("images/median7x7Mandril.bmp");

        im.restoreToOriginal();

        im.medianFilter(15); // 15 x 15
        im.write("images/median15x15Mandril.bmp");
    }

    public static void quest7(ImageManager im) {
        im.unsharpMasking(1);
        im.write("images/unsharpMandril.bmp");
    }

    public static void quest8_Rect1(ImageManager im) {
        FrequencyDomainManager rect1fft = im.getFrequencyDomain();

        rect1fft.IHPE(3);
        rect1fft.writeSpectrumLogScaled("Images/Rect1ILPFR3Spectrum.bmp");
        rect1fft.writePhase("images/Rect1ILPFR3Phase.bmp");

        rect1fft.IHPE(5);
        rect1fft.writeSpectrumLogScaled("Images/Rect1ILPFR5Spectrum.bmp");
        rect1fft.writePhase("images/Rect1ILPFR5Phase.bmp");

        rect1fft.IHPE(10);
        rect1fft.writeSpectrumLogScaled("Images/Rect1ILPFR10Spectrum.bmp");
        rect1fft.writePhase("images/Rect1ILPFR10Phase.bmp");

        rect1fft.IHPE(20);
        rect1fft.writeSpectrumLogScaled("Images/Rect1ILPFR20Spectrum.bmp");
        rect1fft.writePhase("images/Rect1ILPFR20Phase.bmp");
    }

    public static void quest8_Rect2(ImageManager im) {
        FrequencyDomainManager rect2fft = im.getFrequencyDomain();

        rect2fft.IHPE(3);
        rect2fft.writeSpectrumLogScaled("Images/Rect2ILPFR3Spectrum.bmp");
        rect2fft.writePhase("images/Rect2ILPFR3Phase.bmp");

        rect2fft.IHPE(5);
        rect2fft.writeSpectrumLogScaled("Images/Rect2ILPFR5Spectrum.bmp");
        rect2fft.writePhase("images/Rect2ILPFR5Phase.bmp");

        rect2fft.IHPE(10);
        rect2fft.writeSpectrumLogScaled("Images/Rect2ILPFR10Spectrum.bmp");
        rect2fft.writePhase("images/Rect2ILPFR10Phase.bmp");

        rect2fft.IHPE(20);
        rect2fft.writeSpectrumLogScaled("Images/Rect2ILPFR20Spectrum.bmp");
        rect2fft.writePhase("images/Rect2ILPFR20Phase.bmp");
    }

    public static void quest8_Rect3(ImageManager im) {
        FrequencyDomainManager rect3fft = im.getFrequencyDomain();

        rect3fft.IHPE(3);
        rect3fft.writeSpectrumLogScaled("Images/Rect3ILPFR3Spectrum.bmp");
        rect3fft.writePhase("images/Rect3ILPFR3Phase.bmp");

        rect3fft.IHPE(5);
        rect3fft.writeSpectrumLogScaled("Images/Rect3ILPFR5Spectrum.bmp");
        rect3fft.writePhase("images/Rect3ILPFR5Phase.bmp");

        rect3fft.IHPE(10);
        rect3fft.writeSpectrumLogScaled("Images/Rect3ILPFR10Spectrum.bmp");
        rect3fft.writePhase("images/Rect3ILPFR10Phase.bmp");

        rect3fft.IHPE(20);
        rect3fft.writeSpectrumLogScaled("Images/Rect3ILPFR20Spectrum.bmp");
        rect3fft.writePhase("images/Rect3ILPFR20Phase.bmp");
    }

    public static void quest9(ImageManager im) {
        im.addSaltNoise(0.10);
        im.write("images/10perSaltMandril.bmp");

        im.restoreToOriginal();

        im.addPepperNoise(0.10);
        im.write("images/10perPepperMandril.bmp");
    }

    public static void quest10(ImageManager im) {
        im.addPepperNoise(0.10);
        im.contraharmonicFilter(3, -1.5);
        im.write("images/-1_5QContraMandril.bmp");

        im.restoreToOriginal();

        im.addPepperNoise(0.10);
        im.contraharmonicFilter(3, 1.5);
        im.write("images/1_5QContraMandril.bmp");
    }

    public static void quest11(ImageManager im) {
        im.resizeBilinear(3.5, 3.5);
        im.write("images/3.5scaleMandril.bmp");

        im.restoreToOriginal();

        im.resizeBilinear(0.35, 0.35);
        im.write("images/0_35scaleMandril.bmp");
    }

    public static void quest12(ImageManager im) {
        ImageManager.StructuringElement se = im.new StructuringElement(3, 3, new Point(1, 1));
        ImageManager im2 = new ImageManager();
        im2.read("images/mandrilB.bmp");
        for (int i = 0; i < se.height; i++) {
            for (int j = 0; j < se.width; j++) {
                se.elements[j][i] = 255;
            }
        }

        im2.erosion(se);
        
        for (int y = 0; y < im.height; y++) {
            for (int x = 0; x < im.width; x++) {
                int origin = im.getRGB(x, y);
                int originR = (origin >> 16) & 0xFF;
                int originG = (origin >> 8) & 0xFF;
                int originB = origin & 0xFF;

                int erosed = im2.getRGB(x, y);
                int erosedR = (erosed >> 16) & 0xFF;
                int erosedG = (erosed >> 8) & 0xFF;
                int erosedB = erosed & 0xFF;

                int newR = Math.max(originR - erosedR, 0);
                int newG = Math.max(originG - erosedG, 0);
                int newB = Math.max(originB - erosedB, 0);
                int newColor = (newR << 16) | (newG << 8) | newB;
                im.setRGB(x, y, newColor);
            }
        }

        im.write("images/mandrilBoundary.bmp");
        
    }

    public static void quest13(ImageManager im) {
        im.cannyEdgeDetection(100, 180);
        im.write("images/mandrilCannyEdge.bmp");
    }

    public static void quest14(ImageManager im) {
        im.cannyEdgeDetection(48, 128);
        im.houghTransform(0.5);
        im.write("images/mandrilHoughLines.bmp");
    }

}
