package com.zumuniyo.main.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zumuniyo.main.dto.MemberDTO;
import com.zumuniyo.main.util.QRCodeUtil;

import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping("/image")
public class ImageController {
	
	@PostMapping("/upload")
	public List<String> uploadImages( List<MultipartFile> images ,HttpServletRequest request){
		
		if(request.getSession().getAttribute("member")==null) return null;
		String memberSeq = ((MemberDTO)request.getSession().getAttribute("member")).getMemSeq().toString();
		
		List<String> fileNames = new ArrayList<String>();
		
		try {
			for(MultipartFile image : images) {
				
				String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); 
				String oldImageName = image.getOriginalFilename();
				String extension =  oldImageName.indexOf(".")>=0?oldImageName.substring(oldImageName.lastIndexOf(".")+1):"";
				StringBuilder random = new StringBuilder("");
				for(int i=0;i<100;i++) random.append(((int)(Math.random()*10)));
				String newImageName = date+random+memberSeq+(extension.equals("")?"":".")+extension;
				
				String rootPath = System.getProperty("user.dir");
				Path DIR = Paths.get(rootPath+"\\src\\main\\resources\\static\\image");
				Files.createDirectories(DIR);
				Path imagePath = DIR.resolve(newImageName).normalize();
				if(Files.exists(imagePath)) return null;			
				image.transferTo(imagePath);
				fileNames.add(newImageName);
				
				log.info("[이미지업로드]: "+oldImageName+" => "+newImageName);
			}
			
		}catch(Exception e) {
			log.info("[이미지업로드] Exception 발생");
			System.out.println(e);
			return null;
		}
		
		return fileNames;
	}
	
	@GetMapping(value ="/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getImage(@PathVariable String imageName,HttpServletRequest request){

		try {
			
			String rootPath = System.getProperty("user.dir");
			Path DIR = Paths.get(rootPath+"\\src\\main\\resources\\static\\image");
			Path imagePath = DIR.resolve(imageName).normalize();
			if(!Files.exists(imagePath)) return null;
			
			InputStream imageStream = new FileInputStream(imagePath.toString());
			log.info("[이미지요청] "+imagePath.toString());
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			int read;
			byte[] imageByteArray = new byte[imageStream.available()];
			while ((read = imageStream.read(imageByteArray, 0, imageByteArray.length)) != -1) {
				buffer.write(imageByteArray, 0, read);
			}
			buffer.flush();
			byte[] targetArray = buffer.toByteArray();
			imageStream.close();

			return new ResponseEntity<byte[]>(targetArray, HttpStatus.OK);
		} catch (Exception e) {
			log.info("[이미지로딩] Exception 발생");
			System.out.println(e);
			return null;
		}
	}
	
	@GetMapping(value ="/qrcode/**")
	public void getQRCode(HttpServletRequest request,HttpServletResponse response){
		
		String url = request.getRequestURI().split("/qrcode/")[1];
		
		try {
			if(url!=null && !url.equals("")) {
				response.setContentType("image/png");
				BufferedImage qrCode = new QRCodeUtil().createQRCode(url);
				ImageIO.write(qrCode, "png", response.getOutputStream());
			}else {
				response.setContentType("text/html;charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				PrintWriter out = response.getWriter();
				out.write("URL을 다시 확인해주세요");
			}
		}catch(Exception e) {
			log.info("[QR생성오류]");
			System.out.println(e);
			return;
		}

		return ;
	}

}
