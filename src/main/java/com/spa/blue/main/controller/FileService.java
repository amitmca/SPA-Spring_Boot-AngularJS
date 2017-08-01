package com.spa.blue.main.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartRequest;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.spa.blue.main.model.FormData;

@RestController
public class FileService {
	
	private AmazonS3Client s3client;
	
	@Autowired
	public FileService(AmazonS3Client s3client) {
		this.s3client = s3client;
	}

	// use this request mapping for post containing text
	@RequestMapping(path="/upload", method = RequestMethod.POST)
	public Map<String, Object>  upload(HttpSession session, HttpServletRequest req,
			Model model, @RequestBody FormData formData) {
		/*
		 * Obtain the Content length of the Input stream for S3 header
		 */
		Map<String, Object> modelView = new HashMap<String, Object>();
		String user = req.getRemoteUser();
		byte[] contentBytes = null;
		try {
			// try this as possible alternitive
			InputStream is2 = formData.getFile().getInputStream();
			
		   // InputStream is = event.getFile(formData.getFile().getName()).getInputStream();
		    contentBytes = IOUtils.toByteArray(is2);
		} catch (IOException e) {
		    System.err.printf("Failed while reading bytes from %s", e.getMessage());
		} 

		Long contentLength = Long.valueOf(contentBytes.length);

		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(contentLength);
		metadata.setContentType(formData.getFile().getContentType());
		

		/*
		 * Reobtain the tmp uploaded file as input stream
		 */
		InputStream inputStream = null;
		try {
			inputStream = formData.getFile().getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*
		 * Put the object in S3
		 */
		try {

		    s3client.putObject(new PutObjectRequest("" + user,
		    		formData.getFile().getOriginalFilename(), inputStream, metadata).withCannedAcl(CannedAccessControlList.PublicReadWrite));

		} catch (AmazonServiceException ase) {
		    System.out.println("Error Message:    " + ase.getMessage());
		    System.out.println("HTTP Status Code: " + ase.getStatusCode());
		    System.out.println("AWS Error Code:   " + ase.getErrorCode());
		    System.out.println("Error Type:       " + ase.getErrorType());
		    System.out.println("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
		    System.out.println("Error Message: " + ace.getMessage());
		} finally {
		    if (inputStream != null) {
		        try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		    }
		}

		String saved = "The file with " + formData.getFile().getOriginalFilename() + " has been saved.";
		modelView.put("saved", saved);
		return modelView;
	}

	
	// delete image
	@GetMapping(path="/delete-image")
	public String deleteImages(Long ID, Model model) {
		// stubbed out for later build
		return "admin/saved";
	}
		
	// list all images
	@RequestMapping("/list-images")
	public String listImages(Model model, @RequestParam String type) {
		// stubbed out for later build
		return "admin/list-all-images";
	}
}


