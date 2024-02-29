package com.hnust.myblog.Service.utils;


import com.hnust.myblog.Mode.Base.Response;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@FeignClient(name = "common")
public interface CommServiceClient {
	@GetMapping("/token/generate/{module}/{id}")
	 Response generate(@PathVariable String module, @PathVariable String id);
	@PostMapping("/token/decode")
	Response decode(@RequestParam String token);

	@PostMapping(path ="/file/upload/{module}/{type}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	Response uploadFile(@PathVariable String module,@PathVariable String type,@RequestPart MultipartFile file);

	@GetMapping(value = "/file/get/{id}",produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
	void getFile(HttpServletResponse response, @PathVariable Long id) throws IOException;
}
