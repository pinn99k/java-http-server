package web.template;

import util.PageUtil;

public class BaseTemplate {

    public static String render(String title, String navHtml, String bodyHtml) {
        return """
            <!DOCTYPE html>
            <html lang="ko">
            <head>
                <meta charset="UTF-8">
                <title>%s</title>
                <style>
                    body { font-family: Arial, sans-serif; margin: 0; padding: 0; background: #fafafa; }
                    header { padding: 16px 20px; border-bottom: 1px solid #ddd; background: #f8f8f8; }
                    header h1 { margin: 0; font-size: 20px; }
                    nav { margin-top: 10px; }
                    nav a { margin-right: 12px; text-decoration: none; color: #333; }
                    nav a:hover { text-decoration: underline; }
                    main { padding: 20px; max-width: 900px; margin: 0 auto; }
                    section.content { padding: 20px; border: 1px solid #ddd; border-radius: 10px; background: #fff; }
                    .btn { display: inline-block; padding: 8px 12px; border: 1px solid #ddd; border-radius: 8px; background: #f5f5f5; color: #333; text-decoration: none; cursor: pointer; }
                    .btn:hover { background: #eee; }
                    .flash { padding: 10px 12px; border: 1px solid #ddd; border-radius: 10px; background: #fffbe6; margin-bottom: 14px; }
                    footer { margin-top: 30px; padding: 12px; border-top: 1px solid #ddd; text-align: center; color: #666; font-size: 12px; }
                </style>
            </head>
            <body>
                <header>
                    <h1>Library System</h1>
                    <nav>%s</nav>
                </header>

                <main>
                    <section class="content">
                        %s
                    </section>
                </main>

                <footer>짤 Library System</footer>
            </body>
            </html>
        """.formatted(PageUtil.escape(title), safe(navHtml), safe(bodyHtml));
    }

    private static String safe(String html) {
        return html == null ? "" : html;
    }
}
