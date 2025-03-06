package org.example.diablo.webhook;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhook")
public class WebHookController {
    @PostMapping("/webhook-endpoint")
    public String handleWebhook(@RequestBody String payload) {
        System.out.println("Received webhook payload: " + payload);
        // 解析payload并处理MR事件
//        payload = "{\"object_kind\":\"merge_request\",\"event_type\":\"merge_request\",\"user\":{\"id\":1,\"name\":\"Administrator\",\"username\":\"root\",\"avatar_url\":\"https://www.gravatar.com/avatar/e64c7d89f26bd1972efa854d13d7dd61?s=80\\u0026d=identicon\",\"email\":\"[REDACTED]\"},\"project\":{\"id\":2,\"name\":\"ruoyi\",\"description\":\"ruoyi\",\"web_url\":\"http://localhost:8200/root/ruoyi\",\"avatar_url\":null,\"git_ssh_url\":\"ssh://git@localhost:8200/root/ruoyi.git\",\"git_http_url\":\"http://localhost:8200/root/ruoyi.git\",\"namespace\":\"Administrator\",\"visibility_level\":20,\"path_with_namespace\":\"root/ruoyi\",\"default_branch\":\"main\",\"ci_config_path\":null,\"homepage\":\"http://localhost:8200/root/ruoyi\",\"url\":\"ssh://git@localhost:8200/root/ruoyi.git\",\"ssh_url\":\"ssh://git@localhost:8200/root/ruoyi.git\",\"http_url\":\"http://localhost:8200/root/ruoyi.git\"},\"object_attributes\":{\"assignee_id\":1,\"author_id\":1,\"created_at\":\"2024-06-10 01:26:41 UTC\",\"description\":\"Uat\",\"head_pipeline_id\":null,\"id\":2,\"iid\":2,\"last_edited_at\":null,\"last_edited_by_id\":null,\"merge_commit_sha\":null,\"merge_error\":null,\"merge_params\":{\"force_remove_source_branch\":\"1\"},\"merge_status\":\"preparing\",\"merge_user_id\":null,\"merge_when_pipeline_succeeds\":false,\"milestone_id\":null,\"source_branch\":\"uat\",\"source_project_id\":2,\"state_id\":1,\"target_branch\":\"main\",\"target_project_id\":2,\"time_estimate\":0,\"title\":\"Uat\",\"updated_at\":\"2024-06-10 01:26:41 UTC\",\"updated_by_id\":null,\"url\":\"http://localhost:8200/root/ruoyi/-/merge_requests/2\",\"source\":{\"id\":2,\"name\":\"ruoyi\",\"description\":\"ruoyi\",\"web_url\":\"http://localhost:8200/root/ruoyi\",\"avatar_url\":null,\"git_ssh_url\":\"ssh://git@localhost:8200/root/ruoyi.git\",\"git_http_url\":\"http://localhost:8200/root/ruoyi.git\",\"namespace\":\"Administrator\",\"visibility_level\":20,\"path_with_namespace\":\"root/ruoyi\",\"default_branch\":\"main\",\"ci_config_path\":null,\"homepage\":\"http://localhost:8200/root/ruoyi\",\"url\":\"ssh://git@localhost:8200/root/ruoyi.git\",\"ssh_url\":\"ssh://git@localhost:8200/root/ruoyi.git\",\"http_url\":\"http://localhost:8200/root/ruoyi.git\"},\"target\":{\"id\":2,\"name\":\"ruoyi\",\"description\":\"ruoyi\",\"web_url\":\"http://localhost:8200/root/ruoyi\",\"avatar_url\":null,\"git_ssh_url\":\"ssh://git@localhost:8200/root/ruoyi.git\",\"git_http_url\":\"http://localhost:8200/root/ruoyi.git\",\"namespace\":\"Administrator\",\"visibility_level\":20,\"path_with_namespace\":\"root/ruoyi\",\"default_branch\":\"main\",\"ci_config_path\":null,\"homepage\":\"http://localhost:8200/root/ruoyi\",\"url\":\"ssh://git@localhost:8200/root/ruoyi.git\",\"ssh_url\":\"ssh://git@localhost:8200/root/ruoyi.git\",\"http_url\":\"http://localhost:8200/root/ruoyi.git\"},\"last_commit\":{\"id\":\"a39deeac9763e89f81993bf85cc5f13efb9c3bad\",\"message\":\"error\\n\",\"title\":\"error\",\"timestamp\":\"2024-06-10T08:16:19+08:00\",\"url\":\"http://localhost:8200/root/ruoyi/-/commit/a39deeac9763e89f81993bf85cc5f13efb9c3bad\",\"author\":{\"name\":\"luomao1\",\"email\":\"luomao1@lenovo.com\"}},\"work_in_progress\":false,\"total_time_spent\":0,\"time_change\":0,\"human_total_time_spent\":null,\"human_time_change\":null,\"human_time_estimate\":null,\"assignee_ids\":[1],\"state\":\"opened\",\"blocking_discussions_resolved\":true,\"action\":\"open\"},\"labels\":[],\"changes\":{\"merge_status\":{\"previous\":\"unchecked\",\"current\":\"preparing\"}},\"repository\":{\"name\":\"ruoyi\",\"url\":\"ssh://git@localhost:8200/root/ruoyi.git\",\"description\":\"ruoyi\",\"homepage\":\"http://localhost:8200/root/ruoyi\"},\"assignees\":[{\"id\":1,\"name\":\"Administrator\",\"username\":\"root\",\"avatar_url\":\"https://www.gravatar.com/avatar/e64c7d89f26bd1972efa854d13d7dd61?s=80\\u0026d=identicon\",\"email\":\"[REDACTED]\"}]}";

        // 假设你已经处理了事件并决定提交评论
        PayloadDTO object = JSON.parseObject(payload, PayloadDTO.class);


        // 调用GitLab API提交评论

        return "Webhook received and processed";
    }


}
