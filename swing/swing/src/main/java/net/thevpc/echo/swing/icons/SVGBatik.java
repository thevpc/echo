package net.thevpc.echo.swing.icons;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package net.thevpc.common.iconset.util;
//
//import java.awt.Image;
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.UncheckedIOException;
//import java.net.URISyntaxException;
//import java.net.URL;
//import javax.imageio.ImageIO;
//import org.apache.batik.transcoder.TranscoderException;
//import org.apache.batik.transcoder.TranscoderInput;
//import org.apache.batik.transcoder.TranscoderOutput;
//import org.apache.batik.transcoder.image.JPEGTranscoder;
//import org.apache.batik.transcoder.image.PNGTranscoder;
//
///**
// *
// * @author thevpc
// */
//public class SVGBatik {
//
//    public static Image getImageFromSvg(URL url, int width0) {
//        try {
//            PNGTranscoder t = new PNGTranscoder();
//            TranscoderInput input = new TranscoderInput(url.toURI().toString());
//            ByteArrayOutputStream ostream = new ByteArrayOutputStream();
//            TranscoderOutput output = new TranscoderOutput(ostream);
//            t.addTranscodingHint(JPEGTranscoder.KEY_WIDTH, new Float(width0));
//            t.transcode(input, output);
//            ByteArrayInputStream bis = new ByteArrayInputStream(ostream.toByteArray());
//            return ImageIO.read(bis);
//        } catch (TranscoderException | URISyntaxException ex) {
//            throw new UncheckedIOException(new IOException(ex));
//        } catch (IOException ex) {
//            throw new UncheckedIOException(ex);
//        }
//    }
//}
