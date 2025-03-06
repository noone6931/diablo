//package org.example.diablo.webhook;
//
//import com.alibaba.dashscope.exception.ApiException;
//import com.alibaba.dashscope.exception.InputRequiredException;
//import com.alibaba.dashscope.exception.NoApiKeyException;
//import org.gitlab.api.GitlabAPI;
//import org.gitlab.api.models.GitlabMergeRequest;
//
//import java.io.IOException;
//
//public class GitlabTest {
//    public static void callWithMessage()
//            throws NoApiKeyException, ApiException, InputRequiredException {
//        // 使用gitlab获得变更代码内容
//        String hostUrl = "http://localhost:9980";
//        String token = "glpat-sqkSCZ6FjcfPM3tWmiBw";
//        GitlabAPI gitlabAPI = GitlabAPI.connect(hostUrl, token);
//        try {
//            GitlabMergeRequest mergeRequestChanges = gitlabAPI.getMergeRequestChanges(1, 1);
//            System.out.println(mergeRequestChanges.getChanges());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static void main(String[] args) {
//        try {
//            callWithMessage();
//        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
//            System.out.println(e.getMessage());
//        }
//        System.exit(0);
//    }
//}
