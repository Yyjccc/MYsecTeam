package org.hnust.MYSec.Configure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@AllArgsConstructor
@Configuration
@NoArgsConstructor
public class FileManager {
	@Value("${file.root}")
	public String uploadRootDir;
}
