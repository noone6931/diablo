//package org.example.diablo.webhook;
//
//import com.alibaba.dashscope.aigc.generation.Generation;
//import com.alibaba.dashscope.aigc.generation.GenerationParam;
//import com.alibaba.dashscope.aigc.generation.GenerationResult;
//import com.alibaba.dashscope.common.Message;
//import com.alibaba.dashscope.common.Role;
//import com.alibaba.dashscope.exception.ApiException;
//import com.alibaba.dashscope.exception.InputRequiredException;
//import com.alibaba.dashscope.exception.NoApiKeyException;
//import com.alibaba.dashscope.utils.JsonUtils;
//import com.alibaba.fastjson.JSON;
//import org.gitlab.api.GitlabAPI;
//import org.gitlab.api.models.GitlabCommitDiff;
//import org.gitlab.api.models.GitlabMergeRequest;
//import org.gitlab.api.models.GitlabNote;
//
//import java.io.IOException;
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class QWenGitlabTest {
//    public static void callWithMessage()
//            throws NoApiKeyException, ApiException, InputRequiredException {
//        Generation gen = new Generation();
//        List<Message> messages = new ArrayList<>();
//
//        // 1. 获得代码变更
//        GitlabMergeRequest mergeRequest = getMergeRequestChanges(1, 1);
//        System.out.println(JSON.toJSONString(mergeRequest));
//        /**
//         * 这段代码中的 @@ -33,6 +33,8 @@ 是 diff 格式中表示代码变更位置的标记。在这个标记中：
//         *
//         * -33,6 表示原始文件中被修改部分的起始行和行数。意思是从第 33 行开始，连续 6 行的代码被修改了。
//         * +33,8 表示修改后的文件中对应的起始行和行数。意思是在修改后的文件中，修改部分也是从第 33 行开始，但是新增了 8 行代码。
//         * 因此，这个 diff 标记告诉我们在原始文件的第 33 行开始的 6 行代码被修改，修改后的文件中也是从第 33 行开始，新增了 8 行代码。
//         *
//         * 在 GitLab API 中创建讨论时，你可以使用这样的 diff 标记来指定你要评论的代码位置。具体来说，你可以使用 position 参数来指定 base_sha、start_sha 和 head_sha，以及新增的 new_path 和 new_line 来确定你要评论的代码位置。在你的 API 请求中，你需要提供这些信息来确保你的评论会出现在正确的代码位置上。
//         */
//        List<GitlabCommitDiff> changes = mergeRequest.getChanges();
//        for (GitlabCommitDiff commitDiff : changes) {
//            String diff = commitDiff.getDiff();
//            Message systemMsg =
//                    Message.builder().role(Role.SYSTEM.getValue()).content("You are a helpful code assistant.").build();
////            Message userMsg = Message.builder().role(Role.USER.getValue()).content("对以下gitlab的merge request只做变更前后代码做code review，返回格式按照：代码评分、代码建议返回，代码：" + diff).build();
//            Message userMsg = Message.builder().role(Role.USER.getValue()).content("对以下gitlab的merge request只做变更前后代码做code review，返回格式按照：代码评分、代码建议（尽量简短），以下是代码变更diff：" + diff).build();
//            messages.add(systemMsg);
//            messages.add(userMsg);
//            GenerationParam param =
//                    GenerationParam.builder()
//                            .apiKey("sk-7b87607c7a54412c83a609661a05f0b3")
//                            .model(Generation.Models.QWEN_PLUS).messages(messages)
//                            .resultFormat(GenerationParam.ResultFormat.MESSAGE)
//                            .build();
//            GenerationResult result = gen.call(param);
//            // 3. 把大模型code review结果提交到评论
//            String comment = result.getOutput().getChoices().get(0).getMessage().getContent();
//            System.out.println(comment);
//
//            createNote(mergeRequest, comment);
//            GitlabAPI gitlabAPI = getGitlabAPI();
//            try {
//                gitlabAPI.createCommitComment(mergeRequest.getIid(), mergeRequest.getSha(), comment, commitDiff.getNewPath(), "19", "new");
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
////        String diffText = changes.stream().map(GitlabCommitDiff::getDiff).collect(Collectors.joining());
////        GitlabCommitDiff commitDiff = mergeRequest.getChanges().get(0);
//////        String diff = commitDiff.getDiff();
////        String diff = diffText;
////        // 2. 通过大模型获得code review 结果
////        Message systemMsg =
////                Message.builder().role(Role.SYSTEM.getValue()).content("You are a helpful code assistant.").build();
////        Message userMsg = Message.builder().role(Role.USER.getValue()).content("对以下gitlab的merge request只做变更前后代码做code review，返回格式按照：代码评分、代码建议返回，代码：" + diff).build();
////        messages.add(systemMsg);
////        messages.add(userMsg);
////        GenerationParam param =
////                GenerationParam.builder()
////                        .apiKey("sk-7b87607c7a54412c83a609661a05f0b3")
////                        .model(Generation.Models.QWEN_TURBO).messages(messages)
////                        .resultFormat(GenerationParam.ResultFormat.MESSAGE)
////                        .build();
////        GenerationResult result = gen.call(param);
////        System.out.println(JsonUtils.toJson(result));
////        // {"requestId":"468f22eb-673d-91ed-8b2c-51db041ef92d","usage":{"input_tokens":25,"output_tokens":68,"total_tokens":93},"output":{"choices":[{"finish_reason":"stop","message":{"role":"assistant","content":"通义千问是阿里云自主研发的超大规模语言模型，能够回答问题、创作文字，还能表达观点、撰写代码。作为一个大型预训练语言模型，我能够根据您提出的指令产出相关的回复，并尽可能地提供准确和有用的信息。如果您有任何问题或需要帮助，请随时告诉我，我会尽力提供支持。"}}]}}
////        /**
////         * {
////         * 	"requestId": "468f22eb-673d-91ed-8b2c-51db041ef92d",
////         * 	"usage": {
////         * 		"input_tokens": 25,
////         * 		"output_tokens": 68,
////         * 		"total_tokens": 93
////         *        },
////         * 	"output": {
////         * 		"choices": [{
////         * 			"finish_reason": "stop",
////         * 			"message": {
////         * 				"role": "assistant",
////         * 				"content": "通义千问是阿里云自主研发的超大规模语言模型，能够回答问题、创作文字，还能表达观点、撰写代码。作为一个大型预训练语言模型，我能够根据您提出的指令产出相关的回复，并尽可能地提供准确和有用的信息。如果您有任何问题或需要帮助，请随时告诉我，我会尽力提供支持。"
////         *            }
////         *        }]
////         *    }
////         * }
////         */
////
////        // 3. 把大模型code review结果提交到评论
////        String comment = result.getOutput().getChoices().get(0).getMessage().getContent();
////        System.out.println(comment);
////
////        createNote(mergeRequest, comment);
////        GitlabAPI gitlabAPI = getGitlabAPI();
////        try {
////            gitlabAPI.createCommitComment(mergeRequest.getIid(), mergeRequest.getSha(), comment, commitDiff.getNewPath(), "19", "new");
////        } catch (IOException e) {
////            throw new RuntimeException(e);
////        }
//    }
//
//    public static GitlabMergeRequest getMergeRequestChanges(Serializable projectId, Integer mergeRequestIid)
//            throws NoApiKeyException, ApiException, InputRequiredException {
//        // 使用gitlab获得变更代码内容
//        GitlabAPI gitlabAPI = getGitlabAPI();
//        try {
//            GitlabMergeRequest mergeRequestChanges = gitlabAPI.getMergeRequestChanges(projectId, mergeRequestIid);
//            System.out.println(JSON.toJSONString(mergeRequestChanges.getChanges()));
//            return mergeRequestChanges;
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static GitlabAPI getGitlabAPI() {
//        String hostUrl = "http://localhost:9980";
//        String token = "glpat-sqkSCZ6FjcfPM3tWmiBw";
//        GitlabAPI gitlabAPI = GitlabAPI.connect(hostUrl, token);
//        return gitlabAPI;
//    }
//
//    public static GitlabNote createNote(GitlabMergeRequest mergeRequest, String message)
//            throws NoApiKeyException, ApiException, InputRequiredException {
//        // 使用gitlab获得变更代码内容
//        GitlabAPI gitlabAPI = getGitlabAPI();
//        try {
//            GitlabNote note = gitlabAPI.createNote(mergeRequest, message);
//            System.out.println(JSON.toJSONString(note));
//            return note;
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
