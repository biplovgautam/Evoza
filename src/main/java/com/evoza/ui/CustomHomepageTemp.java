package com.evoza.ui;

import java.util.List;

public class CustomHomepageTemp {

    public static String generateHomePageContent(List<String> bookmarks, List<String> favoritePages, List<String> recentPages) {
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<!DOCTYPE html>");
        htmlContent.append("<html lang=\"en\">");
        htmlContent.append("<head>");
        htmlContent.append("<meta charset=\"UTF-8\">");
        htmlContent.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        htmlContent.append("<title>Home Page</title>");
        htmlContent.append("<link href=\"https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap\" rel=\"stylesheet\">");
        htmlContent.append("<style>");
        htmlContent.append("body { font-family: 'Poppins', sans-serif; background-color: #CAD6E1; margin: 0; display: flex; height: 100vh; }");
        htmlContent.append(".sidebar { width: 250px; background-color: #2C3E50; color: white; display: flex; flex-direction: column; align-items: center; padding: 20px; position: relative; }");
        htmlContent.append(".sidebar .logo { margin-bottom: 40px; }");
        htmlContent.append(".sidebar .decorations { position: absolute; bottom: 20px; width: 100%; display: flex; justify-content: center; }");
        htmlContent.append(".sidebar .decorations .triangle { width: 0; height: 0; border-left: 20px solid transparent; border-right: 20px solid transparent; border-top: 20px solid #1A252F; margin-right: 10px; }");
        htmlContent.append(".sidebar .decorations .square { width: 20px; height: 20px; background-color: #1A252F; }");
        htmlContent.append(".main-content { flex: 1; padding: 20px; overflow-y: auto; }");
        htmlContent.append(".header { display: flex; justify-content: flex-end; align-items: center; margin-bottom: 20px; }");
        htmlContent.append(".search-bar { display: flex; align-items: center; background-color: white; border-radius: 20px; padding: 5px 10px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); }");
        htmlContent.append(".search-bar input { border: none; outline: none; padding: 10px; font-size: 14px; border-radius: 20px; width: 200px; }");
        htmlContent.append(".search-bar img { width: 20px; height: 20px; margin-right: 10px; cursor: pointer; }");
        htmlContent.append(".actions { display: flex; align-items: center; margin-left: 20px; }");
        htmlContent.append(".actions img { width: 20px; height: 20px; margin-left: 10px; cursor: pointer; }");
        htmlContent.append(".section { margin-bottom: 40px; }");
        htmlContent.append(".section h2 { font-size: 18px; color: #333; margin-bottom: 20px; }");
        htmlContent.append(".section .grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(100px, 1fr)); gap: 20px; }");
        htmlContent.append(".section .grid .icon { background-color: white; border-radius: 10px; padding: 20px; text-align: center; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); }");
        htmlContent.append(".section .grid .icon img { width: 40px; height: 40px; margin-bottom: 10px; }");
        htmlContent.append(".section .grid .icon p { margin: 0; font-size: 14px; color: #555; }");
        htmlContent.append("</style>");
        htmlContent.append("</head>");
        htmlContent.append("<body>");
        htmlContent.append("<div class=\"sidebar\">");
        htmlContent.append("<img src=\"logo.png\" alt=\"Logo\" class=\"logo\">");
        htmlContent.append("<div class=\"decorations\">");
        htmlContent.append("<div class=\"triangle\"></div>");
        htmlContent.append("<div class=\"square\"></div>");
        htmlContent.append("</div>");
        htmlContent.append("</div>");
        htmlContent.append("<div class=\"main-content\">");
        htmlContent.append("<div class=\"header\">");
        htmlContent.append("<div class=\"search-bar\">");
        htmlContent.append("<img src=\"search-icon.png\" alt=\"Search\" onclick=\"search()\">");
        htmlContent.append("<input type=\"text\" id=\"searchInput\" placeholder=\"Find Bookmarks\">");
        htmlContent.append("</div>");
        htmlContent.append("<div class=\"actions\">");
        htmlContent.append("<img src=\"plus-icon.png\" alt=\"Add\">");
        htmlContent.append("<img src=\"edit-icon.png\" alt=\"Edit\">");
        htmlContent.append("<img src=\"settings-icon.png\" alt=\"Settings\">");
        htmlContent.append("</div>");
        htmlContent.append("</div>");
        htmlContent.append("<div class=\"section\">");
        htmlContent.append("<h2>Favorites</h2>");
        htmlContent.append("<div class=\"grid\">");
        for (String favoritePage : favoritePages) {
            htmlContent.append("<div class=\"icon\">");
            htmlContent.append("<img src=\"").append(favoritePage).append("-icon.png\" alt=\"").append(favoritePage).append("\">");
            htmlContent.append("<p>").append(favoritePage).append("</p>");
            htmlContent.append("</div>");
        }
        htmlContent.append("</div>");
        htmlContent.append("</div>");
        htmlContent.append("<div class=\"section\">");
        htmlContent.append("<h2>Dev Sites</h2>");
        htmlContent.append("<div class=\"grid\">");
        for (String bookmark : bookmarks) {
            htmlContent.append("<div class=\"icon\">");
            htmlContent.append("<img src=\"").append(bookmark).append("-icon.png\" alt=\"").append(bookmark).append("\">");
            htmlContent.append("<p>").append(bookmark).append("</p>");
            htmlContent.append("</div>");
        }
        htmlContent.append("</div>");
        htmlContent.append("</div>");
        htmlContent.append("<div class=\"section\">");
        htmlContent.append("<h2>General</h2>");
        htmlContent.append("<div class=\"grid\">");
        for (String recentPage : recentPages) {
            htmlContent.append("<div class=\"icon\">");
            htmlContent.append("<img src=\"").append(recentPage).append("-icon.png\" alt=\"").append(recentPage).append("\">");
            htmlContent.append("<p>").append(recentPage).append("</p>");
            htmlContent.append("</div>");
        }
        htmlContent.append("</div>");
        htmlContent.append("</div>");
        htmlContent.append("</div>");
        htmlContent.append("<script>");
        htmlContent.append("function search() {");
        htmlContent.append("var query = document.getElementById('searchInput').value;");
        htmlContent.append("if (query) {");
        htmlContent.append("if (!query.startsWith('http://') && !query.startsWith('https://')) {");
        htmlContent.append("query = 'http://' + query;");
        htmlContent.append("}");
        htmlContent.append("window.location.href = query;");
        htmlContent.append("}");
        htmlContent.append("}");
        htmlContent.append("</script>");
        htmlContent.append("</body>");
        htmlContent.append("</html>");
        return htmlContent.toString();
    }
}
