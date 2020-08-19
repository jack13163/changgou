package jack.changgou.vo;

import jack.changgou.file.FastDFSFile;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;

import java.io.*;

/**
 * 实现文件管理：
 * 文件上传
 * 文件删除
 * 文件下载
 * 文件信息获取
 * Storage信息获取
 * Tracker信息获取
 */
public class FastDFSUtil {
    // 加载Tracker连接信息
    static {
        try {
            // 查找ClassPath下的文件路径
            String fileName = new ClassPathResource("fdfs_client.conf").getPath();
            ClientGlobal.init(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取TrackerServer
     * @return
     */
    public static TrackerServer getTrackerServer() throws IOException {
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        return trackerServer;
    }

    /**
     * 文件上传
     *
     * @param fastDFSFile
     * @return 文件路径
     * @throws Exception
     */
    public static String[] upload(FastDFSFile fastDFSFile) throws Exception {
        // 得到Tracker客户端获取的信息，并传入Storage客户端
        StorageClient storageClient = new StorageClient(getTrackerServer(), null);
        // 上传文件内容的字节数据，扩展名和附加参数
        NameValuePair[] metas = new NameValuePair[1];
        metas[0] = new NameValuePair("author", fastDFSFile.getAuthor());
        /**
         * uploads:
         *  uploads[0]: 文件上传所存储的Storage的组名字： 这里为group1
         *  uploads[1]: 文件存储到Storage上的名字：例如，M00/02/11/fjkd.jpg
         */
        String[] uploads = storageClient.upload_file(fastDFSFile.getContent(), fastDFSFile.getExt(), metas);
        return uploads;
    }

    /**
     * 获取文件信息
     *
     * @param groupName
     * @param remoteFileName
     * @return
     * @throws Exception
     */
    public static FileInfo getFileInfo(String groupName, String remoteFileName) throws Exception {
         // 得到Tracker客户端获取的信息，并传入Storage客户端
        StorageClient storageClient = new StorageClient(getTrackerServer(), null);
        // 通过Storage客户端获取文件信息
        FileInfo file_info = storageClient.get_file_info(groupName, remoteFileName);
        return file_info;
    }

    /**
     * 下载文件
     *
     * @param groupName
     * @param remoteFileName
     * @return
     * @throws Exception
     */
    public static InputStream download(String groupName, String remoteFileName) throws Exception {
        // 得到Tracker客户端获取的信息，并传入Storage客户端
        StorageClient storageClient = new StorageClient(getTrackerServer(), null);
        // 通过Storage客户端获取文件信息
        byte[] file_info = storageClient.download_file(groupName, remoteFileName);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(file_info);
        return inputStream;
    }

    /**
     * 删除文件
     *
     * @param groupName
     * @param remoteFileName
     * @return
     * @throws Exception
     */
    public static void deleteFile(String groupName, String remoteFileName) throws Exception {
        // 得到Tracker客户端获取的信息，并传入Storage客户端
        StorageClient storageClient = new StorageClient(getTrackerServer(), null);
        // 通过Storage客户端获取文件信息
        storageClient.delete_file(groupName, remoteFileName);
    }

    /**
     * 获取Storage信息
     *
     * @return
     * @throws Exception
     */
    public static StorageServer getStorageInfo() throws Exception {
        // 创建Tracker客户端
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        // 获取Storage客户端的信息
        StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
        return storeStorage;
    }

    /**
     * 获取Storage的IP和端口信息
     *
     * @return
     * @throws Exception
     */
    public static ServerInfo[] getStorageInfo(String groupName, String remoteFileName) throws Exception {
        // 创建Tracker客户端
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        // 获取Storage客户端的信息
        ServerInfo[] storeStorage = trackerClient.getFetchStorages(trackerServer, groupName, remoteFileName);
        return storeStorage;
    }

    /**
     * 获取Tracker的访问IP和端口信息
     *
     * @return
     * @throws Exception
     */
    public static String getTrackerUrl() throws Exception {
        // 获取Storage客户端的信息
        String ipAddr = getTrackerServer().getInetSocketAddress().getHostString();
        int port = ClientGlobal.getG_tracker_http_port();
        return "http://" + ipAddr + ":" + port;
    }

    /**
     * 测试获取文件信息
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
//        FileInfo fileInfo = getFileInfo("group1", "M00/00/00/wKiJdl8rBDGAQgPRAABqctc-zSk036.jpg");
//        System.out.println(fileInfo.getSourceIpAddr());
//        System.out.println(fileInfo.getFileSize());
//        System.out.println(fileInfo.getCreateTimestamp());
//        System.out.println(fileInfo.getCrc32());

//        InputStream inputStream = download("group1", "M00/00/00/wKiJdl8rBDGAQgPRAABqctc-zSk036.jpg");
//        FileOutputStream fileOutputStream = new FileOutputStream("C:/Users/Administrator/Desktop/01.jpg");
//        byte[] buff = new byte[1024];
//        while(inputStream.read(buff) > 0){
//            fileOutputStream.write(buff);
//        }
//        fileOutputStream.flush();
//        fileOutputStream.close();
//        inputStream.close();

//        deleteFile("group1", "M00/00/00/wKiJdl8rBDGAQgPRAABqctc-zSk036.jpg");
//        // 禁用缓存后测试删除：wKiJdl8rAZ-AKnKvAAE-OcjITh8968.jpg
//        deleteFile("group1", "M00/00/00/wKiJdl8rAZ-AKnKvAAE-OcjITh8968.jpg");

//        StorageServer storageInfo = getStorageInfo();
//        System.out.println(storageInfo.getStorePathIndex());
//        System.out.println(storageInfo.getInetSocketAddress().getAddress());
//        System.out.println(storageInfo.getInetSocketAddress().getPort());

//        // 获取Storage组的IP和端口信息【一个文件可能存在于多台机器上】
//        ServerInfo[] groupInfo = getStorageInfo("group1", "M00/00/00/wKiJdl8rA16AQGMiAABqctc-zSk185.jpg\n");
//        for (int i = 0; i < groupInfo.length; i++) {
//            System.out.println(groupInfo[i].getIpAddr());
//            System.out.println(groupInfo[i].getPort());
//        }

        // 获取Tracker访问的IP和端口信息
        System.out.println(getTrackerUrl());

    }

}
