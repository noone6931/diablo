package org.example.diablo.webhook;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * {
 * 	"object_kind": "merge_request",
 * 	"event_type": "merge_request",
 * 	"user": {
 * 		"id": 1,
 * 		"name": "Administrator",
 * 		"username": "root",
 * 		"avatar_url": "https://www.gravatar.com/avatar/e64c7d89f26bd1972efa854d13d7dd61?s=80\u0026d=identicon",
 * 		"email": "[REDACTED]"
 *        },
 * 	"project": {
 * 		"id": 2,
 * 		"name": "ruoyi",
 * 		"description": "ruoyi",
 * 		"web_url": "http://localhost:8200/root/ruoyi",
 * 		"avatar_url": null,
 * 		"git_ssh_url": "ssh://git@localhost:8200/root/ruoyi.git",
 * 		"git_http_url": "http://localhost:8200/root/ruoyi.git",
 * 		"namespace": "Administrator",
 * 		"visibility_level": 20,
 * 		"path_with_namespace": "root/ruoyi",
 * 		"default_branch": "main",
 * 		"ci_config_path": null,
 * 		"homepage": "http://localhost:8200/root/ruoyi",
 * 		"url": "ssh://git@localhost:8200/root/ruoyi.git",
 * 		"ssh_url": "ssh://git@localhost:8200/root/ruoyi.git",
 * 		"http_url": "http://localhost:8200/root/ruoyi.git"
 *    },
 * 	"object_attributes": {
 * 		"assignee_id": 1,
 * 		"author_id": 1,
 * 		"created_at": "2024-06-10 01:26:41 UTC",
 * 		"description": "Uat",
 * 		"head_pipeline_id": null,
 * 		"id": 2,
 * 		"iid": 2,
 * 		"last_edited_at": null,
 * 		"last_edited_by_id": null,
 * 		"merge_commit_sha": null,
 * 		"merge_error": null,
 * 		"merge_params": {
 * 			"force_remove_source_branch": "1"
 *        },
 * 		"merge_status": "preparing",
 * 		"merge_user_id": null,
 * 		"merge_when_pipeline_succeeds": false,
 * 		"milestone_id": null,
 * 		"source_branch": "uat",
 * 		"source_project_id": 2,
 * 		"state_id": 1,
 * 		"target_branch": "main",
 * 		"target_project_id": 2,
 * 		"time_estimate": 0,
 * 		"title": "Uat",
 * 		"updated_at": "2024-06-10 01:26:41 UTC",
 * 		"updated_by_id": null,
 * 		"url": "http://localhost:8200/root/ruoyi/-/merge_requests/2",
 * 		"source": {
 * 			"id": 2,
 * 			"name": "ruoyi",
 * 			"description": "ruoyi",
 * 			"web_url": "http://localhost:8200/root/ruoyi",
 * 			"avatar_url": null,
 * 			"git_ssh_url": "ssh://git@localhost:8200/root/ruoyi.git",
 * 			"git_http_url": "http://localhost:8200/root/ruoyi.git",
 * 			"namespace": "Administrator",
 * 			"visibility_level": 20,
 * 			"path_with_namespace": "root/ruoyi",
 * 			"default_branch": "main",
 * 			"ci_config_path": null,
 * 			"homepage": "http://localhost:8200/root/ruoyi",
 * 			"url": "ssh://git@localhost:8200/root/ruoyi.git",
 * 			"ssh_url": "ssh://git@localhost:8200/root/ruoyi.git",
 * 			"http_url": "http://localhost:8200/root/ruoyi.git"
 *        },
 * 		"target": {
 * 			"id": 2,
 * 			"name": "ruoyi",
 * 			"description": "ruoyi",
 * 			"web_url": "http://localhost:8200/root/ruoyi",
 * 			"avatar_url": null,
 * 			"git_ssh_url": "ssh://git@localhost:8200/root/ruoyi.git",
 * 			"git_http_url": "http://localhost:8200/root/ruoyi.git",
 * 			"namespace": "Administrator",
 * 			"visibility_level": 20,
 * 			"path_with_namespace": "root/ruoyi",
 * 			"default_branch": "main",
 * 			"ci_config_path": null,
 * 			"homepage": "http://localhost:8200/root/ruoyi",
 * 			"url": "ssh://git@localhost:8200/root/ruoyi.git",
 * 			"ssh_url": "ssh://git@localhost:8200/root/ruoyi.git",
 * 			"http_url": "http://localhost:8200/root/ruoyi.git"
 *        },
 * 		"last_commit": {
 * 			"id": "a39deeac9763e89f81993bf85cc5f13efb9c3bad",
 * 			"message": "error\n",
 * 			"title": "error",
 * 			"timestamp": "2024-06-10T08:16:19+08:00",
 * 			"url": "http://localhost:8200/root/ruoyi/-/commit/a39deeac9763e89f81993bf85cc5f13efb9c3bad",
 * 			"author": {
 * 				"name": "luomao1",
 * 				"email": "luomao1@lenovo.com"
 *            }
 *        },
 * 		"work_in_progress": false,
 * 		"total_time_spent": 0,
 * 		"time_change": 0,
 * 		"human_total_time_spent": null,
 * 		"human_time_change": null,
 * 		"human_time_estimate": null,
 * 		"assignee_ids": [1],
 * 		"state": "opened",
 * 		"blocking_discussions_resolved": true,
 * 		"action": "open"
 *    },
 * 	"labels": [],
 * 	"changes": {
 * 		"merge_status": {
 * 			"previous": "unchecked",
 * 			"current": "preparing"
 *        }
 *    },
 * 	"repository": {
 * 		"name": "ruoyi",
 * 		"url": "ssh://git@localhost:8200/root/ruoyi.git",
 * 		"description": "ruoyi",
 * 		"homepage": "http://localhost:8200/root/ruoyi"
 *    },
 * 	"assignees": [{
 * 		"id": 1,
 * 		"name": "Administrator",
 * 		"username": "root",
 * 		"avatar_url": "https://www.gravatar.com/avatar/e64c7d89f26bd1972efa854d13d7dd61?s=80\u0026d=identicon",
 * 		"email": "[REDACTED]"
 *    }]
 * }
 */
@lombok.NoArgsConstructor
@lombok.Data
public class PayloadDTO {

    @JsonProperty("object_kind")
    private String objectKind;
    @JsonProperty("event_type")
    private String eventType;
    @JsonProperty("user")
    private UserDTO user;
    @JsonProperty("project")
    private ProjectDTO project;
    @JsonProperty("object_attributes")
    private ObjectAttributesDTO objectAttributes;
    @JsonProperty("labels")
    private List<?> labels;
    @JsonProperty("changes")
    private ChangesDTO changes;
    @JsonProperty("repository")
    private RepositoryDTO repository;
    @JsonProperty("assignees")
    private List<AssigneesDTO> assignees;

    @lombok.NoArgsConstructor
    @lombok.Data
    public static class UserDTO {
        @JsonProperty("id")
        private Integer id;
        @JsonProperty("name")
        private String name;
        @JsonProperty("username")
        private String username;
        @JsonProperty("avatar_url")
        private String avatarUrl;
        @JsonProperty("email")
        private String email;
    }

    @lombok.NoArgsConstructor
    @lombok.Data
    public static class ProjectDTO {
        @JsonProperty("id")
        private Integer id;
        @JsonProperty("name")
        private String name;
        @JsonProperty("description")
        private String description;
        @JsonProperty("web_url")
        private String webUrl;
        @JsonProperty("avatar_url")
        private Object avatarUrl;
        @JsonProperty("git_ssh_url")
        private String gitSshUrl;
        @JsonProperty("git_http_url")
        private String gitHttpUrl;
        @JsonProperty("namespace")
        private String namespace;
        @JsonProperty("visibility_level")
        private Integer visibilityLevel;
        @JsonProperty("path_with_namespace")
        private String pathWithNamespace;
        @JsonProperty("default_branch")
        private String defaultBranch;
        @JsonProperty("ci_config_path")
        private Object ciConfigPath;
        @JsonProperty("homepage")
        private String homepage;
        @JsonProperty("url")
        private String url;
        @JsonProperty("ssh_url")
        private String sshUrl;
        @JsonProperty("http_url")
        private String httpUrl;
    }

    @lombok.NoArgsConstructor
    @lombok.Data
    public static class ObjectAttributesDTO {
        @JsonProperty("assignee_id")
        private Integer assigneeId;
        @JsonProperty("author_id")
        private Integer authorId;
        @JsonProperty("created_at")
        private String createdAt;
        @JsonProperty("description")
        private String description;
        @JsonProperty("head_pipeline_id")
        private Object headPipelineId;
        @JsonProperty("id")
        private Integer id;
        @JsonProperty("iid")
        private Integer iid;
        @JsonProperty("last_edited_at")
        private Object lastEditedAt;
        @JsonProperty("last_edited_by_id")
        private Object lastEditedById;
        @JsonProperty("merge_commit_sha")
        private Object mergeCommitSha;
        @JsonProperty("merge_error")
        private Object mergeError;
        @JsonProperty("merge_params")
        private MergeParamsDTO mergeParams;
        @JsonProperty("merge_status")
        private String mergeStatus;
        @JsonProperty("merge_user_id")
        private Object mergeUserId;
        @JsonProperty("merge_when_pipeline_succeeds")
        private Boolean mergeWhenPipelineSucceeds;
        @JsonProperty("milestone_id")
        private Object milestoneId;
        @JsonProperty("source_branch")
        private String sourceBranch;
        @JsonProperty("source_project_id")
        private Integer sourceProjectId;
        @JsonProperty("state_id")
        private Integer stateId;
        @JsonProperty("target_branch")
        private String targetBranch;
        @JsonProperty("target_project_id")
        private Integer targetProjectId;
        @JsonProperty("time_estimate")
        private Integer timeEstimate;
        @JsonProperty("title")
        private String title;
        @JsonProperty("updated_at")
        private String updatedAt;
        @JsonProperty("updated_by_id")
        private Object updatedById;
        @JsonProperty("url")
        private String url;
        @JsonProperty("source")
        private SourceDTO source;
        @JsonProperty("target")
        private TargetDTO target;
        @JsonProperty("last_commit")
        private LastCommitDTO lastCommit;
        @JsonProperty("work_in_progress")
        private Boolean workInProgress;
        @JsonProperty("total_time_spent")
        private Integer totalTimeSpent;
        @JsonProperty("time_change")
        private Integer timeChange;
        @JsonProperty("human_total_time_spent")
        private Object humanTotalTimeSpent;
        @JsonProperty("human_time_change")
        private Object humanTimeChange;
        @JsonProperty("human_time_estimate")
        private Object humanTimeEstimate;
        @JsonProperty("assignee_ids")
        private List<Integer> assigneeIds;
        @JsonProperty("state")
        private String state;
        @JsonProperty("blocking_discussions_resolved")
        private Boolean blockingDiscussionsResolved;
        @JsonProperty("action")
        private String action;

        @lombok.NoArgsConstructor
        @lombok.Data
        public static class MergeParamsDTO {
            @JsonProperty("force_remove_source_branch")
            private String forceRemoveSourceBranch;
        }

        @lombok.NoArgsConstructor
        @lombok.Data
        public static class SourceDTO {
            @JsonProperty("id")
            private Integer id;
            @JsonProperty("name")
            private String name;
            @JsonProperty("description")
            private String description;
            @JsonProperty("web_url")
            private String webUrl;
            @JsonProperty("avatar_url")
            private Object avatarUrl;
            @JsonProperty("git_ssh_url")
            private String gitSshUrl;
            @JsonProperty("git_http_url")
            private String gitHttpUrl;
            @JsonProperty("namespace")
            private String namespace;
            @JsonProperty("visibility_level")
            private Integer visibilityLevel;
            @JsonProperty("path_with_namespace")
            private String pathWithNamespace;
            @JsonProperty("default_branch")
            private String defaultBranch;
            @JsonProperty("ci_config_path")
            private Object ciConfigPath;
            @JsonProperty("homepage")
            private String homepage;
            @JsonProperty("url")
            private String url;
            @JsonProperty("ssh_url")
            private String sshUrl;
            @JsonProperty("http_url")
            private String httpUrl;
        }

        @lombok.NoArgsConstructor
        @lombok.Data
        public static class TargetDTO {
            @JsonProperty("id")
            private Integer id;
            @JsonProperty("name")
            private String name;
            @JsonProperty("description")
            private String description;
            @JsonProperty("web_url")
            private String webUrl;
            @JsonProperty("avatar_url")
            private Object avatarUrl;
            @JsonProperty("git_ssh_url")
            private String gitSshUrl;
            @JsonProperty("git_http_url")
            private String gitHttpUrl;
            @JsonProperty("namespace")
            private String namespace;
            @JsonProperty("visibility_level")
            private Integer visibilityLevel;
            @JsonProperty("path_with_namespace")
            private String pathWithNamespace;
            @JsonProperty("default_branch")
            private String defaultBranch;
            @JsonProperty("ci_config_path")
            private Object ciConfigPath;
            @JsonProperty("homepage")
            private String homepage;
            @JsonProperty("url")
            private String url;
            @JsonProperty("ssh_url")
            private String sshUrl;
            @JsonProperty("http_url")
            private String httpUrl;
        }

        @lombok.NoArgsConstructor
        @lombok.Data
        public static class LastCommitDTO {
            @JsonProperty("id")
            private String id;
            @JsonProperty("message")
            private String message;
            @JsonProperty("title")
            private String title;
            @JsonProperty("timestamp")
            private String timestamp;
            @JsonProperty("url")
            private String url;
            @JsonProperty("author")
            private AuthorDTO author;

            @lombok.NoArgsConstructor
            @lombok.Data
            public static class AuthorDTO {
                @JsonProperty("name")
                private String name;
                @JsonProperty("email")
                private String email;
            }
        }
    }

    @lombok.NoArgsConstructor
    @lombok.Data
    public static class ChangesDTO {
        @JsonProperty("merge_status")
        private MergeStatusDTO mergeStatus;

        @lombok.NoArgsConstructor
        @lombok.Data
        public static class MergeStatusDTO {
            @JsonProperty("previous")
            private String previous;
            @JsonProperty("current")
            private String current;
        }
    }

    @lombok.NoArgsConstructor
    @lombok.Data
    public static class RepositoryDTO {
        @JsonProperty("name")
        private String name;
        @JsonProperty("url")
        private String url;
        @JsonProperty("description")
        private String description;
        @JsonProperty("homepage")
        private String homepage;
    }

    @lombok.NoArgsConstructor
    @lombok.Data
    public static class AssigneesDTO {
        @JsonProperty("id")
        private Integer id;
        @JsonProperty("name")
        private String name;
        @JsonProperty("username")
        private String username;
        @JsonProperty("avatar_url")
        private String avatarUrl;
        @JsonProperty("email")
        private String email;
    }
}
