package web.content;

public class AddUserContent {
    public static String renderAddUser(String sid) {

        String s = "<h2>유저 생성</h2>" +
                "<form action='/admin' method='GET'>" +
                "<input type='hidden' name='sessionId' value='" +
                sid +
                "'>" +
                "<input type='hidden' name='action' value='addUser'>" +
                """
                            <label>아이디:</label><br>
                            <input type='text' name='username'><br><br>
                        
                            <label>비밀번호:</label><br>
                            <input type='password' name='password'><br><br>
                        
                            <label>유형:</label><br>
                            1: 관리자<br>
                            2: 일반 사용자<br>
                            <input type='text' name='type'><br><br>
                        
                            <button type='submit'>생성</button>
                        </form>
                        <br>
                        """;

        return s;
    }
}
