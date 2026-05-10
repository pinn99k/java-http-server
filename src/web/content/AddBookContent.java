package web.content;

public class AddBookContent {

    public static String renderAddBook(String sid) {

        String s = "<h2>책 추가</h2>" +
                "<form action='/admin' method='GET'>" +

                // ✅ sessionId 유지 + action 전달
                "<input type='hidden' name='sessionId' value='" +
                sid +
                "'>" +
                "<input type='hidden' name='action' value='addBook'>" +
                """
                            <label>제목:</label><br>
                            <input type='text' name='title'><br><br>
                        
                            <label>저자:</label><br>
                            <input type='text' name='author'><br><br>
                        
                            <label>종류:</label><br>
                            1: 인쇄본<br>
                            2: eBook<br>
                            <input type='text' name='type'><br><br>
                        
                            <button type='submit'>추가</button>
                        </form>
                        <br>
                        """;
        return s;
    }
}
