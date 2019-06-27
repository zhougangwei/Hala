package chat.hala.hala.wight.banner;

import android.support.v4.view.ViewPager.PageTransformer;

import chat.hala.hala.wight.banner.transformer.AccordionTransformer;
import chat.hala.hala.wight.banner.transformer.BackgroundToForegroundTransformer;
import chat.hala.hala.wight.banner.transformer.DefaultTransformer;
import chat.hala.hala.wight.banner.transformer.ForegroundToBackgroundTransformer;
import chat.hala.hala.wight.banner.transformer.CubeInTransformer;
import chat.hala.hala.wight.banner.transformer.CubeOutTransformer;

import chat.hala.hala.wight.banner.transformer.DepthPageTransformer;
import chat.hala.hala.wight.banner.transformer.FlipHorizontalTransformer;
import chat.hala.hala.wight.banner.transformer.FlipVerticalTransformer;
import chat.hala.hala.wight.banner.transformer.RotateDownTransformer;
import chat.hala.hala.wight.banner.transformer.RotateUpTransformer;
import chat.hala.hala.wight.banner.transformer.ScaleInOutTransformer;
import chat.hala.hala.wight.banner.transformer.StackTransformer;
import chat.hala.hala.wight.banner.transformer.TabletTransformer;
import chat.hala.hala.wight.banner.transformer.ZoomInTransformer;
import chat.hala.hala.wight.banner.transformer.ZoomOutTranformer;
import chat.hala.hala.wight.banner.transformer.ZoomOutSlideTransformer;




public class Transformer {
    public static Class<? extends PageTransformer> Default = DefaultTransformer.class;
    public static Class<? extends PageTransformer> Accordion = AccordionTransformer.class;
    public static Class<? extends PageTransformer> BackgroundToForeground = BackgroundToForegroundTransformer.class;
    public static Class<? extends PageTransformer> ForegroundToBackground = ForegroundToBackgroundTransformer.class;
    public static Class<? extends PageTransformer> CubeIn = CubeInTransformer.class;
    public static Class<? extends PageTransformer> CubeOut = CubeOutTransformer.class;
    public static Class<? extends PageTransformer> DepthPage = DepthPageTransformer.class;
    public static Class<? extends PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;
    public static Class<? extends PageTransformer> FlipVertical = FlipVerticalTransformer.class;
    public static Class<? extends PageTransformer> RotateDown = RotateDownTransformer.class;
    public static Class<? extends PageTransformer> RotateUp = RotateUpTransformer.class;
    public static Class<? extends PageTransformer> ScaleInOut = ScaleInOutTransformer.class;
    public static Class<? extends PageTransformer> Stack = StackTransformer.class;
    public static Class<? extends PageTransformer> Tablet = TabletTransformer.class;
    public static Class<? extends PageTransformer> ZoomIn = ZoomInTransformer.class;
    public static Class<? extends PageTransformer> ZoomOut = ZoomOutTranformer.class;
    public static Class<? extends PageTransformer> ZoomOutSlide = ZoomOutSlideTransformer.class;
}
