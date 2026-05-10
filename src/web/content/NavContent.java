package web.content;

import util.PageUtil;

public class NavContent {

    public static String render(String sid) {
        String adminLink = (sid == null || sid.isBlank())
                ? "/?flash=login_required"
                : "/admin?sessionId=" + sid;

        String userLink = (sid == null || sid.isBlank())
                ? "/?flash=login_required"
                : "/user?sessionId=" + sid;

        return """
                    <a href="/">Home</a>
                    <a href="%s">Admin</a>
                    <a href="%s">User</a>
                """.formatted(PageUtil.escape(adminLink), PageUtil.escape(userLink));
    }
}
