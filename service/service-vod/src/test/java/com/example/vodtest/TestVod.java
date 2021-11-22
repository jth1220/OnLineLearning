package com.example.vodtest;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

public class TestVod {

    public static void main(String[] args) throws Exception {
        uploadVideo();
    }

    //媒体资源管理



    //上传视频
    public static void uploadVideo(){
        String accessKeyId="LTAI5tPuiFvGs3gjf4VxS5Fd";
        String accessKeySecret="O8DL1XbszhyE0vpLhxWU0BynyVyVAX";
        String title="上传测试";//上传之后文件名
        String fileName="C:\\Users\\JiangTonghui\\Desktop\\谷粒学院\\1-阿里云上传测试视频\\6 - What If I Want to Move Faster.mp4";//本地文件路径和名称
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        //并发线程数
        request.setTaskNum(1);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

    //根据id获取视频播放凭证
    public static void getPlayAuth() throws Exception{
        //创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tPuiFvGs3gjf4VxS5Fd", "O8DL1XbszhyE0vpLhxWU0BynyVyVAX");
        //获取视频凭证request 和 response
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId("007ac94ecdec43268ceb96fcae90adb0");
        GetVideoPlayAuthResponse response = client.getAcsResponse(request);
        System.out.println("Play auth:"+response.getPlayAuth());
    }

    //根据id获取视频地址
    public static void getPlayUrl() throws Exception{
        //根据视频id获取视频地址
        //创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tPuiFvGs3gjf4VxS5Fd", "O8DL1XbszhyE0vpLhxWU0BynyVyVAX");
        //获取视频地址request
        GetPlayInfoRequest request=new GetPlayInfoRequest();
        //想request中设置视频id
        request.setVideoId("007ac94ecdec43268ceb96fcae90adb0");
        //调用初始化对象的方法
        GetPlayInfoResponse response = client.getAcsResponse(request);
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }
}
