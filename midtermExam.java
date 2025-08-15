public class midtermExam {
    public static void main(String[] args) {
        ExamImageManager im = new ExamImageManager();
        im.read("images/gamemaster_noise_2025.bmp");
        
        // Preprocess
        im.medianFilter(5);
        im.alphaTrimmedFilter(5, 6);
        im.gaussianFilter(3, 1.3);
        im.pixelate(16);
        im.write("images/gameMasterPreprocess.bmp");

        // Paint
        paint(im);
        im.write("images/gameMasterPaint.bmp");

        // // Postprocess  
        // im.pixelate(2);         
        // im.write("images/gameMasterPostprocess.bmp");
    }

    public static void paint(ExamImageManager im) {

        // background
        im.fillColor(0, 0, im.width, im.height, 
                    255, 0, 0, 
                    190, 213, 154, 
                    35);           

        // hair
        im.fillColor(im.width / 8, 0 , 6 * im.width / 7, 3 * im.height / 7, 
                    157, 0, 255,     // purple
                    24, 195, 26, 
                    40);

        // shirt
        im.fillColor(im.width / 13, 5 * im.height / 6, im.width, im.height, 
                    0, 0, 255,       // blue
                    46, 175, 57, 
                    95);

        // skin face
        im.fillColor(im.width / 8, im.height / 4, 6 * im.width / 7, 6 * im.height / 9, 
                    210, 180, 140,   // light brown (skin tone)
                    143, 204, 160, 
                    59);

        // skin neck
        im.fillColor(3 * im.width / 9, 6 * im.height / 9, 6 * im.width / 9 , 8 * im.height / 9, 
                    210, 180, 140,   // light brown (skin tone)
                    141, 204, 196, 
                    75);

        // moustache
        im.fillColor(im.width / 7, im.height / 2, 6 * im.width / 7, 6 * im.height / 7, 
                    128, 128, 128,   // gray
                    90, 175,90, 
                    70);            

        // black sunglasses
        im.fillColor(im.width / 7, 3 * im.height / 8, 6 * im.width / 7, 5 * im.height / 8, 
                    0, 0, 0, 
                    30, 30, 30, 
                    65);
        
        // white sunglasses' left
        im.fillColor(2 * im.width / 7, 3 * im.height / 8, 3 * im.width / 7, 4 * im.height / 8, 
                    255, 255, 255, 
                    200, 200, 200, 
                    80);

        // white sunglasses' right
        im.fillColor(4 * im.width / 7, 3 * im.height / 8, 13 * im.width / 18 - 1, 4 * im.height / 8, 
                    255, 255, 255, 
                    200, 200, 200, 
                    80);
        
        // paint detail
        paintDetails(im);
    }

    public static void paintDetails(ExamImageManager im) {
        
        // face
        im.fillColor(3 * im.width / 14 - 10, 2 * im.height / 7 - 10, 11 * im.width / 14 + 10, 5 * im.height / 7 - 20, 
                    210, 180, 140, 
                    245, 245, 210, 
                    30);

        // neck
        im.fillColor(3 * im.width / 9 + 20, 6 * im.height / 9, 6 * im.width / 9 - 20, 8 * im.height / 9, 
                    210, 180, 140,   
                    226, 227, 210, 
                    60);

        // hair
        im.fillColor(im.width / 9, 0 , 6 * im.width / 7, 3 * im.height / 7 , 
                    157, 0, 255, 
                    97, 220, 128, 
                    62);

        // black sunglasses
        im.fillColor(im.width / 7, 3 * im.height / 8, 6 * im.width / 7, 5 * im.height / 8, 
                    0, 0, 0, 
                    94, 102, 51, 
                    65);

        // white sunglasses' left
        im.fillColor(2 * im.width / 7, 3 * im.height / 8, 3 * im.width / 7, 4 * im.height / 8, 
                    255, 255, 255, 
                    255, 0, 0, 
                    15);

        // white sunglasses' right
        im.fillColor(4 * im.width / 7, 3 * im.height / 8, 13 * im.width / 18, 4 * im.height / 8, 
                    255, 255, 255, 
                    255, 00, 0, 
                    15);   

        // background
        im.fillColor(0, 0, im.width, im.height, 
                    245, 245, 220, 
                    94, 102, 51, 
                    60);
    }
}
