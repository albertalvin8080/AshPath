package org.featherlessbipeds.ashpath.utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

public class ImageToBase64
{
    public static void main(String[] args) throws IOException
    {
        String img = "neymar.jpg";
        byte[] bytes = Files.readAllBytes(Path.of("src/main/resources/imgs/" + img));
        String str = Base64.getEncoder().encodeToString(bytes);

        String outputDir = "generated/";
        if (!Files.exists(Path.of(outputDir)))
        {
            Files.createDirectory(Path.of(outputDir));
        }

        File out = new File(outputDir + img.substring(0, img.indexOf(".jpg")) + ".txt");
        Files.deleteIfExists(out.toPath());

        PrintWriter pw = new PrintWriter(out);
        pw.write(str);
        pw.flush();
    }
}
