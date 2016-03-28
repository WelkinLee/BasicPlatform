package util.validate;

/**
 * Created with IntelliJ IDEA.
 * User: yangyang
 * Date: 14-12-22
 * Time: 下午11:03
 * To change this template use File | Settings | File Templates.
 */

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import logger.FundamentalLogger;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 简单验证码生成工具
 */
public class ImageUtil {
    private static final char[] chars = {'0','1','2','3','4','5','6','7','8','9',
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q',
            'r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H',
            'I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    private static final int SIZE = 4;
    private static final int LINES = 12;
    private static final int WIDTH = 100;
    private static final int HEIGHT = 60;
    private static int FONT_SIZE = 35;
    private static final FundamentalLogger logger = FundamentalLogger.getLogger(ImageUtil.class);
    /**
     *通过绘图函数生成验证码
     * @return
     */
    public static Map<String,BufferedImage> createImage() {
        StringBuffer sb = new StringBuffer();
        BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0,0,WIDTH,HEIGHT);
        Random random = new Random();
        //画字符
        for (int i=1;i<=SIZE;i++) {
            int r = random.nextInt(chars.length);
            g.setColor(getRandomColor());
            g.setFont(new Font(null,Font.BOLD+Font.ITALIC,FONT_SIZE));
            g.drawString(chars[r]+"",(i-1)*WIDTH/SIZE,(int)(HEIGHT*0.7));
            sb.append(chars[r]);
        }
        //画干扰线
        for (int i=1;i<=LINES;i++) {
            g.setColor(getRandomColor());
            g.drawLine(random.nextInt(WIDTH),random.nextInt(HEIGHT),
                    random.nextInt(WIDTH),random.nextInt(HEIGHT));
        }

        Map<String,BufferedImage> map = new HashMap<String, BufferedImage>();
        map.put(sb.toString(),image);
        return map;
    }

    /**
     *获取一个随机颜色 返回Color对象
     * @return
     */
    private static Color getRandomColor() {
        Random random = new Random();
        Color color = new Color(random.nextInt(256),random.nextInt(256),random.nextInt(256));
        return color;
    }

    /**
     * 将BufferImage转换为InputStream类型,并保存验证码图片
     * @param image
     * @return
     * @throws IOException
     */
    public static String getInputFilePath(BufferedImage image,String key,HttpServletRequest request) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
        encoder.encode(image);
        byte[] imageBts = bos.toByteArray();
        InputStream in = new ByteArrayInputStream(imageBts);

//        String validateImgRootPath = FundamentalConfigProvider.get("bookManageSystem.img.root.path");
//        String validateImgRelativePath = FundamentalConfigProvider.get("bookManageSystem.img.relative.path");
//        String imageDir = validateImgRootPath+validateImgRelativePath + "/validate/";
//        String imagePath = imageDir + key + ".jpg";
        String realPath = request.getSession().getServletContext().getRealPath("/");
        String imageDir = realPath + "\\image\\";
        String imagePath = imageDir + key + ".jpg";
        logger.info(realPath);
        File dir = new File(imageDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File imageFile = new File(imagePath);
        if (imageFile.exists()) {
            imageFile.delete();
        }
        imageFile.createNewFile();

        byte[] bs = new byte[1024*2];
        int len;
        OutputStream out = new FileOutputStream(imageFile);
        while ((len = in.read(bs)) != -1) {
            out.write(bs,0,len);
        }
        out.close();
        in.close();
        //return imagePath;
        return key;
    }

}
