package base;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UploadCase extends BaseCase {
    public String filePath;
    public String fileKey = "files";

}
