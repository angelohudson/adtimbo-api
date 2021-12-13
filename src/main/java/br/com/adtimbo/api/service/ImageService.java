package br.com.adtimbo.api.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

	private static final String FOLDER = "/home/photo/membro/";

	public void uploadFile(MultipartFile file, String name) throws IOException {
		byte[] data = file.getBytes();
		Path path = Paths.get(FOLDER + name);
		Files.write(path, data);
	}

	public byte[] getPhotoById(String id) throws IOException {
		File file = new File(FOLDER + id);
		Path path = Paths.get(file.getAbsolutePath());
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
		return resource.getByteArray();
	}

}