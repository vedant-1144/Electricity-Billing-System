//package New_Origin;
//
//import com.google.zxing.BarcodeFormat;
//import com.google.zxing.EncodeHintType;
//import com.google.zxing.WriterException;
//import com.google.zxing.client.j2se.MatrixToImageWriter;
//import com.google.zxing.common.BitMatrix;
//import com.google.zxing.qrcode.QRCodeWriter;
//
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.util.Base64;
//import java.util.HashMap;
//import java.util.Map;
//
//public class UPI_QR_generator {
//
//    public static String generateQRCode(String upiId, String amount) {
//        String upiUrl = "upi://pay?pa=" + upiId + "&am=" + amount;
//        int width = 300;
//        int height = 300;
//        String imageType = "png"; // or "jpg", "gif", etc.
//
//        Map<EncodeHintType, Object> hints = new HashMap<>();
//        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//
//        try {
//            BitMatrix bitMatrix = new QRCodeWriter().encode(upiUrl, BarcodeFormat.QR_CODE, width, height, hints);
//            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            ImageIO.write(qrImage, imageType, baos);
//            byte[] bytes = baos.toByteArray();
//            return Base64.getEncoder().encodeToString(bytes);
//        } catch (WriterException | IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public static void main(String[] args) {
//        String upiId = "yourupi@example.com"; // Replace with your UPI ID
//        String amount = "100"; // Replace with the amount to be paid
//        String qrCodeBase64 = generateQRCode(upiId, amount);
//        if (qrCodeBase64 != null) {
//            System.out.println("Generated QR code successfully.");
//            // Now you can use the base64 string to display the QR code in your application
//        } else {
//            System.out.println("Failed to generate QR code.");
//        }
//    }
//}
