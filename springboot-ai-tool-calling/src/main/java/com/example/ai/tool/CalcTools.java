package com.example.ai.tool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.regex.Pattern;

/**
 * 数学计算工具，支持加减乘除及括号运算
 * 注意：Java 17+ 已移除 Nashorn JS 引擎，使用自定义表达式解析器替代
 */
@Component
@Slf4j
public class CalcTools {

    // 安全正则：只允许数字 + 运算符
    private static final Pattern SAFE_EXPR = Pattern.compile("[0-9+\\-*/().\\s]+");

    @Tool(description = "计算数学表达式，支持加减乘除及括号，例如 10+20*3 或 (10+20)*3")
    public String calculate(
            @ToolParam(description = "数学表达式，例如 10+20*3 或 (10+20)*3") String expr) {
        try {

            if (!SAFE_EXPR.matcher(expr).matches()) {
                return "非法表达式，仅支持数字 + - * / ( )";
            }

            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("graal.js");
            if (engine == null) {
                log.error("错误：找不到 graal.js 引擎，请确认依赖已正确添加");
                return "";
            }

            Object result = engine.eval(expr);
            // 使用 Number 接口安全转换（避免直接强转 double）
            double numericResult = ((Number) result).doubleValue();
            return "计算结果:" + numericResult;
        } catch (Exception e) {
            log.error("计算失败", e);
            return "计算失败：" + e.getMessage();
        }
    }



}