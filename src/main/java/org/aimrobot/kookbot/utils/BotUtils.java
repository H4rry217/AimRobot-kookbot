package org.aimrobot.kookbot.utils;

import love.forte.simbot.definition.Contact;
import love.forte.simbot.message.ResourceImage;
import love.forte.simbot.resources.ByteArrayResource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;

/**
 * @program: AimRobot-QQBot
 * @description:
 * @author: H4rry217
 **/

public class BotUtils {

    public static Image textToImage(String output){
        return textToImage(output, new Font("宋体", Font.PLAIN, 20));
    }
    public static Image textToImage(String output, Font font){
        Image image = null;

        try{
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            createImage(output, font, outputStream);

            byte[] imageBytes = outputStream.toByteArray();
            InputStream inputStream = new ByteArrayInputStream(imageBytes);


            image = ImageIO.read(inputStream);

            outputStream.close();
            inputStream.close();

        }catch (Exception e){
            throw new RuntimeException(e);
        }

        return image;
    }

    public static byte[] imageToBytes(Image image) {
        byte[] bytes = new byte[0];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try{
            ImageIO.write((RenderedImage) image, "png", baos);

            bytes = baos.toByteArray();
            baos.close();
        }catch (Exception ignored){

        }

        return bytes;
    }

    public static ResourceImage textToResourceImage(String s){
        return love.forte.simbot.message.Image.of(new ByteArrayResource(UUID.randomUUID().toString(), BotUtils.imageToBytes(BotUtils.textToImage(s))));
    }

    public static void createImage(String text, Font font, OutputStream outputStream) throws Exception {
        int[] arr = getWidthAndHeight2(text, font);
        int width = arr[0];
        int height = arr[1];

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(Color.black);
        g2d.setFont(font);

        String[] strRow = text.split("\n");
        if(strRow.length > 1){
            for (int i = 0; i < strRow.length; i++) {
                g2d.drawString(strRow[i], 0, (i + 1) * font.getSize());
            }
        }else{
            g2d.drawString(text, 0, font.getSize());
        }

        g2d.dispose();

        ImageIO.write(image, "png", outputStream);
        // 输出png图片
    }

    private static int[] getWidthAndHeight2(String text, Font font) {
        String[] strings = text.split("\n");
        int max = 0;

        for (String s : strings) {
            int fullCharCount = countFullWidthChar(s);

            //如果是半角则宽度减为font.size的一半
            int len = ((s.length() - fullCharCount) / 2) + fullCharCount;

            max = Math.max(max, len);
        }

        int width = (font.getSize() * max);
        int height = font.getSize() * (strings.length + 1);

        return new int[]{width, height};
    }

    public static String prettyShow(String[] columnNames, List<Object[]> datas, String title){
        return prettyShow(columnNames, datas, title, true);
    }

    public static String prettyShow(String[] columnNames, List<Object[]> datas, String title, boolean splitLine){
        StringBuilder stringBuilder = new StringBuilder(title).append("\n");
        String joinerDelimiter = splitLine? "|": "";
        StringJoiner headerNamesJoiner = new StringJoiner(joinerDelimiter);

        int[] columnsLen = Arrays.stream(columnNames).mapToInt(t -> (t.length() + countFullWidthChar(t))).toArray();

        for (Object[] rowData : datas) {
            for (int i1 = 0; i1 < rowData.length; i1++) {
                String valStr = rowData[i1].toString();
                columnsLen[i1] = Math.max(columnsLen[i1], (valStr.length() + countFullWidthChar(valStr)));
            }
        }

        for (int a = 0; a < columnNames.length; a++){
            headerNamesJoiner.add(String.format("%-"+(columnsLen[a] - countFullWidthChar(columnNames[a]))+"s ", columnNames[a]));
        }

        stringBuilder.append(headerNamesJoiner).append("\n");

        if(splitLine){
            int len = stringBuilder.length() - 1;
            stringBuilder.append("-".repeat(Math.max(0, len))).append("\n");
        }

        for (Object[] rowData : datas) {
            StringJoiner rowDataJoiner = new StringJoiner(joinerDelimiter);
            for (int i = 0; i < rowData.length; i++) {
                String displayData = rowData[i].toString().trim();
                rowDataJoiner.add(String.format("%-"+(columnsLen[i] - countFullWidthChar(displayData))+"s ", displayData));
            }

            stringBuilder.append(rowDataJoiner).append("\n");
        }


        return stringBuilder.toString();
    }

    public static int countFullWidthChar(String str){
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if(!isHalfWidthChar(str.charAt(i))) count++;
        }

        return count;
    }

    public static boolean isHalfWidthChar(char c) {
        return c <= '\u00FF' || '\uFF61' <= c && c <= '\uFFDC' || '\uFFE8' <= c && c <= '\uFFEE';
    }

}
