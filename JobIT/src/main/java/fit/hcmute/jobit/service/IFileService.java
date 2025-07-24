package fit.hcmute.jobit.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

public interface IFileService {
    void createDirectory(String folder) throws URISyntaxException;
    String saveFile(MultipartFile file, String folder) throws URISyntaxException, IOException;
    long getFileLength(String fileName, String folder) throws URISyntaxException;
    InputStreamResource getResource(String fileName, String folder) throws URISyntaxException, FileNotFoundException;
}
