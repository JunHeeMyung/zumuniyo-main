package com.zumuniyo.main.util;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRCodeUtil {
	
	public BufferedImage createQRCode(String url) {

		BufferedImage bufferedImage = null;

		try {
			String qrCodeUrl = new String(url.getBytes("UTF-8"), "ISO-8859-1");
			int qrCodeColor = Color.BLACK.getRGB();
			int backgroundColor = Color.WHITE.getRGB();

			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeUrl, BarcodeFormat.QR_CODE, 150, 150);
			MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrCodeColor, backgroundColor);
			bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return bufferedImage;
	}

}