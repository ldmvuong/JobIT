package fit.hcmute.jobit.controller;

import fit.hcmute.jobit.dto.response.file.UploadFileResponse;
import fit.hcmute.jobit.exception.StorageException;
import fit.hcmute.jobit.service.IFileService;
import fit.hcmute.jobit.util.annotation.ApiMessage;
import fit.hcmute.jobit.util.property.FileProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final IFileService fileService;

    private final FileProperties fileProperties;

    @PostMapping
    @ApiMessage("Upload a file")
    public ResponseEntity<UploadFileResponse> uploadFile(@RequestParam(value = "file", required = false) MultipartFile file,
                                                         @RequestParam("folder") String folder)
            throws URISyntaxException, IOException {

        if (file == null || file.isEmpty()) {
            throw new StorageException("Empty file");
        }

        String fileName = file.getOriginalFilename();
        List<String> allowedExtensions = Arrays.asList("pdf", "jpg", "jpeg", "png", "doc", "docx");

        boolean isValidExtension = allowedExtensions.stream()
                .anyMatch(ext -> fileName != null && fileName.toLowerCase().endsWith("." + ext));

        if (!isValidExtension) {
            throw new StorageException("Invalid file type. Allowed types: " + String.join(", ", allowedExtensions));
        }

        // create folder if it does not exist
        fileService.createDirectory(fileProperties.getBaseUri() + folder);

        // save file to the specified folder
        String uploadFile = fileService.saveFile(file, folder);
        // return the response with file information
        UploadFileResponse response = new UploadFileResponse(uploadFile, Instant.now());
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<Resource> download(
            @RequestParam(name = "fileName", required = false) String fileName,
            @RequestParam(name = "folder", required = false) String folder)
            throws StorageException, URISyntaxException, FileNotFoundException {

        // Check for missing parameters
        if (fileName == null || folder == null) {
            throw new StorageException("Missing required params: (fileName or folder)");
        }

        // Check file existence
        long fileLength = this.fileService.getFileLength(fileName, folder);
        if (fileLength == 0) {
            throw new StorageException("File with name = " + fileName + " not found.");
        }

        InputStreamResource resource = this.fileService.getResource(fileName, folder);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentLength(fileLength)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
