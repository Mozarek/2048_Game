package visualization;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

import static java.lang.Math.min;

public class Config {
    public final int width;
    public final int height;
    public final int targetValueExponent;
    public final int maxGeneratedValueExponent;
    public final int moveHistorySize;
    public final int tilesOnStart;
    public final int animationTime;
    public final int fps;

    Config(String filePath) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(filePath));
        JSONObject initialData = (JSONObject) obj;

        this.width = (int) (long) initialData.get("width");
        this.height = (int) (long) initialData.get("height");
        this.targetValueExponent = (int) (long) initialData.get("targetValueExponent");

        this.maxGeneratedValueExponent = (int) (long) initialData.get("maxGeneratedValueExponent");

        this.moveHistorySize = (int) (long) initialData.get("moveHistorySize");

        int tilesOnStart = (int) (long) initialData.get("tilesOnStart");
        this.tilesOnStart = min(tilesOnStart, width*height);

        this.animationTime = (int) (long) initialData.get("animationTime");
        this.fps = (int) (long) initialData.get("fps");
    }
}
