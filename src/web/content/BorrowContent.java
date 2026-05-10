package web.content;
import static util.PageUtil.escape;

public class BorrowContent {
    public static String renderBorrowResult(boolean ok, String title) {
        if (ok) {
            return "<p>" + escape(title) + " 대출 성공!</p>";
        }
        return "<p>" + escape(title) + " 대출 실패!</p>";
    }
}
