package com.example.ai;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;

/**
 * CSDN 博客爬取 + 转换为 Markdown（完整支持图片）
 * 目标博客：https://blog.csdn.net/m0_52165864/article/details/160280834
 */
public class CsdnBlogToMarkdown {

    // 目标博客URL
    private static final String BLOG_URL = "https://blog.csdn.net/";
    // 生成的Markdown保存路径
    private static final String SAVE_PATH = "D:/Rokid博客_转换结果.md";

    public static void main(String[] args) {
        try {
            // 1. 爬取博客HTML
            Document doc = crawlBlogHtml(BLOG_URL);
            // 2. 解析HTML为Markdown
            String markdown = parseHtmlToMarkdown(doc);
            // 3. 打印结果
            System.out.println("========== 转换后的 Markdown 内容 ==========\n");
            System.out.println(markdown);
            // 4. 保存到本地文件
            saveMarkdownToFile(markdown, SAVE_PATH);
            System.out.println("\n========== 文件已保存至：" + SAVE_PATH + " ==========");

        } catch (Exception e) {
            System.err.println("转换失败：");
            e.printStackTrace();
        }
    }

    /**
     * 爬取CSDN博客HTML内容
     */
    private static Document crawlBlogHtml(String url) throws IOException {
        Connection connection = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                .timeout(10000)
                .ignoreContentType(true)
                .ignoreHttpErrors(true);
        return connection.get();
    }

    /**
     * 核心：将HTML解析为标准Markdown格式（完整包含图片）
     */
    private static String parseHtmlToMarkdown(Document doc) {
        StringBuilder md = new StringBuilder();

        // 1. 提取博客标题
        String title = doc.select("h1.title-article").text().trim();
        md.append("# ").append(title).append("\n\n");

        // 2. 提取发布信息
        String time = doc.select("span.time").text().trim();
        md.append("> 发布时间：").append(time).append("\n\n");
        md.append("---\n\n");

        // 3. 提取博客正文（CSDN正文固定id：content_views）
        Element content = doc.getElementById("content_views");
        if (content == null) {
            return "未获取到博客正文";
        }

        // 遍历正文的所有子节点（支持文本、标签混合）
        traverseNode(content, md);

        return md.toString();
    }

    /**
     * 递归遍历 DOM 节点，生成 Markdown
     * 支持：标题、图片、代码块、表格、列表、分割线、加粗、斜体、段落等
     */
    private static void traverseNode(Node node, StringBuilder md) {
        if (node instanceof TextNode) {
            // 纯文本节点：保留原始文本内容（处理空白字符）
            String text = ((TextNode) node).getWholeText();
            if (!text.isBlank()) {
                md.append(text.trim());
            }
            return;
        }

        if (!(node instanceof Element)) {
            return;
        }

        Element element = (Element) node;
        String tagName = element.tagName().toLowerCase();

        // 根据标签类型进行不同的处理
        switch (tagName) {
            // 标题
            case "h1": case "h2": case "h3": case "h4": case "h5": case "h6":
                int level = Integer.parseInt(tagName.substring(1));
                md.append("#".repeat(level)).append(" ");
                // 递归处理标题内部的子节点（可能包含加粗、斜体等）
                for (Node child : element.childNodes()) {
                    traverseNode(child, md);
                }
                md.append("\n\n");
                break;

            // 图片（核心）
            case "img":
                String imgUrl = element.attr("data-original");
                if (imgUrl.isEmpty()) {
                    imgUrl = element.attr("src");
                }
                String alt = element.attr("alt").isEmpty() ? "博客图片" : element.attr("alt");
                md.append("![").append(alt).append("](").append(imgUrl).append(")\n\n");
                break;

            // 代码块
            case "pre":
                md.append("```java\n").append(element.text().trim()).append("\n```\n\n");
                break;
            case "code":
                // 如果 <code> 不是 <pre> 的子元素，则按内联代码处理
                if (!element.parent().tagName().equals("pre")) {
                    md.append("`").append(element.text()).append("`");
                } else {
                    // 已在 pre 中处理，避免重复
                }
                break;

            // 分割线
            case "hr":
                md.append("---\n\n");
                break;

            // 段落：递归处理内部子节点，最后加两个换行
            case "p":
                for (Node child : element.childNodes()) {
                    traverseNode(child, md);
                }
                md.append("\n\n");
                break;

            // 表格
            case "table":
                Elements trs = element.select("tr");
                for (Element tr : trs) {
                    Elements tds = tr.select("td, th");
                    md.append("| ");
                    for (Element td : tds) {
                        md.append(td.text().trim()).append(" | ");
                    }
                    md.append("\n");
                    if (trs.indexOf(tr) == 0) {
                        md.append("| --- ".repeat(tds.size())).append("|\n");
                    }
                }
                md.append("\n");
                break;

            // 无序列表项
            case "li":
                md.append("- ");
                for (Node child : element.childNodes()) {
                    traverseNode(child, md);
                }
                md.append("\n");
                break;
            case "ul": case "ol":
                // 列表容器直接递归子节点，由 li 负责输出内容
                for (Node child : element.childNodes()) {
                    traverseNode(child, md);
                }
                md.append("\n");
                break;

            // 加粗 / 斜体
            case "strong": case "b":
                md.append("**");
                for (Node child : element.childNodes()) {
                    traverseNode(child, md);
                }
                md.append("**");
                break;
            case "em": case "i":
                md.append("*");
                for (Node child : element.childNodes()) {
                    traverseNode(child, md);
                }
                md.append("*");
                break;

            // 块引用
            case "blockquote":
                md.append("> ");
                for (Node child : element.childNodes()) {
                    traverseNode(child, md);
                }
                md.append("\n\n");
                break;

            // 默认：递归处理所有子节点（适用于 div、span 等容器）
            default:
                for (Node child : element.childNodes()) {
                    traverseNode(child, md);
                }
                break;
        }
    }

    /**
     * 将Markdown内容保存到本地文件
     */
    private static void saveMarkdownToFile(String markdown, String path) throws IOException {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(markdown);
            writer.flush();
        }
    }
}