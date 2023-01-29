package com.blobstorage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("blob")
public class BlobController {

    @Value("azure-blob://wshicontainer/wshi-test.txt")
    private Resource blobFile;

    @GetMapping("/readBlobFile")
    public String readBlobFile() throws IOException {
        InputStream inputStream = this.blobFile.getInputStream();
        String result = StreamUtils.copyToString(
                inputStream,
                Charset.defaultCharset());
        System.out.println("wshi: " + result);
        return "wshi: " + result;
    }

    @PostMapping("/writeBlobFile")
    public String writeBlobFile(@RequestBody String data) throws IOException {
        try (OutputStream os = ((WritableResource) this.blobFile).getOutputStream()) {
            os.write(data.getBytes());
        }
        return "file was updated";
    }
}