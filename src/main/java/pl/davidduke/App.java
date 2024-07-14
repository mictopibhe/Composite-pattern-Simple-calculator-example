package pl.davidduke;

import lombok.extern.slf4j.Slf4j;
import pl.davidduke.util.ThreeBuilder;
import pl.davidduke.util.Tokenizer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// 1 + a * 2
@Slf4j
public class App {
    public static void main(String[] args) {
        try {
            Map<String, String> variables = extractVariables(args);
            Tokenizer tokenizer = new Tokenizer(args[0], variables);
            log.info("{} = {}", args[0],
                    Objects.requireNonNull(ThreeBuilder.buildExpressionTree(tokenizer.tokenize())).calculate());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private static Map<String, String> extractVariables(String[] args) {
        Map<String, String> variables = new HashMap<>();
        for (int i = 1; i < args.length; i++) {
            String[] splitLine = args[i]
                    .replace(" ", "")
                    .replace(',', '.')
                    .split("=");

            variables.put(splitLine[0], splitLine[1]);
        }
        return variables;
    }
}
