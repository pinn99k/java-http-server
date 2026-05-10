package util;

public class FlashMessage {

    public static String home(String flash) {
        if (flash == null) return null;
        return switch (flash) {
            case "login_err" -> "아이디/비밀번호가 올바르지 않습니다.";
            case "login_required" -> "로그인이 필요합니다.";
            default -> null;
        };
    }

    public static String user(String flash) {
        if (flash == null) return null;
        return switch (flash) {
            case "borrow_ok" -> "대출 성공!";
            case "borrow_fail" -> "대출 실패!";
            case "return_ok" -> "반납 성공!";
            case "return_fail" -> "반납 실패!";
            default -> null;
        };
    }

    public static String admin(String flash) {
        if (flash == null) return null;
        return switch (flash) {
            case "add_book_ok" -> "책 추가 성공!";
            case "add_book_fail" -> "책 추가 실패!";
            case "add_user_ok" -> "유저 생성 성공!";
            case "add_user_fail" -> "유저 생성 실패!";
            default -> null;
        };
    }

    public static String wrapFlashBox(String msg) {
        if (msg == null || msg.isBlank()) return "";
        return "<div class='flash'>" + PageUtil.escape(msg) + "</div>";
    }
}
