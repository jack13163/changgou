package jack.changgou.controller;

import jack.changgou.file.FastDFSFile;
import jack.changgou.vo.FastDFSUtil;
import jack.changgou.vo.Result;
import jack.changgou.vo.StatusCode;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/upload")
@CrossOrigin
public class FileUploadController {

    /**
     * 文件上传
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping
    public Result<String> upload(@RequestBody MultipartFile file) throws Exception {
        // 封装文件信息
        FastDFSFile fastDFSFile = new FastDFSFile(file.getOriginalFilename(),
                file.getBytes(),
                StringUtils.getFilenameExtension(file.getOriginalFilename()));
        String[] info = FastDFSUtil.upload(fastDFSFile);
        // 拼接已经上传的文件访问地址，http://192.168.137.118:8080/group1/M00/02/11/fjkd.jpg
        String url = FastDFSUtil.getTrackerUrl() + "/"+ info[0]+ "/" + info[1];
        return new Result(true, StatusCode.OK, "文件上传成功", url);
    }
}
