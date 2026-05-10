package web.content;

import util.PageUtil;
public class LoginContent {

    public static String renderLogin(String errMsg) {
        StringBuilder sb = new StringBuilder();

        if (errMsg != null) {
            sb.append("<p style='color:red;'>")
                    .append(PageUtil.escape(errMsg))
                    .append("</p>");
        }

        sb.append("<h1>로그인</h1>");
        sb.append("""
                <form action='/' method='GET'>
                    <input type='hidden' name='action' value='login'>
                
                    <label>아이디:</label><br>
                    <input type='text' name='username'><br><br>
                
                    <label>비밀번호:</label><br>
                    <input type='password' name='password'><br><br>
                
                    <button type='submit'>로그인</button>
                </form>
                <br>
                """);

        return sb.toString();
    }
}
